package com.eshoppingbackend.EShopping.Backend.Service;

import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.AddProductDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.AddUsersDTO;
import com.eshoppingbackend.EShopping.Backend.Entity.Product;
import com.eshoppingbackend.EShopping.Backend.Exception.WrongAccessException;
import com.eshoppingbackend.EShopping.Backend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UsersService usersService;
    public void addProduct(AddProductDTO addProductDTO){
        if(usersService.isAdmin(addProductDTO.getUserName())){
            Product product = new Product();
            product.setProductName(addProductDTO.getProductName());
            product.setCategory(addProductDTO.getCategory());
            product.setPrice(addProductDTO.getPrice());
            product.setDescription(addProductDTO.getDescription());
            product.setQuantity(addProductDTO.getQuantity());
            productRepository.save(product);
        }
        else {
            throw new WrongAccessException("user does not have admin access");
        }
    }

    public List<Product> getAllProducts(String userName){
        if(usersService.isAdmin(userName)){
            List<Product> productList = productRepository.findAll();
            return productList;
        }else {
            throw new WrongAccessException("User is not Authorized to see these details");
        }
    }

    public Product getProductById(int pId){
        return productRepository.findById(pId).orElse(null);
    }
}
