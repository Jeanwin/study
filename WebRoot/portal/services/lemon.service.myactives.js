/**
 * Created by Administrator on 14-3-20.
 */
define([ 'angular', 'config' ], function(angular, config) {
	angular.module('lemon.service.myactives', []).constant('MyActivesServiceConfig', {
		urls : {
			myactivesList : config.backend.ip + config.backend.base + 'myworks/getMyworks'
		}
	}).factory('MyActivesService', [ '$http', '$q', 'MyActivesServiceConfig', function($http, $q, MyActivesServiceConfig) {
		return {
			myactivesList : function(keywords, pagination) {
				var deffered = $q.defer();
				var _url = MyActivesServiceConfig.urls.myactivesList;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						keywords : keywords,
						page : pagination
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			}
		};
	} ]);

});