<template>
    <el-dialog :title="form.ruleId ? '编辑' : '新增'" v-model="visible"
      :close-on-click-modal="false" draggable>
      <el-form ref="dataFormRef" :model="form" :rules="dataRules" formDialogRef label-width="90px" v-loading="loading">
       <el-row :gutter="24">
    <el-col :span="24" class="mb20">
      <el-form-item label="路段ID" prop="lotId">
        <el-input v-model="form.lotId" placeholder="请输入路段ID"/>
      </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="起始时间" prop="startTime">
            <el-date-picker type="datetime" placeholder="请选择起始时间" v-model="form.startTime" :value-format="dateTimeStr"></el-date-picker>
      </el-form-item>
      </el-col>


    <el-col :span="24" class="mb20">
      <el-form-item label="结束时间" prop="endTime">
            <el-date-picker type="datetime" placeholder="请选择结束时间" v-model="form.endTime" :value-format="dateTimeStr"></el-date-picker>
      </el-form-item>
      </el-col>


    <el-col :span="24" class="mb20">
      <el-form-item label="费率" prop="feeRate">
        <el-input v-model="form.feeRate" placeholder="请输入费率"/>
      </el-form-item>
      </el-col>

      <el-col :span="24" class="mb20">
      <el-form-item label="记录状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">正常</el-radio>
          <el-radio :label="1">废弃</el-radio>
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

<script setup lang="ts" name="CarFeeRuleDialog">
import { useDict } from '/@/hooks/dict';
import { useMessage } from "/@/hooks/message";
import { getObj, addObj, putObj } from '/@/api/carFeeRule/carFeeRule'
import { rule } from '/@/utils/validate';
const emit = defineEmits(['refresh']);

// 定义变量内容
const dataFormRef = ref();
const visible = ref(false)
const loading = ref(false)
// 定义字典

// 提交表单数据
const form = reactive({
		ruleId:'',
	  lotId: '',
	  startTime: '',
	  endTime: '',
	  feeRate: '',
	  status: '',
});

// 定义校验规则
const dataRules = ref({
        lotId: [{required: true, message: '路段ID不能为空', trigger: 'blur'}],
        startTime: [{required: true, message: '起始时间不能为空', trigger: 'blur'}],
        endTime: [{required: true, message: '结束时间不能为空', trigger: 'blur'}],
        feeRate: [{required: true, message: '费率不能为空', trigger: 'blur'}],
        status: [{required: true, message: '记录状态（正常、废弃）不能为空', trigger: 'blur'}],
})

// 打开弹窗
const openDialog = (id: string) => {
  visible.value = true
  form.ruleId = ''

  // 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

  // 获取carFeeRule信息
  if (id) {
    form.ruleId = id
    getcarFeeRuleData(id)
  }
};

// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
    loading.value = true;
		form.ruleId ? await putObj(form) : await addObj(form);
		useMessage().success(form.ruleId ? '修改成功' : '添加成功');
		visible.value = false;
		emit('refresh');
	} catch (err: any) {
		useMessage().error(err.msg);
	} finally {
    loading.value = false;
  }
};


// 初始化表单数据
const getcarFeeRuleData = (id: string) => {
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