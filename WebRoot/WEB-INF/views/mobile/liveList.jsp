<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="fnc"  uri="/WEB-INF/tlds/fnc.tld"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head lang="en">
<%@ include file="/WEB-INF/views/include/mobileHead.jsp"%>
    <title>直播页面</title>
</head>
<body>
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
        
        
        function changeValue(id){
        	var li =document.getElementById(id);
        	//clearClass();
        	if(li.getAttribute("class")!=null){
        	li.removeAttribute("class");	
        	}else{
        		li.setAttribute("class","cur");	
        	}
        	
        	$.ajax({
        		type:"POST",
    			url:"/study/gateway/subject?deptid="+id+"&type=1",
    			dataType:"json",
    			contentType:"application/json",
    			data:JSON.stringify({"deptid":id}),
    			success:function(data){
    				var liStr = "";
    				
    				$.each(data.data, function(index, i) {
    					liStr+="<li id="+i.value+">"+i.name+"</li>";
    				})
    				$("#childlist").html("");
    				$("#childlist").append(liStr);
    			},
    			error:function(){
    				alert("shibai");
    			}
    		})
        	
        }
        
        
        function clearClass(){
        	/* val childlist = document.getElementById("childlist").children();
        	$.each(childlist,function(index,i){
        		if(i.getAttribute("class")!=null){
        			i.removeAttribute("class");	
        		}
        	}) */
        }
    </script>
    直播
    <div class="search">
        <input type="text" placeholder="请输入搜索内容" onfocus="demo()" />
        <button onclick="ccc()">筛选<i class="icon-arrow-drop-down"></i></button>
    </div>
</header>
<div class="selectedList" id="selectedList" style="display: none;">
    <ul id="foo">
       <!--  <span>共13546546节课</span> -->
       <li class="cur">所有院系</li>
       <c:forEach items="${subject.data}" var="subject">
        <li id="${subject.value}" onclick="changeValue(this.id)">${subject.name}</li>
       </c:forEach>
    </ul>
    <ul class="childList" id="childlist">
       <!--  <li>诸葛亮</li>
        <li>诸葛亮</li>
        <li>诸葛亮<span><i class="icon-check"></i></span></li>
        <li>诸葛亮</li>
        <li>诸葛</li>
        <li>诸葛孔明<span><i class="icon-check"></i></span></li>
        <li>诸葛亮</li>
        <li>诸葛亮</li>
        <li>诸葛亮</li>
        <li>诸葛亮<span><i class="icon-check"></i></span></li>
        <li>诸葛</li>
        <li>诸葛孔明</li> -->
    </ul>
    <div class="listOption">
        <button>重置</button>
        <button onclick="ddd()">确定(349节)</button>
    </div>
</div>
<script>
    function ccc(){
        var selectedList=document.getElementById("selectedList");
        selectedList.style.display="block";
    }
    function ddd(){
        var selectedList=document.getElementById("selectedList");
        selectedList.style.display="none";

    }
    function demo(){
        var listFrom=document.getElementById("listFrom");
         var header=document.getElementById("header");
         var resultPage=document.getElementById("resultPage");
         listFrom.style.display="none";
         header.style.display="none";
         resultPage.style.display="block";
    }
</script>
<div class="playingState paddingTop90" id="listFrom">
    <!--正在观看-->
    <div class="recentWatch">
    <c:if test="${result.guankan.data.size()!=0}"><div class="title color-green"><i class="icon-event-note"></i>正在观看</div></c:if>
    <c:forEach items="${result.guankan.data}" var="live">
	    <div class="playList">
            <a href="liveDetail.html">
                <h3>${live.subject}</h3>
                <div class="resentLive loft">
                    <div class="liveAuthor"><i class="icon-person"></i>${live.username}</div>
                </div>
            </a>
        </div>
	</c:forEach>
       
      
    </div>
    <!--正在直播-->
    <div class="recentWatch">
        <div class="title color-blue"><i class="icon-event-note"></i>正在直播</div>
        <div class="playList">
    <c:forEach items="${result.zhibo.data}" var="zhibo">
    <c:if test="${zhibo.status==3}">
       <a href="liveDetail.html">
                <h3>${zhibo.subject}</h3>
                <div class="resentLive loft">
                    <div class="liveAuthor"><i class="icon-person"></i>${zhibo.username}</div>
                    <div class="liveCurrentStatic color-blue"><i class="icon-living"></i>正在直播：${zhibo.ybfsj}</div>
                </div>
            </a>
    </c:if>
	 
	</c:forEach>
        </div>
    </div>
    <!--即将开始-->
    <div class="recentWatch">
        <div class="title"><i class="icon-event-note"></i>即将开始</div>
        <div class="playList">
        
    <c:forEach items="${result.zhibo.data}" var="zhibo2">
	    <c:if test="${zhibo2.status==2}">
	            <a href="liveDetail.html">
	                <h3>${zhibo2.subject}</h3>
	                <div class="resentLive loft">
	                    <div class="liveAuthor"><i class="icon-person"></i>${zhibo2.username}</div>
	                    <div class="liveCurrentStatic color-blue"><i class="icon-living"></i>正在直播：${zhibo2.ybfsj}</div>
	                </div>
	            </a>
	    </c:if>
	 
	</c:forEach>
        </div>
    </div>
    <!--更多直播-->
    <div class="recentWatch">
        <div class="title"><i class="icon-event-note"></i>更多直播</div>
        <div class="playList">
        
         <c:forEach items="${result.zhibo.data}" var="zhibo3">
	    <c:if test="${zhibo3.status==1}">
	            <a href="liveDetail.html">
	                <h3>${zhibo3.subject}</h3>
	                <div class="resentLive loft">
	                    <div class="liveAuthor"><i class="icon-person"></i>${zhibo3.username}</div>
	                    <div class="liveCurrentStatic color-blue"><i class="icon-living"></i>正在直播：${zhibo3.ybfsj}</div>
	                </div>
	            </a>
	    </c:if>
	 
	</c:forEach>
           <!--  <a href="liveDetail.html">
                <h3>六上“生物的多样性”单元疑难解答</h3>
                <div class="resentLive loft">
                    <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                    <div class="liveCurrentStatic color-gray">2016年10月10日 12:30</div>
                </div>
            </a>
            <a href="liveDetail.html">
                <h3>六上“生物的多样性”单元疑难解答</h3>
                <div class="resentLive loft">
                    <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                    <div class="liveCurrentStatic color-gray">2016年10月10日 12:30</div>
                </div>
            </a>
            <a href="liveDetail.html">
                <h3>六上“生物的多样性”单元疑难解答</h3>
                <div class="resentLive loft">
                    <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                    <div class="liveCurrentStatic color-gray">2016年10月10日 12:30</div>
                </div>
            </a>
            <a href="liveDetail.html">
                <h3>六上“生物的多样性”单元疑难解答</h3>
                <div class="resentLive loft">
                    <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                    <div class="liveCurrentStatic color-gray">2016年10月10日 12:30</div>
                </div>
            </a>
            <a href="liveDetail.html">
                <h3>六上“生物的多样性”单元疑难解答</h3>
                <div class="resentLive loft">
                    <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                    <div class="liveCurrentStatic color-gray">2016年10月10日 12:30</div>
                </div>
            </a> -->
        </div>
    </div>
</div>
    <!--搜索结果显示-->
    <div class="resultPage" id="resultPage" style="display: none;">
        <div class="searchResult">
            <input type="text" placeholder="请输入课程名、教师" />
            <button onclick="eee()">取消</button>
        </div>
        <div class="playingState" style="display: none;">
            <!--最近观看-->
            <div class="recentWatch">
                <div class="title color-green"><i class="icon-event-note"></i>最近观看</div>
                <div class="playList">
                    <a href="liveDetail.html">
                        <h3>六上“生物的多样性”单元疑难解答</h3>
                        <div class="resentLive">
                            <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                            <div class="liveCurrentStatic color-green"><i class="icon-living"></i>剩余23:22</div>
                        </div>
                    </a>
                    <a href="liveDetail.html">
                        <h3>六上“生物的多样性”单元疑难解答</h3>
                        <div class="resentLive">
                            <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                            <div class="liveCurrentStatic color-green"><i class="icon-living"></i>不足一分钟 </div>
                        </div>
                    </a>
                </div>
            </div>
            <!--更多点播-->
            <div class="recentWatch">
                <div class="title"><i class="icon-event-note"></i>更多点播</div>
                <div class="playList">
                    <a href="liveDetail.html">
                        <h3>六上“生物的多样性”单元疑难解答</h3>
                        <div class="resentLive">
                            <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                            <div class="liveTime">1:24:21</div>
                            <div class="liveCurrentStatic color-gray"><span><i class="icon-chat-3"></i>24</span><span><i class="icon-visibility"></i>308</span></div>
                        </div>
                    </a>
                    <a href="liveDetail.html">
                        <h3>六上“生物的多样性”单元疑难解答</h3>
                        <div class="resentLive">
                            <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                            <div class="liveTime">1:24:21</div>
                            <div class="liveCurrentStatic color-gray"><span><i class="icon-chat-3"></i>24</span><span><i class="icon-visibility"></i>308</span></div>
                        </div>
                    </a>
                    <a href="liveDetail.html">
                        <h3>六上“生物的多样性”单元疑难解答</h3>
                        <div class="resentLive">
                            <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                            <div class="liveTime">1:24:21</div>
                            <div class="liveCurrentStatic color-gray"><span><i class="icon-chat-3"></i>24</span><span><i class="icon-visibility"></i>308</span></div>
                        </div>
                    </a>
                    <a href="liveDetail.html">
                        <h3>六上“生物的多样性”单元疑难解答</h3>
                        <div class="resentLive">
                            <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                            <div class="liveTime">1:24:21</div>
                            <div class="liveCurrentStatic color-gray"><span><i class="icon-chat-3"></i>24</span><span><i class="icon-visibility"></i>308</span></div>
                        </div>
                    </a>
                    <a href="liveDetail.html">
                        <h3>六上“生物的多样性”单元疑难解答</h3>
                        <div class="resentLive">
                            <div class="liveAuthor"><i class="icon-person"></i>唐雨泽</div>
                            <div class="liveTime">1:24:21</div>
                            <div class="liveCurrentStatic color-gray"><span><i class="icon-chat-3"></i>24</span><span><i class="icon-visibility"></i>308</span></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <div class="noContent">
            <span><i class="icon-phone_search_no"></i> </span>
            <p>暂无内容</p>
        </div>

    </div>
    <script>
    
        function eee(){
            var listFrom=document.getElementById("listFrom");
            var header=document.getElementById("header");
            var resultPage=document.getElementById("resultPage");
            listFrom.style.display="block";
            header.style.display="block";
            resultPage.style.display="none"
        }
        
        
    
    </script>

</body>
</html>