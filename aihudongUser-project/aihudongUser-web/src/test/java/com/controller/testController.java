package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BaseTest;
import com.model.Logger;
import com.model.Room;
import com.model.Screen;
import com.model.User;
import com.service.RoomService;
import com.service.ScreenService;
import com.service.UserService;
import com.util.HttpUtil;
import com.util.JsonUtils;
import com.util.StringRandom;

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
		String password = "TVRJeg==";
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
	
	@Test
	public void testSha1() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		SortedMap<String, String> sortSignParams = new TreeMap<String, String>();
		sortSignParams.put("appId", "hgdx90001");
		sortSignParams.put("tenant", "90001");
		sortSignParams.put("userName", "sysmanager");
		
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : sortSignParams.entrySet()) {
		    String k = (String) entry.getKey();
		    String v = (String) entry.getValue();
		    sb.append(k.toLowerCase() + "=" + v + "&");
		}
		String str = sb.substring(0, sb.lastIndexOf("&"));
		System.out.println("str"+str);
		MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
		mdTemp.update(str.getBytes("UTF-8"));
		byte[] md = mdTemp.digest();

		final String HEX = "0123456789abcdef";
		StringBuilder sb1 = new StringBuilder(md.length * 2);
		for (byte b : md) {
			sb1.append(HEX.charAt((b >> 4) & 0x0f));
			sb1.append(HEX.charAt(b & 0x0f));
		}
		System.out.println(sb1.toString());
		
	}
	
	@Test
	public void testStringRandom() {
		System.out.println(StringRandom.getStringRandom(6));
	}
	
	
}
