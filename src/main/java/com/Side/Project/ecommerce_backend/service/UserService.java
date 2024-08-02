package com.Side.Project.ecommerce_backend.service;

import com.Side.Project.ecommerce_backend.api.models.LoginBody;
import com.Side.Project.ecommerce_backend.api.models.RegistrationBody;
import com.Side.Project.ecommerce_backend.exception.EmailFailureException;
import com.Side.Project.ecommerce_backend.exception.UserAlreadyExist;
import com.Side.Project.ecommerce_backend.exception.UserNotVerifiedException;
import com.Side.Project.ecommerce_backend.models.LocalUser;
import com.Side.Project.ecommerce_backend.models.VerificationToken;
import com.Side.Project.ecommerce_backend.models.dao.LocalUserDAO;
import com.Side.Project.ecommerce_backend.models.dao.VerificationTokenDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    /** Local User DAO **/
    private LocalUserDAO localUserDAO;
    private VerificationTokenDAO verificationTokenDAO;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    private EmailService emailService;



    public UserService(LocalUserDAO localUserDAO, VerificationTokenDAO verificationTokenDAO,
                       EncryptionService encryptionService, JWTService jwtService, EmailService emailService) {
        this.localUserDAO = localUserDAO;
        this.verificationTokenDAO = verificationTokenDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    /**
     *
     * @param registrationBody the registration request
     * @return saving the user into db, after passing controllers if not throw errors
     * @throws UserAlreadyExist if user is already registered
     * @throws EmailFailureException if the email token is not validated
     */


    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExist, EmailFailureException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent())
        {
            throw new UserAlreadyExist();
        }
        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUsername());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        verificationTokenDAO.save(verificationToken);
        return localUserDAO.save(user);
    }

    /**
     * Create a verification Token
     * @param user request
     * @return a token string
     */


    private VerificationToken createVerificationToken(LocalUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    /**
     * Login a user provides an authentification token back.
     * @param loginBody the login request
     * @return the auth token.Nul if the request was invalid.
     */

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase((loginBody.getUsername()));
        if (opUser.isPresent()){
            LocalUser user = opUser.get();
            if (encryptionService.VerifyPassword(loginBody.getPassword(), user.getPassword())) {
                if (user.isEmailVerified()) {
                    return jwtService.generateJWT(user);
                }
                else
                {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                 boolean resend = verificationTokens.size() == 0 ||
                         verificationTokens.get(0).getCreatedTimeStamp.before(new Timestamp(System.currentTimeMillis() - (60*60*1000)));
                 if (resend) {
                     VerificationToken verificationToken = createVerificationToken(user);
                     verificationTokenDAO.save(verificationToken);
                     emailService.sendVerificationEmail(verificationToken);
                 }
                 throw new UserNotVerifiedException(resend);
                }
            }
        }
     return null;
    }

    @Transactional
    public boolean verifyUser(String token){
    Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
        if (opToken.isPresent())
        {
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if (!user.isEmailVerified()){
                user.setEmailVerified(true);
                localUserDAO.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
}
