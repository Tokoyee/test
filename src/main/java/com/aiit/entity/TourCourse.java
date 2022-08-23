package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("巡课信息")
public class TourCourse {
    @ApiModelProperty("主键id")
    public int id;
    @ApiModelProperty("问题1答案")
    public String q1;
    @ApiModelProperty("问题2答案")
    public String q2;
    @ApiModelProperty("问题3答案")
    public String q3;
    @ApiModelProperty("问题4答案")
    public String q4;
    @ApiModelProperty("问题5答案")
    public String q5;
    @ApiModelProperty("出勤率")
    public float attendance;
    @ApiModelProperty("抬头率")
    public float riseRate;
    @ApiModelProperty("评价及建议")
    public String advice;
    @ApiModelProperty("课程从表编号")
    public String courseSlaveId;
    @ApiModelProperty("用户名")
    public String userName;
    @ApiModelProperty("角色编号")
    public String roleId;
    @ApiModelProperty("备注")
    public String remark;
    @ApiModelProperty("评价时间")
    public String dateTime;
    @ApiModelProperty("抓拍图片")
    public String imgUrl;
}
