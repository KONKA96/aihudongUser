package com.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.AesCbcUtil;
import com.util.HttpUtil;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

@RequestMapping("/xiaoChengXu")
@Controller
public class WeChatXiaoChengXuController {
	
	Logger logger = Logger.getLogger(this.getClass());
 
	@Value("${xiaochengxuAppId}")
	private String xiaochengxuAppId;
	@Value("${xiaochengxuAppSecret}")
	private String xiaochengxuAppSecret;
	
	/**
	 * 通过code访问接口获取参数
	 * @author KONKA
	 * @param js_code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/code2accessToken", produces = { "text/json;charset=UTF-8" })
	public String code2accessToken(String js_code) {
		
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		String param = "appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
		
		param = param.replace("APPID", xiaochengxuAppId);
		param = param.replace("SECRET", xiaochengxuAppSecret);
		param = param.replace("JSCODE", js_code);
		
		String sendGET = HttpUtil.sendGET(url, param);
		JSONObject jsonObject = JSONObject.fromObject(sendGET);
		
		return sendGET;
	}
	
	/**
	 * 获取用户信息（unionid）
	 * @author KONKA
	 * @param encryptedData
	 * @param session_key
	 * @param iv
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUserInfo", produces = { "text/json;charset=UTF-8" })
	public String getUserInfo(String encryptedData,String session_key,String iv) {
		
		String decrypt = "";
		try {
			decrypt = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
			logger.info(decrypt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return decrypt;
	}
}
