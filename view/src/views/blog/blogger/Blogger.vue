<!-- 用户博客 -->
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
          <blog-list ref="blogListRef"/>
        </el-col>
        <el-col :span="7">
          <div class="grid-content bg-purple">
            <!-- 博客分组 -->
            <group-blog @findBlogGroup="findBlogGroup" @findBlogTag="findBlogTag"/>
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
import blogList from "./BlogList.vue";
import {provide, ref} from "vue";

export default {
  components: {
    groupBlog, hotBlog,author,blogList
  },
  setup() {
    provide("float_type","float-right");//设置hotBlog样式
    //博客列表对象
    const blogListRef = ref();
    // 获取博主博客分组groupId的列表--供子组件调用
    const findBlogGroup = (groupId,title) => {
      blogListRef.value.findBlogGroup(groupId,title);
    }
    // 获取博主博客分类tagId的列表--供子组件调用
    const findBlogTag = (tagId,title) => {
      blogListRef.value.findBlogTag(tagId,title);
    }
    return {blogListRef,findBlogGroup,findBlogTag};
  },
};
</script>
<style>
body {
  background-color: #f3f5f5;
}
</style>