<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
						<h5>
							<small>屏幕</small>
						</h5>
						<div class="ibox-tools">
							<button class="btn btn-primary" onclick="goback()">返回</button>
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal" id="editForm">

							<div class="form-group">
								<label class="col-sm-2 control-label">房间</label>
								<div class="col-sm-10">
									<div class="input-group">
										<input id="roomInput" type="text" onkeyup="emptyRoom()"
											name="num" class="form-control" aria-label="...">
										<div class="input-group-btn">
											<button type="button" class="btn btn-default dropdown-toggle"
												data-toggle="dropdown" aria-haspopup="true"
												aria-expanded="false">
												<span class="caret"></span>
											</button>
											<ul id="roomUl" class="dropdown-menu dropdown-menu-right">

											</ul>
										</div>
										<!-- /btn-group -->
									</div>
									<!-- /input-group -->
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">屏幕数</label>
								<div class="col-sm-10">
									<select id="screenNumber" class="form-control m-b"
										name="number" onchange="produceUsnPwd(this)">
										<option value="">--请选择--</option>
										<option value="1">--1--</option>
										<option value="2">--2--</option>
										<option value="3">--3--</option>
										<option value="4">--4--</option>
										<option value="5">--5--</option>
										<option value="6">--6--</option>
										<option value="7">--7--</option>
										<option value="8">--8--</option>
										<option value="9">--9--</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<div class="col-sm-10">您的总屏幕数为：${user.screenNum }
									您的剩余屏幕数为：${user.screenRemain }</div>
							</div>

							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="button"
										onclick="updateInfo()">保存</button>
									<button class="btn btn-white" type="reset">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="diaLogDivT" style="display: none;">
		<div class="ibox-content">
			<div class="dataTables_wrapper form-inline" role="grid">
				<form id="editFormScreen">
					<table
						class="table table-striped table-bordered table-hover dataTables-example">
						<thead>
							<tr>
								<th>序号</th>
								<th>屏幕用户名</th>
								<th>屏幕密码</th>
								<th>屏幕类型</th>
								<th>类型</th>
							</tr>
						</thead>
						<tbody id="tbodyT">

						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="../common/include_js.jsp" />

</body>
<script type="text/javascript">
	function emptyRoom(obj) {
		$("#screenNumber").empty();
		$("#screenNumber").append("<option value=''>--请选择--</option><option value='1'>--1--</option><option value='2'>--2--</option><option value='3'>--3--</option><option value='4'>--4--</option><option value='5'>--5--</option><option value='6'>--6--</option><option value='7'>--7--</option><option value='8'>--8--</option><option value='9'>--9--</option>");
		$("#tbodyT").empty();
	} 
	var screenListNew;
	var screenListOld;
	var screenOldLength;
	function produceUsnPwd(obj) {
		$("#tbodyT").empty();
		var roomId = $("#roomSelected").val();
		var num = obj.value;
		$
				.ajax({
					url : "/aihudongUser-web/screen/selectScreenByRoom",
					data : $("#editForm").serialize(),
					type : "post",
					success : function(data) {
						if(data.min!=null || data=="min"){
							alert("您剩余的屏幕数量不足，请先购买！");
							return false;
						}else{
							
						
						screenListNew = data.screenListNew;
						screenListOld = data.screenListOld;
						if (screenListOld != null) {
							for (var i = 0; i < screenListOld.length; i++) {
								$("#tbodyT")
										.append(
												"<tr><input type='hidden' name='screenList["+i+"].id' value='"+screenListOld[i].id+"'><td>"
														+ (i+1)
														+ "</td><td><input type='text' disabled='disabled' name='screenList["+i+"].username' value='" + screenListOld[i].username
										+ "'/></td><td><input type='text' disabled='disabled' name='screenList["+i+"].password' value='" + screenListOld[i].password
										+ "'/></td><td><select name='screenList["+i+"].type'><option value='1'>触摸屏</option><option value='2'>文档屏</option><option value='3'>投影</option><option value='4'>电视</option><option value='5'>临时屏幕</option></select></td><td>原有</td></tr>");
							}
							screenOldLength=screenListOld.length;
							
						}else{
							screenOldLength=0;
						}
						$("#tbodyT")
						.append(
								"<input id='roomId' type='hidden' name='id' value='"+data.room.id+"'>");
						for (var j = screenOldLength; j < screenListNew.length
								+ screenOldLength; j++) {
							$("#tbodyT")
									.append(
											"<tr><input type='hidden' name='screenList["+j+"].id' value='"+screenListNew[j-screenOldLength].id+"'><td>"
													+ (j+1)
													+ "</td><td><input type='text' name='screenList["+j+"].username'  value='" + screenListNew[j-screenOldLength].username
										+ "' readonly='true'/></td><td><input type='text'  name='screenList["+j+"].password' value='" + screenListNew[j-screenOldLength].password
										+ "' readonly='true'/></td><td><select name='screenList["+j+"].type'><option value='1'>触摸屏</option><option value='2'>文档屏</option><option value='3'>投影</option><option value='4'>电视</option><option value='5'>临时屏幕</option></select></td><td>新增</td></tr>");
						}

						
						}
					}
				})
		$("#diaLogDivT").css("display", "inline-block");
	}


	function changeRoom(obj) {
		$("#screenNumber").empty();
		$("#screenNumber").append("<option value=''>--请选择--</option><option value='1'>--1--</option><option value='2'>--2--</option><option value='3'>--3--</option><option value='4'>--4--</option><option value='5'>--5--</option><option value='6'>--6--</option><option value='7'>--7--</option><option value='8'>--8--</option><option value='9'>--9--</option>");
		$("#tbodyT").empty();
		$("#roomInput").val(obj.innerText);
		$("#diaLogDivT").css("display", "none");
	}

	$(document).ready(function() {
		$('.i-checks').iCheck({
			checkboxClass : 'icheckbox_square-green',
			radioClass : 'iradio_square-green',
		});
	});

	
	function updateInfo() {
		$.ajax({
			url : "/aihudongUser-web/screen/insertScreen",
			data : $("#editFormScreen").serialize(),
			type : "post",
			success : function(data) {
				if (data == 'success') {
					alert("操作成功！");
					window.location = "/aihudongUser-web/screen/selectAllScreen";
				} else if (data == "error") {
					alert("用户名存在重复，前查找后再添加");
				} else if (data=="min"){
					alert("您剩余的屏幕数量不足，请先购买！");
				}else{
					alert("操作失败");
				}
			}
		})
	}

	function goback() {
		window.history.back();
	}
</script>
</html>
