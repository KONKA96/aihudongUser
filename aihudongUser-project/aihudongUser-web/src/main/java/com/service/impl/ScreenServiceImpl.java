package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ScreenMapper;
import com.model.Screen;
import com.service.ScreenService;

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
