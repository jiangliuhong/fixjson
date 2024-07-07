<script lang="ts" setup>
import iconUrl from '@/assets/images/logo.png'
</script>
<template>
  <div id="app-content-wrapper">
    <!-- title bar -->
    <div id="app-toolbar" class="flex-box-h" style="--wails-draggable: drag">
      <!-- title -->
      <div id="app-toolbar-title">
        <n-space :size="3" :wrap="false" :wrap-item="false" align="center">
          <n-avatar :size="30" :src="iconUrl" color="#0000" />
          <div style="min-width: 68px; white-space: nowrap; font-weight: 800">Fix JSON</div>
          <transition name="fade">
            <n-text class="ellipsis" strong style="font-size: 13px">
              - gsd
            </n-text>
          </transition>
        </n-space>
      </div>
    </div>
    <!-- browser tabs -->
    <div class="app-toolbar-tab flex-item-expand">
      1111
    </div>
    <!-- content -->
    <div
        id="app-content"
        class="flex-box-h flex-item-expand"
        style="--wails-draggable: none">
    </div>
  </div>
</template>
<style scoped>
#app-content-wrapper {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  box-sizing: border-box;

  #app-toolbar {

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
  }
}
</style>