package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("巡课抓拍图片表")
public class TourCourseImg {
    @ApiModelProperty("主键id")
    public int id;
    @ApiModelProperty("课程从表编号")
    public String courseSlaveId;
    @ApiModelProperty("用户名")
    public String userName;
    @ApiModelProperty("角色编号")
    public String roleId;
    @ApiModelProperty("抓拍图片")
    public String imgUrl;
}
