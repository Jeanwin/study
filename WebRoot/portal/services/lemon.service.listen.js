/**
 * Created by Administrator on 14-3-20.
 */
define([ 'angular', 'config' ], function(angular, config) {
	angular.module('lemon.service.listen', []).constant('ListenServiceConfig', {
		urls : {
			listenList : config.backend.ip + config.backend.base + 'myrecords/getMyrecords'
		}
	}).factory('ListenService', [ '$http', '$q', 'ListenServiceConfig', function($http, $q, ListenServiceConfig) {
		return {
			listenList : function(keywords, pagination) {
				var deffered = $q.defer();
				var _url = ListenServiceConfig.urls.listenList;
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