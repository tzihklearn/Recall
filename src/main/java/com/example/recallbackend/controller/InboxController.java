package com.example.recallbackend.controller;

import com.example.recallbackend.Service.InboxService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.result.InboxDetailsResult;
import com.example.recallbackend.pojo.dto.result.InBoxGetAllResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@RestController
@RequestMapping(value = "/parent/inbox", method = RequestMethod.GET)
public class InboxController {

    @Resource
    private InboxService inboxService;

    @GetMapping("/all")
    public CommonResult<List<InBoxGetAllResult>> getAll(@NotNull Integer userId, @NotNull Long time) {
        return inboxService.getAll(userId, time);
    }

    @GetMapping("/details")
    public CommonResult<InboxDetailsResult> getDetails(@NotNull Integer scheduleBoxId, @NotNull Integer parentId,
                                                       @NotNull Integer childId, @NotNull Long time) {
        return inboxService.getDetails(scheduleBoxId, parentId, childId, time);
    }

    
}
