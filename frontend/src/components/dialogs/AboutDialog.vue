<script setup lang="ts">
import iconUrl from '@/assets/images/logo.png'
import useDialog from '@/stores/dialog'
import {useThemeVars} from 'naive-ui'
import {onMounted, ref} from 'vue'
import {GetClientVersion} from "wailsjs/go/service/preferencesService";

const themeVars = useThemeVars()
const dialogStore = useDialog()
const version = ref('')
onMounted(() => {
  GetClientVersion().then(({data}) => {
    version.value = data.version
  })
})

</script>

<template>
  <n-modal v-model:show="dialogStore.aboutDialogVisible" :show-icon="false" preset="dialog" transform-origin="center">
    <n-space :size="10" :wrap="false" :wrap-item="false" align="center" vertical>
      <n-avatar :size="120" :src="iconUrl" color="#0000"></n-avatar>
      <div class="about-app-title">Fix JSON</div>
      <n-text>{{ version }}</n-text>
      <div :style="{ color: themeVars.textColor3 }" class="about-copyright">
        Copyright © 2024 Tinycraft.cc All rights reserved
      </div>
    </n-space>
  </n-modal>
</template>

<style lang="scss" scoped>
.about-app-title {
  font-weight: bold;
  font-size: 18px;
  margin: 5px;
}

.about-link {
  cursor: pointer;

  &:hover {
    text-decoration: underline;
  }
}

.about-copyright {
  font-size: 12px;
}
</style>