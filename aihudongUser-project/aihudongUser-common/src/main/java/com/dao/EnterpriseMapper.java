package com.dao;

import java.util.List;
import java.util.Map;

import com.model.Enterprise;

public interface EnterpriseMapper {
	List<Enterprise> selectAllEnterprise(Map<String,Object> map);
	
    int deleteByPrimaryKey(Integer id);

    int insert(Enterprise record);

    int insertSelective(Enterprise record);

    Enterprise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Enterprise record);

    int updateByPrimaryKey(Enterprise record);
}