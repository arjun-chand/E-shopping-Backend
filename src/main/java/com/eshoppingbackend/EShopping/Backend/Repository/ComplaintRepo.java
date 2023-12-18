package com.eshoppingbackend.EShopping.Backend.Repository;

import com.eshoppingbackend.EShopping.Backend.Entity.Complain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepo extends MongoRepository<Complain, String> {
}
