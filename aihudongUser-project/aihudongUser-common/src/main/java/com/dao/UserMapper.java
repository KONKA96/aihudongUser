package com.dao;

import java.util.List;
import java.util.Map;

import com.model.User;

public interface UserMapper {
	List<User> userLogin(Map<String,Object> map);
	
	List<String> selectAllId();
	
	List<User> selectAllUser(Map<String,Object> map);
	
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}