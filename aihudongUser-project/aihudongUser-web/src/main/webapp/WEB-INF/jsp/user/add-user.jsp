<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户信息</title>
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
                        	<button class="btn btn-primary" onclick="goback()">返回</button>
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal" id="editForm">
                        	<input type="hidden" name="id" value="${requestScope.user.id }">
                        	<div class="form-group">
                                <label class="col-sm-2 control-label">请输入要生成的人数：</label>

                                <div class="col-sm-10">
                                    <input id="number" name="number" type="text" class="form-control" placeholder="请输入1-50之内的数字" onkeyup="changeNumber()">
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
                    
                    <div id="diaLogDivT" style="display: none;" class="col-sm-12">
						<div class="ibox-content">
							<div class="dataTables_wrapper form-inline" role="grid">
								<form id="editUsers">
									<table
										class="table table-striped table-bordered table-hover dataTables-example">
										<thead>
											<tr>
												<th>用户ID</th>
												<th>用户名</th>
												<th>密码</th>
												<!-- <th>类型</th> -->
											</tr>
										</thead>
										<tbody id="tbodyT">
				
										</tbody>
									</table>
								</form>
							</div>
						</div>
					</div>
                    
                </div>
            </div>
        </div>
    </div>
	
	<jsp:include page="../common/include_js.jsp" />

</body>
<script type="text/javascript">
	
	$(document).ready(function () {
	    $('.i-checks').iCheck({
	        checkboxClass: 'icheckbox_square-green',
	        radioClass: 'iradio_square-green',
	    });
	});
	
	function changeNumber(){
		/* var result=$("#regPassword").val().match(/[0-9]{1,}/);
		if(result!=$("#regPassword").val()){
			$("#diaLogDivT").css("display", "none");
			alert("请输入数字！");
			return false;
		} */
		$("#tbodyT").empty();
		if($("#number").val()>=1 && $("#number").val()<=50){
			$("#diaLogDivT").css("display", "inline-block");
			$.ajax({
				url:"/aihudongUser-web/user/produceUsers",
				data:$("#editForm").serialize(),
				type:"post",
				success:function(data){
					console.log(data.userList);
					for (var i = 0; i < data.length; i++) {
						$("#tbodyT").append("<tr><td>"+data[i].id+"</td><td>"+data[i].username+"</td><td>"+data[i].password+"</td></tr>");
					}
				}
			})
		}else if($("#number").val()==null||$("#number").val()==""){
			
			return true;
		}else{
			$("#diaLogDivT").css("display", "none");
			alert("您输入的数字不符合，请重新输入！");
			return false;
		}
		
	}
	
	function updateInfo(){
		$.ajax({
			url:"/aihudongUser-web/user/insertUsers",
			type:"post",
			success:function(data){
				if(data=='success'){
					alert("操作成功！");
					window.location="/aihudongUser-web/user/selectAllUser";
				}else if(data=='max'){
					alert("您创建的用户数量不能超过50人！");
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
