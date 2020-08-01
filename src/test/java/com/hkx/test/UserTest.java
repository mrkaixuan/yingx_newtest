package com.hkx.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.hkx.dao.UserMapper;
import com.hkx.entity.City;
import com.hkx.entity.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Resource
    UserMapper userMapper;
    @Test
    public void exportPicture() throws IOException {
        //获得User集合
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            user.setPicImg("src/main/webapp/upload/cover/"+user.getPicImg());
        }
        System.out.println(users);


        //参数：(一级标题，二级标题，表名)，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户","用户表"), User.class,users);

        workbook.write(new FileOutputStream(new File("F://用户信息.xls")));

        //释放资源
        workbook.close();


    /*    List<Student1> stus = new ArrayList<Student1>();
        stus.add(new Student1("1", "小黄", 23,"E:\\Source_Pic\\11.jpg"));
        stus.add(new Student1("2", "小刘", 26,"E:\\Source_Pic\\22.jpg"));
        stus.add(new Student1("3", "小黑", 24,"E:\\Source_Pic\\33.jpg"));
        stus.add(new Student1("4", "小张", 18,"E:\\Source_Pic\\44.jpg"));

        //参数：(一级标题，二级标题，表名)，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),Student1.class,stus);

        workbook.write(new FileOutputStream(new File("F:/easypoi图片导入.xls")));

        //释放资源
        workbook.close();*/

    }

    @Test
    public void queryMessage() throws IOException {
        List<City> list = userMapper.queryMessage("男");
        for (City city : list) {
            System.out.println(list);
        }
    }

    @Test
    /**
     * 通过Goeasy动态的发送Json数据,从而动态展示Echars数据
     */
    public void queryMonthMessage(){
        List<City> man = userMapper.queryMonth("男");
        List<City> woman = userMapper.queryMonth("女");
        System.out.println("男生信息"+man);
        System.out.println("女生信息"+woman);


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
        System.out.println(month);
        System.out.println(boys);
        System.out.println(girls);





//        map.put("month", Arrays.asList("1","2","3","4","5","6"));
//        map.put("boys", Arrays.asList(50, 200, 100, 100, 100, 200));
//        map.put("girls", Arrays.asList(5, 20, 36, 100, 10, 20));

    }

}
