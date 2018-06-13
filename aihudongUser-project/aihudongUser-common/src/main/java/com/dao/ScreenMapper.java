package com.dao;

import java.util.List;
import java.util.Map;

import com.model.Screen;

public interface ScreenMapper {
	List<String> selectAllId();
	
	List<Screen> selectAllScreen(Map<String,Object> map);
	
    int deleteByPrimaryKey(String id);

    int insert(Screen record);

    int insertSelective(Screen record);

    Screen selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Screen record);

    int updateByPrimaryKey(Screen record);
}