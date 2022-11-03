<template>
  <el-checkbox v-model="isCollapse" style="margin-bottom: 20px" v-on:click="collapse(isCollapse);">
    collapse
  </el-checkbox>
  <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
  >
    <el-sub-menu index="1">
      <template #title>
        <el-icon><Location /></el-icon>
        <span>Navigator One</span>
      </template>
      <el-menu-item-group>
        <template #title><span>服务器</span></template>
        <el-menu-item index="1-1">游戏服务器</el-menu-item>
        <el-menu-item index="1-2">item two</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="Group Two">
        <el-menu-item index="1-3">item three</el-menu-item>
      </el-menu-item-group>
      <el-sub-menu index="1-4">
        <template #title><span>item four</span></template>
        <el-menu-item index="1-4-1">item one</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
    <el-menu-item index="2">
      <el-icon><icon-menu /></el-icon>
      <template #title>Navigator Two</template>
    </el-menu-item>
    <el-menu-item index="3" disabled>
      <el-icon><Document /></el-icon>
      <template #title>Navigator Three</template>
    </el-menu-item>
    <el-menu-item index="4">
      <el-icon><setting /></el-icon>
      <template #title>Navigator Four</template>
    </el-menu-item>
  </el-menu>
</template>

<script>
import {Document, Menu as IconMenu, Location, Setting} from '@element-plus/icons-vue'
import {ref} from "vue";

export default {

  components: {
    Document,
    IconMenu,
    Location,
    Setting
  },
  data() {
    return {
      isCollapse: false
    }
  },

  methods: {

    collapse(value){
      ref(value)
    },
  }

}


</script>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>