package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_join")
    private long dateJoin;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "date_created")
    private long dateCreated;

    @Column(name = "date_modified")
    private Long dateModified;
}
