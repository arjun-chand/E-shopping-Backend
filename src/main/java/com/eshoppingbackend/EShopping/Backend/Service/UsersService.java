package com.eshoppingbackend.EShopping.Backend.Service;

import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.AddUsersDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.LoginRequestDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.LoginResponseDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.ShowCartDTO;
import com.eshoppingbackend.EShopping.Backend.Entity.Cart;
import com.eshoppingbackend.EShopping.Backend.Entity.Users;
import com.eshoppingbackend.EShopping.Backend.Exception.AdminNotAvailableException;
import com.eshoppingbackend.EShopping.Backend.Exception.UserNotFoundException;
import com.eshoppingbackend.EShopping.Backend.Exception.WrongAccessException;
import com.eshoppingbackend.EShopping.Backend.Exception.WrongCredentialsException;
import com.eshoppingbackend.EShopping.Backend.Repository.UsersRepository;
import jakarta.persistence.TransactionRequiredException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    public Users getUserById(int uId){
        return usersRepository.getReferenceById(uId);
    }

    public Users signUP(AddUsersDTO usersDTO){
        List<Object[]> adminCount= usersRepository.countOfAdminsAvailable();
        int count = Integer.parseInt(adminCount.get(0)[0].toString());
        /*if(count == 0){
            throw new AdminNotAvailableException("Admin is not Available, kindly do Admin Setup");
        }*/

        String role = usersDTO.getRole().toString();
        Users user = new Users();
        user.setUserName(usersDTO.getUserName());
        user.setPassword(usersDTO.getPassword());
        user.setAddress(usersDTO.getAddress());
        user.setPhoneNumber(usersDTO.getPhoneNumber());
        user.setEmail(usersDTO.getEmail());
        user.setAdminApprove(false);
        user.setRole(usersDTO.getRole().toString());

        if(count == 0 && role.equals("Admin")){
            if(usersDTO.getPassword().equals("accio123")){
                user.setAdminApprove(true);
            }
        }

        usersRepository.save(user);
        return user;
    }

    public LoginResponseDTO logIn(LoginRequestDTO loginRequestDTO){
        String userName = loginRequestDTO.getUserName();
        Users user = usersRepository.findByUserName(userName);
        if(user != null){
            if(user.getPassword().equals(loginRequestDTO.getPassword())) {
                return new LoginResponseDTO("Success");
            }
            else {
                throw new WrongCredentialsException("User Password is wrong");
            }
        }else {
            throw new UserNotFoundException("following user is not exist ");
        }
    }

    public boolean isAdmin(String userName){

        Users user = usersRepository.findByUserName(userName);
        if(user == null){
            throw new UserNotFoundException("following user is not exist as Admin");
        }
        if(user.isAdminApprove()){
            return true;
        }
        else {
            return false;
        }
    }

    public void approveAdmin(String adminUserName, int uId){
        if(isAdmin(adminUserName)){
            try {

            }catch (TransactionRequiredException tr){

            }
            usersRepository.approveAdmin(uId);
        }else {
            throw new WrongAccessException("user does not require permission");
        }
    }


}