package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("摄像头实体类")
public class Camera {
    @ApiModelProperty("主键id")
    public int id;
    @ApiModelProperty("摄像头id")
    public int cameraId;
    @ApiModelProperty("摄像头视频流")
    public int cameraUrl;
}
