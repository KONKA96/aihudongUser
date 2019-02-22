package com.service;

import java.util.List;
import java.util.Map;

import com.model.User;

public interface UserService {
	List<User> userLogin(Map<String,Object> map);
	
	List<String> selectAllId();
	
	List<User> selectAllUser(Map<String,Object> map);
	
	User selectByPrimaryKey(User user);
	
	int deleteByPrimaryKey(User user);
	
	int insertSelective(User user);

	int updateByPrimaryKeySelective(User user);
}
