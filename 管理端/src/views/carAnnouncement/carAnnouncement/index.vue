<template>
  <div class="layout-padding">
    <div class="layout-padding-auto layout-padding-view">
      <el-row>
        <div class="mb8" style="width: 100%">
          <el-row v-show="showSearch">
						<el-form ref="queryRef" :inline="true" :model="state.queryForm" @keyup.enter="getDataList">
							<el-form-item :label="$t('carAnnouncement.title')" prop="title">
								<el-input v-model="state.queryForm.title" :placeholder="$t('carAnnouncement.title')" clearable />
							</el-form-item>
              <el-form-item :label="$t('carAnnouncement.content')" prop="content">
								<el-input v-model="state.queryForm.content" :placeholder="$t('carAnnouncement.content')" clearable />
							</el-form-item>
							<el-form-item>
								<el-button icon="Search" type="primary" @click="getDataList">{{ $t('common.queryBtn') }}</el-button>
								<el-button icon="Refresh" @click="resetQuery">{{ $t('common.resetBtn') }}</el-button>
							</el-form-item>
						</el-form>
					</el-row>
          <el-button icon="Share" type="success" class="ml10" @click="formDialogRef.openDialog()"
            v-auth="'carAnnouncement_carAnnouncement_add'">
            发 布
          </el-button>
          <el-button plain :disabled="multiple" icon="Delete" type="primary"
            v-auth="'carAnnouncement_carAnnouncement_del'" @click="handleDelete(selectObjs)">
            删 除
          </el-button>
          <right-toolbar v-model:showSearch="showSearch" :export="'carAnnouncement_carAnnouncement_export'"
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
          <el-table-column prop="userId" label="管理员Id"  show-overflow-tooltip/>
          <el-table-column prop="title" label="公告标题"  show-overflow-tooltip/>
          <el-table-column prop="content" label="公告内容"  show-overflow-tooltip/>
          <el-table-column prop="publishTime" label="发布时间"  show-overflow-tooltip/>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <!-- <el-button icon="Share" text type="success" v-auth="'carAnnouncement_carAnnouncement_del'" @click="handleShare([scope.row])">发布</el-button> -->
            <el-button icon="edit-pen" text type="primary" v-auth="'carAnnouncement_carAnnouncement_edit'"
              @click="formDialogRef.openDialog(scope.row.announcementId)">编辑</el-button>
            <el-button icon="delete" text type="danger" v-auth="'carAnnouncement_carAnnouncement_del'" @click="handleDelete([scope.row.announcementId])">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" v-bind="state.pagination" />
    </div>

    <!-- 编辑、新增  -->
    <form-dialog ref="formDialogRef" @refresh="getDataList(false)" />

  </div>
</template>

<script setup lang="ts" name="systemCarAnnouncement">
import { BasicTableProps, useTable } from "/@/hooks/table";
import { fetchList, delObjs } from "/@/api/carAnnouncement/carAnnouncement";
import { useMessage, useMessageBox } from "/@/hooks/message";

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
    title: '',
    content: ''
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
  downBlobFile('/carAnnouncement/carAnnouncement/export',Object.assign(state.queryForm, { ids: selectObjs }), 'carAnnouncement.xlsx')
}

// 多选事件
const selectionChangHandle = (objs: { announcementId: string }[]) => {
  selectObjs.value = objs.map(({ announcementId }) => announcementId);
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

// const handleShare = async (row: Object) => {


// }


</script>