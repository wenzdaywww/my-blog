<!-- 博客文章 -->
<template>
  <div>
    <div>
      <el-row>
        <el-col :span="5">
          <div class="grid-content bg-purple purple-left">
            <!-- 博主信息 -->
            <author/>
            <!-- 热门博客 -->
            <hot-blog/>
          </div>
        </el-col>
        <el-col :span="14">
          <!-- 博客文章 -->
          <blog-article/>
        </el-col>
        <el-col :span="5">
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
import groupBlog from "../module/GroupBlog.vue";
import hotBlog from "../module/HotBlog.vue";
import author from "../module/Author.vue";
import blogArticle from "./Content.vue";
import {provide} from "vue";

export default {
  components: {
    groupBlog, hotBlog,author,blogArticle
  },
  setup() {
    provide("float_type","float-right");//设置hotBlog样式
    return {};
  },
};
</script>
<style>
body {
  background-color: #f3f5f5;
}
</style>