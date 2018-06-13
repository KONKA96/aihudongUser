package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jcraft.jsch.JSchException;
import com.model.User;
import com.model.Version;
import com.service.UserService;
import com.test.DownFileFetch;
import com.test.DownFileInfoBean;
import com.util.DownFile;
import com.util.JsonUtils;
import com.util.ProduceId;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping("/index")
public class IndexController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private DownFile downFile;
	
	
	@RequestMapping("/test")
	public String test() {
		return "login";
	}
	/**
	 * 用户登录
	 * @param user
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public String login(User user,HttpSession session) {
		Map<String,Object> map=new HashMap<>();
		List<User> userList =null;
		if(user.getUsername()!=null) {
			if(user.getUsername().length()==11) {
				map.put("telephone",user.getUsername());
				userList=userService.selectAllUser(map);
				if(userList.size()==0) {
					map.remove("telephone");
					map.put("username",user.getUsername());
					userList=userService.selectAllUser(map);
				}
			}else {
				map.put("username",user.getUsername());
				userList=userService.selectAllUser(map);
			}
		}
		if(userList.size()==0) {
			return "none";
		}else if(!userList.get(0).getPassword().equals(user.getPassword())) {
			return "pwdError";
		}else {
			session.setAttribute("user", userList.get(0));
			return "success";
		}
	}
	
	/**
	 * 密码重置
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/resetPwd")
	public String resetPwd(User user){
		Map<String,Object> map=new HashMap<>();
		if(user.getTelephone()!=null) {
			map.put("telephone", user.getTelephone());
		}
		List<User> userList = userService.selectAllUser(map);
		//判断是否存在该电话
		if(userList.size()==0) {
			return "none";
		}
		//设置要更新的用户id
		user.setId(userList.get(0).getId());
		if(userService.updateByPrimaryKeySelective(user)>0) {
			return "success";
		}
		return "";
	}
	
	/**
     * 用户登出
     * @param session
     * @return
     */
	@RequestMapping("/userLogout")
	public String userLogout(HttpSession session) {
		session.invalidate();
    	return "login";
	}
	
	/**
	 * 跳转到首页
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex() {
		return "index";
	}
	
	/**
	 * 跳转到下载页面
	 * @return
	 */
	@RequestMapping("/toDownloadFile")
	public String toDownloadFile() {
		return "/download";
	}
	
	/**
	 * 检测最新安装包接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testDownloadFile")
	public void testDownloadFile(HttpServletRequest request,HttpServletResponse response) {
//		base64转码
		BASE64Encoder encoder = new BASE64Encoder();
//		base64解码
		BASE64Decoder decoder = new BASE64Decoder();
		
		PrintWriter writer=null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String path = request.getServletContext().getRealPath("/");
		path = path.replaceAll("\\\\", "/");
		File file = new File(path+"/upload");
		File[] fileList = file.listFiles();
		List<String> fileVersion = new ArrayList<>();
		for (File file2 : fileList) {
			String[] split = file2.getName().split("-");
			if(split.length>3) {
				fileVersion.add(split[1]);
			}
		}
		Collections.sort(fileVersion);
//		转码后的字符串，最后要写入到流中的数据
		String encode =null;
		Version version=new Version();
		for (File file2 : fileList) {
			if(file2.getName().contains(fileVersion.get(fileVersion.size()-1))) {
				String[] split = file2.getName().split("-");
				version.setAppname(split[0]);
				version.setAppversion(split[1]);
				String vJson = JsonUtils.objectToJson(version);
				encode = encoder.encode(vJson.getBytes());
			}
		}
		if(encode!=null) {
			writer.write(encode);
		}
		writer.close();
	}
	
	/**
	 * 跳转到上传安装包页面
	 * @return
	 */
	@RequestMapping("/toUpload")
	public String toUpload() {
		
		return "/fileUpload";
	}
	/**
	 * 文件上传
	 * @param file 安装包文件
	 * @param request
	 * @param version 版本信息
	 * @return
	 */
	@RequestMapping("/updateFile")
	public String updateFile(@RequestParam(value="file") MultipartFile file,HttpServletRequest request,Version version){
		String fileName = file.getOriginalFilename();
		if(fileName==null) {
			return "/error/404";
		}
		if(!fileName.contains(version.getAppname())||!fileName.contains(version.getAppversion())) {
			return "/error/404";
		}
		if(fileName.substring(fileName.lastIndexOf(".")).equals("exe")) {
			return "/error/404";
		}
		String realPath = request.getServletContext().getRealPath("/upload");
		String newFileName=fileName;
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
		
		
       /* DownFileInfoBean bean = new DownFileInfoBean(null, request.getServletContext().getRealPath("/upload"), 
        		file.getOriginalFilename(), 5,false,f);
        DownFileFetch fileFetch;
		try {
			fileFetch = new DownFileFetch(bean);
			 fileFetch.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  */
        
		
		return "/success/200";
	}
	
	@RequestMapping("/success")
	public String success() {
		return "/success/200";
	}
	
	@RequestMapping("/error")
	public String error() {
		return "/error/404";
	}

}