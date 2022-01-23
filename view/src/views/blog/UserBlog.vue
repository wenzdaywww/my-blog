<template>
  <div>
    <div>
      <el-row>
        <el-col :span="7">
          <div class="grid-content bg-purple purple-left">
            <!-- 博主信息 -->
            <author/>
            <!-- 热门博客 -->
            <hot-blog/>
          </div>
        </el-col>
        <el-col :span="10">
          <!-- 博客列表 -->
          <blog-list/>
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
import groupBlog from "../../components/blog/GroupBlog.vue";
import hotBlog from "../../components/blog/HotBlog.vue";
import author from "../../components/blog/Author.vue";
import blogList from "../../components/blog/BlogList.vue";
import {provide} from "vue";

export default {
  components: {
    groupBlog, hotBlog,author,blogList
  },
  setup() {
    provide("float_type","float-right");//设置hotBlog样式
    return {};
  },
};
</script>
<style>

</style>