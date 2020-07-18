package com.shuai.mapper;

import com.shuai.bean.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DepartmentMapper {
    @Select("select * from department where id =#{id}")
    Department getDepyById(Integer id);
}
