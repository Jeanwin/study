<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="trees" type="java.lang.String" required="true" description="树"%>
	<ul id="treeTag"></ul>

<script type="text/javascript" src="${ctx }/static/js/jquery/jquery-1.9.1.min.js"></script>
	<c:if test="${trees !=null}"> 
	   <script language="javascript">
			var trees = eval("("+'${trees}'+")");
			var html = '';
			$.each(trees,function(i,data){
				if(data.nodes != null){
					html+='<li class="parent"><i class="icon-play-arrow"/>';
				}else{
					html+='<li><i class="icon-play-arrow"/>';
				}
				if(data.isfolder=='0'){
					html+='<i class="icon-Folder"></i>';
				}else{
					html+='<i id="'+data.id+'" class="icon-Recorder"></i>';
				}
				html+=data.title;
				if(data.nodes != null){
					html+=trans(data.nodes);
				}
				html+='</li>';
			});
			
			function trans(nodes){
				var htm = '<ul>';
				$.each(nodes,function(i,data){
					if(data.nodes != null){
						htm+='<li class="parent"><i class="icon-play-arrow"/>';
					}else{
						htm+='<li><i class="icon-play-arrow"/>';
					}
					if(data.isfolder=='0'){
						htm+='<i class="icon-Folder"></i>';
					}else{
						htm+='<i id="'+data.id+'" class="icon-Recorder"></i>';
					}
					htm+=data.title;
					if(data.nodes != null){
						htm+=trans(data.nodes);
					}
					htm+='</li>';
				});
				htm+='</ul>';
				return htm;
			}
			$("#treeTag").append(html);
			$("#block").show();
			$("li").click(function(event){
				if($(this).children("i[class='icon-Recorder']").size()>0){
					if($(this).children("i[class='icon-done']").size()>0){
						$(this).children("i[class='icon-done']").remove();
					}else{
						$(this).parent().children("li").find("i[class='icon-done']").remove();
						$(this).children("i[class='icon-play-arrow']").after('</i><i class="icon-done">');
					}
					event.stopPropagation();
					return;
				}
				var child = $(this).children("ul");
				if(child.is(":hidden"))
					child.show();
				else{
					child.hide();
					$(this).find("i[class='icon-done']").remove();
				}
				event.stopPropagation();
			});
	   </script>
	</c:if>		