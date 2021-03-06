package com.dao;

import java.util.List;
import java.util.Map;

import com.model.Room;

public interface RoomMapper {
	List<Room> selectVirtualRoom(Map<String,Object> map);
	
	List<Room> selectAllRoom(Room room);
	
	List<String> selectAllId();
	
	Room selectScreenByRoom(Room room);
	
    int deleteByPrimaryKey(String id);

    int insert(Room record);

    int insertSelective(Room record);

    Room selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);
}