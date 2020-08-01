package com.hkx.service;

import com.hkx.dao.LogMapper;
import com.hkx.entity.Log;
import com.hkx.entity.LogExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Resource
    private LogMapper logMapper;

    @Override
    public void addLog(Log log) {
        logMapper.insert(log);
    }
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
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
        LogExample example = new LogExample();
        //总条数
        int records = logMapper.selectCountByExample(example);
        //总页数 totals = 总条数/每页展示数据
        Integer totals = records%rows==0?records/rows:records/rows+1;
        //当前页page
        //数据 rows 分页 使用RowBounds 从第几条开始,需要几条数据

        example.setOrderByClause("time DESC");
        List<Log> logs = logMapper.selectByExampleAndRowBounds(example, new RowBounds((page - 1) * rows, rows));

        //List<Log> logs = logMapper.selectByRowBounds(new Log(), new RowBounds((page - 1) * rows, rows));

        map.put("totals",totals);
        map.put("records",records);
        map.put("page",page);
        map.put("rows",logs);

        return map;
    }
}
