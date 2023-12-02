package com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO;


import com.eshoppingbackend.EShopping.Backend.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddUsersDTO {
    private String userName;
    private String password;
    private String email;
    private Long phoneNumber;
    private String address;
    Role role;
}
