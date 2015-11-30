define([ 'app', 'config' ], function(app, config) {
	app.registerController('PersonalInformationCtrl', [ '$scope', '$modal', '$timeout', 'SecurityService', function($scope, $modal, $timeout, SecurityService) {
		$scope.active = 0;
		// $scope.file;
		$scope.imgData = {
			"url" : "assets/img/icon/headPicture.jpg"
		};
		$scope.imgCheck = {
			"flag" : false
		};
		var setTreeHeight = function() {
			$timeout(function() {
				$("#personal-left").css('height', function() {
					return $("#personal-right").height();
				});
			}, 500);

		};
		// 获取用户信息
		$scope.getUserInfo = function() {
			SecurityService.getUser().then(function(data) {
				$scope.user = data;
				// $scope.global.user.roleid = ['1', '2','3'];
				console.log("我在PersonalInformationCtrl中的getUserInfo");
				console.log($scope.user);
				return $scope.user;
			}, function() {
			});
		};
		// 设置用户的方法
		$scope.setUser = function(user) {
			$scope.user = user;
		};
		$scope.$on('to-parent', function(e, d) {
			//alert("i got id");
			console.log("user", d);
			if(d === 'please reflesh user'){
				$scope.getUserInfo();
			}
		});

		var init = function() {
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
			setTreeHeight();
			$scope.getUserInfo();
		};

		init();
	} ]);
});
