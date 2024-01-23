package com.yuno.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuno.model.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long>{
	Roles findByNameRole(String nameRole);
}
