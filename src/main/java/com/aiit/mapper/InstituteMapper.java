package com.aiit.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InstituteMapper {
    @Select("select instituteName from institute where instituteType = #{instituteType}")
    public List<String> getInstituteList(String instituteType);

}
