package com.eshoppingbackend.EShopping.Backend.Controller;

import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.AddProductDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.GeneralMessageDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.UnAuthorised;
import com.eshoppingbackend.EShopping.Backend.Entity.Product;
import com.eshoppingbackend.EShopping.Backend.Exception.UserNotFoundException;
import com.eshoppingbackend.EShopping.Backend.Exception.WrongAccessException;
import com.eshoppingbackend.EShopping.Backend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/e-shopping/admin")
public class Admin {
    @Autowired
    ProductService productService;
    @PostMapping("/product/add")
    public ResponseEntity addProduct(@RequestBody AddProductDTO addProductDTO){
        try {
            productService.addProduct(addProductDTO);
            return new ResponseEntity(new GeneralMessageDTO("Product Successfully added"),HttpStatus.CREATED);
        }catch (WrongAccessException e){
            return new ResponseEntity(new UnAuthorised(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }catch (UserNotFoundException userNotFoundException){
            return new ResponseEntity(new GeneralMessageDTO(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products")
    public ResponseEntity getAllProducts(@RequestParam String userName){
        try {
            List<Product> productList = productService.getAllProducts(userName);
            return new ResponseEntity(productList,HttpStatus.OK);
        }catch (WrongAccessException wrongAccessException){
            return new ResponseEntity(new UnAuthorised(wrongAccessException.getMessage()),HttpStatus.UNAUTHORIZED);
        }catch (UserNotFoundException userNotFoundException){
            return new ResponseEntity(new GeneralMessageDTO(userNotFoundException.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
}
