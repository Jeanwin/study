define([ 'angular', 'config', 'codes' ], function(angular, config, codes) {
	/*-----------------------------------------
	 *获取字典接口
	 ------------------------------------------*/

	angular.module('olive.service.video', [])

	.constant('VideoServiceConfig', {
		files : {
			getRemoteCodes : 'data/code.json',
			processList : 'data/processList.json',
			addDotInfo : 'data/addDotInfo.json',
			delDotInfo : 'data/delDotInfo.json',
			cutImg : 'data/cutImg.json',
			mergeVideos : 'data/mergeVideos.json',
			getMergeStatus : 'data/mergeStatus.json'
		},
		urls : {
			getRemoteCodes : config.backend.ip + config.backend.base + 'code/getRemoteCodes.do',
			processList : config.backend.ip + config.backend.base + 'process/processList',
			addDotInfo : config.backend.ip + config.backend.base + 'process/addDotInfo',
			delDotInfo : config.backend.ip + config.backend.base + 'process/delDotInfo',
			cutImg : config.backend.ip + config.backend.base + 'video/cutImg',
			mergeVideos : config.backend.ip + config.backend.base + 'video/mergeVideos',
			getMergeStatus : config.backend.ip + config.backend.base + 'video/getMergeStatus',
		}
	}).factory('VideoService', [ '$http', '$q', 'VideoServiceConfig', function($http, $q, VideoServiceConfig) {

		var video = [];
		return {
			getVideo : function() {
				return video;
			},

			setVideo : function(videos) {
				this.video = videos;
			},
			processList : function(resourceid) {
				console.log("打点资源id", resourceid);
				var deffered = $q.defer();
				var _url = VideoServiceConfig.urls.processList;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : resourceid,
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			addDotInfo : function(dotInfo) {
				console.log("添加打点", dotInfo);
				var deffered = $q.defer();
				var _url = VideoServiceConfig.urls.addDotInfo;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : dotInfo,
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			delDotInfo : function(dotInfoes) {
				console.log("删除打点", dotInfoes);
				var deffered = $q.defer();
				var _url = VideoServiceConfig.urls.delDotInfo;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : dotInfoes,
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			cutImg : function(data) {
				console.log("视频截图", data);
				var deffered = $q.defer();
				var _url = VideoServiceConfig.urls.cutImg;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : data,
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			mergeVideos : function(data) {
				console.log("分割，合并视频", data);
				var deffered = $q.defer();
				var _url = VideoServiceConfig.urls.mergeVideos;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : data,
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			getMergeStatus : function(data) {
				console.log("分割，合并视频查询：", data);
				var deffered = $q.defer();
				var _url = VideoServiceConfig.urls.getMergeStatus;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : data,
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			showVideo : function(url, isClose, resource) {
				var animateCss = isClose ? 'bounceOutUp' : 'bounceInDown';
				var removeCss = !isClose ? 'bounceOutUp' : 'bounceInDown';
				var video_warper = angular.element(".video_warper");
				var overlay = angular.element("#overlay_video");
				var container = angular.element("#video_container");
				var video = '<video id="video_me" controls autoplay class="video_jeanwin" width="720" height="540" src="' + url + '"></video>';
				// var close = angular.element("video_close");
				video_warper.removeClass(removeCss);
				video_warper.addClass(animateCss);
				// $("#overlay_video").toFixed();
				if (isClose) {
					setTimeout(function() {
						overlay.hide();
						container.empty();
					}, 500);
				} else {
					overlay.show();
					container.html(video);
					var e = document.getElementById("video_me");
					if (e.requestFullscreen) {
						// e.requestFullscreen();
					} else if (e.webkitRequestFullscreen) {
						// e.webkitRequestFullscreen();
						document.addEventListener("webkitfullscreenchange", function(e) {
							if (window.outerHeigth == screen.heigth && window.outerWidth == screen.width){
								if($('.bounceInDown').length>0){
								$('.bounceInDown')[0].className=$('.bounceInDown')[0].className.replace('bounceInDown','')
								}
								//angular.element("#main_content").hide();
							} else {
								//angular.element("#main_content").show();
							}
						});
					} else if (e.mozRequestFullScreen) {
						// e.mozRequestFullScreen();
					} else if (e.msRequestFullscreen) {
						// e.msRequestFullscreen();
					}
				}
			}
		};
	} ]);
});