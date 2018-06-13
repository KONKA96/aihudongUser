package com.service;

import java.util.List;
import java.util.Map;

import com.model.Record;

public interface RecordService {
	/*List<Record> selectAllScreenRecord(Record record);*/
	
	Map<String,Integer> selectRecord(Map<String,Object> map);
	
	int insertSelective(Record record);
	
	int updateByPrimaryKeySelective(Record record);

	Record selectByPrimaryKey(Record record);

}
