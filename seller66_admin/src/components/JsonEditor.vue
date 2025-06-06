<template>
  <div ref="container" :style="{height, minHeight: '200px'}"></div>
</template>

<script setup>
import { ref, watch, onMounted, onBeforeUnmount } from 'vue'
import JSONEditor from 'jsoneditor'
import 'jsoneditor/dist/jsoneditor.css'

const props = defineProps({
  modelValue: { type: [Object, Array], required: true },
  mode: { type: String, default: 'tree' },
  height: { type: String, default: '400px' }
})
const emit = defineEmits(['update:modelValue'])

const container = ref(null)
let editor = null

onMounted(() => {
  editor = new JSONEditor(container.value, {
    mode: props.mode,
    mainMenuBar: false,
    statusBar: false,
    navigationBar: false,
    onChange: () => {
      try {
        const val = editor.get()
        emit('update:modelValue', val)
      } catch (e) {}
    },
    onEditable: function (node) {
      if (node.field !== undefined) {
        return { field: false, value: true }
      }
      return true
    }
  })
  editor.set(props.modelValue)
})

watch(() => props.modelValue, (val) => {
  if (editor && JSON.stringify(editor.get()) !== JSON.stringify(val)) {
    editor.update(val)
  }
})

onBeforeUnmount(() => {
  if (editor) editor.destroy()
})
</script>

<style scoped>
</style> 