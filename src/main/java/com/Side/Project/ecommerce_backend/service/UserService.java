package com.Side.Project.ecommerce_backend.service;

import com.Side.Project.ecommerce_backend.api.models.LoginBody;
import com.Side.Project.ecommerce_backend.api.models.RegistrationBody;
import com.Side.Project.ecommerce_backend.exception.UserAlreadyExist;
import com.Side.Project.ecommerce_backend.models.LocalUser;
import com.Side.Project.ecommerce_backend.models.dao.LocalUserDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    /** Local User DAO **/
    private LocalUserDAO localUserDAO;
    private EncryptionService encryptionService;
    private JWTService jwtService;


    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExist {
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
        return localUserDAO.save(user);
    }

    public String loginUser(LoginBody loginBody){
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase((loginBody.getUsername()));
        if (opUser.isPresent()){
            LocalUser user = opUser.get();
            if (encryptionService.VerifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
     return null;
    }
}
