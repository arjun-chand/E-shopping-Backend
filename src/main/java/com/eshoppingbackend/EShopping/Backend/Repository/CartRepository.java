package com.eshoppingbackend.EShopping.Backend.Repository;

import com.eshoppingbackend.EShopping.Backend.Entity.Cart;
import com.eshoppingbackend.EShopping.Backend.Entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Modifying
    @Transactional
    @Query(value = "update cart set total_items = :totalItems where cart_id = :cid",nativeQuery = true)
    public void updateTotalItems(@Param("cid") int cid, @Param("totalItems") int totalItems);

    @Transactional
    @Modifying
    @Query(value = "UPDATE cart SET total_price = :totalPrice WHERE cart_id = :cartId", nativeQuery = true)
    public void updateTotalPrice(@Param("cartId") int cartId, @Param("totalPrice") int totalPrice);
    @Query(value = "select * from cart where users_u_id = :uid",nativeQuery = true)
    public Cart getCartByUserId(@Param("uid") int uid);


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO cart_products (cart_cart_id, products_p_id) VALUES (:cartId, :productId)", nativeQuery = true)
    public void insertProductInCart(@Param("cartId") int cartId, @Param("productId") int productId);


    @Query(value = "select products_p_id from cart_products where cart_cart_id = :cid",nativeQuery = true)
    public List<Object[]> getAllProductsByCartId(@Param("cid") int cid);


    @Modifying
    @Transactional
    @Query(value = "delete from cart_products where cart_cart_id = :cid and products_p_id = :pid",nativeQuery = true)
    public void removeProductFromUserCart( @Param("cid") int cid, @Param("pid") int pid);
}
