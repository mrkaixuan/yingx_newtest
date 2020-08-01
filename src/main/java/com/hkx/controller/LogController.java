package com.hkx.controller;

import com.hkx.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("log")
public class LogController {
    @Resource
    LogService logService;

    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String, Object> queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = logService.queryByPage(page, rows);
        return map;
    }

}
