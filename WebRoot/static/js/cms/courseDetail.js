var curriculumid = $.getUrlParam("curriculumid");
var floder = $.getUrlParam("floder");
var  workid = $.getUrlParam("workid");
var  source = $.getUrlParam("source");
var  tab = $.getUrlParam("tab");
var typedata;
var com_id;
if(curriculumid != null){
	com_id = curriculumid;
	typedata='1';
}else if(floder != null){
	com_id=floder;
	typedata='2';
}else{
	com_id= workid;
	typedata='3';
}
var templateid;
var recordtemplateid; 
//var resourceid;
$(function(){
	 if(workid != null && workid != undefined){
		 getActiveByWorkId();
	 }
	 if(currentUser != null && currentUser != undefined && workid != null && workid != undefined){
		 getReviewByWorkId();
	 }
	 $('#oneLevelComment').bind('input propertychange', function() {  
		 var length = $(this).val().length;
		  $('#result').html(length);  
	 });  
	 $("#content").focus(function(){
		 try{
//			 VideoControl.b[0].pause();
			 document.getElementsByClassName('miframe')[0].contentWindow.zk_player.pause();
		 }catch(err){
			 
		 }
		
// if ($(".mainWindow")[0].childNodes[0] != null ||
// $(".mainWindow")[0].childNodes[0] != undefined) {
// $(".mainWindow")[0].childNodes[0].pause();
// }
		 
	});
		$(".icon-access-time").live('click',function(){
			var nowtime=$(this).next().html();
			var mintos=nowtime.split(':')[0]*60;
			var s=parseInt(nowtime.split(':')[1].trim());
			var total=mintos+s;
			 console.log(total);
			$("#MainVideo")[0].currentTime=total;

		});
		$(".read").live('click',function(){
	    $(".bookContent").hide();
	    $(this).parent().parent().siblings().find("div.bookContent").parent().parent().hide();
	    $(this).parent().parent().next().show().find(".bookContent").show();
	    var aid=$(this).parent().parent().next().find(".bookContent > a").attr("id");
	    var transferurl=$(this).parent().parent().next().find(".bookContent > span").html();
	    var fp=new FlexPaperViewer( 
		         '/study/static/js/curriculum/flexpaper/FlexPaperViewer',         
		         aid, {  
		             config : {// new FlexPaperView要传三个参数
								// 这里的是FlexPaperViewer,viewerPlaceHolder，和config
		                 SwfFile : escape('/data/transfer/'+transferurl),// 需要Flex打开的文档，但是我发现没有FlexPaperViewer的时候就不恩能够运行。
		                 Scale : 1, // 缩放的意思
		                 ZoomTransition : 'easeOut',// 缩放样式的选择
		                 ZoomTime : 0.5 	,// 缩放使用的时间
		                 ZoomInterval : 0.2,// 缩放比例之间间隔，默认值为0.1，该值为正数。 神马意思？？？
		                 FitPageOnLoad : false,// 自适应页面，工具栏上有
		                 FitWidthOnLoad : true,// 自适应宽度，工具栏上有
		                 FullScreenAsMaxWindow : false,// 如果设置为true的时候，点击全屏并不是全屏而是一个新页面，据说独立的flex播放的时候用这个比较合适
		                 ProgressiveLoading : true,// true的话不全部加载文档，边看边加载
		                 MinZoomSize : 0.2,// 最大缩放比例
		                 MaxZoomSize : 5,// 最小缩放比例
		                 SearchMatchAll : false,// 为true的时候搜索的时候便会出现高亮
		                 InitViewMode : 'Portrait',// 设置启动模式如"Portrait" or
													// "TwoPage" 这句话什么意思？？？
		                 // PrintPaperAsBitmap : false,
		                 RenderingOrder:'flash,html',// 新加入
		                 // PrintEnabled:true,
		                 ViewModeToolsVisible : true,// 工具栏上是否显示样式选择框
		                 ZoomToolsVisible : true,// 工具栏上是否显示缩放工具
		                 NavToolsVisible : true,// 工具栏上是否显示导航工具
		                 CursorToolsVisible : true,// 工具栏上是否显示光标工具
		                 SearchToolsVisible : true,// 工具栏上是否显示搜索
						 WMode:'transparent',
		                 localeChain : 'zh_CN'// 设置语言
		             }  
		         });
		});
		$(".readone").live('click',function(){
		    $(".bookContent").hide();
		    $(this).parent().parent().siblings().find("div.bookContent").parent().parent().hide();
		    $(this).parent().parent().next().show().find(".bookContent").show();
			});	
 
    	var data = {
    			workid:workid,
    			curriculumid :curriculumid,
    			floder:floder,
    			source:typedata
    		};
    	$.ajax({
    		type : "POST",
    		data : JSON.stringify(data),
    		async : false,
    		url : "/study/gateway/selectCurriculumDetails.do",
    		dataType : "json",
    		contentType : 'application/json; charset=UTF-8',
    		success : function(data) {
    			console.log(data);
    			document.getElementById("subjectname").innerHTML=data.data.subject;// 标题
    			document.getElementById("teachername").innerHTML=data.data.teachername;// 老师名字
    			document.getElementById("teacherIntroduce").innerHTML=data.data.teacherIntroduce;// 老师介绍
    			document.getElementById("courseIntroduce").innerHTML=data.data.courseIntroduce;// 课程介绍
// document.getElementById("datestarttime").innerHTML=data.data.date;
    			$("#teacherUrl").attr("src",data.data.pictureURL);
    			if(currentUser == undefined || (data.data.loginname != currentUser.loginname)){
    				$("#editmaterials").attr('disabled',"true");
    			}else{
    				$("#editmaterials").removeAttr("disabled"); 
    			}
    		}
    	});
    	
    	// 保存笔记
    	$("#savenote").click(function(){
    		
    		if (currentUser == null || currentUser == undefined) {
    			window.location = "/study/login";
    			return;
    		}
    		if($("#content").val()== null || $("#content").val()== ""){
    			return;
    		}
    		var videotime;
    		if(document.getElementsByClassName('miframe')[0] == undefined){
    			videotime='00:00';
    		}else{
    			videotime = document.getElementsByClassName('miframe')[0].contentWindow.zk_player.currentTime();
        		videotime=videotime.length>0?videotime:'00:00'
    		}
    		/*	if(typedata == '1'){
    			videotime='0:00';
    		}else{*/
/*    			var myVideo = document.getElementById('MainVideo')// 获取video元素
        		if(myVideo == null){
        			return;
        		}
        		if(myVideo.currentTime=='0'){
        			myVideo=document.getElementById('MainVideo2');
        		}
        		if(myVideo != null && myVideo.currentTime == '0'){
        			myVideo=document.getElementById('MainVideo3');
        		}
        		if(myVideo == null){
        			return;
        		}*/
    			
        		
//    		}
    		
    		var data = {
    				content:$("#content").val(),
    				worksid : workid,
    				curriculumid : curriculumid,
        			floder:floder,
        			type:typedata,
    				videotime:videotime
        		};
        	$.ajax({
        		type : "POST",
        		data : JSON.stringify(data),
        		async : false,
        		url : "/study/rest/curriculumDetails/addNote.do",
        		dataType : "json",
        		contentType : 'application/json; charset=UTF-8',
        		success : function(data) {
        			getNoteList();
        			addMessage("success", "保存成功！");
        			//VideoControl.b[0].play();
        			document.getElementsByClassName('miframe')[0].contentWindow.zk_player.play();
        			 $("#content").val("");
        		}
        	});
    	 });
    	// 保存听课评价
/*
 * $("#savetRecordDetails1").click(function(){ if (currentUser == null ||
 * currentUser == undefined) { window.location = "../login"; return; } var obj
 * =[]; $.each($(".courseViewContent .record-detail"),function(i,datavalue){ var
 * content = "{" + "'id':'" + datavalue.id + "'|" + "'score':'" +
 * datavalue.value + "'" +"}</><><>"; obj.push(content); }); var data = {
 * templateid:$('#recordtemplateid').html(), worksid : $("#workid").html(),
 * floder : $("#floder").html(), curriculumid : $("#curriculumid").html(),
 * type:$("#typedata").html(), content:obj }; $.ajax({ type : "POST", data :
 * JSON.stringify(data), async : false, url :
 * "../rest/curriculumDetails/savetRecordDetails.do", dataType : "json",
 * contentType : 'application/json; charset=UTF-8', success : function(data) {
 * addMessage("success", "听课评价保存成功！"); selectRecordDetails(); }, error :
 * function(data) { addMessage("success", "听课评价保存失败！"); } }); });
 */
    	// 保存评价
    	$("#savetReviewDetails").click(function() {
    		if (currentUser == null || currentUser == undefined) {
    			window.location = "/study/login";
    			return;
    		}
    		/** *********************************@Jeanwin*************************************** */
    		if(workid != null && workid != undefined){
    			if(myReview == null || myReview == undefined){
        			addMessage("error","对不起，您没有评审权限。");
        			return;
        		}else{
        		    if(currentActive.status.indexOf("评审中") == -1){
       			     addMessage("error","对不起，活动不在评审中。");
       			     return;
	       		    }else if(myReview.isover == '1'){
	       			     addMessage("error","您已经评过该作品了");
	       			     return;
	       		    }
        		}
    		}
    		    	var totalnum=0;
    	    		var obj ="[";
    	    		$.each($('.coursedtail .score-num'), function(i, datavalue) {
    	    			 obj = obj+"{";
    	    			obj = obj + '"id":"' + datavalue.id + '",';
    	    			obj = obj + '"score":"' + datavalue.value + '"';
    	    			obj = obj + "},";
    	    			totalnum += datavalue.value*1;
    	    		});
    	    		obj=obj.substring(0, obj.length-1)+ "]";
    	    		//
    	    		var data = {
    	    				templateid:templateid,
    	    				 worksid : workid,
    	    				 floder : floder,
    	    				 curriculumid : curriculumid,
    	    				 type:typedata,
    	    				 content:obj,
    	    				 score:totalnum,
    	    				 remark:$("#remark").val()
    	    				};
    	    		$.ajax({
    	    	 		type : "POST",
    	    	 		data : JSON.stringify(data),
    	    	 		async : false,
    	    	 		url : "/study/rest/curriculumDetails/savetReviewDetails.do",
    	    	 		dataType : "json",
    	    	 		contentType : 'application/json; charset=UTF-8',
    	    	 		success : function(data) {
    	    	 			addMessage("success", "评价保存成功！");
    	    	 			selectReviewDetails();
    	    	 		},
    	    			error : function(data) {
    	    				addMessage("error", "保存失败！");
    	    			}
    	    	 	});
    	});
    
    	//
    	$(".my-note-box").hide();
    	getNoteList();
    	selectReviewDetails();
    	selectRecordDetails();
    	getTrees();
    	//查看视频
    	getCourseDetail();
    	// 查看素材
    	selectMaterialList();
    	selectMaterialOnlineRead();
    	// 查看评价的人数和星星数
    	selectCommentCount();
    	// 评论获取列表数据
    	$("#commentMenu").click(function(){
    		commentList();
    	 });
     	// 给评分标签添加点击事件
    	scoreMenuEvent();

// alert($("#MainVideo").length)
//    	if($("#MainVideo").length>0){
/*
 * author by wh 获取video标签父级div 集合 初始化视频控制js
 * 
 */    	
    	/*var videoObj=[$(".mainWindow")[0]];
    	var videoDoms = $(".videoPos")
    	for(var i=0;i<videoDoms.length;i++){
    		videoObj.push(videoDoms[i])
    	}
    	var VideoControl = new displayVideos(videoObj);*/
    //	window.VideoControl = VideoControl;
    	
/*
 * author by wh 为所有右侧video添加点击事件 ps:Mainvideo 无视
 */
    	/*$(".childWindow div .videoPos").each(function(n){
		    $(this).click(function(){VideoControl.changeMainVideo(n+1)});
		}) */
//		}   
    	//
    	if(currentUser != undefined && (currentUser.usertype == '2')){
    		 $("#commentMenu").addClass("current").siblings().removeClass();
    	     $(".courseViewContent").hide().eq($('.courseViewContent ul li').index("#commentMenu")).show();
    	     commentList();
    	     $(".courseViewNav").find("li:eq(0)").attr("style","display:none");
    	     $(".courseViewNav").find("li:eq(1)").attr("style","display:none");
    	}else{
    		if($("#tab").html() == '1'){
    			$(".courseViewNav").find("li:eq(0)").addClass("current").siblings().removeClass();
        		$(".courseViewContent").hide();
        		$(".reviewWrap").show();
    		}else{
    			$(".courseViewNav").find("li:eq(0)").addClass("current").siblings().removeClass();
        		$(".courseViewContent").hide();
        		$(".reviewWrap").show();
    		}
    		
    	}
    	
    });
 /** *******************************************@Jeanwin******************************************* */
 /**
	 * @addon 根据作品id查询活动详情
	 */
 var currentActive;
 var getActiveByWorkId = function(){
	var workId = workid;
	$.ajax({
		type : "POST",
 		data : $.param({workId:workId}),
 		async : false,
 		url : "/study/gateway/getActiveByWorkId.do",
 		dataType : "json",
 		//contentType : 'application/json; charset=UTF-8',
 		success : function(data) {
 			console.log("getActiveByWorkId:",data);
 			if(data != null && data != undefined){
 				currentActive = data;
 			}
 			// addMessage("success", "评价保存成功！");
 		},
		error : function(data) {
			addMessage("Error", "查询活动详情失败了，请刷新页面");
		}
	});
 };
 /**
	 * @addon 根据作品id查询评审记录
	 */
 var myReview;
 var getReviewByWorkId = function(){
		var workId = workid;
		//console.log("workID",workId);
		$.ajax({
			type : "POST",
	 		data : $.param({workid:workId}),
	 		async : false,
	 		url : "/study/private/getReviewByUser.do",
	 		dataType : "json",
	 		//contentType : 'application/json; charset=UTF-8',
	 		success : function(data) {
	 			console.log("getReviewByWorkId:",data);
	 			if(data != null && data != undefined){
	 				myReview = data;
	 			}
	 		},
			error : function(data) {
				addMessage("Error", "查询评审记录失败了，请刷新页面");
			}
		});
 };
 // 获取评论列表
 var commentList=function(pageIndex){
	 var type = typedata;
	 var resourceid='';
	 if(type==3){
		 resourceid=workid;
	 }else if(type==2){
		 resourceid=floder;
	 }else if(type=1){
		 resourceid=curriculumid;
	 }
	 
	 var totalnums = 0;
	 var keywords ={
			 typedata: type,
			 resourceid: resourceid
	 };
	 
	if (pageIndex == null || pageIndex == undefined || pageIndex == '') {
			pageIndex = 1;
	};
	 var page = {
			 pageSize:10,
			 pageIndex:pageIndex,
			 limit:7
	 };
	 
		var data = {
				keywords:keywords,
				page:page
		};
	console.log("分页传值:"+data);
	
	$.ajax({
		type : "POST",
		data : JSON.stringify(data),
		async : false,
		url : "/study/gateway/getComment.do",
		dataType : "json",
		success : function(data) {
			totalnums = data.total;
			$("#commentNums").text(totalnums);
			 var htmlStr = "";
			 $("#commentBody").empty();
				 $.each(data.data, function(i, field){
					 htmlStr += "<div class='col-xs-12 padding-top-10 padding-bottom-10'>"
					 +"  <div class='col-xs-1 commentHeadPic padding-left-right-none'>"
					 +"   <img src='"+field.pictureURL+"'>"
	                     +"  </div>"
	                 +"  <div class='col-xs-11  padding-bottom-10' style='border-bottom: solid 2px #f7f7f7'>"
	                 +"  <div class='col-sm-8'>"
	                     +"  <div><b>"+field.createuser+"</b></div>"
	                         +"   <div style='font-size: 15px;word-wrap:break-word'>"+field.content+"</div>"
	                         +" </div>"
	                     +"  <div class='col-sm-4'>"
	                     +"  <div class='pull-right'>"
	                         +"   <span style='margin-right: 40px'>"+field.createdate+"</span>"
	                             +"       <span id='score_'"+field.id+">";
	                             for(var i=1;i<=5;i++){
	                            	 if(i<=field.score){
	                            		 htmlStr+=" <i class='icon-star yellow-color'></i>";
	                            	 }else{
	                            		 htmlStr+=" <i class='icon-star gray-color'></i>";
	                            	 }
	                             }
	                             htmlStr+="</span>"
	                   +"    <div style='margin-left: 200px'><span class='btn btn-link' onclick='changeHuiFuDailog("+field.id+");'>回复<span></div>"
	                             +"</div>"
	                         +" </div>";
	                         
	                     // 子集评论列表
	                 if(field.nodes!=null&&field.nodes.length>0){
	                	 htmlStr+="   <div class='col-xs-12 margin-left-none' style='background:#f2f2f2;'>           " ;
	                     
	                     
	                     $.each(field.nodes, function(i, child){
	                    	 htmlStr+="     " +
	                    	"<div class='col-xs-12 padding-top-10' style='border-bottom:1px solid #ccc'>  "
	                    	+ "   <div class='col-sm-10'>                                          " 
	                     +"          <div><b>"+child.createuser+"</b></div>                          " 
	                     +"             <div style='font-size: 15px;word-wrap:break-word'>"+child.content+"</div>               " 
	                     +"          </div>                                                                   " 
	                     +"        <div class='col-sm-2'>                                                   " 
	                     +"             <div>"+child.createdate+"</div>                                                  " 
	                    /* +" <div><a href='' class='btn btn-link'>回复</a></div> " */ 
	                     +"         </div>                                                                   " 
	                     +"   </div>                                                                       " ;
	                   
	                     });
	                  
	                     htmlStr+=" </div>                                                                           " ;
	                 }
	                                                                               
	                         
					 htmlStr+=   "<div class='col-xs-12' style='margin-top: 100px;display:none;' id='"+field.id+"'>"
	                         +"<div class='col-xs-12 padding-left-right-none'>"
	                         +"  <input class='form-control' placeholder='回复"+field.createuser+"：'  id='input"+field.id+"'  name='input"+field.id+"'   value='' maxlength='100'/>"
	                         +"  <input type='hidden' id='resourceid_"+field.id+"' value='"+field.resourceid+"'/>"
	                         +"  <input type='hidden' id='type_"+field.id+"' value='"+field.type+"'/>"
	                         +"</div>"
	                         +"<div class='col-xs-12 padding-left-right-none text-right'>"
	                         +" <span id='spaninput"+field.id+"'>0<span>/100"
	                         +"  </div>"
	                         +" <div class='form-inline' style='margin-top: 25px'>"
	                       /*
							 * +" <div class='col-xs-4' style='margin-left:
							 * -16px'><input class='form-control'
							 * placeholder='账号'></div>" +" <div
							 * class='col-xs-6'><input class='form-control'
							 * placeholder='密码'></div>"
							 */
	                         +"     <div class='col-xs-2' style='margin-bottom: 16px'><a class='btn btn-primary' onclick='submitComment("+field.id+")'>回复</a></div>"
	                         +" </div>"
	                         +" </div>"
	                     +" </div>"
	             +"</div>";
			    
				 });  

			
			 $("#commentBody").append(htmlStr);
			 
			 $('input[name^=input]').keyup(function(){
				 var id=$(this).attr("id");
				 var length = $(this).val().length;
				 $('#span'+id).html(length+"/100");
			 });  
		}
	});
	
	
	// 获取评论分页标签
    getPagenation(pageIndex,totalnums,page.pageSize);
 }
 
 
 // 评论分页start
 var getPagenation = function(pageIndex,total,pageSize) {
	
 				
 				/** *****************************************分页条******************************************** */
 				var pageTotal;
 				if (total % pageSize == 0) {
 					pageTotal = total / pageSize;
 				} else {
 					pageTotal = Math.floor(total / pageSize) + 1;
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
 					
 					var pagination = "";
 					pagination += "<div class=\"col-xs-12 text-right margin-top-20\">";
 					pagination += "<nav><ul class=\"pagination\">";
 					// 如果当前页大于1，显示上一页按钮
 					if (pageIndex > 1) {
 						pagination += "<li><a onclick=\"onActiveSelectPage(1)\" aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
 						pagination += "<li><a onclick=\"onActiveSelectPage(" + (pageIndex - 1) + ")\">上一页<span class=\"sr-only\">(current)</span></a></li>";
 					} else {
 						pagination += "<li class=\"disabled\"><a aria-label=\"Previous\"><span aria-hidden=\"true\">第一页</span></a></li>";
 						pagination += "<li class=\"disabled\"><a >上一页<span class=\"sr-only\">(current)</span></a></li>";
 					}
 					
 					
 					// 中间页
 					for (var i = begin; i <= end; i++) {
 						if (i == pageIndex) {
 							pagination += "<li class=\"active\"><a>" + i + "<span class=\"sr-only\">(current)</span></a></li>";
 						} else {
 							pagination += "<li><a onclick=\"onActiveSelectPage(" + i + ")\">" + i + "<span class=\"sr-only\">(current)</span></a></li>";
 						}
 					}
 					// 如果当前页小于总页数，显示下一页按钮
 					if (pageIndex < pageTotal) {
 						pagination += "<li><a onclick=\"onActiveSelectPage(" + (pageIndex + 1) + ")\">下一页<span class=\"sr-only\">(current)</span></a></li>";
 						pagination += "<li onclick=\"onActiveSelectPage(" + pageTotal + ")\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
 					} else {
 						pagination += "<li class=\"disabled\"><a>下一页<span class=\"sr-only\">(current)</span></a></li>";
 						pagination += "<li class=\"disabled\"><a>最后一页<span class=\"sr-only\">(current)</span></a></li>";
 					}
 					pagination += "</ul></nav></div>";
 					
 					
 				}
 				;
 				
 				
 				/** ********************************************************************************** */
 			$("#review_user_pagination").html(pagination);
 }

 
 var onActiveSelectPage = function(pageIndex) {
	 commentList(pageIndex);
	};
 
 // 查询笔记
 var getNoteList=function(){
	 $("#my-note-content-list").html("");
	 var data = {
			 worksid : workid,
			 floder : floder,
			 curriculumid : curriculumid
			};
	 $.ajax({
 		type : "POST",
 		data : JSON.stringify(data),
 		async : false,
 		url : "/study/gateway/selectNoteByWhere.do",
 		dataType : "json",
 		contentType : 'application/json; charset=UTF-8',
 		success : function(data) {
 			if(data.data.length >0){
 				var noteview="";
 				$.each(data.data, function(i, note) {
					noteview += "<div class='comment-detail'><i class='icon-access-time'></i>&nbsp;";
					noteview += "<span style='color: #5e5e5e'>"+note.videotime+"</span>";
					noteview += "<span hidden='true'>"+note.id+"</span>";
					noteview += "<article contenteditable='true'>"+note.content+"</article>";
				// noteview += "<span class='collapseAll'>缩起全部<i
				// class='icon-arrow-drop-down'></i></span>";
					noteview += "</div>";
				});
 				$("#my-note-content-list").html(noteview);
 			}else{
// addMessage("success", "暂无笔记");
 			};
 			$(".comment-detail").hover(function(){
 		        console.log($(this).find("i"));
 		        if($(this).find("i").hasClass("icon-arrow-drop-up")||$(this).find("i").hasClass("icon-arrow-drop-down")){
 		            $(this).find("i").not(":eq(0)").not(":last").remove();
 		            $(this).find("span:eq(0)").after('<i class="icon-delete blue-color pull-right"></i><i class="icon-create blue-color pull-right"></i>');
 		        }else{
 		            $(this).find("i").not(":eq(0)").remove();
 		            $(this).find("span:eq(0)").after('<i class="icon-delete blue-color pull-right"></i><i class="icon-create blue-color pull-right"></i>');
 		        }
 		    },function(){
 		        if($(this).find("i").hasClass("icon-arrow-drop-up")||$(this).find("i").hasClass("icon-arrow-drop-down")){
 		            $(this).find("i").not(":eq(0)").not(":last").remove();
 		        }else{
 		            $(this).find("i").not(":eq(0)").remove();
 		        }
 		    });
 			 $(".my-note-box .comment-detail article").each(function(){
 	            if($(this).height()>40){
 	                $(this).after('<span class="collapseAll">展开全部<i class="icon-arrow-drop-down"></i></span>');
 	            }
 	        });
 		},
		error : function(data) {
// addMessage("error", data);
		}
 	});
	 
 }
 
 var updateNote=function(id,content){
	 var data={
			 content:content,
			 id : id
	 }
	 $.ajax({
	 		type : "POST",
	 		data : JSON.stringify(data),
	 		async : false,
	 		url : "/study/rest/curriculumDetails/updateNote.do",
	 		dataType : "json",
	 		contentType : 'application/json; charset=UTF-8',
	 		success : function(data) {
	 			getNoteList();
	 			addMessage("success", "修改成功！");
	 		},
			error : function(data) {
				addMessage("success", "修改失败！");
// addMessage("error", data);
			}
	 	});
 }
 // 删除笔记
 var deleteNote=function(id){
	 var data={
			 id : id
	 }
	 $.ajax({
	 		type : "POST",
	 		data : JSON.stringify(data),
	 		async : false,
	 		url : "/study/rest/curriculumDetails/deleteNote.do",
	 		dataType : "json",
	 		contentType : 'application/json; charset=UTF-8',
	 		success : function(data) {
	 			addMessage("success", "删除成功！");
	 		},
			error : function(data) {
				addMessage("success", "修改失败！");
// addMessage("error", data);
			}
	 	});
 }

//查看视频
 var getCourseDetail=function(){
	 var data={
			 curriculumid : curriculumid,
			 floder : floder,
			 workid : workid,
			 source : source
	 }
	 $.ajax({
	 		type : "POST",
	 		data : JSON.stringify(data),
	 		async : false,
	 		url : "/study/gateway/detail.do",
	 		dataType : "json",
	 		contentType : 'application/json; charset=UTF-8',
	 		success : function(data) {
	 			//resourceid=data.data[0].resourceid;
	 			var videohtml="";
	 			var id = window.location.search+"";id = id.substring(id.indexOf("=")+1);
	 			if(typedata == '1'){
	 				if(data.data[0] != null && data.data[0] != undefined){
	 					if(data.data[0].map == '0'){
	 						videohtml+="<div style='width:100%;height:100%;background-color: black;'><span style='color: blue;font-size: 500%;position: absolute;left: 450px;top: 200px;'>课程结束</span></div>";
	 					}else{
	 						videohtml +="<iframe style='border: none; width: 100%; height: 100%;' src=\"/study/static/resource/playlive.html?list="+data.data[0].map+"\"></iframe>";
	 					}
	 				}else{
	 					videohtml +="<iframe style='border: none; width: 100%; height: 100%;' src=\"/study/static/resource/playlive.html?list=\"></iframe>";
	 				}
	 				window.rtmpLivingPlayerLogs=function(){
	 					if(arguments[0]=="volume"){
	 						//音量
	 						play(typedata, id, arguments[1]);
	 					}else if(arguments[0]=="fullScreen"){
	 						var flag = arguments[1]==1?true:false;
	 						fullscreen(typedata, id, flag);
	 					}
	 				}
	 			}else if(typedata == '2' || typedata == '3'){
					videohtml +="<iframe class=\"miframe\" scrolling=\"no\" allowFullScreen style='border:none;width:100%;height:100%;' src=\"/study/static/resource/zkplayer/zkplayer.html\"></iframe><script>requestFullScreen=function(){var docElm = document.documentElement;if(docElm.requestFullscreen){docElm.requestFullscreen();}else if(docElm.msRequestFullscreen){docElm.msRequestFullscreen();}else if(docElm.mozRequestFullScreen){docElm.mozRequestFullScreen();}else if(docElm.webkitRequestFullScreen){docElm.webkitRequestFullScreen();}};exitFullscreen=function(){if(document.exitFullscreen){document.exitFullscreen();}else if(document.msExitFullscreen){document.msExitFullscreen();}else if(document.mozCancelFullScreen){document.mozCancelFullScreen();}else if(document.webkitCancelFullScreen){document.webkitCancelFullScreen();}};IframeOnLoad=function(){"
					+"document.getElementsByClassName('miframe')[0].contentWindow.zk_player.callback="
					+"function(){var id='"+id+"',typedata='"+typedata+"';switch(arguments[0]){case 'play':play(typedata, id, arguments[1]);break;case 'pause':pause(typedata, id, arguments[1]);break;case 'volume':changeVolume(typedata, id, arguments[1]);break;case 'seek':seek(typedata, id, arguments[1],arguments[2]);break;case 'fullscreen':fullscreen(typedata, id, arguments[1],arguments[2]);break;case 'mute':mute(typedata, id, arguments[1],arguments[2]);break;}};"
					//+"function(){var id='"+id+"',typedata='"+typedata+"';alert(id+typedata)}" 
					//+"switch(arguments[0]){case 'play':play(typedata, id, arguments[1]);break;case 'pause':pause(typedata, id, arguments[1]);break;case 'volume':changeVolume(typedata, id, arguments[1]);break;case 'seek':seek(typedata, id, arguments[1],arguments[2]);break;case 'fullscreen':fullscreen(typedata, id, arguments[1],arguments[2]);break;case 'mute':mute(typedata, id, arguments[1],arguments[2]);break;}}"
					//+"function(){var id='"+id+"';alert(id)};"
					+"var obj=JSON.parse('"+data.data[0].map+"'),ar=[];for(var i=0;i<5;i++){if(obj['video'+i]&&obj['video'+i+'times']){obj['video'+i].push(obj['video'+i+'times']);ar.push(obj['video'+i])};}document.getElementsByClassName('miframe')[0].contentWindow.initvideo(ar);}</script>"
	 			}
	 			$(".videoBroadcastBox").html(videohtml);
	 			
	 			
	 			var id = window.location.search+"";
	 			id = id.substring(id.indexOf("=")+1);
/*	 			var videoLogs=function(){
	 				//var id='123';
	 				switch(arguments[0]){
	 				case 'play':play(typedata, id, arguments[1]);break;
	 				case 'pause':pause(typedata, id, arguments[1]);break;
	 				case 'volume':changeVolume(typedata, id, arguments[1]);break;
	 				case 'seek':seek(typedata, id, arguments[1],arguments[2]);break;
	 				case 'fullscreen':fullscreen(typedata, id, arguments[1],arguments[2]);break;
	 				case 'mute':mute(typedata, id, arguments[1],arguments[2]);break;
	 				}
	 			}
	 			$('.miframe')[0].contentWindow.zk_player.callback=videoLogs;*/
	 			//统计
	 			courseDetail(typedata,id);
	 			//心跳
	 				var data = {"sort":"2","type":typedata,"id":id,"loginname":loginname,"visitor":visitor};
	 				intervalProcess = setInterval(ajax,30000,data);
	 		},
			error : function(data) {
			}
	 	});
 }

 // 查看评审明细
var selectReviewDetails = function(id) {
	var data = {
		source : typedata,
		 worksid : workid,
		floder : floder,
		curriculumid : curriculumid
	}
	$.ajax({
		type : "POST",
		data : JSON.stringify(data),
		async : false,
		url : "/study/gateway/selectReviewDetails.do",
		dataType : "json",
		contentType : 'application/json; charset=UTF-8',
		success : function(data) {
			console.log("评审明细" + data.ReviewTemplate);
			if(data.templateid != "" && data.templateid != null){
//				$("#templateid").html(data.templateid);
				templateid=data.templateid;
				$("#remark").html(data.remark);
			var reviewdetailhtml = "";
			// 循环map
			$.each(data.ReviewTemplate, function(key, values) {
				
				console.log(data.content);
				$(values).each(function(i) {
// console.log("第几行："+i);
					if(i=='0'){
						reviewdetailhtml += "<tr>";
						reviewdetailhtml +=	"<td rowspan="+values.length+">"+key+"</td>";
						reviewdetailhtml +="<td class='text-left'>"+this.childKey+"</td>";
						reviewdetailhtml +="<td>"+this.childValue+"</td>";
						reviewdetailhtml +="<td><input type='text' class='score-num' id="+this.id+"></td>";
						reviewdetailhtml += "</tr>";
					}else{
						reviewdetailhtml += "<tr>";
						reviewdetailhtml +="<td class='text-left'>"+this.childKey+"</td>";
						reviewdetailhtml +="<td>"+this.childValue+"</td>";
						reviewdetailhtml +="<td><input type='text' class='score-num' id="+this.id+"></td>";
						reviewdetailhtml += "</tr>";
					}
					
// console.log("\t" + this.childKey);
// console.log("\t" + this.childValue);
				});
			
			});
			
			if(data.assess=='1'){
				$("#assess").show();
			}
			$("#reviewdetail").html(reviewdetailhtml);
			// 循环分数
			$.each(data.content, function(i, values) {
				$("#"+values.id).val(values.score);
			});
// $("#score").find("strong").html(data.score);
			$("#score").html("<strong>"+data.score+"</strong>/"+data.sumScore+"分");
			
			var optionhtml="";
			$(data.templatename).each(function(i,val) {
				optionhtml += "<option id="+val.id+">"+val.name+"</option>";
			});
			$("#templatename").html(optionhtml);
			$(".score-num").blur( function () {
				$("#savetReviewDetails").removeAttr("disabled");
			var score=	$(this).parent().prev().text();
			var myscore=this.value;
			if(parseInt(myscore) < '0'){
				alert('请输入大于零的整数!');
				$("#savetReviewDetails").attr("disabled","disabled");
				return;
			}
			if(parseInt(myscore) >parseInt(score) ){
				alert('大于标准分数');
				$("#savetReviewDetails").attr("disabled","disabled");
				return;
			}
			 var   type="^\\d+$"; 
		     var   re   =   new   RegExp(type); 
		       if(myscore.match(re)==null) 
		        { 
		         alert( "请输入正整数!"); 
		         $("#savetReviewDetails").attr("disabled","disabled");
		        return;
		        }
			var myscorenum=0;
			$(".score-num").each(function(i,s) {
				console.log(s.value);
				if(s.value != ''){
					myscorenum+=parseInt(s.value);
				}
				$("#score").html("<strong>"+myscorenum+"</strong>/"+data.sumScore+"分");
			});
			
			} );
		 }else{
			 // 下拉框里赋值
			 var optionhtml="<option>---请选择---</option>";
			// 循环map
				$.each(data.ReviewTemplate, function(key, values) {
					
					$(values).each(function(i) {
// var
						optionhtml += "<option value="+this.id+">"+this.name+"</option>";
					});
				
				});
				$("#templatename").html(optionhtml);
		 }
		},
		error : function(data) {
// addMessage("error", data);
		}
	});
}
 
 function changeHuiFuDailog(id){
		var stylestr = $("#"+id).attr("style");
		var status = stylestr.indexOf("display:none"); 
		if(status>0){
			$("#"+id).attr("style","margin-top: 50px;display:block;");
		}else{
			$("#"+id).attr("style","margin-top: 50px;display:none;");
		}
 }

 // 针对prenti=0的评论进行评论提交操作
function submitComment(id){
	var isLogin = getUser();
	if(isLogin){
	var content = $("#input"+id).val();
	if(content!=""){
		var data = {
				content:content,
				parentid : id,
				resourceid:$("#resourceid_"+id).val(),
				type:$("#type_"+id).val()
    		};
    	$.ajax({
    		type : "POST",
    		data : JSON.stringify(data),
    		async : false,
    		url : "/study/rest/curriculumDetails/addComment.do",
    		dataType : "json",
    		contentType : 'application/json; charset=UTF-8',
    		success : function(data) {
    			selectCommentCount();
    			commentList();
    			
    			addMessage("success","发表评论成功！");
    		
    			console.log(data);
    		}
    	});
		
	}else{
		addMessage("error","评论不允许为空！");
	}
	}else{
		// 该路径需要进行配置修改，暂时这样实现
		var url ="login";
		
		window.open(url,"_blank","status=yes,scrollbars,resizalbe=yes");

		// window.location.href=url;
	}
}
 
// 针对资源进行评论，及新增parentid=0的一条评论记录
function submitComment1(){
	var isLogin = getUser();
	if(isLogin){
		var content = $("#oneLevelComment").val();
		var score = getScore("scoreMenu");
		if(content!=""){
			var data = {
					//resourceid:id,
					resourceid:com_id,
					content:content,
					parentid : 0,
					//type:$("#type").val(),
					type:typedata,
					score:score
	    		};
	    	$.ajax({
	    		type : "POST",
	    		data : JSON.stringify(data),
	    		async : false,
	    		url : "/study/rest/curriculumDetails/addComment.do",
	    		dataType : "json",
	    		contentType : 'application/json; charset=UTF-8',
	    		success : function(data) {
	    			$("#oneLevelComment").val("");
	    			scoreShow("scoreMenu",1,"on");
	    			commentList();
	    			selectCommentCount();
	    			addMessage("success","发表评论成功！");
	    			 $('#result').html('0');
	    			console.log(data);
	    		}
	    	});
		}else{
			addMessage("error","评论不允许为空！");
		}
	}else{
		// 该路径需要进行配置修改，暂时这样实现
//		var url ="/study/login";
//		window.open(url,"_blank","status=yes,scrollbars,resizalbe=yes");
		// window.location.href=url;
		window.location = "/study/login";
		return;
	}

}
 

var getUser = function() {
	var isLogin;
	$.ajax({
		url : "/study/gateway/getUser",
		type : "POST",
		async : false,
		dataType : "json",
		success : function(data){
			if(data==null){
				isLogin=false;
			}else{
				isLogin= true;
			}
		}
	});
	return isLogin;
}
	


/**
 * 听课记录
 */
var selectRecordDetails = function() {
	var data = {
		source : typedata,
		worksid : workid,
		floder : floder,
		curriculumid : curriculumid
	}
	$.ajax({
		type : "POST",
		data : JSON.stringify(data),
		async : false,
		url : "/study/gateway/selectRecordDetails.do",
		dataType : "json",
		contentType : 'application/json; charset=UTF-8',
		success : function(data) {
			if(data == null ){
				return;
			}
			var div="";
			recordtemplateid=data.templateid;
//			 $("#recordtemplateid").html(data.templateid);
			$.each(data.template,function(i,val){
				
				 div +="<div class='listen-note'>"+
                "<div class='title'><i class='icon-lens'></i> <span>"+val.childKey+"</span> | <span>"+val.childValue+"</span></div>"+
                "<textarea id="+val.id+" class='record-detail'></textarea></div>";
				
					
			});
			div += "<input type='button'   value='保存评价' class='btn btn-primary pull-right btn-bg-blue margin-top-20' id='savetRecordDetails'/>";
			/* $(".listenWrap").append(div); */
			$(".listenWrap").html(div);
			
			// 循环分数
			$.each(data.content, function(i, values) {
				var score="";
				console.log(values.id);
				console.log(values.score);
				if(values.score != ""){
					score=values.score.replace(/<br>/gim,"\r\n")
				}
				$("#"+values.id).val(score);
			});
			/*
			 * $(".listenWrap").children(function (i){
			 * $(".listenWrap").append(div); });
			 */
		    	// 保存听课评价
		    	$("#savetRecordDetails").click(function(){
		    		if (currentUser == null || currentUser == undefined) {
		    			window.location = "/study/login";
		    			return;
		    		}
		    		var obj ="[";
		    		$.each($(".courseViewContent .record-detail"),function(i,datavalue){
		    			 obj = obj+"{";
		    			obj = obj + '"id":"' + datavalue.id + '",';
		    			obj = obj + '"score":"' + datavalue.value + '"';
		    			obj = obj + "},";
		    		});
		    		obj=obj.substring(0, obj.length-1)+ "]";
		    		var data = {
		    			recordtemplateid:recordtemplateid,
						 worksid : workid,
						 floder : floder,
						 curriculumid : curriculumid,
						 type:typedata,
						 content:obj
						};
		    		$.ajax({
		    	 		type : "POST",
		    	 		data : JSON.stringify(data),
		    	 		async : false,
		    	 		url : "/study/rest/curriculumDetails/savetRecordDetails.do",
		    	 		dataType : "json",
		    	 		contentType : 'application/json; charset=UTF-8',
		    	 		success : function(data) {
		    	 			addMessage("success", "听课评价保存成功！");
		    	 			selectRecordDetails();
		    	 		},
		    			error : function(data) {
// addMessage("error", data);
		    			}
		    	 	});
		    	});
		},
		error : function(data) {
// addMessage("error", data);
		}
	});	
}

// 获取全部评论条数
/*
 * var queryCommentNums = function(){ var data = { typedata: $("#type").val(),
 * resourceid: $("#resourceid").val() }; $.ajax({ type : "GET", data : data,
 * async : false, url : "../rest/curriculumDetails/queryCommentNums.do",
 * dataType : "json", contentType : 'application/json; charset=UTF-8', success :
 * function(data) { $("#commentNums").text(data.nums);
 * $("#totalnums").val(data.nums); }, error : function(data) { //
 * addMessage("error", data); } }); }
 */
// 评分控件添加点击事件，当点击评分星星的时候，先将所有的得小星星变灰色，然后再讲点击之前的的所有小星星都置为黄色
var scoreMenuEvent = function(){
	var value=1;
	$("#scoreMenu > i").click(function(){
		value = $(this).attr("value");
		scoreShow("scoreMenu",value,"on");
	}); 
}


// 根据value值的大小，将value值之前的小星星全部设置为统一的颜色，，spanId:分数控件里面的spanid，value：小星星的排序号，type：on||off
// 变黄色||不变黄
var scoreShow = function(spanId,value,type){
	if(type=="on"){
		scoreShow(spanId,5,"off");
		$("#"+spanId+" > i").each(function(index,obj){
			if(index<value){
					$(obj).attr("class","icon-star yellow-color");	
			}else{
				return;
			}
		});
	}else if(type=="off"){
		$("#"+spanId+" > i").each(function(index,obj){
			if(index<value){
					$(obj).attr("class","icon-star gray-color");	
			}else{
				return;
			}
		});
	}
}

// 根据小星星的颜色，来获取分数
var getScore = function(spanId){
	var score = $("#"+spanId+" > i[class='icon-star yellow-color']").length;
	return score;
}
/**
 * 资源列表树
 */
var getTrees = function(){
	$.ajax({
		type : "POST",
// data : JSON.stringify(data),
		async : false,
		url : "/study/rest/curriculumDetails/getTrees.do",
		dataType : "json",
		contentType : 'application/json; charset=UTF-8',
		success : function(data) {
			if(data.length>0 && data[0] != undefined){
				var div="";
				$.each(data[0].nodes,function(i,val){
					
					div += "<li class='recordList' onclick='changerecordList(this)' id="+val.resource_uuid+"><i class='icon-play-arrow'></i><i class='icon-Folder'></i>"+val.title+"</li>";
					
						
				});
				$(".selectVideoFileContent-import").find("ul").append(div);
			}
			
		},
		error : function(data) {
// addMessage("error", data);
		}
	});
}

function changerecordList (param){
	$(".recordList").each(function(index,element){
		if($(this).hasClass("styleRcordlist")){
			$(this).removeClass("styleRcordlist");
		}
	});
// var name=param.parentNode.children[0].className;
	var name = param.className;
	name += " styleRcordlist";
	param.className=name;
}
/**
 * 导入素材
 */
function importMaterial(){
	console.log($(".styleRcordlist"));
	var data = {
			resource_uuid : $(".styleRcordlist").attr("id"),
			worksid : workid,
			floder : floder,
			curriculumid : curriculumid,
			type:typedata
		}
	$.ajax({
		type : "POST",
		data : JSON.stringify(data),
		async : false,
		url : "/study/rest/curriculumDetails/importMaterial.do",
		dataType : "json",
		contentType : 'application/json; charset=UTF-8',
		success : function(data) {
			$(".file-box").hide();
			selectMaterialList();
		},
		error : function(data) {
// addMessage("error", data);
		}
	});
}
var showSelectFile = function(index){
	if(index == 1){
		$("#film_btn").addClass("cur");
		$("#upload_btn").removeClass("cur");
		$("#select_film").show();
		$("#select_upload").hide();
	}else if(index == 2){
		$("#film_btn").removeClass("cur");
		$("#upload_btn").addClass("cur");
		$("#select_film").hide();
		$("#select_upload").show();
		$("#file").val('');
	}else{
		$("#film_btn").addClass("cur");
		$("#upload_btn").removeClass("cur");
		$("#select_film").show();
		$("#select_upload").hide();
	}
};
var j=0;
function uploadOnClick() {
	j++;
	if(j == 1){
	if (currentUser == null || currentUser == undefined) {
		window.location = "/study/login";
		return;
	}
	var fileName = $("#file").val();
	if ("" == fileName) {
		alert("请选择需要上传的文件!");
		return;
	}
	$("#worksidMaterial").val(workid);
	$("#floderMaterial").val(floder);
	$("#curriculumidMaterial").val(curriculumid);
	$("#typeMaterial").val(typedata);
	
	//
	var fileObj = document.getElementById("file").files[0]; // 获取文件对象

    var FileController = "/study/rest/curriculumDetails/uploadMaterial.do";                    // 接收上传文件的后台地址

    // FormData 对象
    var form = new FormData();

    form.append("worksidMaterial", workid);                        // 可以增加表单数据
    form.append("floderMaterial", floder);
    form.append("curriculumidMaterial", curriculumid);
    form.append("typeMaterial", typedata);

    form.append("file", fileObj);                           // 文件对象

    // XMLHttpRequest 对象

    var xhr = new XMLHttpRequest();

    xhr.open("post", FileController, true);

    xhr.onload = function () {

    	addMessage("success", "上传成功！");
        $(".file-box").hide();
    	parent.selectMaterialList();
    	j=0;
    };
    xhr.upload.addEventListener("progress", progressFunction, false)
    xhr.send(form);
	}
}
function progressFunction(evt) {

    var progressBar = document.getElementById("progressBar");

    var percentageDiv = document.getElementById("percentage");

    if (evt.lengthComputable) {

        progressBar.max = evt.total;

        progressBar.value = evt.loaded;

        percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";

    }

}  
/**
 * 课程素材
 */
var selectMaterialList = function (){
	var data = {
			worksid : workid,
			floder : floder,
			curriculumid : curriculumid
			};
	$.ajax({
 		type : "POST",
 		data : JSON.stringify(data),
 		async : false,
 		url : "/study/gateway/selectMaterialList.do",
 		dataType : "json",
 		contentType : 'application/json; charset=UTF-8',
 		success : function(data) {
 			if(data.data.length >0){
 				var noteview="";
 				$.each(data.data, function(i, note) {
					noteview +="<tr>";
					noteview +="<td>"+note.name+"</td>";
					noteview +="<td id='materialid' hidden='hidden'>"+note.id+"</td>";
					noteview +="<td><input class='materialsort' type='text' value="+note.sort+"></td>";
					noteview +="<td>";
					/*
					 * noteview +="<div class='bootstrap-switch
					 * bootstrap-switch-wrapper
					 * bootstrap-switch-id-switch-offColor
					 * bootstrap-switch-animate bootstrap-switch-on'
					 * style='width: 98px;'>"; noteview += "<div
					 * class='bootstrap-switch-container' style='width:96px;
					 * margin-left: 0px;border:1px #27a7ec solid'
					 * onclick='allowDownloadMaterial("+note.id+")'>"; noteview += "
					 * <input id='switch-offColor' type='checkbox'
					 * data-off-color='warning'>"; noteview += "</div> </div>";
					 */
					noteview +="<div class='btn-group' data-toggle='buttons' onclick='allowDownloadMaterial("+note.id+")'>";
					if(note.flag == '1'){
						noteview +=" <label class='btn btn-default active' style='background:orange;color:#fff;'><input type='radio' name='options' id='option1' autocomplete='off'>on</label>";
						noteview +="<label class='btn btn-default' ><input type='radio' name='options' id='option2' autocomplete='off'>off</label>";
					}else{
						noteview += "<label class='btn btn-default'><input type='radio' name='options' id='option1' autocomplete='off'>on</label>";
						noteview +="<label class='btn btn-default' style='background:orange;color:#fff;'><input type='radio' name='options' id='option2' autocomplete='off'>off</label>";
					}
					noteview +=		"</div></td><td><i class='icon-delete' onclick='deleteMaterial("+note.id+")'></i></td></tr>";
				});
 				$("#MaterialListhtml").html(noteview);
 				$(".materialsort").blur(function(){
// $(this).val();
// $(this).parent().parent().find("td[id='materialid']").val();
 		    		var data={
 		    				sort:$(this).val(),
 		    				id:$(this).parent().parent().find("td[id='materialid']").text()
 		    		};
 		    		$.ajax({
 		    	 		type : "POST",
 		    	 		data : JSON.stringify(data),
 		    	 		async : false,
 		    	 		url : "/study/rest/curriculumDetails/updateMaterial.do",
 		    	 		dataType : "json",
 		    	 		contentType : 'application/json; charset=UTF-8',
 		    	 		success : function(data) {
 		    	 			addMessage("success", "修改成功！");
 		    	 			selectMaterialList();
 		    	 		},
 		    			error : function(data) {
// addMessage("error", data);
 		    			}
 		    	 	});
 		    	});
 		    	
 			}else{
// addMessage("success", "暂无笔记");
 				var noteview="";
 				$("#MaterialListhtml").html(noteview);
 			};
 			
 		},
		error : function(data) {
// addMessage("error", data);
		}
 	});
}

/**
 * 课程素材在线阅读
 */
var selectMaterialOnlineRead = function (){
	var data = {
			worksid : workid,
			floder : floder,
			curriculumid : curriculumid
			};
	$.ajax({
 		type : "POST",
 		data : JSON.stringify(data),
 		async : false,
 		url : "/study/gateway/selectMaterialList.do",
 		dataType : "json",
 		contentType : 'application/json; charset=UTF-8',
 		success : function(data) {
 			if(data.data.length >0){
 				var noteviewone="";
 				$.each(data.data, function(i, note) {
 					if(i==0){
 						noteviewone +="<div class='other-resource-tab'>";
 						noteviewone +="<table>";
 						noteviewone +="<tr><td>"
 						noteviewone +="<i class='icon-"+note.nametype+"'></i> "+note.name+"</td>";
 	 					noteviewone +=" <td align='right'>" 
 	 					noteviewone +="<span class='load'><a href='/study/gateway/down?path="+note.fileurl+"&name="+note.name+"'><i class='icon-my-download'></i>下载</a></span>";
 	 					if(note.nametype=="PDF" || note.nametype=="Word"||note.nametype=="PowerPoint"||note.nametype=="Excel"){
 	 						noteviewone +=		"<span class='readone'><a style='color: #ffab2c;'><i class='icon-read'></i>在线阅读</a></span>" ;
 	 	 					
 	 	 					}
 	 					noteviewone +="  </td></tr>";
 	 					noteviewone +=" <tr>";
 	 					noteviewone +=" <td colspan='2'>";
 	 					noteviewone+="<div class='bookContent'>" +
 	 							"<a id='viewerPlaceHolder' style='width:886px;height:755px;display:block'></a>" +
 	 							"</div>";
 	 					noteviewone +="  </td></tr></table></div>";
				
 				$(".book-relative").html(noteviewone);
 				if(note.nametype=="PDF" || note.nametype=="Word"||note.nametype=="PowerPoint"||note.nametype=="Excel"){
 				var fp=new FlexPaperViewer( 
 				         '/study/static/js/curriculum/flexpaper/FlexPaperViewer',         
 				         'viewerPlaceHolder', {  
 				             config : {// new FlexPaperView要传三个参数
										// 这里的是FlexPaperViewer,viewerPlaceHolder，和config
 				                 SwfFile : escape('/data/transfer/'+note.transferurl),// 需要Flex打开的文档，但是我发现没有FlexPaperViewer的时候就不恩能够运行。
 				                 Scale : 1, // 缩放的意思
 				                 ZoomTransition : 'easeOut',// 缩放样式的选择
 				                 ZoomTime : 0.5 	,// 缩放使用的时间
 				                 ZoomInterval : 0.2,// 缩放比例之间间隔，默认值为0.1，该值为正数。
													// 神马意思？？？
 				                 FitPageOnLoad : false,// 自适应页面，工具栏上有
 				                 FitWidthOnLoad : true,// 自适应宽度，工具栏上有
 				                 FullScreenAsMaxWindow : false,// 如果设置为true的时候，点击全屏并不是全屏而是一个新页面，据说独立的flex播放的时候用这个比较合适
 				                 ProgressiveLoading : true,// true的话不全部加载文档，边看边加载
 				                 MinZoomSize : 0.2,// 最大缩放比例
 				                 MaxZoomSize : 5,// 最小缩放比例
 				                 SearchMatchAll : false,// 为true的时候搜索的时候便会出现高亮
 				                 InitViewMode : 'Portrait',// 设置启动模式如"Portrait"
															// or "TwoPage"
															// 这句话什么意思？？？
 				                 // PrintPaperAsBitmap : false,
 				                 RenderingOrder:'flash,html',// 新加入
 				                 // PrintEnabled:true,
 				                 ViewModeToolsVisible : true,// 工具栏上是否显示样式选择框
 				                 ZoomToolsVisible : true,// 工具栏上是否显示缩放工具
 				                 NavToolsVisible : true,// 工具栏上是否显示导航工具
 				                 CursorToolsVisible : true,// 工具栏上是否显示光标工具
 				                 SearchToolsVisible : true,// 工具栏上是否显示搜索
 								 WMode:'transparent',
 				                 localeChain : 'zh_CN'// 设置语言
 				             }  
 				         });  
 						}else{
 							$("div[class='bookContent']").hide();
 						}
		 				if(note.flag == '0'){
		 					$(".load").hide();
		 				}else{
		 					$(".load").show();
		 				}
 					}
 				});
 		    	
 			}if(data.data.length >1){
 				var noteviewother="";
 				noteviewother +="<div class='title'><i class='icon-lens'></i>其他素材</div>";
 				noteviewother +="<div class='other-resource-tab'>";
 				noteviewother +="<table>";
 				$.each(data.data, function(i, noteother) {
 					if(i > 0){
 					noteviewother +="<tr>";
 					noteviewother +=" <td><i class='icon-"+noteother.nametype+"'></i>"+noteother.name+"</td>";
 					noteviewother +=" <td align='right'>" ;
 					
 					if(noteother.flag == '1'){
 						noteviewother +=		"<span class='load' ><a href='/study/gateway/down?path="+noteother.fileurl+"&name="+noteother.name+"'><i class='icon-my-download'></i>下载</a></span>";
 					}
 					if(noteother.nametype=="PDF" || noteother.nametype=="Word"||noteother.nametype=="PowerPoint"||noteother.nametype=="Excel"){
 	 					noteviewother +=		"<span class='read'><a><i class='icon-read'></i>在线阅读</a></span>" ;
 	 					
 	 					}
 					noteviewother +="  </td></tr>";
 					if(noteother.nametype=="PDF" || noteother.nametype=="Word"||noteother.nametype=="PowerPoint"||noteother.nametype=="Excel"){
 					noteviewother +=" <tr style='display:none;'>";
 					noteviewother +=" <td colspan='2'>";
 					noteviewother += "<div class='bookContent'>" +
 					"<a id='viewerPlaceHolder"+i+"' style='width:886px;height:755px;display:block'></a><span style='display:none;'>"+noteother.transferurl+"</span>" +
 							"</div>";
 					noteviewother +=" </td>";
 					noteviewother +=" </tr>";
 					
 					
 					}
 					}
 					
				});	

 				
 				noteviewother +="</table>";
 				noteviewother +="</div>";
 				$(".other-resource").html(noteviewother);
				}
 			
 			
 			else{
// addMessage("success", "暂无笔记");
 			};
 			
 		},
		error : function(data) {
// addMessage("error", data);
		}
 	});
}

/**
 * 
 * @param id
 */
function deleteMaterial(id){
	var data={
			id:id
	};
	$.ajax({
 		type : "POST",
 		data : JSON.stringify(data),
 		async : false,
 		url : "/study/rest/curriculumDetails/deleteMaterial.do",
 		dataType : "json",
 		contentType : 'application/json; charset=UTF-8',
 		success : function(data) {
 			addMessage("success", "删除成功！");
 			selectMaterialList();
 		},
		error : function(data) {
// addMessage("error", data);
		}
 	});
}
function allowDownloadMaterial(id){
	var data={
			id:id
	};
	$.ajax({
 		type : "POST",
 		data : JSON.stringify(data),
 		async : false,
 		url : "/study/rest/curriculumDetails/allowDownloadMaterial.do",
 		dataType : "json",
 		contentType : 'application/json; charset=UTF-8',
 		success : function(data) {
 			selectMaterialList();
 			addMessage("success", "修改成功！");
 		},
		error : function(data) {
// addMessage("error", data);
		}
 	});
}
function changetemplate(){
// alert($("#templatename").val());
	var data={
			templateid:$("#templatename").val()
	};
	$.ajax({
 		type : "POST",
 		data : JSON.stringify(data),
 		async : false,
 		url : "/study/gateway/selectReviewOptiondetail.do",
 		dataType : "json",
 		contentType : 'application/json; charset=UTF-8',
 		success : function(data) {
 			console.log("评审明细" + data.ReviewTemplate);
			if(data.templateid != "" && data.templateid != null){
//				$("#templateid").html(data.templateid);
				templateid=data.templateid;
// $("#remark").html(data.remark);
			var reviewdetailhtml = "";
			// 循环map
			$.each(data.ReviewTemplate, function(key, values) {
				
				console.log(data.content);
				$(values).each(function(i) {
// console.log("第几行："+i);
					if(i=='0'){
						reviewdetailhtml += "<tr>";
						reviewdetailhtml +=	"<td rowspan="+values.length+">"+key+"</td>";
						reviewdetailhtml +="<td class='text-left'>"+this.childKey+"</td>";
						reviewdetailhtml +="<td>"+this.childValue+"</td>";
						reviewdetailhtml +="<td><input type='text' class='score-num' id="+this.id+"></td>";
						reviewdetailhtml += "</tr>";
					}else{
						reviewdetailhtml += "<tr>";
						reviewdetailhtml +="<td class='text-left'>"+this.childKey+"</td>";
						reviewdetailhtml +="<td>"+this.childValue+"</td>";
						reviewdetailhtml +="<td><input type='text' class='score-num' id="+this.id+"></td>";
						reviewdetailhtml += "</tr>";
					}
					
// console.log("\t" + this.childKey);
// console.log("\t" + this.childValue);
				});
			
			});
			
			if(data.assess=='1'){
				$("#assess").show();
			}
			$("#reviewdetail").html(reviewdetailhtml);
			$(".score-num").blur( function () {
				$("#savetReviewDetails").removeAttr("disabled");
			var score=	$(this).parent().prev().text();
			var myscore=this.value;
			if(parseInt(myscore) < '0'){
				alert('分数错误');
				$("#savetReviewDetails").attr("disabled","disabled");
				return;
			}
			if(parseInt(myscore) >parseInt(score) ){
				alert('大于标准分数');
				$("#savetReviewDetails").attr("disabled","disabled");
				return;
			}
			 var   type="^\\d+$"; 
		     var   re   =   new   RegExp(type); 
		       if(myscore.match(re)==null) 
		        { 
		         alert( "请输入正整数!"); 
		         $("#savetReviewDetails").attr("disabled","disabled");
		        return;
		        }
			var myscorenum=0;
			$(".score-num").each(function(i,s) {
				console.log(s.value);
				if(s.value != ''){
					myscorenum+=parseInt(s.value);
				}
				$("#score").html("<strong>"+myscorenum+"</strong>/"+data.sumScore+"分");
			});
			} );
			// 循环分数
			/*
			 * $.each(data.content, function(i, values) {
			 * console.log(values.id); console.log(values.score);
			 * $("#"+values.id).val(values.score); });
			 */
// $("#score").find("strong").html(data.score);
// $("#score").html("<strong>"+data.score+"</strong>/"+data.sumScore+"分");
			
			/*
			 * var optionhtml=""; $(data.templatename).each(function(i,val) {
			 * optionhtml += "<option id="+val.id+">"+val.name+"</option>";
			 * }); $("#templatename").html(optionhtml);
			 */
		 }else{
			 // 下拉框里赋值
		 }
 		},
		error : function(data) {
// addMessage("error", data);
		}
 	});
}
// 查看评论的星星数和人数
function selectCommentCount(){
	var data = {
			worksid : workid,
			floder : floder,
			curriculumid : curriculumid,
			type:typedata
		}
	$.ajax({
		type : "POST",
		data : JSON.stringify(data),
		async : false,
		url : "/study/gateway/selectCommentCount.do",
		dataType : "json",
		contentType : 'application/json; charset=UTF-8',
		success : function(data) {
			var starshtml="";
			for(var i=0;i<data.totalscore;i++){
				starshtml += "<i class='icon-star yellow-color'></i>";
			}
			starshtml+= "<span style='margin:8px 0 0 16px;'>"+data.totalscore+"("+data.sumperson+"人)</span>";
			$(".stars").html(starshtml);
		},
		error : function(data) {
// addMessage("error", data);
		}
	});
}



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