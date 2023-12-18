package com.eshoppingbackend.EShopping.Backend.Controller;

import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.AddUsersDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.LoginRequestDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.PlaceOrderDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.*;
import com.eshoppingbackend.EShopping.Backend.Entity.Orders;
import com.eshoppingbackend.EShopping.Backend.Entity.Users;
import com.eshoppingbackend.EShopping.Backend.Exception.*;
import com.eshoppingbackend.EShopping.Backend.Service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Service End-points", description = "this controlller contains all the end points that the normal can perform")
@RestController
@RequestMapping("/e-shopping/users")
public class UserController {
    @Autowired
    UsersService usersService;

    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;

    @Autowired
    MailService mailService;

    @Autowired
    ComplainService complainService;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody AddUsersDTO addUsersDTO){
        try {
            mailService.sendEmail();
            Users user = usersService.signUP(addUsersDTO);
            return new ResponseEntity(user, HttpStatus.CREATED);
        }catch (AdminNotAvailableException adminNotAvailableException){
            return new ResponseEntity(adminNotAvailableException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/login")
    public ResponseEntity logIn(@RequestBody LoginRequestDTO loginRequestDTO){
        try {
            LoginResponseDTO loginResponseDTO = usersService.logIn(loginRequestDTO);
            return new ResponseEntity("Login successfull",HttpStatus.OK);
        }catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (WrongCredentialsException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/admin/approve-admin")
    public ResponseEntity approveAdmin(@RequestParam String userName,@RequestParam int userId){
        try{
            usersService.approveAdmin(userName, userId);
            return new ResponseEntity(String.format("Admin with the userId %d got approved", userId),HttpStatus.OK);
        }catch (WrongAccessException wrongAccessException){
            return new ResponseEntity(wrongAccessException.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch (UserNotFoundException userNotFoundException){
            return new ResponseEntity(new GeneralMessageDTO(userNotFoundException.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{pid}")
    public ResponseEntity addProductToCart(@PathVariable int pid, @RequestParam int uid){
        try {
            cartService.addProductInCart(pid,uid);
            return new ResponseEntity(new GeneralMessageDTO(String.format("product with Product Id %d and user with ID %d got successfully inserted",pid,uid)),HttpStatus.OK);
        }catch (ProductNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);
        }catch (UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/show-cart")
    public ResponseEntity getAllProductsFromCart( @RequestParam int uid){
        try {
            ShowCartDTO showCartDTO = cartService.showUserCart(uid);
            return new ResponseEntity(showCartDTO,HttpStatus.OK);
        }catch (UserNotFoundException e){
            return new ResponseEntity( new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cart/remove-item")
    public ResponseEntity removeItemFromUserCart(@RequestParam int uid, @RequestParam int pid){
        try {
            cartService.removeProductFromUserCart(uid, pid);
            return new ResponseEntity(new GeneralMessageDTO(String.format("product with pid %d got successfully removed from cart",pid)),HttpStatus.OK);

        }catch (UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping("/place-order")
    public ResponseEntity placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
        try {
            complainService.registerComplain("xyz","123");
            BillDTO bill = orderService.placeOrder(placeOrderDTO);
            return new ResponseEntity(bill, HttpStatus.CREATED);
        }catch (UserNotFoundException userNotFoundException){
            return new ResponseEntity(new GeneralMessageDTO(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{uid}/your-orders")
    public ResponseEntity getAllOrders(@PathVariable int uid){
        try {
            List<Orders> orders = orderService.getAllOrdersByUserId(uid);
            return new ResponseEntity(orders,HttpStatus.OK);
        }catch(UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{uid}/all-orders")
    public ResponseEntity getAllOrdersByPreference(@PathVariable int uid, @RequestParam String deliveryPreference){
        try {
            List<OrderDTO> allOrders = orderService.getAllOrdersByDeliveryPreference(uid, deliveryPreference);
            return new ResponseEntity(allOrders,HttpStatus.OK);
        }catch (UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (WrongPreferenceException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/orders/{uid}/cancel-orders/{oid}")
    public ResponseEntity deleteOrderByOrderIdAndUserId(@PathVariable int uid, @PathVariable int oid){
        try {
            orderService.cancelOrder(uid,oid);
            return new ResponseEntity(new GeneralMessageDTO(String.format("order with order Id %d got cancelled with user Id %d",oid,uid)),HttpStatus.OK);
        }catch (UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);
        }catch (OrderNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);
        }catch (WrongAccessException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }
}
