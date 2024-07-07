<script setup lang="ts">
import WindowMin from '@/components/icons/WindowMin.vue'
import WindowMax from '@/components/icons/WindowMax.vue'
import WindowClose from '@/components/icons/WindowClose.vue'
import { Quit, WindowMinimise, WindowToggleMaximise } from 'wailsjs/runtime/runtime.js'

const handleMinimise = async () => {
  WindowMinimise()
}

const handleMaximise = () => {
  WindowToggleMaximise()
}

const handleClose = () => {3
  Quit()
}
</script>
<template>
  <n-space :size="0" :wrap-item="false" align="center" justify="center">
    <n-tooltip :delay="1000" :show-arrow="false">
      初始化
      <template #trigger>
        <div class="btn-wrapper" @click="handleMinimise">
          <window-min />
        </div>
      </template>
    </n-tooltip>
  </n-space>
</template>
<style scoped>
</style>