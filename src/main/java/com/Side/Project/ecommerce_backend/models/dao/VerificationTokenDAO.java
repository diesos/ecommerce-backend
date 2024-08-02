package com.Side.Project.ecommerce_backend.models.dao;

import com.Side.Project.ecommerce_backend.models.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {
}
