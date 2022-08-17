package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("任务信息")
public class Task {
    @ApiModelProperty("任务编号")
    public String taskId;
    @ApiModelProperty("任务描述")
    public String taskDescribe;
    @ApiModelProperty("任务状态")
    public String taskStatus;
    @ApiModelProperty("任务次数")
    public int taskNum;
    @ApiModelProperty("用户名")
    public String userName;
}
