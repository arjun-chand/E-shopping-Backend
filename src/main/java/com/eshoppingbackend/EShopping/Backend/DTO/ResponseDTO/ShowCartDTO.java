package com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO;

import com.eshoppingbackend.EShopping.Backend.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShowCartDTO {
    List<Product> products;
    private int totalPrice;
    private int totalItems;
}
