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
		
		
		<form id="allExcel" action="">
	        <input type="hidden" name="page" id="page" value="${page.page}">
	    	<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}">
	    	<input type="hidden" name="type" id="type" value="${type}">
	    </form>
	    	
							    	
		<div class="col-xs-12 padding-left-right-none padding-bottom-20" style="background:#f7f7f7;">
			<div class="container pc-show" id="liveCourse">
				<div class="row" style="margin-top:16px">
					<div class="col-xs-12 padding-left-right-none " style="background:#fff">
						<div class="col-xs-2 padding-top-30" style="white-space:nowrap">
							<ul class="nav nav-tabs" role="tablist">
								<li role="presentation">
								<a href="${ctx}/gateway/exec" <c:if test="${type==sub.value||type==''}">style="background-color:#4F9FEB"</c:if> >全部</a>
								</li>
							<c:forEach var="sub" items="${subject}">
								<li role="presentation">
								<a href="${ctx}/gateway/exec?type=${sub.value}" <c:if test="${type==sub.value }">style="background-color:#4F9FEB"</c:if> >${sub.name}</a>
								</li>
							</c:forEach>
							</ul>
						</div>
						<div class="col-xs-10 whiteframe">
							<div class="col-xs-12 padding-left-right-none" style="padding-bottom:2%">
								<div class="col-xs-7 col-xs-offset-3 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4 margin-top-10 margin-bottom-10">
									<img src="${ctx }/static/img/lemonade-pages_title3.png" style="width: 100%" />
								</div>
							</div>
							<div class="col-xs-12">
								<div class="tab-pane col-xs-12 padding-left-right-none" id="biology">
								
								<c:forEach var="live" items="${execList.data}">
									<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 padding-right-none listCourseOver" style="max-width:400px;">
										<div class="vedio-image padding-left-right-none col-xs-12">
											<a href="courseDetail?floder=${live.floder }" target="_blank"> <img src="${live.uploadPic }" onerror="javascript:this.src='${ctx}/static/img/lemon-pages-12.jpg'"/>
											</a>
										</div>
										<div class=" col-xs-12 padding-left-right-none">
											<div class="hover-image">
												<span class="col-xs-3 teacher-image"></span>
											</div>
											<div class="v-meta-overlay">
												<span class="teacher-name">${live.name}</span>
											</div>
										</div>
										<div class="col-xs-12 padding-left-right-none margin-bottom-10 vedio-shadow">
											<div class="col-xs-12 coursename">${live.resourcename}</div>
											<div class="col-xs-12 course-otherinfo">
												<div class="col-xs-6">
													<i class="icon-sms"></i> <span class="vedio-comment">${live.num}</span>
												</div>
												<div class="col-xs-6 text-right padding-right-none pull-right">
													<span class="glyphicon glyphicon-eye-open"></span> <span class="vedio-viewcount">${live.watchwatchnum}</span>
												</div>
											</div>
										</div>
									</div>
									
									</c:forEach>
								
								<div class="col-xs-12 text-right margin-top-20">
                          				 <page:pagebar pageInfo="${page}" formid="allExcel"/>
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

</body>
</html>
