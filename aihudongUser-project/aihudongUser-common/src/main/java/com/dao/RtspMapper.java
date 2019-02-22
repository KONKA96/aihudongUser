package com.dao;

import java.util.List;
import java.util.Map;

import com.model.Rtsp;

public interface RtspMapper {
	List<Rtsp> selectAllRtsp(Map<String,Object> map);
	
    int deleteByPrimaryKey(Integer id);

    int insert(Rtsp record);

    int insertSelective(Rtsp record);

    Rtsp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rtsp record);

    int updateByPrimaryKey(Rtsp record);
}