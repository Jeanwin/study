define([ 'angular', 'config' ], function(angular, config) {
	/*-----------------------------------------
	 *  登录安全
	 ------------------------------------------*/
	angular.module('olive.service.security', [], function($provide, $httpProvider) {
		$provide.factory('myHttpInterceptor', function($q) {
			return function(promise) {
				// convert JSON
				var converJson = function(data) {
					// return JSON.parse(eval('(' + data
					// + ')'));
				};

				return promise.then(function(response) {
					// if(config.data.method === "urls"
					// &&
					// response.config.url.indexOf('.html')
					// < 0)
					// response.data =
					// converJson(response.data);
					return response;
				}, function(response) {
					return $q.reject(response);
				});
			}
		});
		$httpProvider.responseInterceptors.push('myHttpInterceptor');
	})

	.constant('SecurityServiceConfig', {
		files : {
			login : 'data/security/user.login.success.json',
			getUser : 'data/security/user.getUser.json',
			getUsers : 'data/security/user.getUsers.json',
			validatePhone : 'data/security/user.validatePhone.json',
			validateEmail : 'data/security/user.validateEmail.json',
			validatePassword :'data/security/user.validatePassword.json'
		},
		urls : {
			login : config.backend.ip + config.backend.base + 'login.do',
			getUser : config.backend.ip + config.backend.base + 'user/getUser',
			modifyPassword : config.backend.ip + config.backend.base + 'user/modifyPassword',
			modifyUser : config.backend.ip + config.backend.base + 'user/modifyUser',
			getUsers : config.backend.ip + config.backend.base + 'user/findAllUsers',
			validatePhone : config.backend.ip + config.backend.base + 'user/validatePhone',
			validateEmail : config.backend.ip + config.backend.base + 'user/validateEmail',
			validatePassword : config.backend.ip + config.backend.base + 'user/validatePassword'
		}
	}).factory('SecurityService', [ '$http', '$q', 'SecurityServiceConfig', function($http, $q, SecurityServiceConfig) {
		var user = {};

		return {
			/**
			 * 用户登录
			 * 
			 * @param user
			 *            用户信息
			 */
			login : function(user) {

				var deffered = $q.defer();
				$http({
					method : config.data.method === 'files' ? 'GET' : 'POST',
					url : $securityServiceConfig[config.data.method].login,
					headers : {},
					data : {
						user : user
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},

			logout : function(user) {

			},

			getUser : function() {
				var deffered = $q.defer();
				$http({
					method : config.data.method === 'files' ? 'GET' : 'GET',
					// method : 'GET',
					url : config.data.method === 'files' ? SecurityServiceConfig.files.getUser : SecurityServiceConfig.urls.getUser,
					// url : SecurityServiceConfig.urls.getUser,
					dataType : "json",
					headers : {},
					data : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},

			/**
			 * 获取收件人列表
			 */
			getUsers : function(keywords) {
				var deffered = $q.defer();
				$http({
					method : config.data.method === 'files' ? 'GET' : 'POST',
					url : config.data.method === 'files' ? SecurityServiceConfig.files.getUsers : SecurityServiceConfig.urls.getUsers,
					dataType : "json",
					headers : {},
					data : {name : keywords},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			// 修改用户密码
			modifyPassword : function(keywords) {
				console.log(keywords);
				console.log("security服务中的传值");
				var deffered = $q.defer();
				var _url = SecurityServiceConfig.urls.modifyPassword;
				$http({
					method : config.data.method === 'files' ? 'GET' : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : keywords,
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			// 修改个人信息--无图
			modifyUser : function(keywords) {
				var deffered = $q.defer();
				var _url = config.data.method === 'files' ? SecurityServiceConfig.files.modifyUser : SecurityServiceConfig.urls.modifyUser;
				$http({
					method : config.data.method === 'files' ? 'GET' : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : keywords,
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			// 验证手机号
			validatePhone : function(keywords) {
				var deffered = $q.defer();
				var _url = config.data.method === 'files' ? SecurityServiceConfig.files.validatePhone : SecurityServiceConfig.urls.validatePhone;
				$http({
					method : config.data.method === 'files' ? 'GET' : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						phone:keywords
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			// 验证邮箱
			validateEmail : function(keywords) {
				var deffered = $q.defer();
				var _url = config.data.method === 'files' ? SecurityServiceConfig.files.validateEmail : SecurityServiceConfig.urls.validateEmail;
				$http({
					method : config.data.method === 'files' ? 'GET' : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						email:keywords
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			// 验证初始密码
			validatePassword : function(loginname,password) {
				var deffered = $q.defer();
				var _url = config.data.method === 'files' ? SecurityServiceConfig.files.validatePassword : SecurityServiceConfig.urls.validatePassword;
				$http({
					method : config.data.method === 'files' ? 'GET' : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						loginname:loginname,
						password:password
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
		};
	} ]);
});