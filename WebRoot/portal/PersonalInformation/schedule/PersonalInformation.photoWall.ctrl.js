﻿define(['app','config'], function (app,config) {
    app.registerController('PhotoWallCtrl', ['$scope','$modal','$sce','growl','ScheduleService','$stateParams','$location', function ($scope,$modal,$sce,growl,ScheduleService,$stateParams,$location) {
    	
    	
    	//开启点播状态
    	$scope.openVideoState = function(status,floder){
    		
    		ScheduleService.demand(floder,status).then(
                    function(data){
                    	
                    	$scope.videoList = data;
                    	if($scope.videoList.data.length > 0){
	                    	$.each($scope.videoList,function(i,video){
	                    		if(video.nametype == 'Videos'){
	                    			video.fileurl = $sce.trustAsResourceUrl(video.fileurl);
	                    		}
	                         });
	                    	$scope.user = $scope.global.user;
	                    	$scope.type = $scope.videoList.source;
	                    	$scope.photo = $scope.videoList.data;
	                    	$scope.activePhotoShow = $scope.videoList.data[0];
	                    	$scope.activePhoto = 0;
	                    	$scope.resourcevisable=$scope.videoList.data[0].resourcevisable;
	                    	$scope.resourcecollection=$scope.videoList.data[0].resourcecollection;
                    	}
                    },
                    function(code){
                        return [];
                    }
               )
    	}
    	$scope.openVideoClassroom = function(status,floder){
    		
    		ScheduleService.showWisclassroomFiles(floder,status).then(
                    function(data){
                    	
                    	$scope.videoList = data;
                    	if($scope.videoList.data.length > 0){
	                    	$.each($scope.videoList,function(i,video){
	                    		if(video.nametype == 'Videos'){
	                    			video.fileurl = $sce.trustAsResourceUrl(video.fileurl);
	                    		}
	                         });
	                    	$scope.user = $scope.global.user;
	                    	$scope.type = $scope.videoList.source;
	                    	$scope.photo = $scope.videoList.data;
	                    	$scope.activePhotoShow = $scope.videoList.data[0];
	                    	$scope.activePhoto = 0;
	                    	$scope.resourcevisable=$scope.videoList.data[0].resourcevisable;
	                    	$scope.resourcecollection=$scope.videoList.data[0].resourcecollection;
                    	}
                    },
                    function(code){
                        return [];
                    }
               )
    	}
       
        	$scope.openVideo=function(p) {
				if ($scope.flag == '1') {
					if (p.nametype == 'Videos') {
						if (p.transPath != null && p.transPath != undefined && p.transPath != '' && p.transFlag == '1') {
							window.open("http://study.zonekey.com.cn"+p.fileurl);
						} else {							
							var keywords = {
								"id": p.id,
								"fileurl": p.fileurl
							};
							ScheduleService.previewTrans(keywords).then(function (data) {								
								if (data != null && data != undefined) {
									if (data.id === '1') {
										window.location.reload();
										alert("转码成功，请再次点击该视频！");
									} else if (data.id == '-3') {
										growl.addErrorMessage(data.content);
										alert(data.content);
									} else {
										growl.addInfoMessage(data.content);
										alert(data.content);
									}
								} else {
									growl.addErrorMessage("请求失败了，请重试！");
									alert("请求失败了，请重试！");
								}
							}, function (code) {
								growl.addErrorMessage("对不起,请求失败了，错误码:" + code);
								alert("对不起,请求失败了，错误码:" + code);
							});
							return false;
						}
					}
				}else{
					window.open("http://study.zonekey.com.cn"+p.fileurl);
				}
			};
        	$scope.ToactivePhoto = function(index){
        		$scope.activePhoto = index;
        		$scope.activePhotoShow = $scope.videoList.data[index];
        		$scope.resourcevisable=$scope.videoList.data[index].resourcevisable;
            	$scope.resourcecollection=$scope.videoList.data[index].resourcecollection;
        		/*$scope.activePhotoShow = angular.copy(wallList[index]);*/
            	if($scope.flag=='1'){
            		if($scope.activePhotoShow.nametype == 'Videos'){
    					if($scope.activePhotoShow.transPath != null && $scope.activePhotoShow.transPath != undefined &&$scope.activePhotoShow.transPath != '' && $scope.activePhotoShow.transFlag == '1'){
//    					    window.open(resource.transPath);
    					}else{
    						var keywords = {
    	                            "id":$scope.activePhotoShow.id,
    	                            "fileurl":$scope.activePhotoShow.fileurl
    	                        };
    						ScheduleService.previewTrans(keywords).then(function(data){
    							console.log("未转码预览返回数据：",data);
    							if(data != null && data != undefined){
    								if(data.id === '1'){
    									//alert("ok");
//    									window.open(data.content);
    									alert("转码成功，请刷新页面！");
    								} else if(data.id == '-3') {
    									growl.addErrorMessage(data.content);
    									alert(data.content);
    								} else {
    									growl.addInfoMessage(data.content);
    									alert(data.content);
    								} 
    							}else{
    								growl.addErrorMessage("请求失败了，请重试！");
    								alert("请求失败了，请重试！");
    							}
    						},function(code){
    							growl.addErrorMessage("对不起,请求失败了，错误码:"+code);
    							alert("对不起,请求失败了，错误码:"+code);
    						});
    					}
    				}
            	}


        	};
        	
        	//设置录播机单一文件可见性（单一）
        	$scope.oneafterclassvisibility=function (p){
            	if(p.resourcevisable==='1'){
            		p.resourcevisable='0';
            	} else{
            		p.resourcevisable='1';
            	}
            	if($scope.type == '2'){
            	var keywords = {
            			"resourcevisable":p.resourcevisable,
                        "readyresourcid":p.id,
                        "curriculumid":p.curriculumid,
                        "floder":p.floder
                    };
            	
            		ScheduleService.oneafterclassvisibility(keywords).then(
                            function(data){
                            	growl.addSuccessMessage("设置成功");
                            	
                            },function(){
                            	 //处理失败后操作
                            	growl.addErrorMessage("设置失败");
                            }
                        );
            	}else{
            		var keywords = {
                			"resourcevisable":p.resourcevisable,
                            "id":p.id
                        };
                	
                		ScheduleService.onesetafterclassvisibility(keywords).then(
                                function(data){
                                	growl.addSuccessMessage("设置成功");
                                	
                                },function(){
                                	 //处理失败后操作
                                	growl.addErrorMessage("设置失败");
                                }
                            );
            	}
            	
        	};
        	//收藏录播机上单一文件（单一）
        	$scope.onecollectAfterResource=function (p){
            	if(p.resourcecollection==='1' ){
            		p.resourcecollection='0';
            	} else{
            		p.resourcecollection='1';
            	}
            	if($scope.type == '2'){
            	var keywords = {
            			"storetype":"2",
                        "storeid":p.id,
                        "curriculumid":p.curriculumid,
                        "floder":p.floder,
                        "resourcecollection":p.resourcecollection,
                        "name":p.name,
                        "subject":p.subject,
                        "grade":p.grade,
                        "id":p.id,
                        "resourcePath":p.resourcePath,
                        "transPath":p.transPath,
                        "transFlag":p.transFlag
                    };
            	ScheduleService.collectOneVideo(keywords).then(
                        function(data){
                        	 growl.addSuccessMessage("设置成功");
                        	
                        },function(){
                        	 //处理失败后操作
                        	growl.addErrorMessage("设置失败");
                        }
                    );
            	}else{
            		
                 	var keywords = {
                 			"storetype":"3",
                             "storeid":p.id,
                             "curriculumid":p.curriculumid,
                             "resourcecollection":p.resourcecollection,
                             "name":p.name,
                             "fileurl":p.fileurl,
                             "size":p.size,
                             "id":p.id,
                             "transPath":p.transPath,
                             "transFlag":p.transFlag
                         };
                 	ScheduleService.setOnecollectAfterResource(keywords).then(
                             function(data){
                             	 growl.addSuccessMessage("设置成功");
                             	
                             },function(){
                             	 //处理失败后操作
                             	growl.addErrorMessage("设置失败");
                             }
                         );
            	}
        	};
       
      
       
        var init = function(){
        	/*$scope.video =  JSON.parse($stateParams.video);*/
        	var id=$location.search().id;
        	var curriculumid = $location.search().curriculumid;
        	$scope.flag = $location.search().flag;
        	if($scope.flag == null){
        		//录播机
        		$scope.openVideoState(id,curriculumid);
        	}else{
        		//智慧教室
        		$scope.openVideoClassroom(id,curriculumid);
        	}
        	
        	
            
        };

        init();
      }]);
});
