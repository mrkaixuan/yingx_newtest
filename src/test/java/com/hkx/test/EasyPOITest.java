package com.hkx.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hkx.dao.UserMapper;
import com.hkx.entity.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class EasyPOITest {
    @Resource
    UserMapper userMapper;
    /**
     * 生成Excel
     * @throws Exception
     */
    @Test
    public void testExport() throws Exception{
        List<User2> users = new ArrayList<User2>();
        users.add(new User2("1", "小黄", 23, new Date()));
        users.add(new User2("2", "小刘", 26, new Date()));
        users.add(new User2("3", "小黑", 24, new Date()));
        users.add(new User2("4", "小张", 18, new Date()));

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),User2.class, users);

        workbook.write(new FileOutputStream(new File("F:/easypoi.xls")));
        workbook.close();
    }
    /**
     * 一对多导出
     * @throws Exception
     */
    @Test
    public void TestEasyPois() throws Exception{

        //创建学生集合
        List<Student> stus = new ArrayList<Student>();
        stus.add(new Student("1", "小黄", 23));
        stus.add(new Student("2", "小刘", 26));
        stus.add(new Student("3", "小黑", 24));
        stus.add(new Student("4", "小张", 18));

        Teacher teacher = new Teacher("1","马老师",stus);


        Teacher teacher1 = new Teacher("1","张超男",stus);
        Teacher teacher2 = new Teacher("2","张坤",stus);



        //创建老师集合
        List<Teacher> teachers = new ArrayList<Teacher>();
        teachers.add(teacher);
        teachers.add(teacher1);
        teachers.add(teacher2);

        //参数：(一级标题，二级标题，表名)，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","计算机","学生"),Teacher.class, teachers);

        workbook.write(new FileOutputStream(new File("F:/easypoi一对多.xls")));

        //释放资源
        workbook.close();
    }
    /**
     * 一对多导入
     * @throws Exception
     */
    @Test
    public void testInput() throws Exception {

        //创建导入对象
        ImportParams params = new ImportParams();
        params.setTitleRows(2); //表格标题行数,默认0
        params.setHeadRows(2);  //表头行数,默认1

        //获取导入数据
        List<Teacher> teachers = ExcelImportUtil.importExcel(new FileInputStream(new File("F:/easypoi一对多.xls")),Teacher.class, params);

        for (Teacher teacher : teachers) {
            System.out.println(teacher);
            for (Student student : teacher.getStudents()) {
                System.out.println(student);
            }
        }
    }
    /**
     * 图片导出
     */
    @Test
    public void exportPicture() throws IOException {
//创建学生集合
        List<Student1> stus = new ArrayList<Student1>();
        stus.add(new Student1("1", "小黄", 23,"E:\\Source_Pic\\11.jpg"));
        stus.add(new Student1("2", "小刘", 26,"E:\\Source_Pic\\22.jpg"));
        stus.add(new Student1("3", "小黑", 24,"E:\\Source_Pic\\33.jpg"));
        stus.add(new Student1("4", "小张", 18,"E:\\Source_Pic\\44.jpg"));

        //参数：(一级标题，二级标题，表名)，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),Student1.class,stus);

        workbook.write(new FileOutputStream(new File("F:/easypoi图片导入.xls")));

        //释放资源
        workbook.close();

    }
    /**
     * 带图片导入
     */
    @Test
    public void testEasyPoiPhotos() {

        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setSaveUrl("F://pic");

        try {
            //导入  参数：对应实体，导入参数
            List<Student1> student1s= ExcelImportUtil.importExcel(new FileInputStream(new File("F://easypoi图片导入.xls")),Student1.class, params);
            for (Student1 student1 : student1s) {
                System.out.println(student1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test2() throws IOException {
        List<User> users = userMapper.selectAll();
        System.out.println(users);
        for (User user : users) {
            user.setPicImg("src/main/webapp/upload/cover/"+user.getPicImg());
        }
        //参数：(一级标题，二级标题，表名)，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","用户表"), User.class,users);

        workbook.write(new FileOutputStream(new File("F://用户信息表.xls")));
        //释放资源
        workbook.close();
    }


}
