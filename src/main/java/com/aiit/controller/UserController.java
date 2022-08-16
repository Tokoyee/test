package com.aiit.controller;

import com.aiit.entity.Message;
import com.aiit.entity.User;
import com.aiit.entity.UserInfo;
import com.aiit.service.UserService;
import com.aiit.util.JsonUtil;
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
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
@Api(tags = "用户模块")
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
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
    订阅消息
     */
    @GetMapping("/sendMessage")
    @CrossOrigin
    @ApiOperation(value = "订阅消息",notes = "订阅消息->消息推送")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "touser",
                    value = "openid", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "date1",
                    value = "日期", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "thing4",
                    value = "节次", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "thing6",
                    value = "课程名称", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "thing13",
                    value = "授课老师", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "thing17",
                    value = "班级", required = true, defaultValue = "")
    })
    public HashMap<String,Object> getAccessToken(@RequestParam(value = "touser") @ApiParam("openid") String touser,@RequestParam(value = "date1") @ApiParam("日期") String date1  ,@RequestParam(value = "thing4") @ApiParam("节次") String thing4,@RequestParam(value = "thing6") @ApiParam("课程名称") String thing6,@RequestParam(value = "thing13") @ApiParam("授课教师") String thing13,@RequestParam(value = "thing17") @ApiParam("班级") String thing17){
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxd6a69cf9d408a010&secret=2ace9c7abb9119357fb54b2cc60d4c91";
        // 请求头设置,x-www-form-urlencoded格式的数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //第一次请求，获取accessToken
        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        // 发送post请求，并打印结果，以String类型接收响应结果JSON字符串
        String result = restTemplate.postForObject(url, request, String.class);
        JSONObject dataInfo = JSON.parseObject(result);
        String access_token = dataInfo.getString("access_token");

        //第二次请求订阅消息
        String url2 = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+access_token;
        //提交参数设置
        Message message = new Message();
        message.setTouser(touser);
        message.setTemplate_id("ybmmiRLNhvrJNZm0oEsjgI7CT4YEI1dqXbT5PGsIr2U");
        message.setPage("pages/class/classDetail");
        message.setMiniprogram_state("trial");
        message.setLang("zh_CN");
        HashMap<String,Object> paramData = new HashMap<String,Object>();
        HashMap<String,String> date1Map = new HashMap<String,String>();
        HashMap<String,String> thing4Map = new HashMap<String,String>();
        HashMap<String,String> thing6Map = new HashMap<String,String>();
        HashMap<String,String> thing13Map = new HashMap<String,String>();
        HashMap<String,String> thing17Map = new HashMap<String,String>();
        date1Map.put("value",date1);
        thing4Map.put("value",thing4);
        thing6Map.put("value",thing6);
        thing13Map.put("value",thing13);
        thing17Map.put("value",thing17);
        paramData.put("date1",date1Map);
        paramData.put("thing4",thing4Map);
        paramData.put("thing6",thing6Map);
        paramData.put("thing13",thing13Map);
        paramData.put("thing17",thing17Map);
        message.setData(paramData);
        log.info(String.valueOf(message));
        // 组装请求体
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url2,message,String.class);
        log.info(responseEntity.getBody());
        JSONObject resultMsg = JSON.parseObject(responseEntity.getBody());
        String errmsg = resultMsg.getString("errmsg");
        String errcode = resultMsg.getString("errcode");
        dataMap.put("errmsg",errmsg);
        dataMap.put("errcode",errcode);
        return dataMap;
    }
}
