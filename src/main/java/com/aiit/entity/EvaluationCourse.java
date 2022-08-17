package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评课信息")
public class EvaluationCourse {
    @ApiModelProperty("主键id")
    public String id;
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
}
