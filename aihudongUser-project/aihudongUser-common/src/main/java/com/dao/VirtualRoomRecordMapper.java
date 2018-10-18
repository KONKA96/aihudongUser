package com.dao;

import java.util.List;
import java.util.Map;

import com.model.VirtualRoomRecord;

public interface VirtualRoomRecordMapper {
	List<VirtualRoomRecord> selectVRR(Map<String,Object> map);
	
    int deleteByPrimaryKey(Integer id);

    int insert(VirtualRoomRecord record);

    int insertSelective(VirtualRoomRecord record);

    VirtualRoomRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VirtualRoomRecord record);

    int updateByPrimaryKey(VirtualRoomRecord record);
}