
let baseUrl = 'http://localhost:9999/admin';

// 不带token请求
const httpRequest = (opts) => {
	uni.onNetworkStatusChange(function(res) {
		if (!res.isConnected) {
			uni.showToast({
				title: '网络连接不可用！',
				icon: 'none'
			});
		}
		return false
	});
	
	
	let httpDefaultOpts = {
	        url: baseUrl + opts.url,
	        
			data: opts.data,
	        method: opts.method,
	        header: {
	            'Authorization': 'Basic YXBwOmFwcA==',
	            'Content-Type': 'application/x-www-form-urlencoded'
	          },
	    };
	let promise = new Promise(function(resolve, reject) {
		uni.request(httpDefaultOpts).then(
			(res) => {
				console.log(res)
				resolve(res[1])
			}
		).catch(
			(response) => {
				reject(response)
			}
		)
	})
	return promise
};

//带Token请求
const httpTokenRequest = (opts) => {

	uni.onNetworkStatusChange(function(res) {
		if (!res.isConnected) {
			uni.showToast({
				title: '网络连接不可用！',
				icon: 'none'
			});
		}
		return false
	});
	let token = uni.getStorageSync('token');
	console.log(token)
	// hadToken()
	if (token == '' || token == undefined || token == null) {
		uni.showToast({
			title: '账号已过期，请重新登录',
			icon: 'none',
			complete: function() {
				uni.clearStorageSync();
			}
		});
	} else {
		let httpDefaultOpts = {
		  url:  baseUrl + opts.url,
		  data: opts.data,
		  method: opts.method,
		  header: {
		    'Authorization': 'Bearer ' + token,
			'Content-Type': "application/json",
		  },
		  
		}

		let promise = new Promise(function(resolve, reject) {
			uni.request(httpDefaultOpts).then(
				(res) => {
					if (res[1].statusCode == 200) {
						resolve(res[1])
					} else {
						if (res[1].statusCode == 424) {
							uni.clearStorageSync();
							uni.reLaunch({
								url: '/pages/index/tabbar?type='+ 'me'
							});
							
						} else {
							resolve(res[1])
							
						}
					}
				}
			).catch(
				(response) => {
					reject(response)
				}
			)
		})
		return promise
	}
	// let token = uni.getStorageSync('token')
	//此token是登录成功后后台返回保存在storage中的

};

// 拦截器
const hadToken = () => {
	let token = uni.getStorageSync('token');

	if (token == '' || token == undefined || token == null) {
		// uni.showToast({
		// 	title: '账号已过期，请重新登录',
		// 	icon: 'none',
		// 	complete: function() {
				
				uni.clearStorageSync();
		// 	}
		// });
		return false;
	}
	return true
}
export default {
	baseUrl,
	httpRequest,
	httpTokenRequest,
	hadToken
}
