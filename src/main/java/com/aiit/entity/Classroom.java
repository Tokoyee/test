package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("智慧教室实体类")
public class Classroom {
    @ApiModelProperty("主键id")
    public int id;
    @ApiModelProperty("教室编号")
    public String classroomId;
    @ApiModelProperty("楼号")
    public String towerNo;
    @ApiModelProperty("教室号")
    public String classroomNo;
    @ApiModelProperty("摄像头1编号")
    public String cameraOne;
    @ApiModelProperty("摄像头2编号")
    public String cameraTwo;
}
