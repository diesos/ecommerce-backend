package com.Side.Project.ecommerce_backend.service;

import com.Side.Project.ecommerce_backend.api.models.RegistrationBody;
import com.Side.Project.ecommerce_backend.exception.UserAlreadyExist;
import com.Side.Project.ecommerce_backend.models.LocalUser;
import com.Side.Project.ecommerce_backend.models.dao.LocalUserDAO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private LocalUserDAO localUserDAO;

    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExist {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent())
        {
            throw new UserAlreadyExist();
        }
        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUsername());
        // TODO :  Encrypt password.
        user.setPassword(registrationBody.getPassword());
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        return localUserDAO.save(user);
    }
}
