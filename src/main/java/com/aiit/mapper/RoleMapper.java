package com.aiit.mapper;

import com.aiit.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("select roleId from role where roleName = #{roleName}")
    public List<String> getRoleId(String roleName);
}
