
define(['app','config'], function (app,config) {
    app.registerController('videoCutCtrl', ['$scope','$modal','$location','$timeout','$interval','VideoService','ResourceService','VideoService','growl',function($scope,$modal,$location,$timeout,$interval,VideoService,ResourceService,VideoService,growl) {
		
    	// $scope.list = tmpList;
  
        // Hi,i'm from videoset.ctrl.js
    	$scope.$on('checkedVideos', function(e, data) {
    		 alert(data);
			// alert("i got id");
			if(angular.isDefined(data)){
				$scope.cuttingVideos = data;
               
			}
		});
    	// 切换视频
    	$scope.changeVideo = function(resource,index){
    		$("#video_name").text(resource.title);
    		videoCut.in().showVideo(index);
    		videoCut.in().play();
    		console.log("resource:",resource);
    		console.log("index:",index);
    	};
		// 将时间戳换成时间
    	 $scope.getLocalTime = function(nS) {   	  
           var t = new Date(parseInt(nS) * 1000); 
           return (t.getHours()<18?'0':'')+(t.getHours()-8)+':'+(t.getMinutes()>9?t.getMinutes():'0'+t.getMinutes())+':'+(t.getSeconds()>9?t.getSeconds():'0'+t.getSeconds());
              
        }
	   $scope.showVideoTime = function(tstr,activeIndex){
		   $scope.videoTime = $scope.getLocalTime(tstr);
	   }
       // 删除节点
// $scope.deleteDot = function(index){
// $scope.dotInfos.splice(index,1);
// console.log($scope.dotInfos);
// }

       // 添加节点信息
       $scope.addCutInfo = function(){
		   if($scope.list.length>19){return;}
// 截图路径
		   var imgsrc = videoCut.in().videoList[videoCut.in().data[0].i].src;// +'_'+videoCut.in().data[videoCut.in().data.length-1].s+'.jpg'
		   imgsrc = imgsrc.slice(0,imgsrc.lastIndexOf('/')+1)+'temp/'+imgsrc.slice(imgsrc.lastIndexOf('/')+1,imgsrc.length)+'_'+Math.floor(videoCut.in().data[videoCut.in().data.length-1].s)+'.jpg';
		   
		   
		   
var c=document.getElementById("myCanvas");
var ctx=c.getContext("2d");
var video = videoCut.in().videoList[videoCut.in().activeI];
var scale = Math.min(320/video.videoWidth,180/video.videoHeight)
c.width = video.videoWidth*scale// video.videoWidth;
c.height = video.videoHeight*scale
ctx.drawImage(video,0,0,c.width,c.height);
if(c.toDataURL()){
	$scope.list.push({
		"img":c.toDataURL(),
		"st":videoCut.in().startPoint.t,
   		"et":videoCut.in().endPoint.t,
		"de":videoCut.in().data
   });
   $scope.totalInfo();
   console.log('get img url success')
   return;
}



// 发送截图请求
		   VideoService.cutImg(
		   {file_path:videoCut.in().videoList[videoCut.in().data[0].i].src,filename:'',cut_time:videoCut.in().data[videoCut.in().data.length-1].s,count:1}
		   ).then(
		   		function(data){
					if(data.id=='1'){
						console.log('视频剪切图片已生成')
						imgsrc = data.content
					}else{
						growl.addErrorMessage("图片截取失败！");
						// console.log('视频剪切图片生成失败')
					}
				},
				function(code){growl.addErrorMessage("请求出错啦，错误代码"+code);}
			)
		   
		   
		   $scope.list.push({
				"img":imgsrc,
				"st":videoCut.in().startPoint.t,
		   		"et":videoCut.in().endPoint.t,
				"de":videoCut.in().data
		   });
		   $scope.totalInfo();
       }
	   $scope.deleteJJat=function(f){
		   $scope.list = $scope.list.slice(0,f).concat($scope.list.slice(f+1));
		   $scope.totalInfo();	   
	   }
	   // 单击视频片段
	   $scope.videoJJclick = function(e){
			videoCut.in().setStartPoint(e.st);
			videoCut.in().setEndPoint(e.et);
			videoCut.in().checkVideo();
		}
	   // 预览全部
	    $scope.showVideoAll_bool = false;
		$scope.showVideoAll_index = 0;
		$scope.showVideoAll = function(){
			if($scope.list.length>0){
				$scope.showVideoAll_bool = true;
				$scope.showVideoAll_index = 0;
				$scope.videoJJclick($scope.list[0]);
			}
			
			// console.log($scope.list)
		}
		// 每个片段预览结束后回调
		$scope.oncheckover = function(bool){
			if(bool&&$scope.showVideoAll_bool&&++$scope.showVideoAll_index<$scope.list.length){
				$scope.videoJJclick($scope.list[$scope.showVideoAll_index]);
			}else{
				$scope.showVideoAll_bool = false;
			}
		}
		// 统计信息跟新
		$scope.totalInfo = function(){
			$scope.cutinfolength = $scope.list.lengh<10?'0':''+$scope.list.length;
			var t=0;
			for(var i=0;i<$scope.list.length;i++){
				t+=$scope.list[i].et-$scope.list[i].st;
			}
			t = Math.floor(t);
			$scope.totalTimes = $scope.getLocalTime(t);
			if($scope.showVideoAll_bool){
				$scope.showVideoAll_bool = false;
			}
		};
		// 提交合并
		$scope.StartCutVideo = function(){
			var videoCuttingInfo={}
			var dataAr = [];
			for(var i =0;i<$scope.list.length;i++){
				for(var j =0;j<$scope.list[i].de.length;j++){
					var data = $scope.list[i].de[j];
					dataAr.push(
								{
							      "file_path":videoCut.in().videoList[data.i].src,
								  "filename":"",
				                  "start_time":data.s,
				                  "end_time":data.e-data.s
				             	}
							   );
				}
			}
			videoCuttingInfo.video_id='';
			videoCuttingInfo.videoname=($scope.newVideoName?$scope.newVideoName:"未命名");
			videoCuttingInfo.video_path=''
			videoCuttingInfo.viewpoint=dataAr;
			// 提交合并data
			VideoService.mergeVideos(videoCuttingInfo).then(
		   		function(data){
					if(data.id > 0){
						console.log('视频合并请求成功:',data);
					    console.log("data.id:",data.id);
					    $scope.interCheck = $interval(function(){
					    	$scope.getMergeStatus(data.id);
					    },5000);
					}else{
						growl.addErrorMessage("请求失败了，请重试");
					}
				},
				function(code){growl.addErrorMessage("请求出错啦，错误代码"+code);}
			);
			
		};
		// 查询合成状态
		$scope.getMergeStatus = function(mergeId){
			VideoService.getMergeStatus(mergeId).then(
				function(data){
				   console.log("查询视频合成状态：",data);	
				   if(data.response_code == "0"){
					   if(data.content != undefined && data.content != null){
						   if(data.content[0].status == 0){
							   $("#btn_submit_merge").addClass("disabled");
							   $("#btn_submit_merge").text("正在合成...")
						   }else if(data.content[0].status == 1){
							   $("#btn_submit_merge").removeClass("disabled");
							   $("#btn_submit_merge").text("合成新视频");
							   $interval.cancel($scope.interCheck);
							   growl.addSuccessMessage("视频合成成功！");
						   }else if(data.content[0].status == 2){
							   $("#btn_submit_merge").removeClass("disabled");
							   $("#btn_submit_merge").text("合成新视频");
							   $interval.cancel($scope.interCheck);
							   growl.addErrorMessage("视频合成失败了，请重试");
							   $scope.deleteResourceById(mergeId);
						   }else if(data.content[0].status == 3){
							   $("#btn_submit_merge").addClass("disabled");
							   $("#btn_submit_merge").text("正在排队...");
						   }else{
							   $("#btn_submit_merge").removeClass("disabled");
							   $("#btn_submit_merge").text("合成新视频");
							   $interval.cancel($scope.interCheck);
							   growl.addErrorMessage("视频合成失败了，请重试");
							   $scope.deleteResourceById(mergeId);
						   }
					   }else{
						   $interval.cancel($scope.interCheck);
						   $scope.deleteResourceById(mergeId);
					   }
				   }else{
					   $interval.cancel($scope.interCheck);
					   growl.addErrorMessage("视频合成出错了，错误原因："+data.failure_reason);
					   $scope.deleteResourceById(mergeId);
				   }
			    },function(code){
			       $interval.cancel();
				   growl.addErrorMessage("请求出错啦，错误代码"+code);
			});
		};
		$scope.deleteResourceById = function(resourceId){
			 ResourceSevice.deleteByRsourceId(resourceId).then(function(data){
				 console.log("$scope.deleteResourceById:",data);
			 },function(code){
				 console.log("$scope.deleteResourceById:",code);
			 });
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
		// is mime
		// 更换视频
		$scope.EditVideo = function(setOperation) {
			$scope.initChecked($scope.videoTrees);
			var modalInstance = $modal.open({
				templateUrl : 'PersonalInformation/video/PersonalVideo.videoCutting.modal.html',
				backdrop : 'static',
				// windowClass: 'modal-lg',
				controller : CuttingResourceModalCtrl,
				resolve : {
					videoTrees : function() {
						return $scope.videoTrees;
					},
					setOperation : function(){
						return setOperation;
					}
				}
			}).result.then(
					function(checkedVideos){
						// $scope.dealVideo(checkedVideos)
						$scope.cuttingVideo = checkedVideos;
						$scope.cuttingVideoProcessor(checkedVideos);
					}
                 );
		};
		var CuttingResourceModalCtrl = function($modalInstance,$filter, $scope,$location, videoTrees,setOperation) {
			$scope.videoTrees = videoTrees;
			$scope.setOperation = setOperation;
			// 获取选中的视频
			var checkedVideos = [];
			$scope.selectVideoNode = function(node) {
				if (node.checked == true && node.isfolder === '1') {
					checkedVideos.push(node);
				}
				if (node.nodes) {
					$.each(node.nodes, function(index, _node) {
						$scope.selectVideoNode(_node);
					});
				}
			};
			$scope.cuttingVideo = checkedVideos;
			// 确定设置
			$scope.Save = function() {
				$scope.selectVideoNode(videoTrees[0]);
				$modalInstance.close($scope.cuttingVideo);
			};
			// 取消设置
			$scope.cancel = function() {
				activeNode = [];
				$modalInstance.dismiss('cancel');
			};
			
			
			$scope.initCheckedNode = function(node){
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
				if (node.isfolder === '1') {
					if (node.checked == true) {
						node.checked = false;
					} else {
						node.checked = true;
					}
				}
			};
		};
		// 剪切视频
		$scope.cuttingVideoProcessor = function(cuttingVideo){
			videoCut.in().init(cuttingVideo);
			videoCut.in().callback =  $scope.showVideoTime;
			videoCut.in().checkover = $scope.oncheckover
		};

		var init = function(){
			$scope.searchVideoTreesList();
			$scope.cuttingVideo= angular.fromJson($location.search().checkedVideo);
			// alert($scope.cuttingVideos[0].id);
			/*
			 * var videosc = []; $.each($scope.cuttingVideos, function(index,
			 * data) { videosc.push(data.fileUrl); }); var videolist = videosc;
			 */
			$scope.list = [];
			$scope.cutinfolength = '00';
			$scope.totalTimes = "00:00:00";
			$scope.sortableOptions={update:function(e,ui){},stop: function(e,ui){}};
			// var videolist =
			// ["http://192.168.12.167/3.mp4","http://192.168.12.167/VGA.mp4"];
			// new videoCut().init(videolist);
		    $scope.cuttingVideoProcessor($scope.cuttingVideo);
		 };
		 init();
      }]);
});