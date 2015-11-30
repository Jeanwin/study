define([ 'app', 'config' ], function(app, config) {
	app.registerController('listenCtrl', [ '$scope', '$cookieStore', '$modal', 'CodeService', 'ListenService', 'MessageService', 'SecurityService', '$interval', '$filter', 'growl',
			function($scope, $cookieStore, $modal, CodeService, ListenService, MessageService, SecurityService, $interval, $filter, growl) {
		
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
			listenList($scope.keywords);

		};
		
		
		//听课列表
		var listenList = function(key) {
			ListenService.listenList(key, $scope.pagination).then(function(data) {
				$scope.listenList = data.data;
				$scope.totalNums = data.totalNums;
				$scope.pagination.totalItems = data.total;
				$scope.pagesize = parseInt(($scope.pagination.totalItems - 1) / $scope.pagination.pageSize + 1);
			}, function() {

			});
		};
		var init = function() {
					$scope.$parent.active = 8;
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
					listenList($scope.keywords);
				};
				init();
			} ]);
});
