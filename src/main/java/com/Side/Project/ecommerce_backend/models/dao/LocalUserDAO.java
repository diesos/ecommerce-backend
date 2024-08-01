package com.Side.Project.ecommerce_backend.models.dao;

import com.Side.Project.ecommerce_backend.models.LocalUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface LocalUserDAO extends ListCrudRepository<LocalUser, Long> {


    Optional<LocalUser> findByUsernameIgnoreCase(String username);

    Optional<LocalUser> findByEmailIgnoreCase(String email);
}
