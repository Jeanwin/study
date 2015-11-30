/**
 * Created by Administrator on 14-3-20.
 */
define([ 'angular', 'config' ], function(angular, config) {
	angular.module('lemon.service.mynotes', []).constant('MynotesServiceConfig', {
		urls : {
			mynotesList : config.backend.ip + config.backend.base + 'mynotes/getMynotes'
		}
	}).factory('MynotesService', [ '$http', '$q', 'MynotesServiceConfig', function($http, $q, MynotesServiceConfig) {
		return {
			mynotesList : function(keywords, pagination) {
				var deffered = $q.defer();
				var _url = MynotesServiceConfig.urls.mynotesList;
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