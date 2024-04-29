import request from '@/common/request.js';

function handleToLogin() {
    return new Promise((resolve, reject) => {
        var that = this;
        wx.showModal({
            title: '授权登录提示',
            content: '是否允许授权？',
            showCancel: true,
            confirmText: '允许',
            confirmColor: '#669F76',
            cancelColor: '#CC463D',
            success: async res => {
                if (res.confirm) {
                    await wx.login({
                        success: function(resp) {
                            if (resp.code) {
                                
								let opts = {
									url: '/oauth2/token',
									method: 'post',
									data: {
									    grant_type: 'mobile',
									    scope: 'server',
										code: resp.code
									  }
								};
                                request.httpRequest(opts).then(res => {
									console.log(res)
									uni.setStorageSync('token',res.data.access_token)
									uni.setStorageSync('user_info',res.data.user_info)
									resolve(res)
								})

                            }
                        },
                        fail: function(resp) {
                            wx.showToast({
                                title: '出错了，请重新登录',
                                mask: true,
                                icon: 'error'
                            });
                            reject('登录失败');
                        }
                    });
                } else {
                    wx.showToast({
                        title: '取消授权',
                        mask: true,
                        icon: 'error'
                    });
                    reject('用户取消授权');
                }
            }
        });
    });
}


// 将方法暴露出去以便在其他地方使用
module.exports = {
    handleToLogin: handleToLogin
};