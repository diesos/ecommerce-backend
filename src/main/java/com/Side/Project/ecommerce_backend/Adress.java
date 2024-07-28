package com.Side.Project.ecommerce_backend;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Adress {

    @Id
    private long id;

    @Column
    private String user;

    @Column
    private String adress_1;

    @Column
    private String adress_2;

    @Column
    private String City;

    @Column
    private String country;

    @Column
    private String post_code;

    @Column
    private boolean is_active;
}
