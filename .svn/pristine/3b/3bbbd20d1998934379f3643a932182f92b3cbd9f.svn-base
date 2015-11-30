define([ 'app', 'config' ], function(app, config) {
	app.registerController('PersonalInformationCtrl', [ '$scope', '$cookieStore', '$modal', 'CodeService', 'ResourceService', 'MessageService', 'SecurityService', '$interval', '$filter', 'growl',
			function($scope, $cookieStore, $modal, CodeService, ResourceService, MessageService, SecurityService, $interval, $filter, growl) {
				$scope.imgData = {
					"url" : "assets/img/icon/headPicture.jpg"
				};
				$scope.selectChange = function() {
					alert("ok");
					console.log("收件人选择框", $("#user_search_hidden").val());
				};
				// 重新获取用户信息
				$scope.retrieveUser = function() {
					SecurityService.getUser().then(function(data) {
						$scope.user = data;
						// $scope.global.user.roleid = ['1', '2','3'];
						console.log(data);
						return $scope.user;
					}, function() {
					});
				};
				/**
				 * 查看消息
				 */
				$scope.viewMsg = function(msg) {
					console.log(msg);
					MessageService.viewMessage(msg).then(function(data) {
						if (data.id === '1') {
							$scope.searchInbox();
						} else {
							growl.addErrorMessage("标记为已阅读出现异常啦！再点一下");
						}
					}, function() {

					});
				};
				// 初始化加载树
				$scope.searchDeptList = function() {
					ResourceService.detpList().then(function(data) {
						$scope.deptList = data;
					}, function() {

					});
				};
				$scope.showReply = function(sender, msg, index) {
					console.log(msg, sender);
					$scope.accepted = index;
					$scope.Sent = index;
					$scope.sendmessage = index;
					$scope.recieverIds = [ 
						sender.loginname,
					];
					$scope.title = "回复:" + msg.title.toString().substring(msg.title.toString().lastIndexOf(":") + 1);
					$scope.content = "";
				};
				/**
				 * 查询收件箱的function
				 */
				$scope.searchInbox = function() {
					MessageService.queryInbox($scope.user, $scope.pagination).then(function(data) {
						$scope.inboxList = data.data;
						$scope.pagination.totalItems = data.total;
					}, function() {
						growl.addErrorMessage("查询收件箱异常");
					});
				};
				/**
				 * 删除收件箱
				 */
				$scope.deleteInbox = function(msg) {
					var msgids = [];
					msgids[0] = msg.id;
					var messages;
					messages = msg;
					$modal.open({
						templateUrl : 'PersonalInformation/information/PersonalInformation.deleteInbox.modal.html',
						backdrop : 'static',
						controller : DeleteInboxCtrl,
						resolve : {
							deleteInbox : function() {
								console.log("deleteInbox_msgid", msgids);
								return msgids;
							},
							inboxdel : function() {
								console.log("deleteInbox_messages", messages);
								return messages;
							}
						}
					}).result.then(function() {
						$scope.searchInbox();
					}, function(reason) {
						// growl.addErrorMessage("不好意思，请求出错啦！");
						console.log('reason is ' + reason);
					});
				};
				/**
				 * 清空收件箱
				 */
				$scope.deleteInboxes = function(inboxes) {
					var msgids = [];
					for (var i = 0; i < inboxes.length; i++) {
						msgids[i] = inboxes[i].id;
					}
					$modal.open({
						templateUrl : 'PersonalInformation/information/PersonalInformation.deleteInbox.modal.html',
						backdrop : 'static',
						controller : DeleteInboxCtrl,
						resolve : {
							deleteInbox : function() {
								console.log("deleteInbox_msgid", msgids);
								return msgids;
							},
							inboxdel : function() {
								console.log("inboxes", inboxes);
								return inboxes;
							}
						}
					}).result.then(function() {
						$scope.searchInbox();
					}, function(reason) {
						// growl.addErrorMessage("不好意思，请求出错啦！");
						console.log('reason is ' + reason);
					});
				};

				/**
				 * 删除收件箱控制器
				 */
				var DeleteInboxCtrl = function($scope, $modalInstance, deleteInbox, growl) {
					console.log('DeleteInboxCtrl', deleteInbox);

					// $scope.deleteInbox = msgids;
					$scope.ok = function() {
						MessageService.inboxDel(deleteInbox).then(function(data) {
							if (data.id === '2') {
								growl.addSuccessMessage('消息已删除');
							} else {
								growl.addErrorMessage(data.content);
							}
							$modalInstance.close(true);
						}, function(code) {
							growl.addErrorMessage("不好意思，请求出错啦！");
							// 处理失败后操作
							// alert("请求出错啦!");
						});
					};
					$scope.cancel = function() {
						$modalInstance.dismiss('cancel');
					};
				};
				/**
				 * 删除发件箱
				 */
				$scope.deleteOutbox = function(message) {
					var messageIds = [];
					messageIds[0] = message.id;
					$modal.open({
						templateUrl : 'PersonalInformation/information/PersonalInformation.deleteOutbox.modal.html',
						backdrop : 'static',
						controller : DeleteOutboxCtrl,
						resolve : {
							deleteOutbox : function() {
								console.log("deleteOutbox_msgid", messageIds);
								return messageIds;
							},
							outboxdel : function() {
								console.log("deleteOutbox_message", message);
								return message;
							}
						}
					}).result.then(function() {
						$scope.searchOutbox();
					}, function(reason) {
						console.log('reason is ' + reason);
					});
				};
				/**
				 * 清空发件箱
				 */
				$scope.deleteOutboxes = function(outboxes) {
					var msgids = [];
					for (var i = 0; i < outboxes.length; i++) {
						msgids[i] = outboxes[i].id;
					}
					$modal.open({
						templateUrl : 'PersonalInformation/information/PersonalInformation.deleteOutbox.modal.html',
						backdrop : 'static',
						controller : DeleteOutboxCtrl,
						resolve : {
							deleteOutbox : function() {
								console.log("deleteoutbox_msgid", msgids);
								return msgids;
							},
							outboxdel : function() {
								console.log("outboxes", outboxes);
								return outboxes;
							}
						}
					}).result.then(function() {
						$scope.searchOutbox();
					}, function(reason) {
						// growl.addErrorMessage("不好意思，请求出错啦！");
						console.log('reason is ' + reason);
					});
				};
				/**
				 * 删除收件箱controller
				 */
				var DeleteOutboxCtrl = function($scope, $modalInstance, deleteOutbox, growl) {
					console.log('DeleteOutboxCtrl', deleteOutbox);

					// $scope.deleteInbox = msgids;
					$scope.ok = function() {
						MessageService.outboxDel(deleteOutbox).then(function(data) {
							if (data.id === '2') {
								growl.addSuccessMessage('消息已删除');
							} else {
								growl.addErrorMessage(data.content);
							}
							$modalInstance.close(true);
						}, function(code) {
							growl.addErrorMessage("不好意思，请求出错啦！");
							// 处理失败后操作
							// alert("请求出错啦!");
						});
					};
					$scope.cancel = function() {
						$modalInstance.dismiss('cancel');
					};
				};

				/**
				 * 查询发件箱的function
				 */
				$scope.searchOutbox = function() {
					MessageService.queryOutbox($scope.user, $scope.pagination).then(function(data) {
						$scope.outboxList = data.data;
						$scope.pagination.totalItems = data.total;

					}, function() {
						growl.addErrorMessage("查询收件箱异常");
					});
				};
				// 去重复数组
				$scope.unique = function(data) {
					data = data || [];
					var a = {};
					len = data.length;
					for (var i = 0; i < len; i++) {
						var v = data[i];
						if (typeof (a[v]) == 'undefined') {
							a[v] = 1;
						}
					}
					;
					data.length = 0;
					for ( var i in a) {
						data[data.length] = i;
					}
					return data;
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
					if ($scope.accepted === "1") {
						$scope.searchInbox($scope.user, _pagination);
					} else if ($scope.Sent == "2") {
						$scope.searchOutbox($scope.user, _pagination);
					} else {

					}
				};
				$scope.getRecieverList = function(keywords) {
					// var keywords = $("input .select2-input").val();
					// alert(keywords);
					SecurityService.getUsers(keywords).then(function(data) {
						$scope.recieverList = data;
					}, function(error) {
						growl.addErrorMessage("获取用户列表失败");
					});
					return false;
				};
				/**
				 * 发送消息
				 */
				$scope.sendMesage = function(recieverIds, title, content, isEmail) {
					angular.element("#btn_send_message").addClass("disabled");
					angular.element("#btn_send_message").text("发送中...");
					$scope.recieverIds = recieverIds;
					$scope.title = title;
					$scope.content = content;
					$scope.isEmail = isEmail;
					MessageService.sendMessages($scope.recieverIds, $scope.title, $scope.content, $scope.isEmail).then(function(data) {
						if (data.id == "5") {
							$scope.recieverIds = [];
							$scope.title =　"";
							$scope.content = "";
							$scope.isEmail = "N";
							angular.element("#btn_send_message").removeClass("disabled");
							angular.element("#btn_send_message").text("发送");
							growl.addSuccessMessage(data.content);
						} else {
							angular.element("#btn_send_message").removeClass("disabled");
							angular.element("#btn_send_message").text("重试");
							growl.addErrorMessage(data.content);
						}
					}, function(error) {
						angular.element("#btn_send_message").removeClass("disabled");
						angular.element("#btn_send_message").text("重试");
						growl.addErrorMessage("消息发送失败啦，请重试");
					});
					//angular.element("#btn_send_message").removeClass("disabled").html("发送");
					/*angular.element("#btn_send_message").removeClass("disabled");
					angular.element("#btn_send_message").text("发送");*/
				};
				// 是否显示已发送、已接受和发消息的选中状态
				$scope.showmodal = function(index) {
					$scope.accepted = index;
					$scope.Sent = index;
					$scope.sendmessage = index;
				};
				// 鼠标移动上去的时候，标题变化
				$scope.titleColor = function(index) {
					$scope.titlenameColor = index;
				};
				// 已接收鼠标点击列表底色变化
				$scope.backgroundChange = function() {
					$scope.reply = !$scope.reply;
					$scope.backnameChange = !$scope.backnameChange;
					$scope.ShowEllipsisCharacter();
				};
				// 已发送鼠标点击列表底色变化
				$scope.backgroundChange1 = function() {
					$scope.reply = !$scope.reply;
					$scope.backnameChange1 = !$scope.backnameChange1;
					$scope.ShowEllipsisCharacter();
				};
				//

				// 增加收件人>>查询所有部门及各部门人数
				$scope.addDeptReciever = function(user) {
					var modalInstance = $modal.open({
						templateUrl : 'PersonalInformation/information/PersonalInformation.informationAdd.modal.html',
						backdrop : 'static',
						// windowClass: 'modal-lg',
						controller : PersonalInformationAddModalCtrl,
						resolve : {
							user : function() {
								return user;
							},
							deptList : function() {
								return $scope.deptList;
							},
							setRecieverIds : function() {
								return $scope.setRecieverIds;
							}
						}
					});
				};

				// editcontent
				$scope.editcontent = function(index) {
					$scope.editconditionpencil = index;
				};
				$scope.setRecieverIds = function(data) {
					$scope.recieverIds = data
				};
				var PersonalInformationAddModalCtrl = function($scope, $modalInstance, user, deptList, setRecieverIds) {

					$scope.user = user;
					$scope.deptList = deptList;
					console.log("$scope.deptList", $scope.deptList);
					// 点击弹框文字底色变化
					// 选择机构底色变化
					$scope.backChange = function(dept) {
						if (dept.checked == true) {
							dept.checked = false;
						} else {
							dept.checked = true;
						}
					};
					/**
					 * 添加部门收件人
					 */
					$scope.addReciever = function(users) {
					};
					$scope.addDeptReciever = function(users) {
						$scope.addReciever(user);
					};

					$scope.ok = function() {
						var checkedDeptList = $filter('filter')($scope.deptList, {
							checked : true
						});
						console.log("checkedDeptList", checkedDeptList);
						// var checkedResourceList =
						// $filter('filter')($scope.resourceList,
						// {checked:true});
						// var flag = "0";
						/*
						 * if(!$scope.organizationModal){ flag = "1";
						 * checkedDeptList = $scope.multi2Value; }
						 */
						MessageService.findUsersByDeptIds(checkedDeptList).then(function(data) {
							if (data != null) {
								console.log("data", data);
								var users = [];
								$.each(data,function(i,d){
									users.push(d.id);
								})
								setRecieverIds(users);
								$modalInstance.close();
								// growl.addSuccessMessage("");
							} else {
								$modalInstance.close();
							}
						});
					};
					$scope.cancel = function() {
						$modalInstance.dismiss('cancel');
					};

				};
				/*$scope.$watch('queryParam', function(newValue, oldValue) {
					//alert(newValue);
					if (newValue !== oldValue) {
						$scope.getRecieverList(newValue);
						$scope.query.callback({
							results : $scope.recieverList
						});
					}
				});*/
				
				// 显示和隐藏多余文字
				$scope.ShowEllipsisCharacter = function() {
					$scope.ellipsisShow = !$scope.ellipsisShow;
				};
				$scope.matchStart = function(term, text) {
					if (text.toUpperCase().indexOf(term.toUpperCase()) == 0) {
						return true;
					} else {
						return false;
					}
				};
				var init = function() {
					// console.log($scope.user);
					$scope.reply = false;
					$scope.ellipsisShow = false;
					$scope.$parent.active = 3;
					$scope.backnameChange = false;
					$scope.backnameChange1 = false;
					$scope.accepted = '1';

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
					}

					/*
					 * $scope.recieverIds = { id : 'AK', // id text : 'Alaska' //
					 * name };
					 */// 结构必须一样，否则默认值不匹配，显示为underfined
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
					$scope.retrieveUser();
					$scope.searchInbox();
					$scope.searchDeptList();
					$scope.getRecieverList();
					$scope.select2Options = {
						multiple : true,
						theme : "bootStrap",
						closeOnSelect : false,
						allowClear : true,
						// data : $scope.recieverList,
						// 'simple_tags': true,
						// matcher: $scope.matchStart,
						// resultsAdapter : 'ResultsAdapter',
						// selectionAdapter : "SingleSelection",
						// maximumInputLength : 3,
						// minimumInputLength : 2,
						// width: "element",
						// simple_tags: true,
						// allowClear: true,
						// maximumSelectionSize: 3,
						query : function(query) {
							query.callback({
								results : $scope.recieverList
							});
							//$scope.query = query;
							//$scope.queryParam = query.term;
							console.log("query:", $scope.queryParam);
							// 监听一个model 当一个model每次改变时 都会触发第2个函数
						}
					};
				};
				init();
			} ]);
});
