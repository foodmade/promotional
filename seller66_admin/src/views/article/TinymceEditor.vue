<template>
  <Editor
    api-key="tzrpmzkb6xjxx4y8o4p21dluu77rwsm920wy2qzxbcr156au"
    v-model="innerContent"
    :init="{
      base_url: '/tinymce',
      language: 'zh_CN',
      language_url: '/tinymce/lang/zh_CN.js',
      height: 500,
      width: '100%',
      menubar: true,
      plugins: [
        'advlist', 'autolink', 'lists', 'link', 'image', 'charmap', 'preview',
        'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
        'insertdatetime', 'media', 'table', 'help', 'wordcount'
      ],
      toolbar: 'undo redo | blocks | ' +
        'bold italic backcolor | alignleft aligncenter ' +
        'alignright alignjustify | bullist numlist outdent indent | ' +
        'image link table | removeformat | help',
      content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }',
      file_picker_types: 'image',
      file_picker_callback: filePickerCallback
    }"
    @onInit="handleEditorInit"
  />
</template>

<script setup>
import { ref, watch } from 'vue'
import Editor from '@tinymce/tinymce-vue'

const props = defineProps({
  modelValue: String
})
const emit = defineEmits(['update:modelValue'])
const innerContent = ref(props.modelValue)

watch(() => props.modelValue, v => {
  if (v !== innerContent.value) innerContent.value = v
})
watch(innerContent, v => emit('update:modelValue', v))

const handleEditorInit = (editor) => {
  // 可选：初始化后逻辑
}

const filePickerCallback = (callback, value, meta) => {
  if (meta.filetype === 'image') {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/*');
    input.click();
    input.onchange = function () {
      const file = this.files[0];
      const reader = new FileReader();
      reader.onload = function () {
        callback(reader.result, { alt: file.name });
      };
      reader.readAsDataURL(file);
    };
  }
};
</script> 