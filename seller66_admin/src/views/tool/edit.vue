<template>
  <div class="app-container">
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="120px"
      class="form-container"
    >
      <el-form-item label="工具名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入工具名称" />
      </el-form-item>

      <el-form-item label="工具Logo" prop="image">
        <el-input v-model="form.image" placeholder="请输入Logo URL" />
        <div class="form-preview" v-if="form.image">
          <img :src="form.image" alt="Logo预览" style="max-height: 60px; max-width: 100%;" />
        </div>
      </el-form-item>

      <el-form-item label="价格" prop="price">
        <el-input v-model="form.price" placeholder="请输入价格信息，如: $39/月起" />
      </el-form-item>

      <el-form-item label="官网" prop="website">
        <el-input v-model="form.website" placeholder="请输入官网URL" />
      </el-form-item>

      <el-form-item label="评论链接" prop="reviewLink">
        <el-input v-model="form.reviewLink" placeholder="请输入评论页面链接" />
      </el-form-item>

      <el-form-item label="评分" prop="rating">
        <el-rate
          v-model="form.rating"
          show-score
          text-color="#ff9900"
          score-template="{value}"
        />
      </el-form-item>

      <el-form-item label="工具描述" prop="description">
        <el-input
          v-model="form.description"
          type="textarea"
          :rows="4"
          placeholder="请输入工具描述"
        />
      </el-form-item>

      <el-form-item label="功能特点" prop="features">
        <div v-for="(feature, index) in form.features" :key="index" class="feature-item">
          <el-input v-model="form.features[index]" placeholder="请输入功能特点">
            <template #append>
              <el-button @click="removeFeature(index)" icon="Delete" />
            </template>
          </el-input>
        </div>
        <el-button type="primary" plain @click="addFeature" icon="Plus" style="width: 100%; margin-top: 10px;">
          添加功能特点
        </el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm" :loading="loading">
          {{ isEdit ? '更新' : '创建' }}
        </el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { ref, reactive, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import ApiFactory from '@/api';

export default {
  name: 'ToolEdit',
  setup() {
    const route = useRoute();
    const router = useRouter();
    const formRef = ref(null);
    const loading = ref(false);

    // 判断是新建还是编辑
    const isEdit = computed(() => route.params.id !== undefined);

    // 表单数据
    const form = reactive({
      name: '',
      image: '',
      price: '',
      website: '',
      reviewLink: '',
      rating: 4.5,
      description: '',
      features: ['']
    });

    // 表单验证规则
    const rules = {
      name: [
        { required: true, message: '请输入工具名称', trigger: 'blur' },
        { min: 2, max: 50, message: '长度在2到50个字符之间', trigger: 'blur' }
      ],
      image: [
        { required: true, message: '请输入Logo URL', trigger: 'blur' },
        { type: 'url', message: '请输入有效的URL', trigger: 'blur' }
      ],
      price: [
        { required: true, message: '请输入价格信息', trigger: 'blur' }
      ],
      website: [
        { required: true, message: '请输入官网URL', trigger: 'blur' },
        { type: 'url', message: '请输入有效的URL', trigger: 'blur' }
      ],
      description: [
        { required: true, message: '请输入工具描述', trigger: 'blur' },
        { min: 10, message: '描述不能少于10个字符', trigger: 'blur' }
      ]
    };

    // 添加功能特点
    const addFeature = () => {
      form.features.push('');
    };

    // 移除功能特点
    const removeFeature = (index) => {
      if (form.features.length > 1) {
        form.features.splice(index, 1);
      }
    };

    // 获取工具详情
    const getToolDetail = async (id) => {
      loading.value = true;
      try {
        const toolApi = ApiFactory.getToolApi();
        const response = await toolApi.getTool(id);
        
        if (response.isSuccess()) {
          // 填充表单数据
          const toolData = response.data;
          form.name = toolData.name;
          form.image = toolData.image;
          form.price = toolData.price;
          form.website = toolData.website;
          form.reviewLink = toolData.reviewLink;
          form.rating = toolData.rating;
          form.description = toolData.description;
          
          // 处理功能特点数组
          if (Array.isArray(toolData.features) && toolData.features.length > 0) {
            form.features = [...toolData.features];
          }
        } else {
          ElMessage({
            message: response.message || '获取工具详情失败',
            type: 'error',
            duration: 5000
          });
        }
      } catch (error) {
        console.error('获取工具详情出错:', error);
        ElMessage({
          message: '获取工具详情出错，请稍后重试',
          type: 'error',
          duration: 5000
        });
      } finally {
        loading.value = false;
      }
    };

    // 提交表单
    const submitForm = async () => {
      if (!formRef.value) return;

      await formRef.value.validate(async (valid) => {
        if (valid) {
          loading.value = true;
          try {
            // 过滤空的功能特点
            const filteredFeatures = form.features.filter(feature => feature.trim() !== '');
            if (filteredFeatures.length === 0) {
              filteredFeatures.push('暂无详细功能说明');
            }
            
            const toolData = {
              ...form,
              features: filteredFeatures
            };
            
            const toolApi = ApiFactory.getToolApi();
            let response;
            
            if (isEdit.value) {
              // 更新工具
              response = await toolApi.updateTool(route.params.id, toolData);
            } else {
              // 创建工具
              response = await toolApi.createTool(toolData);
            }
            
            if (response.isSuccess()) {
              ElMessage({
                message: isEdit.value ? '工具更新成功' : '工具创建成功',
                type: 'success',
                duration: 2000
              });
              
              // 返回列表页
              router.push('/tool/list');
            } else {
              ElMessage({
                message: response.message || (isEdit.value ? '工具更新失败' : '工具创建失败'),
                type: 'error',
                duration: 5000
              });
            }
          } catch (error) {
            console.error(isEdit.value ? '更新工具出错:' : '创建工具出错:', error);
            ElMessage({
              message: isEdit.value ? '更新工具出错，请稍后重试' : '创建工具出错，请稍后重试',
              type: 'error',
              duration: 5000
            });
          } finally {
            loading.value = false;
          }
        }
      });
    };

    // 取消操作
    const cancel = () => {
      router.push('/tool/list');
    };

    onMounted(() => {
      if (isEdit.value) {
        getToolDetail(route.params.id);
      }
    });

    return {
      formRef,
      form,
      rules,
      loading,
      isEdit,
      addFeature,
      removeFeature,
      submitForm,
      cancel
    };
  }
};
</script>

<style lang="scss" scoped>
.form-container {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.feature-item {
  margin-bottom: 10px;
}

.form-preview {
  margin-top: 10px;
  padding: 10px;
  border: 1px dashed #dcdfe6;
  border-radius: 4px;
  display: flex;
  justify-content: center;
}
</style> 