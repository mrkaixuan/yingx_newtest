package com.hkx.service;

import com.hkx.entity.Admin;

import java.util.HashMap;

public interface AdminService {
    HashMap<String,String> login(Admin admin, String enCode);
}
