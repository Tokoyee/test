package com.aiit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("文章信息")
public class Article {
    @ApiModelProperty("主键id")
    public int id;
    @ApiModelProperty("文章作者")
    public String author;
    @ApiModelProperty("文章标题")
    public String title;
    @ApiModelProperty("文章内容")
    public String include;
    @ApiModelProperty("文章发表时间")
    public String time;

}
