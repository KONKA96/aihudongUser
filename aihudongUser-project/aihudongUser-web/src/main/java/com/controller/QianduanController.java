package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.Err;
import com.model.Record;
import com.model.Room;
import com.model.Screen;
import com.model.User;
import com.service.RecordService;
import com.service.RoomService;
import com.service.ScreenService;
import com.service.UserService;
import com.util.JsonUtils;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping("/front")
public class QianduanController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ScreenService screenService;
	@Autowired
	private RecordService recordService;
	@Autowired
	private RoomService roomService;
	
	/**
	 * 用户登录
	 * @param string
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value="/userLogin", produces="text/plain")
	public void UserLogin(@RequestBody String string,
			HttpServletResponse response,HttpServletRequest request) throws IOException{
		response.setCharacterEncoding("utf-8");
		ServletContext servletContext = request.getServletContext();
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
//		base64解码
		BASE64Decoder decoder = new BASE64Decoder();
		string=new String(decoder.decodeBuffer(string), "UTF-8");
		JSONObject fromObject = JSONObject.fromObject(string);
		
		String username =fromObject.getString("username");
		String password=fromObject.getString("password");
		String device=fromObject.getString("device");
		
		Set<Entry<String, String>> entrySet = new HashSet<>();
		for (Entry<String, String> entry : entrySet) {
			if(entry.getKey().equals("username")){
				username=entry.getValue();
			}
			if(entry.getKey().equals("password")){
				password=entry.getValue();
			}
			if(entry.getKey().equals("device")){
				device=entry.getValue();
			}
		}
		HttpSession session = request.getSession();
		
		Map<String,Object> map=new HashMap<>();
		map.put("username",username);
		List<User> userList = userService.selectAllUser(map);
		List<Screen> screenList = screenService.selectAllScreen(map);
		
		PrintWriter writer=null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		转码后的字符串，最后要写入到流中的数据
		String encode =null;
//		保存的记录对象
		Record record=new Record();
		
		if(userList.size()!=0) {
			User user=userList.get(0);
			if(!user.getPassword().equals(password)) {
				Err err = new Err(1001,"用户名不存在或密码错误");
				String errString = JsonUtils.objectToJson(err);
				String errEncode = encoder.encode(errString.getBytes());
				writer.write(errEncode);
			}else {
				session.setAttribute("user", user);
				servletContext.setAttribute(user.getUsername(), session);
				
				String sessionId = session.getId();
				user.setSessionId(sessionId);
				
				String jsonTeacher = JsonUtils.objectToJson(user);
				encode = encoder.encode(jsonTeacher.getBytes());
				
				record.setUserId(user.getId());
				record.setRole(user.getRole());
			}
		}else if(screenList.size()!=0) {
			Screen screen = screenList.get(0);
			session.setAttribute("screen", screen);
			servletContext.setAttribute(screen.getUsername(), session);

			screen.setRole(4);

			String sessionId = session.getId();
			screen.setSessionId(sessionId);
			String jsonScreen = JsonUtils.objectToJson(screen);
			encode = encoder.encode(jsonScreen.getBytes());

			record.setUserId(screen.getId());
			record.setRole(4);
		}
		
		
//		将用户信息传到前端
		if(encode!=null){
			writer.write(encode);
			session.setAttribute("startTime", new Date());
//			新建记录对象，新增的数据库中，还缺少登出时间和连接的屏幕id
			record.setStartTime(new Date());
			recordService.insertSelective(record);
//			将该条记录的id记录在session中，方便后面更改该条记录
			session.setAttribute("recordId", record.getId());
			session.setAttribute("startTime", record.getStartTime());
			session.setAttribute("role", record.getRole());
			session.setAttribute("userId", record.getUserId());
		}
		writer.close();
	}
	
	/**
	 * 用户登出
	 * @param string 传回来的所有信息
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/userLogout")
	public void userLogout(@RequestBody String string,HttpServletResponse response,HttpServletRequest request) throws IOException{
		ServletContext servletContext = request.getServletContext();
		
//		base64解码
		BASE64Decoder decoder = new BASE64Decoder();
		
		string=new String(decoder.decodeBuffer(string), "UTF-8");
		JSONObject fromObject = JSONObject.fromObject(string);
		String username=fromObject.getString("username");
		String sessionId=fromObject.getString("sessionId");
		HttpSession session=(HttpSession) servletContext.getAttribute(username);
		if(session.getId().equals(sessionId)){
//			更新记录表内登出的时间
			int id= (int) session.getAttribute("recordId");
			Record record=new Record();
			record.setId(id);
			record.setEndTime(new Date());
			Date startTime=(Date) session.getAttribute("startTime");
//			计算使用的时长
			long second=(record.getEndTime().getTime()-startTime.getTime())/1000;
//			转化为小时、分钟、秒
			int hour=(int) (second/(60*60));
			int minute=(int) ((second%(60*60))/60);
			int sec=(int) (second%60);
			
			int role=(int) session.getAttribute("role");
			String userId=(String) session.getAttribute("userId");
			if(role==1 || role==2){
				User user=new User();
				user.setId(userId);
				user=userService.selectByPrimaryKey(user);
//				更新教师使用时长，通过':'分割字符串，分别计算秒、分钟、小时
				String duration = user.getDuration();
				
				user.setTimes(user.getTimes()+1);
				user.setDuration(countTime(duration,hour,minute,sec));
				userService.updateByPrimaryKeySelective(user);
			}else if(role==4){
				Screen screen=new Screen();
				screen.setId(userId);
				screen=screenService.selectByPrimaryKey(screen);
				int number=screen.getTimes();
				String duration = screen.getDuration();
				
				screen.setTimes(number+1);
				screen.setDuration(countTime(duration,hour,minute,sec));
				screenService.updateByPrimaryKeySelective(screen);
			}
			recordService.updateByPrimaryKeySelective(record);
//			清除session
			session.invalidate();
			servletContext.removeAttribute(username);
		}
	}
	
	/**
	 * 用户与屏幕连接
	 * @param string
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/connectToScreen")
	public void connectToScreen(@RequestBody String string,HttpServletResponse response,HttpServletRequest request) throws IOException{
		ServletContext servletContext = request.getServletContext();
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
//		base64解码
		BASE64Decoder decoder = new BASE64Decoder();
		string=new String(decoder.decodeBuffer(string), "UTF-8");
		JSONObject fromObject = JSONObject.fromObject(string);
		String usernameUser =fromObject.getString("usernameUser");
		int role=fromObject.getInt("role");
		String sessionId=fromObject.getString("sessionId");
		String usernameScreen =fromObject.getString("usernameScreen");
		String password =fromObject.getString("password");
		HttpSession session=(HttpSession) servletContext.getAttribute(usernameUser);
		PrintWriter writer = response.getWriter();
		String encode =null;
//		如果session中没有用户，则session失效，报1002
		if(session==null){
			Err err = new Err(1002,"会话过期");
			String errString = JsonUtils.objectToJson(err);
			encode = encoder.encode(errString.getBytes());
			writer.write(encode);
		}else{
//			通过用户名密码查询屏幕
			Map<String,Object> map=new HashMap<>();
			map.put("username", usernameScreen);
			List<Screen> selectAllScreen = screenService.selectAllScreen(map);
//			如果没有查到，报1001
			if(selectAllScreen.size()<=0){
				Err err = new Err(1003,"屏幕不存在!");
				String errString = JsonUtils.objectToJson(err);
				encode = encoder.encode(errString.getBytes());
				writer.write(encode);
			}else{
				Map<String,Object> mapUser=new HashMap<>();
				mapUser.put("username", usernameUser);
				List<User> userList = userService.selectAllUser(mapUser);
				User user=userList.get(0);
				
				
				Screen screen = selectAllScreen.get(0);
				if (!user.getId().equals(screen.getUserId())) {
					Err err = new Err(1003, "屏幕不存在!");
					String errString = JsonUtils.objectToJson(err);
					encode = encoder.encode(errString.getBytes());
					writer.write(encode);
				} else {
					Room room = screen.getRoom();
					room = roomService.selectScreenByRoom(room);
					if (room != null) {
						String jsonRoom = JsonUtils.objectToJson(room);
						encode = encoder.encode(jsonRoom.getBytes());
					}
					if (encode != null) {
						writer.write(encode);
					}

					// 更新记录中连接的屏幕ID
					int id = (int) session.getAttribute("recordId");
					Record record = new Record();
					record.setId(id);
					record.setScreenId(screen.getId());
					recordService.updateByPrimaryKeySelective(record);
				}
			}
		}
//		
		writer.close();
	}
	
	@RequestMapping("/breakConnect")
	public void breakConnect(String usernameUser,String sessionId,String usernameScreen,
			String password,HttpServletResponse response,HttpServletRequest request){
		ServletContext servletContext = request.getServletContext();
		HttpSession session=(HttpSession) servletContext.getAttribute(usernameUser);
	}
	
	/**
	 * 增加用户的使用时长
	 * @param duration 原有时长
	 * @param hour 小时
	 * @param minute 分钟
	 * @param sec 秒
	 * @return
	 */
	public String countTime(String duration,int hour,int minute,int sec){
		
		String[] split = duration.split(":");
		sec=Integer.parseInt(split[2])+sec;
		if(sec>=60){
			minute+=(sec/60);
			sec%=60;
		}
		minute+=Integer.parseInt(split[1]);
		if(minute>=60){
			hour+=(minute/60);
			minute%=60;
		}
		hour+=Integer.parseInt(split[0]);
		return hour+":"+minute+":"+sec;
	}
}
