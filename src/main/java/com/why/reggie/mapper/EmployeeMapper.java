package com.why.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.why.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    @Select("select * from employee where username=#{username} and password=#{password}")
    public Employee getEmployeeUsername(@Param("username") String username, @Param("password") String password);

}
