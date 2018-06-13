<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>屏幕转移</title>
<jsp:include page="../common/include_css.jsp" />
<jsp:include page="../common/include_js.jsp" />
</head>
<body>
	<br />
	<br />
	<button class="btn btn-primary" onclick="goback()">返回</button>
	<form class="form-horizontal" id="editForm">
	<input type="hidden" name="id" value="${screen.id }" />
	<div
		style="width: 700px; height: 500px; text-align: center; margin: 0 auto;">
		屏幕ID：<input type="text" name="id" class="btn btn-primary" disabled="disabled"
			value="${screen.id }" />
		<div>
			<br />
			<br />
			<div>
					<i style="margin-left: 5px;" class="fa fa-arrow-right"></i>
				<div class="form-group" style="display:inline-block;">
					<c:forEach items="${roomList }" var="room">
						<c:if test="${screen.roomId==room.id }">
							<input type="text" class="btn btn-primary" disabled="disabled" value="${room.num }" />
						</c:if>
					</c:forEach>
				</div>

			</div>
			<br />
			<br /> 
			<!-- <i style="margin-left: 5px;" class="fa fa-arrow-right"></i> -->
			<div class="form-group" style="display:inline-block;">
					<select id="roomSelected" class="form-control m-b" name="roomId" style="width:150px;">
						<option value="">--请选择--</option>
						<c:forEach items="${roomList }" var="room">
                            <option value="${room.id }">--${room.num }--</option>
                        </c:forEach>
					</select>
			</div>
		</div>
		<br /> <button class="btn btn-primary" type="button" onclick="updateInfo()">保存</button>
		<button type="button" class="btn btn-default">取消</button>
	</div>
	</form>
</body>
<script type="text/javascript">
function updateInfo(){
	if($("#roomSelected")[0].value==""){
		alert("房间必选");
		return false;
	}
	
	$.ajax({
		url:"/aihudongUser-web/screen/updateScreen",
		data:$("#editForm").serialize(),
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

function goback(){
	window.history.back();
}
</script>
</html>