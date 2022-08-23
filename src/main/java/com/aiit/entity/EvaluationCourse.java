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
    @ApiModelProperty("问题6答案")
    public String q6;
    @ApiModelProperty("问题7答案")
    public String q7;
    @ApiModelProperty("问题8答案")
    public String q8;
    @ApiModelProperty("问题9答案")
    public String q9;
    @ApiModelProperty("问题10答案")
    public String q10;
    @ApiModelProperty("问题11答案")
    public String q11;
    @ApiModelProperty("问题12答案")
    public String q12;
    @ApiModelProperty("问题13答案")
    public String q13;
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
}
