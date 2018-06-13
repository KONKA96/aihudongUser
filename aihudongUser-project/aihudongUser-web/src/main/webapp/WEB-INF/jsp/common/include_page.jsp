<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="col-sm-6">
		<div class="dataTables_info">显示
			${start<0 ? '0' : start } 到 ${total }
			项，共 ${totalCount } 项</div>
	</div>
	<div class="col-sm-6">
		<div class="dataTables_paginate paging_simple_numbers">
			<ul class="pagination">
				<li class="paginate_button previous"><a href="<%=request.getParameter("pageTitle")%>?index=1">首页</a>
				<li class="paginate_button previous"><a href="<%=request.getParameter("pageTitle")%>?index=${index-1}">上一页</a>
				</li>
					<c:if test="${index>1 }">
						<li class="paginate_button">
							<a href="<%=request.getParameter("pageTitle")%>?index=${index-1<1 ? 1 :index-1}">${index-1 }</a>
						</li>
					</c:if>
					<li class="paginate_button">
						<a href="<%=request.getParameter("pageTitle")%>?index=${index}">${index }</a>
					</li>
					<c:if test="${end>=index+1 }">
						<li class="paginate_button">
							<a href="<%=request.getParameter("pageTitle")%>?index=${index+1>end ? end : index+1}">${index+1 }</a>
						</li>
					</c:if>
				<li class="paginate_button next"><a href="<%=request.getParameter("pageTitle")%>?index=${index+1>end ? index : index+1}">下一页</a></li>
				<li class="paginate_button previous"><a href="<%=request.getParameter("pageTitle")%>?index=${end}">末页</a>
			</ul>
		</div>
	</div>
</div>

<script>
	//page为当前页
	function searchForm(page){
		//将id为currentPage的input标签的值 修改为
		//传递进来的page
		$("#currentPage").val(page);
		//将id为searchForm的表单，提交
		$("#searchForm").submit();
	}
</script>