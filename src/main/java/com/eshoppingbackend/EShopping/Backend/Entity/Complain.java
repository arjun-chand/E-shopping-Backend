package com.eshoppingbackend.EShopping.Backend.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("Complain")
public class Complain {
    @Id
    String id;

    String complaintName;
    String complaintDescription;
}
