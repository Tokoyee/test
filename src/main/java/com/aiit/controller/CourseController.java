package com.aiit.controller;

import com.aiit.entity.EvaluationCourse;
import com.aiit.entity.ScoreStandard;
import com.aiit.entity.Task;
import com.aiit.entity.TourCourse;
import com.aiit.service.CourseService;
import com.aiit.util.JsonUtil;
import com.aiit.util.JwtUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

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
            @ApiImplicitParam(paramType = "query", name = "include",
                    value = "听课内容", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "advice",
                    value = "评价及建议", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "courseSlaveId",
                    value = "课程子表编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "taskNum",
                    value = "任务次数", required = true, defaultValue = "")
    })
    public Map<String,Object> addListenCourseInfo(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "include") @ApiParam("听课内容") String include,@RequestParam(value = "advice") @ApiParam("评价及建议") String advice,@RequestParam(value = "courseSlaveId") @ApiParam("课程子表编号") String courseSlaveId,@RequestParam(value = "taskNum") @ApiParam("任务次数") int taskNum){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            taskNum = taskNum - 1;
            String taskStatus = "未完成";
            if (taskNum <= 0){
                taskNum = 0;
                taskStatus = "已完成";
            }
            Task task = new Task();
            task.setTaskNum(taskNum);
            task.setTaskStatus(taskStatus);
            task.setTaskDescribe("听课");
            task.setUserName(userName);
            courseService.addListenCourseInfo(include,advice,courseSlaveId,userName,task);
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
                    value = "备注", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "taskNum",
                    value = "任务次数", required = true, defaultValue = "")
    })
    public Map<String,Object> addEvaluationCourseInfo(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "attitudeScore") @ApiParam("教学态度得分") int attitudeScore,@RequestParam(value = "includeScore") @ApiParam("教学内容得分") int includeScore,@RequestParam(value = "methodScore") @ApiParam("教学方法得分") int methodScore,@RequestParam(value = "effectScore") @ApiParam("教学效果得分") int effectScore,@RequestParam(value = "advice") @ApiParam("评价及建议") String advice,@RequestParam(value = "courseSlaveId") @ApiParam("课程子表编号") String courseSlaveId,@RequestParam(value = "remark") @ApiParam("备注") String remark,@RequestParam(value = "taskNum") @ApiParam("任务次数") int taskNum){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
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
            taskNum = taskNum - 1;
            String taskStatus = "未完成";
            if (taskNum <= 0){
                taskNum = 0;
                taskStatus = "已完成";
            }
            Task task = new Task();
            task.setTaskNum(taskNum);
            task.setTaskDescribe("评课");
            task.setTaskStatus(taskStatus);
            task.setUserName(userName);
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
            @ApiImplicitParam(paramType = "query", name = "attitudeScore",
                    value = "教学态度得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "includeScore",
                    value = "教学内容得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "methodScore",
                    value = "教学方法得分", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "attendance",
                    value = "出勤率", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "riseRate",
                    value = "抬头率", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "advice",
                    value = "评价及建议", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "courseSlaveId",
                    value = "课程子表编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "remark",
                    value = "备注", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "taskNum",
                    value = "任务次数", required = true, defaultValue = ""),
    })
    public Map<String,Object> addTourCourseInfo(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "attitudeScore") @ApiParam("教学态度得分") int attitudeScore,@RequestParam(value = "includeScore") @ApiParam("教学内容得分") int includeScore,@RequestParam(value = "methodScore") @ApiParam("教学方法得分") int methodScore,@RequestParam(value = "attendance") @ApiParam("出勤率") float attendance,@RequestParam(value = "riseRate") @ApiParam("抬头率") float riseRate,@RequestParam(value = "advice") @ApiParam("评价及建议") String advice,@RequestParam(value = "courseSlaveId") @ApiParam("课程子表编号") String courseSlaveId,@RequestParam(value = "remark") @ApiParam("备注") String remark,@RequestParam(value = "taskNum") @ApiParam("任务次数") int taskNum){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            TourCourse tourCourse = new TourCourse();
            tourCourse.setAttitudeScore(attitudeScore);
            tourCourse.setIncludeScore(includeScore);
            tourCourse.setMethodScore(methodScore);
            tourCourse.setTotalScore(attitudeScore+includeScore+methodScore);
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
            taskNum = taskNum - 1;
            String taskStatus = "未完成";
            if (taskNum <= 0){
                taskNum = 0;
                taskStatus = "已完成";
            }
            Task task = new Task();
            task.setTaskNum(taskNum);
            task.setTaskStatus(taskStatus);
            task.setTaskDescribe("巡课");
            task.setUserName(userName);
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
