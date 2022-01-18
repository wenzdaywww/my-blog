<template>
  <div class="about">
    <v-header />
    <div class="content">
      <el-row>
        <el-col :span="7">
          <div class="grid-content bg-purple purple-left">
            <!-- 热门博客 -->
            <hot-blog/>
          </div>
        </el-col>
        <el-col :span="10">
          <!-- 博客列表 -->
          <router-view v-slot="{ Component }"></router-view>
        </el-col>
        <el-col :span="7">
          <div class="grid-content bg-purple">
            <!-- 博客分组 -->
            <group-blog/>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import vHeader from "../../components/Header.vue";
import groupBlog from "../../components/GroupBlog.vue";
import hotBlog from "../../components/HotBlog.vue";
import {provide } from "vue";
export default {
  components: {
    vHeader,groupBlog, hotBlog
  },
  setup() {
    //向子组件传值
    provide("isAdmin",false);
    return {};
  },
};
</script>
<style>
body {
  background: url("src/assets/img/backgroup.webp");
}
</style>