<!DOCTYPE html>
<html class="no-js" id="ng-app" ng-controller="MainCtrl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible" />
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="pragma" content="no-cache" />
<title>来自zonekey个人空间的消息</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	font-family: 微软雅黑;
	font-size: 14px
}

body {
	color: #000;
	background: #fff;
	margin: 0;
	padding: 0;
}

.outerframe {
	width: auto;
	height: auto;
	padding: 30px 50px;
	background: rgb(161, 101, 101);
}

.whiteframe {
	padding: auto;
	width: 100%;
	margin: auto;
	height: auto;
	border-radius: 5px;
	background: #fff;
	margin: auto;
}

.message_box {
	padding: 30px 30px;
	min-height: 600px;
}

.message_brand {
	min-height: 46px;
	margin-bottom: 20px;
	padding-right: 20px;
	background: rgb(255, 240, 223);
}

.message_brand img {
	padding-left: 20px;
}

.zonekey {
	height: 46px;
	color: rgb(168, 44, 44);
	line-height: 46px;
	font-size: 28px;
	font-weight: 400;
	display: block;
	float: right;
}

.space {
	height: 46px;
	line-height: 46px;
	font-size: 22px;
	font-weight: 200;
	display: block;
	float: right;
}

.message_title {
	text-align: center;
}

.title {
	margin-bottom: 10px;
}

.title strong {
	font-size: 18px;
}

.sendinfo {
	background: rgb(253, 246, 246);
	min-height: 30px;
	line-height: 30px;
}

.sender {
	margin-right: 30px;
}

.message_content {
	background: rgb(195, 250, 250);
	min-height: 300px;
	text-align: left;
	padding: 30px;
}

.message_content .content pre {
	word-break: break-word;
}
.message_all{
    min-height:50px;
    padding: 30px 0px 5px 0px;
}
.message_tip {
	color: red;
	position: relative;
	text-align: right;
}

.message_info {
	color: green;
	position: relative;
	padding: 0px 0px;
	text-align:right;
}

.message_sender {
	background: rgb(225, 255, 225);
	min-height: 100px;
	padding: 15px 30px;
}

.sender_header {
	display: block;
	min-width: 80px;
	min-height: 80px;
	float: left;
}

.sender_header img {
	margin: auto;
	padding: auto;
	line-height: 80px;
	width: 80px;
	height: 80px;
	border-radius: 50%;
	float: left;
}

.sender_detail {
	margin-left: 100px;
}

.sender_emial {
	padding: 5px 0px;
}

.btn_link {
	display: block;
	background: rgb(34, 145, 69);
	width: 100px;
	height: 30px;
	color: #06264A;
	border-radius: 30px;
	margin-right: 20px;
	float: left;
	line-height: 30px;
	text-decoration: none;
	text-align: center;
	border-radius: 30px;
}

a:hover {
	background: rgb(193, 226, 204);
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="outerframe">
		<div class="whiteframe">
			<div class="message_box">
			    <#assign appName="/study/"> 
			    <#assign ip="http://192.168.12.220/"> 
				<div class="message_brand">
					<span class="space">|个人空间</span><span class="zonekey">zonekey</span><img src="${appBase}/portal/assets/img/logo.png" />
				</div>
				<div class="message_title">
					<div class="title">
						<strong>主题：${message.title}</strong>
					</div>
					<div class="sendinfo">
						<span class="sender">这条消息来自:${sender.name}</span> <span class="senddate">发送时间:${senddate}</span>
					</div>
				</div>
				<div class="message_content">
					<div class="content">
						<pre>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${message.content}</pre>
					</div>
					<div class="message_all">
						<p><div class="message_tip">此邮件来自个人空间系统邮箱账号，请勿直接回复!</div></p>
						<div class="message_info">您可以点击下方的回复按钮，或者登录空间回复消息</div>
					</div>
				</div>
				<div class="message_sender">
					<div class="sender_header">
						<#if sender.pictureURL?? && sender.pictureURL != "">
						   <img src="${sender.pictureURL}"> 
						<#else><img src="${appBase}/portal/assets/img/icon/headPicture.jpg">
						</#if>
					</div>
					<div class="sender_detail">
						<div class="sender_name">
							<strong>${sender.name}</strong>
						</div>
						<div class="sender_emial">
							<a href="mailto:${sender.email}">${sender.email}</a>
						</div>
						<a class="btn_link" href="mailto:${sender.email}">回复</a><a class="btn_link" href="${appBase}">登录查看</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>