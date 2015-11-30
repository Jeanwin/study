define([ 'app', 'config' ], function(app, config) {
	app.registerController('myActivesCtrl', [ '$scope', '$cookieStore', '$modal', 'CodeService', 'MyActivesService', 'MessageService', 'SecurityService', '$interval', '$filter', 'growl',
			function($scope, $cookieStore, $modal, CodeService, MyActivesService, MessageService, SecurityService, $interval, $filter, growl) {
		
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
			myactivesList($scope.keywords);

		};
		
		
		//评课列表
		var myactivesList = function(key) {
			MyActivesService.myactivesList(key, $scope.pagination).then(function(data) {
				$scope.myactivesList = data.data;
				$scope.codeMap = data.codeMap.data;
				$scope.total =data.total;
				$scope.countPS= data.countPS;
				$scope.pagination.totalItems = data.total;
				$scope.pagesize = parseInt(($scope.pagination.totalItems - 1) / $scope.pagination.pageSize + 1);
			}, function() {

			});
		};
		
		// 翻译
		$scope.transfer = function(dic, value) {
			var name = "";
			$.each(dic, function(index, data) {
				if (data.value == value) {
					name = data.name;
					return false;
				}
			});
			return name;
		};
		
		
		var init = function() {
					$scope.$parent.active = 9;
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
					myactivesList($scope.keywords);
				};
				init();
			} ]);
});
