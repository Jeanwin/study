<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
</head>
<body>
	<!--[if lte IE 8]>
<div>不支持此浏览器</div>
<![endif]-->
	<div class="container-fluid padding-left-right-none">
		<%@ include file="/WEB-INF/views/include/header.jsp"%>
		<div class="col-xs-12 padding-left-right-none padding-bottom-20" style="background:#f7f7f7;">
			<div class="container-fluid pc-show" id="moreNoticeInfo">
				<div class="container pc-show">
					<div class="row" style="margin-top:16px">
						<div class="col-xs-12 padding-left-right-none" style="background:#fff">
							<div class="col-xs-2 padding-top-30" style="white-space:nowrap">
								<ul class="nav nav-tabs" role="tablist" id="active_nav">
									<!-- 活动导航条 -->
								</ul>
							</div>
							<div class="col-xs-10 whiteframe">
								<div class="col-xs-12 padding-left-right-none margin-top-10">
									<div class="col-xs-10 col-sm-10 col-md-10  margin-top-20 breadcrumb1">您所在的位置：<a href="${ctx}/gateway/getAllActives">教研中心</a>&gt;新建活动</div>
								</div>
								<div class="col-xs-12">
									<div class="content borderTop padding-bottom-30 padding-top-10">
										<form class="form-horizontal" id="new_active_form" method="post" enctype="multipart/form-data" target="hidden_frame">
											<table class="tab">
												<tr>
													<th class="col-sm-2 control-label">活动名称</th>
													<td class="col-sm-8"><input class="form-control border-radius-none" style="height:34px;line-height:34px;width:100%" id="active_name" type="text" /></td>
													<td rowspan="5" class="col-sm-2"><img id="active_img" onclick="onFileSelect()" src="../static/img/front-cover.jpg" width="278" height="170" /><br /> <input name="file"
														id="active_file" onchange="imgPreview()" type="file" class="hidden" /> <input type="button" id="fileBtn" value="添加宣传封面" onclick="onFileSelect()" class="btn btnAddCover" /></td>
												</tr>
												<tr>
													<th class="control-label">活动类型</th>
													<td><select class="form-control border-radius-none form_height_34" style="height:34px;line-height:34px;width:100%" id="active_type">
													</select></td>
												</tr>
												<tr>
													<th class="control-label">报名时间</th>
													<td>
													    <input id="active_sign_begin_date" type="text" class="timetxt form-control border-radius-none datepicker" style="line-height: 16px;float:left;width:46%" placeholder="请选择时间" />
													    <div style="height:34px;line-height:34px;float:left;width:8%;text-align:center">—</div>
													    <input id="active_sign_end_date" type="text" class="timetxt form-control border-radius-none datepicker" style="line-height: 16px;float:left;width:46%" placeholder="请选择时间" />
													</td>  
												</tr>
												<tr>
													<th class="control-label">评审时间</th>
													<td>
													    <input id="active_review_begin_date" type="text" class="timetxt form-control border-radius-none datepicker" style="line-height: 16px;float:left;width:46%" placeholder="请选择时间" />
													    <div style="height:34px;line-height:34px;float:left;width:8%;text-align:center">—</div>
													    <input id="active_review_end_date" type="text"class="timetxt form-control border-radius-none datepicker" style="line-height: 16px;float:left;width:46%" placeholder="请选择时间" /></td>
												</tr>
												<tr>
													<th class="control-label">评审表</th>
													<td><select id="review_table" style="height:34px;line-height:34px;width:100%" class="form-control form_height_34 border-radius-none"></select></td>
												</tr>
												<tr>
													<th class="control-label">活动描述</th>
													<td colspan="2"><textarea style="padding: 6px 12px;min-height:120px;border: 1px #dcdcdc solid;" class="form-control form_height_34 border-radius-none" id="active_description" placeholder="最多200个字" maxlength="200"></textarea></td>
												</tr>
												<tr>
													<th class="control-label">专家组</th>
													<td><button class="btn btn-edit-expert btn-bg-green" id="btn-edit-expert">
															<i class="icon-create"></i>添加专家成员
														</button> <span id="specialist_total"></span></td>
												</tr>
												<tr>
													<th></th>
													<!-- 专家列表 -->
													<td colspan="2" class="expertPerson"></td>
												</tr>
												<tr>
													<th class="control-label">评审分配</th>
													<td><input type="radio" value="1" name="model" id="every-expert" checked><label for="every-expert" class="margin-right-20 margin-left-5">每个专家评审全部项目</label><input type="radio"
														value="0" name="model" id="hand-expert" checked><label for="hand-expert" class="margin-left-5">手动分配专家</label></td>
												</tr>
												<tr>
													<th></th>
													<td colspan="2" align=""><input type="button" id="btn_active_submit" onclick="submitActive()" value="提交活动" class="btn btn-bg-blue" /></td>
												</tr>
											</table>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 底部jsp -->
		<%@ include file="/WEB-INF/views/include/footer.jsp"%>
		<!--  -->
	</div>
	<div class="editExpertBox box" id="editExpertBox" style="position:fixed;top:0;margin:30px auto;left:50%;z-index:2;min-width: 750px;">
		<span class="title" id="users_amount">添加专家</span>
		<div class="expertSearch" style="margin: 15px 0px">
			<form class="form-inline">
				<div class="form-group margin-right-20">
					<label class="sr-only">请选择机构</label>
				    <!-- <select style="width:250px" class="form-control border-radius-none" id="select_dept"></select> -->
				    <input type="text" placeholder="请选择机构" class="form-control border-radius-none" style="width:210px;background: #fff;" disabled="disabled" id="input_dept"/>
				    <span onclick="openDeptTree()" id="btn_open_dept_tree" style="vertical-align: middle;margin-left: -5px;width: 40px;height: 34px;text-align: center;padding: 6px 12px;border: 1px solid #ccc;display: inline-block;border-collapse: collapse;">
				    <i class="glyphicon glyphicon-list"></i></span>
					<!-- 机构树 -->
					<div class="tree hidden" style="top: 120px;left: 101px;" id="saveactive_dept_tree">
					</div>
				</div>
				<div class="form-group margin-right-20">
					<label class="sr-only">请输入关键字名称</label> <input type="text" name="username" class="form-control border-radius-none" placeholder="请输入关键字名称" />
				</div>
				<div class="form-group">
					<label style="margin-top: 6px;"><input type="button" value="查询" class="btn btn-bg-blue padding-left-20 padding-right-20" id="btn-search-expert" /></label>
				</div>
			</form>
		</div>
		<div class="expertTab1" style="min-height: 514px;">
			<table>
				<thead>
					<tr>
						<th class="col-xs-2">姓名</th>
						<th class="col-xs-2">登录账号</th>
						<th class="col-xs-2">性别</th>
						<th class="col-xs-4">机构</th>
						<th class="col-xs-2">操作</th>
					</tr>
				</thead>
				<tbody id="table_users">
					<!-- users -->
				</tbody>
			</table>
			<!-- 分页条 -->
			<div class="col-xs-12 text-right" id="review_user_pagination"></div>
			<div class="row margin-top-20">
				<div class="col-xs-4 col-lg-offset-2" id="save_add_special">
					<input type="button" value="确定" class="btn btn-bg-blue btn-block" />
				</div>
				<div class="col-xs-4" id="cancel_add_special">
					<input type="button" value="取消" class="btn btn-bg-white btn-block cancel-box" />
				</div>
			</div>
		</div>
		<!-- <div class="expertTab expertTab2">
			<div class="top clearfix">
				<span class="pull-right">搜索结果：4</span><i class="icon-reply back" id="to-expertTab1"></i>返回
			</div>
			<table>
				<thead>
					<tr>
						<th>姓名</th>
						<th>登录账号</th>
						<th>性别</th>
						<th>机构</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>刘旭旭</td>
						<td>liuxuxu</td>
						<td>男</td>
						<td>外国语学院</td>
						<td><i class="icon-my-library-add"></i></td>
					</tr>
					<tr>
						<td>刘旭旭</td>
						<td>liuxuxu</td>
						<td>男</td>
						<td>外国语学院</td>
						<td><i class="icon-my-library-del"></i></td>
					</tr>
					<tr>
						<td>刘旭旭</td>
						<td>liuxuxu</td>
						<td>男</td>
						<td>外国语学院</td>
						<td><i class="icon-my-library-del"></i></td>
					</tr>
					<tr>
						<td>刘旭旭</td>
						<td>liuxuxu</td>
						<td>男</td>
						<td>外国语学院</td>
						<td><i class="icon-my-library-add"></i></td>
					</tr>
				</tbody>
			</table>
			<div class="row margin-top-20">
				<div class="col-xs-4 col-lg-offset-2">
					<input type="button" value="确定" class="btn btn-bg-blue btn-block" />
				</div>
				<div class="col-xs-4">
					<input type="button" value="取消" class="btn btn-bg-white btn-block cancel-box" />
				</div>
			</div>
		</div> -->
	</div>
	<!--遮罩层-->
	<div class="overlay"></div>
	<!-- t -->
	<div id="message_info" class="growl" style="display:none">
		<div id="message_content" class="growl-item alert"></div>
	</div>
	<%-- <script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/partical.js"></script> --%>
	<script type="text/javascript" src="${ctx}/static/js/teaching/getUrlParam.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/ajaxFileUpload/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/teaching/saveActive.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/datepicker.js"></script>
</body>
</html>