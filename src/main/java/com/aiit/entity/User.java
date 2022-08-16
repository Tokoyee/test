package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户实体类")
public class User {
    @ApiModelProperty("主键id")
    public int id;
    @ApiModelProperty("用户编号")
    public String userId;
    @ApiModelProperty("用户名")
    public String userName;
    @ApiModelProperty("真实姓名")
    public String trueName;
    @ApiModelProperty("密码")
    public String password;
    @ApiModelProperty("头像")
    public String imgUrl;
    @ApiModelProperty("手机号")
    public String phoneNumber;
    @ApiModelProperty("所属学院")
    public String institude;
}
