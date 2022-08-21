package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("日期时间信息")
public class DateTime {
    @ApiModelProperty("日期")
    public String date;
    @ApiModelProperty("时间")
    public String time;
}
