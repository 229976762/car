<template>
	<view>
		<cu-custom class="navBox" bgColor="bg-gradual-blue" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">确认停车</block>
		</cu-custom>

		<view class="bannerBg" @tap="plateShow = true">
			<image src="https://zhoukaiwen.com/img/keyboard.jpeg" mode="widthFix"></image>
			<view class="carNumBox">
				<view class="plate-box" :class="PageCur=='1'?'':'green'">
					<uni-label class="uni-label-pointer focus" id="input0" data-index="0">{{plateNo.substr(0,1)}}</uni-label>
					<uni-label class="uni-label-pointer" id="input1" data-index="1">{{plateNo.substr(1,1)}}</uni-label>
					<uni-label class="uni-label-pointer" id="input2" data-index="2">{{plateNo.substr(2,1)}}</uni-label>
					<uni-label class="uni-label-pointer" id="input3" data-index="3">{{plateNo.substr(3,1)}}</uni-label>
					<uni-label class="uni-label-pointer" id="input4" data-index="4">{{plateNo.substr(4,1)}}</uni-label>
					<uni-label class="uni-label-pointer" id="input5" data-index="5">{{plateNo.substr(5,1)}}</uni-label>
					<uni-label class="uni-label-pointer" id="input6" data-index="6">{{plateNo.substr(6,1)}}</uni-label>
					<uni-label class="uni-label-pointer" :class="PageCur=='1'?'xinnengyuan':''" id="input7" data-index="7">{{plateNo.substr(7,1)}}</uni-label>
				</view>
			</view>
		</view>
		<view style="margin-top: 110rpx;" class="cu-list menu">
			<view class="cu-item">
				<view class="content">
					<image src="https://zhoukaiwen.com/img/icon/carP.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">车牌号码</text>
				</view>
				<view class="action">
					<view v-if="PageCur=='1'" class="cu-tag round bg-blue light">{{plateNo | plateNoF}}</view>
					<view v-if="PageCur!='1'" class="cu-tag round bg-green light">{{plateNo | plateNoF}}</view>
				</view>
			</view>

			<view class="cu-item">
				<view class="content">
					<image src="https://zhoukaiwen.com/img/icon/car.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">车辆类型</text>
				</view>
				<view class="action">
					<text v-show="plateNo != ''" :class="PageCur=='1'?'text-blue':'text-green'">临停车辆</text>
				</view>
			</view>

			<view class="cu-item">
				<view class="content">
					<image src="https://zhoukaiwen.com/img/icon/clock2.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">入场时间</text>
				</view>
				<view class="TimeText">
					<picker mode="time" @change="onTimeChange" :value="order.paymentTime">
						<view class="Time-picker">{{getCurrentTime() + '' + order.paymentTime}}</view>
					</picker>
				</view>
				<!-- <view class="action">
					<text :class="PageCur=='1'?'text-blue':'text-green'">{{ order.paymentTime || getCurrentTime() }}</text>
				</view> -->
			</view>

			<view class="cu-item" @tap="showInputDialog">
				<view class="content">
					<image src="https://zhoukaiwen.com/img/icon/clock1.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">停车时长</text>
				</view>
				<view class="action">
					<text :class="PageCur=='1'?'text-blue':'text-green'">{{ order.useTime }}</text>
				</view>
			</view>
			<view class="cu-item">
				<view class="content">
					<image src="https://zhoukaiwen.com/img/icon/pay.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">停车费用</text>
				</view>
				<view class="action">
					<text :class="PageCur=='1'?'text-blue':'text-green'">共计：{{ order.fee }}元</text>
				</view>
			</view>
		</view>
		
		<view  class="cu-list menu">
			<view class="cu-item">
				<view class="content padding-tb-sm">
					<view class="text-lg">
						<text class="cuIcon-info text-blue margin-right-xs"></text> 停车规则
					</view>
					<view class="text-gray">
						当前空车位总数：{{lotInfo.freeSpaces}}
					</view>
					<view class="text-gray">
						时段：{{ displayTimePeriod() }}
					</view>
					<view class="text-gray">
						收费：{{lotInfo.feeRate}}元/小时
					</view>
				</view>
			</view>
		</view>
		<uni-view class="padding flex flex-direction">
			<uni-button v-show="plateNo != ''" @tap="payment" style="border-radius: 12rpx;" class="action cu-btn margin-tb-sm lg" :class="PageCur=='1'?'bg-blue':'bg-green'">确认支付 ¥{{order.fee}}元</uni-button>
			<uni-button v-show="plateNo == ''" @tap="plateShow = true" style="border-radius: 12rpx;" class="cu-btn margin-tb-sm lg" :class="PageCur=='1'?'bg-blue':'bg-green'">输入车牌号</uni-button>
		</uni-view>



		<view class="cu-modal" :class="showModal?'show':''">
			<view v-show="showModal" class="action modal-overlay" @tap="cancelInput"></view>
		    <view class="cu-dialog" style="z-index: 10000;">
		        <view class="cu-bar bg-white justify-end">
		            <view class="content text-black text-xl text-bold">停车信息</view>
		            <view class="action" @tap="cancelInput">
		                <text class="cuIcon-close text-red"></text>
		            </view>
		        </view>
				
		        <view style="margin-top: 10rpx;" class="cu-list menu">
					<view class="cu-item">
						<view class="content">
							<text class="text-grey">停车时长:</text>
						</view>
						<view class="action">
							<input v-model="order.useTime" class="input" placeholder="请输入停车时长（小时）" style="text-align: left;"></input>
						</view>
					</view>
		            
		            <p @click="confirmInput" class="btn-confirm">
		                <text class="text-blue text-xl text-bold">[确认修改]</text>
		            </p>
		            <view style="height: 11rpx;width: 1rpx;"></view>
		        </view>
		    </view>
		</view>


		<!-- 自定义键盘组件 -->
		<plate-input @typeChange="typeChange" v-if="plateShow" :plate="plateNo" @export="setPlate"
			@close="plateShow = false & close()" />
	</view>

</template>

<script>
	import plateInput from '@/components/uni-plate-input/uni-plate-input.vue';
	import request from '@/common/request.js';
	
	
	
	export default {
		components: {
			plateInput
		},
		data() {
			return {
				plateNo: '',
				plateShow: false,
				inputDisabled: true,
				showModal: false,
				PageCur: 1,
				car: {},
				lotInfo: {
					feeRate: 0
				},
				order:{
					fee: 12,
					spaceId: 0,
					useTime: 2,
					paymentTime: '12:00', //进场时间
					orderTime: null
				}
			};
		},
		onLoad(options) {
			
			this.car = JSON.parse(options.to)
			let opts = {
				url: '/carParkingLot/wx/getLost',
				method: 'get',
				data: {
					lotId: this.car.lotId
				}
			};
			var that = this
			request.httpTokenRequest(opts).then(res => {
				console.log(res)
				if(res.data.data === null){
					uni.showModal({
					  title: '提示',
					  content: '该路段已无空闲车位，将自动退出',
					  showCancel: true,
					  duration: 3000,
					  confirmText: '确定',
					  success: res => {
					    
					  }
					});
					 setTimeout(() => {
						 uni.navigateBack();
					 }, 3000);
				}
				this.lotInfo = res.data.data
				this.order.fee = this.lotInfo.feeRate * 2
			})
		},
		methods: {
			
			showInputDialog() {
			   this.showModal = true;   
			},
			confirmInput() {
			    this.order.fee = this.order.useTime * this.lotInfo.feeRate
				this.showModal = false
			},
			cancelInput() {
			  this.showModal = false
			},
			onTimeChange: function(e) {
				this.order.paymentTime = e.detail.value;
			},
			getCurrentTime() {
			  let now = new Date(); // 获取当前时间的Date对象
			  let year = now.getFullYear(); // 获取年份
			  let month = String(now.getMonth() + 1).padStart(2, '0'); // 获取月份，补0至两位
			  let date = String(now.getDate()).padStart(2, '0'); // 获取日期，补0至两位
			  
			  // 将获取到的年、月、日、小时、分钟、秒数拼接成指定格式的字符串
			  return `${year}-${month}-${date} `;
			},
			handleInput(event) {

			  this.order.fee = Number.parseInt(event.target.value) * this.lotInfo.feeRate;
			},
			setPlate(plate) {
				console.log(plate)
				if (plate.length >= 7) this.plateNo = plate;
				this.plateShow = false;
			},
			typeChange(e) {
				console.log(e);
				this.PageCur = e;
				this.plateNo = '';
			},
			close(){
				this.PageCur = 1;
			},
			displayTimePeriod() {
			  if (this.lotInfo.startTime !== null && this.lotInfo.startTime !== undefined) {
				return `${this.lotInfo.startTime} - ${this.lotInfo.endTime}`;
			  } else {
				return '全天';
			  }
			},
			handlePaymentTimeChange(event) {
			  this.order.paymentTime = event.mp.detail.value;
			},
			payment(){
				
				this.order.plateNumber = this.plateNo
				this.order.paymentTime = this.order.paymentTime.length > 10?this.order.paymentTime:this.getCurrentTime() + '' + this.order.paymentTime + ':00'
				this.order.spaceNumber = this.lotInfo.spaceNumber
				this.order.spaceId = Number.parseInt(this.lotInfo.spaceId)
				this.order.useTime = this.order.useTime.toString()
				this.order.lotName = this.lotInfo.lotName
				this.order.status = 1
				let opts = {
					url: '/carOrder/wx',
					method: 'post',
					data: this.order
				};
				request.httpTokenRequest(opts).then(result => {
					// console.log(res)
					var flag = result.data.code === 1?'余额不足，请充值': '下单成功！'
						
					uni.showModal({
					  title: '提示',
					  content: flag,
					  showCancel: false,
					  confirmText: '确定',
					  success: res => {
						  if (res.confirm) 
							 uni.navigateBack();
					  }
					});
					setTimeout(() => {
						uni.navigateBack();
					}, 3000);
				})
			}
		},
		filters:{
			plateNoF(val){
				if(val == ''){
					return '请先填写车辆号牌'
				}else{
					let arr = val.split('');
					arr.splice(2,0,' · ');
					return arr.join('')
				}
			}
		}
	};
</script>

<style lang="scss" scoped>
	
	.modal-overlay {
	    position: fixed;
	    top: 0;
	    left: 0;
	    right: 0;
	    bottom: 0;
	    background-color: rgba(0, 0, 0, 0.5); /* Adjust the opacity as needed */
	    z-index: 9999; /* Make sure it's above other content */
	}
	
	
	.bannerBg {
		width: 750rpx;
		position: relative;

		image {
			width: 750rpx;
		}

		.carNumBox {
			position: absolute;
			width: 650rpx;
			height: 180rpx;
			background-color: #ffffff;
			bottom: -90rpx;
			left: 50%;
			z-index: 9;
			margin-left: -325rpx;
			border-radius: 12rpx;
		}

		.plate-box {
			position: relative;
			width: 420rpx;
			height: 50rpx;
			padding: 24rpx;
			background: #2d66d8;
			box-sizing: content-box;
			margin: auto;
			border-radius: 10rpx;
			-webkit-transition: all 1s;
			transition: all 1s;
			margin-top: 42rpx;
		}

		.plate-box.green {
			background: -webkit-linear-gradient(#f8f8f8 20%, #16b235);
			background: linear-gradient(#f8f8f8 20%, #16b235);
			box-shadow: 0 0 4rpx 0 #ebebeb;
		}

		.plate-box::before {
			content: '';
			border: 2rpx solid #ffffff;
			position: absolute;
			top: 4rpx;
			left: 4rpx;
			width: 455rpx;
			border-radius: 10rpx;
			height: 86rpx;
		}

		.plate-box.green::before {
			border: 1px solid #000000;
		}

		.plate-box uni-label {
			position: relative;
			vertical-align: middle;
			display: inline-block;
			width: 50rpx;
			color: #fff;
			text-align: center;
			font-size: 50rpx;
			height: 50rpx;
			line-height: 50rpx;
			text-shadow: 0rpx 0rpx 2rpx #000;
		}

		.plate-box.green uni-label {
			color: #222;
			text-shadow: 0 0 1px #fff;
		}

		.plate-box uni-label.xinnengyuan::before {
			content: '';
			position: absolute;
			left: 12%;
			top: 12%;
			width: 76%;
			height: 76%;
			background-size: 100%;
			background-repeat: no-repeat;
			background-image: url(https://zhoukaiwen.com/img/icon/keyboard_icon.png);
		}

		.plate-box uni-label:nth-child(2) {
			margin-right: 20rpx;
		}

		.plate-box::after {
			content: '·';
			position: absolute;
			left: 126rpx;
			top: 30rpx;
			color: #fff;
			font-size: 50rpx;
			line-height: 40rpx;
		}

		.plate-box.green::after {
			content: '';
			position: absolute;
			left: 120rpx;
			top: 38rpx;
			width: 26rpx;
			height: 26rpx;
			background-size: 100%;
			background-repeat: no-repeat;
			background-image: url(https://zhoukaiwen.com/img/car_d_logo.png);
			background-repeat-x: no-repeat;
			background-repeat-y: no-repeat;
		}
	}

	.cu-form-group {
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
		background-color: #ffffff;
		padding: 1upx 30upx;
		display: -webkit-box;
		display: -webkit-flex;
		display: -ms-flexbox;
		display: flex;
		align-items: center;
		min-height: 100upx;
		justify-content: space-between;
	}
</style>
