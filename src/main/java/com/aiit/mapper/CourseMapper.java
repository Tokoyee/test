package com.aiit.mapper;

import com.aiit.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("select * from camera where cameraId = #{cameraId}")
    public Camera getCameraUrl(String cameraId);
    @Select("SELECT DISTINCT instituteName FROM user")
    public List<String> getAllInstitude();
    @Select("SELECT trueName FROM user u,role r,user_role ur,author a,role_author ra WHERE u.userId = ur.userId AND r.roleId = ur.roleId AND ra.roleId = r.roleId AND ra.authorId = a.authorId AND instituteName = #{instituteName} AND roleName = '老师'")
    public List<String> getInstitudeTeacher(String instituteName);
    @Select("SELECT cs.week,cs.weeks,cs.periodTime,cs.courseSlaveId,cs.time,dw.date,cm.courseName,cm.classroomId,u.trueName,u.instituteName FROM course_slave cs,date_week dw,course_master cm,user u WHERE date = '2022-11-01' AND time = '08:30-10:05' AND cs.week = dw.week AND cs.weeks = dw.weeks AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId")
    public List<CourseInfo> getNowCourse(CourseInfo courseInfo);
    @Select("select * from evaluation_course_standard")
    public List<ScoreStandard> getEvaluationStandard();
    @Delete("delete from evaluation_course_standard where standardId = #{standardId}")
    public void deleteEvaluationStandard(String standardId);
    @Insert("insert into evaluation_course_standard(`standardId`,`standardType`,`standardInclude`,`standardWeight`) values(#{standardId},#{standardType},#{standardInclude},#{standardWeight})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addEvaluationStandard(ScoreStandard scoreStandard);
    @Update("update evaluation_course_standard set standardType = #{standardType},standardInclude = #{standardInclude},standardWeight = #{standardWeight} where standardId = #{standardId}")
    public void updateEvaluationStandard(ScoreStandard scoreStandard);
    @Select("select * from tour_course_standard")
    public List<ScoreStandard> getTourStandard();
    @Delete("delete from tour_course_standard where standardId = #{standardId}")
    public void deleteTourStandard(String standardId);
    @Insert("insert into tour_course_standard(`standardId`,`standardType`,`standardInclude`,`standardWeight`) values(#{standardId},#{standardType},#{standardInclude},#{standardWeight})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addTourStandard(ScoreStandard scoreStandard);
    @Update("update tour_course_standard set standardType = #{standardType},standardInclude = #{standardInclude},standardWeight = #{standardWeight} where standardId = #{standardId}")
    public void updateTourStandard(ScoreStandard scoreStandard);
    @Insert("insert into listen_course(`include`,`advice`,`courseSlaveId`,`userName`) values(#{include},#{advice},#{courseSlaveId},#{userName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addListenCourseInfo(ListenCourse listenCourse);
    @Insert("insert into evaluation_course(`attitudeScore`,`includeScore`,`methodScore`,`effectScore`,`totalScore`,`advice`,`courseSlaveId`,`userName`) values(#{attitudeScore},#{includeScore},#{methodScore},#{effectScore},#{totalScore},#{advice},#{courseSlaveId},#{userName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addEvaluationCourseInfo(EvaluationCourse evaluationCourse);
    @Insert("insert into tour_course(`attitudeScore`,`includeScore`,`methodScore`,`totalScore`,`attendance`,`riseRate`,`advice`,`courseSlaveId`,`userName`) values(#{attitudeScore},#{includeScore},#{methodScore},#{totalScore},#{attendance},#{riseRate},#{advice},#{courseSlaveId},#{userName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addTourCourseInfo(TourCourse tourCourse);
    @Select("SELECT cs.week,cs.weeks,cs.periodTime,cs.time,dw.date,cm.courseName,cm.classroomId,u.trueName,u.instituteName FROM course_slave cs,date_week dw,course_master cm,user u WHERE date = '2022-11-01' AND time = '08:30-10:05' AND cs.week = dw.week AND cs.weeks = dw.weeks AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId AND courseName LIKE concat('%',#{searchText},'%')")
    public List<CourseInfo> getCourseInfoWithText(String searchText);
    @Select("SELECT DISTINCT u.trueName FROM course_slave cs,date_week dw,course_master cm,user u WHERE DATE = '2022-11-01' AND TIME = '08:30-10:05' AND cs.week = dw.week AND cs.weeks = dw.weeks AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId")
    public List<String> getAllTeacher();
}
