package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.Logger;
import com.model.Room;
import com.model.User;
import com.service.EnterpriseService;
import com.service.RoomService;
import com.service.UserService;
import com.util.JsonUtils;
import com.util.PageUtil;
import com.util.ProduceId;
import com.util.ProduceVirtualRoomIdUtil;
import com.util.StringRandom;

import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping("/user")
public class UserController {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Value("${defaultUserPwd}")
	private String defaultUserPwd;
	
	@Value("${logoUploadPath}")
	private String logoUploadPath;
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private PageUtil pageUtil;
	
	
	
	
	@RequestMapping("/toUserInfo")
	public String toUserInfo() {
		return "/user/user-info";
	}
	/**
	 * 查询该用户下管理的所有子用户
	 * @param user
	 * @param session
	 * @param modelMap
	 * @param index
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectAllUser")
	public String selectAllUser(User user,HttpSession session,ModelMap modelMap,@RequestParam(required=true,defaultValue="1") Integer index,
            @RequestParam(required=false,defaultValue="15") Integer pageSize,HttpServletRequest request){
		PageHelper.startPage(index, pageSize);
		
		User adminUser=(User) session.getAttribute("user");
		Map<String,Object> map=new HashMap<>();
		if(user.getTruename()!=null) {
			map.put("truename", user.getTruename());
			logger.info(adminUser.getUsername()+"模糊搜索:"+user.getTruename());
		}
		if(adminUser.getRole()==1) {
			map.put("adminId", adminUser.getId());
			logger.info(adminUser.getUsername()+"用户查询所有子用户");
		}else if(adminUser.getRole()==0) {
			map.put("role", 1);
			logger.info(adminUser.getUsername()+"管理员查询所有用户");
		}else {
			return "/user/list-user";
		}
		
		Page<User> userList = (Page<User>) userService.selectAllUser(map);
		pageUtil.setPageInfo(userList, index, pageSize,request);
		modelMap.put("userList",userList);
		logger.info("userList"+userList);
		return "/user/list-user";
	}
	
	/**
	 * 检测输入的旧密码是否和原来匹配（个人）
	 * 
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testOldPwd")
	public String testOldPwd(String password,HttpSession session) {
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		User user = (User) session.getAttribute("user");
		password = new String(encoder.encode(password.getBytes()));
		password = new String(encoder.encode(password.getBytes()));
		if (password.equals(user.getPassword())) {
			return "success";
		}
		return "";
	}
	
	/**
	 * 跳转到新增页面
	 * @param user
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/toAddUser")
	public String toAddUser(ModelMap modelMap){
		return "/user/add-user";
	}
	
	/**
	 * 跳转到用户编辑页面
	 * @param user
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/toUpdate")
	public String toUpdate(User user,ModelMap modelMap){
		if(user.getId()!=null){
			Map<String,Object> map=new HashMap<>();
			map.put("id", user.getId());
			List<User> userList = userService.selectAllUser(map);
			modelMap.put("userChild", userList.get(0));
		}
		return "/user/edit-user";
	}
	
	/**
	 * 产生相应数量的用户信息，最后存放在session中
	 * @param number
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/produceUsers",produces="text/json;charset=UTF-8")
	public String produceUsers(String number,HttpSession session) {
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		User user=(User) session.getAttribute("user");
		List<String> idList = userService.selectAllId();
		JSONObject jsonObject=new JSONObject();
		int count=0;
		Map<String,Object> map=new HashMap<>();
		//查询所有用户
		List<User> selectAllUser = userService.selectAllUser(map);
		//将所有用户放入map内，键为用户名，方便查找
		Map<String,User> userMap=new HashMap<>();
		for (User user2 : selectAllUser) {
			userMap.put(user2.getUsername(), user2);
		}
		map.put("adminId", user.getId());
		//通过用户查询子用户
		List<User> selectUserByAdmin = userService.selectAllUser(map);
		List<User> userList=new ArrayList<>();
		
		/*String pwd = new String(encoder.encode(defaultUserPwd.getBytes()));
		pwd = new String(encoder.encode(pwd.getBytes()));*/
		
		int index=1;
		for(int i=1;i<=Integer.parseInt(number);i++) {
			
			//如果添加的子用户与已经存在的用户用户名相同，则下标加1
			while(userMap.get(user.getUsername()+(selectUserByAdmin.size()+index))!=null) {
				index++;
			}
			
			String newId = ProduceId.produceUserId(idList);
			idList.add(newId);
			Collections.sort(idList);
			User uu=new User();
			
			uu.setId(newId);
			uu.setUsername(user.getUsername()+(selectUserByAdmin.size()+index));
			uu.setTruename(user.getUsername()+(selectUserByAdmin.size()+index));
			uu.setSex(0);
			uu.setTelephone(user.getTelephone());
			uu.setEmail(user.getEmail());
			uu.setPassword(defaultUserPwd);
			
			//教育用户创建子用户权限为学生权限，企业用户创建子用户为教师权限
			if(user.getEnterpriseType()==1) {
				uu.setRole(2);
			}else if(user.getEnterpriseType()==2) {
				uu.setRole(1);
			}
			uu.setAdminId(user.getId());
			uu.setScreenNum(0);
			uu.setDuration("00:00:00");
			uu.setTimes(0);
			
			
			
			index++;
			/*if(userService.insertSelective(uu)>0) {
				
			}*/
			count++;
			userList.add(uu);
		}
		session.setAttribute("userList", userList);
		if(count==Integer.parseInt(number)) {
			return JsonUtils.objectToJson(userList);
		}
		return "error";
	}
	
	/**
	 * 批量新增用户，用户信息由produceUsers方法产生，存放在session内
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insertUsers")
	public String insertUsers(HttpSession session) {
		@SuppressWarnings("unchecked")
		List<User> userList=(List<User>) session.getAttribute("userList");
		
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		User adminUser=(User) session.getAttribute("user");
		Map<String,Object> map=new HashMap<>();
		
		map.put("adminId", adminUser.getId());
		//查询该用户下的子用户
		List<User> selectUserByAdmin = userService.selectAllUser(map);
		if(selectUserByAdmin.size()+userList.size()>50) {
			return "max";
		}
		int count=0;
		for (User user : userList) {
			if(user.getPassword()!=null) {
				String pwd = new String(encoder.encode(user.getPassword().getBytes()));
				pwd = new String(encoder.encode(pwd.getBytes()));
				user.setPassword(pwd);
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
			
			if(userService.insertSelective(user)>0) {
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
				count++;
			}
		}
		if(count==userList.size()) {
			session.removeAttribute("userList");
			logger.info(((User)(session.getAttribute("user"))).getUsername()+"新增"+count+"位子用户");
			return "success";
		}
		return "";
	}
	
	/**
	 * 根据有无Id判断进行更新或者新增操作
	 * @param teacher
	 * @return
	 */
	/*@ResponseBody*/
	@RequestMapping("/updateInfo")
	public String updateInfo(@RequestParam(value="file",required = false) MultipartFile file,User user,HttpSession session,
			HttpServletRequest request){
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		User adminUser=(User) session.getAttribute("user");
		Map<String,Object> map=new HashMap<>();
		if(user.getUsername()!=null) {
			map.put("username", user.getUsername());
			List<User> userList = userService.selectAllUser(map);
			if(userList.size()!=0 && !userList.get(0).getId().equals(user.getId())) {
				return "exist";
			}
		}
		
		if(user.getId()!=null && user.getId()!="") {

			if(user.getPassword()!=null) {
				String pwd = new String(encoder.encode(user.getPassword().getBytes()));
				pwd = new String(encoder.encode(pwd.getBytes()));
				user.setPassword(pwd);
			}
			
			if(file!=null) {
				//上传logo
				String fileName = file.getOriginalFilename();
				String realPath = request.getServletContext().getRealPath("/upload");
				String newFileName=UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
				String filePath = realPath+File.separator+newFileName;
				File uploadFile =new File(filePath);
				try {
					file.transferTo(uploadFile);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String showPath=request.getContextPath()+"/upload/"+newFileName;
				user.getEnterprise().setLogo(showPath);
				
				enterpriseService.updateByPrimaryKeySelective(user.getEnterprise());
			}
			
			if(userService.updateByPrimaryKeySelective(user)>0) {
				if(user.getId().equals(adminUser.getId())) {
					map.put("id", user.getId());
					List<User> userList = userService.selectAllUser(map);
					session.setAttribute("user", userList.get(0));
				}
				logger.info(adminUser.getUsername()+"成功修改了"+user.getUsername()+"的信息");
				return "/user/user-info";
			}
		}else {
			List<String> idList = userService.selectAllId();
			String newId=null;
			if(idList.size()==0){
//				如果表内没有数据，手动生成id
				newId="us1";
			}else{
				newId=ProduceId.produceUserId(idList);
			}
			if(newId!=null) {
				user.setId(newId);
			}
			if(user.getPassword()!=null) {
				String pwd = new String(encoder.encode(user.getPassword().getBytes()));
				pwd = new String(encoder.encode(pwd.getBytes()));
				user.setPassword(pwd);
			}
			user.setRole(2);
			user.setAdminId(adminUser.getId());
			if(userService.insertSelective(user)>0) {
				logger.info(adminUser.getUsername()+"新增了一条用户信息");
				return "/user/user-info";
			}
		}
		return "";
	}
	/**
	 * 删除用户数据
	 * @param teacher
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteUser")
	public String deleteUser(User user,HttpSession session){
		User loginUser=(User) session.getAttribute("user");
		//如果是管理员删除用户，需要删除用户下所有的子用户
		if(loginUser.getRole()==0) {
			logger.info(loginUser.getUsername()+"删除了"+user.getUsername()+"用户下的所有子用户");
			HashMap<String,Object> map=new HashMap<>();
			map.put("adminId", user.getId());
			List<User> selectAllUser = userService.selectAllUser(map);
			for (User user2 : selectAllUser) {
				userService.deleteByPrimaryKey(user2);
			}
		}
		logger.info(loginUser.getUsername()+"删除了"+user.getId());
		//删除该用户
		if(userService.deleteByPrimaryKey(user)>0){
			return "success";
		}
		return "error";
	}
	
	/**
	 * 给用户添加屏幕数量
	 * @param id
	 * @param num
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addScreenNum")
	public String addScreenNum(@RequestParam String id,@RequestParam String num,HttpSession session) {
		Map<String,Object> map=new HashMap<>();
		map.put("id", id);
		List<User> userList = userService.selectAllUser(map);
		User user=userList.get(0);
		user.setScreenNum(Integer.parseInt(num));
		
		User userLogin=(User) session.getAttribute("user");
		if(userService.updateByPrimaryKeySelective(user)>0) {
			logger.info(userLogin.getUsername()+"给"+user.getUsername()+"分配的屏幕数量为:"+num);
			return "success";
		}
		return "error";
	}
	
	public void rewardInvitedUser(User user,UserService userService) {
		//主用户邀请一位用户增加一块屏幕数
		if(user.getRole()==1)
			user.setScreenNum(user.getScreenNum()+1);
		//所有用户邀请一位用户都可以增加10个上传文件数量
		user.setMaxFileNum(user.getMaxFileNum()+10);
		userService.updateByPrimaryKeySelective(user);
	}
}
