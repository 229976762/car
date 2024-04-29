<template>
    <el-dialog :title="form.announcementId ? '编辑' : '新增'" v-model="visible"
      :close-on-click-modal="false" draggable>
      <el-form ref="dataFormRef" :model="form" :rules="dataRules" formDialogRef label-width="60px" v-loading="loading">
       <el-row :gutter="24">
    <el-col :span="12" class="mb20">
      <el-form-item label="管理员Id" prop="userId">
        <el-input v-model="form.userId" placeholder="请输入管理员Id"/>
      </el-form-item>
      </el-col>

    <el-col :span="12" class="mb20">
      <el-form-item label="公告标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入公告标题"/>
      </el-form-item>
      </el-col>

    <el-col :span="12" class="mb20">
      <el-form-item label="公告内容" prop="content">
        <el-input v-model="form.content" placeholder="请输入公告内容"/>
      </el-form-item>
      </el-col>

    <!-- <el-col :span="12" class="mb20">
      <el-form-item label="发布时间" prop="publishTime">
        <el-input v-model="form.publishTime" placeholder="请输入发布时间"/>
      </el-form-item>
      </el-col> -->

			</el-row>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="visible = false">取消</el-button>
          <el-button type="success" @click="onSubmit" :disabled="loading">发布</el-button>
        </span>
      </template>
    </el-dialog>
</template>

<script setup lang="ts" name="CarAnnouncementDialog">
import { useDict } from '/@/hooks/dict';
import { useMessage } from "/@/hooks/message";
import { getObj, addObj, putObj } from '/@/api/carAnnouncement/carAnnouncement'
import { rule } from '/@/utils/validate';
const emit = defineEmits(['refresh']);

// 定义变量内容
const dataFormRef = ref();
const visible = ref(false)
const loading = ref(false)
// 定义字典

// 提交表单数据
const form = reactive({
		announcementId:'',
	  userId: '',
	  title: '',
	  content: '',
	  publishTime: '',
});

// 定义校验规则
const dataRules = ref({
        userId: [{required: true, message: '管理员Id不能为空', trigger: 'blur'}],
        title: [{required: true, message: '公告标题不能为空', trigger: 'blur'}],
        content: [{required: true, message: '公告内容不能为空', trigger: 'blur'}],
        publishTime: [{required: true, message: '发布时间不能为空', trigger: 'blur'}],
})

// 打开弹窗
const openDialog = (id: string) => {
  visible.value = true
  form.announcementId = ''

  // 重置表单数据
	nextTick(() => {
		dataFormRef.value?.resetFields();
	});

  // 获取carAnnouncement信息
  if (id) {
    form.announcementId = id
    getcarAnnouncementData(id)
  }
};

// 提交
const onSubmit = async () => {
	const valid = await dataFormRef.value.validate().catch(() => {});
	if (!valid) return false;

	try {
    loading.value = true;
		form.announcementId ? await putObj(form) : await addObj(form);
		useMessage().success(form.announcementId ? '修改成功' : '添加成功');
		visible.value = false;
		emit('refresh');
	} catch (err: any) {
		useMessage().error(err.msg);
	} finally {
    loading.value = false;
  }
};


// 初始化表单数据
const getcarAnnouncementData = (id: string) => {
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