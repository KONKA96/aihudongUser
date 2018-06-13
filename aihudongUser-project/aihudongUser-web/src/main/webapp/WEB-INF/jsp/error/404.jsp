<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 页面</title>
    <jsp:include page="../common/include_css.jsp" />
</head>

<body class="gray-bg">


    <div class="middle-box text-center animated fadeInDown">
        <h1>404</h1>
        <h3 class="font-bold">您的操作好像不对吧</h3>

        <!-- <div class="error-desc">
           <span id="count">5</span>秒之后返回首页
        </div> -->
    </div>

   <jsp:include page="../common/include_js.jsp" />

</body>
<!-- <script language="javascript" type="text/javascript">
var i = 5;
var intervalid;
intervalid = setInterval("fun()", 1000);
function fun() {
if (i == 0) {
	window.location.reload();
clearInterval(intervalid);
}
document.getElementById("count").innerHTML = i;
i--;
}
</script> -->
</html>
    