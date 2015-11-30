<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head lang="en">
<%@ include file="/WEB-INF/views/include/mobileHead.jsp"%>
<title>我的收藏</title>
</head>
<body>
	<header id="header">
		<div class="back">
			<a href="./index"><i class="icon-chevron-left"></i></a>
		</div>
		<div class="iconBar">
			<a href="#" id="btn_collection_del"><i class="icon-delete"></i></a>
		</div>
		我的收藏
	</header>
	<div class="mainContent paddingTop50">
		<!--我的收藏开始-->
		<div class="contentLive" id="collection_list">
			<!-- 收藏列表 -->
		</div>
		<div class="nocollect" id="no_collection" style="display:none">
			<i class="icon-star"></i>
			<div class="noResentLiveTip">
				<div>您最近没有收藏的视频</div>
				<div>
					请进入<a href="./unicast">点播栏目</a>收藏
				</div>
			</div>
		</div>
		<div class="deletAll" id="del_btn" style="display:none;">
			<button id="btn_select_all">
				<i class="icon-check2" style="display:none;"></i> <span class="icon-check-all"></span><span>全选</span>
			</button>
			<button id="btn_delete_confirm">
				<span>删除</span>
			</button>
		</div>
		<!--我的收藏结束-->
		<script type="text/javascript">
			var isDeleteMode = false;
			var deleteIds = [];
			var pageData = [];
			var isLoaded = false;
			function onDeleteSelect() {
				$("#collection_list").find(".enable").each(function() {
					$(this).off("click");
					$(this).on("click", function() {
						if ($(this).find(".icon-check1").length > 0) {
							$(this).html('<i class="icon-check2"></i>');
						} else {
							$(this).html('<span class="icon-check1"></span>')
						}
					})
				})
			}
			function renderCollection(data) {
				var renderDiv = "";
				$.each(data, function(i, d) {
					if (isDeleteMode) {
						renderDiv += '<div class="deletList">';
						renderDiv += '<div class="enable"><span class="icon-check1"></span></div>';
						renderDiv += '<div class="liveInfo widChange">';
					} else {
						renderDiv += '<div class="liveInfo">';
					}
					renderDiv += '<span class="id_class" style="display:none">' + d.id + '</span><a href="liveDetail.html" class="liveTitle"><h3>' + d.courseName + '</h3></a>';
					renderDiv += '<div class="liveInfoCont">';
					renderDiv += '<div class="liveInfoAuthor">';
					renderDiv += '<i class="icon-person"></i><span>' + d.username + '</span>';
					renderDiv += '</div>';
					renderDiv += '<div class="liveInfoOpt">';
					if (d.playtime === d.duration && d.playtime !== 0) {
						renderDiv += '已看完';
					} else if (0 < d.playtime < d.duration && d.playtime !== 0) {
						renderDiv += ('剩余' + $.formatSeconds(d.duration - d.playtime, ":"));
					} else {
						renderDiv += '<span><i class="icon-chat-3"></i>' + d.commentNum + '</span><span><i class="icon-visibility"></i>' + d.viewNum + '</span>';
					}
					renderDiv += '</div>';
					renderDiv += '</div>';
					renderDiv += '<div class="time">' + d.watchtime + '</div>';
					/* if (d.playtime === 0) {
						renderDiv += '<div class="time">未观看</div>';
					} else {
						renderDiv += '<div class="time">' + $.formatSeconds(d.playtime, ":") + '</div>';
					} */
					renderDiv += '</div>';
					if (isDeleteMode) {
						renderDiv += '</div>';
					}
				})
				return renderDiv;
			}
			function renderCollectionList(data, isClear) {
				pageData.push(data);
				$("#no_collection").hide();
				$.renderView({
					data : data.data,
					containerId : "collection_list",
					callback : renderCollection,
					noCallback : function() {
						$("#no_collection").show();
					},
					isClear : isClear
				})
				isLoaded = true;
				if (isDeleteMode) {
					onDeleteSelect();
				}
			}
			function collectionList(pageIndex, isClear, pageSize) {
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
					url : $.urls.collectionList,
					data : data,
					operation : "查询收藏列表",
					render : renderCollectionList,
					isClear : isClear
				})
			}
			function delCollection(ids) {
				$.sendPost({
					url : $.urls.removeCollections,
					data : ids,
					operation : "删除收藏",
					successCallback : function(d) {
						if (d.id === "1") {
							collectionList(1, true);
							$.message(d.content, "success");
						} else {
							$.message(d.content, "error");
						}
					}
				})
			}
			$(function() {
				collectionList();
				$("#btn_collection_del").on("click", function() {
					if ($("#collection_list").find(".liveInfo").length > 0) {
						if ($("#collection_list").find(".widChange").length === 0) {
							isDeleteMode = true;
							$("#collection_list").find(".liveInfo").wrap('<div class="deletList"></div>').addClass("widChange");
							$("#collection_list").find(".liveInfo").each(function(i) {
								$(this).before('<div class="enable"><span class="icon-check1"></span></div>');
							})
							$("#del_btn").show();
							$(".icon-delete").get(0).style.color = "#FF0000";
							onDeleteSelect();
						} else {
							isDeleteMode = false;
							$("#collection_list").find(".liveInfo").unwrap('<div class="deletList"></div>').removeClass("widChange");
							$("#collection_list").find(".enable").each(function(i) {
								$(this).remove();
							})
							$("#del_btn").hide();
							$(".icon-delete").get(0).style.color = "#666666";
							$("#btn_select_all").find(".icon-check-all").show();
							$("#btn_select_all").find(".icon-check2").hide();
						}
					}
				})
				$("#btn_select_all").on("click", function() {
					if ($(this).find(".icon-check2").get(0).style.display === "none") {
						$(this).find(".icon-check-all").hide();
						$(this).find(".icon-check2").show();
						$("#collection_list").find(".enable").each(function() {
							$(this).html('<i class="icon-check2"></i>');
						})
					} else {
						$(this).find(".icon-check-all").show();
						$(this).find(".icon-check2").hide();
						$("#collection_list").find(".enable").each(function() {
							$(this).html('<span class="icon-check1"></span>')
						})
					}
				})
				$("#btn_delete_confirm").on("click", function() {
					deleteIds = [];
					$("#collection_list").find(".deletList").each(function(i, d) {
						if ($(this).find(".icon-check2").eq(0).length > 0) {
							deleteIds.push($(this).find(".liveInfo").find("span").text());
						}
					})
					if (deleteIds.length > 0) {
						delCollection(deleteIds);
					} else {
						$.message("请选中要删除的收藏记录", "error");
					}
				})
				$(window).on("scroll", function() {
					var top = $("#header").height() + 10;
					var originHeight = $(document).height();
					var trigerHeight = originHeight - $(window).height();
					if (trigerHeight <= ($(window).scrollTop() + top)) {
						if (isLoaded) {
							var prevPage = pageData[0].page.pageIndex;
							var totalPage = Math.ceil(pageData[1].total / pageData[0].page.limit);
							if (prevPage < totalPage) {
								collectionList(prevPage + 1, false, pageData[0].page.limit);
							}
						}
					}
				})
			})
		</script>
	</div>
</body>
</html>