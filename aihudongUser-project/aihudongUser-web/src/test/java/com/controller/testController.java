package com.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.BaseTest;
import com.model.Logger;
import com.model.Room;
import com.model.Screen;
import com.model.User;
import com.service.RoomService;
import com.service.ScreenService;
import com.service.UserService;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class testController extends BaseTest{
	
	@Autowired
	private UserService userService;
	@Autowired
	private ScreenService screenService;
	@Autowired
	private RoomService roomService;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	/*@Test
	public void frontLogin(HttpServletResponse response,HttpServletRequest request) throws IOException {
		Teacher teacher=new Teacher();
		teacher.setUsername("2");
		teacher.setPassword("2");
		QianduanController q=new QianduanController();
		q.UserLogin("2",response,request);
	}*/
	
	/**
	 * 密码加密算法
	 */
	@Test
	public void testMD5() {
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		String password = "qqq123";
		String string = null;
		string = new String(encoder.encode(password.getBytes()));
		string = new String(encoder.encode(string.getBytes()));
		System.out.println(string);
		
		
	}
	
	@Test
	public void testBase64() {
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
//		base64解码
		BASE64Decoder decoder = new BASE64Decoder();
		
		String string = null;
		string = new String(encoder.encode("123".getBytes()));
		string = new String(encoder.encode(string.getBytes()));
		List<User> selectAllUser = userService.selectAllUser(null);
		for (User user : selectAllUser) {
			user.setPassword(string);
			userService.updateByPrimaryKeySelective(user);
		}
		List<Screen> selectAllScreen = screenService.selectAllScreen(null);
		for (Screen screen : selectAllScreen) {
			screen.setPassword(string);
			screenService.updateByPrimaryKeySelective(screen);
		}
		List<Room> selectAllRoom = roomService.selectAllRoom(null);
		for (Room room : selectAllRoom) {
			room.setPassword(string);
			roomService.updateByPrimaryKeySelective(room);
		}
	}
	
	@Test
	public void decodeBase64() throws IOException {
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
//		base64解码
		BASE64Decoder decoder = new BASE64Decoder();
		
		List<User> selectAllUser = userService.selectAllUser(null);
		for (User user : selectAllUser) {
			String pwd=new String(decoder.decodeBuffer(user.getPassword()), "UTF-8");
			pwd=new String(decoder.decodeBuffer(pwd), "UTF-8");
			System.out.println("-----"+user.getUsername()+":"+pwd);
		}
	}
	
}
