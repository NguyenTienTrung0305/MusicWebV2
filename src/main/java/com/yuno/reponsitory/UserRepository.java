package com.yuno.reponsitory;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yuno.model.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	Users findByEmail(String email);
	
	// lay ra tat ca user co role la user va ton tai keyword trong name hoac email
	// pageable: so luong ban ghi
	// su dung ten bien trong mysql = ten bien trong object
//	@Query("select o from Users o where "
//					+ "(:keyword is null or :keyword = '' "
//					+ "or o.fullName like %:keyword% " 
//					+ "or o.email like %:keyword%) "
//					+ "and lower(o.roles.name) = 'user' ")
//	Page<Users> findAll(@Param("keyword") String keyword , Pageable pageable);
}
