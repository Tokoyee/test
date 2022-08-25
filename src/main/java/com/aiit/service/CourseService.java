package com.aiit.service;

import com.aiit.entity.*;

import java.util.List;
import java.util.Map;

public interface CourseService {
    /**
     * 获取教师列表
     * @return Map<String,Object>
     */
    Map<String,Object> getTeacherList();

    /**
     *获取当前日期的课程
     * @param pageNum 页码数
     * @param pageNo 页码
     * @param userName 用户名
     * @param roleId 角色id
     * @return Map<String,Object>
     */
    Map<String,Object> getNowCourse(int pageNum,int pageNo,String userName,String roleId);

    /**
     * 获取评课指标列表
     * @return List<ScoreStandard> 评课指标列表
     */
    List<ScoreStandard> getEvaluationStandardList();

    /**
     * 获取巡课指标列表
     * @return 巡课指标列表
     */
    List<ScoreStandard> getTourStandardList();

    /**
     * 添加听课记录
     * @param listenCourse 教师听课信息
     * @param task 任务信息
     */
    void addListenCourseInfo(ListenCourse listenCourse,Task task);

    /**
     * 删除评课指标
     * @param standardId 评课指标编号
     */
    void deleteEvaluationStandard(String standardId);

    /**
     * 添加评课指标
     * @param scoreStandard 评课指标编号
     */
    void addEvaluationStandard(ScoreStandard scoreStandard);

    /**
     * 更新评课指标
     * @param scoreStandard 评课信息标准
     */
    void updateEvaluationStandard(ScoreStandard scoreStandard);

    /**
     * 获取听课指标列表
     * @return List<ScoreStandard> 课指标列表
     */
    List<ScoreStandard> getListenStandardList();

    /**
     * 删除听课指标
     * @param standardId 听课指标编号
     */
    void deleteListenStandard(String standardId);

    /**
     * 添加听课指标
     * @param scoreStandard 评分标准信息
     */
    void addListenStandard(ScoreStandard scoreStandard);

    /**
     * 更新听课指标
     * @param scoreStandard 评分标准信息
     */
    void updateListenStandard(ScoreStandard scoreStandard);

    /**
     * 删除巡课指标
     * @param standardId 巡课指标编号
     */
    void deleteTourStandard(String standardId);

    /**
     * 添加巡课指标
     * @param scoreStandard 评分标准信息
     */
    void addTourStandard(ScoreStandard scoreStandard);

    /**
     * 更新巡课指标
     * @param scoreStandard  评分标准信息
     */
    void updateTourStandard(ScoreStandard scoreStandard);

    /**
     * 添加评课记录
     * @param evaluationCourse 评课信息
     * @param task 任务信息
     */
    void addEvaluationCourseInfo(EvaluationCourse evaluationCourse,Task task);

    /**
     * 添加巡课记录
     * @param tourCourse 巡课信息
     * @param task 任务信息
     */
    void addTourCourseInfo(TourCourse tourCourse,Task task);

    /**
     * 获取学院信息
     * @param userName 用户名
     * @param roleId 角色id
     * @return Map<String,Object>
     */
    Map<String,Object> getInstituteInfo(String userName,String roleId);

    /**
     * 获取教师所授课程
     * @param userName 用户名
     * @param roleId 角色id
     * @return Map<String,Object>
     */
    Map<String,Object> getTeacherCourseInfo(String userName,String roleId);

    /**
     * 搜索课程
     * @param userName 用户名
     * @param searchText 关键字
     * @param roleId 角色编号
     * @param pageNo 页码
     * @param pageNum 页数据量
     * @return List<CourseInfo> 课程列表
     */
    List<CourseInfo> getCourseInfoWithText(String userName,String searchText,String roleId,int pageNo,int pageNum);

    /**
     * 获取未开始课程
     * @param userName 用户名
     * @param roleId 角色id
     * @param pageNo 页码
     * @param pageNum 页数据量
     * @return Map<String,Object>
     */
    Map<String,Object> getNotStartCourse(String userName,String roleId,int pageNo,int pageNum);

    /**
     * 搜索预约课程
     * @param userName 角色名
     * @param searchText 关键字
     * @param roleId 角色编号
     * @param pageNo 页码
     * @param pageNum 页数据量
     * @return Map<String,Object>
     */
    Map<String,Object> searchNotStartCourse(String userName,String searchText,String roleId,int pageNo,int pageNum);

    /**
     * 获取任务信息
     * @param task 任务信息
     * @return 任务
     */
    Task getTaskInfo(Task task);

    /**
     *根据角色获取相应的评价记录
     * @param userName 用户名
     * @param roleId 角色编号
     * @return Map<String,Object>
     */
    Map<String,Object> getRecord(String userName,String roleId);

    /**
     * 获取课程时间
     * @param courseSlaveId 课程id
     * @return 时间
     */
    DateTime getCourseDateTime(String courseSlaveId);

    /**
     * 获取角色听课信息
     * @param listenCourse 教师听课信息
     * @return 听课信息
     */
    ListenCourse getListenCourseInfo(ListenCourse listenCourse);

    /**
     * 获取角色巡课信息
     * @param tourCourse 巡课信息
     * @return 巡课信息
     */
    TourCourse getTourCourseInfo(TourCourse tourCourse);

    /**
     * 获取角色评课信息
     * @param evaluationCourse 评课信息
     * @return 评课信息
     */
    EvaluationCourse getEvaluationCourseInfo(EvaluationCourse evaluationCourse);

    /**
     * 上传巡课照片
     * @param tourCourse 巡课抓拍图片表
     */
    void uploadClassroomImg(TourCourseImg tourCourse);

    /**
     * 删除图片
     * @param imgUrl 照片url
     */
    void deleteClassroomImg(String imgUrl);
}
