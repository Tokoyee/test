package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("当日课程信息")
public class CourseInfo {
    @ApiModelProperty("第几周")
    public String week;
    @ApiModelProperty("星期几")
    public String weeks;
    @ApiModelProperty("课程时间段")
    public String periodTime;
    @ApiModelProperty("课程始末时间")
    public String time;
    @ApiModelProperty("当前日期")
    public String date;
    @ApiModelProperty("课程名称")
    public String courseName;
    @ApiModelProperty("课程子表编号")
    public String courseSlaveId;
    @ApiModelProperty("教室地址")
    public String classroomId;
    @ApiModelProperty("授课教师")
    public String trueName;
    @ApiModelProperty("所属学院")
    public String instituteName;
    @ApiModelProperty("是否订阅")
    public boolean subscription;
}
