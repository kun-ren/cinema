package com.movie.api.controller;

import com.movie.api.constant.Roles;
import com.movie.api.model.dto.LoginDto;
import com.movie.api.model.entity.Role;
import com.movie.api.model.entity.Worker;
import com.movie.api.service.RoleService;
import com.movie.api.service.WorkerService;
import com.movie.api.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "Worker API")
@RequestMapping("/api/worker")
public class WorkerController {

    @Resource
    private WorkerService workerService;

    @Resource
    private RoleService roleService;

    @PostMapping("/login")
    @ApiOperation("Worker login")
    public Map<String, Object> login(@RequestBody LoginDto dto) throws Exception {
        Worker worker = workerService.login(dto);
        Map<String, Object> map = new HashMap<>();
        //Use the remember-me token lifetime when selected
        long exp = dto.isRemember() ? JwtTokenUtil.REMEMBER_EXPIRATION_TIME : JwtTokenUtil.EXPIRATION_TIME;

        //List roles for the logged-in support worker
        List<String> roles = new ArrayList<>();
        //Assign the basic support role
        roles.add(Roles.ROLE_WORKER);

        for (Role role : roleService.listRolesByWorkerId(worker.getId())){
            roles.add(role.getValue());
        }
        map.put("token", JwtTokenUtil.createToken(dto.getUsername(), roles, exp));
        map.put("worker", worker);
        return map;
    }

    @PostMapping("")
    @ApiOperation("Add worker")
    public Worker create(@RequestBody Worker worker) throws Exception {
        return workerService.create(worker);
    }

    @GetMapping("")
    @ApiOperation("List all workers")
    public List<Worker> list(){
        return workerService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Find worker by ID")
    public Worker findById(@PathVariable String id){
        return workerService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete worker by ID")
    public void deleteById(@PathVariable String id){
        workerService.deleteById(id);
    }

    @PutMapping("")
    @ApiOperation("Update worker profile")
    public void update(@RequestBody Worker worker) throws Exception {
        workerService.update(worker);
    }

}
