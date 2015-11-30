define(['angular'], function (angular) {
	angular.module('dragDropDirective', []).directive('sortable', function() {
	   return {
                restrict: "EA",
                controller: "dragDropCtrl",
                scope: {
                    language: "@"
                },
                link: function(scope, elm, attrs, ctrl) {
                }
            }
	})
	.controller("dragDropCtrl", ["$scope", "$element", "$http", function(scope, elm, $http) {
        elm.sortable({
        	cursor: "pointer"
        });
        elm.disableSelection();
    }]);
})