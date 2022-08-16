package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("角色实体类")
public class Role {
    @ApiModelProperty("主键id")
    public int id;
    @ApiModelProperty("角色编号")
    public String roleId;
    @ApiModelProperty("角色名称")
    public String roleName;
}
