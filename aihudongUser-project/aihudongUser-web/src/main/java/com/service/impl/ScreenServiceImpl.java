package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ScreenMapper;
import com.model.Screen;
import com.service.ScreenService;

import sun.misc.BASE64Encoder;

@Service
public class ScreenServiceImpl implements ScreenService {

	@Autowired
	private ScreenMapper screenMapper;
	@Override
	public List<Screen> selectAllScreen(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return screenMapper.selectAllScreen(map);
	}

	@Override
	public int insertSelective(Screen screen) {
		// TODO Auto-generated method stub
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		if(screen.getPassword()!=null) {
			String pwd = new String(encoder.encode(screen.getPassword().getBytes()));
			pwd = new String(encoder.encode(pwd.getBytes()));
			screen.setPassword(pwd);
		}
		return screenMapper.insertSelective(screen);
	}

	@Override
	public Screen selectByPrimaryKey(Screen screen) {
		// TODO Auto-generated method stub
		return screenMapper.selectByPrimaryKey(screen.getId());
	}

	@Override
	public int updateByPrimaryKeySelective(Screen screen) {
		// TODO Auto-generated method stub
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		if(screen.getPassword()!=null) {
			String pwd = new String(encoder.encode(screen.getPassword().getBytes()));
			pwd = new String(encoder.encode(pwd.getBytes()));
			screen.setPassword(pwd);
		}
		return screenMapper.updateByPrimaryKeySelective(screen);
	}

	@Override
	public int deleteByPrimaryKey(Screen screen) {
		// TODO Auto-generated method stub
		return screenMapper.deleteByPrimaryKey(screen.getId());
	}

	@Override
	public List<String> selectAllId() {
		// TODO Auto-generated method stub
		return screenMapper.selectAllId();
	}

}
