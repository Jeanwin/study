<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head lang="en">
<%@ include file="/WEB-INF/views/include/mobileHead.jsp"%>
<title>个人中心</title>
</head>
<body>
	<header id="header">
		<div class="back">
			<a href="index.html"><i class="icon-chevron-left"></i></a>
		</div>
		<div class="iconBar">
			<i class="icon-format-list-bulleted" id="list" onclick="aaa()"></i><i class="icon-close" id="close" onclick="bbb()" style="display: none;"></i>
		</div>
		<nav id="nav" style="display: none;">
			<a href="liveCourses.html">直播</a> <a href="./unicast">点播</a> <a href="collection.html">收藏</a> <a href="./personal">个人中心</a> <a href="information.html">公告</a>
		</nav>
		<script>
			function aaa() {
				var nav = document.getElementById("nav");
				var list = document.getElementById("list");
				var close = document.getElementById("close");
				nav.style.display = "block";
				list.style.display = "none";
				close.style.display = "block";
			}
			function bbb() {
				var nav = document.getElementById("nav");
				var list = document.getElementById("list");
				var close = document.getElementById("close");
				nav.style.display = "none";
				list.style.display = "block";
				close.style.display = "none";
			}
		</script>
		个人中心
	</header>
	<div class="personalInfo">
		<div class="personal">
			<img id="user_picture" src="${ctx}/static/mobile/images/lemonade-elements-27.png" />
			<div class="personalNameCorp">
				<div class="personalName" id="user_name"></div>
				<div id="user_dept"></div>
			</div>
			<div class="modPass">
				<a href="./modify"><i class="icon-password"></i><span>修改密码</span></a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function renderUserInfo(d, isClear) {
			$("#user_picture").attr("src", d.pictureURL);
			$("#user_name").text(d.name);
			$("#user_dept").text(d.deptName);
		}
		function userInfo() {
			$.sendPost({
				url : $.urls.getUser,
				render : renderUserInfo,
				type : 'GET',
				errorCallback : function(xhr) {
					console.log("当前没有用户登录");
				},
				operation : "查询当前登录用户"
			})
		}
		$(function() {
			userInfo();
		})
	</script>
	<div class="mainContent">
		<div class="tabTitle">
			<span class="current">最近点播</span> <span><a href="./toCollection">我的收藏</a></span>
		</div>
		<!--最近点播开始-->
		<div class="contentLive" id="play_record_list">
			<!-- 最近点播 list-->
		</div>
		<div id="noplayrecord" class="noResentLive" style="display:none">
			<i class="icon-stay_current_portrait"></i>
			<div class="noResentLiveTip">
				<div>您最近没有观看的视频</div>
				<div>
					请进入<a href="./unicast">点播栏目</a>
				</div>
			</div>
		</div>
		<!--最近点播结束-->
		<script type="text/javascript">
			var pageData = [];
			var isLoaded = false;
			function playList(data) {
				var renderDiv = "";
				$.each(data, function(i, d) {
					renderDiv += '<div class="liveInfo">';
					renderDiv += '<a href="liveDetail.html" class="liveTitle"><h3>' + d.courseName + '</h3></a>';
					renderDiv += '<div class="liveInfoCont">';
					renderDiv += '<div class="liveInfoAuthor">';
					renderDiv += '<i class="icon-person"></i><span>刘梅</span><i class="icon-stay_current_portrait mlIcon"></i><span>剩余49:27</span>';
					renderDiv += '</div>';
					renderDiv += '<div class="liveInfoOpt">一周内</div>';
					renderDiv += '</div>';
					renderDiv += '</div>';
				})
				return renderDiv;
			}
			function renderPlayList(data, isClear) {
				pageData.push(data);
				$("#noplayrecord").hide();
				$.renderView({
					data : data.data,
					containerId : "play_record_list",
					callback : playList,
					noMessage : "无最近观看记录",
					noCallback : function() {
						$("#noplayrecord").show();
					},
					isClear : isClear
				});
				isLoaded = true;
			}
			function playRecords(pageIndex, isClear, pageSize) {
				pageData = [];
				isLoaded = false;
				if (pageIndex == null || pageIndex == undefined || pageIndex == '') {
					pageIndex = 1;
				}
				var limits = pageSize ? pageSize : $.config.pageSize;
				var page = {
					pageIndex : pageIndex,
					offset : (pageIndex - 1) * limits,
					limit : limits
				}
				var data = {
					keywords : null,
					page : page
				}
				pageData.push(data);
				$.sendPost({
					url : $.urls.playRecordList,
					data : data,
					operation : "查询个人点播记录",
					render : renderPlayList,
					isClear : isClear
				})
			}
			$(function() {
				playRecords(1, true);
				$(window).on("scroll", function() {
					var top = $("#header").height() + 10;
					var originHeight = $(document).height();
					var trigerHeight = originHeight - $(window).height();
					if (trigerHeight <= ($(window).scrollTop() + top)) {
						if (isLoaded) {
							var prevPage = pageData[0].page.pageIndex;
							var totalPage = Math.ceil(pageData[1].total / pageData[0].page.limit);
							if (prevPage < totalPage) {
								playRecords(prevPage + 1, false, pageData[0].page.limit);
							}
						}
					}
				})
			})
		</script>
	</div>
</body>
</html>