package com.aiit.service;

import com.aiit.entity.*;

import java.util.List;
import java.util.Map;

public interface CourseService {
    public Map<String,Object> getTeacherList();
    public Map<String,Object> getNowCourse(int pageNum,int pageNo,String userName,String roleId);
    public List<ScoreStandard> getEvaluationStandardList();
    public List<ScoreStandard> getTourStandardList();
    public void addListenCourseInfo(String include,String advice,String courseSlaveId,String userName,Task task);
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
}
