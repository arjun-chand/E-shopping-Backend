package com.eshoppingbackend.EShopping.Backend.Repository;

import com.eshoppingbackend.EShopping.Backend.Entity.Orders;
import jakarta.transaction.Transactional;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO orders_order_items (orders_o_id, order_items_p_id) VALUES (:oid, :pid)", nativeQuery = true)
    public void insertProductVsOrder(@Param("oid") int oid, @Param("pid") int pid);

    @Query(value = "select * from orders where users_u_id = :uid",nativeQuery = true)
    public List<Orders> getAllOrdersByUserId(@Param("uid") int uid);

    @Query(value = "select * from orders where users_u_id = :uid and is_delivered = false", nativeQuery = true)
    public List<Orders> getAllNotDeliveredOrdersByUserId(@Param("uid") int uid);

    @Query(value = "select * from orders where users_u_id = :uid and is_delivered = true", nativeQuery = true)
    public List<Orders> getAllDeliveredOrdersByUserId(@Param("uid") int uid);


    @Modifying
    @Transactional
    @Query(value = "delete from orders where o_id = :oid and users_u_id = :uid",nativeQuery = true)
    public void deleteOrderByOrderIdAndUserId(int uid, int oid);


    @Query(value = "select * from orders where o_id = :oid and users_u_id = :uid",nativeQuery = true)
    public Orders getOrderByOrderIdAndUserId(int uid, int oid);

    @Modifying
    @Transactional
    @Query(value = "delete from orders_order_items where orders_o_id = :oid and order_items_p_id = :pid",nativeQuery = true)
    public void deleteOrderVsProduct(int oid, int pid);
}
