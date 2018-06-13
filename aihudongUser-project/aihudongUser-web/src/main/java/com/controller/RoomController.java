package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Room;
import com.service.RoomService;

@Controller
@RequestMapping("/room")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@RequestMapping("/selectRoomById")
	public String selectRoomById(Room room,ModelMap modelMap){
		room=roomService.selectScreenByRoom(room);
		modelMap.put("room", room);
		return "/room/edit-room";
	}
	
	@ResponseBody
	@RequestMapping("/updateRoom")
	public String updateRoom(Room room) {
		if(roomService.updateByPrimaryKeySelective(room)>0) {
			return "success";
		}
		return "";
	}
}
