package com.movie.api.controller;

import com.movie.api.model.entity.DailyWork;
import com.movie.api.service.DailyWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Daily work API")
@RequestMapping("/api/daily")
public class DailyWorkController {

    @Resource
    private DailyWorkService dailyWorkService;

    @PostMapping("")
    @ApiOperation("Add daily work")
    public void create(@RequestBody DailyWork dailyWork){
        dailyWorkService.save(dailyWork);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete daily work by ID")
    public void delete(@PathVariable String id){
        dailyWorkService.deleteById(id);
    }

    @GetMapping("")
    @ApiOperation("List all")
    public List<DailyWork> findAll(){
        return dailyWorkService.findAll();
    }

}
