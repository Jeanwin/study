<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head lang="en">
<%@ include file="/WEB-INF/views/include/mobileHead.jsp"%>
<title>学习平台首页</title>
</head>
<body>
	<header>
		<div class="logo">
			<i class="icon-zklogo"></i>
		</div>
		<div class="iconBar">
			<i class="icon-format-list-bulleted" id="list" onclick="aaa()"></i><i class="icon-close" id="close" onclick="bbb()" style="display: none;"></i>
		</div>
		<nav id="nav" style="display: none;">
			<a href="liveCourses.html">直播</a> <a href="videoDemand.html">点播</a> <a href="collection.html">收藏</a> <a href="personalCenter.html">个人中心</a> <a href="information.html">公告</a>
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
	</header>
	<div class="container">
		<div class="focusImage">
			<img src="${ctx}/static/mobile/images/banner1.png" />
		</div>
		<div class="infoTip">
			<h3>
				<a href="information.html">更多</a>北京等七省市将设立独立外资独资学校
			</h3>
		</div>
		<script>
			function zb() {
				var db = document.getElementById("db");
				var zb = document.getElementById("zb");
				var dbContent = document.getElementById("dbcontent");
				var zbContent = document.getElementById("zbContent");
				zb.className = "current";
				db.className = "";
				zbContent.style.display = "block";
				dbContent.style.display = "none";
			}
			function db() {
				var db = document.getElementById("db");
				var zb = document.getElementById("zb");
				var dbContent = document.getElementById("dbcontent");
				var zbContent = document.getElementById("zbContent");
				zb.className = "";
				db.className = "current";
				zbContent.style.display = "none";
				dbContent.style.display = "block";
			}
		</script>
		<div class="mainContent bg-gray">
			<div class="tabTitle">
				<span class="current" onclick="zb()" id="zb">直播</span> <span onclick="db()" id="db">点播</span>
			</div>
			<!--直播开始-->
			<div class="contentLive" id="zbContent">
				<div class="liveInfo">
					<a href="liveDetail.html" class="liveTitle"><span><i class="icon-play-arrow"></i></span>
						<h3>中国近现代史 太平天国运动</h3></a>
					<div class="liveInfoCont">
						<div class="liveInfoAuthor">
							<i class="icon-person"></i><span>刘梅</span>
						</div>
						<div class="liveInfoOpt">
							<span><i class="icon-chat-3"></i>24</span><span><i class="icon-visibility"></i>308</span>
						</div>
					</div>
				</div>
				<div class="liveInfo">
					<a href="liveDetail.html" class="liveTitle"><h3>中国近现代史 太平天国运动</h3></a>
					<div class="liveInfoCont">
						<div class="liveInfoAuthor">
							<i class="icon-person"></i><span>刘梅</span>
						</div>
						<div class="liveInfoOpt">
							<span>2015-10-10 10:10</span>
						</div>
					</div>
				</div>
				<div class="liveInfo">
					<a href="liveDetail.html" class="liveTitle"><h3>中国近现代史 太平天国运动</h3></a>
					<div class="liveInfoCont">
						<div class="liveInfoAuthor">
							<i class="icon-person"></i><span>刘梅</span>
						</div>
						<div class="liveInfoOpt">
							<span>2015-10-10 10:10</span>
						</div>
					</div>
				</div>
				<div class="liveInfo">
					<a href="liveDetail.html" class="liveTitle"><h3>中国近现代史 太平天国运动</h3></a>
					<div class="liveInfoCont">
						<div class="liveInfoAuthor">
							<i class="icon-person"></i><span>刘梅</span>
						</div>
						<div class="liveInfoOpt">
							<span>2015-10-10 10:10</span>
						</div>
					</div>
				</div>
				<div class="more">
					<a href="liveCourses.html">更多...</a>
				</div>
			</div>
			<!--直播结束-->

			<!--点播开始-->
			<div style="display: none;" class="contentLive" id="dbcontent">
				<div id="dbDiv">
					<!-- 点播列表 -->
				</div>
				<div class="more">
					<a href="${ctx}/mobile/unicast">更多...</a>
				</div>
			</div>
			<!--点播结束-->
			<script type="text/javascript">
				/**********************index 点播*********************/
				function unicastView(data, isClear) {
					if (isClear) {
						$("#dbDiv").html("");
					}
					if (data.count > 0) {
						var unicastDiv = '';
						$.each(data.data, function(index, i) {
							unicastDiv += '<div class="liveInfo">';
							unicastDiv += '<a href="liveDetail.html" class="liveTitle"><h3>' + i.courseName + '</h3></a>';
							unicastDiv += '<div class="liveInfoCont">';
							unicastDiv += '<div class="liveInfoAuthor">';
							unicastDiv += '<i class="icon-person"></i><span>' + i.username + '</span>';
							unicastDiv += '</div>';
							unicastDiv += '<div class="liveInfoOpt">';
							/* 观看状态 */
							console.log(i.playtime + "/" + i.duration)
							if (i.playtime === i.duration && i.playtime !== 0) {
								unicastDiv += '<span>已看完</span>';
							} else if (0 < i.playtime < i.duration && i.playtime !== 0) {
								unicastDiv += '<span>剩余' + $.formatSeconds(i.duration - i.playtime, ":") + '</span>';
							} else {
								unicastDiv += '<span><i class="icon-chat-3"></i>' + i.commentNum + '</span>';
								unicastDiv += '<span><i class="icon-visibility"></i>' + i.viewNum + '</span>';
							}
							/* end观看状态 */
							unicastDiv += '</div>';
							unicastDiv += '</div>';
							if (i.duration === 0 || i.duration === null || i.duration === undefined) {
								unicastDiv += '<div class="time">未知时长</div></div>';
							} else {
								unicastDiv += '<div class="time">' + $.formatSeconds(i.duration, ":") + '</div></div>';
							}
						})
						$("#dbDiv").append(unicastDiv);
					} else {
						console.log("暂无点播资源！");
					}
				}
				function getUnicast(pageIndex, name, deptid, loginname, pageSize) {
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
						render : unicastView,
						operation : "查询点播列表"
					});
				}
				/**********************index 点播*********************/
				$(function() {
					getUnicast();
				})
			</script>
		</div>
	</div>
	<footer> </footer>
</body>
</html>