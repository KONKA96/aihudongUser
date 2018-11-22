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
	public void testPost() {
		String url = "http://celjwmanager.buct.edu.cn/openapi/ssoservice";
		Map<String,Object> params = new HashMap<>();
		params.put("tenant", "90001");
		params.put("method", "getToken");
		HashMap<String, String> data = new HashMap<>();
		data.put("userName", "sysmanager");
		data.put("tenant", "90001");
		data.put("sign", "4ec9d48487810ca16159e2a2838bab1af186dd0f");
		data.put("appId", "hgdx90001");
		
		params.put("data", data);
		String sendPost = sendPost(url, params);
		System.out.println(sendPost);
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
	
	
	public String sendPost(String url, Map<String, Object> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if(param.length()>0){
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    //System.out.println(entry.getKey()+":"+entry.getValue());
                }
                //System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
