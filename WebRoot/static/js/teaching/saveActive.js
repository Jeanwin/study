/**
 * 判断是否编辑模式
 */
var editMode = false;
var isEditMode = function() {
	if ($.getUrlParam("activeid") != undefined && $.getUrlParam("activeid") != null && $.getUrlParam("activeid") != '') {
		editMode = true;
		$("#btn_active_submit").val("修改活动");
	}
	console.log("是否编辑模式：", editMode);
};
var openDeptTree = function() {
	initDeptTree();
	if ($("#saveactive_dept_tree").hasClass("hidden")) {
		// alert(1);

		$("#saveactive_dept_tree").slideDown('fast');
		$("#saveactive_dept_tree").removeClass("hidden").css('overflow', 'auto');
	} else {
		$("#saveactive_dept_tree").addClass("hidden");
		$("#saveactive_dept_tree").slideUp('fast');
		$("#input_dept").val(checedDept.title);
		getUsersByDept();
	}
	console.log("checedDept", checedDept);
};
/**
 * 生成机构树
 */
var initDeptTree = function() {
	// alert(1);
	checedDept = {
		id : '',
		title : ''
	};
	$("li").has("span[style='display:inline-block;']").removeClass('backchanage');
	// console.log($("li"));
};
var deptTreeNodes;
var checedDept = {
	id : '',
	title : ''
};
var makeDeptTree = function() {
	$
			.ajax({
				url : '../dept/getDeptTree',
				type : 'GET',
				async : false,
				datatype : 'json',
				cache : true,
				success : function(data) {
					console.log('查询机构树返回数据：', data);
					if (data != null && data != undefined) {
						deptTreeNodes = data;
						var trees = data;
						var html = '';
						var count = 0;
						function trans(nodes) {
							count = count + 1;
							// var indent = 30 * count;
							// console.log(count);
							if (count > 1) {
								var htm = '<ul style="margin-left:0px;padding-left:20px;display:none">';
							} else {
								var htm = '<ul style="margin-left:0px;padding-left:20px">';
							}
							$
									.each(
											nodes,
											function(i, d) {
												if (d.nodes.length > 0) {
													htm += '<li style="padding-left:0px"><i style="color: #2196F3;margin-right:10px" class="glyphicon glyphicon-folder-close"/><span id="dept_name" style="display:inline-block;">';
												} else {
													htm += '<li style="padding-left:0px"><i style="color: #8E6211;margin-right:10px" class="icon-tablet"/><span id="dept_name" style="display:inline-block;">';
												}
												htm += d.title + '</span>';
												htm += '<span class="hidden">' + JSON.stringify({
													id : d.id,
													title : d.title
												}) + '</span>';
												htm += '</li>';
												if (d.nodes != null) {
													htm += trans(d.nodes);
												}
											});
							htm += '</ul>';
							return htm;
						}
						if (trees.nodes.length > 0) {
							html += '<ul><li><i style="color: #2196F3;margin-right:10px" class="glyphicon glyphicon-folder-close"></i><span id="dept_name" style="display:inline-block;">'
									+ trees.title + '</span> <span class="hidden" id="dept">' + JSON.stringify({
										id : trees.id,
										title : trees.title
									}) + '</span></li>';
							html += trans(data.nodes);
						} else {
							html += '<ul><li><i style="color: #2196F3;margin-right:10px" class="glyphicon glyphicon-folder-close"></i><span id="dept_name" style="display:inline-block;">'
									+ trees.title + '</span> <span class="hidden" id="dept">' + JSON.stringify({
										id : trees.id,
										title : trees.title
									}) + '</span></li>';
						}
						;
						$("#saveactive_dept_tree").html(html);
						// $("#block").show();
						$("li").find("i").click(function(event) {
							initDeptTree();
							var child = $(this).parent("li").next("ul:eq(0)");
							if (child.is(":hidden")) {
								child.slideDown('fast');
								$(this).attr("class", "glyphicon glyphicon-folder-open");
							} else {
								child.slideUp('fast');
								$(this).attr("class", "glyphicon glyphicon-folder-close");
							}
							event.stopPropagation();
						});
						// 部门名称点击事件
						$("li").find("span[id='dept_name']").click(function() {
							// initDeptTree();
							/*
							 * console.log($(this).parent("li:eq(0)").hasClass('backchanage'));
							 * if($(this).parent("li").hasClass('backchanage')){
							 * checedDept={id:'',title:''};
							 * $(this).parent("li").removeClass('backchanage');
							 * }else{
							 */
							checedDept = $.parseJSON($(this).next("span").text());
							$("#saveactive_dept_tree").addClass("hidden");
							$("#saveactive_dept_tree").slideUp('fast');
							$("#input_dept").val(checedDept.title);
							getUsersByDept();
							/*
							 * $(this).parent("li").addClass('backchanage'); }
							 */
							console.log("checedDept", checedDept);
						});
					} else {
						addMessage("success", "对不起，没有搜索到部门信息");
					}
				},
				error : function() {
					addMessage('error', '查询机构异常，请刷新页面！');
				}
			});
};
var currentActive;
var fillForm = function() {
	if (editMode) {
		$.ajax({
			url : './getActiveEdit',
			type : 'POST',
			async : false,
			dataType : "json",
			data : $.param({
				activeid : $.getUrlParam("activeid")
			}),
			cache : false,
			success : function(data) {
				console.log("查询当前编辑的活动：", data);
				if (data != null) {
					currentActive = data;
					$("#active_name").val(data.active.name);
					$("#active_type").find("option[value='" + data.active.type + "']").attr("selected", true);
					if (data.active.picture != null && data.active.picture != undefined && data.active.picture != '') {
						$("#active_img").attr("src", data.active.picture);
					} else {
						$("#active_img").attr("src", "../static/img/front-cover.jpg");
					}
					;
					$("#active_sign_begin_date").val(data.active.regbegintime);
					$("#active_sign_end_date").val(data.active.regendtime);
					$("#active_review_begin_date").val(data.active.conbegintime);
					$("#active_review_end_date").val(data.active.conendtime);
					$("#active_description").val(data.active.description);
					// $("#review_table").val(data.active.contemplate);
					$("#review_table").find("option[value='" + data.active.contemplate + "']").attr("selected", true);
					$.each($("input[name='model']"), function(i, d) {
						console.log("d:", d);
						if (d.value == data.active.model) {
							d.checked = true;
						}
					});
					var special = "";
					$.each(data.reviewUsers, function(i, d) {
						special += "<a href='javascript:;' onclick='removeSpecialist(this)'>" + d.username + "</a><span style='display:none;'>" + d.userid + "</span>";
					});
					$(".expertPerson").html(special);
					$("#specialist_total").html("(共" + $(".expertPerson").find("a").length + "位)");
					// 添加hover事件
					$(".expertPerson a").hover(function() {
						if ($(this).find("i").length == 0) {
							$(this).append("<i class='icon-close btn_del_expert'></i>");
						}
					}, function() {
						$(this).find("i").remove();
					});
				} else {
					addMessage("error", "未查询到当前活动信息");
				}
			},
			error : function(xhr) {
				addMessage("error", "查询活动详情失败,请重新刷新当前页面 ");
			}
		});
	}
};

/**
 * 用按钮选择文件
 */
var onFileSelect = function() {
	var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;

	if (ie) {
		document.getElementById("active_file").click();
		document.getElementById("fileBtn").value = document.getElementById("active_file").value;
		// imgPreview();
	} else {
		var a = document.createEvent("MouseEvents");
		a.initEvent("click", true, true);
		document.getElementById("active_file").dispatchEvent(a);
		// imgPreview();
	}
	;
};
// 图片预览
var imgPreview = function() {
	console.log($("#active_file").val());
	var nameArray = $("#active_file").val().split("."); // 定义一数组
	var suffix = nameArray[nameArray.length - 1];
	if (suffix != 'jpg' && suffix != 'gif' && suffix != 'jpeg' && suffix != 'png') {
		addMessage("error", "你选择的不是图片,请选择图片！");
		document.getElementById('active_img').src = '../static/img/front-cover.jpg';
		$("#active_file").val('');
		// $("#fileBtn").css("border","1px solid red");
		// console.log("val",$("#active_file").val());
		return;
	} else {
		var f = document.getElementById('active_file').files[0];
		var src = window.URL.createObjectURL(f);
		document.getElementById('active_img').src = src;
	}
	;
};
// 查询所有部门
var getDepts = function() {
	var result;
	$("#select_dept").empty();
	$.ajax({
		type : "POST",
		// data:
		async : true,
		url : "./getDepts.do",
		dataType : "json",
		cache : false,
		success : function(data) {
			result = data;
			// 生成部门
			var options = "<option selected value=''>全部</option>";
			$.each(data, function(idx, item) {
				options += "<option value='" + item.value + "'>" + item.name + "</option>"
			});
			$("#select_dept").append(options);
		},
		error : function(xhr) {
			addMessage("error", "不好意思，请求出错了，错误码" + xhr.status);
		}
	});
	return result;
};
// 删除专家验证
var delReviewerCheck = function(userId) {
	console.log("删除专家验证userId:", userId);
	var checkFlag = false;
	$.ajax({
		type : "POST",
		data : JSON.stringify({
			userId : userId,
			activeId : $.getUrlParam("activeid")
		}),
		async : false,
		url : "./delReviewerCheck.do",
		dataType : "json",
		contentType : 'application/json; charset=UTF-8',
		cache : false,
		success : function(data) {
			console.log("delReviewerCheck:", data);
			if (data.id == '1') {
				checkFlag = true;
			}
		},
		error : function(xhr) {
			addMessage("error", "不好意思，请求出错了，错误码" + xhr.status);
		}
	});
	console.log("checkFlag:", checkFlag);
	return checkFlag;
};
// 根据部门和姓名查询用户
var getUsersByDept = function(pageIndex) {
	var keywords = {
		// activeId : activeid
		// deptid : $("#select_dept").val(),
		deptid : checedDept.id,
		name : $("input[name='username']").val()
	};
	if (pageIndex == null || pageIndex == undefined || pageIndex == '') {
		pageIndex = 1;
	}
	;
	var page = {
		pageSize : 7,
		pageIndex : pageIndex,
		limit : 7
	};
	/*
	 * if (pageIndex == null || pageIndex == '' || pageIndex == undefined) {
	 * page.pageIndex = 1; } ;
	 */
	var data = {
		keywords : keywords,
		page : page
	};
	console.log("查询用户传值：", data);
	var users;
	// $("#table_users").empty();
	// $("user_amount").empty();
	$.ajax({
		type : "POST",
		data : JSON.stringify(data),
		async : true,
		url : "./getByDept.do",
		dataType : "json",
		cache : false,
		contentType : 'application/json; charset=UTF-8',
		success : function(data) {
			console.log("users:", data);
			users = data;

			// 生成用户列表
			var users = "";
			var pagination = "";
			// var result = getUsersByDept(null, null, page, keywords);
			if (data.total > 0) {
				$.each(data.data, function(index, user) {
					users += "<tr>";
					users += "<td>" + user.name + "</td>";
					users += "<td>" + user.loginname + "</td>";
					if (user.sex === '0') {
						users += "<td>男</td>";
					} else if (user.sex === '1') {
						users += "<td>女 </td>";
					} else {
						users += "<td>未填 </td>";
					}
					;
					users += "<td>" + user.deptName + "</td>";
					users += "<td onclick='addelSpecialist(this)'><i class='icon-my-library-add'></i></td>";
					users += "</tr>";
				});
				/** *****************************************分页条******************************************** */
				var pageTotal;
				if (data.total % page.pageSize == 0) {
					pageTotal = data.total / page.pageSize;
				} else {
					pageTotal = Math.floor(data.total / page.pageSize) + 1;
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
					pagination += "<div class=\"col-xs-12 text-right margin-top-20\">";
					pagination += "<nav><ul class=\"pagination\">";
					// 如果当前页大于1，显示上一页按钮
					if (pageIndex > 1) {
						pagination += "<li><a onclick=\"onActiveSelectPage(1)\" aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
						pagination += "<li><a onclick=\"onActiveSelectPage(" + (pageIndex - 1) + ")\"><i class='icon-chevron-left'></i></a></li>";
					} else {
						pagination += "<li class=\"disabled\"><a aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
						pagination += "<li class=\"disabled\"><a ><i class='icon-chevron-left'></i></a></li>";
					}
					// 中间页
					for (var i = begin; i <= end; i++) {
						if (i == pageIndex) {
							pagination += "<li class=\"active\"><a class='pagination_index_mine' style='padding: 6px 2px;'>" + i + "<span class=\"sr-only\">(current)</span></a></li>";
						} else {
							pagination += "<li><a class='pagination_index_mine' style='padding: 6px 2px;' onclick=\"onActiveSelectPage(" + i + ")\">" + i
									+ "<span class=\"sr-only\">(current)</span></a></li>";
						}
					}
					// 如果当前页小于总页数，显示下一页按钮
					if (pageIndex < pageTotal) {
						pagination += "<li><a onclick=\"onActiveSelectPage(" + (pageIndex + 1) + ")\"><i class='icon-chevron-right'></i></a></li>";
						pagination += "<li onclick=\"onActiveSelectPage(" + pageTotal + ")\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
					} else {
						pagination += "<li class=\"disabled\"><a><i class='icon-chevron-right'></i></a></li>";
						pagination += "<li class=\"disabled\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
					}
					pagination += "</ul></nav></div>";
				}
				;
				/** ********************************************************************************** */
			}
			;
			$("#table_users").html(users);
			$("#review_user_pagination").html(pagination);
			// $("#users_amount").html("专家成员：共"+data.total+"人");
		},
		error : function(xhr) {
			addMessage("error", "不好意思，请求出错了，错误码" + xhr.status);
		}
	});
	return users;
};

var onActiveSelectPage = function(pageIndex) {
	getUsersByDept(pageIndex);
};
var searchSpecialist = function(activeid) {
	var specialist;
	var data = {
		activeId : activeid
	};
	console.log("查询本次活动的专家", data);
	$.ajax({
		type : "POST",
		data : JSON.stringify(data),
		async : false,
		cache : false,
		url : "./reviewUsers.do",
		dataType : "json",
		contentType : 'application/json; charset=UTF-8',
		success : function(data) {
			console.log("specialist:", data);
		},
		error : function(xhr) {
			addMessage("error", "不好意思，请求出错了，错误码" + xhr.status);
		}
	});
};
var addSpecialist = function(activeid, users) {

};
/**
 * 添加，删除专家，没有保存到数据库
 */

var specialist = [];
var specialUsers = {
	specialist : specialist
}
var addelSpecialist = function(object) {
	if ($(object).find("i").hasClass("icon-my-library-add")) {
		var idx = isLoginnameRepeat($(object).siblings(":eq(1)").html(), specialist);
		console.log(idx);
		if (idx === -1) {
			specialist.push({
				loginname : $(object).siblings(":eq(1)").html(),
				name : $(object).siblings(":eq(0)").html()
			});
			$(object).find("i").removeClass("icon-my-library-add");
			$(object).find("i").addClass("icon-my-library-del");
		}
		;
	} else {
		// jQuery.inArray({loginname:$(object).siblings(":eq(1)").html(),name:$(object).siblings(":eq(0)").html()},
		// specialist);
		var index = isLoginnameRepeat($(object).siblings(":eq(1)").html(), specialist);
		console.log(index);
		if (index !== -1) {
			specialist.splice(index, 1);
			$(object).find("i").removeClass("icon-my-library-del");
			$(object).find("i").addClass("icon-my-library-add");
		}
		;
	}
	;
	console.log(specialist);
	return specialist;
};

/**
 * 判断登录名是否重复,并返回下标
 */
var isLoginnameRepeat = function(loginname, array) {
	if (array.length === 0) {
		return -1;
	} else {
		var index = -1;
		for (var i = 0; i < array.length; i++) {
			if (loginname == array[i].loginname) {
				index = i;
			}
			;
		}
		;
		return index;
	}
	;
};
/**
 * 生成专家列表
 */
var saveAddSpecialist = function() {
	console.log(specialist);
	$("#specialist_total").empty();
	var specialDom = "";
	$.each(specialist, function(idx, item) {
		var tempS = [];
		$(".expertPerson").find("a").each(function() {
			// alert($(this).html());
			tempS.push({
				loginname : $(this).next().html(),
				name : $(this).html()
			});
		});
		console.log("temps", tempS);
		var exist = isLoginnameRepeat(item.loginname, tempS);
		if (exist == -1) {
			specialDom += "<a href='javascript:;' onclick='removeSpecialist(this)'>" + item.name + "</a><span style='display:none;'>" + item.loginname + "</span>";
		}
		;
	});
	$(".expertPerson").append(specialDom);
	$("#specialist_total").html("(共" + $(".expertPerson").find("a").length + "位)");
	// hover
	$(".expertPerson a").hover(function() {
		if ($(this).find("i").length == 0) {
			$(this).append("<i class='icon-close btn_del_expert'></i>");
		}
	}, function() {
		$(this).find("i").remove();
	});
};
/**
 * 移除专家
 */
var removeSpecialist = function(object) {
	if (!editMode) {
		$("#specialist_total").empty();
		var index = isLoginnameRepeat($(object).html(), specialist);
		if (index != -1) {
			specialist.splice(index, 1);
		}
		;
		$(object).remove();
		$(object).next().remove();
		$("#specialist_total").html("(共" + $(".expertPerson").find("a").length + "位)");
	} else {
		var userId = $(object).next("span").text();
		if (delReviewerCheck(userId)) {
			$("#specialist_total").empty();
			var index = isLoginnameRepeat($(object).html(), specialist);
			if (index != -1) {
				specialist.splice(index, 1);
			}
			;
			$(object).remove();
			$(object).next().remove();
			$("#specialist_total").html("(共" + $(".expertPerson").find("a").length + "位)");
		} else {
			addMessage("error", "专家已评审，不能删除");
		}
	}
};
/**
 * 提交活动
 */
var submitActive = function() {
	var fileName = $("#active_file").val();
	var file = document.getElementById("active_file").files;
	// 评审专家
	var reviewUsers = [];
	$(".expertPerson").find("a").each(function() {
		reviewUsers.push($(this).next().html());
	});
	var data = {
		name : $("#active_name").val() ? $("#active_name").val() : '',
		type : $("#active_type").val(),
		// file : document.getElementById("active_file").files,
		regbegintime : $("#active_sign_begin_date").val(),
		regendtime : $("#active_sign_end_date").val() + "T23:59",
		conbegintime : $("#active_review_begin_date").val(),
		conendtime : $("#active_review_end_date").val() + "T23:59",
		description : $("#active_description").val(),
		contemplate : $("#review_table").val(),
		model : $("input[name='model']:checked").val(),
		reviewUsers : reviewUsers
	};
	if (editMode) {
		data.id = $.getUrlParam("activeid");
	}
	;
	console.log("data", data, "editMode", editMode);
	if (fileName != null && fileName != '') {
		// alert("ok");
		// 上传文件
		// console.log("$.fileup", $.ajaxFileUpload);
		var _url = './newActiveHasPicture.do';
		if (editMode) {
			_url = './updateActiveHasPicture';
		}
		;
		if (validateNewActive(data) && validateImage(file)) {
			$.ajaxFileUpload({
				url : _url,// 处理图片
				secureuri : false,
				fileElementId : 'active_file',// file控件id
				dataType : 'text', // 返回值类型 一般设置为json
				//contentType : 'text/plain; charset=utf-8',
				data : data,
				success : function(rd, status) {
					console.log(rd);
					if (rd.id === '1') {
						addMessage("success", rd.content);
						setTimeout(function() {
							if (editMode) {
								window.location = "../gateway/activeDetail?activeid=" + $.getUrlParam("activeid");
							} else {
								window.location = "../gateway/getAllActives";
							}
						}, 2000);
					} else {
						addMessage("error", rd.content);
					}
					;
				},
				error : function(da, status, e) {
					var rd = da.responseText;
					var start = rd.indexOf(">");
					if (start != -1) {
						var end = rd.indexOf("<", start + 1);
						if (end != -1) {
							rd = rd.substring(start + 1, end);
						}
					}
					rd = eval("(" + rd + ")");
					if (rd.id === '1') {
						addMessage("success", rd.content);
						setTimeout(function() {
							if (editMode) {
								window.location = "../gateway/activeDetail?activeid=" + $.getUrlParam("activeid");
							} else {
								window.location = "../gateway/getAllActives";
							}
						}, 2000);
					} else {
						addMessage("error", rd.content);
					}
					;
				}
			});
		}
	} else {
		if (validateNewActive(data)) {
			var _aurl = "./newActiveHasNoPicture.do";
			if (editMode) {
				_aurl = './updateActiveHasNoPicture';
			}
			;
			$.ajax({
				type : "POST",
				data : JSON.stringify(data),
				async : false,
				cache : false,
				url : _aurl,
				dataType : "json",
				// contentType: false,
				contentType : 'application/json; charset=UTF-8',
				success : function(d) {
					if (d.id === '1') {
						addMessage("success", d.content);
						setTimeout(function() {
							if (editMode) {
								window.location = "../gateway/activeDetail?activeid=" + $.getUrlParam("activeid");
							} else {
								window.location = "../gateway/getAllActives";
							}
						}, 2000);
					} else {
						addMessage("error", d.content);
					}
					;
				},
				error : function(xhr) {
					addMessage("error", "不好意思，请求出错了，错误码" + xhr.status);
				}
			});
		}
	}
	;
	document.getElementById('active_img').src = '../static/img/front-cover.jpg';
	var ev = window.event;
	if (ev.preventDefault) {
		ev.preventDefault();
	} else {
		ev.returnValue = false;
	}
	return false;// 禁用回车事件
};
var jumpUrl = function(url) {
	window.location = url;
};
/**
 * 
 */
var validateNewActive = function(data) {
	if (data.name == null || data.name == undefined || data.name == "") {
		addMessage("error", "请填写活动名称");
		return false;
	} else if (data.type == null || data.type == undefined || data.type == "") {
		addMessage("error", "请选择活动类型");
		return false;
	} else if (data.regbegintime == null || data.regbegintime == undefined || data.regbegintime == "") {
		addMessage("error", "请选择活动报名开始时间");
		return false;
	} else if (data.regendtime == null || data.regendtime == undefined || data.regendtime == "") {
		addMessage("error", "请选择活动报名截至时间");
		return false;
	} else if (data.conbegintime == null || data.conbegintime == undefined || data.conbegintime == "") {
		addMessage("error", "请选择活动评审开始时间");
		return false;
	} else if (data.conendtime == null || data.conendtime == undefined || data.conendtime == "") {
		addMessage("error", "请选择活动评审截止时间");
		return false;
	} else if (data.regbegintime > data.regendtime) {
		addMessage("error", "活动报名开始时间必须小于报名截止时间");
		return false;
	} else if (data.regbegintime > data.conbegintime) {
		addMessage("error", "活动评审开始时间必须大于报名开始时间");
		return false;
	} else if (data.conbegintime > data.conendtime) {
		addMessage("error", "活动评审开始时间必须小于评审截止时间");
		return false;
	} else if (data.description == null || data.description == undefined || data.description == "") {
		addMessage("error", "活动描述不能为空");
		return false;
	} else if (data.contemplate == null || data.contemplate == undefined || data.contemplate == "") {
		addMessage("error", "请选择活动评审表");
		return false;
	} else if (data.model == null || data.model == undefined || data.model == "") {
		addMessage("error", "请选择活动评审分配方式");
		return false;
	} else if (data.reviewUsers == 0) {
		addMessage("error", "请选择本次活动的评审专家");
		return false;
	} else if (data.description.length > 500) {
		addMessage("error", "活动描述不能超过500个字");
		return false;
	} else {
		return true;
	}
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
 * 提示信息
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
 * 查询阶段
 */
var activeType;
/**
 * 生成活动类型option和导航条
 */
var getActiveType = function() {
	activeType = getSysCode("JYActivityType");
	var options = "";
	var nav = '<li role="presentation" class="allActive currentActive"><a href="../gateway/getAllActives?id=">全部活动</a></li>';
	if (activeType != null) {
		$.each(activeType, function(i, d) {
			if (i == 0) {
				options += "<option value='" + d.value + "' selected='true'>" + d.name + "</option>";
			} else {
				options += "<option value='" + d.value + "'>" + d.name + "</option>";
			}
			nav += "<li role='presentation'><a href='../gateway/getAllActives?value=" + d.value + "'>" + d.name + "</a></li>";
		});
	}
	$("#active_type").html(options);
	$("#active_nav").html(nav);
};
var getSysCode = function(value) {
	var code;
	$.ajax({
		url : "../gateway/code.do",
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
var getReviewOptions = function() {
	$.ajax({
		url : "../gateway/getReviewOptions",
		type : "POST",
		dataType : "json",
		success : function(data) {
			console.log("评审表options:", data);
			var options = "";
			if (data != null) {
				$.each(data, function(i, d) {
					if (i == 0) {
						options += "<option value='" + d.value + "' selected='true'>" + d.name + "</option>";
					} else {
						options += "<option value='" + d.value + "'>" + d.name + "</option>";
					}
				});
			}
			$("#review_table").html(options);
		},
		error : function(xhr, error, message) {
			// console.log(arguments);
			addMessage("error", "不好意思，请求出错了，错误码" + xhr.status)
		}
	});
};

/** ******************************************************************************************** */
$(function() {
	isEditMode();
	// $("#editExpertBox").css("left", "50%");
	// console.log($.ajaxFileUpload);
	getActiveType();
	// addMessage("success","错误消息");
	getReviewOptions();
	// 本次活动的专家
	getDepts();
	makeDeptTree();
	getUsersByDept();
	var specialists = [];
	setTimeout(function() {
		fillForm();
	}, 100);
	// fillForm();
	// 点击编辑专家成员
	$("#btn-edit-expert").click(function() {
		$("html").addClass("noScroller");
		$("#editExpertBox").css("margin-left", "-" + ($("#editExpertBox").outerWidth() / 2) + "px");
		getDepts();
		$("input[name='username']").val("");
		getUsersByDept();
		specialist = [];
		$("#editExpertBox,.overlay").slideDown();
		return false;
	});
	// 单击遮罩
	$("#cancel_add_special").click(function() {
		initDeptTree();
		$("#saveactive_dept_tree").addClass("hidden");
		$("#saveactive_dept_tree").slideUp('fast');
		$("#input_dept").val(checedDept.title);
		$("html").removeClass("noScroller");
		$("#editExpertBox,.overlay").slideUp();
		return false;
	});
	// 选择部门
	$("#select_dept").change(function() {
		// keywords.deptid = $(this).val();
		// keywords.name = $("input[name='username']").val();
		getUsersByDept();
		return false;
	});
	// 输入用户名
	$("input[name='username']").change(function() {
		// keywords.deptid = $("#select_dept").val();
		// keywords.name = $(this).val();
		getUsersByDept();
		return false;
	});
	// 单击查询
	$("#btn-search-expert").click(function() {
		console.log($("#table_users > tr > td > .icon-my-library-add"));
		// keywords.deptid = $("#select_dept").val();
		// keywords.name = $("input[name='username']").val();
		getUsersByDept();
		return false;
	});
	// 保存专家生成专家列表
	$("#save_add_special").click(function() {
		initDeptTree();
		$("#saveactive_dept_tree").addClass("hidden");
		$("#saveactive_dept_tree").slideUp('fast');
		$("#input_dept").val(checedDept.title);
		// console.log("$.fileupload:",$.ajaxFileUpload);
		saveAddSpecialist();
		$("html").removeClass("noScroller");
		$("#editExpertBox,.overlay").slideUp();
		return false;
	});
	$("input[name='username'],input[id='active_name']").keypress(function(e) {
		var ev = e || window.event;
		if (ev.keyCode == 13) {
			getUsersByDept();
			if (ev.preventDefault) {
				ev.preventDefault();
			} else {
				ev.returnValue = false;
			}
			return false;// 禁用回车事件
		}
	});
	/*
	 * $("input[name='username']").keypress(function(e) { getUsersByDept();
	 * //return false; });
	 */
	/** ****************************************************************************************************** */
});
