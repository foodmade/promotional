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
        'insertdatetime', 'media', 'table', 'help', 'wordcount', 'promotion'
      ],
      toolbar: 'undo redo | blocks | ' +
        'bold italic backcolor | alignleft aligncenter ' +
        'alignright alignjustify | bullist numlist outdent indent | ' +
        'image link table promotion | removeformat | help',
      content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px } ' +
        '.promotion-button { display: block; width: 500px; margin: 30px auto; padding: 20px 0; background-color: #d9535e; color: #fff; text-decoration: none; border-radius: 10px; font-weight: bold; font-size: 24px; box-shadow: 2px 4px 10px rgba(0,0,0,0.15); text-align: center; transition: background 0.2s; } .promotion-button:hover { background-color: #c9303e; }',
      file_picker_types: 'image',
      file_picker_callback: filePickerCallback,
      setup: (editor) => {
        editor.ui.registry.addButton('promotion', {
          text: '插入促销',
          onAction: () => {
            editor.windowManager.open({
              title: '插入促销',
              body: {
                type: 'panel',
                items: [
                  {
                    type: 'input',
                    name: 'title',
                    label: '标题',
                    placeholder: '请输入促销标题'
                  },
                  {
                    type: 'input',
                    name: 'link',
                    label: '跳转链接',
                    placeholder: '请输入跳转链接'
                  }
                ]
              },
              buttons: [
                {
                  type: 'submit',
                  text: '确定'
                },
                {
                  type: 'cancel',
                  text: '取消'
                }
              ],
              onSubmit: (api) => {
                const data = api.getData();
                editor.insertContent(createButtonHtml(data.title, data.link));
                api.close();
              }
            });
          }
        });
      }
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

const createButtonHtml = (title, link) => {
  return '<a href="' + link + '" style="display: block; width: 500px; margin: 30px auto; padding: 20px 0; background-color: #d9535e; color: #fff; text-decoration: none; border-radius: 10px; font-weight: bold; font-size: 24px; box-shadow: 2px 4px 10px rgba(0,0,0,0.15); text-align: center; transition: background 0.2s;" target="_blank">' + title + '</a>';
};

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