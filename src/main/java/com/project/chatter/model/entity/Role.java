package com.project.chatter.model.entity;

import com.project.chatter.model.enums.RoleType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public Role() {
    }

    public Role(RoleType role) {
        this.role = role;
    }

    public RoleType getRole() {
        return role;
    }

    public Role setRole(RoleType name) {
        this.role = name;
        return this;
    }
}
