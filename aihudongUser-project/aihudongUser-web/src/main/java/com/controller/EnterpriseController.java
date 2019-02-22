package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Enterprise;
import com.model.User;
import com.service.EnterpriseService;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {
	
	@Autowired
	private EnterpriseService enterpriseService;
	
	@RequestMapping("/selectEnterprise")
	public String selectEnterprise() {
		return "";
	}
	
	/**
	 * 修改公司资料
	 * @author KONKA
	 * @param enterprise
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateEnterprise")
	public String updateEnterprise(Enterprise enterprise,HttpSession session) {
		if(enterprise.getId()==null) {
			return "";
		}else {
			if(enterpriseService.updateByPrimaryKeySelective(enterprise)>0) {
				User user = (User) session.getAttribute("user");
				user.setEnterprise(enterprise);
				session.setAttribute("user", user);
				return "success";
			}
		}
		
		return "";
	}

}
