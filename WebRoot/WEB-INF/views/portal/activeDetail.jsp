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
		<!--头部开始-->
		<%@ include file="/WEB-INF/views/include/header.jsp"%>
		<!--头部结束-->
		<div class="col-xs-12 padding-left-right-none padding-bottom-20" style="background:#f7f7f7;">
			<div class="container pc-show">
				<div class="row" style="margin-top:16px">
					<div class="col-xs-12 padding-left-right-none" style="background: #fff;margin-bottom: 15px;">
						<div class="col-xs-12 breadcrumb1 padding-top-20">您所在的位置：<a href="${ctx}/gateway/getAllActives">教研中心</a>&gt;活动详情</div>
						<div class="col-xs-12 borderTop activeInfo">
							<div class="col-xs-12 margin-top-20">
								<div class="col-xs-5 activeImg">
									<img id="active_img" src="${ctx}/static/img/front-cover.jpg" />
								</div>
								<div class="col-xs-7">
									<h1 id="active_title"></h1>
									<!--  -->
									<div class="activeState" id="active_status"></div>
									<!--  -->
									<div class="enrollTime2" id="active_regdate"></div>
									<div class="access-time" id="active_reviewdate"></div>
								</div>
							</div>
							<div class="col-xs-12">
								<pre id="active_description" style="border:none;"></pre>
							</div>
							<!--  -->
							<div class="col-xs-12">
								<a id="btn_open_edit_active" onclick="openEditActive()"><input type="button" value="编辑活动" class="btn btn-bg-blue  pull-right" /></a>
							</div>
						</div>
					</div>
					<div class="activeRelativeInfo">
						<ul class="clearfix" id="active_navbar">
							<li class="cur" onclick="showView('0')">参赛作品</li>
							<li onclick="showView('1')">评审情况</li>
							<li onclick="showView('2')">统计</li>
							<li onclick="showView('3')">我要报名</li>
						</ul>
					</div>
					<div class="col-xs-12 padding-left-right-none margin-top-15" id="activeDetailTab">
						<!-- 动态内容 -->
						<!-- start参赛作品 -->
						<div class="col-xs-12 activeRelativeContent" style="background:#fff;min-height:82px" id="div_works_view"></div>
						<!-- 参赛作品end -->
						<!-- start评审情况 -->
						<div class="col-xs-12 margin-top-15 padding-left-right-none activeRelativeContent" style="background:#fff;display:none;min-height:82px">
							<table class="reviewInfoTab">
								<thead id="table_works_review_header">
									<tr>
										<th align="50%">作品</th>
										<th>作者</th>
										<th>评审进度</th>
										<th>最高/最低</th>
										<th>综合得分</th>
										<th></th>
									</tr>
								</thead>
								<tbody id="table_works_review"></tbody>
							</table>
							<div class="col-xs-12 text-right margin-top-20" id="review_work_pagination"></div>
						</div>
						<!-- 评审情况end -->
						<!-- start统计 -->
						<div class="col-xs-12 margin-top-15 activeRelativeContent" style="background: #fff;display:none;">
							<div class="situation">
								<div class="title">
									<span>报名情况：</span>
								</div>
								<div class="staticPic" style="background:#f2f2f2;padding: 12px;width:100%;height:auto;min-height:280px;" id="active_chart_bar">
									<img src="${ctx}/static/img/111.jpg" />
								</div>
							</div>
							<div class="situation">
								<div class="title">
									<span>评审进度：</span>
								</div>
								<div class="staticPic" id="active_chart_pie" style="position:relative;width:100%;height:auto;min-height:280px;background:#f2f2f2;padding: 12px;">
									<img src="${ctx}/static/img/112.jpg" />
								</div>
							</div>
							<div id="active_detail_review_detail" class="situation border-none">
								<div class="title">
									<em class="pull-right" id="review_users_amount">共8位专家</em><span>评审情况：</span>
								</div>
								<table class="reviewInfoTab">
									<thead>
										<tr>
											<th>专家</th>
											<th>应评</th>
											<th>已评</th>
											<th>未评</th>
											<th>完成比</th>
										</tr>
									</thead>
									<tbody id="table_review_users"></tbody>
								</table>
							</div>
						</div>
						<!-- 统计end -->
						<!-- start我要报名 -->
						<div class="col-xs-12 margin-top-15 activeRelativeContent" style="background: #fff;display:none;">
							<div class="col-xs-12 wantEnroll padding-left-right-none">
								<div class="col-xs-12 text-right margin-top-15" style="display:none">
									已提交一个项目 <a href="./getAllActives.do"><button class="btn btn-bg-green">
											<i class="icon-create margin-right-5"></i>查看项目
										</button></a>
								</div>
								<div class="col-xs-12 margin-top-20 padding-left-40">
									<form class="form-horizontal" action="./signUpHasPicture.do" id="new_works_form" method="post" enctype="multipart/form-data" target="hidden_frame">
										<div class="col-xs-4 padding-left-10 padding-right-10">
											<div class="col-cs-12">
												<img id="works_img" onclick="onFileSelect()" src="${ctx}/static/img/front-cover.jpg" class="btn-block" />
											</div>
											<input name="file" id="works_file" onchange="imgPreview()" type="file" class="hidden" />
											<div class="col-xs-12 padding-left-right-none">
												<input onclick="onFileSelect()" type="button" id="works_btn" value="修改" class="btn btn-bg-gray btn-block" />
											</div>
										</div>
										<div class="col-xs-8">
											<!-- <form class="form-horizontal"> -->
											<div class="form-group">
												<label class="control-label col-sm-2">作品来源</label>
												<div class="col-sm-10" id="broadcastYesNo">
													<input onclick="selectWorkSource()" type="radio" id="broadcast" name="broadcastOrVideotape" value="1" checked="true"><label for="broadcast" class="margin-right-10 margin-left-5">直播</label>
													<input onclick="selectWorkSource()" type="radio" id="videotape" name="broadcastOrVideotape" value="2" /><label for="videotape" class="margin-left-5">录像</label>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-2"></label>
												<div class="col-sm-10" id="selcourse">
													<span onclick="openAddResourceView()"><i class="icon-my-library-add" style="font-size:20px;color:#999;"></i><span id="works_source_type"></span></span><span id="works_source_name"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-2">作品名称</label>
												<div class="col-sm-5">
													<input id="works_name" name="worksname" type="text" class="form-control border-radius-none" value="" />
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-2">学科</label>
												<div class="col-sm-5">
													<select id="works_subject" name="workssubject" class="form-control border-radius-none">
														<!-- <option>语文</option> -->
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-2">阶段</label>
												<div class="col-sm-5">
													<select id="works_grade" name="worksgrade" class="form-control border-radius-none">
														<!-- <option>初中</option> -->
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-2">作品描述</label>
												<div class="col-sm-10">
													<textarea id="works_description" name="worksdescription" class="form-control border-radius-none" placeholder="请输入作品描述" rows="5"></textarea>
												</div>
											</div>
											<div class="form-group margin-top-50">
												<label class="control-label col-sm-2"></label>
												<div class="col-sm-5" id="btn_submit_new_works" onclick="submitWorks()">
													<input type="button" value="提交报名" class="btn form-control btn-bg-blue" />
												</div>
											</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!-- 我要报名end -->
				</div>
				<!-- start评审详情 -->
				<div class="col-xs-12 padding-left-right-none margin-top-15" id="reviewInfoMore">
					<div class="reviewMoreInfo">
						<div class="averageScore pull-right">
							目前平均分：<span id="work_detail_avgscore"></span>
						</div>
						<a onclick="showView(1)" class="margin-right-20"><i class="icon-reply margin-right-5" style="vertical-align: text-bottom"></i>返回</a> <span id="work_detail_title"></span>
					</div>
					<div class="col-xs-12 margin-top-15 padding-left-right-none" style="background:#fff;">
						<table class="reviewInfoTab">
							<thead>
								<tr>
									<th class="col-xs-2">评委<span class="blue-color margin-left-10" id="work_detail_review_amount"></span></th>
									<th class="col-xs-2">评审时间</th>
									<th class="col-xs-2">评分</th>
									<th class="col-xs-6">意见</th>
								</tr>
							</thead>
							<tbody id="table_review_info_more"></tbody>
						</table>
					</div>
				</div>
				<!-- end评审详情 -->
				<!-- start作品管理 -->
				<div class="col-xs-12 padding-left-right-none margin-top-15" id="writingManage">
					<div class="reviewMoreInfo">
						<a onclick="showView(0)"><i class="icon-reply"></i>返回</a>
					</div>
					<div class="col-xs-12 margin-top-15 padding-left-right-none" style="background:#fff;">
						<table class="reviewInfoTab">
							<thead>
								<tr>
									<th><button onclick="showAssign()" class="btn btn-bg-white btn-person-add" id="expert-distribution">
											<i class="icon-person-add"></i> 分配专家
										</button>参赛作品</th>
									<th>作者</th>
									<th>学科</th>
									<th>评审专家</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="table_works_assign"></tbody>
						</table>
						<div class="col-xs-12 text-right margin-top-20" id="works_assign_pagination"></div>
					</div>
				</div>
				<!-- 分配专家end -->
			</div>
		</div>
	</div>
	<!-- 底部jsp -->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<!--  -->
	</div>
	<!-- here -->
	<div class="box" id="selbroadcastBox" style="position:fixed;top:30px;left:50%;min-width: 730px">
		<span class="title">选择直播课程</span>
		<div class="expertSearch" style="margin: 15px 0px">
			<form class="form-inline">
				<div class="form-group margin-right-20">
					<label class="sr-only">周次</label> <select onchange="getBroadCast()" class="form-control border-radius-none" id="select_weeks"></select>
				</div>
				<div class="form-group margin-right-20">
					<label class="sr-only">星期</label> <select onchange="getBroadCast()" class="form-control border-radius-none" id="select_weekdate"></select>
				</div>
				<div class="form-group margin-right-20">
					<label class="sr-only">请输入课程关键字</label> <input onchange="getBroadCast()" type="text" name="subject" class="form-control border-radius-none" placeholder="请输入关键字名称" />
				</div>
				<div class="form-group">
					<label><input onclick="getBroadCast()" type="button" value="查询" class="btn btn-bg-blue padding-left-20 padding-right-20" id="btn-search-expert" /></label>
				</div>
			</form>
		</div>
		<!-- 选择直播课start -->
		<div class="">
			<div style="max-height:400px;overflow-y:auto;overflow-x:hidden">
				<table>
					<thead>
						<tr>
							<th></th>
							<th>周次</th>
							<th>星期</th>
							<th>节次</th>
							<th>课程</th>
							<th>主题</th>
						</tr>
					</thead>
					<tbody id="table_live_curriculum"></tbody>
				</table>
			</div>
			<!-- 分页 -->
			<div class="col-xs-12 text-right" style="padding-right:0px" id="live_curriculum_pagination"></div>

			<div class="row">
				<div class="col-xs-4 col-lg-offset-2">
					<input type="button" onclick="okSelectLive()" value="确定" class="btn btn-bg-blue btn-block" />
				</div>
				<div class="col-xs-4" onclick="closeAddResourceView()">
					<input type="button" value="取消" class="btn btn-bg-white btn-block cancel-box" />
				</div>
			</div>
		</div>
		<!-- 选择直播课end -->
	</div>
	<div class="file-box col-xs-6 col-md-4" id="select_record_film" style="position:fixed;top:30px;left:50%;min-width: 550px;">
		<div class="file-box-top">
			<span class="pull-right color-red">最多只能选择一个</span> <a class="cur" id="film_btn" onclick="showSelectFileView(1,1)">选择录像文件</a> | <a id="upload_btn" onclick="showSelectFileView(2)">上传</a>
		</div>
		<div class="file-box-mid" id="select_film">
			<ul id="treeTag">
			</ul>
			<div class="row margin-top-20">
				<div class="col-xs-6" onclick="okSelectFilm()">
					<input type="button" value="添加" class="btn btn-bg-blue" style="width:100%;" />
				</div>
				<div class="col-xs-6" onclick="closeAddResourceView()">
					<input type="button" value="取消" class="btn btn-bg-white" id="cancelFileBox" style="width:100%;" />
				</div>
			</div>
		</div>
		<!-- 选择上传文件 -->
		<div class="file-box-mid selectVideoFileContent" style="display: none;" id="select_upload">
			<div class="col-xs-12 padding-left-right-none">
				<span class="btn btn-success fileinput-button" id="choose_btn" onclick="onVideoFileSelect()"> <i class="icon-add"></i> <span>上传文件</span></span>
			    <input id="choose" style="display:none" onchange="onSelectFilm()" name="files" type="file" class="choose">
				
				<div style="margin-top:5px;border-top:1px #ddd solid;min-height: 206px;">
					<table class="table" style="min-height:190px">
						<thead>
							<tr>
								<th class="col-xs-4">文件名</th>
								<th class="col-xs-2">大小</th>
								<th class="col-xs-3">进度</th>
								<th class="col-xs-2">速率</th>
								<th class="col-xs-1" style="white-space: nowrap;">操作</th>
							</tr>
						</thead>
						<tbody id="table_new_works_upload">
						</tbody>
					</table>
				</div>
				<div class="row margin-top-20">
					<div class="col-xs-6" onclick = "okSelectUpload()">
						<input type="button" value="添加" class="btn btn-bg-blue" style="width:100%;" />
					</div>
					<div class="col-xs-6" onclick="closeAddResourceView()">
						<input type="button" value="取消" class="btn btn-bg-white" id="cancelFileBox2" style="width:100%;" />
					</div>
				</div>
			</div>
		</div>
		<!-- 选择上传文件 -->
	</div>
	<!-- 分配专家 -->
	<div class="selExpertBox box col-xs-4" id="selExpertBox" style="top: 30px;left: 500px;position:fixed">
		<span class="title clearfix"><em class="pull-right" id="list_expert_count"></em>分配专家</span>
		<div class="expertTab">
			<ul id="list_expert">
			</ul>
			<!-- 7-31 -->
			<div style="width:100%;text-align:right;color:#2CA7E7;">已评审的专家不会被删除!</div>
			<div class="row margin-top-10">
				<div class="col-xs-4 col-lg-offset-2" onclick="okAssignSpecialist()">
					<input type="button" value="确定" class="btn btn-bg-blue btn-block" />
				</div>
				<div class="col-xs-4" onclick="closeAssign()">
					<input type="button" value="取消" class="btn btn-bg-white btn-block cancel-box" />
				</div>
			</div>
		</div>
	</div>
	<!-- 删除提示 -->
	<div id="works_delete" style="background-color: #f2f2f2;width:40%;position:fixed;top:30px;left:50%;z-index:2;min-width:400px;height:300px;border-radius: 6px;display:none">
		<div class="modal-header" style="padding:15px">
			<a class="close" onclick="cancelDeleteWorks()"> <span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
			</a> <span class="modal-title">
				<h3>删除作品</h3>
			</span>
		</div>
		<div class="modal-body" style="min-height:160px">
			<h4>
				确认删除<strong id="works_del_author"></strong>的作品:
				<h3 id="works_del_name" style="text-align:center"></h3>
				<div style="max-height:380px;overflow-y:auto"></div>
			</h4>
		</div>
		<div class="modal-footer">
			<button class="btn btn-danger" style="min-width:120px" onclick="deleteWorks()" focus-me="true">确认</button>
			<button class="btn btn-default" style="min-width:120px" onclick="cancelDeleteWorks()">取消</button>
		</div>
	</div>
	<!--遮罩层-->
	<div class="overlay"></div>
	<!-- <div class="overlay" onclick="closeAssign();cancelDeleteWorks();closeAddResourceView()"></div> -->
	<!-- t -->
	<div id="message_info" class="growl" style="display:none">
		<div id="message_content" class="growl-item alert"></div>
	</div>
	<%-- <script type="text/javascript" src="${ctx}/static/js/jquery/jquery-1.11.3.min.js"></script> --%>
	<script type="text/javascript" src="${ctx}/static/js/teaching/getUrlParam.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/ajaxFileUpload/ajaxfileupload.js"></script>
	<script src="${ctx}/static/js/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${ctx}/static/js/jquery-file-upload/js/jquery.iframe-transport.js"></script>
    <script src="${ctx}/static/js/jquery-file-upload/js/jquery.fileupload.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/teaching/activeDetail.js"></script>
</body>
</html>