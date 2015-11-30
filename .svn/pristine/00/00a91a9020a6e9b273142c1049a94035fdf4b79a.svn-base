<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<script type="text/javascript">
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
	};
};
     $(function(){
    	 $.each($("#active_list_nav").find("li"),function(i,d){
    		 var ahref = $(this).find("a").attr("href");
     		 var value = "";
     		 if(ahref.lastIndexOf("value=") != -1){
     		    value = ahref.substring(ahref.lastIndexOf("value=")+6,ahref.length);
     		 }
    		console.log(value);
    		console.log("param",$.getUrlParam("value"));
    	    if($.getUrlParam("value") === null){
    	    	if(i === 0){
    	    		$(d).addClass("currentActive");
    	    	}else{
    	    		$(d).removeClass("currentActive");
    	    	}
    	    }else{
    	    	if($.getUrlParam("value") === value){
    	    		$(d).addClass("currentActive");
    	    	}else{
    	    		$(d).removeClass("currentActive");
    	    	}
    	    }
    	 });
    	 if(currentUser != null || currentUser != undefined){
    		 if(currentUser.roles.indexOf("管理员") != -1){
    			 $("#btn_open_new_active").show();
    		 }else{
    			 $("#btn_open_new_active").hide();
    		 }
    	 }else{
    		 $("#btn_open_new_active").hide();
    	 };
    	 $("#btn_open_new_active").click(function(){
    		if (currentUser == null || currentUser == undefined) {
    			window.location = "../login";
    		}else{
    			if (currentUser.roles.indexOf("管理员") != -1) {
        			window.location = "../private/saveActive.do";
        		} else {
        			addMessage("error","对不起，您没有管理员权限");
        		}
    		}
    		return false;
    	 }); 
     })
</script>
</head>
<body>
	<!--[if lte IE 8]>
<div>不支持此浏览器</div>
<![endif]-->
	<div class="container-fluid padding-left-right-none">
		<%@ include file="/WEB-INF/views/include/header.jsp"%>
		<div class="col-xs-12 padding-left-right-none padding-bottom-20" style="background:#f7f7f7;">

			<form id="allActives" action="">
				<input type="hidden" name="page" id="page" value="${page.page}"> 
				<input type="hidden" name="pageSize" id="pageSize" value="10"> 
				<input type="hidden" name="value" id="activeType" value="${activeType}">

				<div class="container-fluid pc-show" id="moreNoticeInfo">
					<div class="container pc-show">
						<div class="row" style="margin-top:16px">
							<div class="col-xs-12 padding-left-right-none" style="background:#fff">
								<div class="col-xs-2 padding-top-30" style="white-space:nowrap">
									<ul class="nav nav-tabs" role="tablist" id="active_list_nav">
										<li role="presentation"><a href="${ctx}/gateway/getAllActives?id=&pageSize=10" <c:if test="${activeType==null }">style="background-color:#4F9FEB"</c:if>>全部活动</a></li>
										<c:forEach var="syscode" items="${sysCodeList.data}">
											<li role="presentation"><a href="${ctx}/gateway/getAllActives?value=${syscode.value}&pageSize=10" <c:if test="${activeType==syscode.value }">style="background-color:#4F9FEB"</c:if>>${syscode.name}</a></li>
										</c:forEach>
									</ul>
								</div>
								<div class="col-xs-10 whiteframe">
									<div class="col-xs-12 padding-left-right-none margin-top-10">
										<div class="col-xs-10 col-sm-10 col-md-10  margin-top-20 breadcrumb1"></div>
										<div class="col-xs-2 col-sm-2 col-md-2 margin-top-10 text-right">
											<a id="btn_open_new_active"><input type="button" value="新建活动" class="btn btn-primary color-btn" /></a>
										</div>
									</div>
									<div class="col-xs-12">
										<div class="content">
											<c:forEach var="active" items="${activeList}" varStatus="i">
												<div class="raceList">
													<h2>
														<a href="./activeDetail?activeid=${active.id}" target="_self">主题:${active.name} 
													</h2>
													</a>
													<div class="raceTime">
														<span class="enrollTime"><i class="icon-access-time yellow-color font-large"></i> 报名时间:${active.regbegintime}--${active.regendtime}</span><span class="commentTime"> <i
															class="icon-access-time green-color font-large"></i>评审时间:${active.conbegintime}--${active.conendtime}
														</span>
													</div>
													<div class="row">
														<div class="col-xs-4 imgFace">
															<a href="./activeDetail?activeid=${active.id}"> 
															<%-- 	<c:choose>
																	<c:when test="${active.picture != '' && active.picture != null }">
																		<img width="285" height="178" src="${active.picture}" />
																	</c:when>
																	<c:otherwise>
																		<img width="285" height="178" src="${ctx}/portal/assets/img/lemonade-pages_pic1.jpg" />
																	</c:otherwise>
														   </c:choose> --%> 
														   
														   <img width="285" height="178" src="${active.picture}" onerror="javascript:this.src='${ctx}/static/img/lemon-pages-12.jpg'" />
															</a>
														</div>
														<div class="col-xs-8">
															<p>${active.description}</p>
														</div>
													</div>
													<div class="raceActive">
														<c:if test="${active.status==1}">
															<img src="${ctx}/static/img/enroll_in.jpg" />
														</c:if>
														<c:if test="${active.status==2}">
															<img src="${ctx}/static/img/enroll_in.jpg" /><img src="${ctx}/static/img/review_in.jpg" />
														</c:if>
														<c:if test="${active.status==3}">
															<img src="${ctx}/static/img/review_in.jpg" />
														</c:if>
														<c:if test="${active.status==4}">
															<img src="${ctx}/static/img/review_over.jpg" />
														</c:if>
													</div>
												</div>
											</c:forEach>


											<!--  <div class="col-xs-12 text-right margin-top-20">
                                        <div class="page">
                                            <a href=""><i class="icon-chevron-left"></i></a><a href="">1</a><a href="">2</a><a href="">3</a><a href="" class="cur">4</a><a href="">5</a><a href="">6</a><a href="">7</a><a href="">8</a><a href="">9</a><a href="">10</a><a href=""><i class="icon-chevron-right"></i> </a>
                                        </div>
                                    </div> -->

											<div class="col-xs-12 text-right margin-top-20">
												<page:pagebar pageInfo="${page}" formid="allActives" />
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</form>
		</div>
	</div>
	<div id="message_info" class="growl" style="display:none">
		<div id="message_content" class="growl-item alert"></div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/teaching/getUrlParam.js"></script>
</body>
</html>