package com.util;

import java.util.List;
import java.util.Random;

/**
 * 产生虚拟教室的房间id
 * 要求六位数字不重复
 * @author KONKA
 *
 */
public class ProduceVirtualRoomIdUtil {
	
	public String ProduceVirtualRoomId(List<String> idList) {
		String roomId=null;
		Random random = new Random();
		A:while(true) {
			//随机产生6位数字[100000,999999]
			Integer nextInt = (Integer)random.nextInt(900000)+100000;
			int i=0;
			for (String string : idList) {
				if(string.equals(String.valueOf(nextInt))) {
					break;
				}else {
					i++;
				}
				
				if(i==idList.size()) {
					roomId=String.valueOf(nextInt);
					break A;
				}
			}
		}
		return roomId;
	}

}
