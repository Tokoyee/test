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
    @ApiModelProperty("教学态度得分")
    public int attitudeScore;
    @ApiModelProperty("教学内容得分")
    public int includeScore;
    @ApiModelProperty("教学方法得分")
    public int methodScore;
    @ApiModelProperty("教学效果得分")
    public int effectScore;
    @ApiModelProperty("总得分")
    public int totalScore;
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
    @ApiModelProperty("备注")
    public String remark;
    @ApiModelProperty("评价时间")
    public String dateTime;
    @ApiModelProperty("抓拍图片")
    public String imgUrl;
}
