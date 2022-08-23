package com.aiit.service;

import com.aiit.entity.*;

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
    public Map<String,Object> deleteSubscription(RoleSubscription roleSubscription);
    public List<Subscription> getAllSubscription();
    public List<Subscription> getTodaySubscription(String remindDate);
    public List<SubscriptionInfo> getSubscriptionWithRole(UserSubscription userSubscription);
    public Task roleTaskInfo(Task task);
}
