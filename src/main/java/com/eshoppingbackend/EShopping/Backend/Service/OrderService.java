package com.eshoppingbackend.EShopping.Backend.Service;

import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.PlaceOrderDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.BillDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.OrderDTO;
import com.eshoppingbackend.EShopping.Backend.Entity.Orders;
import com.eshoppingbackend.EShopping.Backend.Entity.Product;
import com.eshoppingbackend.EShopping.Backend.Entity.Users;
import com.eshoppingbackend.EShopping.Backend.Exception.OrderNotFoundException;
import com.eshoppingbackend.EShopping.Backend.Exception.UserNotFoundException;
import com.eshoppingbackend.EShopping.Backend.Exception.WrongAccessException;
import com.eshoppingbackend.EShopping.Backend.Exception.WrongPreferenceException;
import com.eshoppingbackend.EShopping.Backend.Repository.OrderRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<OrderDTO> getAllOrdersByDeliveryPreference(int uid, String deliveryPreference) {
        if (usersService.getUserById(uid) == null) {
            throw new UserNotFoundException("User does not exist in our database");
        }

        List<Orders> orders;
        if (deliveryPreference.equals("delivered")) {
            orders = orderRepository.getAllDeliveredOrdersByUserId(uid);
        } else if (deliveryPreference.equals("Not_Delivered")) {
            orders = orderRepository.getAllNotDeliveredOrdersByUserId(uid);
        } else {
            throw new WrongPreferenceException("Delivery preference is not correct");
        }

        // Convert Orders to OrdersDTO
        List<OrderDTO> ordersDTOList = orders.stream()
                .map(order -> new OrderDTO(order.getOId(), order.getTotalOrderPrice(), order.getOrderName()))
                .collect(Collectors.toList());

        return ordersDTOList;
    }

    public void cancelOrder(int uid, int oid){
        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException(String.format("User with Id %d does not exist in System",uid));
        }
        if(orderRepository.findById(oid).orElse(null) == null){
            throw new OrderNotFoundException(String.format("Order with order Id: %d does not exist in system",oid));
        }

        Orders order = orderRepository.getOrderByOrderIdAndUserId(uid,oid);//chance to get null so if admin line

        Users user = usersService.getUserById(uid);
        if(usersService.isAdmin(user.getUserName())){
            order = orderRepository.findById(uid).orElse(null);
        }
        //order doest not belong to that id
        if(order == null){
            throw new WrongAccessException(String.format("User with user Id %d does not have access to cancel order with order Id %d",uid,oid));
        }

        //rmoving entry from parent beacause we are unable to delete product because it is dependent
        List<Product> products = order.getOrderItems();

        for(Product p : products){
            int pid = p.getPId();
            orderRepository.deleteOrderVsProduct(oid,pid);
        }
        orderRepository.deleteById(oid);
    }
}
