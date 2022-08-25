package com.aiit.service;

import com.aiit.entity.*;

import java.util.List;
import java.util.Map;

public interface UserService {
    public User getUser(String userName);

    /**
     * 获取用户信息
     * @param userName 用户名
     * @param password 密码
     * @return Map<String,Object>用户信息
     */
    public Map<String,Object> getUserInfo(String userName,String password);
    public void updateTrueName(User user);
    public void updateUserImg(User user);
    public void addUser(UserInfo userInfo);
    public void sendMassage(String touser,String subscriptionId);
    public Map<String,Object> getSubscriptionCourse();
    public Map<String,Object> addSubscription(Subscription subscription);
    public Map<String,Object> deleteSubscription(RoleSubscription roleSubscription);
    public List<Subscription> getAllSubscription();
    public List<Subscription> getTodaySubscription(String remindDate);
    public List<SubscriptionInfo> getSubscriptionWithRole(UserSubscription userSubscription);
    public Task roleTaskInfo(Task task);
}
