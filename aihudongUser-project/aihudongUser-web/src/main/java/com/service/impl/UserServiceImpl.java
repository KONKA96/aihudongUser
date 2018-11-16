package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserMapper;
import com.model.User;
import com.service.UserService;

import sun.misc.BASE64Encoder;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User selectByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(user.getId());
	}

	public int deleteByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(user.getId());
	}

	public int insertSelective(User user) {
		// TODO Auto-generated method stub
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		if(user.getPassword()!=null) {
			String pwd = new String(encoder.encode(user.getPassword().getBytes()));
			pwd = new String(encoder.encode(pwd.getBytes()));
			user.setPassword(pwd);
		}
		if(user.getRole()==1) {
			if(user.getScreenNum()==null) {
				user.setScreenNum(2);
			}
		}
		if(user.getScreenNum()==null) {
			user.setScreenNum(0);
		}
		if(user.getDuration()==null) {
			user.setDuration("00:00:00");
		}
		if(user.getTimes()==null) {
			user.setTimes(0);
		}
		
		return userMapper.insertSelective(user);
	}

	public int updateByPrimaryKeySelective(User user) {
		// TODO Auto-generated method stub
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		if(user.getPassword()!=null) {
			String pwd = new String(encoder.encode(user.getPassword().getBytes()));
			pwd = new String(encoder.encode(pwd.getBytes()));
			user.setPassword(pwd);
		}
		return userMapper.updateByPrimaryKeySelective(user);
	}

	public List<User> selectAllUser(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return userMapper.selectAllUser(map);
	}

	@Override
	public List<String> selectAllId() {
		// TODO Auto-generated method stub
		return userMapper.selectAllId();
	}

}
