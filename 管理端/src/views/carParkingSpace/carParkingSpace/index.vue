<template>
  <div class="layout-padding">
    <div class="layout-padding-auto layout-padding-view">
      <el-row>
        <div class="mb8" style="width: 100%">
          <el-row v-show="showSearch">
						<el-form ref="queryRef" :inline="true" :model="state.queryForm" @keyup.enter="getDataList">
							<el-form-item :label="$t('carParkingSpace.lotId')" prop="lotId">
								<el-input v-model="state.queryForm.lotId" :placeholder="$t('carParkingSpace.lotId')" clearable />
							</el-form-item>
              <el-form-item :label="$t('carParkingSpace.spaceNumber')" prop="spaceNumber">
								<el-input v-model="state.queryForm.spaceNumber" :placeholder="$t('carParkingSpace.spaceNumber')" clearable />
							</el-form-item>
              <el-form-item label="车位状态" prop="status">
                <el-select v-model="state.queryForm.status" placeholder="车位状态" clearable>
                  <el-option label="空闲" value="0"></el-option>
                  <el-option label="已预订" value="1"></el-option>
                  <el-option label="已停车" value="2"></el-option>
                </el-select>
              </el-form-item>

							<el-form-item>
								<el-button icon="Search" type="primary" @click="getDataList">{{ $t('common.queryBtn') }}</el-button>
								<el-button icon="Refresh" @click="resetQuery">{{ $t('common.resetBtn') }}</el-button>
							</el-form-item>
						</el-form>
					</el-row>
          <el-button icon="folder-add" type="primary" class="ml10" @click="formDialogRef.openDialog()"
            v-auth="'carParkingSpace_carParkingSpace_add'">
            新 增
          </el-button>
          <el-button plain :disabled="multiple" icon="Delete" type="primary"
            v-auth="'carParkingSpace_carParkingSpace_del'" @click="handleDelete(selectObjs)">
            删除
          </el-button>
          <right-toolbar v-model:showSearch="showSearch" :export="'carParkingSpace_carParkingSpace_export'"
                @exportExcel="exportExcel" class="ml10 mr20" style="float: right;"
            @queryTable="getDataList"></right-toolbar>
        </div>
      </el-row>
      <el-table :data="state.dataList" v-loading="state.loading" border 
        :cell-style="tableStyle.cellStyle" :header-cell-style="tableStyle.headerCellStyle"
				@selection-change="selectionChangHandle"
        @sort-change="sortChangeHandle">
        <el-table-column type="selection" width="40" align="center" />
        <el-table-column type="index" label="#" width="40" />
          <el-table-column prop="lotId" label="所属路段ID"  show-overflow-tooltip/>
          <el-table-column prop="spaceNumber" label="车位编号"  show-overflow-tooltip/>
          <el-table-column prop="status" label="车位状态"  show-overflow-tooltip/>
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button icon="edit-pen" text type="primary" v-auth="'carParkingSpace_carParkingSpace_edit'"
              @click="formDialogRef.openDialog(scope.row.spaceId)">编辑</el-button>
            <el-button icon="delete" text type="primary" v-auth="'carParkingSpace_carParkingSpace_del'" @click="handleDelete([scope.row.spaceId])">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" v-bind="state.pagination" />
    </div>

    <!-- 编辑、新增  -->
    <form-dialog ref="formDialogRef" @refresh="getDataList(false)" />

  </div>
</template>

<script setup lang="ts" name="systemCarParkingSpace">
import { BasicTableProps, useTable } from "/@/hooks/table";
import { fetchList, delObjs } from "/@/api/carParkingSpace/carParkingSpace";
import { useMessage, useMessageBox } from "/@/hooks/message";
import { useDict } from '/@/hooks/dict';

// 引入组件
const FormDialog = defineAsyncComponent(() => import('./form.vue'));
// 定义查询字典

// 定义变量内容
const formDialogRef = ref()
// 搜索变量
const queryRef = ref()
const showSearch = ref(true)
// 多选变量
const selectObjs = ref([]) as any
const multiple = ref(true)

const state: BasicTableProps = reactive<BasicTableProps>({
  queryForm: {
    lotId: null,
    spaceNumber: null,
    status: null
  },
  pageList: fetchList
})

//  table hook
const {
  getDataList,
  currentChangeHandle,
  sizeChangeHandle,
  sortChangeHandle,
  downBlobFile,
	tableStyle
} = useTable(state)

// 清空搜索条件
const resetQuery = () => {
  // 清空搜索条件
  queryRef.value?.resetFields()
  // 清空多选
  selectObjs.value = []
  getDataList()
}

// 导出excel
const exportExcel = () => {
  downBlobFile('/carParkingSpace/carParkingSpace/export',Object.assign(state.queryForm, { ids: selectObjs }), 'carParkingSpace.xlsx')
}

// 多选事件
const selectionChangHandle = (objs: { spaceId: string }[]) => {
  selectObjs.value = objs.map(({ spaceId }) => spaceId);
  multiple.value = !objs.length;
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
</script>