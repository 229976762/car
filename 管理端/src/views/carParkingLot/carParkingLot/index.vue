<template>
  <div class="layout-padding" style="height: 100%; width: 100%;">
    <div class="layout-padding-auto layout-padding-view" style="display: flex; flex-direction: row; height: 100%; width: 100%;">
      <!-- 左半区域：表格 -->
      <div class="table-container" style="flex: 1; margin-right: 5px;">

        <el-row v-show="showSearch">
						<el-form ref="queryRef" :inline="true" :model="state.queryForm" @keyup.enter="getDataList">
							<el-form-item :label="$t('carParkingLot.lotName')" prop="lotName">
								<el-input v-model="state.queryForm.lotName" :placeholder="$t('carParkingLot.lotName')" clearable />
							</el-form-item>
							<el-form-item>
								<el-button icon="Search" type="primary" @click="getDataList">{{ $t('common.queryBtn') }}</el-button>
								<el-button icon="Refresh" @click="resetQuery">{{ $t('common.resetBtn') }}</el-button>
							</el-form-item>
						</el-form>
					</el-row>
        <el-row>
          <div class="mb8" style="width: 100%">
            <el-button icon="folder-add" type="primary" class="ml10" @click="formDialogRef.openDialog()" v-auth="'carParkingLot_carParkingLot_add'">
              新 增
            </el-button>
            <el-button plain :disabled="multiple" icon="Delete" type="primary" v-auth="'carParkingLot_carParkingLot_del'" @click="handleDelete(selectObjs)">
              删除
            </el-button>
            <right-toolbar v-model:showSearch="showSearch" :export="'carParkingLot_carParkingLot_export'" @exportExcel="exportExcel" class="ml10 mr20" style="float: right;" @queryTable="getDataList"></right-toolbar>
          </div>
        </el-row>
        <el-table :data="state.dataList" v-loading="state.loading" border :cell-style="tableStyle.cellStyle"
         :header-cell-style="tableStyle.headerCellStyle" 
         @selection-change="selectionChangHandle" 
         @sort-change="sortChangeHandle" 
         @row-click="handleRowClick"
         >
          <el-table-column type="selection" width="30" align="center" />
          <el-table-column type="index" label="#" width="30" />
          <el-table-column prop="lotId" label="路段编号" show-overflow-tooltip/>
          <el-table-column prop="lotName" label="路段名称" show-overflow-tooltip/>
          <el-table-column prop="x" label="路段x位置" show-overflow-tooltip/>
          <el-table-column prop="y" label="路段Y位置" show-overflow-tooltip/>
          <el-table-column prop="capacity" label="容量" show-overflow-tooltip width="50"/>
          <!-- <el-table-column prop="state" label="状态" show-overflow-tooltip/> -->
          <el-table-column prop="state" label="状态" show-overflow-tooltip>
            <!-- 根据 state 的值动态显示不同内容 -->
            <template #default="{ row }">
              <el-tag size="medium" :type="row.state === 0 ? 'success' : 'danger'">
                {{ row.state === 0 ? '正常' : '停用' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="selectPoint" label="操作" width="130">
            <template #default="scope">
              <el-button icon="edit-pen" text type="primary" v-auth="'carParkingLot_carParkingLot_edit'" @click="formDialogRef.openDialog(scope.row.lotId)">编辑</el-button>
              <el-button icon="delete" text type="primary" v-auth="'carParkingLot_carParkingLot_del'" @click="handleDelete([scope.row.lotId])">删除</el-button>
            </template>
          </el-table-column>
          <el-table-column label="选点" prop="selectPoint" width="70">
            <template #default="scope">
              <el-button icon="map-location" text type="warning" @click="myAddress(scope.row)">选点</el-button>
            </template>
          </el-table-column>

        </el-table>
        <pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" v-bind="state.pagination" />
      </div>
      <!-- 右半区域：地图组件 -->
      <div class="map-container" style="height: 100%; width: 100%;">

        <!-- <el-input style="width: 200px" placeholder="输入较完整的地址" v-model="address"></el-input> -->
        <el-form inline  size="mini">
            <el-form-item class="mb-0" label="搜索地点:">
              <el-row>
                <div style="margin-right: 5px;">
                  <el-input style="width: 200px" placeholder="输入较完整的地址" v-model="address.text"></el-input>
                </div>
                <div>
                  <el-button  type="primary" @click="search"  icon="search">搜索</el-button>
                </div>
              </el-row>
            </el-form-item>
        </el-form>
        <div v-if="address.resultText !== ''" style="color: blue;">
          {{address.resultText}}
        </div>
        <Amap style="margin-top: 10px;" ref="amapRef" @position-clicked="handlePositionClicked" />
      </div>
    </div>
    <!-- 编辑、新增表单 -->
    <form-dialog ref="formDialogRef" @refresh="getDataList(false)" />
  </div>
</template>

<script setup lang="ts">
import { defineAsyncComponent, ref, reactive } from 'vue';
import { BasicTableProps, useTable } from "/@/hooks/table";
import { pageList, delObjs } from "/@/api/carParkingLot/carParkingLot";
import { useMessage, useMessageBox } from "/@/hooks/message";
import axios from "axios";
import { putObj } from '/@/api/carParkingLot/carParkingLot'
const emit = defineEmits(['refresh']);

// 引入组件
const FormDialog = defineAsyncComponent(() => import('./form.vue'));
const Amap = defineAsyncComponent(() => import('./amap.vue'));

// 定义变量内容
const formDialogRef = ref()
const amapRef = ref();
const address = reactive({
  resultText: '',
  text: ''
});
// 搜索变量
const queryRef = ref()
const showSearch = ref(true)
// 多选变量
const selectObjs = ref([]) as any
const multiple = ref(true)

const state: BasicTableProps = reactive<BasicTableProps>({
  queryForm: {
    lotName: ''
  },
  pageList: pageList
})



// 处理接收到的数据
const handlePositionClicked = (data: any) => {
  console.log('Received data from child:', data);
  // 进行进一步处理
  if (addressData.value) {
    addressData.value.x = data.lng
    addressData.value.y = data.lat
    onSubmit()
  }
    
}

// 清空搜索条件
const resetQuery = () => {
	queryRef.value?.resetFields();
	state.queryForm.lotName = '';
	getDataList();
};

const onSubmit = async () => {
	try {
		await putObj(addressData.value)
		useMessage().success('选点成功');
    emit('refresh');
    addressData.value = null;
	} catch (err: any) {
	} finally {
  }
};


//  table hook
const {
  getDataList,
  currentChangeHandle,
  sizeChangeHandle,
  sortChangeHandle,
  downBlobFile,
  tableStyle
} = useTable(state)

//搜索地点
const search = () => {
    const url = 'https://restapi.amap.com/v5/place/text'
    axios({
        url,
        method: 'get',
        params: {
            key: "d3460720729a83b4a1ffaf4587983b70",
            keywords: address.text,
            
        }
    })
      .then(response => {
          console.log(response)
            let res = response.data
            let geoLocation = res.pois[0].location
            let locationInfo = res.pois[0]
            let locationArray = geoLocation.split(',')

            address.resultText = locationInfo.cityname+locationInfo.adname + locationInfo.address;

            var row = {
                 x: Number(locationArray[0]),
                 y: Number(locationArray[1]),
                lotName: address.text
            }
            amapRef.value.AmapIndex(row);
        })
}


// 导出excel
const exportExcel = () => {
  downBlobFile('/carParkingLot/carParkingLot/export', Object.assign(state.queryForm, { ids: selectObjs }), 'carParkingLot.xlsx')
}

// 多选事件
const selectionChangHandle = (objs: { lotId: string }[]) => {
  selectObjs.value = objs.map(({ lotId }) => lotId);
  multiple.value = !objs.length;
};

// //点击表格
const handleRowClick = (row: any, column: any) => {
  if (column.property === 'selectPoint') 
        return;
      
  if (amapRef.value) {
    amapRef.value.AmapIndex(row);
  }
};

// 删除操作
const handleDelete = async (ids: string[]) => {
  try {
    await useMessageBox().confirm('此操作将永久删除');
  } catch {
    return;
  }

  try {
    await delObjs(ids);
    getDataList();
    useMessage().success('删除成功');
  } catch (err: any) {
    useMessage().error(err.msg);
  }
};

const addressData = ref();
const myAddress = (row:any) => {
  // 设置选中状态为true
  addressData.value = row;
};
</script>

<style scoped>
.layout-padding-auto {
  margin: 0 auto;
}

</style>
