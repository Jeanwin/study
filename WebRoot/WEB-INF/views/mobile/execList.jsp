<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head lang="en">
<%@ include file="/WEB-INF/views/include/mobileHead.jsp"%>
<title>点播页面</title>
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
			<a href="liveCourses.html">直播</a> <a href="./unicast">点播</a> <a href="./collection">收藏</a> <a href="./personal">个人中心</a> <a href="information.html">公告</a>
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
		点播
		<div class="search">
			<input type="text" name="name" placeholder="请输入搜索内容" />
			<button onclick="ccc()">
				筛选<i class="icon-arrow-drop-down"></i>
			</button>
		</div>
	</header>
	<div class="selectedList" id="selectedList" style="display: none;">
		<ul id="dept_list">
			<!-- 部门 -->
		</ul>
		<ul class="childList" id="teacher_list">
			<!-- <li>诸葛亮<span><i class="icon-check"></i></span></li> -->
		</ul>
		<div class="listOption">
			<button>重置</button>
			<button onclick="ddd()">
				确定
				<!-- (349节) -->
			</button>
		</div>
	</div>
	<script>
		function ccc() {
			var selectedList = document.getElementById("selectedList");
			selectedList.style.display = "block";
		}
		function ddd() {
			var selectedList = document.getElementById("selectedList");
			selectedList.style.display = "none";
		}
		function demo() {
			var listFrom = document.getElementById("listFrom");
			var header = document.getElementById("header");
			var resultPage = document.getElementById("resultPage");
			listFrom.style.display = "none";
			header.style.display = "none";
			resultPage.style.display = "block";
		}
	</script>
	<div class="playingState paddingTop90" id="listFrom">
		<!--最近观看-->
		<div class="recentWatch">
			<div class="title color-green">
				<i class="icon-event-note"></i>最近观看
			</div>
			<div class="playList" id="latest_play_view">
				<!-- 最近观看 -->
			</div>
		</div>
		<script type="text/javascript">
			function renderLatestView(data) {
				var renderDiv = "";
				$.each(data, function(i, d) {
					renderDiv += '<a href="demandDetail.html">';
					renderDiv += '<h3>' + d.courseName + '</h3>';
					renderDiv += '<div class="resentLive loft">';
					renderDiv += '<div class="liveAuthor">';
					renderDiv += '<i class="icon-person"></i>' + d.username;
					renderDiv += '</div>';
					renderDiv += '<div class="liveCurrentStatic color-green">';
					if (0 < d.playtime < d.duration && d.playtime !== 0) {
						if (d.playtime < 60) {
							renderDiv += '<i class="icon-living"></i>不足一分钟';
						} else {
							renderDiv += '<i class="icon-living"></i>剩余' + $.formatSeconds(d.duration - d.playtime, ":");
						}
					} else if (d.playtime === d.duration && d.playtime !== 0) {
						renderDiv += '<i class="icon-living"></i>已看完';
					} else {
						renderDiv += '<i class="icon-living"></i>未观看';
					}
					renderDiv += '</div>';
					renderDiv += '</div>';
					renderDiv += '</a>';
				})
				return renderDiv;
			}
			function latestView(data, isClear) {
				//data, containerId, callback, noMessage, isClear
				$.renderView({
					data : data,
					containerId : "latest_play_view",
					callback : renderLatestView,
					noMessage : "无最近观看记录",
					isClear : isClear
				});
			}
			function latestPlay() {
				$.sendPost({
					url : $.urls.latestPlay,
					render : latestView,
					operation : "查询最近观看记录",
					isClear : true
				});
			}
			$(function() {
				latestPlay();
			})
		</script>
		<!--更多点播-->
		<div class="recentWatch">
			<div class="title">
				<i class="icon-event-note"></i>更多点播
			</div>
			<div class="playList" id="unicast_list">
				<!-- 更多点播 -->
			</div>
		</div>
	</div>
	<div class="noContent" style="text-align: center;padding-top: 6px;display:none;">
		<span><i class="icon-phone_search_no" style="font-size: 20px;color: #666666;"></i> </span>
		<p>暂无内容</p>
	</div>
	<script type="text/javascript">
		function renderUnicastView(data) {
			var renderDiv = "";
			$.each(data, function(i, d) {
				renderDiv += '<a href="demandDetail.html">';
				renderDiv += '<h3>' + d.courseName + '</h3>';
				renderDiv += '<div class="resentLive">';
				renderDiv += '<div class="liveAuthor">';
				renderDiv += '<i class="icon-person"></i>' + d.username;
				renderDiv += '</div>';
				renderDiv += '<div class="liveTime">' + $.formatSeconds(d.duration, ":") + '</div>';
				renderDiv += '<div class="liveCurrentStatic color-gray">';
				renderDiv += '<span><i class="icon-chat-3"></i>' + d.commentNum + '</span><span><i class="icon-visibility"></i>' + d.viewNum + '</span>';
				renderDiv += '</div>';
				renderDiv += '</div>';
				renderDiv += '</a>';
			})
			return renderDiv;
		}
		function renderDept(data) {
			var render = '<li class="cur"><span style="display:none;"></span>所有院系</li>';
			$.each(data, function(i, d) {
				render += '<li><span style="display:none">' + d.value + '</span>' + d.name + '</li>';
			})
			return render;
		}
		function renderTeacher(data) {
			var render = '';
			$.each(data, function(i, d) {
				render += '<li><span style="display:none">' + d.loginname + '</span>' + d.name + '<span><i class="icon-check"></i></span></li>';
			})
			return render;
		}
		function renderMoreUnicast(data, isClear) {
			$.renderView({
				data : data.data,
				containerId : "unicast_list",
				callback : renderUnicastView,
				noMessage : "暂无点播资源",
				isClear : isClear
			});
			$("#dept_list").html('<span>共' + data.count + '节课</span>');
			$.renderView({
				data : data.depts,
				containerId : "dept_list",
				callback : renderDept,
				noMessage : "没有二级部门"
			});
			$.renderView({
				data : data.teachers,
				containerId : "teacher_list",
				callback : renderTeacher,
				noMessage : "未找到教师",
				isClear : true
			});
		}
		function moreUnicast(pageIndex, name, deptid, loginname, isClear, pageSize) {
			if (pageIndex == null || pageIndex == undefined || pageIndex == '') {
				pageIndex = 1;
			}
			var limits = pageSize ? pageSize : $.config.pageSize;
			var page = {
				offset : (pageIndex - 1) * limits,
				limit : limits
			}
			var keywords = {
				name : name ? name : null,
				deptid : deptid ? deptid : null,
				loginname : loginname ? loginname : null,
			}
			var data = {
				keywords : keywords,
				page : page
			}
			$.sendPost({
				url : $.urls.unicastList,
				data : data,
				render : renderMoreUnicast,
				operation : "查询更多直播",
				isClear : isClear
			})
		}
		$(function() {
			moreUnicast();
			$("input[name='name']").on("input", function() {
				moreUnicast(1, $(this).val(), null, null, true);
			})
		})
	</script>
	<script>
		function eee() {
			var listFrom = document.getElementById("listFrom");
			var header = document.getElementById("header");
			var resultPage = document.getElementById("resultPage");
			listFrom.style.display = "block";
			header.style.display = "block";
			resultPage.style.display = "none"
		}
	</script>
</body>
</html>