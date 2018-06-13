package com.service;

import java.util.List;

import com.model.Room;

public interface RoomService {
	List<Room> selectAllRoom(Room room);
	
	List<String> selectAllId();
	
	Room selectScreenByRoom(Room room);
	
	int insertSelective(Room room);

	Room selectByPrimaryKey(Room room);

	int updateByPrimaryKeySelective(Room room);
}
