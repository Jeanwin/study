<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head lang="en">
<%@ include file="/WEB-INF/views/include/mobileHead.jsp"%>
    <title>公告列表</title>
</head>
<body class="bg-gray">
<header id="header">
    <div class="back"><a href="index.html"><i class="icon-chevron-left"></i></a></div>
    <div class="iconBar"><i class="icon-format-list-bulleted" id="list" onclick="aaa()"></i><i class="icon-close" id="close" onclick="bbb()" style="display: none;"></i></div>
    <nav id="nav" style="display: none;">
        <a  href="liveCourses.html">直播</a>
        <a  href="videoDemand.html">点播</a>
        <a  href="collection.html">收藏</a>
        <a  href="personalCenter.html">个人中心</a>
        <a  href="information.html">公告</a>
    </nav>
    <script>
        function aaa(){
            var nav=document.getElementById("nav");
            var list=document.getElementById("list");
            var close=document.getElementById("close");
            nav.style.display="block";
            list.style.display="none";
            close.style.display="block";
        }
        function bbb(){
            var nav=document.getElementById("nav");
            var list=document.getElementById("list");
            var close=document.getElementById("close");
            nav.style.display="none";
            list.style.display="block";
            close.style.display="none";
        }
    </script>
    公告
</header>
<div class="infoPage">
    <div class="infoList">
        <a href="infoDetail.html">
            <div class="infoTime">1小时前<i class="icon-lens"></i></div>
            <h3>武汉市中小学生建筑模型竞赛开赛 孩子动手做未来城市</h3>
            <p>“未来的城市不仅要各种设施齐全、楼房高大，还要是干净、纯粹的。”昨日上午，2015年武汉市中小学生建筑模型竞赛开赛，481名小选手自己设计、动手制作，运用各种材料，将他们对未来城市的想象制作成了模型。</p>
        </a>
    </div>
    <div class="infoList">
        <a href="infoDetail.html">
            <div class="infoTime">2天前</div>
            <h3>武汉市中小学生建筑模型竞赛开赛 孩子动手做未来城市</h3>
            <p>“未来的城市不仅要各种设施齐全、楼房高大，还要是干净、纯粹的。”昨日上午，2015年武汉市中小学生建筑模型竞赛开赛，481名小选手自己设计、动手制作，运用各种材料，将他们对未来城市的想象制作成了模型。</p>
        </a>
    </div>


</div>
</body>
</html>