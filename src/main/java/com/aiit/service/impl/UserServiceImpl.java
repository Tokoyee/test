package com.aiit.service.impl;

import com.aiit.entity.*;
import com.aiit.mapper.CourseMapper;
import com.aiit.mapper.RoleMapper;
import com.aiit.mapper.UserMapper;
import com.aiit.service.UserService;
import com.aiit.util.JsonUtil;
import com.aiit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CourseMapper classroomMapper;
    @Autowired
    RoleMapper roleMapper;
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
                    userImg = "http://aiitbeidou.cn:8080/userImg/"+img;
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
    public void updateImgName(User user){
        userMapper.updateImgName(user);
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
}
