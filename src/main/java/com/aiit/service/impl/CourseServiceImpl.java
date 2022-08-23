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
            String[] imgList = new String[]{"http://aiitbeidou.cn:8080/courseImg/img1.png","http://aiitbeidou.cn:8080/courseImg/img2.png","http://aiitbeidou.cn:8080/courseImg/img3.png","http://aiitbeidou.cn:8080/courseImg/img4.png","http://aiitbeidou.cn:8080/courseImg/img5.png"};
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == dataList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                CourseInfo ci = dataList.get(i);
                ci.setCourseImg(imgList[new Random().nextInt(5)]);
                resList.add(ci);
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
    public void addListenCourseInfo(ListenCourse listenCourse,Task task){
//        courseMapper.addListenCourseInfo(listenCourse);
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
    public Map<String,Object> getNotStartCourse(String userName,String roleId,int pageNo,int pageNum){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        String trueName = userMapper.getUserTrueName(userName).get(0);
        String instituteType = userMapper.getUserInstituteType(userName).get(0);
        List<String> instituteList = instituteMapper.getInstituteList(instituteType);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String now_date = formatter.format(date).split(" ")[0];
        String now_time = formatter.format(date).split(" ")[1];
        int now_hour = Integer.parseInt(now_time.split(":")[0]);
        int now_minute = Integer.parseInt(now_time.split(":")[1]);
        int minutes = now_hour * 60 + now_minute;
        String res_time = null;
        if (minutes < 510){  //第一二节未开始
            res_time = "08:30-10:05";
        }else if(minutes >= 510 && minutes < 625){//一二开始，第三四节未开始
            res_time = "10:25-12:00";
        }else if(minutes >= 625 && minutes < 840){//三四开始，第五六节未开始
            res_time = "14:00-15:35";
        }else if(minutes >= 840 && minutes < 955){//五六开始，第七八节未开始
            res_time = "15:55-17:30";
        }else if(minutes >= 955 && minutes < 1140){//七八开始，第九十十一节未开始
            res_time = "19:00-21:25";
        }
        List<CourseInfo> dataList = new ArrayList<CourseInfo>();
        if (res_time != null){
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setDate(now_date);
            courseInfo.setTime(res_time);
            List<CourseInfo> todayCourse = courseMapper.getTodayCourse(courseInfo);
            for (int i = 0;i<todayCourse.size();i++){
                dataList.add(todayCourse.get(i));
            }
        }
        List<CourseInfo> afterTodayCourse = courseMapper.getAfterTodayCourse(now_date);
        for (int i = 0;i<afterTodayCourse.size();i++){
            dataList.add(afterTodayCourse.get(i));
        }
        List<CourseInfo> resList = new ArrayList<CourseInfo>();
        List<CourseInfo> filterList = new ArrayList<CourseInfo>();
        if (roleId.equals("2022220230001")){//老师
            if (dataList.size() == 0){
                dataMap.put("data","本学期所有课程均已结束");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= dataList.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<dataList.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(dataList.get(i).getInstituteName())){
                        if (!dataList.get(i).getTrueName().equals(trueName)){
                            filterList.add(dataList.get(i));
                        }
                    }
                }

            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == filterList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                CourseInfo ci = filterList.get(i);
                List<SubscriptionInfo> subscriptionInfos = userMapper.getSubscriptionWithRole(new UserSubscription(userName,"2022220230001"));
                for(int j = 0;j<subscriptionInfos.size();j++){
                    if (subscriptionInfos.get(j).getCourseSlaveId().equals(ci.getCourseSlaveId())){
                        ci.setSubscription(true);
                    }
                }
                resList.add(ci);
            }
            dataMap.put("result",resList);
            return dataMap;
        }else if (roleId.equals("2022220230003")){//学校教务管理人员
            if (dataList.size() == 0){
                dataMap.put("data","本学期所有课程均已结束");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= dataList.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<dataList.size();i++){
                if (!dataList.get(i).getTrueName().equals(trueName)){
                    filterList.add(dataList.get(i));
                }
            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == filterList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                CourseInfo ci = filterList.get(i);
                List<SubscriptionInfo> subscriptionInfos = userMapper.getSubscriptionWithRole(new UserSubscription(userName,"2022220230003"));
                for(int j = 0;j<subscriptionInfos.size();j++){
                    if (subscriptionInfos.get(j).getCourseSlaveId().equals(ci.getCourseSlaveId())){
                        ci.setSubscription(true);
                    }
                }
                resList.add(ci);
            }
            dataMap.put("result",resList);
            return dataMap;
        }else if (roleId.equals("2022220230004")){//学院教务管理人员
            if (dataList.size() == 0){
                dataMap.put("data","本学期所有课程均已结束");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= dataList.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<dataList.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(dataList.get(i).getInstituteName())){
                        if (!dataList.get(i).getTrueName().equals(trueName)){
                            filterList.add(dataList.get(i));
                        }
                    }
                }

            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == filterList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                CourseInfo ci = filterList.get(i);
                List<SubscriptionInfo> subscriptionInfos = userMapper.getSubscriptionWithRole(new UserSubscription(userName,"2022220230004"));
                for(int j = 0;j<subscriptionInfos.size();j++){
                    if (subscriptionInfos.get(j).getCourseSlaveId().equals(ci.getCourseSlaveId())){
                        ci.setSubscription(true);
                    }
                }
                resList.add(ci);
            }
            dataMap.put("result",resList);
            return dataMap;
        }else if (roleId.equals("2022220230005")){//学校督导
            if (dataList.size() == 0){
                dataMap.put("data","本学期所有课程均已结束");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= dataList.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<dataList.size();i++){
                if (!dataList.get(i).getTrueName().equals(trueName)){
                    filterList.add(dataList.get(i));
                }
            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == filterList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                CourseInfo ci = filterList.get(i);
                List<SubscriptionInfo> subscriptionInfos = userMapper.getSubscriptionWithRole(new UserSubscription(userName,"2022220230005"));
                for(int j = 0;j<subscriptionInfos.size();j++){
                    if (subscriptionInfos.get(j).getCourseSlaveId().equals(ci.getCourseSlaveId())){
                        ci.setSubscription(true);
                    }
                }
                resList.add(ci);
            }
            dataMap.put("result",resList);
            return dataMap;
        }else if (roleId.equals("2022220230006")){//学院督导
            if (dataList.size() == 0){
                dataMap.put("data","本学期所有课程均已结束");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= dataList.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<dataList.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(dataList.get(i).getInstituteName())){
                        if (!dataList.get(i).getTrueName().equals(trueName)){
                            filterList.add(dataList.get(i));
                        }
                    }
                }

            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == filterList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                CourseInfo ci = filterList.get(i);
                List<SubscriptionInfo> subscriptionInfos = userMapper.getSubscriptionWithRole(new UserSubscription(userName,"2022220230006"));
                for(int j = 0;j<subscriptionInfos.size();j++){
                    if (subscriptionInfos.get(j).getCourseSlaveId().equals(ci.getCourseSlaveId())){
                        ci.setSubscription(true);
                    }
                }
                resList.add(ci);
            }
            dataMap.put("result",resList);
            return dataMap;
        }else if (roleId.equals("2022220230007")){//辅导员
            return dataMap;
        }
        return dataMap;
    }
    public Map<String,Object> searchNotStartCourse(String userName,String searchText,String roleId,int pageNo,int pageNum){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        String trueName = userMapper.getUserTrueName(userName).get(0);
        String instituteType = userMapper.getUserInstituteType(userName).get(0);
        List<String> instituteList = instituteMapper.getInstituteList(instituteType);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String now_date = formatter.format(date).split(" ")[0];
        String now_time = formatter.format(date).split(" ")[1];
        int now_hour = Integer.parseInt(now_time.split(":")[0]);
        int now_minute = Integer.parseInt(now_time.split(":")[1]);
        int minutes = now_hour * 60 + now_minute;
        String res_time = null;
        if (minutes < 510){  //第一二节未开始
            res_time = "08:30-10:05";
        }else if(minutes >= 510 && minutes < 625){//一二开始，第三四节未开始
            res_time = "10:25-12:00";
        }else if(minutes >= 625 && minutes < 840){//三四开始，第五六节未开始
            res_time = "14:00-15:35";
        }else if(minutes >= 840 && minutes < 955){//五六开始，第七八节未开始
            res_time = "15:55-17:30";
        }else if(minutes >= 955 && minutes < 1140){//七八开始，第九十十一节未开始
            res_time = "19:00-21:25";
        }
        List<CourseInfo> dataList = new ArrayList<CourseInfo>();
        SearchCourse searchCourse = new SearchCourse();
        searchCourse.setDate(now_date);
        searchCourse.setSearchText(searchText);
        if (res_time != null){
            searchCourse.setTime(res_time);
            List<CourseInfo> todayCourse = courseMapper.searchTodayCourse(searchCourse);
            for (int i = 0;i<todayCourse.size();i++){
                dataList.add(todayCourse.get(i));
            }
        }
        List<CourseInfo> afterTodayCourse = courseMapper.searchAfterTodayCourse(searchCourse);
        for (int i = 0;i<afterTodayCourse.size();i++){
            dataList.add(afterTodayCourse.get(i));
        }
        List<CourseInfo> resList = new ArrayList<CourseInfo>();
        List<CourseInfo> filterList = new ArrayList<CourseInfo>();
        if (roleId.equals("2022220230001")){//老师
            if (dataList.size() == 0){
                dataMap.put("data","本学期所有课程均已结束");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= dataList.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<dataList.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(dataList.get(i).getInstituteName())){
                        if (!dataList.get(i).getTrueName().equals(trueName)){
                            filterList.add(dataList.get(i));
                        }
                    }
                }

            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == filterList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(filterList.get(i));
            }
            dataMap.put("result",resList);
            return dataMap;
        }else if (roleId.equals("2022220230003")){//学校教务管理人员
            if (dataList.size() == 0){
                dataMap.put("data","本学期所有课程均已结束");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= dataList.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<dataList.size();i++){
                if (!dataList.get(i).getTrueName().equals(trueName)){
                    filterList.add(dataList.get(i));
                }
            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == filterList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(filterList.get(i));
            }
            dataMap.put("result",resList);
            return dataMap;
        }else if (roleId.equals("2022220230004")){//学院教务管理人员
            if (dataList.size() == 0){
                dataMap.put("data","本学期所有课程均已结束");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= dataList.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<dataList.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(dataList.get(i).getInstituteName())){
                        if (!dataList.get(i).getTrueName().equals(trueName)){
                            filterList.add(dataList.get(i));
                        }
                    }
                }

            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == filterList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(filterList.get(i));
            }
            dataMap.put("result",resList);
            return dataMap;
        }else if (roleId.equals("2022220230005")){//学校督导
            if (dataList.size() == 0){
                dataMap.put("data","本学期所有课程均已结束");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= dataList.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<dataList.size();i++){
                if (!dataList.get(i).getTrueName().equals(trueName)){
                    filterList.add(dataList.get(i));
                }
            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == filterList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(filterList.get(i));
            }
            dataMap.put("result",resList);
            return dataMap;
        }else if (roleId.equals("2022220230006")){//学院督导
            if (dataList.size() == 0){
                dataMap.put("data","本学期所有课程均已结束");
                return JsonUtil.success(dataMap);
            }
            if (((pageNo-1) * pageNum) >= dataList.size()){
                dataMap.put("data",dataList);
                return JsonUtil.success(dataMap);
            }
            for(int i = 0;i<dataList.size();i++){
                for(int j = 0;j<instituteList.size();j++){
                    if (instituteList.get(j).equals(dataList.get(i).getInstituteName())){
                        if (!dataList.get(i).getTrueName().equals(trueName)){
                            filterList.add(dataList.get(i));
                        }
                    }
                }

            }
            for(int i = ((pageNo - 1) * pageNum);i<pageNum*pageNo;i++){
                if (i == filterList.size()){
                    break;
                }
                //过滤掉老师所带课程,以及其他学院的课程
                resList.add(filterList.get(i));
            }
            dataMap.put("result",resList);
            return dataMap;
        }else if (roleId.equals("2022220230007")){//辅导员
            return dataMap;
        }
        return dataMap;
    }
    public Task getTaskInfo(Task task){
        return userMapper.getTaskInfo(task);
    }

    public Map<String,Object> getRecord(String userName,String roleId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<Record> dataList = new ArrayList<Record>();
        List<Record> records = null;
        if (roleId.equals("2022220230001")){
            records = courseMapper.getListenRecord(userName);
        }else if (roleId.equals("2022220230003") || roleId.equals("2022220230004")){
            records = courseMapper.getTourRecord(userName);
        }else if (roleId.equals("2022220230005") || roleId.equals("2022220230006")){
            records = courseMapper.getEvaluationRecord(userName);
        }
        for(int i = 0;i<records.size();i++){
            dataList.add(records.get(i));
        }
        dataMap.put("result",dataList);
        return dataMap;
    }
    public boolean findRecord(String userName,String roleId,String courseSlaveId){
        List<Record> records = null;
        if (roleId.equals("2022220230001")){
            records = courseMapper.getListenRecord(userName);
        }else if (roleId.equals("2022220230003") || roleId.equals("2022220230004")){
            records = courseMapper.getTourRecord(userName);
        }else if (roleId.equals("2022220230005") || roleId.equals("2022220230006")){
            records = courseMapper.getEvaluationRecord(userName);
        }
        for(int i = 0;i<records.size();i++){
            if (records.get(i).getCourseSlaveId().equals(courseSlaveId)){
                return true;
            }
        }
        return false;
    }
    public DateTime getCourseDateTime(String courseSlaveId){
        return courseMapper.getCourseDateTime(courseSlaveId);
    }
    public ListenCourse getListenCourseInfo(ListenCourse listenCourse){
        return courseMapper.getListenCourseInfo(listenCourse);
    }
    public TourCourse getTourCourseInfo(TourCourse tourCourse){
        return courseMapper.getTourCourseInfo(tourCourse);
    }
    public EvaluationCourse getEvaluationCourseInfo(EvaluationCourse evaluationCourse){
        return courseMapper.getEvaluationCourseInfo(evaluationCourse);
    }
}
