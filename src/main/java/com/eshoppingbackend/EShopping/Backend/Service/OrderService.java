package com.eshoppingbackend.EShopping.Backend.Service;

import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.PlaceOrderDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.BillDTO;
import com.eshoppingbackend.EShopping.Backend.Entity.Orders;
import com.eshoppingbackend.EShopping.Backend.Entity.Product;
import com.eshoppingbackend.EShopping.Backend.Entity.Users;
import com.eshoppingbackend.EShopping.Backend.Exception.UserNotFoundException;
import com.eshoppingbackend.EShopping.Backend.Exception.WrongPreferenceException;
import com.eshoppingbackend.EShopping.Backend.Repository.OrderRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    UsersService usersService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderRepository orderRepository;
    public BillDTO placeOrder(PlaceOrderDTO placeOrderDTO){
        List<Integer> productIds = placeOrderDTO.getProducts();
        String userName = placeOrderDTO.getUserName();

        Users user = usersService.findUserByName(userName);

        if(usersService.findUserByName(userName) == null){
            throw new UserNotFoundException("User does not exist in System");
        }

        List<Product> products = new ArrayList<>();
        for (int pid : productIds){
            products.add(productService.getProductById(pid));
        }
        Orders obj = new Orders();

        Date date = new Date();
        date = DateUtils.addDays(date,7);
        obj.setEstimateDelivery(date);
        obj.setTotalOrderItems(products.size());
        int totalPrice = 0;

        for (Product p : products){
            totalPrice += p.getPrice();
        }
        obj.setTotalOrderPrice(totalPrice);
        obj.setUsers(user);
        orderRepository.save(obj);

        for (Product p: products){
            orderRepository.insertProductVsOrder(obj.getOId(), p.getPId());
        }

        BillDTO bill = new BillDTO();
        bill.setProducts(products);
        bill.setTotalItems(productIds.size());
        bill.setTotalBill(totalPrice);

        return bill;
    }

    public List<Orders> getAllOrdersByUserId(int uid){
        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException("user not exist in the System");
        }
        return orderRepository.getAllOrdersByUserId(uid);
    }

    public List<Orders> getAllOrdersByDeliveryPreference(int uid, String deliveryPreference){
        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException("user does not exist in out database");
        }
        if(deliveryPreference.equals("delivered")){
            return orderRepository.getAllDeliveredOrdersByUserId(uid);
        } else if (deliveryPreference.equals("Not_Delivered")) {
            return orderRepository.getAllNotDeliveredOrdersByUserId(uid);
        }else {
            throw new WrongPreferenceException("Delivery preference is not Correct");
        }

    }
}
