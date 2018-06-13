package com.dao;

import java.util.Map;

import com.model.Record;

public interface RecordMapper {
	Integer selectRecord(Map<String,Object> map);
	
    int deleteByPrimaryKey(Integer id);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);
}