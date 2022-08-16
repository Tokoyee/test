package com.aiit.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @ApiModelProperty("openid")
    public String touser;
    @ApiModelProperty("模板id")
    public String template_id;
    @ApiModelProperty("详情页")
    public String page;
    @ApiModelProperty("版本")
    public String miniprogram_state;
    @ApiModelProperty("语言")
    public String lang;
    @ApiModelProperty("数据")
    public Map<String,Object> data;
}
