<script lang="ts" setup>
import iconUrl from "@/assets/images/logo.png";
import {useThemeVars} from "naive-ui";
import {computed, reactive, ref} from "vue";
import {extraTheme} from "@/utils/extra_theme";
import usePreferencesStore from "@/stores/preferences";
import ToolbarControlWidget from '@/components/common/ToolbarControlWidget.vue'
import {isMacOS} from '@/utils/platform.js'

const prefStore = usePreferencesStore()
const themeVars = useThemeVars()
const exThemeVars = computed(() => {
  return extraTheme(prefStore.isDark)
})
const toolbarVars = ['File', 'Edit', 'Help']
const maximised = ref(false)
const data = reactive({
  navMenuWidth: 50,
  toolbarHeight: 38,
})
</script>
<template>
  <div id="app-content-wrapper">
    <!-- title bar -->
    <div id="app-toolbar" class="flex-box-h" style="--wails-draggable: drag">
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
    </div>
  </div>
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