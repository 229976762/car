<template>
	<view class="components-home">

	

		<view class="bgImg">

			<view class="bannerBox">
				<swiper style="height: 520rpx;" class="swiper" @change="cardSwiper" circular="true"
					indicator-dots="true" autoplay="true" interval="4000" duration="600">
					<swiper-item class="swiper-list" v-for="(item, index) in bannerList" :key="index">
						<view class="swiper-item uni-bg-red">
							<image class="swiper-img radius shadow-warp" :src="item.imageUrl" mode="widthFix"></image>
						</view>
					</swiper-item>
				</swiper>
				<view class="indication">
					<block v-for="(item,index) in bannerList" :key="index">
						<view class="spot" :class="cardCur == index ? 'active': '' "></view>
					</block>
				</view>
			</view>

			
			
			<view class="cu-card article no-card">
				<view class="cu-item shadow borderBottom" v-for="(item, index) in newsList" :key="index">
					<view class="title">
						<view class="text-cut text-bold text-lg">{{item.lotName}}</view>
					</view>
					<view class="content">
						<image src="../../static/logo.png" mode="aspectFit"></image>
						<view class="desc">
							<view class="padding text-black text-xl text-bold">{{item.plateNumber}}</view>
							<view class="margin-top-xs" style="display: flex;align-items:center;justify-content: space-between;">
								<view class="text-gray light sm text-df round fl">{{item.paymentTime}}</view>
								<view>
									<view class="text-gray light sm round margin-lr-xs"
										style="display: inline-flex;align-items:center;">
										<text class="text-gray text-df" style="margin-right: 6rpx;">{{item.status === 0 ? '待支付' : item.status === 1 ? '已支付' : '已取消'}}</text>
										<text class="text-df" style="margin-top: 2rpx;">{{item.fee}}元</text>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
				
				
				<view class="page-box" v-if="newsList.length < 1">
					<view>
						<view class="centre">
							<image src="https://cdn.zhoukaiwen.com/noData1.png" mode="widthFix"></image>
							<view class="explain">
								暂无数据
								<view class="tips">可以去看看有其他</view>
							</view>
							<!-- <view class="btn">随便逛逛</view> -->
						</view>
					</view>
				</view>
			</view>
			
			
			
			
			
			</view>
		<view style="height: 120rpx;width: 1rpx;"></view>

	</view>
</template>

<script>
	import request from '@/common/request.js';
	export default {
		data() {
			return {
				cardCur: 0,
				bannerList: [{
						imageUrl: 'https://cdn.zhoukaiwen.com/qh_banner8.jpg'
					},
					{
						imageUrl: 'https://pic2.zhimg.com/v2-f98227a722dc687b4b5caaae41a8801d_xs.jpg?source=172ae18b'
					}
				],
				newsList: []
			}
		},
		onShow() {

		},
		mounted() {
			var id = uni.getStorageSync('user_info').id
			
			let opts = {
				url: `/carOrder/wx/${id}`,
				method: 'get',
				data: {
					title: '',
					content: '',
					current: 1,
					size: 3,
					descs: '',
					ascs: ''
				}
			};
			request.httpTokenRequest(opts).then(res => {
				console.log(res);
				// uni.hideLoading();
				if (res.statusCode == 200) {
					this.newsList = res.data.data;
					
				} else {
					console.log('数据请求错误～');
				}
			});
		},
		methods: {
			cardSwiper(e) {
				
			},
			
		}
	}
</script>

<style lang="scss" scoped>
	.swiper-item {
		height: 100%;
	}

	/* 轮播指示点 start*/
	.indication {
		z-index: 9999;
		width: 100%;
		height: 36rpx;
		position: absolute;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: center;
	}

	.spot {
		background-color: #FFFFFF;
		opacity: 0.6;
		width: 10rpx;
		height: 10rpx;
		border-radius: 20rpx;
		top: -130rpx;
		margin: 0 8rpx !important;
		position: relative;
	}

	.spot.active {
		opacity: 1;
		width: 30rpx;
		background-color: #0081ff;
	}

	.bgImg {
		position: relative;
		margin-bottom: 100rpx;

		.left_box {
			position: absolute;
			height: 130rpx;
			width: 60%;
			background: #ffffff;
			bottom: -55rpx;
			left: 5%;
			border-radius: 15rpx;
		}

		.right_box {
			text-align: center;
			position: absolute;
			height: 130rpx;
			width: 25%;
			background: #ffffff;
			bottom: -55rpx;
			right: 5%;
			border-radius: 15rpx;
		}
	}

	.mainBox {
		width: 750rpx;
		height: 300rpx;
		padding: 0 5%;
		margin-bottom: 10rpx;

		.mainBtn {
			width: 45%;
		}
	}

	.radius {
		border-radius: 18rpx !important;
	}

	// 弹窗
	.cu-dialog {
		background: #FFFFFF;
		overflow: visible;
	}

	.modal_bg {
		width: 100%;
		height: 400rpx;
		position: absolute;
		top: -100rpx;
		background-image: url(https://zhoukaiwen.com/img/qdpz/modal_bg.png);
		background-size: 100%;
		background-repeat: no-repeat;
	}

	.modal_main {
		text-align: left;
		background-color: #FFFFFF;
	}

	.title-header {
		position: absolute;
		bottom: 0;
		width: 100%;
		display: flex;
		height: 120rpx;
		font-size: 40rpx;
		align-items: center;
		justify-content: center;
		font-weight: bold;
		background-image: url(https://cdn.zhoukaiwen.com/wccswF.png);
		background-size: cover;
	}

	.title-text {
		background-image: -webkit-linear-gradient(0deg, #ff8a34, #FBBD12);
		-webkit-background-clip: text;
		-webkit-text-fill-color: transparent;
	}
</style>
