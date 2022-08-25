package com.aiit.service.impl;

import com.aiit.entity.*;
import com.aiit.mapper.CourseMapper;
import com.aiit.mapper.RoleMapper;
import com.aiit.mapper.UserMapper;
import com.aiit.service.UserService;
import com.aiit.util.JsonUtil;
import com.aiit.util.JwtUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CourseMapper classroomMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    CourseMapper courseMapper;
    /*
    获取用户信息
     */
    public Map<String,Object> getUserInfo(String userName,String password){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<UserInfo> userInfos = userMapper.getUserInfo(userName);
        if (userInfos.size() != 0){
            UserInfo user = userInfos.get(0);
            String pwd = user.getPassword();
            String md5pwd = DigestUtils.md5DigestAsHex(password.getBytes());
            if (pwd.equals(md5pwd)){
                String trueName = null,userImg = null,token = null,phoneNumber = null,userId = null;
                List<String> roleList = new ArrayList<String>();
                for(int i = 0;i<userInfos.size();i++){
                    String roleName = userInfos.get(i).getRoleName();
                    if (roleName.equals("院长")){
                        roleList.add("老师");
                        roleList.add("学院督导");
                        roleList.add("学院教务管理人员");
                    }else {
                        roleList.add(roleName);
                    }
                    trueName = user.getTrueName();
                    String img = user.getImgUrl();
                    userImg = img;
                    token = JwtUtil.sign(user);
                    phoneNumber = user.getPhoneNumber();
                    userId = user.getUserId();
                }
                dataMap.put("phoneNumber",phoneNumber);
                List<Object> resList = new ArrayList<Object>();
                for(int i = 0;i<roleList.size();i++){
                    Map<String,Object> infoMap = new HashMap<String,Object>();
                    String roleName = roleList.get(i);
                    String roleId = roleMapper.getRoleId(roleName).get(0);
                    infoMap.put("roleId",roleId);
                    infoMap.put("roleName",roleName);
                    resList.add(infoMap);
                }
                List<Task> tasks = userMapper.getTaskList(userName);
                dataMap.put("taskList",tasks);
                dataMap.put("roleList",roleList);
                dataMap.put("token",token);
                dataMap.put("roleList",resList);
                dataMap.put("userImg",userImg);
                dataMap.put("trueName",trueName);
                dataMap.put("userId",userId);
                return JsonUtil.success(dataMap);
            }
        }
        return JsonUtil.error(dataMap);
    }
    /*
    通过用户名查找用户，一般用于上传头像等明确了用户已经登陆过的时候
     */
    public User getUser(String userName){
        return userMapper.getUser(userName);
    }
    /*
    更新用户真实姓名
     */
    public void updateTrueName(User user){
        userMapper.updateTrueName(user);
    }
    /*
    更新用户头像
     */
    public void updateUserImg(User user){
        userMapper.updateImgUrl(user);
    }
    /*
    添加用户
     */
    public void addUser(UserInfo userInfo){
        List<User> users = userMapper.getAllUser();
        User user = users.get(users.size()-1);
        Long userId = Long.parseLong(user.getUserId()) + 1;
        String newId = userId.toString();
        User newUser = new User();
        newUser.setUserId(newId);
        newUser.setUserName(userInfo.getUserName());
        newUser.setTrueName(userInfo.getTrueName());
        newUser.setPassword(userInfo.getPassword());
        newUser.setImgUrl(userInfo.getImgUrl());
        Role role = new Role();
        role.setRoleId(newId);
        role.setRoleName(userInfo.getRoleName());
        UserRole userRole = new UserRole();
        userRole.setUserId(newId);
        userRole.setRoleId(newId);
        Author author = new Author();
        author.setAuthorId(newId);
        RoleAuthor roleAuthor = new RoleAuthor();
        roleAuthor.setRoleId(newId);
        roleAuthor.setAuthorId(newId);
        userMapper.addUser(newUser);
        userMapper.addRole(role);
        userMapper.addUserRole(userRole);
        userMapper.addAuthor(author);
        userMapper.addRoleAuthor(roleAuthor);
    }
    public void sendMassage(String touser,String subscriptionId){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxd6a69cf9d408a010&secret=2ace9c7abb9119357fb54b2cc60d4c91";
        // 请求头设置,x-www-form-urlencoded格式的数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //第一次请求，获取accessToken
        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        // 发送post请求，并打印结果，以String类型接收响应结果JSON字符串
        String result = restTemplate.postForObject(url, request, String.class);
        JSONObject dataInfo = JSON.parseObject(result);
        String access_token = dataInfo.getString("access_token");

        //第二次请求订阅消息
        String url2 = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+access_token;
        //提交参数设置
        Message message = new Message();
        message.setTouser(touser);
        message.setTemplate_id("ybmmiRLNhvrJNZm0oEsjgI7CT4YEI1dqXbT5PGsIr2U");
        message.setPage("pages/login/login");
        message.setMiniprogram_state("trial");
        message.setLang("zh_CN");
        HashMap<String,Object> paramData = new HashMap<String,Object>();
        HashMap<String,String> date1Map = new HashMap<String,String>();
        HashMap<String,String> thing4Map = new HashMap<String,String>();
        HashMap<String,String> thing6Map = new HashMap<String,String>();
        HashMap<String,String> thing13Map = new HashMap<String,String>();
        HashMap<String,String> thing17Map = new HashMap<String,String>();
        SubscriptionInfo subscriptionInfo = userMapper.getSubscriptionInfo(subscriptionId);
        String date1 = subscriptionInfo.getDate() + " " + subscriptionInfo.getTime();
        String thing4 = subscriptionInfo.getSection();
        String thing6 = subscriptionInfo.getCourseName();
        String thing13 = subscriptionInfo.getTrueName();
        String thing17 = subscriptionInfo.getClassName();
        date1Map.put("value",date1);
        thing4Map.put("value",thing4);
        thing6Map.put("value",thing6);
        thing13Map.put("value",thing13);
        thing17Map.put("value",thing17);
        paramData.put("date1",date1Map);
        paramData.put("thing4",thing4Map);
        paramData.put("thing6",thing6Map);
        paramData.put("thing13",thing13Map);
        paramData.put("thing17",thing17Map);
        message.setData(paramData);
        log.info(String.valueOf(message));
        // 组装请求体
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url2,message,String.class);
        log.info(responseEntity.getBody());
//        JSONObject resultMsg = JSON.parseObject(responseEntity.getBody());
    }
    public Map<String,Object> getSubscriptionCourse(){
        Map<String,Object> dataMap = new HashMap<String,Object>();

        return dataMap;
    }
    public Map<String,Object> addSubscription(Subscription subscription){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        userMapper.addSubscription(subscription);
        return dataMap;
    }
    public Map<String,Object> deleteSubscription(RoleSubscription roleSubscription){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        userMapper.deleteSubscription(roleSubscription);
        return dataMap;
    }
    public List<Subscription> getAllSubscription(){
        return userMapper.getAllSubscription();
    }
    public List<Subscription> getTodaySubscription(String remindDate){
        return userMapper.getTodaySubscription(remindDate);
    }
    public List<SubscriptionInfo> getSubscriptionWithRole(UserSubscription userSubscription){
        List<SubscriptionInfo> resultList = new ArrayList<SubscriptionInfo>();
        List<SubscriptionInfo> dataList = userMapper.getSubscriptionWithRole(userSubscription);
        for(int i = 0;i<dataList.size();i++){
            SubscriptionInfo subscriptionInfo = dataList.get(i);
            subscriptionInfo.setSubscription(true);
            resultList.add(subscriptionInfo);
        }
        return resultList;
    }
    public Task roleTaskInfo(Task task){
        return userMapper.getTaskInfo(task);
    }
}
