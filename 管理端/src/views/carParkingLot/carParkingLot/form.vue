<template>
    <el-dialog :title="form.lotId ? '编辑' : '新增'" v-model="visible"
      :close-on-click-modal="false" draggable>
      <el-form ref="dataFormRef" :model="form" :rules="dataRules" formDialogRef label-width="90px" v-loading="loading">
       <el-row :gutter="24">
    <el-col :span="24" class="mb20">
      <el-form-item label="路段名称" prop="lotName">
        <el-input v-model="form.lotName" placeholder="请输入路段名称"/>
      </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="路段x位置" prop="x">
        <el-input v-model="form.x" placeholder="请输入路段x位置"/>
      </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="路段Y位置" prop="y">
        <el-input v-model="form.y" placeholder="请输入路段Y位置"/>
      </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="路段容量" prop="capacity">
        <el-input v-model="form.capacity" placeholder="请输入路段容量"/>
      </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="状态" prop="state">
        <!-- <el-input v-model="form.state" placeholder="请输入状态（0正常 1停用）"/> -->
        <el-radio-group v-model="form.state">
          <el-radio :label="0">正常</el-radio>
          <el-radio :label="1">停用</el-radio>
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

<script setup lang="ts" name="CarParkingLotDialog">
import { useDict } from '/@/hooks/dict';
import { useMessage } from "/@/hooks/message";
import { getObj, addObj, putObj } from '/@/api/carParkingLot/carParkingLot'
import { rule } from '/@/utils/validate';
const emit = defineEmits(['refresh']);

// 定义变量内容
const dataFormRef = ref();
const visible = ref(false)
const loading = ref(false)
// 定义字典

// 提交表单数据
const form = reactive({
		lotId:'',
	  lotName: '',
	  x: '',
	  y: '',
	  capacity: '',
	  state: '',
});

// 定义校验规则
const dataRules = ref({
        lotName: [{required: true, message: '路段名称不能为空', trigger: 'blur'}],
        x: [{required: true, message: '路段x位置不能为空', trigger: 'blur'}],
        y: [{required: true, message: '路段Y位置不能为空', trigger: 'blur'}],
        capacity: [{required: true, message: '路段容量不能为空', trigger: 'blur'}],
        state: [{required: true, message: '状态（0正常 1停用）不能为空', trigger: 'blur'}],
})

// 打开弹窗
const openDialog = (id: string) => {
  visible.value = true
  form.lotId = ''

  // 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

  // 获取carParkingLot信息
  if (id) {
    form.lotId = id
    getcarParkingLotData(id)
  }
};

// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
    loading.value = true;
		form.lotId ? await putObj(form) : await addObj(form);
		useMessage().success(form.lotId ? '修改成功' : '添加成功');
		visible.value = false;
		emit('refresh');
	} catch (err: any) {
		useMessage().error(err.msg);
	} finally {
    loading.value = false;
  }
};


// 初始化表单数据
const getcarParkingLotData = (id: string) => {
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