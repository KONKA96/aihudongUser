package com.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProduceId {
	
	public static String produceUserId(List<String> idList){
//		如果缺少的id为1
		if(Integer.parseInt(idList.get(0).substring(2))!=1){
			return idList.get(0).substring(0,2)+"1";
		}
		String role=idList.get(0).substring(0,2);
		List<Integer> indexList=new ArrayList<Integer>();
		for (int i=0; i<idList.size();i++) {
			indexList.add(Integer.parseInt(idList.get(i).substring(2)));
		}
		Collections.sort(indexList);
		for (int j=0;j<indexList.size();j++) {
//			中间不缺少id，新增的id为最后一条记录的下一个
			if(j==indexList.size()-1){
				return role+(indexList.get(j)+1);
			}
//			中间缺少id，新增的id为中间缺少的位置
			if(indexList.get(j+1)-indexList.get(j)!=1){
				return role+(indexList.get(j)+1);
			}
		}
		
		return "";
	}
}
