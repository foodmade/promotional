<template>
  <el-dialog title="弹窗预览" v-model="visible" width="600px" @close="$emit('close')">
    <div class="preview-container">
      <img v-if="row.logo" :src="row.logo" class="preview-logo" />
      <h2 class="preview-title">{{ row.title }}</h2>
      <div class="preview-prices">
        <span class="preview-original" v-if="row.original_price">￥{{ row.original_price }}</span>
        <span class="preview-special">￥{{ row.special_price }}</span>
      </div>
      <div class="preview-discount" v-if="row.discount_amount">立减￥{{ row.discount_amount }}</div>
      <el-button type="danger" class="preview-btn" :href="row.offer_link" target="_blank">{{ row.button_text }}</el-button>
      <div class="preview-user-count" v-if="row.user_count">已有{{ row.user_count }}人领取</div>
    </div>
  </el-dialog>
</template>

<script setup>
import { ref, watch } from 'vue'
const props = defineProps({ row: Object })
const visible = ref(true)
watch(() => props.row, () => { visible.value = true })
</script>

<style scoped>
.preview-container {
  text-align: center;
  padding: 24px 0;
}
.preview-logo {
  width: 80px;
  height: 80px;
  object-fit: contain;
  margin-bottom: 16px;
}
.preview-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 12px;
}
.preview-prices {
  margin-bottom: 8px;
}
.preview-original {
  text-decoration: line-through;
  color: #aaa;
  margin-right: 8px;
}
.preview-special {
  color: #d9535e;
  font-size: 22px;
  font-weight: bold;
}
.preview-discount {
  color: #43a047;
  font-size: 18px;
  margin-bottom: 12px;
}
.preview-btn {
  display: block;
  width: 80%;
  margin: 20px auto 10px auto;
  font-size: 18px;
  font-weight: bold;
  background: linear-gradient(90deg, #ff7300 0%, #ffb347 100%);
  border: none;
  color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(255,115,0,0.15);
}
.preview-user-count {
  color: #888;
  font-size: 14px;
  margin-top: 10px;
}
</style> 