package com.hkx.test;

import com.hkx.entity.User1;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class POITest {
    @Test
    public void testPoi(){
        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();

        //创建一个工作薄   参数：工作薄名字(sheet1,shet2....)
        //不指定工作簿名   默认按照 sheet0，sheet1 命名
        Sheet sheet = workbook.createSheet("sheet1");

        //创建一行 参数：行下标(下标从0开始)
        for (int i = 1; i <=10; i++) {
            //创建10行
            Row row = sheet.createRow(i-1);
            for (int i1 = 1; i1<=10; i1++) {
                //每行创建10个单元格
                Cell cell = row.createCell(i1-1);
                //为每个单元格设置内容信息
                cell.setCellValue(i+"行"+i1+"列");
            }
        }

        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("F://TestPoi.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试创建表格 到处表格
     * @throws Exception
     */
    @Test
    public void testExports() throws Exception{
        //创建集合
        User1 user1 = new User1("1", "小黑", 12, new Date());
        User1 user2 = new User1("2", "小红", 26, new Date());
        User1 user3 = new User1("3", "小绿", 23, new Date());
        User1 user4 = new User1("4", "小紫", 17, new Date());
        User1 user5 = new User1("5", "小蓝", 31, new Date());
        User1 user6 = new User1("6", "小黄", 18, new Date());
        List<User1> users = Arrays.asList(user1,user2,user3,user4,user5,user6);

        //创建 Excel 工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建工作表   参数：表名（相当于Excel的sheet1,sheet2...）
        HSSFSheet sheet = workbook.createSheet("用户信息1");

        //创建标题行
        HSSFRow row = sheet.createRow(0);
        sheet.setColumnWidth(3, 15*256);

        //设置行高   参数：short类型的值
        row.setHeight((short) 500);
        String[] title={"ID","名字","年龄","生日"};
        //---------------------------------------------------------------------

        //构建字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);    //加粗
        font.setColor(Font.COLOR_RED); //颜色
        font.setFontHeightInPoints((short)10);  //字号
        font.setFontName("黑体");  //字体
        font.setItalic(true);    //斜体
        font.setUnderline(FontFormatting.U_SINGLE);  //下划线

        //创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);     //将字体样式引入
        cellStyle.setAlignment(HorizontalAlignment.CENTER);  //文字居中



        //创建样式对象
        CellStyle cellStyle2 = workbook.createCellStyle();
        //创建日期对象
        DataFormat dataFormat = workbook.createDataFormat();
        //设置日期格式
        cellStyle2.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));



//---------------------------------------------------------------------

        //处理单元格对象
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);    //单元格下标
            cell.setCellValue(title[i]);   //单元格内容​
            cell.setCellStyle(cellStyle);  //标题行使用样式
        }




        //处理数据行
        for (int i = 0; i < users.size(); i++) {
            //遍历一次创建一行
            HSSFRow row2 = sheet.createRow(i+1);
            //每行对应放的数据
            row2.createCell(0).setCellValue(users.get(i).getId());
            row2.createCell(1).setCellValue(users.get(i).getName());
            row2.createCell(2).setCellValue(users.get(i).getAge());
            row2.createCell(3).setCellValue(users.get(i).getBir());

            row2.setHeight((short) 600);
            //设置单元格日期格式
            Cell cell2 = row2.createCell(3);
            cell2.setCellStyle(cellStyle2);    //添加日期样式
            cell2.setCellValue(users.get(i).getBir());   //添加数据
        }

       /* //合并行
        Cell cell7=row.createCell(8);
        CellRangeAddress region1=new CellRangeAddress(0, 5, 6, 6);
        sheet.addMergedRegion(region1);
        cell7.setCellValue("合并行7");

        //合并列
        Cell cell8 = row.createCell(8);
        //要合并的列     参数：行开始，行结束，列开时，列结束
        CellRangeAddress region=new CellRangeAddress(0, 0, 0, 3);
        sheet.addMergedRegion(region);
        cell8.setCellValue("合并列8");*/


        //创建输出流 从内存中写入本地磁盘
        workbook.write(new FileOutputStream(new File("F:/测试PoiProject.xls")));
        //关闭资源
        workbook.close();
    }


    /**
     * 导入XSl文档 并装入实体类中
     */
    @Test
    public void testPoiImport() {

        try {
            //获取要导入的文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("F:/测试PoiProject.xls")));

            //获取工作薄
            HSSFSheet sheet = workbook.getSheet("用户信息1");

            for (int i = 2; i < sheet.getLastRowNum(); i++) {

                User1 student = new User1();

                //获取行
                HSSFRow row = sheet.getRow(i);

                //获取Id
                student.setId(row.getCell(0).getStringCellValue());
                //获取name
                student.setName(row.getCell(1).getStringCellValue());
                //获取age
                double ages = row.getCell(2).getNumericCellValue();
                student.setAge((int) ages);
                //获取生日
                student.setBir(row.getCell(3).getDateCellValue());

                //调用一个插入数据库的方法
                System.out.println(student);
            }

            //关闭资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
