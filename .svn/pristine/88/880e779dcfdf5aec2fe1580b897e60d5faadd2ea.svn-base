db = function() {
	var store = window.localStorage, doc = document.documentElement;
	if (!store) {
		doc.style.behavior = 'url(#default#userData)';
	}
	return {
		/**
		 * 保存数据
		 */
		set : function(key, val, context) {
			if (store) {
				return store.setItem(key, val, context);
			} else {
				doc.setAttribute(key, value);
				return doc.save(context || 'default');
			}
		},
		/**
		 * 读取数据
		 */
		get : function(key, context) {
			if (store) {
				return store.getItem(key, context);
			} else {
				doc.load(context || 'default');
				return doc.getAttribute(key) || '';
			}
		},
		/**
		 * 删除数据
		 * 
		 * @param {Object}
		 * @param {Object}
		 */
		rm : function(key, context) {
			if (store) {
				return store.removeItem(key, context);
			} else {
				context = context || 'default';
				doc.load(context);
				doc.removeAttribute(key);
				return doc.save(context);
			}
		},
		/**
		 * 清空数据
		 */
		clear : function() {
			if (store) {
				return store.clear();
			} else {
				doc.expires = -1;
			}
		}
	};
}();
function getCookie(name) {
	/* 获取浏览器所有cookie将其拆分成数组 */
	var arr = document.cookie.split('; ');
    console.log(document.cookie);
	for (var i = 0; i < arr.length; i++) {
		/* 将cookie名称和值拆分进行判断 */
		var arr2 = arr[i].split('=');
		if (arr2[0] == name) {
			return arr2[1];
		}
	}
	return '';
}
