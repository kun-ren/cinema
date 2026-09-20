package com.movie.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.movie.api.constant.Roles;
import com.movie.api.mapper.RoleMapper;
import com.movie.api.model.entity.Role;
import com.movie.api.service.RoleService;
import com.movie.api.utils.DataTimeUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
@CacheConfig(cacheNames = "role")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Role create(Role role) throws Exception {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.in("wid", role.getWid());
        wrapper.in("value", role.getValue());
        if (roleMapper.selectOne(wrapper) != null) {
            throw new Exception("This worker already has this permission. Please do not add it again");
        }
        role.setId(UUID.randomUUID().toString());
        role.setCreateAt(DataTimeUtil.getNowTimeString());
        roleMapper.insert(role);
        return role;
    }

    @Override
    public void deleteById(String id) throws Exception {
        Role role = roleMapper.selectById(id);
        if (role.getValue().equals(Roles.ROLE_WORKER)) throw new Exception("The basic worker role cannot be removed");
        roleMapper.deleteById(id);
    }

    @Override
    public List<Role> listRolesByWorkerId(String wid) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.in("wid", wid);
        return roleMapper.selectList(wrapper);
    }

    @Override
    public void deleteWorkerAllRoles(String wid) {
        roleMapper.delete(new QueryWrapper<Role>().in("wid", wid));
    }

}
