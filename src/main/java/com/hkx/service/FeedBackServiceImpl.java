package com.hkx.service;

import com.hkx.dao.FeedBackMapper;
import com.hkx.entity.FeedBack;
import com.hkx.entity.FeedBackExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService {

    @Resource
    private FeedBackMapper feedBackMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> queryFeedBackByPage(Integer page, Integer rows) {

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

        //查询所有的反馈
        FeedBackExample example = new FeedBackExample();
        //总条数
        int records = feedBackMapper.selectCountByExample(example);

        //总页数 totals = 总条数/每页展示数据
        Integer totals = records%rows==0?records/rows:records/rows+1;

        //当前页page
        //数据 rows 分页 使用RowBounds 从第几条开始,需要几条数据
        List<FeedBack> feedBacks = feedBackMapper.selectByRowBounds(new FeedBack(), new RowBounds((page - 1) * rows, rows));

        map.put("totals",totals);
        map.put("records",records);
        map.put("page",page);
        map.put("rows",feedBacks);
        return map;


    }
}

