package com.controller;

import com.model.Logger;
import com.model.Record;
import com.model.User;
import com.service.RecordService;
import com.service.UserService;
import com.util.HttpUtil;
import com.util.JsonUtils;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author KONKA
 *	微信扫码登录
 *
 */
@Controller
@RequestMapping({ "/weichat" })
public class WeChatLoginController {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Value("${appid}")
	private String appid;
	@Value("${redirect_uri}")
	private String redirect_uri;
	@Value("${appSecret}")
	private String appSecret;
	@Autowired
	private UserService userService;
	@Autowired
	private RecordService recordService;

	@RequestMapping({ "/toIndex" })
	public String toIndex() {
		return "/info";
	}

	@RequestMapping({ "/weiChatLogin" })
	public String weiChatLogin() {
		return "/info2";
	}

	/**
	 * 获取二维码
	 * @author KONKA
	 * @param session
	 * @param response
	 * @param state
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getWeiChatInfo" }, produces = { "text/json;charset=UTF-8" })
	public String getWeiChatInfo(HttpSession session, HttpServletResponse response, @RequestParam(required = false) String state) {
		response.setHeader("Access-Control-Allow-Origin", "*");

		String url = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
		url = url.replace("APPID", this.appid);
		url = url.replace("REDIRECT_URI", this.redirect_uri);
		if(state!=null && !"".equals(state))
			url = url.replace("STATE", state);
		return JsonUtils.objectToJson(url);
	}

	/**
	 * 获取AccessToken
	 * @author KONKA
	 * @param code
	 * @param state
	 * @param session
	 * @param modelMap
	 * @return
	 */
	@RequestMapping({ "/getAccessToken" })
	public String getAccessToken(@RequestParam String code, @RequestParam String state, HttpSession session,
			ModelMap modelMap,HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		ServletContext servletContext = session.getServletContext();
		
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String param = "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		param = param.replace("APPID", this.appid);
		param = param.replace("SECRET", this.appSecret);
		param = param.replace("CODE", code);
		String sendGET = HttpUtil.sendGET(url, param);
		JSONObject jsonObject = JSONObject.fromObject(sendGET);
		//String refresh_token = jsonObject.getString("refresh_token");
		String openid = jsonObject.getString("openid");
		String access_token = jsonObject.getString("access_token");
		String unionid = jsonObject.getString("unionid");
		
		System.out.println(unionid);

		String userInfo = getUserInfo(access_token, openid);
		logger.info("用户信息"+userInfo);
		/*String testAccessTokenAuth = testAccessTokenAuth(access_token, openid);
		System.out.println(testAccessTokenAuth);*/

		User user = new User();
		Record record = new Record();
		user.setOpenId(openid);
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("openId", openid);
		List<User> userList = userService.selectAllUser(hashMap);
		if(userList==null || userList.size()==0) {
			modelMap.put("code", Integer.valueOf(200));
			modelMap.put("serverhost", state);
			modelMap.put("openid", openid);
			modelMap.put("message", "您未绑定账号，请先绑定！");
			return "redirect:https://" + state + "/html5client/login";
		}
		else {
			user = userList.get(0);
			record.setUserId(user.getId());
			record.setRole(Integer.valueOf(1));
			modelMap.put("role", Integer.valueOf(1));
			modelMap.put("username", user.getUsername());
		}
		/*
			//统计在线人数
			Object tcount = servletContext.getAttribute("tcount");
			if(tcount==null) {
				servletContext.setAttribute("tcount", 1);
			}else {
				servletContext.setAttribute("tcount", tcount.toString()+1);
			}
		*/
		modelMap.put("code", Integer.valueOf(200));
		modelMap.put("serverhost", state);
		session.setMaxInactiveInterval(-1);
		session.setAttribute("count", Integer.valueOf(0));

		session.setAttribute("startTime", new Date());

		record.setStartTime(new Date());
		this.recordService.insertSelective(record);

		session.setAttribute("recordId", record.getId());
		session.setAttribute("startTime", record.getStartTime());
		session.setAttribute("role", record.getRole());
		session.setAttribute("userId", record.getUserId());

		return "redirect:https://" + state + "/html5client/login";
	}

	public void reflushAccessToken(String refresh_token) {
		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
		String param = "appid=APPID&grant_type=refresh_token&refresh_token=" + refresh_token;
		param = param.replace("APPID", this.appid);
		HttpUtil.sendGET(url, param);
	}

	public String getUserInfo(String access_token, String openId) {
		String url = "https://api.weixin.qq.com/sns/userinfo";
		String param = "access_token=" + access_token + "&openid=" + openId;
		return HttpUtil.sendGET(url, param);
	}

	public String testAccessTokenAuth(String access_token, String openId) {
		String url = "https://api.weixin.qq.com/sns/auth";
		String param = "access_token=" + access_token + "&openid=" + openId;
		return HttpUtil.sendGET(url, param);
	}

	public String bindUsername(String openId, ModelMap modelMap) {
		modelMap.addAttribute(openId);

		return "";
	}

}
