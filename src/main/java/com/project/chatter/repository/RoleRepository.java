package com.project.chatter.repository;

import com.project.chatter.model.entity.Role;
import com.project.chatter.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByRole(RoleType roleType);
}