package com.eshoppingbackend.EShopping.Backend.Controller;

import com.eshoppingbackend.EShopping.Backend.DTO.RequestDTO.AddProductDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.GeneralMessageDTO;
import com.eshoppingbackend.EShopping.Backend.DTO.ResponseDTO.UnAuthorised;
import com.eshoppingbackend.EShopping.Backend.Entity.Product;
import com.eshoppingbackend.EShopping.Backend.Exception.UserNotFoundException;
import com.eshoppingbackend.EShopping.Backend.Exception.WrongAccessException;
import com.eshoppingbackend.EShopping.Backend.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Admin Service End-points", description = "this controller contains all the end points that Admin can Use")
@RestController
@RequestMapping("/e-shopping/admin")
public class Admin {
    @Autowired
    ProductService productService;

    @Operation(
            summary = "Add product to product table",
            description = "This endpoint can let admin level user to add product into the database",
            tags = { "Admin Service End-points" })
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "product successfully got added into the database", content = { @Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Admin Username does not exist", content = { @Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "", content = {@Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "application/json")}
            )
    })
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
    public ResponseEntity getAllProducts(@Parameter(description = "User name is required such that authorization can be done") @RequestParam String userName){
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
