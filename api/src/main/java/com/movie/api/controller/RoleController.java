package com.movie.api.controller;

import com.movie.api.constant.Roles;
import com.movie.api.model.entity.Role;
import com.movie.api.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Manage worker permissions
 * All endpoints require administrator permissions
 */
@RestController
@Api(tags = "Role API")
@RequestMapping("/api/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/system")
    @ApiOperation("List available system roles")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String[] listSystemRoles() {
        return Roles.roles;
    }

    @PostMapping("")
    @ApiOperation("Add permission")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Role create(@RequestBody Role role) throws Exception {
        return roleService.create(role);
    }

    @GetMapping("")
    @ApiOperation("List worker permissions")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Role> listByWorkerId(String wid) {
        return roleService.listRolesByWorkerId(wid);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Remove permission")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void delete(@PathVariable String id) throws Exception {
        roleService.deleteById(id);
    }


}
