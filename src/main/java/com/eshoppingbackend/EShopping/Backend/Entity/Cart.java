package com.eshoppingbackend.EShopping.Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id") // Use the actual column name in your database
    private int cartId;


    @OneToOne
    Users users;

    private int totalItems;

    @Column(name = "total_price") // Use the actual column name in your database
    private int totalPrice;


    @OneToMany
    List<Product> products;
}
