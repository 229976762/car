<template>
    <el-dialog :title="form.spaceId ? '编辑' : '新增'" v-model="visible"
      :close-on-click-modal="false" draggable>
      <el-form ref="dataFormRef" :model="form" :rules="dataRules" formDialogRef label-width="90px" v-loading="loading">
       <el-row :gutter="24">
    <el-col :span="24" class="mb20">
      <el-form-item label="所属路段ID" prop="lotId">
        <el-input v-model="form.lotId" placeholder="请输入车位编号"/>
        </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="车位编号" prop="spaceNumber">
        <el-input v-model="form.spaceNumber" placeholder="请输入车位编号"/>
      </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="车位状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">空闲</el-radio>
          <el-radio :label="1">已预订</el-radio>
          <el-radio :label="2">已停车</el-radio>
        </el-radio-group>
        </el-form-item>
      </el-col>

			</el-row>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="visible = false">取消</el-button>
          <el-button type="primary" @click="onSubmit" :disabled="loading">确认</el-button>
        </span>
      </template>
    </el-dialog>
</template>

<script setup lang="ts" name="CarParkingSpaceDialog">
import { useDict } from '/@/hooks/dict';
import { useMessage } from "/@/hooks/message";
import { getObj, addObj, putObj } from '/@/api/carParkingSpace/carParkingSpace'
import { rule } from '/@/utils/validate';
const emit = defineEmits(['refresh']);

// 定义变量内容
const dataFormRef = ref();
const visible = ref(false)
const loading = ref(false)
// 定义字典

// 提交表单数据
const form = reactive({
		spaceId:'',
	  lotId: '',
	  spaceNumber: '',
	  status: '',
});

// 定义校验规则
const dataRules = ref({
        lotId: [{required: true, message: '所属路段ID不能为空', trigger: 'blur'}],
        spaceNumber: [{required: true, message: '车位编号不能为空', trigger: 'blur'}],
        status: [{required: true, message: '车位状态（0空闲、1已预订、2已停车）不能为空', trigger: 'blur'}],
})

// 打开弹窗
const openDialog = (id: string) => {
  visible.value = true
  form.spaceId = ''

  // 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

  // 获取carParkingSpace信息
  if (id) {
    form.spaceId = id
    getcarParkingSpaceData(id)
  }
};

// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
    loading.value = true;
		form.spaceId ? await putObj(form) : await addObj(form);
		useMessage().success(form.spaceId ? '修改成功' : '添加成功');
		visible.value = false;
		emit('refresh');
	} catch (err: any) {
		useMessage().error(err.msg);
	} finally {
    loading.value = false;
  }
};


// 初始化表单数据
const getcarParkingSpaceData = (id: string) => {
  // 获取数据
  loading.value = true
  getObj(id).then((res: any) => {
    Object.assign(form, res.data)
  }).finally(() => {
    loading.value = false
  })
};

// 暴露变量
defineExpose({
  openDialog
});
</script>