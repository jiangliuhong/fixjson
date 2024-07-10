<script setup lang="ts">
import {ref} from 'vue'
import JsonEditorVue from 'json-editor-vue'

const json = ref('')


</script>

<template>
  <JsonEditorVue
      v-model="json"
      v-bind="{/* 局部 props & attrs */}"
  />
</template>

<style scoped lang="scss">
.editor-container {
  height: 400px;
  border: 1px solid #e5e5e5;
}
</style>