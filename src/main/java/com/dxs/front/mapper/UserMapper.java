package com.dxs.front.mapper;

import com.dxs.front.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    //形参是类的时候，就直接放到数据库语句#{}中；
    // 不是类的时候，需要注解Param
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token")String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id")Integer id);

}
