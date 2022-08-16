package com.aiit.service;

import com.aiit.entity.User;
import com.aiit.entity.UserInfo;

import java.util.Map;

public interface UserService {
    public User getUser(String userName);
    public Map<String,Object> getUserInfo(String userName,String password);
    public void updateTrueName(User user);
    public void updateImgName(User user);
    public void addUser(UserInfo userInfo);
}
