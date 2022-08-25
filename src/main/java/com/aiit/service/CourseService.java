package com.aiit.service;

import com.aiit.entity.*;

import java.util.List;
import java.util.Map;

public interface CourseService {
    public Map<String,Object> getTeacherList();
    public Map<String,Object> getNowCourse(int pageNum,int pageNo,String userName,String roleId);
    public List<ScoreStandard> getEvaluationStandardList();
    public List<ScoreStandard> getTourStandardList();
    public void addListenCourseInfo(ListenCourse listenCourse,Task task);
    public void deleteEvaluationStandard(String standardId);
    public void addEvaluationStandard(ScoreStandard scoreStandard);
    public void updateEvaluationStandard(ScoreStandard scoreStandard);
    public List<ScoreStandard> getListenStandardList();
    public void deleteListenStandard(String standardId);
    public void addListenStandard(ScoreStandard scoreStandard);
    public void updateListenStandard(ScoreStandard scoreStandard);
    public void deleteTourStandard(String standardId);
    public void addTourStandard(ScoreStandard scoreStandard);
    public void updateTourStandard(ScoreStandard scoreStandard);
    public void addEvaluationCourseInfo(EvaluationCourse evaluationCourse,Task task);
    public void addTourCourseInfo(TourCourse tourCourse,Task task);
    public Map<String,Object> getInstituteInfo(String userName,String roleId);
    public Map<String,Object> getTeacherCourseInfo(String userName,String roleId);
    public List<CourseInfo> getCourseInfoWithText(String userName,String searchText,String roleId,int pageNo,int pageNum);
    public Map<String,Object> getNotStartCourse(String userName,String roleId,int pageNo,int pageNum);
    public Map<String,Object> searchNotStartCourse(String userName,String searchText,String roleId,int pageNo,int pageNum);
    public Task getTaskInfo(Task task);
    public Map<String,Object> getRecord(String userName,String roleId);
    public DateTime getCourseDateTime(String courseSlaveId);
    public ListenCourse getListenCourseInfo(ListenCourse listenCourse);
    public TourCourse getTourCourseInfo(TourCourse tourCourse);
    public EvaluationCourse getEvaluationCourseInfo(EvaluationCourse evaluationCourse);
    public void uploadClassroomImg(TourCourseImg tourCourse);
    public void deleteClassroomImg(String imgUrl);
}
