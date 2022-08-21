package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("搜索课程类")
public class SearchCourse {
    @ApiModelProperty("关键词")
    public String searchText;
    @ApiModelProperty("日期")
    public String date;
    @ApiModelProperty("时间段")
    public String time;
}
