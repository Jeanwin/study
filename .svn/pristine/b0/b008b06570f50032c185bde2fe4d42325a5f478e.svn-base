define([ 'angular', 'config' ], function(angular, config) {
	/*-----------------------------------------
	 *获取字典接口
	 * 连带检索
	 ------------------------------------------*/

	angular.module('lemon.service.code', [])

	.constant('CodeServiceConfig', {
		urls : {
			getCode : config.backend.ip + config.backend.base + 'syscode/code', // 新建文件夹
			getCodeByParentId : config.backend.ip + config.backend.base + 'syscode'
		}
	}).factory('CodeService', [ '$http', '$q', 'CodeServiceConfig', function($http, $q, CodeServiceConfig) {
		return {
			getCode : function(value) {
				var deffered = $q.defer();
				var _url = CodeServiceConfig.urls.getCode;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						value : value
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			getCodeByParentId : function(value) {
				var deffered = $q.defer();
				var _url = CodeServiceConfig.urls.getCode;
				$http({
					method : 'GET',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						id : value
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