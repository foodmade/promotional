<template>
  <div class="app-container">
    <el-form ref="articleForm" :model="articleForm" label-width="100px" class="form-container">
      <div class="form-header">
        <h3>{{ isEdit ? '编辑文章' : '创建文章' }}</h3>
      </div>
      
      <el-form-item prop="title" label="文章标题">
        <el-input v-model="articleForm.title" placeholder="请输入文章标题" />
      </el-form-item>
      
      <el-row>
        <el-col :span="12">
          <el-form-item label="作者" prop="author">
            <el-select
              v-model="articleForm.author"
              placeholder="请选择作者"
              style="width: 100%"
            >
              <el-option
                v-for="item in authorOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
                <div style="display: flex; align-items: center;">
                  <img
                    :src="item.avatar"
                    style="width: 30px; height: 30px; border-radius: 50%; margin-right: 10px;"
                  />
                  <span>{{ item.name }}</span>
                </div>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发布时间" prop="timestamp">
            <el-date-picker
              v-model="articleForm.createTime"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="选择日期时间"
              style="width: 100%;"
            />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-form-item label="文章路径" prop="slug">
        <el-input v-model="articleForm.slug" placeholder="请输入路径 例如：smartscout-brand-hunter-guide-2025" />
      </el-form-item>
      
      <el-form-item label="封面图" prop="featuredImage">
        <el-upload
          class="featured-image-uploader"
          :auto-upload="false"
          :show-file-list="false"
          :on-change="(file) => beforeImageUpload(file.raw) && handleUpload(file.raw)"
        >
          <img v-if="articleForm.featuredImage" :src="articleForm.featuredImage" class="featured-image" />
          <el-icon v-else class="featured-image-uploader-icon"><Plus /></el-icon>
        </el-upload>
        <div class="el-upload__tip">建议尺寸：1200x630px，支持jpg、png格式</div>
      </el-form-item>
      
      <el-form-item label="文章标签" prop="tags">
        <el-select
          v-model="articleForm.tags"
          multiple
          filterable
          allow-create
          default-first-option
          placeholder="请选择文章标签"
        >
          <el-option
            v-for="item in tagOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      
      <el-form-item label="摘要" prop="summary">
        <el-input
          v-model="articleForm.excerpt"
          type="textarea"
          :rows="2"
          placeholder="请输入文章摘要"
        />
      </el-form-item>
      
      <el-form-item label="文章内容" prop="content" class="editor-container">
        <TinymceEditor v-model="articleForm.content" />
      </el-form-item>
    
      
      <el-form-item>
        <el-button type="primary" @click="submitForm">{{ isEdit ? '更新' : '发布' }}</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import TinymceEditor from './TinymceEditor.vue'
import ApiFactory from '@/api';

export default {
  name: 'ArticleEdit',
  components: {
    TinymceEditor,
    Plus
  },
  data() {
    return {
      articleForm:{
        id: undefined,
        title: '',
        author: '',
        createTime: '',
        content: '',
        excerpt: '',
        category: '',
        tags: [],
        image:'',
        slug:'',
        featuredImage: ''
      },
      authorOptions: []
    }
  },
  methods:{
    // 获取文章详情
    async fetchArticle(id) {
      try {
        const articleApi = ApiFactory.getArticleApi();
        const response = await articleApi.getArticle(id);
        
        if (response.isSuccess()) {
          // 将tags字符串转换为数组
          const articleData = response.data;
          if (articleData.tags) {
            articleData.tags = articleData.tags.split(',');
          }
          // 更新表单数据
          Object.assign(this.articleForm, articleData);
        } else {
          ElMessage.error(response.message || '获取文章详情失败');
        }
      } catch (error) {
        console.error('获取文章详情失败:', error);
        ElMessage.error('获取文章详情失败，请重试');
      }
    },
    // 获取作者列表
    async fetchAuthorList() {
      try {
        const authorApi = ApiFactory.getAuthorApi();
        const response = await authorApi.getAuthorList();
        if (response.isSuccess()) {
          this.authorOptions = response.data;
        } else {
          ElMessage.error(response.message || '获取作者列表失败');
        }
      } catch (error) {
        console.error('获取作者列表失败:', error);
        ElMessage.error('获取作者列表失败，请重试');
      }
    },
    // 自定义上传方法
    async handleUpload(file) {
      try {
        const uploadApi = ApiFactory.getUploadApi();
        const response = await uploadApi.uploadImage(file);
        console.log('uploadImage:'+JSON.stringify(response))
        if (response.isSuccess()) {
          this.articleForm.featuredImage = response.data.url;
          this.articleForm.image = response.data.url;
          ElMessage.success('封面图上传成功');
        } else {
          ElMessage.error(response.message || '上传失败');
        }
      } catch (error) {
        console.error('上传图片失败:', error);
        ElMessage.error('上传失败，请重试');
      }
    },
    // 上传前校验
    beforeImageUpload(file) {
      const isImage = file.type.startsWith('image/');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        ElMessage.error('只能上传图片文件!');
        return false;
      }
      if (!isLt2M) {
        ElMessage.error('图片大小不能超过 2MB!');
        return false;
      }
      return true;
    },
    async submitForm(){
      const articleApi = ApiFactory.getArticleApi();
      // 创建提交数据的副本
      const submitData = { ...this.articleForm };
      // 将tags数组转换为逗号分隔的字符串
      submitData.tags = submitData.tags.join(',');
      const response = await articleApi.createArticle(submitData);
      console.log('createArticle:'+JSON.stringify(response))
      if(response.isSuccess()){
        ElMessage.success('发布成功!');
        this.$router.push('/article/list');
      }else{
        ElMessage.error(response.message || '发布失败');
      }
    },
    resetForm(){
      console.log(this.articleForm)
    }
  },
  setup() {
    const route = useRoute()
    const router = useRouter()

    // 分类选项
    const categoryOptions = [
      { label: '前端', value: 'frontend' },
      { label: '后端', value: 'backend' },
      { label: '移动端', value: 'mobile' },
      { label: '技术分享', value: 'sharing' }
    ]

    // 标签选项
    const tagOptions = [
      { label: 'reviews', value: 'reviews' },
      { label: 'comparisons', value: 'comparisons' },
      { label: 'discounts', value: 'discounts' },
      { label: 'guides', value: 'guides' },
      { label: 'research', value: 'research' },
      { label: 'resources', value: 'resources' }
    ]

    // 判断是否是编辑模式
    const isEdit = computed(() => {
      return route.path.includes('/edit/')
    })

    return {
      isEdit,
      categoryOptions,
      tagOptions
    }
  },
  mounted() {
    this.fetchAuthorList();
    if (this.isEdit && this.$route.params.id) {
      this.fetchArticle(this.$route.params.id);
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;
}

.form-container {
  max-width: 960px;
  padding: 35px 35px 15px 35px;
  margin: 20px auto;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.form-header {
  text-align: center;
  margin-bottom: 30px;
  
  h3 {
    margin: 0;
    font-size: 24px;
    font-weight: 500;
    color: #333;
  }
}

/* TinyMCE编辑器容器样式 */
.editor-container {
  :deep(.tox-tinymce) {
    border-radius: 4px;
    border-color: #dcdfe6;
    
    &:hover {
      border-color: #c0c4cc;
    }
    
    &:focus-within {
      border-color: #409eff;
    }
  }
}

/* 确保编辑器中的图片不会撑破布局 */
:deep(.tox-edit-area__iframe) {
  img {
    max-width: 100%;
    height: auto;
  }
}

/* 自定义编辑器样式 */
.simple-editor {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  overflow: hidden;
  
  .editor-toolbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 8px;
    background-color: #f5f7fa;
    border-bottom: 1px solid #dcdfe6;
    
    .toolbar-left {
      display: flex;
      align-items: center;
      flex-wrap: wrap;
      gap: 5px;
      
      .el-select {
        margin-right: 5px;
      }
      
      .format-group, .align-group, .list-group, .indent-group {
        margin: 0 5px;
      }
    }
    
    .toolbar-right {
      display: flex;
      gap: 10px;
    }
  }
  
  .editor-content {
    min-height: 350px;
    padding: 12px 20px;
    overflow-y: auto;
    background-color: #fff;
    outline: none;
    
    &:focus {
      border-color: #409eff;
    }
    
    /* 基本内容样式 */
    h1, h2, h3, h4, h5, h6 {
      margin-top: 0.5em;
      margin-bottom: 0.5em;
    }
    
    h1 {
      font-size: 2em;
      font-weight: bold;
      color: #333;
      border-bottom: 1px solid #eee;
      padding-bottom: 0.3em;
      margin-top: 1em;
    }
    
    h2 {
      font-size: 1.5em;
      font-weight: bold;
      color: #333;
      margin-top: 0.8em;
    }
    
    h3 {
      font-size: 1.2em;
      font-weight: bold;
      color: #444;
    }
    
    h4 {
      font-size: 1.1em;
      font-weight: bold;
      color: #555;
    }
    
    p {
      margin: 1em 0;
    }
    
    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 1em;
      
      th, td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
      }
      
      th {
        background-color: #f2f2f2;
      }
    }
  }
}

/* 源代码编辑器样式 */
.source-code-editor {
  font-family: Consolas, Monaco, 'Andale Mono', 'Ubuntu Mono', monospace;
  
  :deep(.el-textarea__inner) {
    font-family: Consolas, Monaco, 'Andale Mono', 'Ubuntu Mono', monospace;
    tab-size: 2;
    line-height: 1.5;
    font-size: 14px;
  }
}

/* HTML预览区域样式 */
.html-preview {
  min-height: 300px;
  max-height: 500px;
  padding: 15px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  background-color: #fff;
  overflow: auto;
}

/* 全屏模式样式 */
:global(.editor-fullscreen-mode) {
  overflow: hidden;
  
  .simple-editor {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    z-index: 9999;
    border-radius: 0;
    display: flex;
    flex-direction: column;
    
    .editor-content {
      flex: 1;
      overflow: auto;
    }
  }
}

/* 按钮样式 */
.el-button-group button {
  padding: 4px 6px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 图片上传与设置相关样式 */
.image-uploader-container {
  .image-preview {
    margin-top: 20px;
    
    h4 {
      margin-bottom: 10px;
      font-weight: 500;
    }
    
    .preview-wrapper {
      display: flex;
      flex-direction: column;
      margin-bottom: 20px;
      
      img {
        max-width: 100%;
        max-height: 300px;
        border: 1px solid #dcdfe6;
        border-radius: 4px;
        padding: 5px;
        background-color: #f8f9fa;
      }
      
      .image-info {
        margin-top: 10px;
        font-size: 13px;
        color: #666;
        
        p {
          margin: 5px 0;
        }
      }
    }
  }
  
  .image-settings {
    border-top: 1px solid #ebeef5;
    padding-top: 15px;
    margin-top: 15px;
    
    h4 {
      margin-bottom: 15px;
      font-weight: 500;
    }
    
    .el-form-item {
      margin-bottom: 15px;
    }
    
    .unit {
      margin-left: 5px;
      color: #606266;
    }
  }
}

.el-upload__tip {
  line-height: 1.4;
  font-size: 12px;
  color: #888;
}

/* CTA按钮对话框样式 */
.cta-button-container {
  .color-label {
    margin-left: 10px;
    color: #606266;
  }
  
  .cta-preview {
    margin: 15px 0;
    padding: 20px;
    background-color: #f8f9fa;
    border-radius: 4px;
    text-align: center;
  }
}

/* 编辑器标题样式 - 全局选择器 */
.simple-editor .editor-content h2 {
  font-size: 1.5em !important;
  font-weight: bold !important;
  color: #333 !important;
  margin-top: 0.8em !important;
}

.simple-editor .editor-content h3 {
  font-size: 1.2em !important;
  font-weight: bold !important;
  color: #444 !important;
}

.simple-editor .editor-content h4 {
  font-size: 1.1em !important;
  font-weight: bold !important;
  color: #555 !important;
}

.featured-image-uploader {
  :deep(.el-upload) {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);

    &:hover {
      border-color: var(--el-color-primary);
    }
  }
}

.featured-image-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 300px;
  height: 157px;
  text-align: center;
  line-height: 157px;
}

.featured-image {
  width: 300px;
  height: 157px;
  display: block;
  object-fit: cover;
}
</style> 