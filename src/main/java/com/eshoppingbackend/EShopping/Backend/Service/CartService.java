package com.eshoppingbackend.EShopping.Backend.Service;

import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.ShowCartDTO;
import com.eshoppingbackend.EShopping.Backend.Entity.Cart;
import com.eshoppingbackend.EShopping.Backend.Entity.Product;
import com.eshoppingbackend.EShopping.Backend.Entity.Users;
import com.eshoppingbackend.EShopping.Backend.Exception.ProductNotFoundException;
import com.eshoppingbackend.EShopping.Backend.Exception.UserNotFoundException;
import com.eshoppingbackend.EShopping.Backend.Repository.CartRepository;
import com.eshoppingbackend.EShopping.Backend.Repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductService productService;

    @Autowired
    UsersService usersService;

    public void createCart(Cart cart){
        cartRepository.save(cart);
    }

    public void addProductInCart(int pId, int uid){
        if(productService.getProductById(pId) == null){
            throw new ProductNotFoundException(String.format("Product with ID %d does not exist in the system", pId));
        }
        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException(String.format("User with %d ID is not exist in Out Database",uid));
        }
        Cart c = cartRepository.getCartByUserId(uid);
        if(c == null){
            c = new Cart();
            c.setUsers(usersService.getUserById(uid));
            createCart(c);
        }
        Product product = productService.getProductById(pId);

        int productPrize = product.getPrice();
        int totalQuantity = c.getTotalItems() + 1;

        cartRepository.updateTotalItems(c.getCartId(), totalQuantity);
        cartRepository.updateTotalPrice(c.getCartId(), c.getTotalPrice() + productPrize);
        cartRepository.insertProductInCart(c.getCartId(), product.getPId());
    }

    public ShowCartDTO showUserCart(int uid){
        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException("User does not exist in the DataBase");
        }
        Cart c = cartRepository.getCartByUserId(uid);
        List<Object[]> productDB = cartRepository.getAllProductsByCartId(c.getCartId());
        List<Product> products = new ArrayList<>();

        for (Object[] obj : productDB){
            Product p = productService.getProductById(Integer.parseInt(obj[0].toString()));
            products.add(p);
        }
        ShowCartDTO showCartDTOObj = new ShowCartDTO();
        showCartDTOObj.setProducts(products);
        showCartDTOObj.setTotalItems(c.getTotalItems());
        showCartDTOObj.setTotalPrice(c.getTotalPrice());
        return showCartDTOObj;
    }

    public void removeProductFromUserCart(int uid, int pid){
        if(cartRepository.getAllProductsByCartId(uid) == null){
            throw new UserNotFoundException(String.format("User with ID %d does not exist in System",uid));
        }
        Cart c = cartRepository.getCartByUserId(uid);
        Product p = productService.getProductById(pid);
        cartRepository.updateTotalPrice(c.getCartId(),c.getTotalPrice()-p.getPrice());
        cartRepository.updateTotalItems(c.getCartId(),c.getTotalItems()-1);
        cartRepository.removeProductFromUserCart(c.getCartId(),pid);
    }
}
