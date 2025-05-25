<template>
  <div class="app-container seller-tools-edit">
    <el-card shadow="never" class="form-card">
      <el-form :model="form" ref="formRef" label-width="120px" :rules="rules">
        <!-- 基础信息 -->
        <el-divider content-position="left">基础信息</el-divider>
        <el-row :gutter="32">
          <el-col :span="12">
            <el-form-item label="产品名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入产品名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品图片" prop="image">
              <el-upload
                class="avatar-uploader"
                :auto-upload="false"
                :show-file-list="false"
                :on-change="(file) => beforeImageUpload(file.raw) && handleUpload(file.raw)"
              >
                <img v-if="form.image" :src="form.image" class="tool-img" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="32">
          <el-col :span="12">
            <el-form-item label="简短描述" prop="short_description">
              <el-input v-model="form.short_description" type="textarea" :rows="2" placeholder="介绍产品" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择类型">
                <el-option label="amazon-seller-tools" value="amazon-seller-tools" />
                <el-option label="eaby-seller-tools" value="eaby-seller-tools" />

              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 评分单独一行 -->
        <el-row :gutter="32">
          <el-col :span="24">
            <el-form-item label="评分" prop="rating">
              <el-rate
                v-model="form.rating"
                :max="10"
                allow-half
                show-score
                :score-template="`${form.rating ? form.rating.toFixed(1) : '0.0'} 分`"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 价格与排名 -->
        <el-divider content-position="left">价格与排名</el-divider>
        <el-row :gutter="32">
          <el-col :span="6">
            <el-form-item label="最低价格" prop="price">
              <el-input-number v-model="form.price" :min="0" :step="0.01" controls-position="right" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="评论数" prop="purchase_count">
              <el-input-number v-model="form.purchaseCount" :min="0" controls-position="right" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="免费天数" prop="free_trial_days">
              <el-input-number v-model="form.freeTrialDays" :min="0" controls-position="right" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="排名" prop="rank">
              <el-input-number v-model="form.rank" :min="1" controls-position="right" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="32">
          <el-col :span="9">
            <el-form-item label="创建时间" prop="createTime">
              <el-date-picker v-model="form.createTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width:100%;" />
            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label="价格描述" prop="detailedPricing">
              <el-input v-model="form.detailedPricing" placeholder="如：49美元/月起" />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 链接信息 -->
        <el-divider content-position="left">链接信息</el-divider>
        <el-row :gutter="32">
          <el-col :span="8">
            <el-form-item label="免费试用链接" prop="freeTrialLink">
              <el-input v-model="form.freeTrialLink" placeholder="https://..." />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="官网地址" prop="website">
              <el-input v-model="form.website" placeholder="https://..." />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="文章地址" prop="reviewLink">
              <el-input v-model="form.reviewLink" placeholder="https://..." />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 产品要点与详细特征 -->
        <el-divider content-position="left">产品要点与详细特征</el-divider>
        <el-row :gutter="32">
          <el-col :span="12">
            <el-form-item label="产品要点" prop="highlights">
              <div v-for="(item, idx) in highlightsArr" :key="idx" class="dynamic-input">
                <el-input v-model="highlightsArr[idx]" placeholder="请输入产品要点" style="width:80%;" />
                <el-button icon="Delete" @click="highlightsArr.splice(idx,1)" v-if="highlightsArr.length>1" circle />
              </div>
              <el-button @click="highlightsArr.push('')" type="primary" size="small" plain>添加要点</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="详细特征" prop="detailedFeatures">
              <div v-for="(item, idx) in detailedFeaturesArr" :key="idx" class="dynamic-input">
                <el-input v-model="detailedFeaturesArr[idx]" placeholder="请输入详细特征" style="width:80%;" />
                <el-button icon="Delete" @click="detailedFeaturesArr.splice(idx,1)" v-if="detailedFeaturesArr.length>1" circle />
              </div>
              <el-button @click="detailedFeaturesArr.push('')" type="primary" size="small" plain>添加特征</el-button>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item style="margin-top:32px;">
          <el-button type="primary" @click="submitForm" size="large" round>保存</el-button>
          <el-button @click="cancel" size="large" round>取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { Plus } from '@element-plus/icons-vue';
import ApiFactory from '@/api';

const route = useRoute();
const router = useRouter();
const formRef = ref(null);
const form = reactive({
  name: '',
  logo: '',
  description: '',
  features: '',
  createTime: '',
  updateTime: '',
  freeTrialLink: '',
  detailedPricing: '',
  website: '',
  review_link: '',
  shortDescription: '',
  highlights: '',
  detailedFeatures: '',
  rating: 0,
  price: 0,
  purchaseCount: 0,
  freeTrialDays: 0,
  rank: 0,
  type: '',
  image: '',
  // 其他字段...
});
const highlightsArr = ref(['']);
const detailedFeaturesArr = ref(['']);

const beforeImageUpload = (file) => {
  // 校验图片类型和大小
  return true;
};
const handleUpload = async (file) => {
  const uploadApi = ApiFactory.getUploadApi();
  const res = await uploadApi.uploadImage(file);
  if (res.isSuccess()) {
    form.image = res.data.url;
    ElMessage.success('上传成功');
  }
};
const getDetail = async (id) => {
  const sellerToolsApi = ApiFactory.getSellerToolsApi();
  const res = await sellerToolsApi.getDetail(id);
  if (res.isSuccess()) {
    Object.assign(form, res.data);
    highlightsArr.value = form.highlights
      ? form.highlights.split(',').map(i => i.replace(/^"+|"+$/g, '').trim())
      : [''];
    detailedFeaturesArr.value = form.detailedFeatures
      ? form.detailedFeatures.split(',').map(i => i.replace(/^"+|"+$/g, '').trim())
      : [''];
  }
};
const submitForm = async () => {
  form.highlights = highlightsArr.value.filter(i => i.trim() !== '').join(',');
  form.detailedFeatures = detailedFeaturesArr.value.filter(i => i.trim() !== '').join(',');
  await formRef.value.validate();
  const api = ApiFactory.getSellerToolsApi();
  let res;
  if (route.params.id) {
    res = await api.update(form);
  } else {
    res = await api.create(form);
  }
  if (res.isSuccess()) {
    ElMessage.success('保存成功');
    router.push('/sellerTools/list');
  }
};
const cancel = () => router.push('/sellerTools/list');

onMounted(() => {
  if (route.params.id) {
    getDetail(route.params.id);
  } else {
    highlightsArr.value = form.highlights
      ? form.highlights.split(',').map(i => i.replace(/^"+|"+$/g, '').trim())
      : [''];
    detailedFeaturesArr.value = form.detailedFeatures
      ? form.detailedFeatures.split(',').map(i => i.replace(/^"+|"+$/g, '').trim())
      : [''];
  }
});
</script>

<style scoped>
.seller-tools-edit {
  max-width: 1000px;
  margin: 0 auto;
  padding: 32px 0;
}
.form-card {
  padding: 32px 24px;
  border-radius: 12px;
  box-shadow: 0 4px 24px #f0f1f2;
  background: #fff;
}
.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 8px;
  cursor: pointer;
  transition: border-color 0.3s;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.avatar-uploader:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}
.tool-img {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  object-fit: cover;
  box-shadow: 0 2px 8px #eee;
}
.dynamic-input {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}
.el-rate {
  margin-right: 8px;
}
</style> 