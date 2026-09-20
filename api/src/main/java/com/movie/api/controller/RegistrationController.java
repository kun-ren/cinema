package com.movie.api.controller;

import com.movie.api.model.entity.Registration;
import com.movie.api.service.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Activity API")
@RequestMapping("/api/registration")
public class RegistrationController {

    @Resource
    private RegistrationService registrationService;

    @PostMapping("")
    @ApiOperation("Register for activity")
    public void create(@RequestBody Registration registration) throws Exception {
        registrationService.create(registration);
    }

    @GetMapping("")
    @ApiOperation("List all registrations")
    public List<Registration> create() {
        return registrationService.findAll();
    }

}
