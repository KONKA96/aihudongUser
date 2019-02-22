package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Logger;
import com.model.Rtsp;
import com.service.RtspService;
import com.util.JsonUtils;

@Controller
@RequestMapping("/rtsp")
public class RtspController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private RtspService rtspService;
	
	/**
	 * 查询rtsp
	 * @author KONKA
	 * @param rtsp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectAllRtsp", produces = { "text/json;charset=UTF-8" })
	public String selectAllRtsp(Rtsp rtsp) {
		//查询参数集合
		Map<String,Object> map = new HashMap<>();
		//返回参数集合
		Map<String, Object> argMap = new HashMap<>();
		
		if(rtsp!=null && rtsp.getId()!=null) {
			map.put("id", rtsp.getId());
		}
		if(rtsp!=null && rtsp.getRtspName()!=null) {
			map.put("rtspName", rtsp.getRtspName());
		}
		List<Rtsp> rtspList = rtspService.selectAllRtsp(map);
		
		if(rtspList!=null && rtspList.size()!=0) {
			argMap.put("code", 200);
			argMap.put("rtspList", rtspList);
			return JsonUtils.objectToJson(argMap);
		}
		
		return "";
	}

}
