// import QQMapWX from "./qqmap-wx-jssdk.js";
import amapWX from '../libs/amap-wx.130.js'

/** 
 * 此方法为腾讯位置服务方法  日调用量1万次/key,并发数5次/key/秒
 * @param {Object} location
 * @param {Object} callback
 */
// function tencentGeocode(lat, lon, callback) {
// 	const qqmapsdk = new QQMapWX({
// 		key: "XIEBZ-H2F3L-ZPDPN-MPK2I-O7MXF-C7FNG" //自己申请的key
// 	});
// 	let location = {
// 		latitude: lat,
// 		longitude: lon
// 	}
// 	qqmapsdk.reverseGeocoder({
// 		location: location,
// 		success(addressRes) {
// 			let address = addressRes.result.address
// 			callback(address)
// 			console.log('腾讯->', address)
// 		},
// 		fail(res) {
// 			console.log('失败', res)
// 		}
// 	});
// }
/** 
 * 附近搜索
 * @param {Object} location
 * @param {Object} callback
 */
function getPoiAround(lat, lon,keywords,callback) {
	const amapPlugin = new amapWX.AMapWX({
	 key: "5bb820b2042587b51fcd1d1fbafe5ef8"
	});
	let location = '' + lon + ',' + lat + '';
	
	amapPlugin.getPoiAround({
	 location: location, //location的格式为'经度,纬度'，
	 querykeywords:keywords,
	 //成功回调
	 success: function(data) {
	  callback(data)
	  //console.log("附近->", data)
	 },
	 fail: function(info) {
	  //失败回调
	  console.log(info)
	  
	 }
	})
   
   }
/** 
 * 此方法为高德位置服务方法  
 * @param {Object} location
 * @param {Object} callback
 */
function amapGeocode(lat, lon, callback) {
	const amapPlugin = new amapWX.AMapWX({
		key: "5bb820b2042587b51fcd1d1fbafe5ef8"
	});
	let location = '' +  lon + ',' + lat + '';
	amapPlugin.getRegeo({
		location: location, //location的格式为'经度,纬度'
		//成功回调
		success: function(data) {
			callback(data)
			
		},
		fail: function(info) {
			//失败回调
			console.log(info)
			// 定位权限未开启，引导设置
			uni.showModal({
				title: '温馨提示',
				content: '您已拒绝定位,请开启',
				confirmText: '去设置',
				success(res) {
					if (res.confirm) {
						//打开授权设置
						that.openSetting()
					}
				}
			})
		}
	})

}

export default {
	// tencentGeocode,
	amapGeocode,
	getPoiAround
}
