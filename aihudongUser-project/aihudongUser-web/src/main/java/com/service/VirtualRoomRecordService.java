package com.service;

import java.util.List;
import java.util.Map;

import com.model.VirtualRoomRecord;

public interface VirtualRoomRecordService {
	List<VirtualRoomRecord> selectVRR(Map<String,Object> map);
	
    int deleteByPrimaryKey(VirtualRoomRecord virtualRoomRecord);

    int insertSelective(VirtualRoomRecord virtualRoomRecord);

    VirtualRoomRecord selectByPrimaryKey(VirtualRoomRecord virtualRoomRecord);
    
    int updateByPrimaryKeySelective(VirtualRoomRecord virtualRoomRecord);
}
