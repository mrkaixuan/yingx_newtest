package com.hkx.service;

import com.hkx.dao.CategoryMapper;
import com.hkx.entity.Category;
import com.hkx.entity.CategoryDTO;
import com.hkx.entity.CategoryExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    //查询一级类别
    public HashMap<String, Object> queryOneCategoryByPage(Integer page, Integer rows) {


        /**
         * 传递数据
         * page 当前页
         * rows 每页展示数据
         *
         * 返回数据
         * total 总页数
         * records 总条数
         * page 当前页
         * rows 数据
         */
        HashMap<String, Object> map = new HashMap<>();
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo("1");
        //总条数
        int records = categoryMapper.selectCountByExample(example);
        //总页数 totals = 总条数 取模/每页展示数据
        Integer totals = records%rows==0?records/rows:records/rows+1;
        //当前页page
        //数据 rows 分页 使用RowBounds 从第几条开始,需要几条数据
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, new RowBounds((page - 1) * rows, rows));

        map.put("totals",totals);//总页数
        map.put("records",records);//总条数
        map.put("page",page);//当前页
        map.put("rows",categories);//分页数据

        return map;

    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    //查询二级类别
    public HashMap<String, Object> querySecondCategoryByPage(Integer page, Integer rows,String parentId) {


        /**
         * 传递数据
         * page 当前页
         * rows 每页展示数据
         *
         * 返回数据
         * total 总页数
         * records 总条数
         * page 当前页
         * rows 数据
         */
        HashMap<String, Object> map = new HashMap<>();
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo("2").andParentIdEqualTo(parentId);
        //总条数
        int records = categoryMapper.selectCountByExample(example);

        //总页数 totals = 总条数/每页展示数据
        Integer totals = records%rows==0?records/rows:records/rows+1;

        //当前页page
        //数据 rows 分页 使用RowBounds 从第几条开始,需要几条数据
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, new RowBounds((page - 1) * rows, rows));

        map.put("totals",totals);
        map.put("records",records);
        map.put("page",page);
        map.put("rows",categories);

        return map;

    }

    @Override
    public HashMap<String, Object> deleteCategory(Category category) {
        HashMap<String, Object> map = new HashMap<>();
        //判断是一级类别还是二级类别
        Category cates = categoryMapper.selectOne(category);
        if(cates.getLevels().equals("1")){
            //一级类别   是否有对应的二级类别
            //有二级类别  不能删除  提示信息
            //没有二级类别   正常删除
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getId());
            int count = categoryMapper.selectCountByExample(example);
            if(count==0){
                //一级类别下没有二级类别,直接删除
                CategoryExample examples = new CategoryExample();
                examples.createCriteria().andIdEqualTo(category.getId());
                categoryMapper.deleteByExample(examples);

                map.put("status","200");
                map.put("message","删除成功");
            }else{
                //一级类别下有二级类别,不能删除
                map.put("status","400");
                map.put("message","删除失败，该类别下有子类别");
            }
        }else{
            //二级类别   二级类别下是否有视频
            //有  不能删除  提示信息
            //没有  正常删除
            CategoryExample examples = new CategoryExample();
            examples.createCriteria().andIdEqualTo(category.getId());
            categoryMapper.deleteByExample(examples);
            map.put("status","200");
            map.put("message","删除成功");
        }

        return map;
    }

    @Override
    public List<CategoryDTO[]> queryAllCategory() {
        List<CategoryDTO[]> categoryDTOS = categoryMapper.queryAllCategory();
        return  categoryDTOS;
    }

}
