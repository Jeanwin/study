define([ 'app', 'config' ], function(app, config) {
	app.registerController('PersonalSetCtrl', [ '$scope', 'CodeService','$modal', 'SecurityService', 'growl', '$upload', '$timeout', '$location',
			function($scope,CodeService,$modal, SecurityService, growl, $upload, $timeout, $location) {
				// 是否显示个人信息和密码修改
				$scope.showmodal = function(index) {
					$scope.Myinformation = index;
					$scope.Mypassword = index;
				};
				$scope.progress = -1;
				$scope.upload;
				$scope.error = false;
				// $scope.img;
				$scope.file;
				$scope.imgData = {
					"url" : "assets/img/icon/headPicture.jpg"
				};
				$scope.imgCheck = {
					"flag" : false
				};
				$scope.uploadRightAway = false;
				$scope.picChange = false;

				$scope.onFileSelect = function($files) {
					$scope.picChange = true;
					$scope.file = $files[0];

					if ($files.length > 1) {
						growl.addSuccessMessage('一次只能上传一个文件');
					}

					if ($scope.file.type.indexOf('image') < 0) {
						growl.addErrorMessage('请上传图片文件');
						$scope.file = null;
						return false;
					}

					var fileReader = new FileReader();
					fileReader.readAsDataURL($scope.file);
					var loadFile = function(fileReader) {
						fileReader.onload = function(e) {
							$timeout(function() {
								// $scope.img = e.target.result;
								$scope.imgData.url = e.target.result;
								$scope.imgCheck.flag = true;
							}, 1000);
						}
					}(fileReader);

					$scope.progress = -1;
					if ($scope.uploadRightAway) {
						$scope.save();
					}
				};

				// 重新获取用户信息
				$scope.retrieveUser = function() {
					$scope.$emit('to-parent', 'please reflesh user');
					SecurityService.getUser().then(function(data) {
						$scope.setUser(data);
						// console.log("$scope.retrieveUseripload",$scope.user)
						// console.log("$emit",$scope.user);
						// here
					}, function() {
					});
				};
				$scope.setUser = function(user) {
					$scope.user = user;
				};
				$scope.modifyPassword = function(originalPassword, newPassword, secondPassword) {
					var keywords = {
						'loginname' : $scope.user.loginname,
						'oldPassword' : originalPassword,
						'newPassword' : newPassword,
						'repPassword' : secondPassword
					}
					SecurityService.modifyPassword(keywords).then(function(data) {
						if (data.content == "密码修改成功") {
							growl.addSuccessMessage(data.content);
						} else {
							growl.addErrorMessage(data.content);
						}
					}, function(error) {
						growl.addErrorMessage("密码修改失败啦，请重试");
					});
				};

				/*
				 * //修改用户--Email验重 $scope.selectEmail = function(){ var findUser = {
				 * "email":$scope.user.email }; if($scope.user.email !== ''){
				 * SecurityService.findEmail().then( function(data){
				 * $scope.showEmailError = data > 0; }, function(code){
				 * growl.addErrorMessage('发生意外错误'); } ); }; };
				 */
				// 修改用户--电话号码验重
				/*
				 * $scope.selectPhone = function(){ var findUser = {
				 * "phone":$scope.user.phone }; if($scope.user.phone !== ''){
				 * SecurityService.findPhone().then( function(data){
				 * $scope.showPhoneError = data > 0; }, function(code){
				 * growl.addErrorMessage('发生意外错误'); } ); }; };
				 */
				/**
				 * 修改个人信息
				 */
				$scope.modifyPersonalInfo = function(user) {
					// alert(user.pictureURL);
					if ($scope.file) {
						var url = config.backend.ip + config.backend.base + 'user/modifyPersonalInfo';
						// alert($scope.file);
						console.log(user);
						var data = {
							id : $scope.user.id,		
							loginname : $scope.user.loginname,
							pictureURL : $scope.user.pictureURL === undefined ? "" : user.pictureURL,
							sex : $scope.user.sex === undefined ? " " : user.sex,
							phone : $scope.user.phone === undefined ? " " : user.phone,
							email : $scope.user.email === undefined ? " " : user.email,
							remark : $scope.user.remark === undefined ? " " : user.remark
						};
						$scope.upload = $upload.upload({
							url : url,
							method : 'POST',
							data : data,
							file : $scope.file
						}).progress(function(evt) {
							$scope.progress = parseInt(100.0 * evt.loaded / evt.total);
						}).success(function(data, status, headers, config) {
							if (data.id === "6") {
								growl.addSuccessMessage(data.content);
								$scope.retrieveUser();
							} else {
								growl.addErrorMessage(data.content);
							}
						}).error(function() {
							growl.addErrorMessage("不好意思，修改出错了，请重试");
							$scope.error = true;
						});
					} else {
						var data = {
							id : $scope.user.id,	
							loginname : $scope.user.loginname,
							sex : $scope.user.sex === undefined ? " " : user.sex,
							phone : $scope.user.phone === undefined ? " " : user.phone,
							email : $scope.user.email === undefined ? " " : user.email,
							remark : $scope.user.remark === undefined ? " " : user.remark
						};
						SecurityService.modifyUser(data).then(function(data) {
							if (data.id === '5') {
								growl.addSuccessMessage("用户信息修改成功！");
								$scope.retrieveUser();
							} else {
								growl.addErrorMessage(data.content);
							}
						}, function() {
							growl.addErrorMessage("不好意思，修改出错了，请重试");
						});
					}
					;
				};

				// 添加用户--Email验重

				$scope.selectEmail = function() {
					var findUser = {
						"email" : $scope.user.email
					};
					console.log("validateEmail", $scope.user.email);
					$scope.showEmailError = false;
					$scope.emailValid = false;
					if ($scope.user.email !== '' && angular.isDefined($scope.user.email)) {
						SecurityService.validateEmail($scope.user.email).then(function(data) {
							if (data.id === '1') {
								$scope.emailValid = true;
								$scope.emailMsg = data.content;
								// $scope.showEmailError = true;
							} else if (data.id === '2') {
								$scope.showEmailError = true;
							} else {
								growl.addErrorMessage(data.content);
							}
						}, function(code) {
							growl.addErrorMessage('验证邮箱号发生意外错误');
						});
					}
					;
				};
				// 添加用户--电话号码验重
				$scope.selectPhone = function() {
					var findUser = {
						"phone" : $scope.user.phone
					};
					$scope.showPhoneError = false;
					$scope.phoneValid = false;
					if ($scope.user.phone !== '' && angular.isDefined($scope.user.phone)) {
						SecurityService.validatePhone($scope.user.phone).then(function(data) {
							if (data.id === '1') {
								$scope.phoneValid = true;
								$scope.phoneMsg = data.content;
								$scope.showPhoneError = false;
							} else if (data.id === '2') {
								$scope.showPhoneError = true;
							} else {
								growl.addErrorMessage(data.content);
							}
						}, function(code) {
							growl.addErrorMessage('验证手机号发生意外错误');
						});
					}
					;
				};
				/**
				 * 验证密码
				 */
				$scope.validatePassword = function() {
					var password = $scope.originalPassword;
					var loginname = $scope.user.loginname;
					console.log(password,loginname);
					$scope.showPasswordError = false;
					$scope.passwordValid = false;
					if (password !== '' && angular.isDefined(password)) {
						SecurityService.validatePassword(loginname,password).then(function(data) {
                            if(data.id === '2'){
                            	$scope.passwordValid = true;
                            	$scope.passwordMsg = data.content;
                            }else if(data.id === '3'){
                            	$scope.showPasswordError = true;
                            }else {
								growl.addErrorMessage(data.content);
							}   
						}, function(code) {
							growl.addErrorMessage('验证密码发生意外错误');
						});
					}
					;
				};
				// 字典列表
				$scope.getcode = function(value) {
					CodeService.getCode(value).then(function(data) {
						if(value="sex"){
							$scope.sexList = data;
						}
					}, function() {

					});
				};

				var init = function() {
					$scope.retrieveUser();
					$scope.showEmailError = false;
					$scope.emailValid = false;
					$scope.showPhoneError = false;
					$scope.phoneValid = false;
					$scope.showPasswordError = false;
					$scope.passwordValid = false;
					$scope.Myinformation = '1';
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
					$scope.getcode("sex");
					$scope.$parent.active = 4;
				};
				init();
			} ]);
});