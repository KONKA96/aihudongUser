package com.service;

import java.util.List;
import java.util.Map;

import com.model.Rtsp;

public interface RtspService {
	
	List<Rtsp> selectAllRtsp(Map<String,Object> map);
	
    int deleteByPrimaryKey(Rtsp rtsp);

    int insertSelective(Rtsp rtsp);

    Rtsp selectByPrimaryKey(Rtsp rtsp);

    int updateByPrimaryKeySelective(Rtsp rtsp);
}
