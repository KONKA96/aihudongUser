package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.EnterpriseMapper;
import com.model.Enterprise;
import com.service.EnterpriseService;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
	
	@Autowired
	private EnterpriseMapper enterpriseMapper;

	@Override
	public List<Enterprise> selectAllEnterprise(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return enterpriseMapper.selectAllEnterprise(map);
	}

	@Override
	public int deleteByPrimaryKey(Enterprise enterprise) {
		// TODO Auto-generated method stub
		return enterpriseMapper.deleteByPrimaryKey(enterprise.getId());
	}

	@Override
	public int insertSelective(Enterprise enterprise) {
		// TODO Auto-generated method stub
		return enterpriseMapper.insertSelective(enterprise);
	}

	@Override
	public Enterprise selectByPrimaryKey(Enterprise enterprise) {
		// TODO Auto-generated method stub
		return enterpriseMapper.selectByPrimaryKey(enterprise.getId());
	}

	@Override
	public int updateByPrimaryKeySelective(Enterprise enterprise) {
		// TODO Auto-generated method stub
		return enterpriseMapper.updateByPrimaryKeySelective(enterprise);
	}

}
