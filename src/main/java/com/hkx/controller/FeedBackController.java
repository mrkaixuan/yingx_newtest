package com.hkx.controller;

import com.hkx.service.FeedBackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("feedback")
public class FeedBackController {

    @Resource
    private FeedBackService feedBackService;
    /**
     * 分页展示
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping("queryFeedBackByPage")
    public HashMap<String, Object> queryFeedBackByPage(Integer page, Integer rows){
        HashMap<String, Object> map = feedBackService.queryFeedBackByPage(page, rows);
        return map;
    }
}
