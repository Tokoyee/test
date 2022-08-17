package com.aiit.service.impl;

import com.aiit.entity.*;
import com.aiit.mapper.CourseMapper;
import com.aiit.mapper.InstituteMapper;
import com.aiit.mapper.UserMapper;
import com.aiit.service.CourseService;
import com.aiit.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    InstituteMapper instituteMapper;
    public Map<String,Object> getTeacherList(){
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        List<Object> data_list = new ArrayList<Object>();
        List<Object> result_list = new ArrayList<Object>();
        List<String> institudeList = courseMapper.getAllInstitude();
        data_list.add(institudeList);
        for(int i = 0;i < institudeList.size();i++){
            List<String> dataList = new ArrayList<String>();
            String institude = institudeList.get(i);
            List<String> teacherList = courseMapper.getInstitudeTeacher(institude);
            for(int j = 0;j < teacherList.size();j++){
                dataList.add(teacherList.get(j));
            }
            if(i == 0){
                data_list.add(dataList);
            }
            result_list.add(dataList);
        }
        dataMap.put("data_list",data_list);
        dataMap.put("result_list",result_list);

        return JsonUtil.success(dataMap);
    }
    public Map<String,Object> getNowCourse(int pageNum,int pageNo,String userName,String roleId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        String trueName = userMapper.getUserTrueName(userName).get(0);
        String instituteType = userMapper.getUserInstituteType(userName).get(0);
        List<String> instituteList = instituteMapper.getInstituteList(instituteType);
        if (roleId.equals("2022220230001")){//老师
            CourseInfo courseInfo = new CourseInfo();
            //正式上课时解除下方注释
//            courseInfo.setDate(now_date);
//            courseInfo.setTime(res_time);
            List<CourseInfo> courseInfos = courseMapper.getNowCourse(courseInfo);
            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            List<Object> resList = new ArrayList<Object>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= courseInfos.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<courseInfos.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(courseInfos.get(i).getInstituteName())){
                        if (!courseInfos.get(i).getTrueName().equals(trueName)){
                            dataList.add(courseInfos.get(i));
                        }
                    }
                }

            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(dataList.get(i));
            }
            dataMap.put("data",resList);
        }else if (roleId.equals("2022220230003")){//学校教务管理人员
            CourseInfo courseInfo = new CourseInfo();
            //正式上课时解除下方注释
//            courseInfo.setDate(now_date);
//            courseInfo.setTime(res_time);
            List<CourseInfo> courseInfos = courseMapper.getNowCourse(courseInfo);
            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            List<Object> resList = new ArrayList<Object>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= courseInfos.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<courseInfos.size();i++){
                if (!courseInfos.get(i).getTrueName().equals(trueName)){
                    dataList.add(courseInfos.get(i));
                }
            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程
                resList.add(dataList.get(i));
            }
            dataMap.put("data",resList);
        }else if (roleId.equals("2022220230004")){//学院教务管理人员
            CourseInfo courseInfo = new CourseInfo();
            //正式上课时解除下方注释
//            courseInfo.setDate(now_date);
//            courseInfo.setTime(res_time);
            List<CourseInfo> courseInfos = courseMapper.getNowCourse(courseInfo);
            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            List<Object> resList = new ArrayList<Object>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= courseInfos.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<courseInfos.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(courseInfos.get(i).getInstituteName())){
                        if (!courseInfos.get(i).getTrueName().equals(trueName)){
                            dataList.add(courseInfos.get(i));
                        }
                    }
                }
            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(dataList.get(i));
            }
            dataMap.put("data",resList);
        }else if (roleId.equals("2022220230005")){//学校督导
            CourseInfo courseInfo = new CourseInfo();
            //正式上课时解除下方注释
//            courseInfo.setDate(now_date);
//            courseInfo.setTime(res_time);
            List<CourseInfo> courseInfos = courseMapper.getNowCourse(courseInfo);
            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            List<Object> resList = new ArrayList<Object>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= courseInfos.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<courseInfos.size();i++){
                if (!courseInfos.get(i).getTrueName().equals(trueName)){
                    dataList.add(courseInfos.get(i));
                }
            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程
                resList.add(dataList.get(i));
            }
            dataMap.put("data",resList);
        }else if (roleId.equals("2022220230006")){//学院督导
            CourseInfo courseInfo = new CourseInfo();
            //正式上课时解除下方注释
//            courseInfo.setDate(now_date);
//            courseInfo.setTime(res_time);
            List<CourseInfo> courseInfos = courseMapper.getNowCourse(courseInfo);
            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            List<Object> resList = new ArrayList<Object>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= courseInfos.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<courseInfos.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(courseInfos.get(i).getInstituteName())){
                        if (!courseInfos.get(i).getTrueName().equals(trueName)){
                            dataList.add(courseInfos.get(i));
                        }
                    }
                }

            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(dataList.get(i));
            }
            dataMap.put("data",resList);
        }else if (roleId.equals("2022220230007")){//辅导员
        }

//        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date(System.currentTimeMillis());
//        String[] now_datetime = formatter.format(date).split(" ");
//        String now_date = now_datetime[0];
//        String now_time = now_datetime[1];
//        int now_hour = Integer.parseInt(now_time.split(":")[0]);
//        int now_minute = Integer.parseInt(now_time.split(":")[1]);
//        int minutes = now_hour * 60 + now_minute;
//        String res_time = null;
//        if (minutes >= 510 && minutes <= 605){  //第一二节
//            res_time = "08:30-10:05";
//        }else if(minutes >= 625 && minutes <= 720){//第三四节
//            res_time = "10:25-12:00";
//        }else if(minutes >= 840 && minutes <= 935){//第五六节
//            res_time = "14:00-15:35";
//        }else if(minutes >= 955 && minutes <= 1050){//第七八节
//            res_time = "15:55-17:30";
//        }else if(minutes >= 1140 && minutes <= 1285){//第九十十一节
//            res_time = "19:00-21:25";
//        }
//        if (res_time == null){
//            dataMap.put("data","当前未存在正在上课的教室");
//        }else {
//            CourseInfo courseInfo = new CourseInfo();
//            courseInfo.setDate(now_date);
//            courseInfo.setTime(res_time);
//            List<CourseInfo> courseInfos = courseMapper.getNowCourse(courseInfo);
//            List<Object> dataList = new ArrayList<Object>();
//            if (courseInfos.size() == 0){
//                dataMap.put("data","当前未存在正在上课的教室");
//                return JsonUtil.success(dataMap);
//            }
//            if (((pageNo-1) * pageNum) >= courseInfos.size()){
//                dataMap.put("data",dataList);
//                return JsonUtil.success(dataMap);
//            }
//            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
//                if (i == courseInfos.size()){
//                    break;
//                }
//                dataList.add(courseInfos.get(i));
//            }
//            dataMap.put("data",dataList);
//        }
        return JsonUtil.success(dataMap);
    }
    public List<ScoreStandard> getEvaluationStandardList(){
        return courseMapper.getEvaluationStandard();
    }
    public List<ScoreStandard> getTourStandardList(){
        return courseMapper.getTourStandard();
    }
    public void addListenCourseInfo(String include,String advice,String courseSlaveId,String userName,Task task){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String now_datetime = formatter.format(date);
        ListenCourse listenCourse = new ListenCourse();
        listenCourse.setInclude(include);
        listenCourse.setAdvice(advice);
        listenCourse.setCourseSlaveId(courseSlaveId);
        listenCourse.setUserName(userName);
        listenCourse.setDateTime(now_datetime);
        courseMapper.addListenCourseInfo(listenCourse);
        userMapper.solveOnceTask(task);
    }
    public void deleteEvaluationStandard(String standardId){
        courseMapper.deleteEvaluationStandard(standardId);
    }
    public void addEvaluationStandard(ScoreStandard scoreStandard){
        courseMapper.addEvaluationStandard(scoreStandard);
    }
    public void updateEvaluationStandard(ScoreStandard scoreStandard){
        courseMapper.updateEvaluationStandard(scoreStandard);
    }
    public void deleteTourStandard(String standardId){
        courseMapper.deleteTourStandard(standardId);
    }
    public void addTourStandard(ScoreStandard scoreStandard){
        courseMapper.addTourStandard(scoreStandard);
    }
    public void updateTourStandard(ScoreStandard scoreStandard){
        courseMapper.updateTourStandard(scoreStandard);
    }
    public List<ScoreStandard> getListenStandardList(){
        return courseMapper.getListenStandard();
    }
    public void deleteListenStandard(String standardId){
        courseMapper.deleteListenStandard(standardId);
    }
    public void addListenStandard(ScoreStandard scoreStandard){
        courseMapper.addListenStandard(scoreStandard);
    }
    public void updateListenStandard(ScoreStandard scoreStandard){
        courseMapper.updateListenStandard(scoreStandard);
    }
    public void addEvaluationCourseInfo(EvaluationCourse evaluationCourse,Task task){
        courseMapper.addEvaluationCourseInfo(evaluationCourse);
        userMapper.solveOnceTask(task);
    }
    public void addTourCourseInfo(TourCourse tourCourse,Task task){
        courseMapper.addTourCourseInfo(tourCourse);
        userMapper.solveOnceTask(task);
    }
    public Map<String,Object> getInstituteInfo(String userName,String roleId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        String trueName = userMapper.getUserTrueName(userName).get(0);
        String instituteType = userMapper.getUserInstituteType(userName).get(0);
        List<CourseInfo> courseInfos = courseMapper.getNowCourse(new CourseInfo());
        if (roleId.equals("2022220230001")){//老师
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataMap;
            }
            List<String> instituteList = instituteMapper.getInstituteList(instituteType);
            List<Object> resultList = new ArrayList<Object>();
            for(int i = 0;i<instituteList.size();i++){
                Map<String,Object> infoMap = new HashMap<String,Object>();
                List<CourseInfo> resList = new ArrayList<CourseInfo>();
                for(int j = 0;j<courseInfos.size();j++){
                    if (instituteList.get(i).equals(courseInfos.get(j).getInstituteName())){
                        if (!courseInfos.get(j).getTrueName().equals(trueName)){
                            resList.add(courseInfos.get(j));
                        }
                    }
                }
                infoMap.put("name",instituteList.get(i));
                infoMap.put("courseList",resList);
                resultList.add(infoMap);
            }
            dataMap.put("result",resultList);
            return dataMap;
        }else if (roleId.equals("2022220230003")){//学校教务管理人员
            List<String> instituteList = courseMapper.getAllInstitude();
            List<Object> resultList = new ArrayList<Object>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataMap;
            }
            for(int i = 0;i<instituteList.size();i++){
                Map<String,Object> infoMap = new HashMap<String,Object>();
                List<CourseInfo> resList = new ArrayList<CourseInfo>();
                for(int j = 0;j<courseInfos.size();j++){
                    if (instituteList.get(i).equals(courseInfos.get(j).getInstituteName())){
                        if (!courseInfos.get(j).getTrueName().equals(trueName)){
                            resList.add(courseInfos.get(j));
                        }
                    }
                }
                infoMap.put("name",instituteList.get(i));
                infoMap.put("courseList",resList);
                resultList.add(infoMap);
            }
            dataMap.put("result",resultList);
            return dataMap;
        }else if (roleId.equals("2022220230004")){//学院教务管理人员
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataMap;
            }
            List<String> instituteList = instituteMapper.getInstituteList(instituteType);
            List<Object> resultList = new ArrayList<Object>();
            for(int i = 0;i<instituteList.size();i++){
                Map<String,Object> infoMap = new HashMap<String,Object>();
                List<CourseInfo> resList = new ArrayList<CourseInfo>();
                for(int j = 0;j<courseInfos.size();j++){
                    if (instituteList.get(i).equals(courseInfos.get(j).getInstituteName())){
                        if (!courseInfos.get(j).getTrueName().equals(trueName)){
                            resList.add(courseInfos.get(j));
                        }
                    }
                }
                infoMap.put("name",instituteList.get(i));
                infoMap.put("courseList",resList);
                resultList.add(infoMap);
            }
            dataMap.put("result",resultList);
            return dataMap;
        }else if (roleId.equals("2022220230005")){//学校督导
            List<String> instituteList = courseMapper.getAllInstitude();
            List<Object> resultList = new ArrayList<Object>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataMap;
            }
            for(int i = 0;i<instituteList.size();i++){
                Map<String,Object> infoMap = new HashMap<String,Object>();
                List<CourseInfo> resList = new ArrayList<CourseInfo>();
                for(int j = 0;j<courseInfos.size();j++){
                    if (instituteList.get(i).equals(courseInfos.get(j).getInstituteName())){
                        if (!courseInfos.get(j).getTrueName().equals(trueName)){
                            resList.add(courseInfos.get(j));
                        }
                    }
                }
                infoMap.put("name",instituteList.get(i));
                infoMap.put("courseList",resList);
                resultList.add(infoMap);
            }
            dataMap.put("result",resultList);
            return dataMap;
        }else if (roleId.equals("2022220230006")){//学院督导
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataMap;
            }
            List<String> instituteList = instituteMapper.getInstituteList(instituteType);
            List<Object> resultList = new ArrayList<Object>();
            for(int i = 0;i<instituteList.size();i++){
                Map<String,Object> infoMap = new HashMap<String,Object>();
                List<CourseInfo> resList = new ArrayList<CourseInfo>();
                for(int j = 0;j<courseInfos.size();j++){
                    if (instituteList.get(i).equals(courseInfos.get(j).getInstituteName())){
                        if (!courseInfos.get(j).getTrueName().equals(trueName)){
                            resList.add(courseInfos.get(j));
                        }
                    }
                }
                infoMap.put("name",instituteList.get(i));
                infoMap.put("courseList",resList);
                resultList.add(infoMap);
            }
            dataMap.put("result",resultList);
            return dataMap;
        }else if (roleId.equals("2022220230007")){//辅导员
            return dataMap;
        }
        return dataMap;
    }
    public Map<String,Object> getTeacherCourseInfo(String userName,String roleId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        String trueName = userMapper.getUserTrueName(userName).get(0);
        String instituteType = userMapper.getUserInstituteType(userName).get(0);
        List<String> teacherList = courseMapper.getAllTeacher();
        List<CourseInfo> courseInfos = courseMapper.getNowCourse(new CourseInfo());
        if (roleId.equals("2022220230001")){//老师
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataMap;
            }
            List<String> instituteList = instituteMapper.getInstituteList(instituteType);
            List<CourseInfo> resList = new ArrayList<CourseInfo>();
            for(int i = 0;i<instituteList.size();i++){
                for(int j = 0;j<courseInfos.size();j++){
                    if (instituteList.get(i).equals(courseInfos.get(j).getInstituteName())){
                        if (!courseInfos.get(j).getTrueName().equals(trueName)){
                            resList.add(courseInfos.get(j));
                        }
                    }
                }
            }
            List<Object> resultList = new ArrayList<Object>();
            for(int i = 0;i<teacherList.size();i++){
                List<CourseInfo> dataList = new ArrayList<CourseInfo>();
                Map<String,Object> infoMap = new HashMap<String,Object>();
                for(int j = 0;j<resList.size();j++){
                    if (resList.get(j).getTrueName().equals(teacherList.get(i))){
                        dataList.add(resList.get(j));
                    }
                }
                infoMap.put("name",teacherList.get(i));
                infoMap.put("courseList",dataList);
                resultList.add(infoMap);
            }
            dataMap.put("result",resultList);
            return dataMap;
        }else if (roleId.equals("2022220230003")){//学校教务管理人员
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataMap;
            }
            List<Object> resultList = new ArrayList<Object>();
            List<CourseInfo> resList = new ArrayList<CourseInfo>();
            for(int i = 0;i<courseInfos.size();i++){
                if (!courseInfos.get(i).getTrueName().equals(trueName)){
                    resList.add(courseInfos.get(i));
                }
            }
            for(int i = 0;i<teacherList.size();i++){
                Map<String,Object> infoMap = new HashMap<String,Object>();
                List<CourseInfo> dataList = new ArrayList<CourseInfo>();
                for(int j = 0;j<resList.size();j++){
                    if (resList.get(j).getTrueName().equals(teacherList.get(i))){
                        dataList.add(resList.get(j));
                    }
                }
                infoMap.put("name",teacherList.get(i));
                infoMap.put("courseList",dataList);
                resultList.add(infoMap);
            }
            dataMap.put("result",resultList);
            return dataMap;
        }else if (roleId.equals("2022220230004")){//学院教务管理人员
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataMap;
            }
            List<String> instituteList = instituteMapper.getInstituteList(instituteType);
            List<CourseInfo> resList = new ArrayList<CourseInfo>();
            for(int i = 0;i<instituteList.size();i++){
                for(int j = 0;j<courseInfos.size();j++){
                    if (instituteList.get(i).equals(courseInfos.get(j).getInstituteName())){
                        if (!courseInfos.get(j).getTrueName().equals(trueName)){
                            resList.add(courseInfos.get(j));
                        }
                    }
                }
            }
            List<Object> resultList = new ArrayList<Object>();
            for(int i = 0;i<teacherList.size();i++){
                List<CourseInfo> dataList = new ArrayList<CourseInfo>();
                Map<String,Object> infoMap = new HashMap<String,Object>();
                for(int j = 0;j<resList.size();j++){
                    if (resList.get(j).getTrueName().equals(teacherList.get(i))){
                        dataList.add(resList.get(j));
                    }
                }
                infoMap.put("name",teacherList.get(i));
                infoMap.put("courseList",dataList);
                resultList.add(infoMap);
            }
            dataMap.put("result",resultList);
            return dataMap;
        }else if (roleId.equals("2022220230005")){//学校督导
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataMap;
            }
            List<Object> resultList = new ArrayList<Object>();
            List<CourseInfo> resList = new ArrayList<CourseInfo>();
            for(int i = 0;i<courseInfos.size();i++){
                if (!courseInfos.get(i).getTrueName().equals(trueName)){
                    resList.add(courseInfos.get(i));
                }
            }
            for(int i = 0;i<teacherList.size();i++){
                Map<String,Object> infoMap = new HashMap<String,Object>();
                List<CourseInfo> dataList = new ArrayList<CourseInfo>();
                for(int j = 0;j<resList.size();j++){
                    if (resList.get(j).getTrueName().equals(teacherList.get(i))){
                        dataList.add(resList.get(j));
                    }
                }
                infoMap.put("name",teacherList.get(i));
                infoMap.put("courseList",dataList);
                resultList.add(infoMap);
            }
            dataMap.put("result",resultList);
            return dataMap;
        }else if (roleId.equals("2022220230006")){//学院督导
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataMap;
            }
            List<String> instituteList = instituteMapper.getInstituteList(instituteType);
            List<CourseInfo> resList = new ArrayList<CourseInfo>();
            for(int i = 0;i<instituteList.size();i++){
                for(int j = 0;j<courseInfos.size();j++){
                    if (instituteList.get(i).equals(courseInfos.get(j).getInstituteName())){
                        if (!courseInfos.get(j).getTrueName().equals(trueName)){
                            resList.add(courseInfos.get(j));
                        }
                    }
                }
            }
            List<Object> resultList = new ArrayList<Object>();
            for(int i = 0;i<teacherList.size();i++){
                List<CourseInfo> dataList = new ArrayList<CourseInfo>();
                Map<String,Object> infoMap = new HashMap<String,Object>();
                for(int j = 0;j<resList.size();j++){
                    if (resList.get(j).getTrueName().equals(teacherList.get(i))){
                        dataList.add(resList.get(j));
                    }
                }
                infoMap.put("name",teacherList.get(i));
                infoMap.put("courseList",dataList);
                resultList.add(infoMap);
            }
            dataMap.put("result",resultList);
            return dataMap;
        }else if (roleId.equals("2022220230007")){//辅导员
            return dataMap;
        }
        return dataMap;
    }
    public List<CourseInfo> getCourseInfoWithText(String userName,String searchText,String roleId,int pageNo,int pageNum){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        String trueName = userMapper.getUserTrueName(userName).get(0);
        String instituteType = userMapper.getUserInstituteType(userName).get(0);
        List<String> instituteList = instituteMapper.getInstituteList(instituteType);
        List<CourseInfo> courseInfos = courseMapper.getCourseInfoWithText(searchText);
        if (roleId.equals("2022220230001")){//老师
            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            List<CourseInfo> resList = new ArrayList<CourseInfo>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataList;
            }
            if (((pageNo-1) * pageNum) >= courseInfos.size()){
                dataMap.put("data",dataList);
                return dataList;
            }
            for(int i = 0;i<courseInfos.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(courseInfos.get(i).getInstituteName())){
                        if (!courseInfos.get(i).getTrueName().equals(trueName)){
                            dataList.add(courseInfos.get(i));
                        }
                    }
                }

            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(dataList.get(i));
            }
            return resList;
        }else if (roleId.equals("2022220230003")){//学校教务管理人员

            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            List<Object> resList = new ArrayList<Object>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataList;
            }
            if (((pageNo-1) * pageNum) >= courseInfos.size()){
                dataMap.put("data",dataList);
                return dataList;
            }
            for(int i = 0;i<courseInfos.size();i++){
                if (!courseInfos.get(i).getTrueName().equals(trueName)){
                    dataList.add(courseInfos.get(i));
                }
            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程
                resList.add(dataList.get(i));
            }
            dataMap.put("data",resList);
        }else if (roleId.equals("2022220230004")){//学院教务管理人员
            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            List<CourseInfo> resList = new ArrayList<CourseInfo>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataList;
            }
            if (((pageNo-1) * pageNum) >= courseInfos.size()){
                dataMap.put("data",dataList);
                return dataList;
            }
            for(int i = 0;i<courseInfos.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(courseInfos.get(i).getInstituteName())){
                        if (!courseInfos.get(i).getTrueName().equals(trueName)){
                            dataList.add(courseInfos.get(i));
                        }
                    }
                }
            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(dataList.get(i));
            }
            return resList;
        }else if (roleId.equals("2022220230005")){//学校督导

            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            List<CourseInfo> resList = new ArrayList<CourseInfo>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataList;
            }
            if (((pageNo-1) * pageNum) >= courseInfos.size()){
                dataMap.put("data",dataList);
                return dataList;
            }
            for(int i = 0;i<courseInfos.size();i++){
                if (!courseInfos.get(i).getTrueName().equals(trueName)){
                    dataList.add(courseInfos.get(i));
                }
            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程
                resList.add(dataList.get(i));
            }
            return resList;
        }else if (roleId.equals("2022220230006")){//学院督导

            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            List<CourseInfo> resList = new ArrayList<CourseInfo>();
            if (courseInfos.size() == 0){
                dataMap.put("data","当前未存在正在上课的教室");
                return dataList;
            }
            if (((pageNo-1) * pageNum) >= courseInfos.size()){
                dataMap.put("data",dataList);
                return dataList;
            }
            for(int i = 0;i<courseInfos.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(courseInfos.get(i).getInstituteName())){
                        if (!courseInfos.get(i).getTrueName().equals(trueName)){
                            dataList.add(courseInfos.get(i));
                        }
                    }
                }

            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(dataList.get(i));
            }
            return resList;
        }else if (roleId.equals("2022220230007")){//辅导员
            List<CourseInfo> dataList = new ArrayList<CourseInfo>();
            return dataList;
        }
        List<CourseInfo> dataList = new ArrayList<CourseInfo>();
        return dataList;
    }
}
