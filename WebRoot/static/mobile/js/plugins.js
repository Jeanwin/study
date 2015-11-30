(function($) {
	$.animateIt = function(target, clazz, duration) {
		var dft = "animated fadeIn";
		if (target != undefined && target != null) {
			// 移除基础样式
			var oClass = target.attr("class") ? target.attr("class") : "";
			var bClass = clazz ? clazz : dft;
			var aClass = bClass.split(" ");
			if (target.hasClass(aClass[0])) {
				if (oClass.indexOf(aClass[0]) !== -1) {
					target.attr("class", oClass.substring(0, oClass.lastIndexOf(aClass[0])));
				}
			}
			if (duration != undefined && duration != null) {
				for (var i = 0; i < 20; i++) {
					// 开始动画
					setTimeout(function() {
						target.addClass(bClass);
					}, duration * i + 1);
					// 取消样式
					setTimeout(function() {
						oClass = target.attr("class") ? target.attr("class") : "";
						if (oClass.indexOf(aClass[0]) !== -1) {
							target.attr("class", oClass.substring(0, oClass.lastIndexOf(aClass[0])));
						}
					}, duration * (i + 1));
				}
			} else {
				setTimeout(function() {
					target.addClass(bClass);
				}, 0);
			}
		} else {
			console.log("not find target object to be animated");
		}
	}
	/** ********************************************************************************** */
	$.message = function(message, type, duration) {
		$("#message_info").remove();
		var messageDiv = '<div id="message_info" class="growl">';
		if (message != undefined && message != null && message != '') {
			if (type == undefined || type == null || type === '') {
				messageDiv += '<div class="growl-item alert growl-success"><p class="growl-p">' + message + '</p></div>';
			} else {
				messageDiv += '<div class="growl-item alert growl-' + type + '"><p class="growl-p">' + message + '</p></div>';
			}
			messageDiv += '</div>';
			$(messageDiv).appendTo($("body"));
			$.animateIt($("#message_info"), "animated slideInUp");
			// $("#message_info").get(0).style.display = "block";
			var time = duration ? duration : 2000;
			setTimeout(function() {
				// $("#message_info").get(0).style.display = "none";
				$.animateIt($("#message_info"), "animated slideOutDown");
			}, time);
		}
	}
	/** ********************************************************************************** */
	$.formatSeconds = function(value, sep) {
		var theTime = parseInt(value);// 秒
		var theTime1 = 0;// 分
		var theTime2 = 0;// 小时
		var sepCharSecond = sep ? sep : "秒";
		var sepCharMinites = sep ? sep : "分";
		var sepCharHour = sep ? sep : "小时";
		if (theTime > 60) {
			theTime1 = parseInt(theTime / 60);
			theTime = parseInt(theTime % 60);
			if (theTime1 > 60) {
				theTime2 = parseInt(theTime1 / 60);
				theTime1 = parseInt(theTime1 % 60);
			}
		}
		var result = "" + parseInt(theTime) + (sepCharSecond == "秒" ? sepCharSecond : "");
		if (theTime1 > 0) {
			result = "" + parseInt(theTime1) + sepCharMinites + result;
		}
		if (theTime2 > 0) {
			result = "" + parseInt(theTime2) + sepCharHour + result;
		}
		return result;
	}
	/** ********************************************************************************* */
	$.renderView = function(arguments) {

		var args = {
			data : arguments.data,
			containerId : arguments.containerId,
			callback : arguments.callback,
			noMessage : arguments.noMessage ? arguments.noMessage : "暂无记录",
			noCallback : arguments.noCallback,
			isClear : arguments.isClear
		}
		var noData = function() {
			console.log(args.noMessage);
		}
		var noHandler = args.noCallback ? args.noCallback : noData;
		if (args.isClear) {
			$("#" + args.containerId).html("");
		}
		if (args.data.length > 0) {
			$("#" + args.containerId).append(args.callback(args.data));
		} else {
			noHandler();
		}
	}
	$.sendPost = function(arguments) {
		var args = {
			url : arguments.url ? arguments.url : "",
			data : arguments.data ? arguments.data : "",
			render : arguments.render ? arguments.render : null,
			operation : arguments.operation ? arguments.operation : arguments.url,
			errorCallback : arguments.errorCallback,
			successCallback : arguments.successCallback,
			isClear : arguments.isClear ? arguments.isClear : false,
			contentType : arguments.contentType ? arguments.contentType : "application/json",
			type : arguments.type ? arguments.type : "POST",
			async : arguments.async ? arguments.async : true,
		}
		function errorHandler(xhr, error) {
			console.log(operation + "失败");
			$.message(args.operation + "失败，错误码:" + xhr.status, "error");
		}
		function successHandler(da) {
			console.log(args.operation + ":" + da);
			args.render(da, args.isClear);
		}
		$.ajax({
			url : $.config.appName + args.url,
			type : args.type,
			async : args.async,
			dataType : "json",
			contentType : args.contentType,
			data : JSON.stringify(args.data),
			success : args.successCallback ? args.successCallback : successHandler,
			error : args.errorCallback ? args.errorCallback : errorHandler
		})
	}
})(Zepto)
