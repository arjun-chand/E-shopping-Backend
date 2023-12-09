package com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO;

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
public class PlaceOrderDTO {
    List<Integer> products;
    String userName;
}
