<script setup lang="ts">
import {useThemeVars} from 'naive-ui'
import {computed} from 'vue'
import usePreferencesStore from '@/stores/preferences.js'
import {extraTheme} from '@/utils/extra_theme.js'
import {BrowserOpenURL} from 'wailsjs/runtime/runtime.js'
import IconButton from '@/components/common/IconButton.vue'
import {useRender} from '@/utils/render'
import useDialogStore from '@/stores/dialog'
import { LogoGithub, SettingsOutline} from '@vicons/ionicons5'

const themeVars = useThemeVars()
const prefStore = usePreferencesStore()
const exThemeVars = computed(() => {
  return extraTheme(prefStore.isDark)
})
const render = useRender()
const emit = defineEmits(['update:value'])
const props = defineProps({
  value: {
    type: String,
    default: 'server',
  },
  width: {
    type: Number,
    default: 60,
  },
})
const menuOptions: any = computed(() => {
  return []
})
const iconSize = computed(() => Math.floor(props.width * 0.45))
const openGithub = () => {
  BrowserOpenURL('https://github.com/jiangliuhong/fixjson')
}
const preferencesOptions: any = computed(() => {
  return [
    {
      label: '偏好设置',
      key: 'preferences',
    },
    {
      label: '报错错误',
      key: 'report',
    },
    {
      type: 'divider',
      key: 'd1',
    },
    {
      label: '关于',
      key: 'about',
    }
  ]
})
const dialogStore = useDialogStore()
const onSelectPreferenceMenu = (key: string) => {
  switch (key) {
    case 'preferences':
      //dialogStore.openPreferencesDialog()
      break
    case 'report':
      BrowserOpenURL('https://github.com/jiangliuhong/fixjson/issues')
      break
    case 'about':
      dialogStore.openAboutDialog()
      break
  }
}
</script>

<template>
  <div
      id="app-ribbon"
      :style="{
            width: props.width + 'px',
            minWidth: props.width + 'px',
        }"
      class="flex-box-v">
    <!-- header -->
    <div class="ribbon-wrapper flex-box-v">
      <n-tooltip v-for="(m, i) in menuOptions" :key="i" :delay="2" :show-arrow="false" placement="right">
        <template #trigger>
          <div
              v-show="m.show !== false"
              :class="{ 'ribbon-item-active': props.value === m.key }"
              class="ribbon-item clickable"
              @click="emit('update:value', m.key)">
            <n-icon :size="iconSize">
              <component :is="m.icon" :stroke-width="3.5"/>
            </n-icon>
          </div>
        </template>
        {{ m.label }}
      </n-tooltip>
    </div>
    <div class="flex-item-expand"></div>
    <!-- bottom -->
    <div class="nav-menu-item flex-box-v">
      <n-dropdown
          :options="preferencesOptions"
          :render-icon="({ icon }:any) => render.renderIcon(icon)"
          :render-label="({ label }:any) => render.renderLabel(label, { class: 'context-menu-item' })"
          trigger="click"
          @select="onSelectPreferenceMenu">
        <icon-button :icon="SettingsOutline" :size="iconSize" :stroke-width="3"/>
      </n-dropdown>
      <icon-button
          :icon="LogoGithub"
          :size="iconSize"
          :tooltip-delay="100"
          t-tooltip="Github"
          @click="openGithub"/>
    </div>
  </div>
</template>

<style scoped lang="scss">
#app-ribbon {
  //height: 100vh;
  border-right: v-bind('exThemeVars.splitColor') solid 1px;
  background-color: v-bind('exThemeVars.ribbonColor');
  box-sizing: border-box;
  color: v-bind('themeVars.textColor2');
  --wails-draggable: drag;

  .ribbon-wrapper {
    gap: 20px;
    margin-top: 5px;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
    padding-right: 3px;
    --wails-draggable: none;

    .ribbon-item {
      width: 100%;
      height: 100%;
      text-align: center;
      line-height: 1;
      color: v-bind('themeVars.textColor3');
      //border-left: 5px solid #000;
      border-radius: v-bind('themeVars.borderRadius');
      padding: 8px 0;
      position: relative;

      &:hover {
        background-color: rgba(0, 0, 0, 0.05);
        color: v-bind('themeVars.primaryColor');

        &:before {
          position: absolute;
          width: 3px;
          left: 0;
          top: 24%;
          bottom: 24%;
          border-radius: 9999px;
          content: '';
          background-color: v-bind('themeVars.primaryColor');
        }
      }
    }

    .ribbon-item-active {
      //background-color: v-bind('exThemeVars.ribbonActiveColor');
      color: v-bind('themeVars.primaryColor');

      &:hover {
        color: v-bind('themeVars.primaryColor') !important;
      }

      &:before {
        position: absolute;
        width: 3px;
        left: 0;
        top: 24%;
        bottom: 24%;
        border-radius: 9999px;
        content: '';
        background-color: v-bind('themeVars.primaryColor');
      }
    }
  }

  .nav-menu-item {
    align-items: center;
    padding: 10px 0 15px;
    --wails-draggable: none;

    button {
      margin: 20px 0;
    }

  }

}
</style>