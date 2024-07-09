<script setup lang="ts">
import {onBeforeUnmount, onMounted, ref,Ref} from 'vue'
import JSONEditor from '@json-editor/json-editor';

const editor: Ref<any> = ref(null);
let jsonEditor:any
const json = ref('')
const onJSONChange = (e: Event) => {
  console.log('value:', e)
}
onMounted(() => {
  jsonEditor.value = new JSONEditor(editor.value, {
    schema: {
      type: 'object',
      title: 'Example Schema',
      properties: {
        name: {type: 'string'},
        age: {type: 'integer'},
      },
    },
    startval: {
      name: 'John Doe',
      age: 30,
    },
  });
})
onBeforeUnmount(() => {
  if (jsonEditor.value) {
    jsonEditor.value.destroy();
  }
});
</script>

<template>
  <div ref="editor"></div>
</template>

<style scoped lang="scss">
.editor-container {
  height: 400px;
  border: 1px solid #e5e5e5;
}
</style>