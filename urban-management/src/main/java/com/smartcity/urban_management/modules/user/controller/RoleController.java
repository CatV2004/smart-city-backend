package com.smartcity.urban_management.modules.user.controller;

import com.smartcity.urban_management.modules.user.entity.Role;
import com.smartcity.urban_management.modules.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping()
    public List<Role> getCurrentUser() {
        return roleService.getRoles();
    }

}
