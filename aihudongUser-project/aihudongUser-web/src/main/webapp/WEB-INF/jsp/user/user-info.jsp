<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户</title>
    <jsp:include page="../common/include_css.jsp" />
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><small>用户</small></h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal" id="editForm">
                        	<input type="hidden" name="id" value="${user.id }">
                        	<div class="form-group">
                                <label class="col-sm-2 control-label">用户名</label>

                                <div class="col-sm-10">
                                    <input name="username" value="${user.username }" type="text" class="form-control" disabled="disabled" placeholder="用户名">
                                </div>
                            </div>
                            <%-- <div class="form-group">
                                <label class="col-sm-2 control-label">密码</label>
                                <div class="col-sm-10">
                                    <input name="password" value="${user.password }" type="text"  class="form-control" placeholder="密码">
                                </div>
                            </div> --%>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">真实姓名</label>
                                <div class="col-sm-10">
                                    <input name="truename" value="${user.truename }" type="text" class="form-control" placeholder="真实姓名">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">联系方式</label>
                                <div class="col-sm-10">
                                    <input name="telephone" value="${user.telephone }" type="text" class="form-control" placeholder="联系方式">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">电子邮箱</label>
                                <div class="col-sm-10">
                                    <input name="email" value="${user.email }" type="text" class="form-control" placeholder="电子邮箱">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">性别</label>
                                <div class="col-sm-10">
                                    <select class="form-control m-b" name="sex">
                                    		<option value="0" ${user.sex==0 ? 'selected' :'' }>男</option>
                                    		<option value="1" ${user.sex==1 ? 'selected' :'' }>女</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">屏幕数</label>
                                <div class="col-sm-10">
                                    <input name="screenNum" value="${user.screenNum }" type="text" disabled="disabled" class="form-control" placeholder="屏幕数">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">邀请码</label>
                                <div class="col-sm-10">
                                    <input name="inviteCode" value="${user.inviteCode }" type="text" disabled="disabled" class="form-control" placeholder="邀请码">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="button" onclick="updateInfo()">保存</button>
                                    <button class="btn btn-white" type="reset">取消</button>
                                    <button class="btn btn-danger" type="button" onclick="resetPwd()">重置密码</button>
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
	
	var userId = "${user.id}";
	function resetPwd() {
		swal({
			title : "请输入旧密码",
			text : "",
			type : "input",
			showCancelButton : true,
			closeOnConfirm : false,
			closeOnCancel : true,
			animation : "slide-from-top",
			inputPlaceholder : "原密码",
			confirmButtonText : "确定",
			cancelButtonText : "取消",
		}, function(inputValue) {
			$.ajax({
				url : "/aihudongUser-web/user/testOldPwd",
				data : "password=" + inputValue,
				type : "post",
				//与原密码进行比对
				success : function(data) {
					//成功匹配，准备输入新密码
					if (data == 'success') {
						inputNewPwdFirst();
					} else {
						//未成功匹配
						swal("与原密码不匹配!", "请重试", "error");
					}
				}
			})
		})
	}

	function inputNewPwdFirst() {
		swal({
			title : "请输入新密码",
			text : "",
			type : "input",
			showCancelButton : true,
			closeOnConfirm : false,
			closeOnCancel : true,
			animation : "slide-from-top",
			inputPlaceholder : "密码",
			confirmButtonText : "确定",
			cancelButtonText : "取消",
		}, function(inputValue) {
			inputNewPwdSecond(inputValue);
		})
	}

	function inputNewPwdSecond(pwd) {
		swal({
			title : "请再次输入新密码",
			text : "",
			type : "input",
			showCancelButton : true,
			closeOnConfirm : false,
			closeOnCancel : true,
			animation : "slide-from-top",
			inputPlaceholder : "密码",
			confirmButtonText : "确定",
			cancelButtonText : "取消",
		}, function(inputValue) {
			if (pwd != inputValue) {
				swal("两次输入密码不一致!", "操作失败", "error");
			} else {
				$.ajax({
					url : "/aihudongUser-web/user/updateInfo",
					data : "id=" + userId + "&password=" + inputValue,
					type : "post",
					//与原密码进行比对
					success : function(data) {
						//成功匹配，准备输入新密码
						if (data == 'success') {
							swal("修改成功!", "", "success");
						} else {
							//未成功匹配
							swal("修改失败!", "请重试", "error");
						}
					}
				})
			}
		})
	}
	
	function updateInfo(){
		$.ajax({
			url:"/aihudongUser-web/user/updateInfo",
			data:$("#editForm").serialize(),
			type:"post",
			success:function(data){
				if(data=='success'){
					alert("操作成功！");
					window.location="/aihudongUser-web/user/toUserInfo";
				}else{
					alert("操作失败");
				}
			}
		})
	}
</script>
</html>
