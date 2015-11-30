/**
 * Created by Administrator on 14-3-20.
 */
define([ 'angular', 'config' ], function(angular, config) {
	angular.module('lemon.service.review', []).constant('ReviewServiceConfig', {
		urls : {
			reviewList : config.backend.ip + config.backend.base + 'reviewRecord/getAllReviewRecords'
		}
	}).factory('ReviewService', [ '$http', '$q', 'ReviewServiceConfig', function($http, $q, ReviewServiceConfig) {
		return {
			reviewList : function(keywords, pagination) {
				var deffered = $q.defer();
				var _url = ReviewServiceConfig.urls.reviewList;
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