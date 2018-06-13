<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户管理</title>
    <jsp:include page="../common/include_css.jsp" />
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><small>用户管理</small></h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<div  class="dataTables_wrapper form-inline" role="grid">
                    		<!-- 查询条件 -->
	                    	<form method="post" id="searchForm" action="/aihudongUser-web/user/selectAllUser">
		                    	<div class="row">
		                    		<c:if test="${ sessionScope.user.role==1}">
		                    		<div class="col-sm-2">
		                    			<div class="dataTables_length">
		                    				<a href="/aihudongUser-web/user/toAddUser" class="btn btn-primary ">新增用户</a>
		                    			</div>
		                    		</div>
		                    		</c:if>
		                    		<div class="col-sm-10">
		                    			<%-- <div class="input-group">
		                    				<!-- 根据院系、专业查询 -->
		                    				<select class="form-control" onchange="changeSubject(this)" name="facultyId">
		                    					<option value="">--请选择--</option>
			                                    <c:forEach items="${facultyList }" var="faculty">
			                                    	<option value="${faculty.id}" ${student.subject.faculty.id==faculty.id ? 'selected' : '' }>${faculty.facultyName }</option>
			                                    </c:forEach>
                                    		</select>
		                    			</div> --%>
		                    			<%-- <div class="input-group">
		                    				<select class="form-control" id="subjectSelected" name="subjectId">
		                    					<option value="">--请选择--</option>
		                    					<c:forEach items="${facultyList }" var="faculty">
													<c:if test="${requestScope.student.subject.faculty.id==faculty.id}">
														<c:forEach items="${faculty.subjectList }" var="subject">
															<option value="${subject.id}"
																${requestScope.student.subjectId==subject.id ? 'selected' : '' }>${subject.subjectName }</option>
														</c:forEach>
													</c:if>
												</c:forEach>
                                    		</select>
		                    			</div> --%>
		                    			
		                    			<div class="input-group" style="float:right;">
		                    				<!-- 真实姓名 -->
				                            <input type="text" name="truename" value="${user.truename }" class="form-control" placeholder="关键字查找">
				                            <div class="input-group-btn">
				                                <input type="submit"  class="btn btn-primary" value="搜索">
				                            </div>
				                        </div>
		                    		</div>
		                    	</div>
	                    	</form>
                    		<!-- 查询条件结束 -->
	                        <table class="table table-striped table-bordered table-hover dataTables-example">
	                            <thead>
	                                <tr>
	                                	<th>用户名</th>
	                                	<th>真实姓名</th>
	                                    <th>联系方式</th>
	                                    <th>性别</th>
	                                    <c:if test="${sessionScope.user.role==0 }">
	                                    <th>剩余屏幕数量</th>
	                                    <th>使用时长</th>
	                                    </c:if>
	                                    <th>备注</th>
	                                    <th>操作</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            	<c:forEach items="${userList }" var="uu">
	                            		<tr class="gradeA">
	                            			 <td>${uu.username }</td>
	                            			 <td>${uu.truename }</td>
		                            		 <td>${uu.telephone }</td>
		                            		 <td>
		                            		 	<c:if test="${uu.sex==0 }">男</c:if>
		                            		 	<c:if test="${uu.sex==1 }">女</c:if>
		                            		 </td>
		                            		 <c:if test="${sessionScope.user.role==0 }">
		                            		 	<td>${uu.screenNum }</td>
		                            		 	<td>${uu.duration }</td>
		                            		 </c:if>
		                            		 <td>${uu.remake }</td>
			                                 <td>
			                                 	<a href="/aihudongUser-web/user/toUpdate?id=${uu.id }"><i style="margin-left:5px;" class="fa fa-edit"></i></a>
			                                 	<a href="javascript:;" onclick="deleteUser('${uu.id }')"><i style="margin-left:5px;" class="fa fa-trash"></i></a>
			                                 	<c:if test="${sessionScope.user.role==0 }">
			                                 	<a onclick="addNum('${uu.id }');"><i style="margin-left:5px;" class="fa fa-plus"></i></a>
			                                 	</c:if>
			                                 </td>
		                            	</tr>
		                            </c:forEach>
	                            </tbody>
	                        </table>
							<!-- 分页 -->
							<jsp:include page="../common/include_page.jsp">
 								<jsp:param value="/aihudongUser-web/user/selectAllUser" name="pageTitle"/>
							</jsp:include>
							<!-- 分页结束 -->
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../common/include_js.jsp" />

</body>
<script type="text/javascript">
function addNum(id){
	swal({   
		title: "修改使用屏幕数量",   
		text: "",   
		type: "input",   
		showCancelButton: true,   
		closeOnConfirm: false,   
		animation: "slide-from-top",   
		inputPlaceholder: "属性值",
		confirmButtonText: "确定",
        cancelButtonText: "取消",
		//inputValue:"123"  //回显时使用该属性
	}, 
	function(inputValue){   
		if (inputValue === false) 
			return false;      
		if (inputValue === "") {     
			swal.showInputError("属性值不能为空!");     
			return false   
		}else{
			//在这里触发ajax进行新增
			$.ajax({
				url:"/aihudongUser-web/user/addScreenNum?num="+inputValue+"&id="+id,
				/* data:"attrValue"+inputValue+"&attrId="+id, */
				type:"post",
				success:function(data){
					if(data=='success'){
						alert("操作成功！");
						window.location="/aihudongUser-web/user/selectAllUser";
					}else{
						alert("操作失败");
					}
				}
			})
		}
		
	});
}


//JS校验form表单信息  
function checkData(){  
   var fileDir = $("#upfile").val();  
   var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
   if("" == fileDir){  
       alert("选择需要导入的Excel文件！");  
       return false;  
   }  
   if(".xls" != suffix && ".xlsx" != suffix ){  
       alert("选择Excel格式的文件导入！");  
       return false;  
   }  
   return true;  
} 

	function deleteUser(id){
		var f=window.confirm("你确定要删除这项吗？");
		if(f){
			$.ajax({
				url:"/aihudongUser-web/user/deleteUser",
				data:"id="+id,
				type:"post",
				success:function(data){
					if(data=='success'){
						alert("操作成功！");
						window.location="/aihudongUser-web/user/selectAllUser";
					}else{
						alert("操作失败");
					}
				}
			})
		}
		
	}
</script>
</html>
