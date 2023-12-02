package com.eshoppingbackend.EShopping.Backend.Repository;

import com.eshoppingbackend.EShopping.Backend.Entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    public Users findByUserName(String userName);

    @Query(value = "select count(*) from public.users where users.admin_approve = true;", nativeQuery = true)
    public List<Object[]> countOfAdminsAvailable();

    @Transactional
    @Modifying
    @Query(value = "update users set admin_approve = true where users.u_id = :uid", nativeQuery = true)
    public void approveAdmin(@Param("uid") int uid);
}
