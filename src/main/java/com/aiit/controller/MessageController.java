package com.aiit.controller;

import com.aiit.service.MessageService;
import com.aiit.util.JsonUtil;
import com.aiit.util.JwtUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
@Api(tags = "消息模块")
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;
    @ApiOperation(value = "获取文章类型",notes = "获取文章类型列表")
    @Cacheable("getArticleType")
    @GetMapping("/getArticleType")
    @CrossOrigin
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = "")
    })
    public Map<String,Object> getArticleType(@RequestParam(value = "token") @ApiParam("token") String token){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            return JsonUtil.success(messageService.getArticleType());
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @ApiOperation(value = "获取文章列表",notes = "根据文章类型获取相应文章列表")
    @Cacheable("getArticleList")
    @GetMapping("/getArticleList")
    @CrossOrigin
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "articleType",
                    value = "文章类型", required = true, defaultValue = "")
    })
    public Map<String,Object> getArticleList(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "articleType") @ApiParam("文章类型") String articleType){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            return JsonUtil.success(messageService.getArticleList(articleType));
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
    @ApiOperation(value = "获取文章内容",notes = "根据文章编号获取相应文章内容")
    @Cacheable("getArticleInclude")
    @GetMapping("/getArticleInclude")
    @CrossOrigin
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token",
                    value = "token", required = true, defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "id",
                    value = "文章编号", required = true, defaultValue = "")
    })
    public Map<String,Object> getArticleInclude(@RequestParam(value = "token") @ApiParam("token") String token,@RequestParam(value = "id") @ApiParam("文章编号") int id){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        if (JwtUtil.verify(token) != null){
            String userName = JwtUtil.verify(token);
            return JsonUtil.success(messageService.getArticleInclude(id));
        }else {
            return JsonUtil.token_error(dataMap);
        }
    }
}
