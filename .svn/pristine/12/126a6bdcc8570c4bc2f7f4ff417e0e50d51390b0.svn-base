 <%@ page contentType="text/html;charset=UTF-8"%>
  <div class="top-bar hidden-xs" style="background:#0f97e9">
        <div class="index-top row margin-none">
            <div class="col-xs-3 col-xs-offset-7">
                <div class="input-group input-group-sm">
                    <span class="input-group-btn">
                        <select class="btn btn-default" style="background-color: #0e7ab7;border: none;color: #f1f1f1">
                            <option>直播课程</option>
                            <option>精品课程</option>
                        </select>
                    </span>
                    <input type="text" class="form-control" style="position: relative; background-color: #0e7ab7;border: none;color: #f1f1f1;" />
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" style="background-color: #0e7ab7;border: none;">
                            <span style="color: #fff" class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>
                </div>
            </div>
            <div class="btn btn-link col-xs-1 border-none">
                <span id="login_title"><a style="color: #ffffff;font-size: 13px" href="${ctx}/gateway/login">登录</a></span>
                <a style="color: #ffffff">
                    <select class="LoginSelect" style="background:#3BACDE">
                        <option value="${ctx}/gateway/index">门户首页</option>
                        <!-- <option value="./getAllActives">教研中心</option> -->
                    </select>
                </a>
            </div>
        </div>
    </div>
<div class="col-xs-12 navbar navbar-customer">
      <!--  -->
      <div class="container">
        <div class="navbar-header">
           <button class="navbar-toggle margin-right-none padding-right-none margin-top-none">
                <a href="#"><i class="icon-phone_user fontSize-30"></i></a>
           </button>
           <div  class="navbar-toggle margin-right-none padding-left-none padding-right-none  margin-top-none" >
                <i class="icon-phone_search fontSize-30 margin-right-10"></i>
                <span>
                    <div class="input-group input-group-sm" style="width:180px;margin-top:-2px;margin-right:-10px;">
                        <span class="input-group-btn">
                            <select class="btn btn-default" style="padding-left:0;padding-right:1px;">
                                <option value="直播">直播</option>
                                <option value="热门">热门</option>
                                <option value="精品" selected="selected">精品</option>
                            </select>
                        </span>
                        <input type="text" class="form-control" />
                        <span class="deleteContent">
                            <span class="glyphicon glyphicon-remove"></span>
                        </span>
                    </div>
                </span>
          </div>          
          <div class="navbar-brand logo padding-right-none padding-left-none padding-top-5" >
              <a href="#"><img src="${ctx}/static/img/logo.png" /></a>
          </div>
        </div>
        <!-- <div class="navbar-collapse collapse" role="navigation"> -->
        <div role="navigation">
            <ul class="nav navbar-nav">
                <li class="current">
                    <a href="${ctx}/gateway/index">首页</a>
                </li>
                <li id="pipe">|</li>
                <li>
                    <a href="${ctx}/gateway/live">直播课程</a>
                </li>
                <li id="pipe">|</li>
                <li><a href="${ctx}/gateway/exec">精品课程</a></li>
                <li id="pipe">|</li>
                <li><a href="${ctx}/gateway/info">通知公告</a></li>
                <li id="pipe">|</li>
                <li><a href="${ctx}/gateway/getAllActives">教研中心</a></li>
            </ul>
        </div>
      </div>
    </div> 
    <script type="text/javascript">
    	var url = window.location.href;
    		url = url.substring(url.lastIndexOf("/")+1);
    		$("ul[class='nav navbar-nav']>li[class='current']").removeClass("current");
    		if(url.indexOf("index")==0){
    			$("ul[class='nav navbar-nav']>li:eq(0)").addClass("current");
    		}else if(url.indexOf("live")==0){
    			$("ul[class='nav navbar-nav']>li:eq(2)").addClass("current");
    		}else if(url.indexOf("exec")==0){
    			$("ul[class='nav navbar-nav']>li:eq(4)").addClass("current");
    		}else if(url.indexOf("info")==0){
    			$("ul[class='nav navbar-nav']>li:eq(6)").addClass("current");
    		}else{
    			$("ul[class='nav navbar-nav']>li:eq(8)").addClass("current");
    		};
    </script>