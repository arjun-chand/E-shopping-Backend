package com.eshoppingbackend.EShopping.Backend.Service;

import com.eshoppingbackend.EShopping.Backend.Entity.Complain;
import com.eshoppingbackend.EShopping.Backend.Repository.ComplaintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplainService {

    @Autowired
    ComplaintRepo complaintRepo;

    public void registerComplain(String complainName, String complainDescription){
        Complain c = new Complain();
        c.setComplaintDescription(complainDescription);
        c.setComplaintName(complainName);
        complaintRepo.save(c);
    }
}
