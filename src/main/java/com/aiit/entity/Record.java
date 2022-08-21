package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评价记录")
public class Record {
    @ApiModelProperty("课程名称")
    public String courseName;
    @ApiModelProperty("教师姓名")
    public String trueName;
    @ApiModelProperty("教室名称")
    public String classroomId;
    @ApiModelProperty("授课班级")
    public String className;
    @ApiModelProperty("时间段")
    public String periodTime;
    @ApiModelProperty("课程时间")
    public String time;
    @ApiModelProperty("评价时间")
    public String dateTime;
    @ApiModelProperty("第几周")
    public String week;
    @ApiModelProperty("星期几")
    public String weeks;
    @ApiModelProperty("节次")
    public String section;
    @ApiModelProperty("课程子表编号")
    public String courseSlaveId;
}
