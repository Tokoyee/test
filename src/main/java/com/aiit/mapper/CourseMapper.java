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
    @Select("select * from listen_course_standard")
    public List<ScoreStandard> getListenStandard();
    @Delete("delete from listen_course_standard where standardId = #{standardId}")
    public void deleteListenStandard(String standardId);
    @Insert("insert into listen_course_standard(`standardId`,`standardType`,`standardInclude`,`standardWeight`) values(#{standardId},#{standardType},#{standardInclude},#{standardWeight})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addListenStandard(ScoreStandard scoreStandard);
    @Update("update listen_course_standard set standardType = #{standardType},standardInclude = #{standardInclude},standardWeight = #{standardWeight} where standardId = #{standardId}")
    public void updateListenStandard(ScoreStandard scoreStandard);
    @Insert("insert into listen_course(`q1`,`q2`,`q3`,`q4`,`include`,`advice`,`courseSlaveId`,`userName`,`dateTime`,`roleId`) values(#{q1},#{q2},#{q3},#{q4},#{include},#{advice},#{courseSlaveId},#{userName},#{dateTime},#{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addListenCourseInfo(ListenCourse listenCourse);
    @Insert("insert into evaluation_course(`q1`,`q2`,`q3`,`q4`,`q5`,`q6`,`q7`,`q8`,`q9`,`q10`,`q11`,`q12`,`q13`,`advice`,`courseSlaveId`,`userName`,`dateTime`,`roleId`,`remark`) values(#{q1},#{q2},#{q3},#{q4},#{q5},#{q6},#{q7},#{q8},#{q9},#{q10},#{q11},#{q12},#{q13},#{advice},#{courseSlaveId},#{userName},#{dateTime},#{roleId},#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addEvaluationCourseInfo(EvaluationCourse evaluationCourse);
    @Insert("insert into tour_course(`q1`,`q2`,`q3`,`q4`,`q5`,`attendance`,`riseRate`,`advice`,`courseSlaveId`,`userName`,`dateTime`,`roleId`,`remark`) values(#{q1},#{q2},#{q3},#{q4},#{q5},#{attendance},#{riseRate},#{advice},#{courseSlaveId},#{userName},#{dateTime},#{roleId},#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void addTourCourseInfo(TourCourse tourCourse);
    @Select("SELECT cs.week,cs.weeks,cs.periodTime,cs.time,dw.date,cm.courseName,cm.classroomId,u.trueName,u.instituteName FROM course_slave cs,date_week dw,course_master cm,user u WHERE date = '2022-11-01' AND time = '08:30-10:05' AND cs.week = dw.week AND cs.weeks = dw.weeks AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId AND (cm.courseName LIKE concat('%',#{searchText},'%') or u.trueName LIKE concat('%',#{searchText},'%') or u.instituteName LIKE concat('%',#{searchText},'%'))")
    public List<CourseInfo> getCourseInfoWithText(String searchText);
    @Select("SELECT DISTINCT u.trueName FROM course_slave cs,date_week dw,course_master cm,user u WHERE DATE = '2022-11-01' AND TIME = '08:30-10:05' AND cs.week = dw.week AND cs.weeks = dw.weeks AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId")
    public List<String> getAllTeacher();
    @Select("SELECT cs.week,cs.weeks,cs.periodTime,cs.courseSlaveId,cs.time,dw.date,cm.courseName,cm.classroomId,u.trueName,u.instituteName FROM course_slave cs,date_week dw,course_master cm,user u WHERE cs.week = dw.week AND DATE > '2022-11-01' AND cs.weeks = dw.weeks AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId ORDER BY date")
    public List<CourseInfo> getAfterTodayCourse(String date);
    @Select("SELECT cs.week,cs.weeks,cs.periodTime,cs.courseSlaveId,cs.time,dw.date,cm.courseName,cm.classroomId,u.trueName,u.instituteName FROM course_slave cs,date_week dw,course_master cm,user u WHERE cs.week = dw.week AND DATE = '2022-11-01' AND time >= #{time} and cs.weeks = dw.weeks AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId ORDER BY time")
    public List<CourseInfo> getTodayCourse(CourseInfo courseInfo);
    @Select("SELECT cs.week,cs.weeks,cs.periodTime,cs.courseSlaveId,cs.time,dw.date,cm.courseName,cm.classroomId,u.trueName,u.instituteName FROM course_slave cs,date_week dw,course_master cm,user u WHERE cs.week = dw.week AND DATE > '2022-11-01' AND cs.weeks = dw.weeks AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId and (cm.courseName LIKE concat('%',#{searchText},'%') or u.trueName LIKE concat('%',#{searchText},'%') or u.instituteName LIKE concat('%',#{searchText},'%')) ORDER BY date")
    public List<CourseInfo> searchAfterTodayCourse(SearchCourse searchCourse);
    @Select("SELECT cs.week,cs.weeks,cs.periodTime,cs.courseSlaveId,cs.time,dw.date,cm.courseName,cm.classroomId,u.trueName,u.instituteName FROM course_slave cs,date_week dw,course_master cm,user u WHERE cs.week = dw.week AND DATE = '2022-11-01' AND time >= #{time} and cs.weeks = dw.weeks AND cs.courseMasterId = cm.courseMasterId AND cm.userId = u.userId and (cm.courseName LIKE concat('%',#{searchText},'%') or u.trueName LIKE concat('%',#{searchText},'%') or u.instituteName LIKE concat('%',#{searchText},'%')) ORDER BY time")
    public List<CourseInfo> searchTodayCourse(SearchCourse searchCourse);
    @Select("SELECT cs.courseSlaveId,cm.courseName,u.trueName,classroomId,className,periodTime,time,DATETIME,WEEK,weeks,section FROM evaluation_course ec,course_slave cs,course_master cm,user u WHERE cs.courseMasterId = cm.courseMasterId AND cs.courseSlaveId = ec.courseSlaveId AND u.userId = cm.userId AND ec.userName = #{userName}")
    public List<Record> getEvaluationRecord(String userName);
    @Select("SELECT cs.courseSlaveId,cm.courseName,u.trueName,classroomId,className,periodTime,TIME,DATETIME,WEEK,weeks,section FROM tour_course tc,course_slave cs,course_master cm,user u WHERE cs.courseMasterId = cm.courseMasterId AND cs.courseSlaveId = tc.courseSlaveId AND u.userId = cm.userId AND tc.userName = #{userName}")
    public List<Record> getTourRecord(String userName);
    @Select("SELECT cs.courseSlaveId,cm.courseName,u.trueName,classroomId,className,periodTime,TIME,DATETIME,WEEK,weeks,section FROM listen_course lc,course_slave cs,course_master cm,user u WHERE cs.courseMasterId = cm.courseMasterId AND cs.courseSlaveId = lc.courseSlaveId AND u.userId = cm.userId AND lc.userName = #{userName}")
    public List<Record> getListenRecord(String userName);
    @Select("SELECT dw.date,cs.time FROM date_week dw,course_slave cs WHERE dw.week = cs.week AND dw.weeks = cs.weeks AND courseSlaveId = #{courseSlaveId}")
    public DateTime getCourseDateTime(String courseSlaveId);
    @Select("select * from listen_course where userName = #{userName} and roleId = #{roleId} and courseSlaveId = #{courseSlaveId}")
    public ListenCourse getListenCourseInfo(ListenCourse listenCourse);
    @Select("select * from listen_course where userName = #{userName} and roleId = #{roleId}")
    public List<ListenCourse> getListenCourseList(ListenCourse listenCourse);
    @Select("select * from tour_course where userName = #{userName} and roleId = #{roleId} and courseSlaveId = #{courseSlaveId}")
    public TourCourse getTourCourseInfo(TourCourse tourCourse);
    @Select("select * from tour_course where userName = #{userName} and roleId = #{roleId}")
    public List<TourCourse> getTourCourseList(TourCourse tourCourse);
    @Select("select * from evaluation_course where userName = #{userName} and roleId = #{roleId} and courseSlaveId = #{courseSlaveId}")
    public EvaluationCourse getEvaluationCourseInfo(EvaluationCourse evaluationCourse);
    @Select("select * from evaluation_course where userName = #{userName} and roleId = #{roleId}")
    public List<EvaluationCourse> getEvaluationCourseList(EvaluationCourse evaluationCourse);
}
