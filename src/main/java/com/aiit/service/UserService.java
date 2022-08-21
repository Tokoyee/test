package com.aiit.service;

import com.aiit.entity.Subscription;
import com.aiit.entity.User;
import com.aiit.entity.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {
    public User getUser(String userName);
    public Map<String,Object> getUserInfo(String userName,String password);
    public void updateTrueName(User user);
    public void updateImgName(User user);
    public void addUser(UserInfo userInfo);
    public void sendMassage(String touser,String subscriptionId);
    public Map<String,Object> getSubscriptionCourse();
    public Map<String,Object> addSubscription(Subscription subscription);
    public Map<String,Object> deleteSubscription(String subscriptionId);
    public List<Subscription> getAllSubscription();
    public List<Subscription> getTodaySubscription(String remindDate);
}
