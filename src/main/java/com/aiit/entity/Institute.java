package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("学院实体类")
public class Institute {
    @ApiModelProperty("主键id")
    public int id;
    @ApiModelProperty("学院编号")
    public int instituteId;
    @ApiModelProperty("学院名称")
    public int instituteName;
    @ApiModelProperty("学院类型")
    public int instituteType;
}
