package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RoomMapper;
import com.model.Room;
import com.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomMapper roomMapper;
	@Override
	public Room selectScreenByRoom(Room room) {
		// TODO Auto-generated method stub
		
		return roomMapper.selectScreenByRoom(room);
	}
	@Override
	public int insertSelective(Room room) {
		// TODO Auto-generated method stub
		return roomMapper.insertSelective(room);
	}
	@Override
	public Room selectByPrimaryKey(Room room) {
		// TODO Auto-generated method stub
		return roomMapper.selectByPrimaryKey(room.getId());
	}
	@Override
	public int updateByPrimaryKeySelective(Room room) {
		// TODO Auto-generated method stub
		return roomMapper.updateByPrimaryKeySelective(room);
	}
	@Override
	public List<String> selectAllId() {
		// TODO Auto-generated method stub
		return roomMapper.selectAllId();
	}
	@Override
	public List<Room> selectAllRoom(Room room) {
		// TODO Auto-generated method stub
		return roomMapper.selectAllRoom(room);
	}
	@Override
	public List<Room> selectVirtualRoom(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return roomMapper.selectVirtualRoom(map);
	}
	@Override
	public int deleteByPrimaryKey(Room room) {
		// TODO Auto-generated method stub
		return roomMapper.deleteByPrimaryKey(room.getId());
	}

}
