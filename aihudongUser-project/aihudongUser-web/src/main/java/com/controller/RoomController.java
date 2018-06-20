package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Logger;
import com.model.Room;
import com.model.User;
import com.service.RoomService;

@Controller
@RequestMapping("/room")
public class RoomController {
	
	protected Logger logger = Logger.getLogger(this.getClass());

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
	public String updateRoom(Room room,HttpSession session) {
		User user=(User) session.getAttribute("user");
		if(roomService.updateByPrimaryKeySelective(room)>0) {
			logger.info(user.getUsername()+"修改了"+room.getNum()+"房间信息");
			return "success";
		}
		return "";
	}
}
