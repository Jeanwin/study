define(['app','config'], function (app,config) {
    app.registerController('PersonalInformationDetailCtrl', ['$scope','$modal','$filter','$upload','$timeout','$interval','$sce','growl','ScheduleService','$location' , function ($scope,$modal,$filter,$upload,$timeout,$interval,$sce,growl,ScheduleService,$location) {
    	// 预览
		$scope.viewOnline = function(resource) {
			if(resource.readyresourcefileurl != null && resource.readyresourcefileurl != undefined && resource.readyresourcefileurl != ''){
				if (resource.nametype == 'Pictrue' || resource.nametype == 'Documents' || resource.readyresourcefileurl.lastIndexOf('.swf') != -1 || resource.readyresourcefileurl.lastIndexOf('.html') != -1) {
                    window.open(resource.readyresourcefileurl);
				} else if(resource.nametype == 'Videos'){
					if(resource.transPath != null && resource.transPath != undefined &&resource.transPath != ''){
						window.open(resource.transPath);
					}else{
						growl.addErrorMessage("对不起,转码未完成");
					}
				}else if(resource.nametype == 'Word' ||  resource.readyresourcefileurl.lastIndexOf('.ppt') != -1 || resource.nametype == 'Excel' || resource.nametype == 'PDF'){
					window.open('../resource/readOnline?path='+resource.readyresourcefileurl.replace(config.resourceIp,''));
				}else {
					growl.addInfoMessage("暂不支持此格式文件预览");
				}
			}else{
				growl.addErrorMessage("对不起,文件路径不存在！");
			}
		};
    	//查看智慧教室的资源
    	$scope.openWisdomVideoState = function(id,curriculumid){
    		/*ScheduleService.showWisclassroomFiles(curriculumid,id).then(
                    function(data){
                    	$scope.videoList = data;
                    },
                    function(code){
                        return [];
                    }
               )
            */
    		
           
    	}
    	
    	//开启点播状态
    	$scope.openVideoState = function(status,floder,index){
    		
    		ScheduleService.demand(floder,status).then(
                    function(data){
                    	
                    	$scope.videoList = data;
                    },
                    function(code){
                        return [];
                    }
               )
            
    		
           $timeout(function(){
        	   $scope.photoWall($scope.videoList)
            },800)
    	}
    	$scope.showMoreReadyResource=function(){
    		if($scope.showMore==false){
    			$scope.curriculumMaterialsList=[];
        		$scope.curriculumMaterialsList=$scope.curriculumMaterialsListparam;
        		angular.element("#show_more_btn").html('简洁模式<i class="icon-arrow-drop-up"></i>');
        		$scope.showMore = true;
    		}else{
    			$scope.curriculumMaterialsList = $filter('limitTo')($scope.curriculumMaterialsListparam,3);
    			angular.element("#show_more_btn").html('展开更多<i class="icon-arrow-drop-down"></i>');
    			$scope.showMore = false;
    		}
    	}
    	$scope.onFileSelect = function($files) {


            $scope.file = $files[0];

            if($files.length > 1) {
                growl.addSuccessMessage('一次只能上传一个文件');
            }

            if($scope.file.type.indexOf('image') < 0) {
                growl.addErrorMessage('请上传图片文件');
                $scope.file = null;
                return false;
            }


            var fileReader = new FileReader();
            fileReader.readAsDataURL($scope.file);
            var loadFile = function(fileReader) {
                fileReader.onload = function(e) {
                    $timeout(function() {
                        $scope.schedule.imageurl = e.target.result;
                    });
                }
            }(fileReader);


            $scope.progress = -1;
            if ($scope.uploadRightAway) {
                $scope.startUpload();
            }
          //192.168.12.214:8080/curriculum/20150403144405.jpg
            $scope.startUpload();
            
    	}
    	$scope.startUpload = function(){
            var url = config.backend.ip + config.backend.base + 'rest/myClassRoom/uploadImage';
            $scope.upload = $upload.upload({            	
                url: url,
                data: {
                	curriculumid:$scope.schedule.id
                },
                file:$scope.file
            }).progress(function(evt) {
                $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
                console.log('percent: ' + $scope.progress);
            }).success(function(data, status, headers, config) {
                if(data.id == "1"){
                    growl.addSuccessMessage("上传成功");
                    return true;
                }else{
                    growl.addErrorMessage("上传失败");
                }
                 
            }).error(function(){
                growl.addErrorMessage("上传失败");
            });
        };
    	//上传资源
       /* $scope.uploadResource = function(){
            var modalInstance = $modal.open({
                templateUrl: 'PersonalInformation/resource/PersonalResource.UploadResource.modal.html',
                backdrop:'static',
                controller:UploadResourceModalCtrl,
                resolve: {
                     parentid: function() {
                         return $scope.parentid;
                     },
		            id: function() {
		                return $scope.schedule.id;
		            }
                }
            }).result.then(
                    function(){
                    	readyresource($scope.schedule.id);
                    }
                );
            
        };*/
      //上传资源控制器
       /* var UploadResourceModalCtrl = function($modalInstance,growl,parentid,id,$scope){
        	$scope.file ;
        	$scope.onFileSelect = function($files) {

                $scope.file = $files;
                
                if ($scope.uploadRightAway) {
                    $scope.startUpload();
                }
                for(var i=0;i< $scope.file.length;i++){
                	
               
                new upload($scope.file[i],i,'',$scope.progress,function(i){console.log('success' , i)},function(){alert('failed')})
                }
        	}
        	var upload = function(blobOrFile,index,url,progress,success,failed){
        		  var xhr = new XMLHttpRequest();
        		  xhr.open('POST', url, true);
        		  xhr.onload = function(e){success(index);};
        		  xhr.upload.onprogress = function(e) {
        		    if (e.lengthComputable) {
        				progress((e.loaded / e.total) * 100,index)
        		    }
        		  };
        		  xhr.send(blobOrFile);
        	};
        	
        	$scope.progress = function(e,index){
        		$scope.file[index].progress = e; 
        		console.log($scope.file[index].progress);
        	}
            //确定设置
            $scope.ok = function(){
	            var url = config.backend.ip + config.backend.base + 'rest/myClassRoom/upload';
	            $scope.upload = $upload.upload({
	                url: url,
	                data: {parentid:'1',
	                		id:id
	                },
	                file:$scope.file
	            }).progress(function(evt) {
	                $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
	                console.log('percent: ' + $scope.progress);
	            }).success(function(data, status, headers, config) {
	                if(data == "1"){
	                    growl.addSuccessMessage("上传成功");
	                    $modalInstance.close(true);
	                   // readyresource($scope.schedule.id);
	                    
	                    return true;
	                }else{
	                    growl.addErrorMessage("上传失败");
	                    $modalInstance.close(true);
	                }
	                 
	            }).error(function(){
	                growl.addErrorMessage("上传失败");
	            });
            };
            //取消设置
            $scope.cancel = function(){
                $modalInstance.close();
            };
        };*/
        //课前资料显示
    	var readyresource = function(keywords){
    		ScheduleService.showResourceBeforeClass(keywords).then(
                    function(data){
                    	console.log(data);
                    	$scope.curriculumMaterialsListparam = data.data;
                    	$scope.curriculumMaterialsList=[];
                    	if($scope.curriculumMaterialsListparam.length<=3){
                    		$scope.curriculumMaterialsList=$scope.curriculumMaterialsListparam;
                    		$scope.more=false;
                    	}else{
                    		$scope.curriculumMaterialsList.push($scope.curriculumMaterialsListparam[0]);
                        	$scope.curriculumMaterialsList.push($scope.curriculumMaterialsListparam[1]);
                        	$scope.curriculumMaterialsList.push($scope.curriculumMaterialsListparam[2]);
                        	$scope.more=true;
                    	}
                    	
                    },function(){
                    	 //处理失败后操作
                      //  alert("添加失败!");
                    }
                );
    	}
    	
    	//课后资料展示
    	var afterclassresource = function(keywords){
    		ScheduleService.showVideoAfterClass(keywords).then(
                    function(data){
                    	console.log(data);
                    	$scope.afterclassresourceList = data.data;
                    },function(){
                    	 //处理失败后操作
                    //    alert("添加失败!");
                    }
                );
    	}
    	
    	//课后智慧教室资料展示
    	var wisclassroomresource = function(keywords){
    		ScheduleService.showWisclassroomResource(keywords).then(
                    function(data){
                    	console.log(data);
                    	$scope.wisclassroomfloderList = data.data;
                    },function(){
                    	 //处理失败后操作
                    //    alert("添加失败!");
                    }
                );
    	}
    	 //初始化加载树
        var dirTrees = function(){
        	ScheduleService.dirTrees().then(
                 function(data){
                     $scope.areaTree = data;
                 },
                 function(){

                 }
             );
         };
    	$scope.okEditOtherObject = function(keywords){
    		ScheduleService.selectCurriculumMaterials(keywords).then(
                    function(data){
                    	if(data != null && data.data　!= null){
                    		if(data.data.scopename){
                    			$scope.showEdit = true;
                    			$scope.editinfoscopename = false;
                    		}else{
                    			$scope.showEdit = false;
                    			$scope.editinfoscopename = true;
                    		}
                    		if(data.data.gradename){
                    			$scope.showEdit2 = true;
                        		$scope.editinfogradename1 = false;
                    		}else{
                    			$scope.showEdit2 = false;
                        		$scope.editinfogradename1 = true;
                    		}
                    		/*if(data.data.subjectname){
                    			$scope.showEditsubjec = true;
                    		}*/
                    		console.log("data.data.gradenamedata.data.gradenamedata.data.gradenamedata.data.gradename")
                    		console.log(data.data.gradename)
                    		console.log(data);
                        	$scope.schedule.subjectname=data.data.subjectname;
                        	/*$scope.schedule.scopename=data.data.scopename;*/
                        	$scope.editModalInput.scopename=data.data.scopename;
                        	$scope.editModalInput.gradename=data.data.gradename;
                        	/*$scope.schedule.gradename=data.data.gradename;*/
                        	$scope.schedule.introduce=data.data.introduce;
                        	$scope.schedule.imageurl=data.data.imageurl;
                        	$scope.schedule.scopename = $scope.transfer($scope.subjectList,data.data.scopename);
                        	$scope.schedule.gradename = $scope.transfer($scope.gradeList,data.data.gradename);
                        	console.log("schedule.gradename schedule.gradename schedule.gradename schedule.gradename")
                    		console.log($scope.schedule.gradename);
                        	console.log($scope.gradeList);
                    	}
                    	
                    },function(){
                    	 //处理失败后操作
                  //      alert("添加失败!");
                    }
                );
    		
    	}
        $scope.initSearchData = function(){
       	 //初始化学科
        	ScheduleService.code('subject').then(
               function(data){
                   $scope.subjectList = data;
                   $scope.subject = data[0].value;
               },
               function(){

               }
           );
       
           //初始化阶段
        	ScheduleService.code('grade').then(
               function(data){
                   $scope.gradeList = data;
                   console.log('$scope.gradeList');
                   console.log($scope.gradeList);
                   $scope.grade = data[0].value;
               },
               function(){

               }
           );
       }
    	
    	//编辑课前资源名称
    	 $scope.okEditResourcename = function(schedule){
        	if(schedule== undefined){
        		return;
        	}
        	var keywords = {
        			"readyresourcename":schedule.readyresourcename,
                    "readyresourcid":schedule.readyresourcid
                };
        	ScheduleService.updateResourcename(keywords).then(
                    function(data){
                    	
                    	
                    },function(){
                    	 //处理失败后操作
                        alert("添加失败!");
                    }
                );
        };
        $scope.okEdit = function(subjectname){
        	if(subjectname== undefined){
        		return;
        	}
        	var keywords = {
                    "subjectname":subjectname,
                    "scopename":'',
                    "gradename":'',
                    "introduce":'',
                    "curriculumid":$scope.schedule.id
                };
        	ScheduleService.updateCurriculum(keywords).then(
                    function(data){
                    	
                    	
                    },function(){
                    	 //处理失败后操作
                        alert("添加失败!");
                    }
                );
        };
        
        $scope.okEditscopename = function(scopename){
        	$scope.schedule.scopename = $scope.transfer($scope.subjectList,scopename);
        	if(scopename== undefined){
        		return;
        	}
        	var keywords = {
                    "subjectname":'',
                    "scopename":scopename,
                    "gradename":'',
                    "introduce":'',
                    "curriculumid":$scope.schedule.id
                };
        	ScheduleService.updateCurriculum(keywords).then(
                    function(data){
                    	
                    	
                    },function(){
                    	 //处理失败后操作
                        alert("添加失败!");
                    }
                );
        };
        
        $scope.okEditgradename = function(gradename){
        	$scope.schedule.gradename = $scope.transfer($scope.gradeList,gradename);
        	if(gradename== undefined){
        		return;
        	}
        	var keywords = {
                    "subjectname":'',
                    "scopename":'',
                    "gradename":gradename,
                    "introduce":'',
                    "curriculumid":$scope.schedule.id
                };
        	ScheduleService.updateCurriculum(keywords).then(
                    function(data){
                    	
                    	
                    },function(){
                    	 //处理失败后操作
                        alert("添加失败!");
                    }
                );
        };
        
        $scope.okEditintroduce = function(introduce){
        	if(introduce== undefined){
        		return;
        	}
        	var keywords = {
                    "subjectname":'',
                    "scopename":'',
                    "gradename":'',
                    "introduce":introduce,
                    "curriculumid":$scope.schedule.id
                };
        	ScheduleService.updateCurriculum(keywords).then(
                    function(data){
                    	
                    	
                    },function(){
                    	 //处理失败后操作
                        alert("添加失败!");
                    }
                );
        };
      //编辑直播课程Modal控制器
        var editsubjectnameCtrl = function ($scope, $modalInstance, schedule, growl) {
            $scope.schedule = angular.copy(schedule);
            


            //保存
            $scope.save = function () {
                    /*var data = {
                        date: $scope.live.date === undefined?'':$scope.live.date,
                        sameclass: $scope.live.sameclass === undefined?'':$scope.live.sameclass,
                        areaid: $scope.live.areaid === undefined?'':$scope.live.areaid,
                        userid: $scope.live.userid === undefined?'':$scope.live.userid,
                        classname: $scope.live.classname === undefined?'':$scope.live.classname,
                        weeks: $scope.live.weeks === undefined?'':$scope.live.weeks,
                        weekdate: $scope.live.weekdate === undefined?'':$scope.live.weekdate,
                        subject: $scope.live.subject === undefined?'':$scope.live.subject,
                        subjectattribute: $scope.live.subjectattribute === undefined?'':$scope.live.subjectattribute,
                        coursedesc: $scope.live.coursedesc === undefined?'':$scope.live.coursedesc
                    };
                    ScheduleService.LiveEditSave(data).then(
                        function(data){
                        	if(data.id === '1'){
                                growl.addSuccessMessage(data.operation);
                                $modalInstance.close(true);
                            }
                            if(data.id === '0'){
                                growl.addErrorMessage(data.operation);
                            }
                        },function(){
                        	 //处理失败后操作
                            alert("添加失败!");
                        }
                    );*/
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        };
       /* //初始化加载树
        var userTrees = function(keywords){
            TreeService.systemTree(keywords).then(
                function(data){
                    $scope.areaTree = data;
                },
                function(){

                }
            );
        };*/
        //editcontent
        $scope.editcontent = function(index){
            $scope.editconditionpencil = index;
        }
        //设置学生是否可见
        $scope.visibility = function(curriculum){
        	 if(curriculum == undefined){
         		return;
         	}
        	if(curriculum.resourcevisable==='0'){
        		 curriculum.resourcevisable='1';
        	} else{
        		curriculum.resourcevisable='0';
        	}
        	var keywords = {
        			"resourcevisable":curriculum.resourcevisable,
                    "readyresourcuuid":curriculum.readyresourcuuid,
                    "curriculumid":curriculum.curriculumid
                };
        	ScheduleService.setupVisibilityClassReady(keywords).then(
                    function(data){
                    	growl.addSuccessMessage("上传成功");
                    	
                    },function(){
                    	 //处理失败后操作
                    	growl.addErrorMessage("设置失败");
                    }
                );
        };
      //课后生成资料设置学生是否可见
        $scope.afterclassvisibility = function(curriculum){
        	if(curriculum == undefined){
         		return;
         	}
        	if(curriculum.resourcevisable==='1'){
        		 curriculum.resourcevisable='0';
        	} else{
        		curriculum.resourcevisable='1';
        	}
        	var keywords = {
        			"resourcevisable":curriculum.resourcevisable,
                    "floder":curriculum.floder,
                    "curriculumid":curriculum.curriculumid
                };
        	ScheduleService.afterclassvisibility(keywords).then(
                    function(data){
                    	growl.addSuccessMessage("设置成功");
                    	
                    },function(){
                    	 //处理失败后操作
                    	growl.addErrorMessage("设置失败");
                    }
                );
        };
        
      //智慧教室学生是否可见
        $scope.setafterclassvisibility = function(curriculum){
        	if(curriculum == undefined){
         		return;
         	}
        	if(curriculum.resourcevisable==='1'){
        		 curriculum.resourcevisable='0';
        	} else{
        		curriculum.resourcevisable='1';
        	}
        	var keywords = {
        			"resourcevisable":curriculum.resourcevisable,
                    "id":curriculum.id,
                    "curriculumid":curriculum.curriculumid
                };
        	ScheduleService.setafterclassvisibility(keywords).then(
                    function(data){
                    	growl.addSuccessMessage("设置成功");
                    	
                    },function(){
                    	 //处理失败后操作
                    	growl.addErrorMessage("设置失败");
                    }
                );
        };
        //是否收藏课前资源
        $scope.collectReadyResource = function(curriculum){
         //   $scope.resourcecollection = !$scope.resourcecollection
            if(curriculum == undefined){
         		return;
         	}
        	if(curriculum.resourcecollection==='1' ){
        		 curriculum.resourcecollection='0';
        	} else{
        		curriculum.resourcecollection='1';
        	}
        	var keywords = {
        			"storetype":"1",
                    "storeid":curriculum.readyresourcid,
                    "curriculumid":curriculum.curriculumid
                };
        	ScheduleService.collectPrepareDate(keywords).then(
                    function(data){
                    	 growl.addSuccessMessage("设置成功");
                    	
                    },function(){
                    	 //处理失败后操作
                    	growl.addErrorMessage("设置失败");
                    }
                );
        };
      //是否收藏录播机课后资源
        $scope.collectAfterResource = function(curriculum){
         //   $scope.resourcecollection = !$scope.resourcecollection
            if(curriculum == undefined){
         		return;
         	}
        	if(curriculum.resourcecollection==='1' ){
        		 curriculum.resourcecollection='0';
        	} else{
        		curriculum.resourcecollection='1';
        	}
        	var keywords = {
        			"storetype":"2",
                    "storeid":curriculum.floder,
                    "curriculumid":curriculum.curriculumid
                };
        	ScheduleService.collectPrepareDate(keywords).then(
                    function(data){
                    	 growl.addSuccessMessage("设置成功");
                    	
                    },function(){
                    	 //处理失败后操作
                    	growl.addErrorMessage("设置失败");
                    }
                );
        };
        
      //是否收藏智慧教室
        $scope.setcollectAfterResource = function(curriculum){
         //   $scope.resourcecollection = !$scope.resourcecollection
            if(curriculum == undefined){
         		return;
         	}
        	if(curriculum.resourcecollection==='1' ){
        		 curriculum.resourcecollection='0';
        	} else{
        		curriculum.resourcecollection='1';
        	}
        	var keywords = {
        			"storetype":"3",
                    "storeid":curriculum.id,
                    "curriculumid":curriculum.curriculumid,
                    "resourcecollection":curriculum.resourcecollection
                };
        	ScheduleService.setcollectAfterResource(keywords).then(
                    function(data){
                    	 growl.addSuccessMessage("设置成功");
                    	
                    },function(){
                    	 //处理失败后操作
                    	growl.addErrorMessage("设置失败");
                    }
                );
        };
        //鼠标移上去的时候出现操作按钮
        $scope.showOperation = function(){
            $scope.showOperationmodal = true;
        };
      //删除课前资源
        $scope.moveFile = function(curriculum){
        	if(curriculum == undefined){
         		return;
         	}
        	var keywords = {
        			"readyresourcuuid":curriculum.readyresourcuuid,
                    "curriculumid":curriculum.curriculumid
                };
        	ScheduleService.moveClassReady(keywords).then(
                    function(data){
                    //	$modalInstance.close(true);
                    	readyresource($scope.schedule.id);
                    	
                    },function(){
                    	 //处理失败后操作
                        growl.addErrorMessage("删除失败");
                        
                    }
                );
        };
        
      //编辑课前资源名称
       /* $scope.editResourceName = function(curriculum){
        	 if(curriculum == undefined){
         		return;
         	}
        	var keywords = {
                    "readyresourcid":curriculum.readyresourcid
                };
        	ScheduleService.moveClassReady(keywords).then(
                    function(data){
                    	$modalInstance.close(true);
                    	 alert("删除成功!");
                    	
                    },function(){
                    	 //处理失败后操作
                        alert("删除失败!");
                    }
                );
        };*/
        $scope.visibility1 = function(){
            $scope.visibilityModal1 = !$scope.visibilityModal1;
        };
       
      
        //鼠标离开的时候隐藏操作按钮
        $scope.hiddenOperation = function(){
            $scope.showOperationmodal = false;
        };
        //照片墙弹框界面
        $scope.photoWall = function(temp){
            var modalInstance = $modal.open({
                templateUrl: 'PersonalInformation/schedule/PersonalInformation.photoWall.Modal.html',
                
                controller: photoWallModalCtrl,
                resolve: {
                    wallList:function(){
                    	return temp;
                    },
                    user:function(){
                    	return $scope.global.user;
                    }
                }
            }).result.then(
                function(data){
                }
            );
        };
        var photoWallModalCtrl = function($scope,$modalInstance,growl,wallList,user){ 
        	$.each(wallList.data,function(i,video){
        		if(video.nametype == 'Videos'){
        			video.fileurl = $sce.trustAsResourceUrl(video.fileurl);
        		}
             });
        	$scope.user = user;
        	$scope.type = wallList.source;
        	$scope.photo = wallList.data;
        	$scope.activePhotoShow = wallList.data[0];
        	$scope.activePhoto = 0;
        	//
        	$scope.resourcevisable=wallList.data[0].resourcevisable;
        	$scope.resourcecollection=wallList.data[0].resourcecollection;
        //	$scope.type=""
        	$scope.ToactivePhoto = function(index){
        		$scope.activePhoto = index;
        		$scope.activePhotoShow = wallList.data[index];
        		$scope.resourcevisable=wallList.data[index].resourcevisable;
            	$scope.resourcecollection=wallList.data[index].resourcecollection;
        		/*$scope.activePhotoShow = angular.copy(wallList[index]);*/
        	};
        	$scope.cancel = function(){
        		//删除video便签
        		$modalInstance.dismiss('cancel');
        	}
        	//设置录播机单一文件可见性（单一）
        	$scope.oneafterclassvisibility=function (){
            	if($scope.resourcevisable==='1'){
            		$scope.resourcevisable='0';
            	} else{
            		$scope.resourcevisable='1';
            	}
            	if($scope.type == '2'){
            	var keywords = {
            			"resourcevisable":$scope.resourcevisable,
                        "readyresourcid":$scope.activePhotoShow.id,
                        "curriculumid":$scope.activePhotoShow.curriculumid,
                        "floder":$scope.activePhotoShow.floder
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
                			"resourcevisable":$scope.resourcevisable,
                            "id":$scope.activePhotoShow.id
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
        	$scope.onecollectAfterResource=function (){
            	if($scope.resourcecollection==='1' ){
            		$scope.resourcecollection='0';
            	} else{
            		$scope.resourcecollection='1';
            	}
            	if($scope.type == '2'){
            	var keywords = {
            			"storetype":"2",
                        "storeid":$scope.activePhotoShow.id,
                        "curriculumid":$scope.activePhotoShow.curriculumid,
                        "floder":$scope.activePhotoShow.floder,
                        "resourcecollection":$scope.resourcecollection,
                        "name":$scope.activePhotoShow.name,
                        "subject":$scope.activePhotoShow.subject,
                        "grade":$scope.activePhotoShow.grade,
                        "id":$scope.activePhotoShow.id,
                        "transPath":$scope.activePhotoShow.transPath,
                        "transFlag":$scope.activePhotoShow.transFlag
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
                             "storeid":$scope.activePhotoShow.id,
                             "curriculumid":$scope.activePhotoShow.curriculumid,
                             "resourcecollection":$scope.resourcecollection,
                             "name":$scope.activePhotoShow.name,
                             "fileurl":$scope.activePhotoShow.fileurl,
                             "size":$scope.activePhotoShow.size,
                             "id":$scope.activePhotoShow.id,
                             "transPath":$scope.activePhotoShow.transPath,
                             "transFlag":$scope.activePhotoShow.transFlag
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
        }
      
        //新建文件弹框界面
        $scope.AddFileDoc = function(schedule){
            var modalInstance = $modal.open({
                templateUrl: 'PersonalInformation/schedule/PersonalInformation.AddFileDoc.Modal.html',
                backdrop:'static',
                controller: AddFileDocModalCtrl,
                resolve: {
                    tree:function(){
                        return $scope.areaTree
                    },
                    schedule:function(){
                        return $scope.schedule
                    },
                    parentid: function() {
                        return $scope.parentid;
                    },
		            id: function() {
		                return $scope.schedule.id;
		            }
                }
            }).result.then(
                function(data){
                	readyresource($scope.schedule.id);
                }
            );
        };
        var AddFileDocModalCtrl = function($scope,$modalInstance,growl,$filter,$interval,growl,tree,schedule,parentid,id){
        	 $scope.schedule = schedule;
             var i = 0;
             var j=0;
             $scope.checkedTreeNode = [];
             $scope.areaTree=tree;
             $scope.SelectItem = [];
             //点击选中时设置控制的单选按钮状态
             var selectedClassrooms = [];
             $scope.areaList=[];
//          上传-------------------------------------
             $scope.file ;
         	$scope.onFileSelect = function($files) {

                 $scope.file = $files;
                 
                 if ($scope.uploadRightAway) {
                     $scope.startUpload();
                 }
                 
                 for (var i = 0; i < $scope.file.length; i++) {
						$scope.file[i].progress = 0;
					}

                 var stop = $interval(function() {
					var count =0;
					for (var i = 0; i < $scope.file.length; i++) {
						if($scope.file[i].progress<100){
							$scope.file[i].progress +=10 ;
						}
						count +=$scope.file[i].progress
					}
					if($scope.file.length*100<=count){
						 $interval.cancel(stop);
					}
					console.log("interval",count);
			      }, 100);
         	}
         	
         	$scope.remove = function(index) {
				$scope.file.splice(index, 1);
				if($scope.file.length <=0){
					$scope.file = null;
				}
			}
         	 //确定设置
            $scope.addUploadFile = function(){
            	j++;
            	if(j == 1){
            		var url = config.backend.ip + config.backend.base + 'rest/myClassRoom/upload';
    	            $scope.upload = $upload.upload({
    	                url: url,
    	                data: {parentid:'1',
    	                		id:id
    	                },
    	                file:$scope.file
    	            }).progress(function(evt) {
    	                $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
    	                console.log('percent: ' + $scope.progress);
    	            }).success(function(data, status, headers, config) {
    	                if(data == "1"){
    	                    growl.addSuccessMessage("上传成功");
    	                    $modalInstance.close(true);
    	                   // readyresource($scope.schedule.id);
    	                    
    	                    return true;
    	                }else{
    	                    growl.addErrorMessage("上传失败");
    	                    $modalInstance.close(true);
    	                }
    	                 
    	            }).error(function(){
    	                growl.addErrorMessage("上传失败");
    	            });
            	}
            };
            //取消设置
            $scope.cancelUpload = function(){
                $modalInstance.close();
            };
//           上传完毕-------------------------------------
            $scope.uploadingModal = false;
            $scope.ImportDataModal = true;

            //导入资料
            $scope.ImportData = function(){
                $scope.ImportDataModal = true;
                $scope.uploadingModal = false;
            };
            //上传
            $scope.uploading = function(){
                $scope.uploadingModal = true;
                $scope.ImportDataModal = false;
            };
          //点击树文件触发的事件
            $scope.setActiveFolder = function(node){
                $scope.ActiveFolder = node;
            };
          
            //确定移动保存到
            $scope.MoveSave = function(){
            	$scope.areaList = [];
            	getSelectClassrooms(tree[0]);
            	/*var checkedList = $filter('filter')($scope.resourceList, {checked:true});*/
            	ScheduleService.importDateForCurriculum($scope.areaList,$scope.schedule.id).then(function(data){
            		if(data!=0){
            			growl.addSuccessMessage("移动成功");
            			$modalInstance.close();
            		}else{
            			growl.addErrorMessage("移动失败");
            		}
            		$modalInstance.close();
            	});
            };
           $scope.cancel = function(){
        	   $modalInstance.close();
           }
          //验证父节点下的子节点是否全被选中的，返回值为true/false
            $scope.checkParentInnerChildIsHalfChecked = function(_node){
                var checkParentInnerChildIsHalfCheckedFlag = false;
                var i = 0;
                if(_node.nodes){
                    $.each(_node.nodes, function(index, nodeCheck){
                    	//必须判断半选标志也为false，才可进行未选中记录技术，否则，会使根节点的半选状态失效（防止处理时出现纰漏）
                    	//范围管理没事，是因为根节点下有选中的子节点，所以不满足根结点字条数与未选中条数相等的情况。
                    	//所以在判断未选中条数时，要把未勾选和未半选两种情况都满足作为判断条件才可以。
                        if(!nodeCheck.isSelected && !nodeCheck.halfFlag){
                        	checkParentInnerChildIsHalfCheckedFlag = true;
                        	i ++;
                        }
                        
                        //只要有半选中状态的记录，就认为有未选中的。
                        if(nodeCheck.halfFlag){
                        	checkParentInnerChildIsHalfCheckedFlag = true;
                        }
                        
                        //如果有选中，并且未全选的，则表示未全选
                        if(nodeCheck.isSelected && !nodeCheck.isAllUnchecked){
                        	checkParentInnerChildIsHalfCheckedFlag = true;
                        }
                    });
                }
                //对于只有一个选项的记录，则不存在选中一半的情况(拿变量i判断不准，有多个子节点选中，只剩下一个未选中的记录，然后操作跟只有一个未选中的情况相同。所以拿父节点的长度比较合适)
                if(_node.nodes.length == 1){
                	checkParentInnerChildIsHalfCheckedFlag = false;
                	
                	_node.isAllUnchecked = false; //对于只有一个选项的记录，则不存在选中一半的情况,必须做选中未选中处理
                	
                	/*if(i == 1){ //对于只有一个选项的记录，则不存在选中一半的情况,必须做选中未选中处理
                		_node.isAllUnchecked = false;
                	} else {
                		_node.isAllUnchecked = true;	
                	}*/
                } else if(_node.nodes.length == i){ //过滤掉全没选中的情况
                	checkParentInnerChildIsHalfCheckedFlag = false;
                	_node.isAllUnchecked = true;
                } else if(_node.nodes.length>0 && i==0){ //过滤掉全没选中的情况
                	checkParentInnerChildIsHalfCheckedFlag = false;
                	_node.isAllChecked = true;
                } else {
                	_node.isAllUnchecked = false; //不加的话，会造成，子节点全选后，父节点不自动勾选
                }
                
                
                return checkParentInnerChildIsHalfCheckedFlag;
            };
            
            //树操作（自上而下）
            $scope.checkAllParent = function(node, value){
                console.log(node);
                console.log($scope.areaTree);

                if(angular.isDefined($scope.areaTree)){
                    $.each($scope.areaTree, function(index, atree){
                        if(atree.nodes){
                            $.each(atree.nodes, function(index, _node){
                                if(node === _node){
                                        if($scope.checkParentInnerChildIsChecked(atree) && atree.isSelected){
                                        	//如果没有全部选中的话，将添加halfFlag字段(如果底下有被选中的节点并且是已选中状态，则判断是否全部节点都选中)
                                        	if($scope.checkParentInnerChildIsHalfChecked(atree)){
                                        		atree.halfFlag = true;
                                        		
                                        		//只有父节点下的子节点全部选中，才给父节点选中,否则算父节点没选中
                                        		atree.isSelected = !atree.isSelected;
                                        	} else {
                                        		atree.halfFlag = false;
                                        	}
                                        	
                                        } else {
                                        	
//                                        	atree.isSelected = !atree.isSelected;
                                        	
                                        	//如果没有全部选中的话，将添加halfFlag字段（如果没有子节点选中，并且状态为未选中，则当匹配上之后，赋值为选中状态，同时判断，
                                        		//如果是父节点的话，是否其子节点都被选中）
                                        	if($scope.checkParentInnerChildIsHalfChecked(atree)){
                                        		atree.halfFlag = true;
                                        	} else {
                                        		atree.halfFlag = false;
                                        		
                                        		//如果全不选的话，就不做选中不选中的处理
                                        		if((!atree.isAllUnchecked) || atree.isAllChecked) {
                                        			//只有父节点下的子节点全部选中，才给父节点选中
                                            		atree.isSelected = !atree.isSelected;
                                        		}
                                        	}
                                        }
                                        console.log(atree.name);
                                        console.log(atree.isSelected);
                                } else {
                                    $.each(_node.nodes, function(index, __node){
                                        console.log(__node);
                                        if(node === __node){
                                                if ($scope.checkParentInnerChildIsChecked(_node) && _node.isSelected) {
                                                	//如果没有全部选中的话，将添加halfFlag字段
                                                	if($scope.checkParentInnerChildIsHalfChecked(_node)){
                                                		_node.halfFlag = true;
                                                		
                                                		//只有父节点下的子节点全部选中，才给父节点选中,否则算父节点没选中
                                                		_node.isSelected = !(_node.isSelected);
                                                	} else {
                                                		_node.halfFlag = false;
                                                	}
                                                	
                                                } else {
                                                	
//                                                    _node.isSelected = !(_node.isSelected);
                                                    
                                                    //如果没有全部选中的话，将添加halfFlag字段
                                                	if($scope.checkParentInnerChildIsHalfChecked(_node)){
                                                		_node.halfFlag = true;
                                                	} else {
                                                		_node.halfFlag = false;
                                                		
                                                		//如果全不选的话，就不做选中不选中的处理
                                                		if((!_node.isAllUnchecked) || _node.isAllChecked) {
    	                                            		//只有父节点下的子节点全部选中，才给父节点选中
    	                                            		_node.isSelected = !(_node.isSelected);
                                                		}
                                                	}
                                                }
                                                console.log(_node.name);
                                                console.log(_node.isSelected);
                                                console.log("找到的话，把找到的父节点框，作为查询条件，再查询父节点的父节点");
                                                //找到的话，把找到的父节点框，作为查询条件，再查询父节点的父节点
                                                $scope.checkAllParent(_node, node.isSelected);
                                        } else {
                                            $scope.getNodePostion(node,__node, node.isSelected);
                                        }
                                    });
                                }

                            });
                        }
                    });

                }

            };
            //反向递归查找节点
            $scope.getNodePostion = function(node, _node, value){

                if(_node.nodes) {
                    $.each(_node.nodes, function (index, __node) {
                        if (node === __node) {
                            if ($scope.checkParentInnerChildIsChecked(_node) && _node.isSelected) {
                            } else {
                                _node.isSelected = !(_node.isSelected);
                            }
                            console.log(_node.name);
                            //找到的话，把找到的父节点框，作为查询条件，再查询父节点的父节点
                            $scope.checkAllParent(_node, node.isSelected);
                        } else {
                            $scope.getNodePostion(node, __node);
                        }
                    });
                }
            };
            //验证父节点下的子节点是否有被选中的，返回值为true/false
            $scope.checkParentInnerChildIsChecked = function(_node){
                var checkParentInnerChildIsCheckedFlag = false;
                if(_node.nodes){
                    $.each(_node.nodes, function(index, nodeCheck){
                        if(nodeCheck.isSelected){
                            checkParentInnerChildIsCheckedFlag = true;
                        }
                    });
                }
                return checkParentInnerChildIsCheckedFlag;
            };
            //结构树选择（自下而上，自上而下）
            $scope.checkAllTrees = function(node, value){
            	$scope.applyEnsure = false;
                //结构树选择（自上而下）
                $scope.checkAllApplys(node, value);

                //延时加载（结构树选择（自下而上）
                $timeout(function(){
                    $scope.checkAllParent(node);
                },200);

            };
            //自上而下，遍历树
            $scope.checkAllApplys = function (node, value) {
                node.isSelected = node.isSelected || false;
                node.isSelected = value === undefined ? !node.isSelected : value;
              
                //如果选中的话，则将halfFlag的标志位改为false，去掉半选中样式
                if(node.halfFlag){
                	node.halfFlag = false;
                }
                if(node.isfolder != '0'){
                    $scope.allItems(node);
                }
                if (node.nodes) {
                    $.each(node.nodes, function (index, _node) {
                        $scope.checkAllApplys(_node, (node.isSelected));
                        $scope.num = index;
                    });
                }
            };
            // 做数组的添加和删除
            $scope.allItems = function (tree) {
                console.log(tree);
//                alert(tree.attribute);
                var a='';
                if (tree.isSelected) {
                    if($scope.SelectItem.length>0){
                        $.each($scope.SelectItem,function(index_1,Items){
                            if(Items.areaid===tree.id){
//                                    alert('id重复');
                                a=index_1;
                            }
                        });
                    }else{
//                            alert('进入了push');
                        $scope.SelectItem.push(
                            {
                                "areaid":tree.id
                            }
                        );
                        a=1;
                    }
                    if(a===''){
                        $scope.SelectItem.push(
                            {
                                "areaid":tree.id
                            }
                        );
                    }

                } else {
//                    alert('进的splice');
                    $.each($scope.SelectItem, function (index, item) {
                        if (item.areaid === tree.id) {
//                            alert('做了删除');
                            $scope.SelectItem.splice(index,1);
                            return false;
                        }
                    });
                }
                if ($scope.SelectItem.length > 0) {
                    console.log($scope.SelectItem.length);
                }else{
                  //  alert('对不起未选中');
                }
            };

            var getSelectClassrooms = function(node){
                if(node.isfolder != '0' && node.isSelected){
                    $scope.areaList.push(
                        {
                            "resource_uuid": node.resource_uuid
                        }
                    );
                }
                if(node.nodes){
                    $.each(node.nodes, function(index, _node){
                        getSelectClassrooms(_node);
                    });
                }
            };
        };

        $scope.transfer = function(templist,temp){
        	for(var i in templist){
        		if(temp == templist[i].value){
        			return templist[i].name;
        		}
        	}
        }
        var init = function(){
//        	var resourcename = window.location.href.split("?class=");
//            var resourcenameHref = JSON.parse(resourcename[resourcename.length - 1]);
            $scope.schedule = JSON.parse($location.search().Jclass);
        //    $scope.curriculumMaterials=[];
        //    $scope.curriculumMaterials.id=$scope.schedule.id;
            $scope.schedule.classtime=$scope.schedule.date+' '+$scope.schedule.starttime;
           // $scope.curriculumMaterials.subjectname=$scope.schedule.subject;
         //   $scope.curriculumMaterials.username=$scope.schedule.username;
         //   $scope.curriculumMaterials.deptname=$scope.schedule.deptname;
            $scope.schedule.datedetail='第'+$scope.schedule.weeks+'周'+' '+'周'+$scope.schedule.weekdate+' '+'第'+$scope.schedule.sameclass+'节';
         //   $scope.curriculumMaterials.areaname=$scope.schedule.areaname;
         //   $scope.curriculumMaterials.imageurl=$scope.schedule.imageurl;
//        	$scope.classinfo = JSON.parse($stateParams.classinfo);
            $scope.collectShowModal = false;
            $scope.visibilityModal1 = false;
            //$scope.visibilityModal = false;
            $scope.showOperationmodal = false;
            $scope.editModalInput=[];
            $scope.checkedTreeNode = [];
            $scope.editModalInput.subjectname = $scope.schedule.subjectname;
         //   $scope.editModalInput.scopename = $scope.schedule.scopename
           /* $scope. keywords = {
                    "subjectname":'',
                    "scopename":'',
                    "gradename":'',
                    "introduce":'',
                    "curriculumid":''
                };*/
          //  userTrees('trees');
            $scope.initSearchData();
            $scope.okEditOtherObject($scope.schedule.id);
            readyresource($scope.schedule.id);
            afterclassresource($scope.schedule.id);
            wisclassroomresource($scope.schedule.id);
            dirTrees();
            $scope.showMore = false;
        };

        init();
      }]);
});
