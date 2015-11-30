define([ 'app', 'config' ], function(app, config) {
	app.registerController('reviewCtrl', [ '$scope', '$cookieStore', '$modal', 'CodeService', 'ReviewService', 'MessageService', 'SecurityService', '$interval', '$filter', 'growl',
			function($scope, $cookieStore, $modal, CodeService, ReviewService, MessageService, SecurityService, $interval, $filter, growl) {
		
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
			
			
			
			reviewList($scope.keywords);

		};
		
		
		//评课列表
		var reviewList = function(key) {
			ReviewService.reviewList(key, $scope.pagination).then(function(data) {
				$scope.reviewList = data.data;
				$scope.reviewdNum = data.reviewdNum;
				$scope.notReviewdNum = data.notReviewdNum;
				$scope.pagination.totalItems = data.total;
				$scope.pagesize = parseInt(($scope.pagination.totalItems - 1) / $scope.pagination.pageSize + 1);
			}, function() {

			});
		};
		var init = function() {
			        $scope.baseUrl = config.backend.base;
			        $scope.temp = 'javascript:this.src=' + $scope.baseUrl + '/assets/img/lemon-pages-12.jpg';
			        $scope.baseIp = config.backend.ip;
			        $scope.domainname = config.backend.domainname;
					$scope.$parent.active = 7;
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
					reviewList($scope.keywords);
				};
				init();
			} ]);
});
