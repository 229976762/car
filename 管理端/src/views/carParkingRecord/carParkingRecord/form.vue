<template>
    <el-dialog :title="form.recordId ? '编辑' : '新增'" v-model="visible"
      :close-on-click-modal="false" draggable>
      <el-form ref="dataFormRef" :model="form" :rules="dataRules" formDialogRef label-width="90px" v-loading="loading">
       <el-row :gutter="24">
    <el-col :span="24" class="mb20">
      <el-form-item label="用户ID" prop="userId">
        <el-input v-model="form.userId" placeholder="请输入用户ID"/>
      </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="车位ID" prop="spaceId">
        <el-input v-model="form.spaceId" placeholder="请输入车位ID"/>
      </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="开始时间" prop="startTime">
            <el-date-picker type="datetime" placeholder="请选择停车开始时间" v-model="form.startTime" :value-format="dateTimeStr"></el-date-picker>
      </el-form-item>
      </el-col>


    <el-col :span="24" class="mb20">
      <el-form-item label="结束时间" prop="endTime">
            <el-date-picker type="datetime" placeholder="请选择停车结束时间" v-model="form.endTime" :value-format="dateTimeStr"></el-date-picker>
      </el-form-item>
      </el-col>


    <el-col :span="24" class="mb20">
      <el-form-item label="记录状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :label="0" border>停车中</el-radio>
          <el-radio :label="1" border>已完成</el-radio>
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

<script setup lang="ts" name="CarParkingRecordDialog">
import { useDict } from '/@/hooks/dict';
import { useMessage } from "/@/hooks/message";
import { getObj, addObj, putObj } from '/@/api/carParkingRecord/carParkingRecord'
import { rule } from '/@/utils/validate';
const emit = defineEmits(['refresh']);

// 定义变量内容
const dataFormRef = ref();
const visible = ref(false)
const loading = ref(false)
// 定义字典

// 提交表单数据
const form = reactive({
		recordId:'',
	  userId: '',
	  spaceId: '',
	  startTime: '',
	  endTime: '',
	  status: '',
});

// 定义校验规则
const dataRules = ref({
        userId: [{required: true, message: '用户ID不能为空', trigger: 'blur'}],
        spaceId: [{required: true, message: '车位ID不能为空', trigger: 'blur'}],
        startTime: [{required: true, message: '停车开始时间不能为空', trigger: 'blur'}],
        endTime: [{required: true, message: '停车结束时间不能为空', trigger: 'blur'}],
        status: [{required: true, message: '记录状态（停车中、已完成）不能为空', trigger: 'blur'}],
})

// 打开弹窗
const openDialog = (id: string) => {
  visible.value = true
  form.recordId = ''

  // 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

  // 获取carParkingRecord信息
  if (id) {
    form.recordId = id
    getcarParkingRecordData(id)
  }
};

// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
    loading.value = true;
		form.recordId ? await putObj(form) : await addObj(form);
		useMessage().success(form.recordId ? '修改成功' : '添加成功');
		visible.value = false;
		emit('refresh');
	} catch (err: any) {
		useMessage().error(err.msg);
	} finally {
    loading.value = false;
  }
};


// 初始化表单数据
const getcarParkingRecordData = (id: string) => {
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