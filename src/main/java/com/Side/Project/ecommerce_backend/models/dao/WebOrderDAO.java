package com.Side.Project.ecommerce_backend.models.dao;

import com.Side.Project.ecommerce_backend.models.LocalUser;
import com.Side.Project.ecommerce_backend.models.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDAO extends ListCrudRepository<WebOrder, Long> {
    List<WebOrder> findByUser(LocalUser user);
}
