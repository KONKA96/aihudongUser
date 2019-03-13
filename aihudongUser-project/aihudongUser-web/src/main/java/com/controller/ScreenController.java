package com.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.Logger;
import com.model.Room;
import com.model.Screen;
import com.model.User;
import com.service.RoomService;
import com.service.ScreenService;
import com.service.UserService;
import com.util.ExportExcel;
import com.util.PageUtil;
import com.util.ProduceId;
import com.util.ProduceVirtualRoomIdUtil;
import com.util.StringRandom;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping("/screen")
public class ScreenController {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ScreenService screenService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private PageUtil pageUtil;
	
	/**
	 * 查询所有该用户下的屏幕，支持分页，模糊搜索
	 * @param screen
	 * @param modelMap
	 * @param index
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectAllScreen")
	public String selectAllScreen(Screen screen,ModelMap modelMap,@RequestParam(required=true,defaultValue="1") Integer index,
            @RequestParam(required=false,defaultValue="15") Integer pageSize,HttpServletRequest request) {
		PageHelper.startPage(index, pageSize);
    	
    	HttpSession session = request.getSession();
    	User user=(User) session.getAttribute("user");
    	
    	Map<String,Object> map=new HashMap<>();
    	if(screen.getDuration()!=null) {
			map.put("duration", screen.getDuration());
			logger.info(user.getUsername()+"模糊搜索屏幕:"+screen.getDuration());
		}
    	
    	if(user.getRole()==1) {
    		map.put("userId", user.getId());
    	}
    	
    	Page<Screen> screenList = (Page<Screen>) screenService.selectAllScreen(map);
    	pageUtil.setPageInfo(screenList, index, pageSize,request);
		modelMap.put("screenList", screenList);
		logger.info(user.getUsername()+"搜索屏幕:"+screenList);
		
		return "/screen/list-screen";
	}
	
	@ResponseBody
	@RequestMapping("/testOldPwd")
	public String testOldPwd(String id,String password) {
		Map<String,Object> map=new HashMap<>();
//		base64转码
		map.put("id", id);
		List<Screen> screenList = screenService.selectAllScreen(map);
		BASE64Encoder encoder = new BASE64Encoder();
		password = new String(encoder.encode(password.getBytes()));
		password = new String(encoder.encode(password.getBytes()));
		if (password.equals(screenList.get(0).getPassword())) {
			return "success";
		}
		return "";
	}
	
	/**
     * 跳转到屏幕的修改页面
     * @param screen
     * @param modelMap
     * @return
     */
    @RequestMapping("/toUpdateScreen")
    public String toUpdateScreen(Screen screen,ModelMap modelMap,HttpSession session){
    	List<Room> roomList = roomService.selectAllRoom(null);
    	modelMap.put("roomList", roomList);
    	if(screen.getId()!=null){
    		Map<String,Object> map=new HashMap<>();
    		map.put("id", screen.getId());
    		List<Screen> screenList = screenService.selectAllScreen(map);
    		screen=screenList.get(0);
    		modelMap.put("screen", screen);
    		return "/screen/edit-screen";
    	}else {
    		Map<String,Object> map=new HashMap<>();
    		//查询该用户所分配的所有屏幕
        	User user=(User) session.getAttribute("user");
        	map.put("userId", user.getId());
        	List<Screen> screenList = screenService.selectAllScreen(map);
        	//查询该用户的剩余屏幕
        	user.setScreenRemain(user.getScreenNum()-screenList.size());
        	session.setAttribute("user", user);
    	}
    	return "/screen/add-screen";
    }
    
    /**
     * 根据选择的房间和要生成的屏幕数量，生成临时的屏幕，存放在session中
     * @param room 要创建的房间
     * @param number 生成屏幕的数量
     * @param session
     * @param modelMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/selectScreenByRoom",produces = "text/json;charset=UTF-8")
    public String selectScreenByRoom(Room room,String number,HttpSession session,ModelMap modelMap){
    	User user=(User) session.getAttribute("user");
    	JSONObject jsonObject=new JSONObject();
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
    	Map<String,Object> map=new HashMap<>();
		//查询该用户所分配的所有屏幕
    	map.put("userId", user.getId());
    	List<Screen> screenList = screenService.selectAllScreen(map);
    	//查询该用户的剩余屏幕
    	user.setScreenRemain(user.getScreenNum()-screenList.size());
    	
    	
    	room.setUserId(user.getId());
    	Room roomN=roomService.selectScreenByRoom(room);
    	List<Screen> screenListOld=new ArrayList<>();
    	
    	//记录房间本来有的屏幕数量
    	int oldNum = 0;
    	//房间不存在，则新建房间
    	if(roomN==null) {
    		session.setAttribute("room", room);
    	}else {
    		oldNum = roomN.getScreenList().size();
    		room=roomN;
    		screenListOld=room.getScreenList();
    		jsonObject.put("screenListOld", screenListOld);
    	}
    	if(user.getScreenRemain()+oldNum<Integer.parseInt(number)) {
    		jsonObject.put("min", "min");
    		return jsonObject.toString();
    	}
    	jsonObject.put("room", room);
    	
    	int start=0;
    	if(screenListOld.size()>0){
    		start=screenListOld.size();
    	}
    	List<String> screenIdList = screenService.selectAllId();
    	List<Screen> screenListNew=new ArrayList<>();
    	String pwd = new String(encoder.encode("123".getBytes()));
		pwd = new String(encoder.encode(pwd.getBytes()));
    	for(int i=0;i<Integer.parseInt(number)-start;i++) {
    		String newId=ProduceId.produceUserId(screenIdList);
    		screenIdList.add(newId);
    		Collections.sort(screenIdList);
    		Screen screen=new Screen();
    		screen.setId(newId);
    		
    		String username=newId.replace("sc", "");
    		if(username.length()<8) {
    			int cishu=8-username.length();
    			for(int j=0;j<cishu;j++) {
    				username="0"+username;
        		}
    		}
    		screen.setUsername(username);
    		
    		screen.setPassword(pwd);
    		screen.setRoomId(room.getId());
    		screen.setUserId(((User)session.getAttribute("user")).getId());
    		screen.setDuration("00:00:00");
    		screen.setTimes(0);
    		screenListNew.add(screen);
    	}
    	
    	jsonObject.put("screenListNew", screenListNew);
    	return jsonObject.toString();
    }
    
    /**
     * 新增屏幕
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertScreen")
    public String insertScreen(HttpSession session,User user,Room roomScreen){
    	roomScreen.setId(UUID.randomUUID().toString());
    	//用户注册
    	if(user.getUsername()!=null) {
    		String register = register(user, session);
    		if(!register.equals("success")) {
        		return register;
        	}
    	}else {
    		user=(User) session.getAttribute("user");
    	}
    	
    	Map<String,Object> map=new HashMap<>();
    	//查询该用户所分配的所有屏幕
    	map.put("userId", user.getId());
    	List<Screen> screenList = screenService.selectAllScreen(map);
    	if(user.getScreenNum()<=screenList.size()) {
    		return "min";
    	}
    	
    	//新建房间
    	Room room=(Room) session.getAttribute("room");
    	if(room!=null) {
    		if(roomScreen.getId().contains(",")) {
    			roomScreen.setId(roomScreen.getId().replace(",", ""));
    		}
    		room.setId(roomScreen.getId());
    		roomService.insertSelective(room);
    		session.removeAttribute("room");
    		logger.info(user.getUsername()+"成功开通了"+room.getNum());
    	}
    	
    	Map<String,Object> userMap=new HashMap<>();
		userMap.put("username", user.getUsername());
		List<User> selectAllUser = userService.selectAllUser(userMap);
		
    	
    	
    	screenList=roomScreen.getScreenList();
//    	添加和修改的总记录
    	int i=0;
//    	只记录添加数量
    	int insert=0;
    	if(screenList!=null) {
    		//手动添加屏幕
	    	for (Screen screen : screenList) {
	    		Screen screenOld=screenService.selectByPrimaryKey(screen);
	    		if(screenOld!=null){
	//    			判断用户名重复
	    			
	    			map.put("username", screenOld.getUsername());
	    			List<Screen> selectAllScreen = screenService.selectAllScreen(map);
	    			if(selectAllScreen.size()!=0){
	    				if(!selectAllScreen.get(0).getId().equals(screenOld.getId())){
	    					return "error";
	    				}
	    			}
	    			if(screenService.updateByPrimaryKeySelective(screen)>0){
	    				i++;
	    			}
	    		}else{
	//    			判断用户名重复
	    			map.put("username", screen.getUsername());
	    			List<Screen> selectAllScreen = screenService.selectAllScreen(map);
	    			if(selectAllScreen.size()!=0){
	    				return "error";
	    			}
	//    			screen
	    			screen.setDuration("00:00:00");
	        		screen.setTimes(0);
	        		screen.setUserId(user.getId());
	        		if(roomScreen.getId()!=null){
	        			screen.setRoomId(roomScreen.getId());
	        		}else{
	        			screen.setRoomId(room.getId());
	        		}
	        		if(screenService.insertSelective(screen)>0){
	        			i++;
	        			insert++;
	        		}
	    		}
			}
	    	logger.info(user.getUsername()+"成功开通"+i+"个屏幕");
        	return "success";
    	}else {
    		roomScreen.setNum("房间1");
    		roomService.insertSelective(roomScreen);
    		//注册自动添加屏幕
    		List<String> screenIdList = screenService.selectAllId();
        	for(int j=0;j<2;j++) {
        		String newId=ProduceId.produceUserId(screenIdList);
        		screenIdList.add(newId);
        		Collections.sort(screenIdList);
        		Screen screen=new Screen();
        		screen.setId(newId);
        		
        		String username=newId.replace("sc", "");
        		if(username.length()<8) {
        			int cishu=8-username.length();
        			for(int x=0;x<cishu;x++) {
        				username="0"+username;
            		}
        		}
        		screen.setUsername(username);
        		
        		screen.setPassword("123");
        		screen.setRoomId(roomScreen.getId());
        		screen.setUserId(selectAllUser.get(0).getId());
        		screen.setDuration("00:00:00");
        		screen.setTimes(0);
        		
        		if(screenService.insertSelective(screen)>0){
        			i++;
        			insert++;
        		}
        	}
        	logger.info(user.getUsername()+"成功开通"+i+"个屏幕");
        	return "success";
    	}
    }
    /**
	 * 用户注册
	 * @param user
	 * @return
	 */
    public String register(User user,HttpSession session) {
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		Map<String,Object> map=new HashMap<>();
		//判断用户名重复
		if(user.getUsername()!=null) {
			map.put("username",user.getUsername());
			List<User> userList = userService.selectAllUser(map);
			if(userList.size()!=0) {
				logger.info(user.getUsername()+"用户已经存在，注册失败");
				return "exist";
			}
		}
		//判断电话重复
		if(user.getTelephone()!=null) {
			map.put("telephone",user.getTelephone());
			map.remove("username");
			List<User> userList = userService.selectAllUser(map);
			if(userList.size()!=0) {
				logger.info(user.getTelephone()+"用户已经存在，注册失败");
				return "phoneexist";
			}
		}
		List<User> allUserList = userService.selectAllUser(null);
		A:while(true) {
			String invitedCode = StringRandom.getStringRandom(6);
			
			int i = 0;
			for (User uu : allUserList) {
				if(uu.getInviteCode().equals(invitedCode)) {
					break;
				}
				i++;
				if(i == allUserList.size()) {
					user.setInviteCode(invitedCode);
					break A;
				}
			}
		}
		List<String> idList = userService.selectAllId();
		String newId = ProduceId.produceUserId(idList);
		user.setId(newId);
		user.setRole(1);
		String pwd = new String(encoder.encode(user.getPassword().getBytes()));
		pwd = new String(encoder.encode(pwd.getBytes()));
		user.setPassword(pwd);
		if(userService.insertSelective(user)>0) {
			logger.info(user.getUsername()+"用户注册成功");
			return "success";
		}
		return "error";
	}
    
    @ResponseBody
    @RequestMapping("/insertRoomScreen")
    public String insertRoomScreen(HttpSession session,Room room) {
    	User user=(User) session.getAttribute("user");
    	
    	room.setNum("房间1");
    	roomService.insertSelective(room);
    	logger.info(user.getUsername()+"成功开通了"+room.getNum()+"房间");
    	
    	int count=0;
    	for(int j=0;j<2;j++) {
    		Screen sc1=new Screen();
        	List<String> idList = screenService.selectAllId();
        	String screenId=ProduceId.produceUserId(idList);
        	idList.add(screenId);
        	Collections.sort(idList);
        	
        	sc1.setId(screenId);
        	
        	String index=screenId.replace("sc", "");
        	String username="";
        	for(int i=0;i<8-index.length();i++) {
        		username+="0";
        	}
        	username+=index;
        	sc1.setUsername(username);
        	
        	sc1.setPassword("123");
        	
        	sc1.setRoomId(room.getId());
        	
        	sc1.setUserId(user.getId());
        	
        	sc1.setDuration("00:00:00");
        	
        	sc1.setTimes(0);
        	
        	if(screenService.insertSelective(sc1)>0) {
        		count++;
        	}
    	}
    	if(count==2) {
    		logger.info(user.getUsername()+"成功开通了"+count+"个屏幕");
    		return "success";
    	}
    	
    	return "";
    }
    /**
     * 修改屏幕信息
     * @param screen
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateScreen")
    public String updateScreen(Screen screen,HttpSession session){
    	User user=(User) session.getAttribute("user");
    	
    	if(screen.getId()!=null){
    		List<Screen> screenList = screenService.selectAllScreen(null);
    		for (Screen sc : screenList) {
//    			用户名相同且id不同，则认为用户名重复
				if(sc.getUsername().equals(screen.getUsername()) && !sc.getId().equals(screen.getId())){
					logger.info(user.getUsername()+"修改屏幕失败，屏幕已经存在");
					return "same";
				}
			}
    		if(screenService.updateByPrimaryKeySelective(screen)>0){
    			logger.info(user.getUsername()+"修改"+screen.getId()+"屏幕成功");
    			return "success";
    		}
    	}else{
    		if(screenService.insertSelective(screen)>0){
    			logger.info(user.getUsername()+"添加屏幕成功");
    			return "success";
    		}
    	}
    	return "error";
    }
    
    /**
     * 删除屏幕信息
     * @param screen
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteScreen")
    public String deleteScreen(Screen screen,HttpSession session){
    	User user=(User) session.getAttribute("user");
    	if(screenService.deleteByPrimaryKey(screen)>0){
    		logger.info(user.getUsername()+"删除了"+screen.getId()+"屏幕");
    		return "success";
    	}
    	return "";
    }
    
    /**
     * 屏幕转移功能
     * @param screen
     * @param modelMap
     * @return
     */
    @RequestMapping("/screenTransfer")
    public String screenTransfer(Screen screen,ModelMap modelMap){
    	Map<String,Object> map=new HashMap<>();
		map.put("id", screen.getId());
    	List<Screen> screenList = screenService.selectAllScreen(map);
    	screen=screenList.get(0);
    	modelMap.put("screen", screen);
    	List<Room> roomList = roomService.selectAllRoom(null);
    	modelMap.put("roomList", roomList);
    	return "/screen/screen-transfer";
    }
    
    /**
     * 屏幕信息导出Excel表格
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response,HttpSession session) throws Exception {
//		base64解码
		BASE64Decoder decoder = new BASE64Decoder();
        // 定义表的标题
        String title = "屏幕信息";
        
        //定义表的列名
        String[] rowsName = new String[] {  "房间", "ID号", "屏幕登录名", "屏幕登录密码" , "使用时长" , "使用总人次"};
        
        //定义表的内容
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
//        List<Person> listPerson = ps.listPerson();
        
        User user=(User) session.getAttribute("user");
        Map<String,Object> map=new HashMap<>();
        if(user.getRole()==1) {
        	map.put("userId", user.getId());
        }
        List<Screen> screenList = screenService.selectAllScreen(map);
        for (int i = 0; i < screenList.size(); i++) {
        	Screen sc = screenList.get(i);
            objs = new Object[rowsName.length];
            objs[0] = sc.getRoom().getNum();
            objs[1] = sc.getId();
            objs[2] = sc.getUsername();
            String pwd = new String(decoder.decodeBuffer(sc.getPassword()), "UTF-8");
    		objs[3] = new String(decoder.decodeBuffer(pwd), "UTF-8");
            objs[4] = sc.getDuration();
            objs[5] = sc.getTimes();
            dataList.add(objs);
        }
        
        // 创建ExportExcel对象
        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        logger.info(user.getUsername()+"导出了excel表格");
        // 输出Excel文件
        try {
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition",
                    "attachment; filename=screenInfo.xls");
            response.setContentType("application/msexcel");
            ex.export(output);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
