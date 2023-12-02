package com.eshoppingbackend.EShopping.Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oId;
    private String orderName;
    @ManyToOne
    Users users;
    private Date estimateDelivery;
    private int totalOrderPrice;
    @OneToMany
    List<Product> orderItems;
    boolean isDelivered;
    private int totalOrderItems;
}
