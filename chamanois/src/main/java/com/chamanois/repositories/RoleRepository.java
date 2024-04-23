package com.chamanois.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chamanois.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByAuthority(String authority);
}
