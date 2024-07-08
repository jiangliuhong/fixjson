<script lang="ts" setup>
import {darkTheme} from 'naive-ui'
import usePreferencesStore from '@/stores/preferences'
import {darkThemeOverrides, themeOverrides} from '@/utils/theme.js'
import AppMain from '@/components/AppMain.vue'
import {onMounted, ref, watch} from 'vue'
import {WindowSetDarkTheme, WindowSetLightTheme} from 'wailsjs/runtime/runtime.js'

const prefStore = usePreferencesStore()
const initializing = ref(true)
onMounted(async () => {
  try {
    initializing.value = true
    //const env = await Environment()
    //console.log('env', env)
    // TODO 加载的逻辑
  } finally {
    initializing.value = false
  }
})

watch(
    () => prefStore.isDark,
    (isDark) => (isDark ? WindowSetDarkTheme() : WindowSetLightTheme()),
)

</script>
<template>
  <n-config-provider
      :inline-theme-disabled="true"
      :locale="prefStore.themeLocale"
      :theme="prefStore.isDark ? darkTheme : undefined"
      :theme-overrides="prefStore.isDark ? darkThemeOverrides : themeOverrides"
      class="fill-height">
    <app-main/>
  </n-config-provider>
</template>
<style lang="scss" scoped>

</style>