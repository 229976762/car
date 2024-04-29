<!-- 模拟数据加载 -->
<template>
	<view class="container">
		<cu-custom class="navBox" bgColor="bg-gradual-blue" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">模拟数据加载</block>
		</cu-custom>
		
		<div>{{JSON.stringify(dataList)}}</div>

		<button class="blueBtn cu-btn bg-blue shadow lg" @tap="getData()">数据加载</button>
	</view>
</template>

<script>
	
	import { handleToLogin } from '@/common/handeLogin'
	
	export default {
		data() {
			return {
				dataList: [],
			}
		},
		watch: {

		},
		mounted() {

		},
		methods: {
			
			getData() {
				uni.reLaunch({
					url: '/pages/index/tabbar?type='+ 'me'
				});
				// uni.showLoading({
				// 	title: '加载中'
				// })
				// handleToLogin().then(res => {
				// 	console.log("2222->", res)
				// 	uni.hideLoading();
				// })

			}
		}
	}
</script>

<style scoped>
	.blueBtn {
		width: 500rpx;
		margin: 50rpx auto;
		display: block;
		line-height: 80rpx;
	}
</style>
