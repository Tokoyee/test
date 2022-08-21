package com.aiit.controller;

import com.aiit.entity.*;
import com.aiit.service.CourseService;
import com.aiit.util.JsonUtil;
import com.aiit.util.JwtUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.Cacheable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@Slf4j
@Api(tags = "课程模块")
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;
    /*
    获取教室信息
     */
//    @GetMapping("/getClassroomInfo")
//    @CrossOrigin
//    @ApiOperation(value = "获取智慧教室信息",notes = "返回该教室的信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "classRoomName",
//                    value = "教室名称", required = false, defaultValue = "")
//    })
//    public Map<String,Object> getClassroomInfo(@RequestParam(value = "searchText",required = false,defaultValue = "") @ApiParam("搜索文本") String searchText){
//        if (searchText.equals("")){
//            return classSevice.getAllClassroomInfo();
//        }
//        return classSevice.getOneClassroomInfo(searchText);
//    }
    /*
    提供各学院的老师名称数据
     */
    @ApiOperation(value = "获取老师列表",notes = "获取各学院的老师列表")
    @Cacheable("getInstitudeTeacher")
    @GetMapping("/getInstitudeTeacher")
    @CrossOrigin
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = "")
    })
    public Map<String,Object> getInstitudeTeacher(@RequestParam(value = "token") @ApiParam("token") String token){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            return courseService.getTeacherList();
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    /*
    获取当前日期的课程
     */
    @GetMapping("/getNowCourse")
    @Cacheable("getNowCourse")
    @CrossOrigin
    @ApiOperation(value = "获取当前课程信息",notes = "获取当前正在上课的课程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageNum",
                    value = "页数据量", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageNo",
                    value = "页码", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = "")
    })
    public Map<String,Object> getNowCourse(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "pageNum") @ApiParam("页数据量") int pageNum,@RequestParam(value = "pageNo") @ApiParam("页码") int pageNo,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            return courseService.getNowCourse(pageNum,pageNo,userName,roleId);
        }else {
         return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/getEvaluationStandardList")
    @Cacheable("getEvaluationStandardList")
    @CrossOrigin
    @ApiOperation(value = "获取评课指标列表",notes = "获取评课指标列表的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = "")
    })
    public Map<String,Object> getEvaluationStandardList(@RequestParam(value = "token") @ApiParam("token") String token){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            dataMap.put("dataList",courseService.getEvaluationStandardList());
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/getTourStandardList")
    @Cacheable("getTourStandardList")
    @CrossOrigin
    @ApiOperation(value = "获取巡课指标列表",notes = "获取巡课指标列表的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = "")
    })
    public Map<String,Object> getTourStandardList(@RequestParam(value = "token") @ApiParam("token") String token){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            dataMap.put("dataList",courseService.getTourStandardList());
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/getListenStandardList")
    @Cacheable("getListenStandardList")
    @CrossOrigin
    @ApiOperation(value = "获取听课指标列表",notes = "获取听课指标列表的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = "")
    })
    public Map<String,Object> getListenStandardList(@RequestParam(value = "token") @ApiParam("token") String token){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            dataMap.put("dataList",courseService.getListenStandardList());
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/addListenCourseInfo")
    @Cacheable("addListenCourseInfo")
    @CrossOrigin
    @ApiOperation(value = "教师听课信息录入",notes = "教师听课后的信息录入")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "attitudeScore",
                    value = "教学态度得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "includeScore",
                    value = "教学内容得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "methodScore",
                    value = "教学方法得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "effectScore",
                    value = "教学效果得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "include",
                    value = "听课内容", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "advice",
                    value = "评价及建议", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "courseSlaveId",
                    value = "课程子表编号", required = true, defaultValue = "")
    })
    public Map<String,Object> addListenCourseInfo(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId,@RequestParam(value = "attitudeScore") @ApiParam("教学态度得分") int attitudeScore,@RequestParam(value = "includeScore") @ApiParam("教学内容得分") int includeScore,@RequestParam(value = "methodScore") @ApiParam("教学方法得分") int methodScore,@RequestParam(value = "effectScore") @ApiParam("教学效果得分") int effectScore,@RequestParam(value = "include") @ApiParam("听课内容") String include,@RequestParam(value = "advice") @ApiParam("评价及建议") String advice,@RequestParam(value = "courseSlaveId") @ApiParam("课程子表编号") String courseSlaveId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            if (courseService.findRecord(userName,roleId,courseSlaveId)){
                return JsonUtil.record_error(dataMap);
            }
            Task task = new Task();
            task.setTaskDescribe("听课");
            task.setUserName(userName);
            Task taskInfo = courseService.getTaskInfo(task);
            int taskNum = taskInfo.getTaskNum();
            taskNum = taskNum - 1;
            String taskStatus = "未完成";
            if (taskNum <= 0){
                taskNum = 0;
                taskStatus = "已完成";
            }
            task.setTaskStatus(taskStatus);
            task.setUserName(userName);
            ListenCourse listenCourse = new ListenCourse();
            listenCourse.setAttitudeScore(attitudeScore);
            listenCourse.setIncludeScore(includeScore);
            listenCourse.setMethodScore(methodScore);
            listenCourse.setEffectScore(effectScore);
            listenCourse.setTotalScore(attitudeScore + includeScore + methodScore + effectScore);
            listenCourse.setInclude(include);
            listenCourse.setAdvice(advice);
            listenCourse.setCourseSlaveId(courseSlaveId);
            listenCourse.setUserName(userName);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String now_datetime = formatter.format(date);
            listenCourse.setDateTime(now_datetime);
            listenCourse.setAttitudeScore(attitudeScore);
            listenCourse.setIncludeScore(includeScore);
            listenCourse.setMethodScore(methodScore);
            listenCourse.setEffectScore(effectScore);
            listenCourse.setTotalScore(attitudeScore+includeScore+methodScore+effectScore);
            courseService.addListenCourseInfo(listenCourse,task);
            dataMap.put("result","添加成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/deleteEvaluationStandard")
    @Cacheable("deleteEvaluationStandard")
    @CrossOrigin
    @ApiOperation(value = "删除评课指标",notes = "删除评课指标列表中的一个")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardId",
                    value = "评课指标编号", required = true, defaultValue = "")
    })
    public Map<String,Object> deleteEvaluationStandard(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "standardId") @ApiParam("评课指标编号") String standardId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            courseService.deleteEvaluationStandard(standardId);
            dataMap.put("result","删除成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/deleteTourStandard")
    @Cacheable("deleteTourStandard")
    @CrossOrigin
    @ApiOperation(value = "删除巡课指标",notes = "删除巡课指标列表中的一个")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardId",
                    value = "巡课指标编号", required = true, defaultValue = "")
    })
    public Map<String,Object> deleteTourStandard(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "standardId") @ApiParam("巡课指标编号") String standardId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            courseService.deleteTourStandard(standardId);
            dataMap.put("result","删除成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/deleteListenStandard")
    @Cacheable("deleteListenStandard")
    @CrossOrigin
    @ApiOperation(value = "删除听课指标",notes = "删除听课指标列表中的一个")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardId",
                    value = "听课指标编号", required = true, defaultValue = "")
    })
    public Map<String,Object> deleteListenStandard(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "standardId") @ApiParam("听课指标编号") String standardId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            courseService.deleteListenStandard(standardId);
            dataMap.put("result","删除成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/addEvaluationStandard")
    @Cacheable("addEvaluationStandard")
    @CrossOrigin
    @ApiOperation(value = "添加评课指标",notes = "新增一个评课指标")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardType",
                    value = "评课指标类型", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardInclude",
                    value = "评课指标内容", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardWeight",
                    value = "评课指标权重", required = true, defaultValue = "")
    })
    public Map<String,Object> addEvaluationStandard(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "standardType") @ApiParam("评课指标类型") String standardType,@RequestParam(value = "standardInclude") @ApiParam("评课指标内容") String standardInclude,@RequestParam(value = "standardWeight") @ApiParam("评课指标权重") String standardWeight){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            List<ScoreStandard> scoreStandardList = courseService.getEvaluationStandardList();
            Long standardId = Long.parseLong(scoreStandardList.get(scoreStandardList.size()-1).getStandardId()) + 1;
            ScoreStandard scoreStandard = new ScoreStandard();
            scoreStandard.setStandardId(standardId.toString());
            scoreStandard.setStandardType(standardType);
            scoreStandard.setStandardInclude(standardInclude);
            scoreStandard.setStandardWeight(Float.parseFloat(standardWeight));
            courseService.addEvaluationStandard(scoreStandard);
            dataMap.put("result","添加成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/addTourStandard")
    @Cacheable("addTourStandard")
    @CrossOrigin
    @ApiOperation(value = "添加巡课指标",notes = "新增一个巡课指标")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardType",
                    value = "巡课指标类型", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardInclude",
                    value = "巡课指标内容", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardWeight",
                    value = "巡课指标权重", required = true, defaultValue = "")
    })
    public Map<String,Object> addTourStandard(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "standardType") @ApiParam("巡课指标类型") String standardType,@RequestParam(value = "standardInclude") @ApiParam("巡课指标内容") String standardInclude,@RequestParam(value = "standardWeight") @ApiParam("巡课指标权重") String standardWeight){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            List<ScoreStandard> scoreStandardList = courseService.getTourStandardList();
            Long standardId = Long.parseLong(scoreStandardList.get(scoreStandardList.size()-1).getStandardId()) + 1;
            ScoreStandard scoreStandard = new ScoreStandard();
            scoreStandard.setStandardId(standardId.toString());
            scoreStandard.setStandardType(standardType);
            scoreStandard.setStandardInclude(standardInclude);
            scoreStandard.setStandardWeight(Float.parseFloat(standardWeight));
            courseService.addTourStandard(scoreStandard);
            dataMap.put("result","添加成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/addListenStandard")
    @Cacheable("addListenStandard")
    @CrossOrigin
    @ApiOperation(value = "添加听课指标",notes = "新增一个听课指标")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardType",
                    value = "听课指标类型", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardInclude",
                    value = "听课指标内容", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardWeight",
                    value = "听课指标权重", required = true, defaultValue = "")
    })
    public Map<String,Object> addListenStandard(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "standardType") @ApiParam("听课指标类型") String standardType,@RequestParam(value = "standardInclude") @ApiParam("听课指标内容") String standardInclude,@RequestParam(value = "standardWeight") @ApiParam("听课指标权重") String standardWeight){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            List<ScoreStandard> scoreStandardList = courseService.getListenStandardList();
            Long standardId = Long.parseLong(scoreStandardList.get(scoreStandardList.size()-1).getStandardId()) + 1;
            ScoreStandard scoreStandard = new ScoreStandard();
            scoreStandard.setStandardId(standardId.toString());
            scoreStandard.setStandardType(standardType);
            scoreStandard.setStandardInclude(standardInclude);
            scoreStandard.setStandardWeight(Float.parseFloat(standardWeight));
            courseService.addListenStandard(scoreStandard);
            dataMap.put("result","添加成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/updateEvaluationStandard")
    @Cacheable("updateEvaluationStandard")
    @CrossOrigin
    @ApiOperation(value = "更新评课指标",notes = "更新评课指标的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardId",
                    value = "评课指标编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardType",
                    value = "评课指标类型", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardInclude",
                    value = "评课指标内容", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardWeight",
                    value = "评课指标权重", required = true, defaultValue = "")
    })
    public Map<String,Object> updateEvaluationStandard(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "standardId") @ApiParam("评课指标编号") String standardId,@RequestParam(value = "standardType") @ApiParam("评课指标类型") String standardType,@RequestParam(value = "standardInclude") @ApiParam("评课指标内容") String standardInclude,@RequestParam(value = "standardWeight") @ApiParam("评课指标权重") String standardWeight){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            ScoreStandard scoreStandard = new ScoreStandard();
            scoreStandard.setStandardId(standardId);
            scoreStandard.setStandardType(standardType);
            scoreStandard.setStandardInclude(standardInclude);
            scoreStandard.setStandardWeight(Float.parseFloat(standardWeight));
            courseService.updateEvaluationStandard(scoreStandard);
            dataMap.put("result","更新成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/updateTourStandard")
    @Cacheable("updateTourStandard")
    @CrossOrigin
    @ApiOperation(value = "更新巡课指标",notes = "更新巡课指标的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardId",
                    value = "巡课指标编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardType",
                    value = "巡课指标类型", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardInclude",
                    value = "巡课指标内容", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardWeight",
                    value = "巡课指标权重", required = true, defaultValue = "")
    })
    public Map<String,Object> updateTourStandard(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "standardId") @ApiParam("巡课指标编号") String standardId,@RequestParam(value = "standardType") @ApiParam("巡课指标类型") String standardType,@RequestParam(value = "standardInclude") @ApiParam("巡课指标内容") String standardInclude,@RequestParam(value = "standardWeight") @ApiParam("巡课指标权重") String standardWeight){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            ScoreStandard scoreStandard = new ScoreStandard();
            scoreStandard.setStandardId(standardId);
            scoreStandard.setStandardType(standardType);
            scoreStandard.setStandardInclude(standardInclude);
            scoreStandard.setStandardWeight(Float.parseFloat(standardWeight));
            courseService.updateTourStandard(scoreStandard);
            dataMap.put("result","更新成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/updateListenStandard")
    @Cacheable("updateListenStandard")
    @CrossOrigin
    @ApiOperation(value = "更新听课指标",notes = "更新听课指标的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardId",
                    value = "听课指标编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardType",
                    value = "听课指标类型", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardInclude",
                    value = "听课指标内容", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "standardWeight",
                    value = "听课指标权重", required = true, defaultValue = "")
    })
    public Map<String,Object> updateListenStandard(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "standardId") @ApiParam("听课指标编号") String standardId,@RequestParam(value = "standardType") @ApiParam("听课指标类型") String standardType,@RequestParam(value = "standardInclude") @ApiParam("听课指标内容") String standardInclude,@RequestParam(value = "standardWeight") @ApiParam("听课指标权重") String standardWeight){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            ScoreStandard scoreStandard = new ScoreStandard();
            scoreStandard.setStandardId(standardId);
            scoreStandard.setStandardType(standardType);
            scoreStandard.setStandardInclude(standardInclude);
            scoreStandard.setStandardWeight(Float.parseFloat(standardWeight));
            courseService.updateListenStandard(scoreStandard);
            dataMap.put("result","更新成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/addEvaluationCourseInfo")
    @Cacheable("addEvaluationCourseInfo")
    @CrossOrigin
    @ApiOperation(value = "添加评课记录",notes = "评课信息的记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "attitudeScore",
                    value = "教学态度得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "includeScore",
                    value = "教学内容得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "methodScore",
                    value = "教学方法得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "effectScore",
                    value = "教学效果得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "advice",
                    value = "评价及建议", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "courseSlaveId",
                    value = "课程子表编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "remark",
                    value = "备注", required = true, defaultValue = "")
    })
    public Map<String,Object> addEvaluationCourseInfo(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId,@RequestParam(value = "attitudeScore") @ApiParam("教学态度得分") int attitudeScore,@RequestParam(value = "includeScore") @ApiParam("教学内容得分") int includeScore,@RequestParam(value = "methodScore") @ApiParam("教学方法得分") int methodScore,@RequestParam(value = "effectScore") @ApiParam("教学效果得分") int effectScore,@RequestParam(value = "advice") @ApiParam("评价及建议") String advice,@RequestParam(value = "courseSlaveId") @ApiParam("课程子表编号") String courseSlaveId,@RequestParam(value = "remark") @ApiParam("备注") String remark){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            if (courseService.findRecord(userName,roleId,courseSlaveId)){
                return JsonUtil.record_error(dataMap);
            }
            EvaluationCourse evaluationCourse = new EvaluationCourse();
            evaluationCourse.setAttitudeScore(attitudeScore);
            evaluationCourse.setIncludeScore(includeScore);
            evaluationCourse.setMethodScore(methodScore);
            evaluationCourse.setEffectScore(effectScore);
            evaluationCourse.setTotalScore(attitudeScore+includeScore+methodScore+effectScore);
            evaluationCourse.setAdvice(advice);
            evaluationCourse.setCourseSlaveId(courseSlaveId);
            evaluationCourse.setUserName(userName);
            evaluationCourse.setRemark(remark);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String now_datetime = formatter.format(date);
            evaluationCourse.setDateTime(now_datetime);
            Task task = new Task();
            task.setTaskDescribe("评课");
            task.setUserName(userName);
            Task taskInfo = courseService.getTaskInfo(task);
            int taskNum = taskInfo.getTaskNum();
            taskNum = taskNum - 1;
            String taskStatus = "未完成";
            if (taskNum <= 0){
                taskNum = 0;
                taskStatus = "已完成";
            }
            task.setTaskStatus(taskStatus);
            courseService.addEvaluationCourseInfo(evaluationCourse,task);
            dataMap.put("result","添加成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/addTourCourseInfo")
    @Cacheable("addTourCourseInfo")
    @CrossOrigin
    @ApiOperation(value = "添加巡课记录",notes = "巡课信息的记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "attitudeScore",
                    value = "教学态度得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "includeScore",
                    value = "教学内容得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "methodScore",
                    value = "教学方法得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "effectScore",
                    value = "教学效果得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "attendance",
                    value = "出勤率", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "riseRate",
                    value = "抬头率", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "advice",
                    value = "评价及建议", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "courseSlaveId",
                    value = "课程子表编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "remark",
                    value = "备注", required = true, defaultValue = "")
    })
    public Map<String,Object> addTourCourseInfo(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId,@RequestParam(value = "attitudeScore") @ApiParam("教学态度得分") int attitudeScore,@RequestParam(value = "includeScore") @ApiParam("教学内容得分") int includeScore,@RequestParam(value = "methodScore") @ApiParam("教学方法得分") int methodScore,@RequestParam(value = "effectScore") @ApiParam("教学效果得分") int effectScore,@RequestParam(value = "attendance") @ApiParam("出勤率") float attendance,@RequestParam(value = "riseRate") @ApiParam("抬头率") float riseRate,@RequestParam(value = "advice") @ApiParam("评价及建议") String advice,@RequestParam(value = "courseSlaveId") @ApiParam("课程子表编号") String courseSlaveId,@RequestParam(value = "remark") @ApiParam("备注") String remark){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            if (courseService.findRecord(userName,roleId,courseSlaveId)){
                return JsonUtil.record_error(dataMap);
            }
            TourCourse tourCourse = new TourCourse();
            tourCourse.setAttitudeScore(attitudeScore);
            tourCourse.setIncludeScore(includeScore);
            tourCourse.setMethodScore(methodScore);
            tourCourse.setEffectScore(effectScore);
            tourCourse.setTotalScore(attitudeScore+includeScore+methodScore+effectScore);
            tourCourse.setAttendance(attendance);
            tourCourse.setRiseRate(riseRate);
            tourCourse.setAdvice(advice);
            tourCourse.setCourseSlaveId(courseSlaveId);
            tourCourse.setUserName(userName);
            tourCourse.setRemark(remark);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String now_datetime = formatter.format(date);
            tourCourse.setDateTime(now_datetime);
            Task task = new Task();
            task.setTaskDescribe("巡课");
            task.setUserName(userName);
            Task taskInfo = courseService.getTaskInfo(task);
            int taskNum = taskInfo.getTaskNum();
            taskNum = taskNum - 1;
            String taskStatus = "未完成";
            if (taskNum <= 0){
                taskNum = 0;
                taskStatus = "已完成";
            }
            task.setTaskStatus(taskStatus);
            courseService.addTourCourseInfo(tourCourse,task);
            dataMap.put("result","添加成功");
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/getInstituteCourse")
    @Cacheable("getInstituteCourse")
    @CrossOrigin
    @ApiOperation(value = "获取学院课程",notes = "根据学院名称获取学院内课程")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = false, defaultValue = "")
    })
    public Map<String,Object> getInstituteCourse(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            return JsonUtil.success(courseService.getInstituteInfo(userName,roleId));
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/getTeacherCourse")
    @Cacheable("getTeacherCourse")
    @CrossOrigin
    @ApiOperation(value = "获取教师所授课程",notes = "根据教师名称获取教师所授课程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = "")
    })
    public Map<String,Object> getTeacherCourse(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            return JsonUtil.success(courseService.getTeacherCourseInfo(userName,roleId));
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/getCourseInfoWithText")
    @Cacheable("getCourseInfoWithText")
    @CrossOrigin
    @ApiOperation(value = "搜索课程",notes = "根据关键字搜索相关课程")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "searchText",
                    value = "关键字", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageNo",
                    value = "页码", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageNum",
                    value = "页数据量", required = true, defaultValue = "")
    })
    public Map<String,Object> getCourseInfoWithText(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "searchText") @ApiParam("关键字") String searchText,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId,@RequestParam(value = "pageNo") @ApiParam("页码") int pageNo,@RequestParam(value = "pageNum") @ApiParam("页数据量") int pageNum){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            dataMap.put("resList",courseService.getCourseInfoWithText(userName,searchText,roleId,pageNo,pageNum));
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/getNotStartCourse")
    @Cacheable("getNotStartCourse")
    @CrossOrigin
    @ApiOperation(value = "预约课程",notes = "预约功能对应的课程列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageNo",
                    value = "页码", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageNum",
                    value = "页数据量", required = true, defaultValue = "")
    })
    public Map<String,Object> getNotStartCourse(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId,@RequestParam(value = "pageNo") @ApiParam("页码") int pageNo,@RequestParam(value = "pageNum") @ApiParam("页数据量") int pageNum){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            return JsonUtil.success(courseService.getNotStartCourse(userName,roleId,pageNo,pageNum));
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @GetMapping("/searchNotStartCourse")
    @Cacheable("searchNotStartCourse")
    @CrossOrigin
    @ApiOperation(value = "搜索预约课程",notes = "根据关键字搜索要预约的课程")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "searchText",
                    value = "关键字", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageNo",
                    value = "页码", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "pageNum",
                    value = "页数据量", required = true, defaultValue = "")
    })
    public Map<String,Object> searchNotStartCourse(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "searchText") @ApiParam("关键字") String searchText,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId,@RequestParam(value = "pageNo") @ApiParam("页码") int pageNo,@RequestParam(value = "pageNum") @ApiParam("页数据量") int pageNum){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            dataMap.put("resList",courseService.searchNotStartCourse(userName,searchText,roleId,pageNo,pageNum));
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @ApiOperation(value = "老师角色雷达图",notes = "老师角色雷达图")
    @GetMapping("/getTeacherRadar")
    @CrossOrigin
    public Map<String,Object> getTeacherRadar(){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<Object> dataList = new ArrayList<Object>();
        String[] typeList = new String[]{"睡觉", "吃东西", "玩手机", "站立", "书写", "看黑板","举手"};
        dataMap.put("categories",typeList);
        Map<String,Object> infoMap1 = new HashMap<String,Object>();
        int[] numList1 = new int[]{8,3,18,2,17,45,1};
        infoMap1.put("name","课程");
        infoMap1.put("data",numList1);
        dataList.add(infoMap1);
        Map<String,Object> infoMap2 = new HashMap<String,Object>();
        int[] numList2 = new int[]{5,2,11,2,22,43,1};
        infoMap2.put("name","平均");
        infoMap2.put("data",numList2);
        dataList.add(infoMap2);
        dataMap.put("dataList",dataList);
        return JsonUtil.success(dataMap);
    }
    @ApiOperation(value = "老师角色柱状图",notes = "老师角色柱状图")
    @GetMapping("/getTeacherBar")
    @CrossOrigin
    public Map<String,Object> getTeacherBar(){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<Object> dataList = new ArrayList<Object>();
        String[] typeList = new String[]{"09-12", "09-13", "09-14", "09-15", "09-16", "09-19","09-20"};
        dataMap.put("categories",typeList);
        Map<String,Object> infoMap1 = new HashMap<String,Object>();
        int[] numList1 = new int[]{98,96,98,94,95,98,92};
        infoMap1.put("name","课程");
        infoMap1.put("data",numList1);
        dataList.add(infoMap1);
        Map<String,Object> infoMap2 = new HashMap<String,Object>();
        int[] numList2 = new int[]{99,97,96,98,98,99,96};
        infoMap2.put("name","平均");
        infoMap2.put("data",numList2);
        dataList.add(infoMap2);
        dataMap.put("dataList",dataList);
        return JsonUtil.success(dataMap);
    }
    @ApiOperation(value = "课程行为雷达图",notes = "课程行为雷达图")
    @GetMapping("/getCourseRadar")
    @CrossOrigin
    public Map<String,Object> getCourseRadar(){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<Object> dataList = new ArrayList<Object>();
        String[] typeList = new String[]{"计算机思维导论(C语言)", "数据结构与算法", "人工智能导论", "数值计算", "计算机网络基础", "数据分析与可视化","大数据技术导论"};
        dataMap.put("categories",typeList);
        Map<String,Object> infoMap1 = new HashMap<String,Object>();
        int[] numList1 = new int[]{90,93,88,92,87,81,91};
        infoMap1.put("name","得分");
        infoMap1.put("data",numList1);
        dataList.add(infoMap1);
        dataMap.put("dataList",dataList);
        return JsonUtil.success(dataMap);
    }
    @ApiOperation(value = "课程行为折线图",notes = "课程行为折线图")
    @GetMapping("/getCourseLine")
    @CrossOrigin
    public Map<String,Object> getCourseLine(){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<Object> dataList = new ArrayList<Object>();
        String[] typeList = new String[]{"09-12", "09-13", "09-14", "09-15", "09-16", "09-19","09-20"};
        dataMap.put("categories",typeList);
        Map<String,Object> infoMap1 = new HashMap<String,Object>();
        int[] numList1 = new int[]{95,92,96,91,97,93,96};
        infoMap1.put("name","计科2104班");
        infoMap1.put("data",numList1);
        dataList.add(infoMap1);
        Map<String,Object> infoMap2 = new HashMap<String,Object>();
        int[] numList2 = new int[]{94,91,90,93,92,95,96};
        infoMap2.put("name","大数据2101班");
        infoMap2.put("data",numList2);
        dataList.add(infoMap2);
        Map<String,Object> infoMap3 = new HashMap<String,Object>();
        int[] numList3 = new int[]{88,85,81,82,87,91,94};
        infoMap3.put("name","人工智能2102班");
        infoMap3.put("data",numList3);
        dataList.add(infoMap3);
        dataMap.put("dataList",dataList);
        return JsonUtil.success(dataMap);
    }
    @ApiOperation(value = "课程行为柱状图",notes = "课程行为柱状图")
    @GetMapping("/getCourseBar")
    @CrossOrigin
    public Map<String,Object> getCourseBar(){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<Object> dataList = new ArrayList<Object>();
        String[] typeList = new String[]{"09-12", "09-13", "09-14", "09-15", "09-16", "09-19","09-20"};
        dataMap.put("categories",typeList);
        Map<String,Object> infoMap1 = new HashMap<String,Object>();
        int[] numList1 = new int[]{90,93,91,92,97,91,94};
        infoMap1.put("name","出勤率");
        infoMap1.put("data",numList1);
        dataList.add(infoMap1);
        dataMap.put("dataList",dataList);
        return JsonUtil.success(dataMap);
    }
    @GetMapping("/getRecord")
    @Cacheable("getRecord")
    @CrossOrigin
    @ApiOperation(value = "获取记录",notes = "根据角色获取相应的评价记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = ""),
    })
    public Map<String,Object> getRecord(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            return JsonUtil.success(courseService.getRecord(userName,roleId));
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    /*
    随机生成字符串
    length 字符串的长度
     */
    public String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789*/-+=";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
