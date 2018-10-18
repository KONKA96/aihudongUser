package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.VirtualRoomRecordMapper;
import com.model.VirtualRoomRecord;
import com.service.VirtualRoomRecordService;

@Service
public class VirtualRoomRecordServiceImpl implements VirtualRoomRecordService {

	@Autowired
	private VirtualRoomRecordMapper virtualRoomRecordMapper;
	
	@Override
	public List<VirtualRoomRecord> selectVRR(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return virtualRoomRecordMapper.selectVRR(map);
	}

	@Override
	public int deleteByPrimaryKey(VirtualRoomRecord virtualRoomRecord) {
		// TODO Auto-generated method stub
		return virtualRoomRecordMapper.deleteByPrimaryKey(virtualRoomRecord.getId());
	}

	@Override
	public int insertSelective(VirtualRoomRecord virtualRoomRecord) {
		// TODO Auto-generated method stub
		return virtualRoomRecordMapper.insertSelective(virtualRoomRecord);
	}

	@Override
	public VirtualRoomRecord selectByPrimaryKey(VirtualRoomRecord virtualRoomRecord) {
		// TODO Auto-generated method stub
		return virtualRoomRecordMapper.selectByPrimaryKey(virtualRoomRecord.getId());
	}

	@Override
	public int updateByPrimaryKeySelective(VirtualRoomRecord virtualRoomRecord) {
		// TODO Auto-generated method stub
		return virtualRoomRecordMapper.updateByPrimaryKeySelective(virtualRoomRecord);
	}


}
