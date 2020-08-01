package com.hkx.service;

import com.hkx.entity.Log;

import java.util.HashMap;

public interface LogService {
    void addLog(Log log);
    HashMap<String, Object> queryByPage(Integer page, Integer rows);
}
