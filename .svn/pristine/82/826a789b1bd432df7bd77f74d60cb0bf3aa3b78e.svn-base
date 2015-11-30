define(['app','config'], function (app,config) {
    app.registerController('videoDotCtrl', ['$scope','$modal','$location','TreeService','ResourceService','VideoService','growl',function($scope,$modal,$location,TreeService,ResourceService,VideoService,growl) {
		
    	// Hi,i'm from videoset.ctrl.js
    	$scope.$on('checkedVideos', function(e, data) {
    		alert(data);
			// alert("i got id");
			if(angular.isDefined(data)){
				$scope.dottingVideos = data;
			}
		});
    	$scope.$on('areaTree', function(e, data) {
			// alert("i got id");
			if(angular.isDefined(data)){
				$scope.areaTree = data;
			}
		});
    	$scope.titleFocus = function(){
    		//alert("focus");
    		videoCO.in().pause();
    	};
    	$scope.titleBlur = function(){
    		//alert("blur");
    		videoCO.in().play();
    	};
    	// 监视播放时间
		$scope.$watch('addDotTime', function(newValue,oldValue) {
			console.log("监视播放进度",arguments);
			angular.forEach($scope.dotInfos,function(v,k){
				if($scope.getLocalTime(v.dotTime) == newValue){
					$scope.tooltipDotTitle = v.description;
					setTimeout(function(){
						$("#point").trigger('mouseenter');
					},1);
					setTimeout(function(){
						$("#point").trigger('mouseleave');
					},3000);
					//alert("focus");
				}
			});
		}, true);
		// 将时间戳换成时间
    	 $scope.getLocalTime = function(nS) {   	  
           var t = new Date(parseInt(nS) * 1000); 
           return (t.getHours()<18?'0':'')+(t.getHours()-8)+':'+(t.getMinutes()>9?t.getMinutes():'0'+t.getMinutes())+':'+(t.getSeconds()>9?t.getSeconds():'0'+t.getSeconds());
              
        };     
        
        // 将时间换成时间戳
        $scope.transdate = function(endTime){
            var date=new Date();
            date.setFullYear(endTime.substring(0,4));
            date.setMonth(endTime.substring(5,7)-1);
            date.setDate(endTime.substring(8,10));
            date.setHours(endTime.substring(11,13));
            date.setMinutes(endTime.substring(14,16));
            date.setSeconds(endTime.substring(17,19));
            return Date.parse(date)/1000;
        };

    	// 初始化节点信息
        $scope.searchDotInfo = function(keywords){
        	VideoService.processList(keywords).then(
                function(data){
                    $scope.dotInfos = data;
                    console.log("$scope.dotInfos:",$scope.dotInfos);
                },
                function(code){
                    addErrorMessage("查询打点信息失败，错误码"+code);
                }
            );
        };

		 // 取消编辑视频打点信息
       // $scope.cancelEdit = function(index){
       // $scope.editModalInput = $scope.dotInfos[index].description;

       // }
       // 确定编辑视频打点信息
       $scope.okEdit = function(index,dotInfo){
             $scope.dotInfos[index].description = dotInfo.description;
             $scope.contentAdd = [];
		     $scope.contentAdd.push({
		         dotTime:dotInfo.dotTime,
				 description:dotInfo.description
		     });
		     $scope.dotInfoMod = ({
		         resourceid: $scope.dottingVideo[0].id,
			     content:$scope.contentAdd
		     });
             VideoService.addDotInfo($scope.dotInfoMod).then(function(data){
		         if(data.id==='1'){
		             growl.addSuccessMessage(data.content);
			         $scope.searchDotInfo($scope.dottingVideo[0].id);
			     }else{
			         growl.addErrorMessage(data.content); 
		         }
             },function(code){
             	//console.log("$scope.dotInfoMod",$scope.dotInfoMod);
             	//console.log("code",code);
             	//console.log("growl",growl);
             	growl.addErrorMessage("请求出错啦，错误代码"+code);
		     });	
       };
       // 删除节点
       $scope.deleteDot = function(index,id){
         //$scope.dotInfos.splice(index,1);
         //alert(id);
         $scope.dotInfoDel = [];
		   $scope.dotInfoDel.push(id);
		   VideoService.delDotInfo($scope.dotInfoDel).then(
		        function(data){
				    if(data.id === '1'){
					    growl.addSuccessMessage(data.content);
					    $scope.searchDotInfo($scope.dottingVideo[0].id);
					}else{
					    growl.addErrorMessage(data.content);
					}
				},
				function(code){
				    growl.addErrorMessage("请求出错啦，错误码"+code);
				}
		   );
		 //$scope.dotInfos.splice(index,1);
         $scope.searchDotInfo($scope.dottingVideo[0].id);
         console.log($scope.dotInfos);
       };

       // 添加节点信息
       $scope.addDotInfo = function(){
		   var t = $scope.addDotTime;
		   if(/[\d][\d]+:[\d][\d]:[\d][\d]/.test(t)){
			   var a = t.split(":");
			   t = parseInt(a[0])*3600+parseInt(a[1])*60+parseInt(a[2]);
			   if(videoCO.in().v_times[videoCO.in().v_times.length-1]<t){
				    return;   
		       }
		   }else{
			   growl.addErrorMessage("请输入正确的时间格式！\n(00:00:00)"); 
			   // alert("请输入正确的时间格式！\n(01:23:45)");
			   return;
		   }	   
		   if(!$scope.dotInfos){$scope.dotInfos=[]}
		   var ar = insertData($scope.dotInfos,{dotTime:t,description:$scope.addDotTitle},'dotTime')
       		if(ar){	
       			//$scope.dotInfoAdd = [];
 		        $scope.contentAdd = [];
		        $scope.contentAdd.push({
		            dotTime:t,
				    description:$scope.addDotTitle
		        });
		        $scope.dotInfoAdd = ({
		            resourceid: $scope.dottingVideo[0].id,
			        content:$scope.contentAdd,
			        videoLength:videoCO.in().v_times[videoCO.in().v_times.length-1]
		        });
                VideoService.addDotInfo($scope.dotInfoAdd).then(function(data){
		           if(data.id==='1'){
		        	   growl.addSuccessMessage(data.content);
			           $scope.searchDotInfo($scope.dottingVideo[0].id);
			        }else{
			        	growl.addErrorMessage(data.content); 
		            }
                },function(code){
                	growl.addErrorMessage("请求出错啦，错误代码"+code);
		        });	
       		 }else{
				 growl.addErrorMessage("时间节点重复，插入失败");
			 }
       };


       // 插入数据
       var insertData = function(ar,obj,id){
			if(ar.length>1){
				if(obj[id]==ar[ar.length-1][id]||ar[0][id]==obj[id]){if(obj[id]==ar[ar.length-1][id]){ar[ar.length-1]=obj}else{ar[0]=obj};return ar;}
				if(obj[id]<ar[ar.length-1][id]&&ar[0][id]<obj[id]){
					var s=0,e=ar.length-1,i=obj[id],m=0;
					while(e-s>1){
						if(i==ar[e][id]||i==ar[s][id]){if(i==ar[e][id]){ar[e]=obj}else{ar[s]=obj};return ar;}
						m=Math.floor((e+s)/2);
						if(i>ar[m][id]){
							s=m;
						}else{
							e=m;
						}
					}
					if(ar[s][id]==i||i==ar[e][id]){if(i==ar[e][id]){ar[e]=obj}else{ar[s]=obj};return ar;}
					return (ar.slice(0,s+1)).concat(obj,ar.slice(e));
					
				}else{
					if(ar[0][id]<obj[id]){
						ar.push(obj)	
					}else{
						ar.unshift(obj);
					}
				}
			}else if(ar.length==0){
				ar.push(obj);	
			}else{
				if(obj[id]==ar[0][id]){
					return [obj];

				}
				if(obj[id]>ar[0][id]){
					ar.push(obj)
				}else{
					ar.unshift(obj);
				}
			}
			return ar;
		};
		$scope.changeVideo = function(){
			
		};
		
		$scope.submitDotInfo = function(){
			var dotInfos = [];
			for(var i =0 ;i<$scope.dotInfos.length;i++){
				dotInfos.push({dotTime:$scope.dotInfos[i].dotTime,description:$scope.dotInfos[i].description})
			}
			VideoService.addDotInfo({"resourceid":$scope.dottingVideo[0].id,"content":dotInfos}).then(
                function(data){                  
                    console.log('onsubmit:',data);
                },
                function(code){
                	growl.addErrorMessage("提交");
                }
            );
			
		};
		// 初始化加载树
		$scope.searchVideoTreesList = function() {
			ResourceService.getVideoTrees().then(function(data) {
				$scope.videoTrees = data;
			}, function() {
				growl.addErrorMessage("查询视频出错了");
			});
		};
		
		$scope.initChecked = function(node) {
			$.each(node, function(index, data) {
				data.checked = false;
				if (data.nodes != null) {
					$scope.initChecked(data.nodes);
				}
			});
		};
		
		// 更换视频
		$scope.EditVideo = function(setOperation) {
			$scope.initChecked($scope.videoTrees);
			var modalInstance = $modal.open({
				templateUrl : 'PersonalInformation/video/PersonalVideo.videoCutting.modal.html',
				backdrop : 'static',
				// windowClass: 'modal-lg',
				controller : DottingResourceModalCtrl,
				resolve : {
					videoTrees : function() {
						return $scope.videoTrees;
					},
					setOperation : function(){
						return setOperation;
					}
				}
			}).result.then(
					function(dottingVideo){
						$scope.dottingVideo = dottingVideo;
						$scope.searchDotInfo($scope.dottingVideo[0].id);
						$scope.dealVideo(dottingVideo);
					}
                 );
		};
		var DottingResourceModalCtrl = function($modalInstance,$filter, $scope,$location, videoTrees,setOperation) {
			$scope.videoTrees = videoTrees;
			$scope.setOperation = setOperation;
			
			// 确定设置
			$scope.Save = function() {
				// $scope.$emit('checkedVideos', $scope.checkedVideos);
				$modalInstance.close($scope.dottingVideo);
			};
			// 取消设置
			$scope.cancel = function() {
				activeNode = [];
				$modalInstance.dismiss('cancel');
			};
			
			
			$scope.getCheckedNode = function(node){
				if(node.isfolder == '1' && node.checked){
					node.checked = false;
				}
				if (node.nodes) {
					$.each(node.nodes, function(index, _node) {
						$scope.getCheckedNode(_node);
					});
				}
			}
			$scope.setActiveFolder = function(node) {
				// 选择文件底色变化
				$scope.getCheckedNode($scope.videoTrees[0]);
				if (node.isfolder === '1') {
					var nvideo = []
					node.checked = true;
					nvideo.push(node);
					$scope.dottingVideo = nvideo;
				}
				
			};
		};
		
		// 视频处理
		$scope.dealVideo = function(dottingVideos){
			/*
			 * $scope.dottingVideos =
			 * ["http://192.168.12.167/3.mp4","http://192.168.12.167/VGA.mp4"];
			 */
			videoCO.in().init(dottingVideos);
			videoCO.in().callback = function(t){console.log(t);$scope.addDotTime = $scope.getLocalTime(t)}
		};
		var init = function(){
			$scope.searchVideoTreesList();
			$scope.dottingVideo = angular.fromJson($location.search().checkedVideo);
			// alert($scope.dottingVideos[0].id);
		    // alert(videos);
		 	//$scope.editModal ="通过记录生活，记录文化，记录历史，来实现自己继承文化的梦想。";
            //$scope.editModalInput = $scope.editModal;
            $scope.searchDotInfo($scope.dottingVideo[0].id);
            $scope.dealVideo($scope.dottingVideo);
		 };
		 init();
      }]);
});
