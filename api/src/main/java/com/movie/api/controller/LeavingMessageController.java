package com.movie.api.controller;

import com.movie.api.model.entity.LeavingMessage;
import com.movie.api.model.vo.ActiveUserVO;
import com.movie.api.model.vo.LeavingMessageVO;
import com.movie.api.service.LeavingMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Message API")
@RequestMapping("/api/lm")
public class LeavingMessageController {

    @Resource
    private LeavingMessageService leavingMessageService;

    @PostMapping("")
    @ApiOperation(value = "Create message")
    public void save(@RequestBody LeavingMessage leavingMessage) {
        leavingMessageService.save(leavingMessage);
    }

    @PutMapping("")
    @ApiOperation("Reply to message")
    public void reply(@RequestBody LeavingMessage leavingMessage) {
        leavingMessageService.reply(leavingMessage);
    }

    @GetMapping("")
    @ApiOperation("List all cinema messages")
    public List<LeavingMessageVO> list() {
        return leavingMessageService.findAll();
    }

    @GetMapping("/active")
    @ApiOperation("List active commenters")
    public List<ActiveUserVO> findActiveUsers() {
        return leavingMessageService.findActiveUsers();
    }

}
