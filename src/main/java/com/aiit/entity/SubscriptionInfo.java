package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订阅课程信息")
public class SubscriptionInfo {
    @ApiModelProperty("教师姓名")
    public String trueName;
    @ApiModelProperty("课程名称")
    public String courseName;
    @ApiModelProperty("授课班级")
    public String className;
    @ApiModelProperty("节次")
    public String section;
    @ApiModelProperty("时间")
    public String time;
    @ApiModelProperty("日期")
    public String date;
    @ApiModelProperty("课程子表编号")
    public String courseSlaveId;
    @ApiModelProperty("课程子表编号")
    public boolean subscription;
}
