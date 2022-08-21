package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订阅信息")
public class Subscription {
    @ApiModelProperty("主键id")
    public int id;
    @ApiModelProperty("订阅编号")
    public String subscriptionId;
    @ApiModelProperty("openid")
    public String touser;
    @ApiModelProperty("课程子表编号")
    public String courseSlaveId;
    @ApiModelProperty("用户名")
    public String userName;
    @ApiModelProperty("角色编号")
    public String roleId;
    @ApiModelProperty("提醒日期")
    public String remindDate;
    @ApiModelProperty("提醒时间")
    public int remindTime;
}
