/**
 * Created by Administrator on 14-3-20.
 */
define([ 'angular', 'config' ], function(angular, config) {
	angular.module('olive.service.message', []).constant('MessageServiceConfig', {
		urls : {
			queryInbox : config.backend.ip + config.backend.base + 'message/queryInbox',
			queryOutbox : config.backend.ip + config.backend.base + 'message/queryOutbox',
			sendMessages : config.backend.ip + config.backend.base + 'message/sendMessages',
			inboxDel : config.backend.ip + config.backend.base + 'message/inboxDel',
			outboxDel : config.backend.ip + config.backend.base + 'message/outboxDel',
			queryDepts : config.backend.ip + config.backend.base + 'dept/queryDepts',
			viewMessage: config.backend.ip + config.backend.base + 'message/viewMessage',
			findUsersByDeptIds: config.backend.ip + config.backend.base + 'user/findUsersByDeptIds'
		}
	}).factory('MessageService', [ '$http', '$q', 'MessageServiceConfig', function($http, $q, MessageServiceConfig) {
		return {
			queryInbox : function(keywords, pagination) {
				var deffered = $q.defer();
				pagination.offset = pagination.pageIndex;
				pagination.limit = pagination.pageSize * 1;
				console.log(pagination);
				var _url = MessageServiceConfig.urls.queryInbox;
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
			queryOutbox : function(keywords, pagination) {
				console.log(pagination);
				var deffered = $q.defer();
				pagination.offset = pagination.pageIndex;
				pagination.limit = pagination.pageSize * 1;
				var _url = MessageServiceConfig.urls.queryOutbox;
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
			sendMessages : function(recieverIds, title, content,isEmail) {
				console.log(recieverIds, title, content);
				var deffered = $q.defer();
				var _url = MessageServiceConfig.urls.sendMessages;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						recieverIds : recieverIds,
						title : title,
						content : content,
						isEmail:isEmail
					},
					timeout : 300000
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			inboxDel : function(msgs) {
				console.log(msgs);
				var deffered = $q.defer();
				var _url = MessageServiceConfig.urls.inboxDel;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						data : msgs,
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			outboxDel : function(keywords) {
				console.log(keywords);
				var deffered = $q.defer();
				var _url = MessageServiceConfig.urls.outboxDel;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						data : keywords
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			/**
			 * 查询部门
			 */
			queryDepts : function(keywords) {
				console.log(keywords);
				var deffered = $q.defer();
				var _url = MessageServiceConfig.urls.queryDepts;
				$http({
					method : 'GET',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						keywords : keywords,
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			/**
			 * 标记为已阅读
			 */
			viewMessage : function(msg) {
				console.log(msg);
				var deffered = $q.defer();
				var _url = MessageServiceConfig.urls.viewMessage;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						id : msg.id,
						recieverid: msg.recieverid
					},
					timeout : config.backend.timeout
				}).success(function(data) {
					deffered.resolve(data);
				}).error(function(e, code) {
					deffered.reject(code);
				});
				return deffered.promise;
			},
			/**
			 * 根据部门查找联系人
			 */
			findUsersByDeptIds : function(checkedDeptList) {
				console.log(checkedDeptList);
				var deffered = $q.defer();
				var _url = MessageServiceConfig.urls.findUsersByDeptIds;
				$http({
					method : 'POST',
					dataType : "json",
					contentType : 'application/json; charset=UTF-8',
					url : _url,
					headers : {},
					data : {
						depts : checkedDeptList
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