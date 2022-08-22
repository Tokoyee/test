package com.aiit.controller;

import com.aiit.entity.*;
import com.aiit.service.CourseService;
import com.aiit.service.UserService;
import com.aiit.util.JsonUtil;
import com.aiit.util.JwtUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.Cacheable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
@Api(tags = "用户模块")
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @GetMapping("/getUserInfo")
    @Cacheable("getUserInfo")
    @CrossOrigin
    @ApiOperation(value = "获取用户信息",notes = "确保用户名和密码正确，会返回该用户的全部信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userName",
                    value = "用户名", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "password",
                    value = "密码", required = true, defaultValue = "")
    })
    public Map<String,Object> getUserInfo(@RequestParam(name = "userName") @ApiParam("用户名") String userName, @RequestParam(name = "password") @ApiParam("密码") String password){
        Map<String,Object> dataMap = userService.getUserInfo(userName,password);
        return dataMap;
    }
    @Value("/www/server/tomcat/webapps/userImg/")
//    @Value("E:\\QQ音乐\\")
    public String upLoadInvoiceImg;
    @CrossOrigin
    @ApiOperation(value = "用户上传头像",notes = "需要同时提供图片和用户两个参数")
    @PostMapping("/upLoadInvoiceImg")
    @ResponseBody
    public Map<String,Object> upLoadAgeImg(@RequestParam(value = "file") @ApiParam("头像图片") MultipartFile file, @RequestParam(value = "userName") @ApiParam("用户名") String userName) {
        Map<String,Object> dataMap = new HashMap<String,Object>();
        try {
            if (file != null){
                String fileName = System.currentTimeMillis()+file.getOriginalFilename();
                String upload_file_dir = upLoadInvoiceImg;
                String destFileName = upLoadInvoiceImg + fileName;
                File upload_file_dir_file = new File(upload_file_dir);
                if (!upload_file_dir_file.exists()){
                    upload_file_dir_file.mkdirs();
                }
                File targetFile = new File(upload_file_dir_file,fileName);
                file.transferTo(targetFile);
                System.out.println("上传了："+destFileName);
                dataMap.put("resp",fileName);
                User user = userService.getUser(userName);
                user.setImgUrl(fileName);
                userService.updateImgName(user);
                String serverImgUrl = "http://aiitbeidou.cn:8080/userImg/"+ fileName;
                dataMap.put("imgUrl",serverImgUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        dataMap.put("result","上传成功");

        return JsonUtil.success(dataMap);
    }
    /*
    添加用户
     */
    @CrossOrigin
    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户",notes = "确保用户名不存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userName",
                    value = "用户名", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "trueName",
                    value = "真实姓名", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "role",
                    value = "用户角色", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "authorDescribe",
                    value = "权限描述", required = true, defaultValue = "")
    })
    public String addUser(@RequestParam(value = "userName") @ApiParam("用户名") String userName,@RequestParam(value = "trueName") @ApiParam("真实姓名") String trueName,@RequestParam(value = "role") @ApiParam("用户角色") String role,@RequestParam(value = "authorDescribe") @ApiParam("权限描述") String authorDescribe) throws Exception {
        UserInfo user = new UserInfo();
        user.setUserName(userName);
        user.setTrueName(trueName);
        String pwd = "111111";
        user.setPassword(pwd);
        user.setRoleName(role);
        user.setImgUrl("1658983108142user.png");
        userService.addUser(user);
        return "添加成功";
    }
    /*
    获取openid
     */
    @GetMapping("/code2Session")
    @CrossOrigin
    @ApiOperation(value = "获取openid",notes = "确保js_code正确")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "js_code",
                    value = "获取openid必须参", required = true, defaultValue = "")
    })
    public HashMap<String,Object> code2Session(@RequestParam(value = "js_code",required = true) @ApiParam("获取openid必须参") String js_code){
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxd6a69cf9d408a010&secret=2ace9c7abb9119357fb54b2cc60d4c91&js_code="+js_code+"&grant_type=authorization_code";
        // 请求头设置,x-www-form-urlencoded格式的数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //第一次请求，获取accessToken
        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("appid", "wxd6a69cf9d408a010");
//        map.add("secret", "2ace9c7abb9119357fb54b2cc60d4c91");
//        map.add("js_code", js_code);
//        map.add("grant_type", "authorization_code");

        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        // 发送post请求，并打印结果，以String类型接收响应结果JSON字符串
        String result = restTemplate.postForObject(url, request, String.class);
        log.info(result);
        HashMap<String,Object> resultMap = new HashMap<String,Object>();
        JSONObject dataInfo = JSON.parseObject(result);
        String session_key = dataInfo.getString("session_key");
        String openid = dataInfo.getString("openid");
        resultMap.put("session_key",session_key);
        resultMap.put("openid",openid);
        dataMap.put("result",resultMap);
        return JsonUtil.success(dataMap);
    }
    /*
    添加订阅
     */
    @GetMapping("/addSubscription")
    @CrossOrigin
    @ApiOperation(value = "订阅消息",notes = "订阅消息->消息推送")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "touser",
                    value = "openid", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "courseSlaveId",
                    value = "课程子表编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = "")
    })
    public HashMap<String,Object> addSubscription(@RequestParam(value = "touser") @ApiParam("openid") String touser,@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "courseSlaveId") @ApiParam("课程子表编号") String courseSlaveId,@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId){
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            Subscription subscription = new Subscription();
            subscription.setUserName(userName);
            subscription.setRoleId(roleId);
            subscription.setTouser(touser);
            subscription.setCourseSlaveId(courseSlaveId);
            DateTime dateTime = courseService.getCourseDateTime(courseSlaveId);
            String remindDate = dateTime.getDate();
            int remindTime = Integer.parseInt(dateTime.getTime().split("-")[0].split(":")[0]) * 60 + Integer.parseInt(dateTime.getTime().split("-")[0].split(":")[1]) - 30;  //提前半小时
            subscription.setRemindDate(remindDate);
            subscription.setRemindTime(remindTime);
            List<Subscription> subscriptions = userService.getAllSubscription();
            String subscriptionId = "";
            if (subscriptions.size() != 0){
                Long id = Long.parseLong(subscriptions.get(subscriptions.size()-1).getSubscriptionId()) + 1;
                subscriptionId = id.toString();
            }else {
                subscriptionId = "2022220230001";
            }
            subscription.setSubscriptionId(subscriptionId);
            dataMap.put("subscriptionId",subscriptionId);
            userService.addSubscription(subscription);
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    /*
    取消订阅
     */
    @GetMapping("/deleteSubscription")
    @CrossOrigin
    @ApiOperation(value = "取消订阅",notes = "取消订阅")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "courseSlaveId",
                    value = "课程子表编号", required = true, defaultValue = "")
    })
    public HashMap<String,Object> deleteSubscription(@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId,@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "courseSlaveId") @ApiParam("课程子表编号") String courseSlaveId){
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            userService.deleteSubscription(new RoleSubscription(roleId,userName,courseSlaveId));
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    /*
    获取订阅列表
     */
    @GetMapping("/getSubscriptionList")
    @CrossOrigin
    @ApiOperation(value = "获取订阅列表",notes = "获取订阅列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "roleId",
                    value = "角色编号", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = "")
    })
    public HashMap<String,Object> getSubscriptionList(@RequestParam(value = "roleId") @ApiParam("角色编号") String roleId,@RequestParam(value = "token") @ApiParam("token") String token){
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            dataMap.put("dataList",userService.getSubscriptionWithRole(new UserSubscription(userName,roleId)));
            return JsonUtil.success(dataMap);
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
}
