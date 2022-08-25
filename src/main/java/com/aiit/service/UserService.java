package com.aiit.service;

import com.aiit.entity.*;

import java.util.List;
import java.util.Map;

public interface UserService {
    User getUser(String userName);

    /**
     * 获取用户信息
     * @param userName 用户名
     * @param password 密码
     * @return Map<String,Object>用户信息
     */
    Map<String,Object> getUserInfo(String userName,String password);

    void updateTrueName(User user);

    /**
     * 上传用户头像
     * @param user 用户
     */
    void updateUserImg(User user);
    void addUser(UserInfo userInfo);

    /**
     * 发送定时推送
     * @param touser openid
     * @param subscriptionId 订阅id
     */
    void sendMassage(String touser,String subscriptionId);
    Map<String,Object> getSubscriptionCourse();

    /**
     * 添加订阅
     * @param subscription 订阅信息
     * @return Map<String,Object>
     */
    Map<String,Object> addSubscription(Subscription subscription);

    /**
     * 取消订阅
     * @param roleSubscription 角色订阅信息
     * @return Map<String,Object>
     */
    public Map<String,Object> deleteSubscription(RoleSubscription roleSubscription);

    /**
     * 获取订阅列表
     * @return List<Subscription>订阅信息列表
     */
    List<Subscription> getAllSubscription();

    /**
     * 获取当日订阅
     * @param remindDate 当日时间
     * @return List<Subscription> 订阅信息列表
     */
    List<Subscription> getTodaySubscription(String remindDate);

    /**
     * 获取用户订阅信息
     * @param userSubscription 用户订阅信息
     * @return List<SubscriptionInfo> 订阅信息列表
     */
    List<SubscriptionInfo> getSubscriptionWithRole(UserSubscription userSubscription);

    /**
     * 用户任务信息
     * @param task 任务信息
     * @return 任务信息
     */
    Task roleTaskInfo(Task task);
}
