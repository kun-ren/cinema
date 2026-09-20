package com.movie.api.controller;

import com.movie.api.model.entity.Arrangement;
import com.movie.api.model.vo.ArrangementVO;
import com.movie.api.service.ArrangementService;
import com.movie.api.service.FilmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "Screening API")
@RequestMapping("/api/arrangement")
public class ArrangementController {

    @Resource
    private ArrangementService arrangementService;

    @Resource
    private FilmService filmService;

    @PostMapping("")
    @ApiOperation("Create screening")
    public void save(@RequestBody Arrangement arrangement) {
        arrangementService.save(arrangement);
    }

    @PutMapping("")
    @ApiOperation("Update screening details")
    public Arrangement update(@RequestBody Arrangement arrangement) {
        return arrangementService.Update(arrangement);
    }

    @DeleteMapping("")
    @ApiOperation("Delete screening by ID")
    public void delete(@RequestParam String id) {
        arrangementService.deleteById(id);
    }

    @GetMapping("")
    @ApiOperation("List screenings")
    public List<Arrangement> list() {
        return arrangementService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Find screening")
    public Map<String, Object> findById(@PathVariable String id) {
        HashMap<String, Object> map = new HashMap<>();
        Arrangement arrangement = arrangementService.findById(id);
        map.put("film", filmService.findById(arrangement.getFid()));
        map.put("arrangement", arrangement);
        return map;
    }

    @GetMapping("/getSeats")
    @ApiOperation("Get booked seats")
    public List<Integer> getSeats(String id) {
        return arrangementService.getSeatsHaveSelected(id);
    }

    @GetMapping("/film/{fid}")
    @ApiOperation("List screenings for a film")
    public ArrangementVO findByFilmId(@PathVariable String fid) {
        return arrangementService.findByFilmId(fid);
    }

}
