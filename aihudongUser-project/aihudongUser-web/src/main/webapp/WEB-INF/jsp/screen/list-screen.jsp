<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>屏幕管理</title>
<jsp:include page="../common/include_css.jsp" />
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							<small>屏幕管理</small>
						</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<div class="dataTables_wrapper form-inline" role="grid">
							<!-- 查询条件 -->
							<form method="post" id="searchForm"
								action="/aihudongUser-web/screen/selectAllScreen">
								<div class="row">
									<div class="col-sm-2">
										<div class="dataTables_length">
											<a href="/aihudongUser-web/screen/toUpdateScreen"
												class="btn btn-primary ">新增屏幕</a>
										</div>
									</div>
									<div class="col-sm-10">

										<div class="input-group" style="float: right;">
											<!-- 关键字 -->
											<input type="text" name="duration"
												value="${screen.duration }" class="form-control"
												placeholder="关键字查找">
											<div class="input-group-btn">
												<input type="submit" class="btn btn-primary" value="搜索">
											</div>
										</div>
									</div>
								</div>
							</form>
							<!-- 查询条件结束 -->
								<form method="POST" enctype="multipart/form-data" id="form1"
									action="/aihudong-web/screen/uploadScreenExcel">
									<table>
										<tr>
											<!-- <td><label for="upfile" class="btn btn-success">Excel批量导入</label></td>
											<td><input id="upfile" type="file" name="upfile"
												class="btn btn-info" style="display: none"></td>
											<td><input type="submit" value="提交"
												onclick="return checkData()" class="btn btn-primary"></td> -->
											<td><a href="/aihudongUser-web/screen/exportExcel" class="btn btn-success">Excel批量导出</a></td>
										</tr>
									</table>
								</form>
							<table
								class="table table-striped table-bordered table-hover dataTables-example">
								<thead>
									<tr>
										<th>房间</th>
										<th>屏幕账号</th>
										<th>屏幕分配者</th>
										<th>使用总时长</th>
										<th>使用总人次</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${screenList }" var="screen">
										<tr class="gradeA">
											<td>${screen.room.num }</td>
											<td>${screen.username }</td>
											<td>${screen.user.username }</td>
											<td>${screen.duration }</td>
											<td>${screen.times }</td>
												<td><a
													href="/aihudongUser-web/screen/toUpdateScreen?id=${screen.id }"><i
														style="margin-left: 5px;" class="fa fa-edit"></i></a> <a
													href="javascript:;" onclick="deleteScreen('${screen.id }')"><i
														style="margin-left: 5px;" class="fa fa-trash"></i></a> 
														<a
													href="/aihudongUser-web/room/selectRoomById?id=${screen.roomId }"><i
														style="margin-left: 5px;" class="fa fa-home"></i></a>
														</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- 分页 -->
							<jsp:include page="../common/include_page.jsp">
								<jsp:param value="/aihudongUser-web/screen/selectAllScreen"
									name="pageTitle" />
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


	function deleteScreen(id){
		var f=window.confirm("你确定要删除这项吗？");
		if(f){
			$.ajax({
				url:"/aihudongUser-web/screen/deleteScreen",
				data:"id="+id,
				type:"post",
				success:function(data){
					if(data=='success'){
						alert("操作成功！");
						window.location="/aihudongUser-web/screen/selectAllScreen";
					}else{
						alert("操作失败");
					}
				}
			})
		}
		
	}
</script>
</html>
