package com.hkx.controller;


import com.hkx.entity.City;
import com.hkx.entity.Models;
import com.hkx.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("echarts")
public class EchartsController {
    @Resource
    UserService userService;

    @RequestMapping("getUserEcharts")
    public HashMap<String, Object> getUserEcharts(){

        HashMap<String, Object> map = new HashMap<>();

        List<City> man = userService.queryMonth("男");
        List<City> woman = userService.queryMonth("女");

        ArrayList<String> month = new ArrayList<>();
        ArrayList<String> boys = new ArrayList<>();
        ArrayList<String> girls = new ArrayList<>();

        for (int i=1;i<=12;i++){
            month.add(i+"月");
            int num =0;
            for (City city : man) {
                if(city.getName().equals(i+"")){
                    num++;
                    boys.add(city.getValue()+"");
                }
            }
            if (num==0){
                boys.add(0+"");
            }
        }
        for (int i=1;i<=12;i++){
            int num =0;
            for (City city : woman) {
                if(city.getName().equals(i+"")){
                    num++;
                    girls.add(city.getValue()+"");
                }
            }
            if (num==0){
                girls.add(0+"");
            }
        }
        map.put("month", month);
        map.put("boys", boys);
        map.put("girls", girls);
        /*map.put("month", Arrays.asList("1","2","3","4","5","6"));
        map.put("boys", Arrays.asList(0, 200, 100, 100, 100, 200));
        map.put("girls", Arrays.asList(5, 0, 36, 100, 10, 20));*/
        return map;
    }

    @RequestMapping("getEchartsMap")
    public ArrayList<Models> getEchartsMap(){

        List<City> maleLists = userService.queryMessage("男");
        List<City> femaleLists = userService.queryMessage("女");

        ArrayList<Models> ModelsList = new ArrayList<>();
        ModelsList.add(new Models("小男生",maleLists));
        ModelsList.add(new Models("小姑娘",femaleLists));

        return ModelsList;
    }
}
