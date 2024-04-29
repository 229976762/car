<template>
    <el-dialog :title="form.orderId ? '编辑' : '新增'" v-model="visible"
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
      <el-form-item label="车牌号" prop="plateNumber">
        <el-input v-model="form.plateNumber" placeholder="请输入车牌号"/>
      </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="下单时间" prop="orderTime">
            <el-date-picker type="datetime" placeholder="请选择下单时间" v-model="form.orderTime" :value-format="dateTimeStr"></el-date-picker>
      </el-form-item>
      </el-col>


    <el-col :span="24" class="mb20">
      <el-form-item label="占用时间" prop="useTime">
          <el-input v-model="form.useTime" placeholder="请输入占用时间"/>
      </el-form-item>
      </el-col>


      <el-col :span="24" class="mb20">
      <el-form-item label="订单状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">待支付</el-radio>
          <el-radio :label="1">已支付</el-radio>
          <el-radio :label="2">已取消</el-radio>
        </el-radio-group>
        </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="支付时间" prop="paymentTime">
        <el-input v-model="form.paymentTime" placeholder="请输入支付时间"/>
      </el-form-item>
      </el-col>

    <el-col :span="24" class="mb20">
      <el-form-item label="停车费用" prop="fee">
        <el-input v-model="form.fee" placeholder="请输入停车费用"/>
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

<script setup lang="ts" name="CarOrderDialog">
import { useDict } from '/@/hooks/dict';
import { useMessage } from "/@/hooks/message";
import { getObj, addObj, putObj } from '/@/api/carOrder/carOrder'
import { rule } from '/@/utils/validate';
const emit = defineEmits(['refresh']);

// 定义变量内容
const dataFormRef = ref();
const visible = ref(false)
const loading = ref(false)
// 定义字典

// 提交表单数据
const form = reactive({
		orderId:'',
	  userId: '',
	  spaceId: '',
  orderTime: '',
  plateNumber: '',
	  useTime: '',
	  status: '',
	  paymentTime: '',
	  fee: '',
});

// 定义校验规则
const dataRules = ref({
        userId: [{required: true, message: '用户ID不能为空', trigger: 'blur'}],
        spaceId: [{required: true, message: '车位ID不能为空', trigger: 'blur'}],
        orderTime: [{required: true, message: '下单时间不能为空', trigger: 'blur'}],
        useTime: [{required: true, message: '占用时间不能为空', trigger: 'blur'}],
        status: [{required: true, message: '订单状态（0待支付、1已支付、2已取消）不能为空', trigger: 'blur'}],
        paymentTime: [{required: true, message: '支付时间不能为空', trigger: 'blur'}],
        fee: [{required: true, message: '停车费用不能为空', trigger: 'blur'}],
})

// 打开弹窗
const openDialog = (id: string) => {
  visible.value = true
  form.orderId = ''

  // 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

  // 获取carOrder信息
  if (id) {
    form.orderId = id
    getcarOrderData(id)
  }
};

// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
    loading.value = true;
		form.orderId ? await putObj(form) : await addObj(form);
		useMessage().success(form.orderId ? '修改成功' : '添加成功');
		visible.value = false;
		emit('refresh');
	} catch (err: any) {
		useMessage().error(err.msg);
	} finally {
    loading.value = false;
  }
};


// 初始化表单数据
const getcarOrderData = (id: string) => {
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