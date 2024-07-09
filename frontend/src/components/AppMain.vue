<script lang="ts" setup>
import iconUrl from "@/assets/images/logo.png";
import {useThemeVars} from "naive-ui";
import {computed, onMounted, reactive, ref} from "vue";
import {extraTheme} from "@/utils/extra_theme";
import usePreferencesStore from "@/stores/preferences";
import ToolbarControlWidget from '@/components/common/ToolbarControlWidget.vue'
import {isMacOS} from '@/utils/platform.js'
import Ribbon from "@/components/sidebar/Ribbon.vue";
import {EventsOn, WindowIsFullscreen, WindowIsMaximised, WindowToggleMaximise} from 'wailsjs/runtime/runtime.js'

const logoPaddingLeft = ref(10)
const maximised = ref(false)
const hideRadius = ref(false)
const prefStore = usePreferencesStore()
const themeVars = useThemeVars()


const toolbarVars = ['File', 'Edit', 'Help']

const data = reactive({
  navMenuWidth: 50,
  toolbarHeight: 38,
})
const props = defineProps({
  loading: Boolean,
})

const exThemeVars = computed(() => {
  return extraTheme(prefStore.isDark)
})
const spinStyle = computed(() => {
  return hideRadius.value
      ? {
        backgroundColor: themeVars.value.bodyColor,
      }
      : {
        backgroundColor: themeVars.value.bodyColor,
        borderRadius: '10px',
      }
})
const wrapperStyle = computed(() => {
  return hideRadius.value
      ? {}
      : {
        border: `1px solid ${themeVars.value.borderColor}`,
        borderRadius: '10px',
      }
})
const onToggleFullscreen = (fullscreen: boolean) => {
  hideRadius.value = fullscreen
  if (fullscreen) {
    logoPaddingLeft.value = 10
  } else {
    logoPaddingLeft.value = isMacOS() ? 70 : 10
  }
}
const onToggleMaximize = (isMaximised: boolean) => {
  if (isMaximised) {
    maximised.value = true
    if (!isMacOS()) {
      hideRadius.value = true
    }
  } else {
    maximised.value = false
    if (!isMacOS()) {
      hideRadius.value = false
    }
  }
}

EventsOn('window_changed', (info) => {
  const {fullscreen, maximised} = info
  onToggleFullscreen(fullscreen === true)
  onToggleMaximize(maximised)
})
onMounted(async () => {
  const fullscreen = await WindowIsFullscreen()
  onToggleFullscreen(fullscreen)
  const maximised = await WindowIsMaximised()
  onToggleMaximize(maximised)
})
</script>
<template>
  <n-spin :show="props.loading" :style="spinStyle" :theme-overrides="{ opacitySpinning: 0 }">
    <div id="app-content-wrapper" :style="wrapperStyle" class="flex-box-v">
      <!-- title bar -->
      <div id="app-toolbar" :style="{ height: data.toolbarHeight + 'px' }" class="flex-box-h"
           style="--wails-draggable: drag" @dblclick="WindowToggleMaximise">
        <!-- title -->
        <div id="app-toolbar-title">
          <n-space :size="10" :wrap="false" :wrap-item="false" align="center">
            <n-avatar :size="30" :src="iconUrl" color="#0000"/>
            <div style="min-width: 68px; white-space: nowrap; font-weight: 800">Fix JSON</div>
            <transition name="fade" v-for="(item,index) in toolbarVars" :index="index">
              <n-text class="ellipsis" strong style="font-size: 13px">
                {{ item }}
              </n-text>
            </transition>
          </n-space>
        </div>
        <div class="flex-item-expand" style="min-width: 15px"></div>
        <!-- simulate window control buttons -->
        <toolbar-control-widget
            v-if="!isMacOS()"
            :maximised="maximised"
            :size="data.toolbarHeight"
            style="align-self: flex-start"/>
      </div>
      <!-- content -->
      <div
          id="app-content"
          :style="prefStore.generalFont"
          class="flex-box-h flex-item-expand"
          style="--wails-draggable: none">
        <ribbon/>
      </div>
    </div>
  </n-spin>
</template>
<style lang="scss" scoped>
#app-content-wrapper {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  box-sizing: border-box;
  background-color: v-bind('themeVars.bodyColor');
  color: v-bind('themeVars.textColorBase');

  #app-toolbar {
    background-color: v-bind('exThemeVars.titleColor');
    border-bottom: 1px solid v-bind('exThemeVars.splitColor');

    &-title {
      padding-left: 10px;
      padding-right: 10px;
      box-sizing: border-box;
      align-self: center;
      align-items: baseline;
    }
  }

  .app-toolbar-tab {
    align-self: flex-end;
    margin-bottom: -1px;
    margin-left: 3px;
    overflow: auto;
  }

  #app-content {
    height: calc(100% - 60px);

    .content-area {
      overflow: hidden;
    }
  }

  .app-side {
    height: 100%;
    background-color: v-bind('exThemeVars.sidebarColor');
    border-right: 1px solid v-bind('exThemeVars.splitColor');
  }
}
</style>