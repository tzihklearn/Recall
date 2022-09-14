package com.example.recallbackend.controller;

import com.example.recallbackend.pojo.CommonResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author tzih
 * @date 2022.09.13
 */
@RestController
@RequestMapping(value = "/relation", method = RequestMethod.POST)
public class RelationController {

    @GetMapping("/getQRCode")
    public CommonResult<String> getQRCode() {
        return null;
    }

    @PostMapping("/set")
    public CommonResult<String> setRelation() {
        return null;
    }

}
