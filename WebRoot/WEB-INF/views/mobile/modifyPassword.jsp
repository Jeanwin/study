<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head lang="en">
<%@ include file="/WEB-INF/views/include/mobileHead.jsp"%>
<title>修改密码</title>
</head>
<body class="bg-gray">
	<header id="header">
		<div class="back">
			<a href="./personal"><i class="icon-chevron-left"></i></a>
		</div>
		修改密码
	</header>
	<div class="login">
		<form id="form_modify_password">
			<input type="password" name="oldPassword" placeholder="原密码" /> <input type="password" name="newPassword" placeholder="输入密码" /> <input type="password" name="repPassword" placeholder="再次输入" />
			<div class="loginBtn">
				<button id="btn_modify_password">保存</button>
			</div>
		</form>
		<script type="text/javascript">
			var user;
			function userInfo() {
				$.sendPost({
					url : $.urls.getUser,
					type : 'GET',
					successCallback : function(d) {
						user = d;
					},
					errorCallback : function(xhr) {
						console.log("当前没有用户登录");
					},
					operation : "查询当前登录用户"
				})
			}
			function modifyPassword() {
				var loginname = user.loginname;
				if (loginname === "" || loginname === null || loginname === undefined) {
					$.message("当前没有用户登录", "error");
				} else {
					var data = {
						loginname : loginname,
						oldPassword : $("input[name='oldPassword']").val(),
						newPassword : $("input[name='newPassword']").val(),
						repPassword : $("input[name='repPassword']").val()
					}
					$.sendPost({
						url : $.urls.modifyPassword,
						data : data,
						operation : "修改个人密码",
						successCallback : function(d) {
							if (d.id == 6) {
								$.message(d.content, "success");
								setTimeout(function(){
									window.location.href = "./personal";
								},2000)
							} else {
								$.message(d.content, "error");
							}
						}
					})
				}
			}
			$(function() {
				userInfo();
				$("#btn_modify_password").on("click", function() {
					modifyPassword();
					return false;
				})
				$("#form_modify_password").on("submit", function() {
					modifyPassword();
					return false;
				})
			})
		</script>
	</div>

</body>
</html>