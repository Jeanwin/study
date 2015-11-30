define([ 'app', 'config' ], function(app, config) {
	app.registerController('PersonalInformationResourceCtrl', [ '$scope', '$modal', '$upload', 'growl', '$filter', '$timeout', '$interval', 'TreeService', 'ResourceService', 'CodeService','VideoService',
			function($scope, $modal, $upload, growl, $filter, $timeout, $interval, TreeService, ResourceService, CodeService,VideoService) {
				$scope.showVideo = function(url,isClose,resource){
					VideoService.showVideo(url,isClose,resource);
				}
		        // 预览
				$scope.viewOnline = function(resource) {
					if (resource.fileurl != null && resource.fileurl != undefined && resource.fileurl != '') {
						if (resource.nametype == 'Pictrue' || resource.fileurl.lastIndexOf('.swf') != -1 || resource.fileurl.lastIndexOf('.html') != -1 || resource.nametype == 'Documents') {
							window.open(resource.fileurl);
						} else if (resource.nametype == 'Videos') {
							if (resource.transPath != null && resource.transPath != undefined && resource.transPath != '' && resource.transFlag == '1') {
								// if( resource.transFlag == '1'){
								 //window.open(resource.transPath);
								$scope.showVideo(resource.transPath,false,resource);
								/*
								 * }else if(resource.transFlag == "1"){
								 * growl.addInfoMessage(data.content); }
								 */
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
											// alert("ok");
											window.open(data.content);
										} else if (data.id == '-3') {
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
						} else if (resource.nametype == 'Word' || resource.fileurl.lastIndexOf('.ppt') != -1 || resource.nametype == 'Excel' || resource.fileurl.lastIndexOf('.pdf') != -1) {
							window.open('../resource/readOnline?path=' + resource.fileurl.replace(config.resourceIp, ''));
						} else {
							growl.addInfoMessage("暂不支持此格式文件预览");
						}
					} else {
						growl.addErrorMessage("对不起,文件路径不存在！");
					}
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
					// is minne
					/*
					 * $scope.keywords.name = $scope.resource.name;
					 * $scope.keywords.source = $scope.resource.source;
					 * $scope.keywords.type = $scope.resource.type;
					 */
					resourceList($scope.keywords);

				};
				// 资源列表展示
				var resourceList = function(key) {
					console.log("key", key);
					key.parentid = $scope.parentid;
					ResourceService.resourceList(key, $scope.pagination).then(function(data) {
						$scope.resourceList = data.data;
						$scope.pagination.totalItems = data.total;
						$scope.pagesize = parseInt(($scope.pagination.totalItems - 1) / $scope.pagination.pageSize + 1);
					}, function() {

					});
				};
				/**
				 * 获取资源来源类型
				 */
				/*
				 * $scope.getResourceSource = function() {
				 * CodeService.getCodeByParentId('ba1722b977ac458d8b4f075c90d8e6ac').then(function(data) {
				 * $scope.resourceSource = data; alert($scope.resourceSource); },
				 * function() { growl.addErrorMessage("查询资源来源出错了"); }); };
				 */
				$scope.searchResource = function(key) {
					resourceList(key);
				}
				// 返回上一级
				$scope.lastDir = function(parentid, index) {
					console.log("aplice之前", $scope.dir);
					var dirLength = $scope.dir.length;
					$scope.parentid = parentid;
					if (angular.isDefined(index)) {
						// alert($scope.dir);
						for (var i = dirLength - 1; i >= 0; i--) {
							if (i > index) {
								// alert($scope.dir[i]);
								$scope.dir.pop($scope.dir[i]);
							}
						}
						var newLength = $scope.dir.length;
						console.log("splice之后", $scope.dir);
						if (newLength < 2) {
							$scope.lastLevel.splice(0, 1);
						} else {
							$scope.lastLevel = [ "返回上一级", $scope.dir[newLength - 2][1] ];
						}
					} else {
						$scope.dir.splice(dirLength - 1, 1);
						if (dirLength < 3) {
							$scope.lastLevel.splice(0, 1);
						} else {
							$scope.lastLevel = [ "返回上一级", $scope.dir[dirLength - 3][1] ];
						}
					}

					console.log($scope.lastLevel);
					$scope.keywords.parentid = parentid;
					resourceList($scope.keywords);

				}
				// 进入子目录
				$scope.enterDir = function(resource) {
					if (resource.isfolder == "0") {
						// 返回上一级
						$scope.lastLevel = [ "返回上一级", resource.parentid ];
						var tmp = [ resource.name, resource.id ];
						$scope.dir.push(tmp);
						$scope.parentid = resource.id;
						$scope.keywords.parentid = resource.id;
						resourceList($scope.keywords);
					}
				};
				// 字典列表
				var getcode = function(value) {
					CodeService.getCode(value).then(function(data) {
						if (value == "grade") {
							$scope.grade = data;
						} else if (value == "Subject") {
							$scope.subject = data;
						} else if (value == "resourceType") {
							$scope.type = data;
						} else if (value == "sourse") {
							$scope.source = data;
						}
					}, function() {

					});
				};
				// 初始化加载树
				var dirTree = function() {
					ResourceService.dirTree().then(function(data) {
						$scope.Tree = data;
					}, function() {

					});
				};
				// 初始化加载树
				var deptList = function() {
					ResourceService.detpList().then(function(data) {
						$scope.deptList = data;
					}, function() {

					});
				};
				// 初始化加载树
				var userList = function() {
					ResourceService.getAllUser("").then(function(data) {
						$scope.userList = data;
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
					$scope.checkAll = !$scope.checkAll;
					$.each($scope.resourceList, function(index, data) {
						data.checked = $scope.checkAll;
					});
					$scope.resource.checked = $scope.checkAll;
				};
				// 点击单个按钮
				$scope.checkFacility = function() {
					$.each($scope.resourceList, function(index, data) {
						console.log(data);
						if (data.checked != undefined && data.checked == true) {
							$scope.resource.checked = data.checked;
							return;
						}
					});
					$scope.resource.checked = false;
				};
				// 监视contractList中是否有元素被改变状态
				$scope.$watch('resourceList', function() {
					// 监测是否有元素被选中
					if (angular.isDefined($scope.resourceList)) {
						var _temp = $filter('filter')($scope.resourceList, {
							checked : true
						});
						$scope.selectedCount = _temp.length;
						$scope.showM = _temp.length > 0 && $scope.flag;
						if (_temp.length === $scope.resourceList.length)
							$scope.checkAll = true;
						else
							$scope.checkAll = false;
					}
				}, true);
				// 我的资源移动到
				$scope.pleaceChange = function() {
					var modalInstance = $modal.open({
						templateUrl : 'PersonalInformation/resource/PersonalResource.placechange.modal.html',
						backdrop : 'static',
						controller : PersonalResourceModalCtrl,
						resolve : {
							tree : function() {
								return $scope.Tree;
							},
							resourceList : function() {
								return $scope.resourceList;
							}
						}
					}).result.then(function() {
						resourceList($scope.keywords);
					});
				};
				var PersonalResourceModalCtrl = function($scope, $modalInstance, tree, resourceList) {
					$scope.Tree = tree;
					$scope.ActiveFolder = tree[0];
					$scope.resourceList = resourceList;
					// 点击树文件触发的事件
					$scope.setActiveFolder = function(node) {
						$scope.ActiveFolder = node;
					};
					// 确定移动保存到
					$scope.MoveSave = function() {
						var checkedList = $filter('filter')($scope.resourceList, {
							checked : true
						});
						ResourceService.move(checkedList, $scope.ActiveFolder.id).then(function(data) {
							if (data != 0) {
								growl.addSuccessMessage("移动成功");
								$modalInstance.close();
							} else {
								growl.addErrorMessage("移动失败");
							}

						});
					};
					// 新建start
					$scope.NewFolder = function() {
						$scope.folder = '';
						var modalInstance = $modal.open({
							templateUrl : 'PersonalInformation/resource/PersonalResource.NewFolder.modal.html',
							backdrop : 'static',
							controller : NewFolderModalCtrl,
							resolve : {
								parentid : function() {
									return $scope.ActiveFolder.id;
								}
							}
						}).result.then(function() {
							ResourceService.dirTree().then(function(data) {
								$scope.Tree = data;
							}, function() {

							});
						});

					};
					// 上传资源控制器
					var NewFolderModalCtrl = function($scope, $modalInstance, parentid, growl) {
						// 确定设置
						$scope.ok = function(folder) {
							ResourceService.addFloder(parentid, folder).then(function(data) {
								if (data != 1) {
									growl.addErrorMessage("文件夹创建失败");
								}
								$modalInstance.close(true);
							}, function() {

							});
						};
						// 取消移动到
						$scope.cancel = function() {
							$modalInstance.close();
						}
					}
					// 新建end

					// 取消移动到
					$scope.cancel = function() {
						$modalInstance.close();
					}

				};
				// 新建文件夹
				$scope.newFolder = function() {
					$scope.folder = '';
					var modalInstance = $modal.open({
						templateUrl : 'PersonalInformation/resource/PersonalResource.NewFolder.modal.html',
						backdrop : 'static',
						controller : NewFolderModalCtrl,
						resolve : {
							parentid : function() {
								return $scope.parentid;
							}
						}
					}).result.then(function() {
						$scope.keywords.parentid = $scope.parentid;
						resourceList($scope.keywords);
						dirTree();
					});
				};
				// 上传资源控制器
				var NewFolderModalCtrl = function($scope, $modalInstance, parentid, growl) {
					// 确定设置
					$scope.ok = function(folder) {
						ResourceService.addFloder(parentid, folder).then(function(data) {
							if (data != 1) {
								growl.addErrorMessage("文件夹创建失败");
							}
							$modalInstance.close(true);
						}, function() {

						});
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
							parentid : function() {
								return $scope.parentid;
							}
						}
					}).result.then(function() {
						$scope.keywords.parentid = $scope.parentid;
						resourceList($scope.keywords);
					});

				};
				// 上传资源控制器
				var UploadResourceModalCtrl = function($modalInstance, $timeout, $interval, growl, parentid, $scope) {
					$scope.file;
					var url = config.backend.ip + config.backend.base + 'resource/upload';
					$scope.disabledAdd = true;
					$scope.onFileSelect = function($files) {

						$scope.file = $files;

						if ($scope.uploadRightAway) {
							$scope.startUpload();
						}
						for (var i = 0; i < $scope.file.length; i++) {
							$scope.file[i].progress = 0;
						}

						/*
						 * var stop = $interval(function() { var count =0; for
						 * (var i = 0; i < $scope.file.length; i++) {
						 * if($scope.file[i].progress<100){
						 * $scope.file[i].progress +=10 ; } count
						 * +=$scope.file[i].progress } if($scope.file.length*100<=count){
						 * $interval.cancel(stop); }
						 * console.log("interval",count); }, 100);
						 */
					}

					$scope.remove = function(index) {
						$scope.file.splice(index, 1);
						$scope.upload[index].abort();
						if ($scope.file.length <= 0) {
							$scope.file = null;
						}
					}
					$scope.parseMb = function(size) {
						return size > 10486 ? (size / 1024 / 1024).toFixed(2) + 'MB' : (size / 1024 / 100.00).toFixed(2) + 'KB';
					}
					// 确定设置
					$scope.ok = function() {
						var url = config.backend.ip + config.backend.base + 'resource/upload';
						if ($scope.file && $scope.file.length) {
							$scope.upload = [];
							var count = 0;
							for (var i = 0; i < $scope.file.length; ++i) {
								$scope.upload[i] = $upload.upload({
									url : url,
									data : {
										parentid : parentid
									},
									file : $scope.file[i]
								}).progress(function(evt) {
									for (var i = 0; i < $scope.file.length; i++) {
										if (evt.total <= $scope.file[i].size + 1023 && evt.total >= $scope.file[i].size - 1023) {
											$scope.file[i].loaded = $scope.parseMb(evt.loaded);
											$scope.file[i].progress = parseInt(100.0 * evt.loaded / evt.total);
										}
									}
									// console.log("file",$scope.file[0]);
									// console.log("evt",evt);
									// console.log(evt.total, "-----",
									// $scope.parseMb(evt.loaded));
								}).success(function(data, status, headers, config) {
									if (data == "1") {
										growl.addSuccessMessage("上传成功");
										// $modalInstance.close(true);
										count++;
										// alert(count);
										if (count === $scope.file.length) {
											$modalInstance.close(true);
										}
										return true;
									} else {
										growl.addErrorMessage("上传失败");
										// $modalInstance.close(true);
									}
								}).error(function() {
									growl.addErrorMessage("上传失败");
								});
							}
						}
					};
					// 取消设置
					$scope.cancel = function() {
						if (angular.isDefined($scope.upload) && $scope.upload.length) {
							for (var i = 0; i < $scope.upload.length; i++) {
								$scope.upload[i].abort();
							}
						}
						$modalInstance.close();
					};
				};
				// 删除资源
				$scope.delResourse = function() {
					var modalInstance = $modal.open({
						templateUrl : 'PersonalInformation/resource/PersonalResource.DelResource.modal.html',
						backdrop : 'static',
						controller : DelResourceModalCtrl,
						resolve : {
							resourceList : function() {
								return $scope.resourceList;
							}
						}
					}).result.then(function() {
						resourceList($scope.keywords);
					});
				};
				// 删除控制器
				var DelResourceModalCtrl = function($modalInstance, resourceList, $scope) {
					$scope.resourceList = resourceList;
					$scope.resourceDel = $filter('filter')($scope.resourceList, {
						checked : true
					});
					// 确定设置
					$scope.ok = function() {
						var checkedList = $filter('filter')($scope.resourceList, {
							checked : true
						});
						console.log("checkedList", checkedList);
						ResourceService.del(checkedList).then(function(data) {
							if (data != 0) {
								growl.addSuccessMessage("删除成功");
								$modalInstance.close();
							} else {
								growl.addErrorMessage("删除失败");
							}

						});
					};
					// 取消设置
					$scope.cancel = function() {
						$modalInstance.close();
					};
				};
				// 下载资源
				$scope.downLoadResourse = function() {
					var checkedList = $filter('filter')($scope.resourceList, {
						checked : true
					});
					ResourceService.down(checkedList).then(function(data) {
					});
				};
				// 编辑列表 设置
				$scope.EditResource = function(resource) {
					var modalInstance = $modal.open({
						templateUrl : 'PersonalInformation/resource/PersonalResource.EditResource.modal.html',
						backdrop : 'static',
						controller : EditResourceModalCtrl,
						resolve : {
							resource : function() {
								return resource;
							},
							grade : function() {
								return $scope.grade;
							},
							subject : function() {
								return $scope.subject;
							},
							source : function() {
								return $scope.source;
							},
							type : function() {
								return $scope.type;
							}
						}
					}).result.then(function() {
						resourceList($scope.keywords);
					});
				};
				var EditResourceModalCtrl = function($modalInstance, resource, grade, subject, source, type, growl, $scope) {
					$scope.resource = resource;
					$scope.grade = grade;
					$scope.subject = subject;
					$scope.source = source;
					$scope.type = type;
					console.log(subject);
					console.log("----");
					// 确定设置
					$scope.ok = function() {
						ResourceService.modify(resource).then(function(data) {
							if (data == 1) {
								growl.addSuccessMessage("编辑成功");
							} else {
								growl.addErrorMessage("编辑失败");
							}
							$modalInstance.close();
						})
					};
					// 取消设置
					$scope.cancel = function() {
						$modalInstance.close();
					};
				};
				// 翻译
				$scope.transfer = function(dic, value) {
					var name = "";
					$.each(dic, function(index, data) {
						if (data.value == value) {
							name = data.name;
							return false;
						}
					});
					return name;
				};
				// 分享文件
				$scope.ResourseShare = function(share) {
					var counts = 0;
					var checkedResourceList = $filter('filter')($scope.resourceList, {
						checked : true
					});
					for (var i = 0; i < checkedResourceList.length; i++) {
						if (checkedResourceList[i].isfolder === '0') {
							counts++;
						}
					}
					if (counts === 0) {
						var modalInstance = $modal.open({
							templateUrl : 'PersonalInformation/resource/PersonalResource.ResourseShare.modal.html',
							// backdrop:'static',
							// windowClass: 'modal-lg',
							controller : ResourseShareModalCtrl,
							resolve : {
								deptList : function() {
									return $scope.deptList;
								},
								resourceList : function() {
									return $scope.resourceList;
								},
								userList : function() {
									return $scope.userList;
								},
								users : function() {
									return $scope.users;
								}
							}
						});
					} else {
						growl.addErrorMessage("对不起，您选择的目录中包含" + counts + "个文件夹！");
					}
				};
				var ResourseShareModalCtrl = function($modalInstance, deptList, resourceList, userList, users, $scope) {
					$scope.userList = userList;
					$scope.deptList = deptList;
					$scope.users = users;
					$scope.resourceList = resourceList;
					$scope.organizationModal = true;
					$scope.MyselftModal = false;
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
					$scope.backChange = function(dept) {
						if (dept.checked == true) {
							dept.checked = false;
						} else {
							dept.checked = true;
						}
					};
					$scope.loginame = "";
					$scope.getuser = function(loginname) {
						ResourceService.getAllUser(loginname).then(function(data) {
							$scope.userList = data;
						})
					}
					$scope.ok = function() {
						var checkedDeptList = $filter('filter')($scope.deptList, {
							checked : true
						});
						var checkedResourceList = $filter('filter')($scope.resourceList, {
							checked : true
						});
						var flag = "0";
						// 选择的是用户
						if (!$scope.organizationModal) {
							flag = "1";
							checkedDeptList = $scope.users;
						}
						ResourceService.shareResource(flag, checkedDeptList, checkedResourceList).then(function(data) {
							if (data > 0) {
								growl.addSuccessMessage("分享成功");
							}
							$modalInstance.close();
						});
					};

					$scope.del = function(index) {
						$scope.userList.push($scope.users[index]);
						$scope.users.splice(index, 1);
					};
					$scope.add = function(index) {
						$scope.users.push($scope.userList[index]);
						$scope.userList.splice(index, 1);
					};
					// 取消分享
					$scope.cancel = function() {
						$modalInstance.close();
					};
				};

				var init = function() {
					// $scope.getResourceSource();
					$scope.checkAll = false;
					$scope.resource = {
						checked : ''
					};
					$scope.showbutton = false;

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
					$scope.$parent.active = 1;
					$scope.resource = {
						"name" : ""
					}
					$scope.keywords = {
						"name" : "",
						"parentid" : "1",
						"source" : "",
						"type" : ""
					};
					$scope.parentid = "1";// 代表的是当前目录的id
					// 类型字典 阶段
					getcode("grade");
					// 学科
					getcode("Subject");
					// 来源
					getcode("sourse");
					getcode("resourceType");
					resourceList($scope.keywords);
					// 面包屑
					// 元素包括的是名称和id
					$scope.dir = [ [ "全部文件", 1 ] ];
					dirTree();
					// 机构列表
					deptList();
					// 查询用户信息
					userList();
					// 分享选中的用户
					$scope.users = [];
				};
				init();

			} ]);
});
