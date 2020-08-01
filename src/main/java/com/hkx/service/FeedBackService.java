package com.hkx.service;

import java.util.HashMap;

public interface FeedBackService {
    HashMap<String, Object> queryFeedBackByPage(Integer page, Integer rows);
}

