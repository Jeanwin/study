define([ 'app', 'config' ], function(app, config) {
	app.registerController('videoSetCtrl', [ '$scope', '$modal', '$filter','$location', 'growl', 'TreeService', 'VideoService','ResourceService', function($scope, $modal,$location,$filter, growl, TreeService,VideoService, ResourceService) {
		$scope.checkedVideos = [];
		$scope.$broadcast('checkedVideos', $scope.checkedVideos);
		/**
		 * 最新视频》》去打点
		 */
		$scope.dotting = function(latestVideo){
			var latestVides1 = [];
			latestVides1.push(latestVideo);
			$scope.selectedLatestVideo = latestVides1; 
		};
		$scope.cutting = function(latestVideo){
			var latestVides2 = [];
			latestVides2.push(latestVideo);
			$scope.selectedLatestVideo = latestVides2; 
		};
		// 初始化加载树
		var userTrees = function(keywords) {
			TreeService.systemTree(keywords).then(function(data) {
				$scope.areaTree = data;
				$scope.$broadcast('areaTree', $scope.areaTree);
			}, function() {

			});
		};

		// 初始化加载树
		$scope.searchDirTreesList = function() {
			ResourceService.getVideoTrees().then(function(data) {
				$scope.videoTrees = data;
			}, function() {
				growl.addErrorMessage("查询视频出错了");
			});
		};

		// 是否显示复选按钮
		$scope.oneCheck = function() {
			console.log($scope.ok)
			$scope.showbutton = !$scope.showbutton;
			if ($scope.showbutton === true) {
				$scope.resource.checked = true;
			} else {
				$scope.resource.checked = false;
			}
		};
		// 点击全部选中时设置控制的单选按钮状态
		$scope.checkAllFacilitys = function() {
			console.log($scope.checkAll);
			if ($scope.checkAll === false && $scope.checkAll1 === false) {
				$scope.resource.checked = true;
				$scope.showbutton = true;
				$scope.checkAll1 = true
			} else {
				$scope.resource.checked = false;
				$scope.showbutton = false;
				$scope.checkAll1 = false;
			}
			// $scope.checkAll = !$scope.checkAll;
			// $.each($scope.userList, function(index, user){
			// user.checked = $scope.checkAll;
			// });
		};
		// 我的资源移动到
		$scope.pleaceChange = function() {
			var modalInstance = $modal.open({
				templateUrl : 'PersonalInformation/resource/PersonalResource.placechange.modal.html',
				backdrop : 'static',
				// windowClass: 'modal-lg',
				controller : PersonalResourceModalCtrl,
				resolve : {
					resourcetree : function() {
						return $scope.areaTree;
					}
				}
			});
		};
		var PersonalResourceModalCtrl = function($scope, $modalInstance, resourcetree) {
			$scope.areaTree = resourcetree;

			// 点击树文件触发的事件
			$scope.setActiveFolder = function(node) {
				$scope.ActiveFolder = node;
			};
			// 确定移动保存到
			$scope.MoveSave = function() {

			};
			// 取消移动到
			$scope.cancel = function() {
				$modalInstance.close();
			}

		};
		// 上传资源
		$scope.newFolder = function() {
			var modalInstance = $modal.open({
				templateUrl : 'PersonalInformation/resource/PersonalResource.newFolder.modal.html',
				backdrop : 'static',
				controller : NewFolderModalCtrl,
				resolve : {
				// del: functiouploadn () {
				// return del;
				// }
				}
			});
		};
		// 上传资源控制器
		var NewFolderModalCtrl = function($modalInstance, $scope) {
			// 确定设置
			$scope.ok = function() {
			};
			// 取消设置
			$scope.cancel = function() {
				$modalInstance.close();
			};
		};
		// 上传资源
		$scope.uploadResource = function() {
			var modalInstance = $modal.open({
				templateUrl : 'PersonalInformation/resource/PersonalResource.UploadResource.modal.html',
				backdrop : 'static',
				controller : UploadResourceModalCtrl,
				resolve : {
				// del: functiouploadn () {
				// return del;
				// }
				}
			});
		};
		// 上传资源控制器
		var UploadResourceModalCtrl = function($modalInstance, $scope) {
			// 确定设置
			$scope.ok = function() {
			};
			// 取消设置
			$scope.cancel = function() {
				$modalInstance.close();
			};
		};
		// 删除资源
		$scope.delResourse = function(del) {
			var modalInstance = $modal.open({
				templateUrl : 'PersonalInformation/resource/PersonalResource.DelResource.modal.html',
				backdrop : 'static',
				controller : DelResourceModalCtrl,
				resolve : {
					del : function() {
						return del;
					}
				}
			});
		};
		// 删除控制器
		var DelResourceModalCtrl = function($modalInstance, $scope, del) {
			// 确定设置
			$scope.ok = function() {
			};
			// 取消设置
			$scope.cancel = function() {
				$modalInstance.close();
			};
		};

		// 取消编辑视频打点信息
		$scope.cancelEdit = function(editModalInput) {
			$scope.editModalInput = $scope.editModal;
		}
		// 确定编辑视频打点信息
		$scope.okEdit = function(editModalInput) {
			$scope.editModal = $scope.editModalInput;
		}
		// 删除节点
		$scope.deleteDot = function() {
			$scope.dotshow = true;
		};

		$scope.initChecked = function(node) {
			$.each(node, function(index, data) {
				data.checked = false;
				if (data.nodes != null) {
					$scope.initChecked(data.nodes);
				}
			});
		};
		$scope.getLatestVideos = function(){
			ResourceService.getLatestVideos().then(
			     function(data){
			    	 $scope.latestVideos = data;
			    	 console.log("latestVideos",$scope.latestVideos);
			     },
			     function(){
			    	 
			     }
			);
		};
		// 列表设置
		$scope.EditVideo = function(setOperation) {
			$scope.initChecked($scope.videoTrees);
			var modalInstance = $modal.open({
				templateUrl : 'PersonalInformation/video/PersonalVideo.videoCutting.modal.html',
				backdrop : 'static',
				// windowClass: 'modal-lg',
				controller : CuttingResourceModalCtrl,
				resolve : {
					setOperation : function() {
						return setOperation;
					},
					areaTree : function() {
						return $scope.areaTree;
					},
					videoTrees : function() {
						return $scope.videoTrees;
					}
				}
			});
		};
		var CuttingResourceModalCtrl = function($modalInstance,$filter, $scope,$location,setOperation, videoTrees) {
			$scope.videoTrees = videoTrees;
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
			$scope.setOperation = setOperation;
			//
			/*
			 * var dirNodes= []; $scope.getNodes = function(dirTrees){ var i =
			 * 0; for(node in dirTrees){ if(node.nodes === null){ dirNodes[i] =
			 * node; console.log(dirNodes[i]); i++; }else{
			 * $scope.getNodes(dirNodes); } } return dirNodes; };
			 */
			// 确定设置
			$scope.Save = function() {
				$scope.selectVideoNode(videoTrees[0]);
				// console.log('checkedVideos',checkedVideos);
				$scope.$broadcast('checkedVideos', checkedVideos);
				$scope.checkedVideos = checkedVideos;
				$modalInstance.close();
				activeNode = [];
			};
			// 取消设置
			$scope.cancel = function() {
				activeNode = [];
				$modalInstance.close();
			};
			$scope.unique = function(arr) {
				var result = [], isRepeated;
				for (var i = 0, len = arr.length; i < len; i++) {
					isRepeated = false;
					for (var j = 0, len = result.length; j < len; j++) {
						if (arr[i].id == arr[j].id) {
							isRepeated = true;
							break;
						}
					}
					if (!isRepeated) {
						result.push(arr[i]);
					}
				}
				return result;
			};
			/*$scope.$watch('videoTrees',function(){
				$scope.temp = $filter('filter')($scope.videoTrees, {checked:true});
			})*/
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
				// 选择文件底色变化】
				if(setOperation == 'cutting'){
					if (node.isfolder === '1') {
						if (node.checked == true) {
							node.checked = false;
						} else {
							node.checked = true;
						}
					}
				}else{
					$scope.getCheckedNode($scope.videoTrees[0]);
					if (node.isfolder === '1') {
						node.checked = true;
					}
					
					
				}
				
			};
		};
		// 分享文件
		$scope.ResourseShare = function(share) {
			var modalInstance = $modal.open({
				templateUrl : 'PersonalInformation/resource/PersonalResource.ResourseShare.modal.html',
				// backdrop:'static',
				// windowClass: 'modal-lg',
				controller : ResourseShareModalCtrl,
				resolve : {
					share : function() {
						return share;
					}
				}
			});
		};
		var ResourseShareModalCtrl = function($modalInstance, $scope) {
			$scope.organizationModal = true;
			$scope.MyselftModal = false;
			// 发消息时候的收件人的格式
			$scope.states = [ {
				id : 'AK',
				text : 'Alaska'
			}, {
				id : 'HI',
				text : 'Hawaii'
			}, {
				id : 'HI1',
				text : 'Holle'
			}, {
				id : 'HI2',
				text : 'Hi'
			}, {
				id : 'HI3',
				text : 'Hawai'
			} ];

			var findState = function(id) {
				for (var i = 0; i < states.length; i++) {
					for (var j = 0; j < states[i].children.length; j++) {
						if (states[i].children[j].id == id) {
							return states[i].children[j];
						}
					}
				}
			};

			$scope.multi2Value = {
				id : 'AK',
				text : 'Alaska'
			}; // 结构必须一样，否则默认值不匹配，显示为underfined

			$scope.multi = {
				multiple : true,
				query : function(query) {
					query.callback({
						results : $scope.states
					});
				}
			};

			// 选择组织机构的
			$scope.organization = function() {
				$scope.organizationModal = true;
				$scope.MyselftModal = false;
			};
			// 选择个人的
			$scope.Myselft = function() {
				$scope.MyselftModal = true;
				$scope.organizationModal = false;
			}
			// 选择机构底色变化
			$scope.backChange = function(keywords) {
				if (keywords === '2') {
					$scope.backchanage2 = keywords;
				}
				if (keywords === '5') {
					$scope.backchanage5 = keywords;
				}
			};
			// 取消分享
			$scope.cancel = function() {
				$modalInstance.close();
			};
		};

		var init = function() {
			$scope.searchDirTreesList();
			$scope.getLatestVideos();
			$scope.editModal = "通过记录生活，记录文化，记录历史，来实现自己继承文化的梦想。";
			$scope.editModalInput = $scope.editModal;
			$scope.checkAll = false;
			$scope.checkAll1 = false;
			$scope.resource = {
				checked : ''
			};
			$scope.showbutton = false;

			userTrees("trees");
			$scope.pagination = {
				totalItems : 0,
				pageIndex : 1,
				pageSize : 10,
				maxSize : 8,
				numPages : 4,
				previousText : config.pagination.previousText,
				nextText : config.pagination.nextText,
				firstText : config.pagination.firstText,
				lastText : config.pagination.lastText
			};
			$scope.$parent.active = 5;
		};

		init();
	} ]);
});
