package com.eshoppingbackend.EShopping.Backend.Entity;

import com.eshoppingbackend.EShopping.Backend.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uId;
    @Column(unique = true)
    private String userName;
    private String password;
    private String address;

    @Column(length = 10, unique = true)
    private Long phoneNumber;

    @Column(unique = true)
    private String email;
    boolean adminApprove;
    private String role;
}
