/**
 * 页面切换
 */
var viewIndex;
var showView = function(index) {
	$("#reviewInfoMore").fadeOut();
	$("#writingManage").fadeOut();
	$("#activeDetailTab").children("div:eq(" + index + ")").fadeIn();
	$("#activeDetailTab").children("div:not(:eq(" + index + "))").fadeOut();
	$("#active_navbar").find("li:not(:eq(" + index + "))").removeClass("cur");
	$("#active_navbar").find("li:eq(" + index + ")").addClass("cur");
	if (index == 0) {
		getWorksList();
		workManagerList();
	} else if (index == 1) {
		if (currentUser == null || currentUser == undefined) {
			window.location = "../login";
			// return;
		} else {
			if (currentUser.roles.indexOf("系统管理员") != -1 || currentUser.roles.indexOf('学校管理员') != -1) {
				console.log(currentUser.adminFlag, currentUser.roles.indexOf('学校管理员'));
				getWorksWithReview();
			} else {
				addMessage("error", "对不起，您没有系统管理员、学校管理员权限！");
			}
		}
	} else if (index == 2) {
		if (currentUser == null || currentUser == undefined) {
			window.location = "../login";
			// return;
		} else {
			if (currentUser.roles.indexOf("学校管理员") != -1 || currentUser.usertype == '1') {
				$("#active_detail_review_detail").show();
			} else {
				$("#active_detail_review_detail").hide();
			}
			getBarChart();
			getPieChart();
			getReviewUserDetail();
		}
	} else {
		if (currentUser == null || currentUser == undefined) {
			window.location = "../login";
			// return;
		} else {
			if (currentUser.usertype != '1') {
				addMessage("error", "对不起，您没有报名权限。");
			}
		}
	}
	// 当前打开的视图
	viewIndex = index;
	return false;
};
/**
 * 进入作品管理
 */
var enterWorksManage = function() {
	if (currentUser == null || currentUser == undefined) {
		window.location = "../login";
		return;
	}
	workManagerList();
	$("#writingManage").fadeIn();
	$("#activeDetailTab").children("div:eq(0)").fadeOut();
	return false;
};
/**
 * 作品管理，查询作品
 */
var tempWorkManager;
var workManagerList = function(pageIndex) {
	var keywords = {
		activeId : activeid
	};
	if (pageIndex == null || pageIndex == undefined || pageIndex == '') {
		pageIndex = 1;
	}
	;
	var page = {
		pageSize : 10,
		pageIndex : pageIndex,
		limit : 10
	};
	/*
	 * if (pageIndex == null || pageIndex == '' || pageIndex == undefined) {
	 * page.pageIndex = 1; } ;
	 */
	var data = {
		keywords : keywords,
		page : page
	};
	$.ajax({
		url : './worksList',
		type : 'POST',
		dataType : 'json',
		cache : true,
		contentType : 'application/json; charset=UTF-8',
		data : JSON.stringify(data),
		success : function(d) {
			console.log("作品管理列表：", d);
			var worksView = "";
			if (d.total > 0) {
				tempWorkManager = d.data;
				$.each(d.data, function(i, work) {
					worksView += "<tr>";
					if (work.picture != null && work.picture != undefined && work.picture != null) {
						worksView += "<td><input value='" + work.id + "' type=\"checkbox\" name=\"personExpert\"><img src='" + work.picture + "' width='253' height='165'/>" + work.name + "</td>";
					} else {
						worksView += "<td><input value='" + work.id + "' type=\"checkbox\" name=\"personExpert\"><img src='../static/img/front-cover.jpg' width='253' height='165'/>" + work.name
								+ "</td>";
					}
					;
					worksView += "<td>" + work.authorName + "</td>";
					if (work.subject != null && work.subject != undefined) {
						worksView += "<td>" + work.subject + "</td>";
					} else {
						worksView += "<td>未知</td>";
					}
					if (work.specialers != null && work.specialers != '') {
						var spe = work.specialers.split(",");
						worksView += "<td>";
						for (var i = 0; i < spe.length; i++) {
							var s = spe[i].split("-")
							if (s[1] == '0') {
								if (i == spe.length - 1) {
									worksView += "<span style='padding: 0px 4px 0px 0px;' title='未评审'>" + s[0] + "</span>";
								} else {
									worksView += "<span style='padding: 0px 4px 0px 0px;' title='未评审'>" + s[0] + ";</span>";
								}
							} else {
								if (i == spe.length - 1) {
									worksView += "<span title='已评审' style='color:red;padding: 0px 4px 0px 0px;'>" + s[0] + "</span>";
								} else {
									worksView += "<span title='已评审' style='color:red;padding: 0px 4px 0px 0px;'>" + s[0] + ";</span>";
								}
							}
						}
						worksView += "</td>";
					} else {
						worksView += "<td><span class=\"wait-distribution\">--待分配--</span></td>";
					}
					;
					if (currentActive.model === "1") {
						worksView += "<td><i class=\"icon-my-library-del\" onclick=\"openDeleteWorks(" + work.id + ")\"></i></td></tr>";
					} else if (currentActive.model === '0') {
						worksView += "<td><i class=\"icon-person-add\" onclick=\"singleAssign(" + work.id + ")\"></i><i class=\"icon-my-library-del\" onclick=\"openDeleteWorks(" + work.id
								+ ")\"></i></td></tr>";
					} else {
						addMessage("error", "未指定评审专家分配方式！");
					}
				});
				/** *****************************************分页条******************************************** */
				var pageTotal;
				if (d.total % page.pageSize == 0) {
					pageTotal = d.total / page.pageSize;
				} else {
					pageTotal = Math.floor(d.total / page.pageSize) + 1;
				}
				if (pageTotal > 1) {
					var pagination = "";
					// 分页条的起始下标
					var begin, end;
					if (pageTotal > 10) {
						if (pageIndex > 6) {
							begin = (pageTotal - pageIndex > 4) ? pageIndex - 5 : (pageTotal - 9);
							end = (pageTotal - pageIndex > 4) ? pageIndex + 4 : pageTotal;
						} else {
							begin = 1;
							end = 10;
						}
					} else {
						begin = 1, end = pageTotal;
					}
					pagination += "<div class=\"col-xs-12 text-right margin-top-20\">";
					pagination += "<nav><ul class=\"pagination\">";
					// 如果当前页大于1，显示上一页按钮
					if (pageIndex > 1) {
						pagination += "<li><a onclick=\"onManagerSelectPage(1)\" aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
						pagination += "<li><a onclick=\"onManagerSelectPage(" + (pageIndex - 1) + ")\"><i class='icon-chevron-left'></i></a></li>";
					} else {
						pagination += "<li class=\"disabled\"><a aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
						pagination += "<li class=\"disabled\"><a ><i class='icon-chevron-left'></i></a></li>";
					}
					// 中间页
					for (var i = begin; i <= end; i++) {
						if (i == pageIndex) {
							pagination += "<li class=\"active\"><a class='pagination_index_mine' style='padding: 6px 2px;'>" + i + "<span class=\"sr-only\">(current)</span></a></li>";
						} else {
							pagination += "<li><a class='pagination_index_mine' style='padding: 6px 2px;' onclick=\"onManagerSelectPage(" + i + ")\">" + i
									+ "<span class=\"sr-only\">(current)</span></a></li>";
						}
					}
					// 如果当前页小于总页数，显示下一页按钮
					if (pageIndex < pageTotal) {
						pagination += "<li><a onclick=\"onManagerSelectPage(" + (pageIndex + 1) + ")\"><i class='icon-chevron-right'></i></a></li>";
						pagination += "<li onclick=\"onManagerSelectPage(" + pageTotal + ")\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
					} else {
						pagination += "<li class=\"disabled\"><a><i class='icon-chevron-right'></i></a></li>";
						pagination += "<li class=\"disabled\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
					}
					pagination += "</ul></nav></div>";
				}
				;
				/** ********************************************************************************** */

			} else {
				addMessage("success", "暂无参赛作品");
			}
			;
			$("#table_works_assign").html(worksView);
			$("#works_assign_pagination").html(pagination);
		},
		error : function(xhr) {
			addMessage("error", "不好意思，请求出错了，错误码" + xhr.status);
		}
	});
};
var onManagerSelectPage = function(pageIndex) {
	workManagerList(pageIndex);
};
/**
 * 查询本次活动的专家
 */
var findSpecialist = function() {
	$.ajax({
		url : '../private/reviewUsers',
		type : 'POST',
		dataType : 'json',
		cache : false,
		data : jQuery.param({
			activeId : activeid
		}),
		success : function(data) {
			console.log("查询本次活动的所有专家:", data);
			if (data.length > 0) {
				var list = "";
				$.each(data, function(i, d) {
					list += "<li><input onclick=\"updataSpecialistAmount()\" type=\"checkbox\" value=\"" + d.userid + "\"/>" + d.username + "</li>";
				});
				$("#list_expert").html(list);
			} else {
				addMessage("success", "您还没有为本次活动添加评审专家");
			}
		},
		error : function(code) {
			addMessage("error", "不好意思，请求出错了，错误码" + code.status);
		}
	});
};
/**
 * 删除作品确认框
 */

var tempWork;
var worksDel;
var openDeleteWorks = function(worksid) {
	if (currentUser.roles.indexOf("系统管理员") != -1) {
		worksDel = findViewWorkById(worksid);
		if (worksDel != null) {
			$("#works_delete").css("margin-left", "-" + ($("#works_delete").outerWidth() / 2) + "px");
			$("html").addClass("noScroller");
			$("#works_del_author").html(worksDel.authorName);
			$("#works_del_name").html(worksDel.name);
			$("#works_delete,.overlay").slideDown();
		}
	} else {
		addMessage("error", "对不起，您没有管理员权限！");
	}
};
var findViewWorkById = function(worksid) {
	$.each(tempWorkManager, function(i, t) {
		if (worksid == t.id) {
			tempWork = t;
		}
	});
	if (tempWork != null && tempWork != undefined && tempWork != "") {
		return tempWork;
	} else {
		return null;
	}
}
/**
 * 取消删除作品
 */
var cancelDeleteWorks = function() {
	$("html").removeClass("noScroller");
	$("#works_delete,.overlay").slideUp();
	return false;
};
/**
 * 删除作品
 */
var deleteWorks = function() {
	var worksIdDel = [];
	worksIdDel.push(worksDel.id);
	console.log("删除作品传值:", worksIdDel);
	$.ajax({
		url : "../private/deleteWorks",
		type : "POST",
		dataType : 'json',
		cache : false,
		contentType : 'application/json; charset=UTF-8',
		data : JSON.stringify(worksIdDel),
		success : function(data) {
			console.log("删除作品返回数据:", data);
			if (data.id === "1") {
				cancelDeleteWorks();
				addMessage("success", data.content);
				workManagerList();
				getWorksList();
			} else {
				addMessage("error", data.content);
			}
		},
		error : function(code) {
			addMessage("error", "不好意思，请求出错了，错误码" + code.status);
		}
	});
};
/**
 * 打开分配专家model
 */
var showAssign = function(single) {
	if (currentUser.roles.indexOf("系统管理员") != -1) {
		if ($("input[name='personExpert']:checked").length > 0 || (single != null && single != '' && single != undefined)) {
			findSpecialist();
			$("html").addClass("noScroller");
			$("#selExpertBox").css("left", "50%");
			$("#selExpertBox").css("margin-left", "-" + ($("#selExpertBox").outerWidth() / 2) + "px");
			$("#list_expert_count").html("已选择0位专家");
			$("#selExpertBox,.overlay").slideDown();
		} else {
			addMessage("error", "请您先选择作品");
		}
	} else {
		addMessage("error", "对不起,您没有管理员权限！");
	}
};
var single = ""; // 选择单个作品分配
var singleAssign = function(worksid) {
	if (currentUser.roles.indexOf("系统管理员") != -1) {
		single = worksid;
		showAssign(single);
	} else {
		addMessage("error", "对不起,您没有管理员权限！");
	}
};
var closeAssign = function() {
	$("html").removeClass("noScroller");
	$("#selExpertBox,.overlay").slideUp();
};
/**
 * 选择专家
 */
var selectExpert = [];
var selectWorks = [];
var updataSpecialistAmount = function() {
	// console.log("selectWorks:",$("input[name='personExpert']:checked"));
	console.log("checkedExperts:", $("#list_expert").find("li").children("input:checked"));
	var count = $("#list_expert").find("li").children("input:checked").length;
	$("#list_expert_count").html("已选择" + count + "位专家");
};
/**
 * 确定分配专家
 */

var okAssignSpecialist = function() {
	selectExpert = [];
	selectWorks = [];
	$.each($("#list_expert").find("li").children("input:checked"), function(i, item) {
		selectExpert.push($(item).val());
	});
	if (single == undefined || single == null || single == '') {
		$.each($("input[name='personExpert']:checked"), function(i, it) {
			selectWorks.push($(it).val());
		});
	} else {
		selectWorks.push(single);
	}
	console.log("选中的专家:", selectExpert);
	console.log("选中的作品:", selectWorks);
	if (selectExpert.length > 0 && selectWorks.length > 0) {
		assignSpecialist(selectWorks, selectExpert);
	}
	;
};
/**
 * 分配专家请求
 */
var assignSpecialist = function(selectWorks, selectExpert) {
	var assign = {
		worksIds : selectWorks,
		specialist : selectExpert,
		activeId : activeid
	};
	console.log("分配专家传值:", assign);
	$.ajax({
		url : '../private/assignSpecialist.do',
		type : 'POST',
		cache : false,
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		data : JSON.stringify(assign),
		success : function(data) {
			console.log("分配专家返回数据:", data);
			if (data.id === "1") {
				workManagerList();
				closeAssign();
				addMessage("success", data.content);
			} else {
				closeAssign();
				// workManagerList();
				addMessage("error", data.content);
			}
		},
		error : function(code) {
			closeAssign();
			addMessage("error", "不好意思，请求出错了，错误码" + code.status);
		}
	});
};
// 活动id
var activeid = $.getUrlParam("activeid");
/**
 * 提示
 */
var addMessage = function(type, message) {
	$("#message_content").html("");
	$("#message_content").removeClass("message_success");
	$("#message_content").removeClass("message_error");
	if (message != null && message != '') {
		if (type == 'success') {
			$("#message_content").addClass("message_success");
		} else {
			$("#message_content").addClass("message_error");
		}
		;
		$("#message_content").html(message);
		$("#message_info").fadeIn();
		setTimeout(function() {
			$("#message_info").fadeOut();
		}, 2000);
	}
	;
};
/**
 * 根据活动id查询活动详情
 */
var currentActive;
var queryActiveDetail = function() {
	console.log("根据活动id查询活动详情参数activeid:", activeid);
	$.ajax({
		url : '../gateway/getActiveById',
		type : 'POST',
		dataType : 'json',
		async : false,
		// contentType : 'application/json; charset=UTF-8',
		cache : false,
		data : jQuery.param({
			activeid : activeid
		}),
		success : function(da) {
			console.log("活动详情：", da);
			if (da != null && da != undefined) {
				currentActive = da;
				if (da.picture != null && da.picture != undefined && da.picture != '') {
					$("#active_img").attr("src", da.picture);
				} else {
					$("#active_img").attr("src", "../static/img/front-cover.jpg");
				}
				;
				if (da.status === '未开始') {
					$("#active_status").html("<span class='enrollIng'>报名未开始</span>");
				} else if (da.status === '报名中,评审中') {
					$("#active_status").html("<span class='enrollIng'>正在报名...</span><span class='review'>正在评审...</span>");
				} else if (da.status === '报名中,评审未开始') {
					$("#active_status").html("<span class='enrollIng'>正在报名...</span><span class='review'>尚未评审</span>");
				} else if (da.status === '报名已结束,评审未开始') {
					$("#active_status").html("<span class='enrollIng'>报名结束</span><span class='review'>尚未评审</span>");
				} else if (da.status === '报名已结束,评审中') {
					$("#active_status").html("<span class='enrollIng'>报名结束</span><span class='review'>正在评审</span>");
				} else if (da.status === '已结束') {
					$("#active_status").html("<span class='over'>已经结束</span>");
				} else {
					$("#active_status").html("");
				}
				;
				$("#active_title").html(da.name);
				$("#active_regdate").html('报名时间:' + da.regdate);
				$("#active_reviewdate").html('评审时间:' + da.condate);
				$("#active_description").html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + da.description);
			}
		},
		error : function(code) {
			addMessage("error", "不好意思，请求出错了，错误码" + code.status);
		}
	});
};
/**
 * 查询参赛作品
 */
var getWorksList = function(pageIndex) {
	// $("#div_works_view").html("");
	var keywords = {
		activeId : activeid
	};
	if (pageIndex == null || pageIndex == undefined || pageIndex == '') {
		pageIndex = 1;
	}
	;
	var page = {
		pageSize : 8,
		pageIndex : pageIndex,
		limit : 8
	};
	if (pageIndex == null || pageIndex == '' || pageIndex == undefined) {
		page.pageIndex = 1;
	}
	;
	var data = {
		keywords : keywords,
		page : page
	};
	$.ajax({
		url : '../gateway/worksList',
		type : 'POST',
		dataType : 'json',
		cache : false,
		contentType : 'application/json; charset=UTF-8',
		data : JSON.stringify(data),
		success : function(d) {
			console.log("参赛作品列表：", d);
			var worksView = "";
			if (d.total > 0) {
				worksView += "<div class='col-xs-12 margin-bottom-15 margin-top-15'>";
				worksView += "<div class='top pull-right clearfix'>共计" + d.total + "个作品";
				if (currentUser != null && currentUser != undefined) {
					if (currentUser.roles.indexOf("系统管理员") != -1) {
						worksView += "<button class='btn btn-bg-green margin-left-5' onclick=\"enterWorksManage()\" id='goWritingManage'><i class='icon-create margin-right-5'></i>进入作品管理</button>";
					}
				}
				worksView += "</div></div>";
				$.each(d.data, function(i, work) {
					worksView += "<div class='col-xs-12 col-xs-6 col-sm-4 col-lg-3'><div class='writingItem'><div class='writingImg'>";
					// 1直播，2 录像
					if (work.type == 1) {
						worksView += "<a href='./courseDetail?curriculumid=" + work.resourceid + "&workid=" + work.id + "'>";
					} else {
						worksView += "<a href='./courseDetail?workid=" + work.id + "&source=" + work.resourceSource + "'>";
					}
					if (work.picture != null && work.picture != undefined && work.picture != null) {
						worksView += "<img src='" + work.picture + "' width='253' height='165'/>";
					} else {
						worksView += "<img src='../static/img/front-cover.jpg' width='253' height='165' />";
					}
					;
					// 1直播，2 录像
					if (work.type == 1) {
						worksView += "</a></div><h3><a href='./courseDetail?curriculumid=" + work.resourceid + "&workid=" + work.id + "'>" + work.name + "</a></h3>";
					} else {
						worksView += "</a></div><h3><a href='./courseDetail?workid=" + work.id + "&source=" + work.resourceSource + "'>" + work.name + "</a></h3>";
					}
					worksView += "<p class='author'>" + work.authorName + " | <span>" + work.deptName + "</span></p>";
					if (work.type == '1') {
						worksView += "<div class='writingSrc bg-color-red'><i class='icon-cast-connected'></i>[直播]第" + work.weeks + "周 第" + work.sameclass + "节</div></div></div>";
					} else {
						worksView += "<div class='writingSrc bg-color-blue'><i class='icon-cast-connected'></i>[录像]</div></div></div>";
					}
				});
				/** *****************************************分页条******************************************** */
				var pageTotal;
				if (d.total % page.pageSize == 0) {
					pageTotal = d.total / page.pageSize;
				} else {
					pageTotal = Math.floor(d.total / page.pageSize) + 1;
				}
				if (pageTotal > 1) {

					// 分页条的起始下标
					var begin, end;
					if (pageTotal > 10) {
						if (pageIndex > 6) {
							begin = (pageTotal - pageIndex > 4) ? pageIndex - 5 : (pageTotal - 9);
							end = (pageTotal - pageIndex > 4) ? pageIndex + 4 : pageTotal;
						} else {
							begin = 1;
							end = 10;
						}
					} else {
						begin = 1, end = pageTotal;
					}
					worksView += "<div class=\"col-xs-12 text-right margin-top-20\">";
					worksView += "<nav><ul class=\"pagination\">";
					// 如果当前页大于1，显示上一页按钮
					if (pageIndex > 1) {
						worksView += "<li><a onclick=\"onSelectPage(1)\" aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
						worksView += "<li><a onclick=\"onSelectPage(" + (pageIndex - 1) + ")\"><i class='icon-chevron-left'></i></a></li>";
					} else {
						worksView += "<li class=\"disabled\"><a aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
						worksView += "<li class=\"disabled\"><a ><i class='icon-chevron-left'></i></a></li>";
					}
					// 中间页
					for (var i = begin; i <= end; i++) {
						if (i == pageIndex) {
							worksView += "<li class=\"active\"><a class='pagination_index_mine' style='padding: 6px 2px;'>" + i + "<span class=\"sr-only\">(current)</span></a></li>";
						} else {
							worksView += "<li><a class='pagination_index_mine' style='padding: 6px 2px;' onclick=\"onSelectPage(" + i + ")\">" + i
									+ "<span class=\"sr-only\">(current)</span></a></li>";
						}
					}
					// 如果当前页小于总页数，显示下一页按钮
					if (pageIndex < pageTotal) {
						worksView += "<li><a onclick=\"onSelectPage(" + (pageIndex + 1) + ")\"><i class='icon-chevron-right'></i></a></li>";
						worksView += "<li onclick=\"onSelectPage(" + pageTotal + ")\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
					} else {
						worksView += "<li class=\"disabled\"><a><i class='icon-chevron-right'></i></a></li>";
						worksView += "<li class=\"disabled\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
					}
					worksView += "</ul></nav></div>";
				}
				;
				/** ********************************************************************************** */

			} else {
				addMessage("success", "暂无参赛作品");
			}
			;
			$("#div_works_view").html(worksView);
		},
		error : function(code) {
			addMessage("error", "不好意思，请求出错了，错误码" + code.status);
		}
	});
};
/**
 * 参赛作品分页事件
 */
var onSelectPage = function(pageIndex) {
	getWorksList(pageIndex);
	return false;
};
/**
 * 查询评审情况
 */
var worksReview;
var getWorksWithReview = function(pageIndex) {
	worksReview = [];
	console.log("worksReview0", worksReview);
	// $("#table_works_review").html("");
	var keywords = {
		activeId : activeid
	};
	if (pageIndex == null || pageIndex == undefined || pageIndex == '') {
		pageIndex = 1;
	}
	;
	var page = {
		pageSize : 10,
		pageIndex : pageIndex,
		limit : 10
	};
	if (pageIndex == null || pageIndex == '' || pageIndex == undefined) {
		page.pageIndex = 1;
	}
	;
	var data = {
		keywords : keywords,
		page : page
	};
	console.log(data);
	$.ajax({
		url : '../private/getReviewList',
		type : 'POST',
		dataType : 'json',
		async : false,
		cache : false,
		contentType : 'application/json; charset=UTF-8',
		data : JSON.stringify(data),
		success : function(d) {
			console.log("评审参赛作品列表：", d);
			var trs = '';
			if (d.total > 0) {
				$("#table_works_review_header").fadeIn();
				for (var i = 0; i < d.data.length; i++) {
					worksReview = d.data;
					console.log("worksReview", worksReview);
					trs += "<tr>";
					if (d.data[i].picture != null && d.data[i].picture != undefined && d.data[i].picture != '') {
						trs += "<td><img src='" + d.data[i].picture + "' width='62' height='45'/>" + d.data[i].name + "</td>"
					} else {
						trs += "<td><img src='../static/img/lemon-pages.jpg' width='62' height='45'/>" + d.data[i].name + "</td>"
					}
					;
					trs += "<td>" + d.data[i].authorName + "</td>";
					trs += "<td>" + d.data[i].reviewprogress + "</td>";
					trs += "<td>" + d.data[i].maxscore + "/" + d.data[i].minscore + "</td>";
					trs += "<td>" + d.data[i].avgscore + "</td>";
					trs += "<td><a class='goReviewInfoPage' onclick='showReviewInfoMore(" + d.data[i].id + ")'>评审详情</a></td>";
					trs += "</tr>";

				}
				;
				/** *****************************************分页条******************************************** */
				var pageTotal;
				if (d.total % page.pageSize == 0) {
					pageTotal = d.total / page.pageSize;
				} else {
					pageTotal = Math.floor(d.total / page.pageSize) + 1;
				}
				if (pageTotal > 1) {
					var pagination = "";
					// 分页条的起始下标
					var begin, end;
					if (pageTotal > 10) {
						if (pageIndex > 6) {
							begin = (pageTotal - pageIndex > 4) ? pageIndex - 5 : (pageTotal - 9);
							end = (pageTotal - pageIndex > 4) ? pageIndex + 4 : pageTotal;
						} else {
							begin = 1;
							end = 10;
						}
					} else {
						begin = 1, end = pageTotal;
					}
					pagination += "<div class=\"col-xs-12 text-right margin-top-20\">";
					pagination += "<nav><ul class=\"pagination\">";
					// 如果当前页大于1，显示上一页按钮
					if (pageIndex > 1) {
						pagination += "<li><a onclick=\"onReviewSelectPage(1)\" aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
						pagination += "<li><a onclick=\"onReviewSelectPage(" + (pageIndex - 1) + ")\"><i class='icon-chevron-left'></i></a></li>";
					} else {
						pagination += "<li class=\"disabled\"><a aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
						pagination += "<li class=\"disabled\"><a><i class='icon-chevron-left'></i></a></li>";
					}
					// 中间页
					for (var i = begin; i <= end; i++) {
						if (i == pageIndex) {
							pagination += "<li class=\"active\"><a class='pagination_index_mine' style='padding: 6px 2px;'>" + i + "<span class=\"sr-only\">(current)</span></a></li>";
						} else {
							pagination += "<li><a class='pagination_index_mine' style='padding: 6px 2px;' onclick=\"onReviewSelectPage(" + i + ")\">" + i
									+ "<span class=\"sr-only\">(current)</span></a></li>";
						}
					}
					// 如果当前页小于总页数，显示下一页按钮
					if (pageIndex < pageTotal) {
						pagination += "<li><a onclick=\"onReviewSelectPage(" + (pageIndex + 1) + ")\"><i class='icon-chevron-right'></i></a></li>";
						pagination += "<li onclick=\"onReviewSelectPage(" + pageTotal + ")\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
					} else {
						pagination += "<li class=\"disabled\"><a><i class='icon-chevron-right'></i></a></li>";
						pagination += "<li class=\"disabled\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
					}
					pagination += "</ul></nav></div>";
				}
				/** ********************************************************************************** */
			} else {
				$("#table_works_review_header").hide();
				addMessage("success", "暂无评审情况！");
			}
			;
			$("#table_works_review").html(trs);
			$("#review_work_pagination").html(pagination);
		},
		error : function(code) {
			addMessage("error", "不好意思，请求出错了，错误码" + code.status);
		}
	});
};
var onReviewSelectPage = function(pageIndex) {
	getWorksWithReview(pageIndex);
	return false;
};
/**
 * 统计柱状图
 */
var getBarChart = function() {
	$("#active_chart_bar").html('');
	console.log(jQuery.param({
		activeid : activeid
	}));
	$.ajax({
		url : '../private/getSignerDept',
		type : 'POST',
		dataType : 'html',
		contentTpe : 'application/json; charset=UTF-8',
		cache : false,
		data : jQuery.param({
			activeid : activeid
		}),
		success : function(da) {
			console.log("柱状图数据", da);
			$("#active_chart_bar").html(da);
		},
		error : function(code) {
			addMessage("error", "不好意思，请求出错了，错误码" + code.status);
		}
	});
};
/**
 * 统计饼图
 */
var getPieChart = function() {
	$("#active_chart_pie").html('');
	$.ajax({
		url : '../private/getPieData',
		type : 'POST',
		dataType : 'html',
		contentTpe : 'application/json; charset=UTF-8',
		cache : false,
		data : jQuery.param({
			activeid : activeid
		}),
		success : function(da) {
			console.log("饼图数据", da);
			$("#active_chart_pie").html(da);
		},
		error : function(code) {
			addMessage("error", "不好意思，请求出错了，错误码" + code.status);
		}
	});
};
/**
 * 获得本次活动各专家的评审情况
 */
var getReviewUserDetail = function() {
	$("#review_users_amount").html("");
	$("#table_review_users").html("");
	$.ajax({
		url : '../private/getSpecialDetail.do',
		type : 'POST',
		dataType : 'json',
		// contentTpe : 'application/json;charset=UTF-8',
		cache : false,
		data : jQuery.param({
			activeid : activeid
		}),
		success : function(da) {
			console.log("统计，获取评审专家评审情况：", da);
			$("#review_users_amount").html("共" + da.length + "位专家");
			if (da != null && da.length > 0) {
				var trs = "";
				$.each(da, function(i, u) {
					trs += "<tr>";
					trs += "<td class='text-center'>" + u.specialName + "</td>";
					trs += "<td>" + u.reviewTotal + "</td>";
					trs += "<td>" + u.reviewedNum + "</td>";
					trs += "<td>" + u.unReviewNum + "</td>";
					trs += "<td>" + u.percent + "</td>";
					trs += "</tr>";
				});
				$("#table_review_users").html(trs);
			} else {
				$("#table_review_users").html("<tr class='error'>暂无评审专家！</tr>");
			}
			;
		},
		error : function(data) {
			console.log("getReviewUserDetail");
			// addMessage("error", "不好意思，请求出错了，错误码" + data.status);
		}
	});
};
/**
 * 查询作品评审详情
 */
var getDetailByWorkId = function(worksid) {
	// $("#table_review_info_more").html("");
	$.ajax({
		url : '../private/reviewDetail',
		type : 'POST',
		cache : false,
		dataType : 'json',
		contentTpe : 'application/json;charset=UTF-8',
		data : jQuery.param({
			worksid : worksid
		}),
		success : function(data) {
			console.log("查询作品评审详情页data:", data);
			var trs = "";
			if (data != null && data.length > 0) {
				$.each(data, function(i, d) {
					trs += "<tr>";
					trs += "<td class='text-center'>" + d.username + "</td>";
					if (d.reviewdateString != null && d.reviewdateString != undefined) {
						trs += "<td>" + d.reviewdateString + "</td>";
					} else {
						trs += "<td>暂未评审</td>";
					}
					;
					if (d.score != null && d.score != undefined) {
						trs += "<td>" + d.score + "</td>";
					} else {
						trs += "<td>暂未打分 </td>";
					}
					;
					if (d.remark != null && d.remark != undefined) {
						trs += "<td class=\"text-left\">" + d.remark + "</td>";
					} else {
						trs += "<td class=\"text-left\">暂无意见 </td>";
					}
					;
					trs += "</tr>";
				});
				trs += "<tr>";
				trs += "<td class=\"text-center\">综合</td>";
				trs += "<td colspan=\"3\" style=\"color:#bc272d;\" id =\"work_detail_avg\">" + tempWork.avgscore + "</td>";
				trs += "</tr>";
			} else {
				addMessage("success", "暂无评审详情");
			}
			;
			$("#table_review_info_more").html(trs);
		},
		error : function(data) {
			addMessage("error", "不好意思，请求出错了，错误码" + data.status);
		}
	});
};
/**
 * 打开作品评审详情页
 */
var tempWork;
var showReviewInfoMore = function(id) {
	tempWork = findWorkById(id);
	console.log("查看作品评审详情tempWork", tempWork);
	getDetailByWorkId(id);
	$("#work_detail_avgscore").html(tempWork.avgscore + "分");
	$("#work_detail_title").html(tempWork.name + "&nbsp;&nbsp;&nbsp;&nbsp;" + tempWork.authorName);
	$("#work_detail_review_amount").html(tempWork.reviewprogress);
	// $("#work_detail_avg").html(tempWork.avgscore);
	$("#reviewInfoMore").fadeIn();
	$("#activeDetailTab").children().fadeOut();
};
// 查找当前查看的作品
var findWorkById = function(worksid) {
	$.each(worksReview, function(i, d) {
		if (d.id == worksid) {
			tempWork = d;
		}
	});
	if (tempWork != null && tempWork != undefined && tempWork != null) {
		return tempWork;
	} else {
		return null;
	}
};
/**
 * @author huangzw
 * @function 活动报名
 */

// 用按钮选择文件
var onFileSelect = function() {
	var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;

	if (ie) {
		document.getElementById("works_file").click();
		document.getElementById("works_btn").value = document.getElementById("works_file").value;
		// imgPreview();
	} else {
		var a = document.createEvent("MouseEvents");
		a.initEvent("click", true, true);
		document.getElementById("works_file").dispatchEvent(a);
		// imgPreview();
	}
	;
};
var onVideoFileSelect = function() {
	var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;

	if (ie) {
		document.getElementById("choose").click();
		document.getElementById("choose_btn").value = document.getElementById("choose").value;
		// imgPreview();
	} else {
		var a = document.createEvent("MouseEvents");
		a.initEvent("click", true, true);
		document.getElementById("choose").dispatchEvent(a);
		// imgPreview();
	}
	;
	return false;
};
// 图片预览
var imgPreview = function() {
	console.log($("#works_file").val());
	var nameArray = $("#works_file").val().split("."); // 定义一数组
	var suffix = nameArray[nameArray.length - 1];
	if (suffix != 'jpg' && suffix != 'gif' && suffix != 'jpeg' && suffix != 'png') {
		addMessage("error", "你选择的不是图片,请选择图片！");
		document.getElementById('works_img').src = '../static/img/front-cover.jpg';
		$("#works_file").val('');
		// $("#fileBtn").css("border","1px solid red");
		// console.log("val",$("#active_file").val());
		return;
	} else {
		var f = document.getElementById('works_file').files[0];
		var src = window.URL.createObjectURL(f);
		document.getElementById('works_img').src = src;
	}
	;
};
// 选择直播课or录像文件
var source = "";
var selectWorkSource = function() {
	console.log("作品类型", $(":radio[name='broadcastOrVideotape']:checked").val());
	source = $(":radio[name='broadcastOrVideotape']:checked").val();
	$("#works_source_name").html("");
	if (source == '2') {
		selectedVideo = "";
		$("#works_source_type").html("选择录像文件");
	} else {
		$(":radio[name='selr']:checked").attr("checked", false);
		$("#works_source_type").html("选择直播课程");
	}
};
var weekMap = [ "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" ];
var openAddResourceView = function() {
	console.log("资源类型", source);
	$("html").addClass("noScroller");
	if (source === '2') {
		$("#select_record_film,.overlay").slideDown();
		showSelectFileView("1", "0");
	} else if (source === '1') {
		$("input[name='subject']").val("");
		var selectWeeksOptions = "<option value=\"\" selected>第几周</option>";
		for (var i = 1; i <= 52; i++) {
			selectWeeksOptions += "<option value='" + i + "'>第" + i + "周</option>";
		}
		;
		var selectWeekDateOptions = "<option value=\"\" selected>星期几</option>";
		for (var i = 1; i <= 7; i++) {
			selectWeekDateOptions += "<option value='" + i + "'>" + weekMap[i - 1] + "</option>";
		}
		$("#select_weeks").html(selectWeeksOptions);
		$("#select_weekdate").html(selectWeekDateOptions);
		getBroadCast();
		$("#selbroadcastBox").css("margin-left", "-" + ($("#selbroadcastBox").outerWidth() / 2) + "px");
		$("#selbroadcastBox,.overlay").slideDown();
	} else {
		addMessage("error", "请您选择作品来源");
	}
};
var closeAddResourceView = function() {
	// videoName = "";
	$("#works_source_name").html("");
	$("html").removeClass("noScroller");
	$("#selbroadcastBox,#select_record_film,.overlay").slideUp();
};
var okSelectFilm = function() {
	// var no = getFilmById(selectedVideo);
	if (curNode != null && curNode != undefined) {
		curNode = $.parseJSON(curNode);
		console.log("选中的节点：", curNode.description);
		if (curNode.title != null && curNode.title != undefined) {
			$("#works_name").val(curNode.title);
		} else {
			$("#works_name").val("");
		}
		//
		if (curNode.description != null && curNode.description != undefined) {
			$("#works_description").val(curNode.description);
		} else {
			$("#works_description").val("");
		}
		//
		if (curNode.imageurl != null && curNode.imageurl != undefined) {
			$("#works_img").attr("src", curNode.imageurl);
		} else {
			$("#works_img").attr("src", "../static/img/front-cover.jpg");
		}
		//
		if (curNode.subject != null && curNode.subject != undefined) {
			$("#works_subject").val(curNode.subject);
			/*
			 * $.each(course, function(i, d) { if (d.name == curNode.subject) {
			 * $("#works_subject").find("option[value='" + d.value +
			 * "']").attr("selected", true); } });
			 */
		}
		;
		if (curNode.grade != null && curNode.grade != undefined) {
			$("#works_grade").val(curNode.grade);
			/*
			 * $.each(grade, function(id, it) { if (it.value == curNode.grade) {
			 * $("#works_grade").find("option[value='" + it.value +
			 * "']").attr("selected", true); } });
			 */
		}
		;
	}
	;
	closeAddResourceView();
	$("#works_source_name").html("&nbsp;&nbsp;:&nbsp;&nbsp;<strong style=\"color:blue\">" + videoName + "</strong>");
};
// 查询直播课
var tempPageIndex;
var broadCastList;
var getBroadCast = function(pageIndex) {
	// $("#live_curriculum_pagination").html("");
	tempPageIndex = pageIndex;
	console.log("周次", $("#select_weeks>option:selected")[0].value);
	console.log("星期", $("#select_weekdate>option:selected")[0].value);
	var keywords = {
		isAll : "1",
		weeks : $("#select_weeks>option:selected")[0].value,
		weekdate : $("#select_weekdate>option:selected")[0].value,
		subject : $("input[name='subject']").val()
	};
	if (pageIndex == null || pageIndex == undefined || pageIndex == '') {
		pageIndex = 1;
	}
	;
	var page = {
		pageSize : 7,
		pageIndex : pageIndex,
		limit : 7,
		order : ""
	};
	/*
	 * if (pageIndex == null || pageIndex == '' || pageIndex == undefined) {
	 * page.pageIndex = 1; } ;
	 */
	var data = {
		keywords : keywords,
		page : page
	};
	console.log("查询直播课传值:", data);
	$.ajax({
		url : "../private/liveCurriculum.do",
		type : "POST",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify(data),
		success : function(d) {
			console.log("查询直播课返回数据:", d);
			var trs = "";
			if (d.total > 0) {
				broadCastList = d.data;
				$.each(d.data, function(id, it) {
					trs += "<tr><td><input type=\"radio\" value='" + it.id + "' name=\"selr\" /></td>";
					trs += "<td>" + it.weeks + "</td>";
					if (it.weekdate == '1') {
						trs += "<td>星期一</td>";
					} else if (it.weekdate == '2') {
						trs += "<td>星期二</td>";
					} else if (it.weekdate == '3') {
						trs += "<td>星期三</td>";
					} else if (it.weekdate == '4') {
						trs += "<td>星期四</td>";
					} else if (it.weekdate == '5') {
						trs += "<td>星期五</td>";
					} else if (it.weekdate == '6') {
						trs += "<td>星期六</td>";
					} else if (it.weekdate == '7') {
						trs += "<td>星期日</td>";
					} else {
						trs += "<td>未知</td>";
					}
					;
					if (it.sameclass != null && it.sameclass != undefined) {
						trs += "<td>" + it.sameclass + "</td>";
					} else {
						trs += "<td>未知</td>";
					}
					if (it.subject != null && it.subject != undefined) {
						trs += "<td>" + it.subject + "</td>";
					} else {
						trs += "<td>未知</td>";
					}
					if (it.subjectattribute != null && it.subjectattribute != undefined) {
						trs += "<td>" + it.subjectattribute + "</td>";
					} else {
						trs += "<td class=\"error\">暂无主题</td>";
					}
					trs += "</tr>";
				});
				/** *************************************分页条********************************************* */
				var pageTotal;
				if (d.total % page.pageSize == 0) {
					pageTotal = d.total / page.pageSize;
				} else {
					pageTotal = Math.floor(d.total / page.pageSize) + 1;
				}
				var pagination = "";
				if (pageTotal > 1) {
					// 分页条的起始下标
					var begin, end;
					if (pageTotal > 10) {
						if (pageIndex > 6) {
							begin = (pageTotal - pageIndex > 4) ? pageIndex - 5 : (pageTotal - 9);
							end = (pageTotal - pageIndex > 4) ? pageIndex + 4 : pageTotal;
						} else {
							begin = 1;
							end = 10;
						}
					} else {
						begin = 1, end = pageTotal;
					}
					pagination += "<div class=\"col-xs-12 text-right\">";
					pagination += "<nav><ul class=\"pagination\">";
					// 如果当前页大于1，显示上一页按钮
					if (pageIndex > 1) {
						pagination += "<li><a onclick=\"onLiveSelectPage(1)\" aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
						pagination += "<li><a onclick=\"onLiveSelectPage(" + (pageIndex - 1) + ")\"><i class='icon-chevron-left'></i></a></li>";
					} else {
						pagination += "<li class=\"disabled\"><a aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
						pagination += "<li class=\"disabled\"><a><i class='icon-chevron-left'></i></a></li>";
					}
					// 中间页
					for (var i = begin; i <= end; i++) {
						if (i == pageIndex) {
							pagination += "<li class=\"active\"><a class='pagination_index_mine' style='padding: 6px 2px;'>" + i + "<span class=\"sr-only\">(current)</span></a></li>";
						} else {
							pagination += "<li><a class='pagination_index_mine' style='padding: 6px 2px;' onclick=\"onLiveSelectPage(" + i + ")\">" + i
									+ "<span class=\"sr-only\">(current)</span></a></li>";
						}
					}
					// 如果当前页小于总页数，显示下一页按钮
					if (pageIndex < pageTotal) {
						pagination += "<li><a onclick=\"onLiveSelectPage(" + (pageIndex + 1) + ")\"><i class='icon-chevron-right'></i></a></li>";
						pagination += "<li onclick=\"onLiveSelectPage(" + pageTotal + ")\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
					} else {
						pagination += "<li class=\"disabled\"><a><i class='icon-chevron-right'></i></a></li>";
						pagination += "<li class=\"disabled\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
					}
					pagination += "</ul></nav></div>";
				}
				;
				$("#live_curriculum_pagination").html(pagination);
				/** *************************************************************************************** */
			} else {
				$("#live_curriculum_pagination").html("");
				trs += "<tr class=\"error\">对不起，暂无直播课，请选择录像文件</tr>"
				addMessage("success", "对不起，没有直播课，请选择录像文件");
			}
			;
			$("#table_live_curriculum").html(trs);
			// console.log($("#table_live_head"));
			// alert($("#table_live_curriculum").find("tr:last").scrollTop());
		},
		error : function(code) {
			addMessage("error", "不好意思，请求出错了，错误码" + code.status);
		}
	});
};
var onLiveSelectPage = function(pageIndex) {
	getBroadCast(pageIndex);
	return false;
};
/*
 * var config = { ip : "http://192.168.12.220" };
 */
var liveCurriculum;
var okSelectLive = function() {
	console.log($(":radio[name='selr']:checked"));
	liveCurriculum = $(":radio[name='selr']:checked").val();
	var tempCurriculum = getLiveById(liveCurriculum);
	if (tempCurriculum.subjectattribute != null && tempCurriculum.subjectattribute != undefined) {
		$("#works_name").val(tempCurriculum.subjectattribute);
	} else if (tempCurriculum.subject != null && tempCurriculum.subject != undefined) {
		$("#works_name").val(tempCurriculum.subject);
	} else {
		$("#works_name").val("");
	}
	// $("#works_subject").val(tempCurriculum.subject);
	// $("#works_grade").val(),
	if (tempCurriculum.course_desc != null && tempCurriculum.course_desc != undefined) {
		$("#works_description").val(tempCurriculum.course_desc);
	} else {
		$("#works_description").val("");
	}
	if (tempCurriculum.imageurl != null && tempCurriculum.imageurl != undefined) {
		$("#works_img").attr("src", tempCurriculum.imageurl);
	} else {
		$("#works_img").attr("src", "../static/img/front-cover.jpg");
	}
	$.each(course, function(i, d) {
		if (d.name == tempCurriculum.subject) {
			$("#works_subject").find("option[value='" + d.value + "']").attr("selected", true);
		}
	});
	closeAddResourceView();
	$("#works_source_name").html("&nbsp;&nbsp;:&nbsp;&nbsp;<strong style=\"color:blue\">" + tempCurriculum.subject + "</strong>");
	console.log("liveCurriculum", tempCurriculum);
};
/**
 * 根据id查询直播课程
 */
var getLiveById = function(liveid) {
	var selectLive;
	$.each(broadCastList, function(idx, item) {
		if (liveid == item.id) {
			selectLive = item;
		}
		;
	});
	return selectLive;
};
/**
 * 根据id查询直播课程
 */
var getFilmById = function(id) {
	$.each(videoTreeNodes, function(idx, item) {
		if (id == item.id) {
			return item;
		}
		;
		if (item.nodes != null) {
			getByParentId(id, item.nodes);
		}
		;
	});
};
var getByParentId = function(id, nodes) {
	$.each(nodes, function(idx, item) {
		if (id == item.id) {
			return item;
		}
		;
		if (item.nodes != null) {
			getByParentId(id, item.nodes);
		}
		;
	});
};
/**
 * 查询学科
 */
var course;
var getAllCourses = function() {
	course = getSysCode("Subject");
	console.log("course:", getSysCode("Subject"));
	var options = "";
	if (course != null) {
		$.each(course, function(i, d) {
			if (i == 0) {
				options += "<option value='" + d.value + "' checked='true'>" + d.name + "</option>";
			} else {
				options += "<option value='" + d.value + "'>" + d.name + "</option>";
			}
		});
	}
	$("#works_subject").html(options);
};
/**
 * 查询阶段
 */
var grade;
var getAllGrade = function() {
	grade = getSysCode("grade");
	var options = "";
	if (grade != null) {
		$.each(grade, function(i, d) {
			if (i == 0) {
				options += "<option value='" + d.value + "' checked='true'>" + d.name + "</option>";
			} else {
				options += "<option value='" + d.value + "'>" + d.name + "</option>";
			}
		});
	}
	$("#works_grade").html(options);
};
var getSysCode = function(value) {
	var code;
	$.ajax({
		url : "./code.do",
		type : "POST",
		async : false,
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify({
			value : value
		}),
		success : function(d) {
			console.log("查询数据字典：", d);
			if (d.length > 0) {
				code = d;
			} else {
				code = null;
				addMessage("success", "请在数据字典中添加类型");
			}
		},
		error : function(da) {
			addMessage("error", "不好意思，请求出错了，错误码" + da.status);
		}
	});
	return code;
};
/**
 * @function 查询视频树
 */
var selectedVideo;
var videoName;
var videoTreeNodes;
var curNode;
var makeVideoTree = function() {
	initCheckedVideo();
	$.ajax({
		url : "../private/videoTrees.do",
		type : "POST",
		async : false,
		dataType : "json",
		success : function(d) {
			console.log("查询视频树:", d);
			if (d.length > 0) {
				videoTreeNodes = d;
				var trees = d;
				function trans(nodes) {
					var count = 0;
					count = count + 1;
					var indent = 30 * count;
					var htm = '<ul>';
					$.each(nodes, function(i, data) {
						if (data.nodes.length > 0) {
							htm += '<li class="parent" style="padding-left:' + indent + 'px"><i class="icon-play-arrow"/>';
						} else {
							htm += '<li style="padding-left:' + indent + 'px"><i class="icon-play-arrow"/>';
						}
						if (data.isfolder == '0') {
							htm += '<i class="icon-Folder"></i>';
						} else {
							htm += '<i id="' + data.id + '" class="icon-Recorder"></i>';
						}
						htm += '<span>' + data.title + '</span>';
						htm += '<span class="hidden">' + JSON.stringify(data) + '</span>';
						if (data.nodes != null) {
							htm += trans(data.nodes);
						}
						htm += '</li>';
					});
					htm += '</ul>';
					return htm;
				}
				var html = '';
				$.each(trees, function(i, data) {
					if (data.nodes.length > 0) {
						html += '<li class="parent"><i class="icon-play-arrow"/>';
					} else {
						html += '<li><i class="icon-play-arrow"/>';
					}
					if (data.isfolder == '0') {
						html += '<i class="icon-Folder"></i>';
					} else {
						html += '<i id="' + data.id + '" class="icon-Recorder"></i>';
					}
					html += '<span>' + data.title + '</span>';
					html += '<span class="hidden" id="video">' + JSON.stringify(data) + '</span>';
					if (data.nodes != null) {
						html += trans(data.nodes);
					}
					html += '</li>';
				});
				$("#treeTag").html(html);
				// $("#block").show();
				$("li").click(function(event) {
					if ($(this).children("i[class='icon-Recorder']").size() > 0) {
						if ($(this).children("i[class='icon-done']").size() > 0) {
							$(this).children("i[class='icon-done']").remove();
							selectedVideo = "";
							videoName = "";
							curNode = {};
						} else {
							$(this).parent().children("li").find("i[class='icon-done']").remove();
							$(this).children("i[class='icon-play-arrow']").after('</i><i class="icon-done">');
							selectedVideo = $(this).children(".icon-Recorder").attr("id");
							videoName = $(this).find("span:eq(0)").text();
							curNode = $(this).find("span:eq(1)").text();
							console.log("选中的视频:", selectedVideo, ";name:", curNode);
						}
						event.stopPropagation();
						return;
					}
					var child = $(this).children("ul");
					if (child.is(":hidden"))
						child.show();
					else {
						child.hide();
						$(this).find("i[class='icon-done']").remove();
					}
					event.stopPropagation();
				});
			} else {
				addMessage("success", "对不起，您暂时还没有视频资源，请先上传");
			}
		},
		error : function(da) {
			addMessage("error", "不好意思，请求出错了，错误码" + da.status);
		}
	});
};
/**
 * 
 */
var initCheckedVideo = function() {
	videoName = "";
	selectedVideo = "";
	console.log("初始化树", selectedVideo);
	if ($("#treeTag").children().length > 0) {
		$("li").find("i .icon-done").remove();
	}
};

/**
 * 提交作品
 */
var submitWorks = function() {
	if (currentUser.usertype == '1') {
		var resourceid;
		var type = $(":radio[name='broadcastOrVideotape']:checked").val();
		// 直播
		if (type == '1') {
			resourceid = $(":radio[name='selr']:checked").val();
		} else {
			resourceid = selectedVideo;
		}
		var data = {
			activeid : activeid,
			name : $("#works_name").val(),
			subject : $("#works_subject").val(),
			grade : $("#works_grade").val(),
			description : $("#works_description").val(),
			type : $(":radio[name='broadcastOrVideotape']:checked").val(),
			resourceid : resourceid,
			imgSrc : $("#works_img").attr("src")
		};
		console.log("活动报名",data);
		var file = $("#works_file")[0].files;
		// console.log("作品图片信息：",file);
		var isImage;
		var hasImage = false;
		if (file.length > 0) {
			hasImage = true;
			isImage = validateImage(file);
		}
		;
		if (validateInsertWorks(data) && hasImage && isImage) {
			// 有图片上传
			$.ajaxFileUpload({
				url : '../private/signUpHasPicture.do',// 处理图片
				secureuri : false,
				fileElementId : 'works_file',// file控件id
				dataType : 'text', // 返回值类型 一般设置为json
				contentType : 'application/json; charset=UTF-8',
				data : data,
				success : function(rd, status) {
					console.log(rd);
					// var rda = eval(d);
					if (rd.id === '1') {
						// showView(0);
						setTimeout(function() {
							showView(0);
						}, 2000);
						addMessage("success", rd.content);
					} else {
						addMessage("error", rd.content);
					}
					;
				},
				error : function(da, status, e) {
					console.log(da);
					var rd = da.responseText;
					var start = rd.indexOf(">");
					if (start != -1) {
						var end = rd.indexOf("<", start + 1);
						if (end != -1) {
							rd = rd.substring(start + 1, end);
						}
					}
					rd = eval("(" + rd + ")");
					// console.log(rd);
					if (rd.id === '1') {
						// showView(0);
						setTimeout(function() {
							showView(0);
						}, 2000);
						addMessage("success", rd.content);
					} else {
						addMessage("error", rd.content);
					}
					;
				}
			});
		} else if (validateInsertWorks(data) && hasImage == false) {
			// 没有图片上传
			$.ajax({
				url : "../private/signUpHasNoPicture.do",
				type : "POST",
				cache : false,
				dataType : "json",
				contentType : "application/json;charset=UTF-8",
				data : JSON.stringify(data),
				success : function(da) {
					if (da.id == '1') {
						// showView(0);
						setTimeout(function() {
							showView(0);
						}, 2000);
						addMessage("success", da.content);
					} else {
						addMessage("error", da.content);
					}
				},
				error : function(xhr, status) {
					console.log(arguments);
					addMessage("error", "不好意思，请求出错了，错误码" + xhr.status);
				}
			});
		} else {
			console.log("未通过表单验证");
		}
		;
		console.log("活动报名提交的数据", data);
	} else {
		addMessage("error", "对不起，您没有报名权限。");
	}
	/*
	 * var ev = window.event; if (ev.preventDefault) { ev.preventDefault(); }
	 * else { ev.returnValue = false; }
	 */
	return false;// 禁用回车事件
};

/**
 * 图片验证
 */
var validateImage = function(file) {
	if (file.length > 1) {
		addMessage("error", "对不起，只能上传一张图片！");
		return false;
	} else if (file.size > 5242880) {
		addMessage("error", "图片大小不能超过5Mb！");
		return false;
	} else {
		return true;
	}
};
/**
 * 表单验证
 */
var validateInsertWorks = function(data) {
	if (data.name == null || data.name == "" || data.name == undefined) {
		addMessage("error", "作品名称不能为空！");
		return false;
	} else if (data.resourceid == null || data.resourceid == "" || data.resourceid == undefined) {
		addMessage("error", "对不起，您没有选择资源！");
		return false;
	} else if (data.type == null || data.type == "" || data.type == undefined) {
		addMessage("error", "请选择资源类型！");
		return false;
	} else if (data.description == null || data.description == "" || data.description == undefined) {
		addMessage("error", "作品描述信息不能为空！");
		return false;
	} else if (data.description.length > 200) {
		addMessage("error", "作品描述信息不能超过200个字！");
		return false;
	} else {
		return true;
	}
};
var showSelectFileView = function(index, type) {
	$("#select_record_film").css("margin-left", "-" + ($("#select_record_film").outerWidth() / 2) + "px");
	if (index == 1) {
		if (type == 1) {
			makeVideoTree();
		}
		$("#film_btn").addClass("cur");
		$("#upload_btn").removeClass("cur");
		$("#select_film").show();
		$("#select_upload").hide();
	} else if (index == 2) {
		$("#table_new_works_upload").html("");
		uploadResouceId = "";
		$("#film_btn").removeClass("cur");
		$("#upload_btn").addClass("cur");
		$("#select_film").hide();
		$("#select_upload").show();
	} else {
		makeVideoTree();
		$("#film_btn").addClass("cur");
		$("#upload_btn").removeClass("cur");
		$("#select_film").show();
		$("#select_upload").hide();
	}
};
/**
 * 选择视频
 */
var parseMb = function(size) {
	// 转为字节
	return size > 1048576 ? (size / 1024 / 1024).toFixed(2) + 'MB' : (size / 1024 / 100.00).toFixed(2) + 'KB';
};
var currentFile;
var videoSufix = [ "avi", "rmvb", "mp4", "mkv", "wmv", "rm", "asf", "divx", "mpg", "mpeg", "mpe", "vob", "3gp", "f4v", "mov", "flv" ];
var onSelectFilm = function() {
	console.log("选择的视频：", $("#choose")[0].files[0]);
	if ($("#choose")[0].files.length > 1) {
		addMessage("error", "一次只能上传一个视频");
		return;
	}
	;
	var file = $("#choose")[0].files[0];
	var trs = "";
	if ($("#choose").val() != null && $("#choose").val() != '' && $("#choose").val() != undefined) {
		var nameArray = $("#choose").val().split("."); // 定义一数组
		var suffix = nameArray[nameArray.length - 1];
		if ($.inArray(suffix, videoSufix) == -1) {
			// document.getElementById('active_img').src =
			// '../static/img/front-cover.jpg';
			$("#choose").val('');
			addMessage("error", "请选择视频文件！");
			return;
		} else {
			var file = $("#choose")[0].files[0];
			currentFile = file;
			// 自动开始上传
			// uploadVideo();
		}
	} else {
		// $("#table_new_works_upload").html(trs);
		addMessage("success", "您没有选择视频文件！");
	}
	return false;
};
var cancelUpload = function() {
	$("#table_new_works_upload").html("");
	uploadResouceId = "";
	return false;
};
okSelectUpload = function() {
	if (uploadResouceId != undefined && uploadResouceId != null && uploadResouceId != "") {
		selectedVideo = uploadResouceId;
		videoName = $("#file_upload_name").html();
		console.log("selectedVideo:", selectedVideo, "videoName:", videoName);
	} else {
		addMessage("error", "对不起，您没有选中视频资源");
	}
	;
	closeAddResourceView();
	$("#works_source_name").html("&nbsp;&nbsp;:&nbsp;&nbsp;<strong style=\"color:blue\">" + videoName + "</strong>");
	$("#works_name").val(videoName);
};
/**
 * @function 上传视频
 */
var uploadResouceId;
var uploadVideo = $("#choose").fileupload({
	url : '../private/upload.do',
	type : "POST",
	dataType : "json",
	async : true,
	sequentialUploads : true,
	singleFileUploads : true,
	autoUpload : true,
	formData : {
		parentid : '1'
	},
	add : function(e, d) {
		var trs = "";
		console.log("file:", d);
		trs += '<tr><td class="col-xs-4"><strong id="file_upload_name" title="' + currentFile.name + '">' + currentFile.name + '</strong></td>';
		trs += '<td class="col-xs-2">' + parseMb(currentFile.size) + '</td>';
		trs += '<td class="col-xs-3">';
		trs += '<div class="progress" style="border:none;margin-bottom:0;">';
		trs += '<div id="bar" class="progress-bar" style="width:0%; background: rgb(66, 139, 202);color:#fff;text-align: center">0%</div>';
		trs += '</div></td>';
		trs += '<td id="upload_rate" class="col-xs-2">0KB/s</td>'
		trs += '<td class="col-xs-1"><button id="btn_upload_cancel" type="button" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash"></span></button></td>';
		trs += '</tr>';
		$("#table_new_works_upload").html(trs);
		d.submit();
		$("#btn_upload_cancel").bind("click", function() {
			d.abort();
			$("#table_new_works_upload").html("");
		});
	},
	success : function(data) {
		console.log("上传资源返回主键", data);
		if (data.length > 0) {
			uploadResouceId = data[0];
		}
	}
}).bind('fileuploadprogressall', function(e, data) {
	var loaded = parseMb(data.loaded);
	var progress = parseInt(100.0 * data.loaded / data.total);
	var uploadRate = parseBitrate(data.bitrate);
	$("#bar").css("width", progress + '%').html(progress + "%");
	$("#upload_rate").html(uploadRate);
	// console.log("progressAll",data);
	/* ... */
}).bind('fileuploaddone', function(e, data) {
	$("#upload_rate").html("0KB/s");
	addMessage("success", "资源上传成功！");
	/* ... */
}).bind('fileuploadfail', function(e, data) {
	addMessage("error", "资源上传失败！");
	// console.log(data);
	/* ... */
});
var parseBitrate = function(bitrate) {
	if (bitrate < 1073741824) {
		return bitrate > 1048576 ? (bitrate / 1024 / 1024 / 8).toFixed(2) + 'MB/s' : (bitrate / 1024 / 8 / 100.00).toFixed(2) + 'KB/s';
	} else {
		return (bitrate / 1024 / 1024 / 1024 / 8).toFixed(2) + 'GB/s';
	}
}
/**
 * 分配专家权限判断 '0'默认为手动分配专家，'1'为自动分配专家
 */
var adminFlag = "0";
var isAdmin = function() {
	$.ajax({
		url : './isAdmin.do',
		type : 'POST',
		async : false,
		dataType : 'json',
		success : function(data) {
			console.log("isAdmin:", data);
			adminFlag = data;
		},
		error : function(xhr) {
			addMessage("error", "不好意思，请求出错了，错误码" + xhr.status);
		}
	});
};
/**
 * 是否显示分配专家按钮
 */
var isShowAssign = function() {
	if (currentActive.model === '1') {
		$("#expert-distribution").hide();
	} else if (currentActive.model === '0') {
		$("#expert-distribution").show();
		// $("i .icon-person-add").show();
	} else {
		$("#expert-distribution").hide();
		// $("i .icon-person-add").hide();
	}
};
/**
 * 编辑活动
 */
var openEditActive = function() {
	if (currentUser == null || currentUser == undefined) {
		window.location = "../login";
		return;
	}
	if (currentUser.roles.indexOf("系统管理员") != -1) {
		window.location = "../private/editActive?activeid=" + activeid;
		/*
		 * if (currentActive.status === "未开始" || currentActive.status ===
		 * "报名中,评审未开始" || currentActive.status === "报名已结束,评审未开始") {
		 * window.location = "../private/editActive?activeid=" + activeid; }
		 * else if (currentActive.status === "报名中,评审中" || currentActive.status
		 * === "报名已结束,评审中") { addMessage("error", "对不起,活动已经开始评审,不能编辑！"); } else
		 * if (currentActive.status === "已结束") { addMessage("error",
		 * "对不起,活动已经结束,不能编辑！"); } else { addMessage("error", "活动状态未知,不能修改!"); }
		 */
	} else {
		addMessage("error", "对不起，您没有管理员权限！");
	}
}

/** ************************************************************************************************* */
$(function() {
	queryActiveDetail();
	isShowAssign();
	isAdmin();
	getWorksList();
	selectWorkSource();
	getAllCourses();
	getAllGrade();
	if (currentActive.status === "报名中,评审未开始" || currentActive.status === "报名中,评审中") {
		if (currentUser != null && currentUser != undefined) {
			makeVideoTree();
			if (currentUser.usertype == '1') {
				showView(3);
			} else {
				showView(0);
			}
		} else {
			showView(0);
		}
	} else {
		if (currentUser != null && currentUser != undefined) {
			makeVideoTree();
		}
		showView(0);
	}
	;
	if (currentUser != null && currentUser != undefined) {
		if (currentUser.roles.indexOf("系统管理员") != -1) {
			$("#btn_open_edit_active,#goWritingManage").show();
		} else {
			$("#btn_open_edit_active,#goWritingManage").hide();
		}
		;
	} else {
		$("#btn_open_edit_active,#goWritingManage").hide();
	}
	;
	$("input[name='subject'],input[id='works_name']").keypress(function(e) {
		var ev = e || window.event;
		if (ev.keyCode == 13) {
			getBroadCast();
			if (ev.preventDefault) {
				ev.preventDefault();
			} else {
				ev.returnValue = false;
			}
			return false;// 禁用回车事件
		}
	});
});
/** ************************************************************************************************* */
