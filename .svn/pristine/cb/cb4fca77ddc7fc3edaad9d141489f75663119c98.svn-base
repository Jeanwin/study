<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head lang="en">
<%@ include file="/WEB-INF/views/include/mobileHead.jsp"%>
<title>用户登录</title>
</head>
<body class="bg-gray">
	<header id="header">
		<div class="back">
			<a href="index.html"><i class="icon-chevron-left"></i></a>
		</div>
		用户登录
	</header>
	<div id="login_message" style="display:none">${error}</div>
	<div class="login">
		<form id="form_login" action="${ctx}/mobile/gateway/login" method="post">
			<input type="text" name="domain" placeholder="单位域名" /> <input type="text" name="username" placeholder="用户名" /> <input type="password" name="password" placeholder="密码" />
			<div class="loginOpt">
				<input type="checkbox" id="autoLogin" /><label for="autoLogin">自动登录</label><span>忘记密码？</span>
			</div>
			<div class="loginBtn">
				<button type="submit">登录</button>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			var error = $("#login_message").text();
			console.log(error);
			if (error != '' && error != undefined && error != null) {
				$.message(error, "error");
			}
		})
	</script>
</body>
</html>