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
    @Select("select * from task where userName = #{userName}")
    public List<Task> getTaskList(String userName);
    @Update("UPDATE task SET taskNum = (taskNum - 1),taskStatus = #{taskStatus},solveTaskNum = (solveTaskNum + 1) WHERE userName = #{userName} AND taskDescribe = #{taskDescribe}")
    public void solveOnceTask(Task task);
    @Select("select * from task where taskDescribe = #{taskDescribe} and userName = #{userName}")
    public Task getTaskInfo(Task task);
    @Insert("insert into subscription(`subscriptionId`,`touser`,`courseSlaveId`,`userName`,`roleId`,`remindDate`,`remindTime`) values(#{subscriptionId},#{touser},#{courseSlaveId},#{userName},#{roleId},#{remindDate},#{remindTime}})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addSubscription(Subscription subscription);
    @Delete("delete from subscription where subscriptionId = #{subscriptionId}}")
    public void deleteSubscription(String subscriptionId);
    @Select("select * from subscription where remindDate = #{remindDate}")
    public List<Subscription> getTodaySubscription(String remindDate);
    @Select("select * from subscription")
    public List<Subscription> getAllSubscription();
    @Select("SELECT u.trueName,cm.courseName,cs.className,cs.section,cs.time,dw.date,cs.courseSlaveId FROM subscription s,course_slave cs,course_master cm,user u,date_week dw WHERE s.courseSlaveId = cs.courseSlaveId  AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId AND dw.week = cs.week AND dw.weeks = cs.weeks AND s.subscriptionId = #{subscriptionId}")
    public SubscriptionInfo getSubscriptionInfo(String subscriptionId);
    @Select("SELECT u.trueName,cm.courseName,cs.className,cs.section,cs.time,dw.date,cs.courseSlaveId FROM subscription s,course_slave cs,course_master cm,user u,date_week dw WHERE s.courseSlaveId = cs.courseSlaveId  AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId AND dw.week = cs.week AND dw.weeks = cs.weeks AND s.userName = #{userName} AND s.roleId = #{roleId}")
    public List<SubscriptionInfo> getSubscriptionWithRole(UserSubscription userSubscription);
}
