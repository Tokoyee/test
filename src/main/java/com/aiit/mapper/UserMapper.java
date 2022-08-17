package com.aiit.mapper;

import com.aiit.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT u.userName,u.userId,u.password,u.trueName,u.imgUrl,u.phoneNumber,r.roleName FROM user u,role r,user_role ur WHERE u.userId = ur.userId AND r.roleId = ur.roleId AND userName = #{userName}")
    public List<UserInfo> getUserInfo(String userName);
    @Select("select * from user where userName = #{userName}")
    public User getUser(String userName);
    @Select("select * from user")
    public List<User> getAllUser();
    @Update("update user set trueName = #{trueName} where userName = #{userName}")
    public void updateTrueName(User user);
    @Update("update user set imgName = #{imgName} where userName = #{userName}")
    public void updateImgName(User user);
    @Update("update user set phoneNumber = #{phoneNumber} where userName = #{userName}")
    public void updatePhoneNumber(User user);
    @Insert("insert into user(`userId`,`userName`,`trueName`,`password`,`imgUrl`) values(#{userId},#{userName},#{trueName},#{password},#{imgName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addUser(User user);
    @Insert("insert into role(`roleId`,`roleName`) values(#{roleId},#{roleName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addRole(Role role);
    @Insert("insert into user_role(`userId`,`roleId`) values(#{userId},#{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addUserRole(UserRole userRole);
    @Insert("insert into author(`authorId`,`authorDescribe`) values(#{authorId},#{authorDescribe})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addAuthor(Author author);
    @Insert("insert into role_author(`roleId`,`authorId`) values(#{roleId},#{authorId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addRoleAuthor(RoleAuthor roleAuthor);
    @Select("select trueName from user where userName = #{userName}")
    public List<String> getUserTrueName(String userName);
    @Select("select instituteType from user where userName = #{userName}")
    public List<String> getUserInstituteType(String userName);
    @Select("select userId from user where userName = #{userName}")
    public List<String> getUserId(String userName);
    @Select("select taskId,taskDescribe,taskStatus,taskNum from task where userName = #{userName}")
    public List<Task> getTaskList(String userName);
    @Update("UPDATE task SET taskNum = #{taskNum},taskStatus = #{taskStatus} WHERE userName = #{userName} AND taskDescribe = #{taskDescribe}")
    public void solveOnceTask(Task task);
}
