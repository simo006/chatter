package com.project.chatter.service.impl;

import com.project.chatter.model.entity.Role;
import com.project.chatter.model.enums.RoleType;
import com.project.chatter.repository.RoleRepository;
import com.project.chatter.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void populateRoles() {
        if (roleRepository.count() == 0) {
            List<Role> roles = List.of(
                    new Role(RoleType.USER),
                    new Role(RoleType.ADMINISTRATOR)
            );

            roleRepository.saveAll(roles);
        }
    }
}
