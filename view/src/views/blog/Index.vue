<template>
  <div>
    <v-header />
    <div>
      <el-row>
        <el-col :span="3">
        </el-col>
        <el-col :span="14">
          <!-- 博客列表 -->
          <blog-list/>
        </el-col>
        <el-col :span="7">
          <div class="grid-content bg-purple">
            <!-- 热门博客 -->
            <hot-blog/>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import hotBlog from "./module/HotBlog.vue";
import blogList from "./module/BlogList.vue";
import {provide} from "vue";

export default {
  components: {
    hotBlog,blogList
  },
  setup() {
    provide("float_type","float-left");//设置hotBlog样式
    return {};
  },
};
</script>
<style>
body {
  background-color: #f3f5f5;
}
</style>