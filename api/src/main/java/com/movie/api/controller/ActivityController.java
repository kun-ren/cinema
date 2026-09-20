package com.movie.api.controller;

import com.movie.api.model.entity.Activity;
import com.movie.api.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Activity API")
@RequestMapping("/api/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @PostMapping("")
    @ApiOperation("Create activity")
    public void create(@RequestBody Activity activity) {
        activityService.create(activity);
    }

    @GetMapping("")
    @ApiOperation("List all activities")
    public List<Activity> findAll() {
        return activityService.findAll();
    }

    @GetMapping("{id}")
    @ApiOperation("Find activity by ID")
    public Activity findById(@PathVariable String id) {
        return activityService.findById(id);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete activity")
    public void delete(@PathVariable String id) {
        activityService.deleteById(id);
    }

}
