<!-- 新闻 详情 -->
<template>
	<view class="container">
		<cu-custom bgColor="bg-gradual-blue" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">详细公告</block>
		</cu-custom>

		<view v-if="newsData.title">
			<view style="background-color: #FFFFFF;padding: 30rpx 30rpx 30rpx 30rpx;">
				<view class="titleBox text-xl text-black text-bold">{{newsData.title}}</view>
				<view class="flex justify-between text-df text-gray margin-top-sm margin-bottom-sm">
					<text>{{newsData.publishTime | formatDate}}</text>
					<text>停车公告</text>
				</view>

				<!-- 中间文章区域 -->
				<view class="contentBox text-lg text-black margin-top-sm margin-tb-lg">
					<view class="margin-top-sm" v-html="newsData.content">
					</view>
				</view>
			</view>	
			
			</view>
		</view>
	</view>
</template>

<script>
	import request from '@/common/request.js';
	export default {
		data() {
			return {
				newsData: [],
				requestStatus: false // 事件防抖
			}
		},
		onLoad(option) {
			console.log(option.to)
			this.newsData = JSON.parse(option.to);
		},
		onShareTimeline() {
			return {
				title: '「智慧开发停车系统]',
				imageUrl: 'https://pic2.zhimg.com/v2-dc70b4b2b95a174ab7d7bc5e54900cdf_720w.jpg?source=172ae18b',
			}
		},
		methods: {
		},
		filters: {
			formatDate(value) {
				if (value == undefined) {
					return;
				}
				// let date = new Date(value * 1000);
				let date = new Date(value);
				//时间戳为10位需*1000，时间戳为13位的话不需乘1000
				let y = date.getFullYear();
				let MM = date.getMonth() + 1;
				MM = MM < 10 ? ('0' + MM) : MM; //月补0
				let d = date.getDate();
				d = d < 10 ? ('0' + d) : d; //天补0
				let h = date.getHours();
				h = h < 10 ? ('0' + h) : h; //小时补0
				let m = date.getMinutes();
				m = m < 10 ? ('0' + m) : m; //分钟补0
				let s = date.getSeconds();
				s = s < 10 ? ('0' + s) : s; //秒补0
				// return y + '-' + MM + '-' + d; //年月日
				return y + '-' + MM + '-' + d + ' ' + h + ':' + m; //年月日时分秒
			},
			
		}
	}
</script>

<style lang="scss" scoped>
	button::after {
		border: none;
		background: transparent;
	}

	uni-button {
		background: transparent;
	}

	.container {
		background-color: #f2f2f2;
		width: 750rpx;

	}

	.solid {
		border-radius: 50rpx;
		text-indent: 12rpx;
	}

	image {
		width: 750rpx;
	}

	.bottomBox {
		width: 750rpx;
		position: fixed;
		left: 0;
		bottom: 0rpx;
	}
</style>
