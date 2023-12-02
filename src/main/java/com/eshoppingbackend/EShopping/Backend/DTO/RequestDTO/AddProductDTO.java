package com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddProductDTO {
    private String productName;
    private String category;
    private String description;
    private int quantity;
    private int price;
    private String userName;
}
