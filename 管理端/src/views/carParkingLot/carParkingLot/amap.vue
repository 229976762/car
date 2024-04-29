<template>
  <div class="home_div">
      <div id="container" style="height: 50vh; width: 100%"></div>
  </div>
</template>

<script setup >
import { onMounted, onUnmounted } from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";
import { defineComponent, defineEmits} from 'vue';


let map = null;



// 结束拾取坐标
const pickLocationStop = () => {
    map.off('click', this.showLocation)
};

const addMarker = (maps, item) => {
    let marker = new AMap.Marker({
        icon: 'src/assets/icons/ip.png',
        position: item.position,
        content: `
        <div class="marker">
          <div class="marker-index">
              <div class="title">${item.name}</div>
          </div>
          <div class="marker-content">
                <div class="note">${item.note.replaceAll('|', '<br>')}</div>
          </div>
        </div>`,
    })
    maps.add(marker)
    map.setCenter(item.position, false, 1000)
}


// 在row-click事件中调用Amap组件的方法
const AmapIndex = (row) => {
  // 在这里实现你的逻辑
  console.log('Clicked on row:', row);

  // marker.setMap(map);
  // map.setFitView();
  addMarker(map, {
      position: [row.x, row.y],
      name: row.lotName,
      note: ''
  })
};


onMounted(() => {
    AMapLoader
            .load({
                key: "6c871689593b173d1ae05c269e97e51b", // 开发应用的 ID
                version: "2.0",   // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
                plugins: [
                    'AMap.ToolBar', // 缩放按钮
                    'AMap.Scale', // 比例尺
                    'AMap.Geolocation', // 定位
                ],
            })
            .then(maps => {
                map = new maps.Map('container', {
                    center: [117.129533, 36.685668],
                    zoom: 11
                })

                // this.map.addControl(new AMap.ToolBar())
                map.addControl(new AMap.Scale())
                map.addControl(new AMap.Geolocation())

                // 定位
                let geolocation = new AMap.Geolocation({
                    // 是否使用高精度定位，默认：true
                    enableHighAccuracy: true,
                    // 设置定位超时时间，默认：无穷大
                    timeout: 10000,
                    // 定位按钮的停靠位置的偏移量，默认：Pixel(10, 20)
                    buttonOffset: new AMap.Pixel(10, 20),
                    //  定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
                    zoomToAccuracy: true,
                    //  定位按钮的排放位置,  RB表示右下
                    buttonPosition: 'RB'
                })
                
                geolocation.getCurrentPosition(function (status, res) {
                  console.log(status)
			            if (status == 'complete') {
			                console.log(status)
                      let center = [res.position.lng, res.position.lat]

                      addMarker(map, {
                          position: center,
                          name: '我',
                          note: ''
                      })
			            } else {
			            }
                })

                map.addControl(geolocation)
              


                // 地图选点操作
                map.on('click', res => {
                    var positionPicked = {
                        lng: res.lnglat.lng,
                        lat: res.lnglat.lat
                    }

                    console.log(positionPicked)
                    emitPosition(positionPicked)

                })
            })
            .catch(e => {
                console.log(e)
            })

   

});

const emit = defineEmits(['position-clicked']);

const emitPosition = (positionPicked) => {
    // 触发自定义事件并传递数据
    emit('position-clicked', positionPicked);
};


onUnmounted(() => {
  map?.destroy();
});

// 暴露变量
defineExpose({
  AmapIndex
});



</script>


<style scope>
  .home_div{
      height: 100%;
      width: 100%;
      padding: 0px;
      margin: 0px;
      position: relative;
  }
</style>

