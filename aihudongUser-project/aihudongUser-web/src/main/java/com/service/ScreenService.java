package com.service;

import java.util.List;
import java.util.Map;

import com.model.Screen;

public interface ScreenService {
	List<String> selectAllId();
	
	List<Screen> selectAllScreen(Map<String,Object> map);
	
	int insertSelective(Screen screen);

    Screen selectByPrimaryKey(Screen screen);

    int updateByPrimaryKeySelective(Screen screen);
    
    int deleteByPrimaryKey(Screen screen);
}
