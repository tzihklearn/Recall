package com.example.recallbackend.controller;

import com.example.recallbackend.Service.InboxService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.ConfirmParam;
import com.example.recallbackend.pojo.dto.param.FeedbackParam;
import com.example.recallbackend.pojo.dto.param.SubmitVideoParam;
import com.example.recallbackend.pojo.dto.result.InboxDetailsResult;
import com.example.recallbackend.pojo.dto.result.InBoxGetAllResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author tzih
 * &#064;date  2022.09.21
 */
@RestController
@RequestMapping(value = "/parent/inbox")
public class InboxController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private InboxService inboxService;

    @GetMapping("/all")
    public CommonResult<List<InBoxGetAllResult>> getAll(@RequestParam Integer userId, @RequestParam Long time) {
        return inboxService.getAll(userId, time);
    }

    @GetMapping("/details")
    public CommonResult<InboxDetailsResult> getDetails(@RequestParam Integer scheduleBoxId, @RequestParam Integer parentId,
                                                       @RequestParam Integer childId, @RequestParam Long time) {
        return inboxService.getDetails(scheduleBoxId, parentId, childId, time);
    }

    @PostMapping("/feedback")
    public CommonResult<String> feedback(@RequestParam Integer parentId, @RequestParam Integer childId,
                                         @RequestParam Integer scheduleBoxId, @RequestParam Integer length) {
//        Integer parentId, Integer childId, Integer scheduleBoxId, Integer length
//        Map<String, String[]> parameterMap = request.getParameterMap();
//
//        Enumeration<String> parameterNames = request.getParameterNames();
//
//
//        System.out.println(parameterNames.toString());
//        Integer parentId = Integer.valueOf(parameterMap.get("parentId")[0]);
//        Integer childId = Integer.valueOf(parameterMap.get("childId")[0]);
//        Integer scheduleBoxId = Integer.valueOf(parameterMap.get("scheduleBoxId")[0]);
//        Integer length = Integer.valueOf(parameterMap.get("length")[0]);

//        Integer length = 0;


        return inboxService.feedback(parentId, childId, scheduleBoxId, length);
    }

    @PostMapping("/confirm")
    public CommonResult<String> confirm(@Valid @RequestBody ConfirmParam confirmParam) {
        return inboxService.confirm(confirmParam);
    }

    
}
