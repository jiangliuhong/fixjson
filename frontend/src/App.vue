<template>
  <n-flex :size="0">
    <tool-bar class="fd-toolbar"/>
    <div class="fd-content">2</div>
  </n-flex>
</template>
<script lang="ts" setup>
import ToolBar from "./components/ToolBar.vue";
</script>
<style scoped>
.fd-toolbar {
  width: 40px;
  background-color: #f9f9f9;
  height: 100vh;
}

.fd-content {
  background-color: #e0e0e0;
  height: 100vh;
  width: calc(100vw - 40px);
}
</style>