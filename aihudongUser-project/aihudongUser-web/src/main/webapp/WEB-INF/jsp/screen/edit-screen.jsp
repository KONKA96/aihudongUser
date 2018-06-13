<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>屏幕</title>
    <jsp:include page="../common/include_css.jsp" />
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><small>屏幕</small></h5>
                        <div class="ibox-tools">
                        	<button class="btn btn-primary" onclick="goback()">返回</button>
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal" id="editForm">
                        	<input type="hidden" name="id" value="${screen.id }">
                        	<div class="form-group">
                                <label class="col-sm-2 control-label">屏幕登录名</label>
                                <div class="col-sm-10">
                                    <input id="username" name="username" value="${screen.username }" type="text" class="form-control" placeholder="屏幕登录名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">屏幕登录密码</label>
                                <div class="col-sm-10">
                                    <input id="password" name="password" value="${screen.password }" type="text" class="form-control" placeholder="屏幕登录密码">
                                </div>
                            </div>
                        	
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">房间</label>
                                <div class="col-sm-10">
                                    <select id="roomSelected" class="form-control m-b" name="roomId">
                                    	<c:forEach items="${roomList }" var="room">
                                    		<c:if test="${screen.roomId==room.id }"><option value="${room.id }">--${room.num }--</option></c:if>
                                    	</c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="button" onclick="updateInfo()">保存</button>
                                    <button class="btn btn-white" type="reset">取消</button>
                                </div>
                            </div>
                        </form> 
                    </div>
                </div>
            </div>
        </div>
    </div>
	
	<jsp:include page="../common/include_js.jsp" />

</body>
<script type="text/javascript">
	$(document).keydown(function(event){
		if(event.keyCode==13){
			updateInfo();
		}
	});

	$(document).ready(function () {
	    $('.i-checks').iCheck({
	        checkboxClass: 'icheckbox_square-green',
	        radioClass: 'iradio_square-green',
	    });
	});
	
	function updateInfo(){
		if($("#username")[0].value==""){
			alert("用户名必填");
			return false;
		}else if($("#password")[0].value==""){
			alert("密码必填");
			return false;
		}else if($("#roomSelected")[0].value==""){
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
				}else if(data=='same'){
					alert("用户名不能相同！");
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
