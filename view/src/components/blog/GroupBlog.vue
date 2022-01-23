<template>
  <!-- 博客分组 -->
  <el-card class="group-card">
    <template #header>
      <div class="clearfix card-title">
        <span>分组</span>
      </div>
    </template>
    <el-row class="group-row" v-for="item in groupBlog">
      <el-col :span="12"><div class="grid-content bg-purple">{{item.name}}</div></el-col>
      <el-col :span="12"><div class="grid-content bg-purple-light">{{item.num}}</div></el-col>
    </el-row>
  </el-card>
  <!-- 博客分类 -->
  <el-card class="class-card">
    <template #header>
      <div class="clearfix card-title">
        <span>分类专栏</span>
      </div>
    </template>
    <el-row class="class-row" v-for="item in classBlog">
      <el-col :span="12"><div class="grid-content bg-purple">{{item.name}}</div></el-col>
      <el-col :span="12"><div class="grid-content bg-purple-light">{{item.num}}</div></el-col>
    </el-row>
  </el-card>
</template>

<script>
import {getCurrentInstance, ref} from "vue";

export default {
  name: "GroupBlog",
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 博客分组数据
    let groupBlog = ref([
      {
        id: 1,
        name: "学习笔记",
        num: 12
      },
      {
        id: 2,
        name: "微服务",
        num: 10
      }
    ]);
    // 博客分类数据
    let classBlog = ref([
      {
        id: 1,
        name: "Java",
        num: 12
      },
      {
        id: 2,
        name: "SpringBoot",
        num: 10
      }
    ]);
    return {groupBlog,classBlog};
  }
}
</script>

<style scoped>
.class-card{
  margin-left: 10px;
  margin-bottom: 20px;
  border-radius: 8px;
  width: 50%;
}
.group-card{
  margin-left: 10px;
  margin-bottom: 20px;
  border-radius: 8px;
  width: 50%;
}

.class-row{
  margin-top: 0px;
  margin-bottom: 0px;
  padding-left: 5px;
  align-content: center;
  height: 30px;
  border-radius: 5px;
}
.group-row{
  margin-top: 0px;
  margin-bottom: 0px;
  padding-left: 5px;
  align-content: center;
  height: 30px;
  border-radius: 5px;
}
.span-title{
  font-size: 20px;
  margin-bottom: 10px;
}
.card-title{
  font-weight: bold;
  font-size: 18px;
}
</style>