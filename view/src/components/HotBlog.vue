<template>
  <!-- 热门博客 -->
  <el-card class="hot-card">
    <span class="span-title"><b>热门博客</b></span>
    <el-row class="hot-row" v-for="item in hotBlog">
      <el-col>
        <a href="#" style="width: 100%;">{{item.title}}</a>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import {getCurrentInstance, ref} from "vue";

export default {
  name: "HotBlog",
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 热门博客数据
    let hotBlog = ref([
      {
        blogId: "1",
        title: "1、你真的理解什么是财富自由吗？"
      },
      {
        blogId: "2",
        title: "2、你真的理解什么是财富自由吗？"
      }
    ]);
    return {hotBlog};
  }
}
</script>

<style scoped>
.hot-card{
  margin-right: 10px;
  border-radius: 8px;
  margin-bottom: 20px;
  width: 50%;
  float: right;
}
.span-title{
  font-size: 20px;
  margin-bottom: 10px;
}
.hot-row{
  margin-top: 0px;
  margin-bottom: 0px;
  align-content: center;
  height: 30px;
}
</style>