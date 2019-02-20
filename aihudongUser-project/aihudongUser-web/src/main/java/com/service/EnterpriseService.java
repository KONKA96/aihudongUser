package com.service;

import java.util.List;
import java.util.Map;

import com.model.Enterprise;

public interface EnterpriseService {
	
	List<Enterprise> selectAllEnterprise(Map<String,Object> map);
	
	int deleteByPrimaryKey(Enterprise enterprise);

    int insertSelective(Enterprise enterprise);

    Enterprise selectByPrimaryKey(Enterprise enterprise);

    int updateByPrimaryKeySelective(Enterprise enterprise);

}
