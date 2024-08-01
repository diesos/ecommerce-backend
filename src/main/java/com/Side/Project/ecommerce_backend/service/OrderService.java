package com.Side.Project.ecommerce_backend.service;

import com.Side.Project.ecommerce_backend.models.LocalUser;
import com.Side.Project.ecommerce_backend.models.WebOrder;
import com.Side.Project.ecommerce_backend.models.dao.WebOrderDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private WebOrderDAO webOrderDAO;

    public OrderService(WebOrderDAO webOrderDAO) {
        this.webOrderDAO = webOrderDAO;
    }

    public List<WebOrder> getOrders(LocalUser user){
        return webOrderDAO.findByUser(user);
    }


}
