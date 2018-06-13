package com.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;
/**
 * 分页工具类
 * @author Administrator
 * 	Integer start	起始项的索引
 *	Integer end		总页数
 *	Integer total	结尾项的索引
 *	Integer totalCount		当前集合的总条数
 *	Integer index	当前页码数或者说要跳转的页面数
 */
@Component
public class PageUtil {
	Integer start;
	Integer end;
	Integer total;
	Integer totalCount;
	Integer index;
	
	public void setPageInfo(Page<?> list,Integer index,Integer pageSize,HttpServletRequest request){
		//如果要跳转的页码数小于0则跳转首页
		if(index<=0){
			index=1;
		}
		//获取末页数
		end=(int) list.getPages();
		//如果要跳转的页数大于末页，则跳转末页
		if(index>=end){
			index=end;
			total=(int) list.getTotal();
		}else{
			total=index*pageSize;
		}
		//该页起始项的索引
		start=(index-1)*pageSize+1;
		//获取总的信息数目
		totalCount=(int) list.getTotal();
		this.index=index;
		
		
		request.setAttribute("index", index);
		request.setAttribute("end", end);
		request.setAttribute("start", start);
		request.setAttribute("total", total);
		request.setAttribute("totalCount", totalCount);
	}
}
