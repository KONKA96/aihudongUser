<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="common/include_css.jsp" />
<jsp:include page="common/include_js.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公网后台管理系统</title>
</head>
<body>
	
	<script type="text/javascript">
		if(window.top.location.href!=location.href)      
		{         
		    window.top.location.href=location.href;      
		}  
	</script>
    <div class="container">
        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4">
                 <a class="btn big-login" data-toggle="modal" href="javascript:void(0)" onclick="openLoginModal();">Log in</a>
                 <a class="btn big-register" data-toggle="modal" href="javascript:void(0)" onclick="openRegisterModal();">Register</a></div>
            <div class="col-sm-4"></div>
        </div>
       
         
		 <div class="modal fade login" id="loginModal">
		      <div class="modal-dialog login animated">
    		      <div class="modal-content">
    		         <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Login with</h4>
                    </div>
                    <div class="modal-body">  
                        <div class="box">
                             <div class="content">
                               <!--  <div class="social">
                                    <a class="circle github" href="/auth/github">
                                        <i class="fa fa-github fa-fw"></i>
                                    </a>
                                    <a id="google_login" class="circle google" href="/auth/google_oauth2">
                                        <i class="fa fa-google-plus fa-fw"></i>
                                    </a>
                                    <a id="facebook_login" class="circle facebook" href="/auth/facebook">
                                        <i class="fa fa-facebook fa-fw"></i>
                                    </a>
                                </div> -->
                                <!-- <div class="division">
                                    <div class="line l"></div>
                                      <span>or</span>
                                    <div class="line r"></div>
                                </div> -->
                                <div class="error"></div>
                                <div class="form loginBox">
                                    <form  accept-charset="UTF-8" id="editForm">
                                    <input id="logUsn" class="form-control" type="text" placeholder="用户名/联系方式" name="username">
                                    <input id="logPwd" class="form-control" type="password" placeholder="密码" name="password">
                                    <input class="btn btn-default btn-login" type="button" value="Login" onclick="login()">
                                    </form>
                                </div>
                             </div>
                        </div>
                        <div class="box">
                            <div class="content registerBox" style="display:none;">
                             <div class="form">
                                <form data-remote="true"  accept-charset="UTF-8" id="registerForm">
                                <input id="regUsn" class="form-control" type="text" placeholder="用户名" name="username">
                                <input id="regTel" class="form-control" type="text" placeholder="联系方式" name="telephone">
                                <input id="regPassword" class="form-control" type="password" placeholder="Password" name="password">
                                <input id="regPassword_confirmation" class="form-control" type="password" placeholder="Repeat Password" name="password_confirmation">
                                <input id="company" class="form-control" type="text" placeholder="公司名称" name="job">
                                <select class="form-control" name="jobType">
								  <option>金融</option>
								  <option>互联网</option>
								  <option>教育</option>
								  <option>医疗</option>
								  <option>娱乐</option>
								  <option>其他</option>
								</select>
                                <input class="btn btn-default btn-register" type="button" value="Create account" name="commit" onclick="register()">
                                </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="forgot login-footer">
                            <span>Looking to 
                                 <a href="javascript: showRegisterForm();">create an account</a>
                            ?</span>
                        </div>
                        <div class="forgot register-footer" style="display:none">
                             <span>Already have an account?</span>
                             <a href="javascript: showLoginForm();">Login</a>
                        </div>
                    </div>        
    		      </div>
		      </div>
		  </div>
    </div>
	<script type="text/javascript">
		function login(){
			if($("#logUsn").val()==""){
				alert("用户名不能为空！");
				return false;
			}else if($("#logPwd").val()==""){
				alert("密码不能为空！");
				return false;
			}
			$.ajax({
				url:"/aihudongUser-web/index/login",
				data:$("#editForm").serialize(),
				type:"post",
				success:function(data){
					if(data=='success'){
						alert("登录成功！");
						window.location.href="/aihudongUser-web/index/toIndex";
					}else if(data=='none'){
						alert("用户名不存在！");
					}else if(data=='pwdError'){
						alert("用户名密码不匹配！");
					}
				}
			})
		}
		
		function register(){
			var result=$("#regPassword").val().match(/[0-9]{1,6}/);
			if($("#regUsn").val()==""){
				alert("用户名不能为空！");
				return false;
			}else if($("#regTel").val()==""){
				alert("联系方式不能为空！");
				return false;
			}else if($("#regPassword").val()==""){
				alert("密码不能为空！");
				return false;
			}else if($("#regPassword_confirmation").val()==""){
				alert("重复密码不能为空！");
				return false;
			}else if($("#regPassword_confirmation").val()!=$("#regPassword").val()){
				alert("两次输入的密码不一致！");
				return false;
			}else if(result==$("#regPassword").val()){
				alert("密码不得是6位以下的纯数字！");
				return false;
			}
				$.ajax({
					url : "/aihudongUser-web/screen/insertScreen",
					data:$("#registerForm").serialize(),
					type : "post",
					success : function(data) {
						if(data=='success'){
							alert("注册成功！");
							openLoginModal();
							$("#logUsn").val()=$("#regUsn").val();
							$("#logPwd").val()=$("#regPassword").val();
						}else if(data=='exist'){
							alert("用户名已存在！");
						}else if(data=='error'){
							alert("注册失败！");
						}else if(data=='phoneexist'){
							alert("该电话已存在！");
						}
					}
				})
		    	/* $.ajax({
					url : "/aihudongUser-web/screen/insertScreen",
					data : $("#editFormScreen").serialize(),
					type : "post",
					success : function(data) {
						if (data == 'success') {
							alert("操作成功！");
							window.location = "/aihudongUser-web/screen/selectAllScreen";
						} else if (data == "error") {
							alert("用户名重复!");
						} else {
							alert("操作失败");
						}
					}
				}) */
		    }
	</script>
</body>
</html>