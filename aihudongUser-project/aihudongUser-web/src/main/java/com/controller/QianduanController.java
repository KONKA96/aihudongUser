package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Record;
import com.model.Room;
import com.model.Screen;
import com.model.User;
import com.model.VirtualRoomRecord;
import com.service.RecordService;
import com.service.RoomService;
import com.service.ScreenService;
import com.service.UserService;
import com.service.VirtualRoomRecordService;
import com.util.JsonUtils;
import com.util.ProduceVirtualRoomIdUtil;

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
	@Autowired
	private VirtualRoomRecordService virtualRoomRecordService;
	
	/**
	 * 用户登录
	 * @author KONKA
	 * @param username 用户名
	 * @param password 密码
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = { "/userLogin" }, produces = { "text/json;charset=UTF-8" })
	public String UserLogin(@RequestParam(required = false) String username,
			@RequestParam(required = false) String password, @RequestParam(required = false) String sid,
			@RequestParam(required = false) String serverhost, @RequestParam(required = false) String openid,
			HttpServletResponse response, HttpServletRequest request, ModelMap modelMap) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		ServletContext servletContext = request.getServletContext();
		HttpSession session = request.getSession();
		//返回参数集合
		Map<String, Object> argMap = new HashMap<>();

		argMap.put("username", username);
		
		Screen screen = new Screen();
		User user = new User();
		
		List<Screen> selectAllScreen = new ArrayList<>();
		List<User> selectAllUser = null;
		//查询参数集合
		Map<String,Object> map = new HashMap<>();
		if (username != null) {
			modelMap.put("username", username);
			
			
			map.put("username", username);
			selectAllUser = userService.selectAllUser(map);

			screen.setUsername(username);

			selectAllScreen = screenService.selectAllScreen(map);
			
		}else if (sid != null) {
			map.put("sid", sid);
			selectAllScreen = screenService.selectAllScreen(map);
			if(selectAllScreen.size()>1) {
				argMap.put("code", Integer.valueOf(1004));
				argMap.put("message", "该机器绑定多个屏幕，请联系管理员!");
				return JsonUtils.objectToJson(argMap);
			}
		}
		
		else {
			argMap.put("code", Integer.valueOf(1002));
			argMap.put("message", "用户名为空");
			return JsonUtils.objectToJson(argMap);
		}
		Record record = new Record();
		if ((selectAllUser == null || selectAllUser.size()==0) && (selectAllScreen.size() == 0)) {
			argMap.put("code", Integer.valueOf(1001));
			argMap.put("message", "用户不存在");
			return JsonUtils.objectToJson(argMap);
		}
		if (selectAllUser != null && selectAllUser.size()!=0) {
			user = selectAllUser.get(0);
			
			if (!user.getPassword().equals(password)) {
				argMap.put("code", Integer.valueOf(1002));
				argMap.put("message", "密码错误");
				return JsonUtils.objectToJson(argMap);
			}
			
			session.setAttribute("user", user);

			servletContext.setAttribute(user.getUsername(), session);
			String sessionId = session.getId();
			user.setSessionId(sessionId);

			record.setUserId(user.getId());
			record.setRole(user.getRole());
			argMap.put("role", user.getRole());
			argMap.put("truename", user.getTruename());
			/* 微信登录
			 * if (openid != null && openid != "") {
				user.setOpenId(openid);
				user.setPassword(null);
				this.teacherService.updateTeacherSelected(teacher);
			}*/
			
			//统计在线人数
			/*Object tcount = servletContext.getAttribute("tcount");
			if(tcount==null) {
				servletContext.setAttribute("tcount", 1);
			}else {
				servletContext.setAttribute("tcount", tcount.toString()+1);
			}*/
		} else if (selectAllScreen.size() != 0) {
			if (password != null && !((Screen) selectAllScreen.get(0)).getPassword()
					.equals(password)) {
				argMap.put("code", Integer.valueOf(1002));
				argMap.put("message", "密码错误");
				return JsonUtils.objectToJson(argMap);
			}
			

			argMap.put("username", selectAllScreen.get(0).getUsername());
			
			screen = (Screen) selectAllScreen.get(0);
			session.setAttribute("screen", screen);
			/* 屏幕随机数
			 * String stringRandom = StringRandom.getStringRandom(3);

			servletContext.setAttribute(screen.getUsername(), stringRandom);
			servletContext.setAttribute(stringRandom, session);
			screen.setRandomname(stringRandom);
			*/

			screen.setRole(4);
			String sessionId = session.getId();
			screen.setSessionId(sessionId);
			

			record.setUserId(screen.getId());
			record.setRole(Integer.valueOf(4));
			screen = (Screen) selectAllScreen.get(0);
			Room room = roomService.selectScreenByRoom(screen.getRoom());
			argMap.put("role", Integer.valueOf(4));
			argMap.put("meetingName", room.getNum());
			argMap.put("meetingId", room.getId());
			argMap.put("meetingType", 1);
			
			if ((selectAllScreen.size() != 0) && (((Screen) selectAllScreen.get(0)).getSid() == null) && (sid != null)) {
				selectAllScreen.get(0).setSid(sid);
				selectAllScreen.get(0).setPassword(null);
				screenService.updateByPrimaryKeySelective(selectAllScreen.get(0));
			}
		}
		session.setMaxInactiveInterval(-1);
		session.setAttribute("count", Integer.valueOf(0));

		session.setAttribute("startTime", new Date());

		record.setStartTime(new Date());
		this.recordService.insertSelective(record);

		session.setAttribute("recordId", record.getId());
		session.setAttribute("startTime", record.getStartTime());
		session.setAttribute("role", record.getRole());
		session.setAttribute("userId", record.getUserId());
		argMap.put("code", Integer.valueOf(200));
		argMap.put("serverhost", serverhost);

		return JsonUtils.objectToJson(argMap);
	}
	
	/**
	 * 用户登出
	 * @param string 传回来的所有信息
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/userLogout")
	public String userLogout(@RequestParam(required = false) String username,HttpServletResponse response,HttpServletRequest request) throws IOException{
		ServletContext servletContext = request.getServletContext();
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		Map<String, Object> argMap = new HashMap<>();
		/*
		 * Screen screen=new Screen(); screen.setUsername(username); List<Screen>
		 * selectAllScreen = screenService.selectAllScreen(screen);
		 */
		HttpSession session = null;
		String randomname = null;
		session = (HttpSession) servletContext.getAttribute(username);
		/*
		 * if(selectAllScreen.size()==0) {
		 * 
		 * }else { randomname = (String) servletContext.getAttribute(username); session
		 * = (HttpSession) servletContext.getAttribute(randomname); }
		 */

		// 更新记录表内登出的时间
		int id = (int) session.getAttribute("recordId");
		Record record = new Record();
		record.setId(id);
		record.setEndTime(new Date());
		Date startTime = (Date) session.getAttribute("startTime");
		// 计算使用的时长
		long second = (record.getEndTime().getTime() - startTime.getTime()) / 1000;
		// 转化为小时、分钟、秒
		int hour = (int) (second / (60 * 60));
		int minute = (int) ((second % (60 * 60)) / 60);
		int sec = (int) (second % 60);

		int role = (int) session.getAttribute("role");
		String userId = (String) session.getAttribute("userId");
		if (role == 1 || role == 2) {
			userLogout(userId, hour, minute, sec, servletContext);
		} else if (role == 4) {
			screenLogout(userId, hour, minute, sec);
		}
		recordService.updateByPrimaryKeySelective(record);
		
		servletContext.removeAttribute(username);
		// 清除session
		argMap.put("code", Integer.valueOf(200));
		
		return JsonUtils.objectToJson(argMap);
	}
	
	public void userLogout(String userId, int hour, int minute, int sec, ServletContext servletContext) {
		User user = new User();
		user.setId(userId);
		user = userService.selectByPrimaryKey(user);
		// 更新教师使用时长，通过':'分割字符串，分别计算秒、分钟、小时
		String duration = user.getDuration();

		user.setTimes(user.getTimes() + 1);
		user.setDuration(countTime(duration, hour, minute, sec));
		//统计在线人数
		userService.updateByPrimaryKeySelective(user);
	}

	public void screenLogout(String userId, int hour, int minute, int sec) {
		Screen screen = new Screen();
		screen.setId(userId);
		screen = screenService.selectByPrimaryKey(screen);
		int number = screen.getTimes();
		String duration = screen.getDuration();

		screen.setTimes(number + 1);
		screen.setDuration(countTime(duration, hour, minute, sec));
		screenService.updateByPrimaryKeySelective(screen);
	}
	
	/**
	 * 用户与屏幕连接
	 * @author KONKA
	 * @param string
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = { "/connectToScreen" }, produces = { "text/json;charset=UTF-8" })
	public String connectToScreen(@RequestParam String usernameUser, @RequestParam String usernameScreen,
			@RequestParam String role, @RequestParam(required = false) String serverhost, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");

		Map<String, Object> argMap = new HashMap<>();

		ServletContext servletContext = request.getServletContext();
		HttpSession session = (HttpSession) servletContext.getAttribute(usernameUser);
		if (session == null) {
			argMap.put("code", Integer.valueOf(1002));
			argMap.put("message", "session失效");
			return JsonUtils.objectToJson(argMap);
		}
		User user = (User) session.getAttribute("user");
		//查询参数集合
		Map<String,Object> map = new HashMap<>();
		Screen screen = new Screen();
		map.put("username", usernameScreen);
		//screen.setUsername(usernameScreen);
		List<Screen> selectAllScreen = screenService.selectAllScreen(map);
		if (selectAllScreen.size() <= 0) {
			argMap.put("code", Integer.valueOf(1003));
			argMap.put("message", "屏幕不存在");
			return JsonUtils.objectToJson(argMap);
		}else if(user.getRole()==1 && !selectAllScreen.get(0).getUserId().equals(user.getId())) {
			argMap.put("code", Integer.valueOf(1004));
			argMap.put("message", "屏幕不属于该用户");
			return JsonUtils.objectToJson(argMap);
		}else if(user.getRole()==2) {
			map.remove("username");
			map.put("id", user.getAdminId());
			List<User> userList = userService.selectAllUser(map);
			if(!userList.get(0).getId().equals(selectAllScreen.get(0).getUserId())) {
				argMap.put("code", Integer.valueOf(1004));
				argMap.put("message", "屏幕不属于该用户");
				return JsonUtils.objectToJson(argMap);
			}
		}
		screen = (Screen) selectAllScreen.get(0);
		Room room = this.roomService.selectScreenByRoom(screen.getRoom());
		List<Screen> screenList = room.getScreenList();
		/* 屏幕随机数
		 * for (Screen scr : screenList) {
			if (!scr.getUsername().equals(usernameScreen)) {
				scr.setRandomname(StringRandom.getStringRandom(3));
			}
		}*/
		int id = ((Integer) session.getAttribute("recordId")).intValue();
		Record record = new Record();
		record.setId(Integer.valueOf(id));
		record.setScreenId(screen.getId());
		this.recordService.updateByPrimaryKeySelective(record);

		argMap.put("usernameUser", usernameUser);
		if (session.getAttribute("screen") != null) {
			argMap.put("usernameScreen", usernameScreen);
		}
		argMap.put("meetingName", room.getNum());
		argMap.put("meetingId", room.getId());

		argMap.put("role", role);
		argMap.put("serverhost", serverhost);
		argMap.put("code", Integer.valueOf(200));
		argMap.put("meetingType", 1);
		return JsonUtils.objectToJson(argMap);
	}
	
	/**
	 * 查询用户历史虚拟教室记录接口
	 * @author KONKA
	 * @param username
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/selectHistoryRoomRecord", produces = { "text/json;charset=UTF-8" })
	public String selectHistoryRoomRecord(@RequestParam(required = false) String username, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> argMap = new HashMap<>();
		if(username==null||"".equals(username)) {
			argMap.put("code", "1001");
			argMap.put("message", "用户名为空");
			return JsonUtils.objectToJson(argMap);
		}
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("username", username);
		List<User> userList = userService.selectAllUser(map);
		
		Set<Room> roomSet = new HashSet<>();
		if(userList==null || userList.size()==0) {
			argMap.put("code", "1002");
			argMap.put("message", "用户不存在！");
			return JsonUtils.objectToJson(argMap);
		}else {
			map.put("userId", userList.get(0).getId());
			List<VirtualRoomRecord> vrrList = virtualRoomRecordService.selectVRR(map);
			for (VirtualRoomRecord virtualRoomRecord : vrrList) {
				Room room = virtualRoomRecord.getRoom();
				room.setKey(room.getId());
				if(room.getUserId().equals(userList.get(0).getId())) {
					room.setRole(1);
				}else {
					room.setRole(2);
				}
				roomSet.add(room);
			}
			
			List<Room> selectVirtualRoom = roomService.selectVirtualRoom(map);
			if(selectVirtualRoom!=null && selectVirtualRoom.size()!=0) {
				Room room = selectVirtualRoom.get(0);
				room.setRole(1);
				room.setKey(room.getId());
				roomSet.add(room);
			}
			argMap.put("code", "200");
			argMap.put("roomList", roomSet);
		}
		return JsonUtils.objectToJson(argMap);
	}
	
	/**
	 * 用户加入虚拟教室接口
	 * @author KONKA
	 * @param username
	 * @param roomId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/joinVirtualRoom", produces = { "text/json;charset=UTF-8" })
	public String joinVirtualRoom(@RequestParam(required = false) String username,
			@RequestParam(required = false) String roomId,@RequestParam(required = false) String password,
			HttpSession session, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> argMap = new HashMap<>();
		if(username==null||"".equals(username)) {
			argMap.put("code", "1001");
			argMap.put("message", "用户名为空");
			return JsonUtils.objectToJson(argMap);
		}
		if(roomId==null||"".equals(roomId)) {
			argMap.put("code", "1001");
			argMap.put("message", "房间id为空");
			return JsonUtils.objectToJson(argMap);
		}
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("username", username);
		List<User> userList = userService.selectAllUser(map);
		
		Room room = new Room();
		room.setId(roomId);
		room = roomService.selectByPrimaryKey(room);
		VirtualRoomRecord virtualRoomRecord = new VirtualRoomRecord();
		virtualRoomRecord.setRoomId(roomId);
		virtualRoomRecord.setStartTime(new Date());
		if(userList==null || userList.size()==0) {
			argMap.put("code", "1002");
			argMap.put("message", "用户不存在！");
			return JsonUtils.objectToJson(argMap);
		}else if(room==null) {
			argMap.put("code", "1002");
			argMap.put("message", "房间不存在！");
			return JsonUtils.objectToJson(argMap);
		}else if(room.getPassword()!=null && !room.getPassword().equals(password)) {
			argMap.put("code", "1003");
			argMap.put("message", "密码不正确！");
			return JsonUtils.objectToJson(argMap);
		}else{
			virtualRoomRecord.setUserId(userList.get(0).getId());
			virtualRoomRecord.setRole(userList.get(0).getRole());
		}
		
		if(room.getUserId().equals(userList.get(0).getId())) {
			argMap.put("role", "1");
		}else {
			argMap.put("role", "2");
		}
		session.setMaxInactiveInterval(0);
		virtualRoomRecordService.insertSelective(virtualRoomRecord);
		ServletContext servletContext = session.getServletContext();
		session.setAttribute("virtualRoomRecord", virtualRoomRecord.getId());
		servletContext.setAttribute(username, session);
		argMap.put("room", room);
		argMap.put("code", "200");
		argMap.put("meetingType", 0);
		return JsonUtils.objectToJson(argMap);
	}
	
	/**
	 * 查询房间接口
	 * @author KONKA
	 * @param roomNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/selectVirtualRoom", produces = { "text/json;charset=UTF-8" })
	public String selectVirtualRoom(@RequestParam(required = false) String roomId,
			@RequestParam(required = false) String username,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> argMap = new HashMap<>();
		if(roomId==null||"".equals(roomId)) {
			argMap.put("code", "1001");
			argMap.put("message", "房间ID为空！");
			return JsonUtils.objectToJson(argMap);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("username", username);
		List<User> userList = userService.selectAllUser(map);
		map.put("roomId", roomId);
		List<Room> roomList = roomService.selectVirtualRoom(map);
		if(roomList.size()==0) {
			argMap.put("code", "1002");
			argMap.put("message", "房间不存在！");
			return JsonUtils.objectToJson(argMap);
		}
		for (Room room : roomList) {
			room.setKey(room.getId());
			if(room.getUserId().equals(userList.get(0).getId())) {
				room.setRole(1);
			}else {
				room.setRole(2);
			}
		}
		argMap.put("code", "200");
		argMap.put("roomList", roomList);
		
		return JsonUtils.objectToJson(argMap);
	}
	
	/**
	 * 编辑虚拟教室接口
	 * @author KONKA
	 * @param roomId
	 * @param roomNum
	 * @param userId
	 * @param desc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateVirtualRoom", produces = { "text/json;charset=UTF-8" })
	public String updateVirtualRoom(@RequestParam(required = false) String roomId,
			@RequestParam(required = false) String roomNum,@RequestParam(required = false) String userId,
			@RequestParam(required = false) String desc,@RequestParam(required = false) String password,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> argMap = new HashMap<>();
		if(roomId==null||"".equals(roomId)) {
			argMap.put("code", "1001");
			argMap.put("message", "房间为空！");
			return JsonUtils.objectToJson(argMap);
		}
		Room room = new Room();
		room.setId(roomId);
		if(roomNum!=null) {
			room.setNum(roomNum);
		}
		if(userId!=null) {
			room.setUserId(userId);
		}
		if(desc!="" || desc!=null) {
			room.setDesc(desc);
		}
		if(password!="" || password!=null) {
			room.setPassword(password);
		}
		roomService.updateByPrimaryKeySelective(room);
		argMap.put("code", "200");
		return JsonUtils.objectToJson(argMap);
	}
	
	/**
	 * 退出虚拟教室接口
	 * @author KONKA
	 * @param username
	 * @param roomId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userLogoutVirtualRoom", produces = { "text/json;charset=UTF-8" })
	public String userLogoutVirtualRoom(@RequestParam(required = false) String username,
			@RequestParam(required = false) String roomId,HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> argMap = new HashMap<>();
		if(username==null||"".equals(username)) {
			argMap.put("code", "1001");
			argMap.put("message", "用户名为空！");
			return JsonUtils.objectToJson(argMap);
		}
		if(roomId==null||"".equals(roomId)) {
			argMap.put("code", "1001");
			argMap.put("message", "房间为空！");
			return JsonUtils.objectToJson(argMap);
		}
		
		ServletContext servletContext = request.getServletContext();
		HttpSession session = (HttpSession) servletContext.getAttribute(username);
		int vrrId = (int) session.getAttribute("virtualRoomRecord");
		VirtualRoomRecord virtualRoomRecord = new VirtualRoomRecord();
		virtualRoomRecord.setId(vrrId);
		virtualRoomRecord = virtualRoomRecordService.selectByPrimaryKey(virtualRoomRecord);
		virtualRoomRecord.setEndTime(new Date());
		virtualRoomRecordService.updateByPrimaryKeySelective(virtualRoomRecord);
		argMap.put("code", "200");
		return JsonUtils.objectToJson(argMap);
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
	
	
	
	@RequestMapping("/insertVirtual")
	@ResponseBody
	public String insertVirtual(String userId) {
		//创建虚拟教室
		Room virtualRoom = new Room();
		ProduceVirtualRoomIdUtil util = new ProduceVirtualRoomIdUtil();
		List<String> idList = roomService.selectAllId();
    	virtualRoom.setId(util.ProduceVirtualRoomId(idList));
    	virtualRoom.setNum(userId+"'s virtual room");
    	virtualRoom.setUserId(userId);
    	roomService.insertSelective(virtualRoom);
		return "success";
	}
}
