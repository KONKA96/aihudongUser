package com.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.User;
import com.service.UserService;
import com.util.JsonUtils;
import com.util.PageUtil;
import com.util.ProduceId;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
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
		}
		if(adminUser.getRole()==1) {
			map.put("adminId", adminUser.getId());
		}else if(adminUser.getRole()==0) {
			map.put("role", 1);
		}
		
		Page<User> userList = (Page<User>) userService.selectAllUser(map);
		pageUtil.setPageInfo(userList, index, pageSize,request);
		modelMap.put("userList",userList);
		return "/user/list-user";
	}
	
	/**
	 * 跳转到更新页面
	 * @param user
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/toAddUser")
	public String toAddUser(ModelMap modelMap){
		return "/user/add-user";
	}
	
	/**
	 * 跳转到更新页面
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
	
	@ResponseBody
	@RequestMapping(value="/produceUsers",produces="text/json;charset=UTF-8")
	public String produceUsers(String number,HttpSession session) {
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
			uu.setPassword("123");
			uu.setRole(2);
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
	
	@ResponseBody
	@RequestMapping("/insertUsers")
	public String insertUsers(HttpSession session) {
		@SuppressWarnings("unchecked")
		List<User> userList=(List<User>) session.getAttribute("userList");
		
		User adminUser=(User) session.getAttribute("user");
		Map<String,Object> map=new HashMap<>();
		
		map.put("adminId", adminUser.getId());
		//查询该用户下的子用户
		List<User> selectUserByAdmin = userService.selectAllUser(map);
		if(selectUserByAdmin.size()+userList.size()>30) {
			return "max";
		}
		int count=0;
		for (User user : userList) {
			if(userService.insertSelective(user)>0) {
				count++;
			}
		}
		if(count==userList.size()) {
			return "success";
		}
		return "";
	}
	
	/**
	 * 根据有无Id判断进行更新或者新增操作
	 * @param teacher
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateInfo")
	public String updateInfo(User user,HttpSession session){
		User adminUser=(User) session.getAttribute("user");
		Map<String,Object> map=new HashMap<>();
		map.put("username", user.getUsername());
		List<User> userList = userService.selectAllUser(map);
		if(userList.size()!=0 && !userList.get(0).getId().equals(user.getId())) {
			return "exist";
		}
		if(user.getId()!=null && user.getId()!="") {
			if(userService.updateByPrimaryKeySelective(user)>0) {
				if(user.getId().equals(adminUser.getId())) {
					session.setAttribute("user", user);
				}
				return "success";
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
			user.setRole(2);
			user.setAdminId(adminUser.getId());
			if(userService.insertSelective(user)>0) {
				return "success";
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
			HashMap<String,Object> map=new HashMap<>();
			map.put("adminId", user.getId());
			List<User> selectAllUser = userService.selectAllUser(map);
			for (User user2 : selectAllUser) {
				userService.deleteByPrimaryKey(user2);
			}
		}
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
	public String addScreenNum(@RequestParam String id,@RequestParam String num) {
		Map<String,Object> map=new HashMap<>();
		map.put("id", id);
		List<User> userList = userService.selectAllUser(map);
		User user=userList.get(0);
		user.setScreenNum(Integer.parseInt(num));
		if(userService.updateByPrimaryKeySelective(user)>0) {
			return "success";
		}
		return "error";
	}
}