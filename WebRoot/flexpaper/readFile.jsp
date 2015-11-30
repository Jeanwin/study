<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title>在线阅读</title>
<style type="text/css" media="screen">
html,body {
	height: 100%;
}

body {
	margin: 0;
	padding: 0;
	overflow: auto;
}

#flashContent {
	display: none;
}
</style>
<script type="text/javascript" src="js/jquery.js">
</script>
<script type="text/javascript" src="js/flexpaper_flash.js">
</script>
<script type="text/javascript" src="js/flexpaper_flash_debug.js">
</script>
</head>
<body>
	<div style="position:absolute;left:200px;top:10px;">
		<a id="viewerPlaceHolder" style="width:1000px;height:700px;display:block">
			</a>
<script type="text/javascript"> 
 			var fp=new FlexPaperViewer( 
         'FlexPaperViewer',         
         'viewerPlaceHolder', {  
             config : {//new FlexPaperView要传三个参数  这里的是FlexPaperViewer,viewerPlaceHolder，和config  
                 SwfFile : escape('/data/transfer/<%=(String) session.getAttribute("fileName")%>'),//需要Flex打开的文档，但是我发现没有FlexPaperViewer的时候就不恩能够运行。  
                 Scale : 1, //缩放的意思  
                 ZoomTransition : 'easeOut',//缩放样式的选择  
                 ZoomTime : 0.5,//缩放使用的时间  
                 ZoomInterval : 0.2,//缩放比例之间间隔，默认值为0.1，该值为正数。  神马意思？？？  
                 FitPageOnLoad : false,//自适应页面，工具栏上有  
                 FitWidthOnLoad : true,//自适应宽度，工具栏上有  
                 FullScreenAsMaxWindow : false,//如果设置为true的时候，点击全屏并不是全屏而是一个新页面，据说独立的flex播放的时候用这个比较合适  
                 ProgressiveLoading : true,//true的话不全部加载文档，边看边加载  
                 MinZoomSize : 0.2,//最大缩放比例  
                 MaxZoomSize : 5,//最小缩放比例  
                 SearchMatchAll : false,//为true的时候搜索的时候便会出现高亮  
                 InitViewMode : 'Portrait',//设置启动模式如"Portrait" or "TwoPage"  这句话什么意思？？？  
                 //PrintPaperAsBitmap : false,
                 RenderingOrder:'flash,html',//新加入  
                 //PrintEnabled:true,
                 ViewModeToolsVisible : true,//工具栏上是否显示样式选择框  
                 ZoomToolsVisible : true,//工具栏上是否显示缩放工具  
                 NavToolsVisible : true,//工具栏上是否显示导航工具  
                 CursorToolsVisible : true,//工具栏上是否显示光标工具  
                 SearchToolsVisible : true,//工具栏上是否显示搜索  
				 WMode:'transparent',
                 localeChain : 'zh_CN'//设置语言  
             }  
         });  
</script>
	</div>
</body>
</html>