package com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserMapper;
import com.model.Logger;
import com.model.Room;
import com.model.User;
import com.service.RoomService;
import com.service.UserService;
import com.util.ProduceVirtualRoomIdUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoomService roomService;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public User selectByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(user.getId());
	}

	public int deleteByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		//删除用户下所属的虚拟教室
		Map<String,Object> map = new HashMap<>();
		map.put("userId", user.getId());
		List<Room> RoomList = roomService.selectVirtualRoom(map);
		for (Room room : RoomList) {
			roomService.deleteByPrimaryKey(room);
		}
		//删除真实教室
		Room room = new Room();
		room.setUserId(user.getId());
		try {
			//可能数据会出现一个人多个教室的情况
			room = roomService.selectScreenByRoom(room);
			if(room!=null)
				roomService.deleteByPrimaryKey(room);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userMapper.deleteByPrimaryKey(user.getId());
	}

	public int insertSelective(User user) {
		// TODO Auto-generated method stub
		//创建虚拟教室
		Room virtualRoom = new Room();
		//产生roomId
		List<String> idList = roomService.selectAllId();
		ProduceVirtualRoomIdUtil util = new ProduceVirtualRoomIdUtil();
    	virtualRoom.setId(util.ProduceVirtualRoomId(idList));
    	virtualRoom.setNum(user.getUsername()+"'s virtual room");
    	virtualRoom.setUserId(user.getId());
    	if(roomService.insertSelective(virtualRoom)>0) {
    		logger.info(user.getUsername()+"成功开通了"+virtualRoom.getNum()+"虚拟教室");
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
		if(user.getMaxFileNum()==null) {
			user.setMaxFileNum(10);
		}
		if(user.getEnterpriseType()==null) {
			user.setEnterpriseType(2);
		}
		
		return userMapper.insertSelective(user);
	}

	public int updateByPrimaryKeySelective(User user) {
		// TODO Auto-generated method stub
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

	@Override
	public List<User> userLogin(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.userLogin(map);
	}

}
