/**
 * Created by Administrator on 14-3-20.
 */
define([ 'angular', 'config' ], function(angular, config) {
	angular.module('lemon.service.resource', []).constant('ResourceServiceConfig', {
		urls : {
			resourceList : config.backend.ip + config.backend.base + 'resource/resourceList', // 资源列表
			addFloder : config.backend.ip + config.backend.base + 'resource/addFloder', // 新建文件夹
			modify : config.backend.ip + config.backend.base + 'resource/modify', // 修改
			move : config.backend.ip + config.backend.base + 'resource/move', // 移动
			del : config.backend.ip + config.backend.base + 'resource/delete', // 删除
			share : config.backend.ip + config.backend.base + 'resource/shareResource', // 分享
			down : config.backend.ip + config.backend.base + 'resource/down', // 下载
			detpList : config.backend.ip + config.backend.base + 'dept/deptList', // 机构列表
			dirTree : config.backend.ip + config.backend.base + 'resource/tree', // 目录树
			dirTrees : config.backend.ip + config.backend.base + 'resource/trees', // 目录树
			getAllUser : config.backend.ip + config.backend.base + 'user/getAllUser',
			getVideoTrees : config.backend.ip + config.backend.base + 'resource/videoTrees', // 视频树
			getLatestVideos : config.backend.ip + config.backend.base + 'resource/getLatestVideos',
			shareList : config.backend.ip + config.backend.base + 'resource/shareList',
			myShareList : config.backend.ip + config.backend.base + 'resource/myShareList',
			cancelShare : config.backend.ip + config.backend.base + 'resource/cancelShare',
			storeMyShare : config.backend.ip + config.backend.base + 'resource/storeMyShare',
			cancelStoreMyShare : config.backend.ip + config.backend.base + 'resource/cancelStore',
			storeResource : config.backend.ip + config.backend.base + 'resource/storeResource',
			previewTrans : config.backend.ip + config.backend.base + 'resource/previewTrans',
			deleteByRsourceId : config.backend.ip + config.backend.base + 'resource/deleteByRsourceId'
		}
	}).factory('ResourceService', [ '$http', '$q', 'ResourceServiceConfig', function($http, $q, resourceServiceConfig) {
		return {
			myShareList : function(keywords, pagination) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.myShareList;
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
			},
			shareList : function(keywords, pagination) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.shareList;
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
			},
			resourceList : function(keywords, pagination) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.resourceList;
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
			},
			getLatestVideos : function() {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.getLatestVideos;
				$http({
					method : 'GET',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			addFloder : function(parentid, name) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.addFloder;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						name : name,
						parentid : parentid
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			modify : function(resource) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.modify;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : resource,
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			dirTree : function() {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.dirTree;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			dirTrees : function() {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.dirTrees;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			getVideoTrees : function() {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.getVideoTrees;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			move : function(list, id) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.move;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					data : {
						list : list,
						id : id
					},
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			del : function(list) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.del;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					data : list,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			down : function(list) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.down;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					data : list,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			detpList : function(list) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.detpList;
				$http({
					method : 'GET',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			shareResource : function(flag, list, resourceList) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.share;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					data : {
						flag : flag,// 判断是用户还是机构
						list : list,
						resourceList : resourceList
					},
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			getAllUser : function(loginname) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.getAllUser;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					data : {
						loginname : loginname
					},
					url : _url,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			cancelShare : function(shareListDel) {
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.cancelShare;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					data : shareListDel,
					url : _url,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			cancelStoreMyShare : function(storeDel) {
				console.log("storeDel",storeDel);
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.cancelStoreMyShare;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					data : storeDel,
					url : _url,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			storeMyShare : function(storeList) {
				console.log("storeList",storeList);
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.storeMyShare;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					data : storeList,
					url : _url,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			storeResource : function(storeList){
				console.log("storeList",storeList);
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.storeResource;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					data : storeList,
					url : _url,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			previewTrans : function(resource){
				console.log("转码资源：",resource);
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.previewTrans;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					data : resource,
					url : _url,
					headers : {},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			deleteByRsourceId : function(resourceId){
				console.log("合成失败，删除资源：",resourceId);
				var deffered = $q.defer();
				var _url = resourceServiceConfig.urls.deleteByRsourceId;
				$http({
					method : 'POST',
					dataType : "json",
					//contentType : 'application/json; charset=UTF-8',
					data : resourceId,
					url : _url,
					headers : {},
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