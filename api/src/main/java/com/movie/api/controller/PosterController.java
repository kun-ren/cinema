package com.movie.api.controller;

import com.movie.api.model.entity.Poster;
import com.movie.api.service.PosterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Homepage poster API")
@RequestMapping("/api/poster")
public class PosterController {

    @Resource
    private PosterService posterService;

    @PostMapping("")
    @ApiOperation("Add homepage poster")
    public void save(@RequestBody Poster poster) {
        posterService.save(poster);
    }

    @PutMapping("")
    @ApiOperation("Update poster")
    public void update(@RequestBody Poster poster) {
        posterService.update(poster);
    }

    @GetMapping("")
    @ApiOperation("List all posters")
    public List<Poster> list(String status) {
        if (status != null) {
            return posterService.findByStatus(Boolean.parseBoolean(status));
        }
        return posterService.findAll();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(("Delete poster"))
    public void delete(@PathVariable String id) {
        posterService.deleteById(id);
    }

    @DeleteMapping("")
    @ApiOperation(("Delete all posters"))
    public void deleteAll() {
        posterService.deleteAll();
    }

}
