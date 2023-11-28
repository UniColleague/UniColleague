package com.swe444.demo.dao;


import com.swe444.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, String> {
}
