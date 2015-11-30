define([ 'app', 'config' ], function(app, config) {
	app.registerController('myShareCtrl', [ '$scope', '$cookieStore', '$modal','VideoService', 'CodeService', 'ResourceService', 'MessageService', 'SecurityService', '$interval', '$filter', 'growl',
			function($scope, $cookieStore, $modal,VideoService, CodeService, ResourceService, MessageService, SecurityService, $interval, $filter, growl) {
				$scope.showVideo = function(url, isClose, resource) {
					VideoService.showVideo(url, isClose, resource);
				}
				// 预览
				$scope.viewOnline = function(resource) {
					if (resource.fileurl != null && resource.fileurl != undefined && resource.fileurl != '') {
						if (resource.nametype == 'Pictrue' || resource.nametype == 'Documents' || resource.fileurl.lastIndexOf('.swf') != -1 || resource.fileurl.lastIndexOf('.html') != -1) {
							window.open(resource.fileurl);
						} else if (resource.nametype == 'Videos') {
							if (resource.transPath != null && resource.transPath != undefined && resource.transPath != '' && resource.transFlag == '1') {
								$scope.showVideo(resource.transPath,false,resource);
							} else {
								var resourceView = {
									id : resource.id,
									fileurl : resource.fileurl
								};
								console.log("预览前转码参数：", resourceView);
								ResourceService.previewTrans(resourceView).then(function(data) {
									console.log("未转码预览返回数据：", data);
									if (data != null && data != undefined) {
										if (data.id === '1') {
											window.open(data.content);
										} else if (data.id === '-3') {
											growl.addErrorMessage(data.content);
										} else {
											growl.addInfoMessage(data.content);
										}
									} else {
										growl.addErrorMessage("请求失败了，请重试！");
									}
								}, function(code) {
									growl.addErrorMessage("对不起,请求失败了，错误码:" + code);
								});
								if (resource.fileurl.lastIndexOf('.mp4') != -1) {
									$scope.showVideo(resource.fileurl,false,resource);
								}
							}
						} else if (resource.nametype == 'Word' || resource.nametype == 'Excel' || resource.fileurl.lastIndexOf('.ppt') != -1 || resource.fileurl.lastIndexOf('.pdf') != -1) {
							window.open('../resource/readOnline?path=' + resource.fileurl.replace(config.resourceIp, ''));
						} else {
							growl.addInfoMessage("暂不支持此格式文件预览");
						}
					} else {
						growl.addErrorMessage("对不起,文件路径不存在！");
					}
				};
				/**
				 * 取消收藏
				 */
				$scope.cancelStoreMyShare = function(myShare) {
					$scope.shareStoreCancel = [];
					$scope.storeCancelList = {
						shareid : myShare.shareid,
						storeid : myShare.storeid
					}
					$scope.shareStoreCancel.push($scope.storeCancelList);
					ResourceService.cancelStoreMyShare($scope.shareStoreCancel).then(function(data) {
						if (data.id === '1') {
							growl.addSuccessMessage(data.content);
							$scope.searchShareList();
						} else {
							growl.addErrorMessage(data.content);
						}
					}, function(code) {
						console.log("取消收藏code:", code);
						growl.addErrorMessage("请求出错啦，请重试");
					});
				};
				/**
				 * 收藏
				 */
				$scope.storeResources = function(resource) {
					$scope.resourcesStore = [];
					$scope.resourceS = {
						source : '1',
						name : resource.name,
						author : resource.shareuser,
						resourceid : resource.id,
						shareid : resource.shareid
					};
					$scope.resourcesStore.push($scope.resourceS);
					ResourceService.storeResource($scope.resourcesStore).then(function(data) {
						if (data.id === '1') {
							growl.addSuccessMessage(data.content);
							$scope.searchShareList();
						} else {
							growl.addErrorMessage(data.content);
						}
					}, function(code) {
						console.log("收藏code:", code);
						growl.addErrorMessage("请求出错啦，请重试");
					});
				};
				/**
				 * （批量）取消分享
				 */
				$scope.cancelShare = function(shareListDel) {
					$scope.shareListDel = [];
					$scope.shareListDel.push(shareListDel);
					ResourceService.cancelShare($scope.shareListDel).then(function(data) {
						if (data.id === "1") {
							growl.addSuccessMessage(data.content);
							$scope.searchMyShareList();
						} else {
							growl.addErrorMessage("取消分享失败啦，请重试");
						}
					}, function() {
						growl.addErrorMessage("取消分享失败啦，请重试");
					});
				};
				/**
				 * 查找分享给我列表
				 */
				$scope.searchShareList = function(keywords) {
					ResourceService.shareList(keywords, $scope.pagination).then(function(data) {
						$scope.shareList = data.data;
						$scope.pagination.totalItems = data.total;
					}, function() {
						growl.addErrorMessage("查询分享出错啦");
					});
				};
				/**
				 * 查找我的分享列表
				 */
				$scope.searchMyShareList = function(keywords) {
					ResourceService.myShareList(keywords, $scope.pagination).then(function(data) {
						$scope.myShareList = data.data;
						$scope.pagination.totalItems = data.total;
					}, function() {
						growl.addErrorMessage("查询分享出错啦");
					});
				};
				// 根据页码查询
				$scope.onSelectPage = function(pageIndex) {
					if (!pageIndex) {
						growl.addErrorMessage("此页码不存在");
					}
					$scope.pagination.pageIndex = pageIndex;
					var _pagination = angular.copy($scope.pagination);
					if (angular.isDefined(_pagination.pageIndex.pageIndex)) {
						_pagination.pageIndex = (_pagination.pageIndex.pageIndex) * 1;
					}
					if ($scope.myShare === "1") {
						$scope.searchMyShareList($scope.keywords, _pagination);
					} else if ($scope.shareToMe == "2") {
						$scope.searchShareList($scope.keywords, _pagination);
					} else {

					}
				};
				$scope.showmodal = function(index) {
					$scope.myShare = index;
					$scope.shareToMe = index;
					// $scope.sendmessage = index;
				};
				var init = function() {
					$scope.$parent.active = 6;
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
					$scope.showmodal('2');
					$scope.searchShareList();
					// $scope.showmodal("1");
				};
				init();
			} ]);
});
