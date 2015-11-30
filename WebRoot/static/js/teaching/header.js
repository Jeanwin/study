var currentUser;
var visitor;
var getUserInfo = function() {
	$.ajax({
		url : "/study/gateway/getUser",
		type : "POST",
		async : false,
		dataType : "json",
		success : function(data) {
			//有用户登录
			if(data != null && data != undefined){
				currentUser = data;
				setCookie("loginname", currentUser.loginname);
				$("#login_title").html('<span style="color:#fff">你好,'+data.name+'</span>');
				$("#logout").html('&nbsp;<a style="color: #ffffff"  href="/study/logout">退出</a>');
				$("ul[class='nav navbar-nav']").append("<li id='pipe'>|</li><li><a href='/study/portal/index.html#/PersonalInformation/resource'>个人中心</a></li>");
			}
			//没有用户登录
			else{
				setCookie("loginname", "",-1);
			}
		},
		error : function(xhr, status) {
			//addMessage("error", "不好意思，请求出错了，错误码" + xhr.status);
		}
	});
};
$(function() {
     getUserInfo();
     visitor = getCookie("visitor");
		if(visitor ==""||visitor == null){
			setCookie("visitor", uuid());
			visitor = getCookie("visitor");
		}
     $(".LoginSelect").bind("change",function(){
    	 window.location=$(".LoginSelect").val(); 
    	 return false;
     });
});

function setCookie(c_name, value, expiredays){
	var exdate=new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	document.cookie=c_name+ "=" + escape(value)+";path=/" + ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}
function getCookie(c_name){
　　　　if (document.cookie.length>0){　　//先查询cookie是否为空，为空就return ""
　　　　　　c_start=document.cookie.indexOf(c_name + "=")　　//通过String对象的indexOf()来检查这个cookie是否存在，不存在就为 -1　　
　　　　　　if (c_start!=-1){ 
　　　　　　　　c_start=c_start + c_name.length+1　　//最后这个+1其实就是表示"="号啦，这样就获取到了cookie值的开始位置
　　　　　　　　c_end=document.cookie.indexOf(";",c_start)　　//其实我刚看见indexOf()第二个参数的时候猛然有点晕，后来想起来表示指定的开始索引的位置...这句是为了得到值的结束位置。因为需要考虑是否是最后一项，所以通过";"号是否存在来判断
　　　　　　　　if (c_end==-1) c_end=document.cookie.length　　
　　　　　　　　return unescape(document.cookie.substring(c_start,c_end))　　//通过substring()得到了值。想了解unescape()得先知道escape()是做什么的，都是很重要的基础，想了解的可以搜索下，在文章结尾处也会进行讲解cookie编码细节
　　　　　　} 
　　　　}
　　　　return ""
}
function uuid() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
	s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");
	return uuid;
	}
/*********************************************************/
//日志统计
/**sort 1:统计,2:心跳,3:播放,4暂停,5声音,6视频出错,7切换屏幕
 * type 1代表直播,2代表精品课程 
 * id 当type为1代表curriculumid,当type为2代表floder
 */
var loginname = getCookie("loginname");
function courseDetail(type,id){
	var data = {"sort":"1","type":type,"id":id,"loginname":loginname,"visitor":visitor};
	ajax(data);
}
/**
 * 播放
 */
function play(type,id,currenttime){
	var data = {"sort":"3","type":type,"id":id,"loginname":loginname,"visitor":visitor,"currenttime":currenttime};
	ajax(data);
}
/**
 * 暂停
 * @param type
 * @param id
 * @param currenttime
 * @returns
 */
function pause(type,id,currenttime){
	var data = {"sort":"4","type":type,"id":id,"loginname":loginname,"visitor":visitor,"currenttime":currenttime};
	ajax(data);
}

/**
 * 音量改变
 * @param type
 * @param id
 * @param currenttime
 * @returns
 */
function changeVolume(type,id,volume){
	var data = {"sort":"5","type":type,"id":id,"loginname":loginname,"visitor":visitor,"volume":volume};
	ajax(data);
}
/**
 * 拖拽
 * @param type
 * @param id
 * @param time 拖拽到时间点
 * @param currenttime
 * @returns
 */
function seek(type,id,time,currenttime){
	var data = {"sort":"8","type":type,"id":id,"loginname":loginname,"visitor":visitor,"currenttime":currenttime,"time":time};
	ajax(data);
}
/**
 * 全屏
 * @param type
 * @param id
 * @param flag true-全屏 false-标准屏幕
 * @param currenttime
 * @returns
 */
function fullscreen(type,id,flag,currenttime){
	var data = {"sort":"9","type":type,"id":id,"loginname":loginname,"visitor":visitor,"currenttime":currenttime,"flag":flag};
	ajax(data);
}
/**
 * 音量开关
 * @param type
 * @param id
 * @param flag true-音量开 false-音量关闭
 * @param currenttime
 * @returns
 */
function mute(type,id,flag,currenttime){
	var data = {"sort":"10","type":type,"id":id,"loginname":loginname,"visitor":visitor,"currenttime":currenttime,"flag":flag};
	ajax(data);
}
/**
 * 视频出错
 * @param type
 * @param id
 * @param currenttime
 * @returns
 */
function error(type,id,error,errorcode){
	var data = {"sort":"6","type":type,"id":id,"loginname":loginname,"visitor":visitor,"error":error,"errorcode":errorcode};
	ajax(data);
}
/**
 * heartbeat 日志心跳
 * @returns
 */
var intervalProcess;
function heartbeat(type,id){
	var data = {"sort":"2","type":type,"id":id,"loginname":loginname,"visitor":visitor};
	intervalProcess = setInterval(ajax,30000,data);
	//setTimeout("stopGoal()", delay);
}

function stopGoal(){
	clearInterval(intervalProcess);
}
//心跳时间
var herttime = 0;
function ajax(data){
	var url = "http://192.168.12.129:250/log/logRecord";
	if(data.sort=="2"){
		//直播
		if(data.type=="1"){
			herttime =herttime+30;
			data.currenttime=herttime;
		}else{
			var currentTime= $('.miframe')[0].contentWindow.zk_player.currentTimes(); 
			data.currenttime=currentTime;
		}
	}
	$.ajax({
		type:"get",
		url:url,
		data:data,
		dataType:"jsonp"
	});
}

