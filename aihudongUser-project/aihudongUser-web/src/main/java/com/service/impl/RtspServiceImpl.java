package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RtspMapper;
import com.model.Rtsp;
import com.service.RtspService;

@Service
public class RtspServiceImpl implements RtspService {
	
	@Autowired
	private RtspMapper rtspMapper;

	@Override
	public List<Rtsp> selectAllRtsp(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rtspMapper.selectAllRtsp(map);
	}

	@Override
	public int deleteByPrimaryKey(Rtsp rtsp) {
		// TODO Auto-generated method stub
		return rtspMapper.deleteByPrimaryKey(rtsp.getId());
	}

	@Override
	public int insertSelective(Rtsp rtsp) {
		// TODO Auto-generated method stub
		return rtspMapper.insertSelective(rtsp);
	}

	@Override
	public Rtsp selectByPrimaryKey(Rtsp rtsp) {
		// TODO Auto-generated method stub
		return rtspMapper.selectByPrimaryKey(rtsp.getId());
	}

	@Override
	public int updateByPrimaryKeySelective(Rtsp rtsp) {
		// TODO Auto-generated method stub
		return rtspMapper.updateByPrimaryKeySelective(rtsp);
	}

}
