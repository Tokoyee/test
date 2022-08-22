package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色订阅消息")
public class RoleSubscription {
    @ApiModelProperty("角色编号")
    public String roleId;
    @ApiModelProperty("用户名")
    public String userName;
    @ApiModelProperty("课程子表编号")
    public String courseSlaveId;
}
