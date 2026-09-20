package com.movie.api.controller;

import com.movie.api.model.entity.WorkerEvaluate;
import com.movie.api.service.WorkerEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Support review API")
@RequestMapping("/api/we")
public class WorkerEvaluateController {

    @Resource
    private WorkerEvaluateService workerEvaluateService;

    @PostMapping("")
    @ApiOperation(value = "Save support review")
    public void save(@RequestBody WorkerEvaluate workerEvaluate) {
        workerEvaluateService.save(workerEvaluate);
    }

    @GetMapping("")
    @ApiOperation(value = "View support reviews")
    public List<WorkerEvaluate> save(String id) {
        return workerEvaluateService.findByWorkerId(id);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete support review")
    public void deleteById(@PathVariable String id) {
        workerEvaluateService.deleteById(id);
    }

}
