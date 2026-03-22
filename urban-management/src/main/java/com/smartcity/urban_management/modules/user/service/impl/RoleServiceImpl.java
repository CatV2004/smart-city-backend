package com.smartcity.urban_management.modules.user.service.impl;

import com.smartcity.urban_management.modules.user.entity.Role;
import com.smartcity.urban_management.modules.user.repository.RoleRepository;
import com.smartcity.urban_management.modules.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
