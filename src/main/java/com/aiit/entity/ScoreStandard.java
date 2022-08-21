package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评分标准信息")
public class ScoreStandard {
    @ApiModelProperty("主键id")
    public int id;
    @ApiModelProperty("评价指标编号")
    public String standardId;
    @ApiModelProperty("评价指标类型")
    public String standardType;
    @ApiModelProperty("评价指标内容")
    public String standardInclude;
    @ApiModelProperty("评价指标权重")
    public float standardWeight;
}
