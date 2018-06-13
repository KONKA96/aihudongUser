package com.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProduceUsername4 {
	public static List<String> produceScreenUsername(List<String> usernameList,int num){
//		存放username的集合
		List<String> usernameNewList=new ArrayList<>();
		
		List<Integer> usrInteger=new ArrayList<>();
		
		for(String username:usernameList){
			usrInteger.add(Integer.parseInt(username));
		}
		Collections.sort(usrInteger);
		// 如果没有屏幕记录，则新建
		if (usernameList.size() == 0) {
			for (int i = 1; i <= num; i++) {
				usernameNewList.add("000" + i);
			}
			return usernameNewList;
		}
//		产生username
		for (int i=1;i <= usrInteger.size();i++) {
//			遍历到最后，则往后面再生成剩下的用户名
			if(i==usrInteger.size()-1 && num!=0){
				while(true){
					i++;
					if(!usrInteger.contains(i)){
						usernameNewList.add("000" + i);
						num--;
						if(num==0)
							break;
					}
				}
				break;
			}
//			
			if(!usrInteger.contains(i)){
				usernameNewList.add("000"+i);
				num--;
			}
		}
//		将其中的用户名格式化
		if(usernameNewList.size()!=0){
			for(int i=0;i<usernameNewList.size();i++){
				String userName = usernameNewList.get(0);
				usernameNewList.remove(0);
				userName=userName.substring(userName.length()-4);
				usernameNewList.add(userName);
			}
		}
		return usernameNewList;
	}
}
