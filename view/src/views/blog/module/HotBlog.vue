<!-- 热门博客 -->
<template>
  <!-- 热门博客 -->
  <el-card v-bind:class="float_type">
    <template #header>
      <div class="clearfix card-title">
        <span>热门博客</span>
      </div>
    </template>
    <el-row class="hot-row" v-for="item in hotBlog">
      <el-col>
        <el-tooltip class="item" effect="light" :content="item.title" placement="bottom">
          <el-link :href="item.blogId ? '/article?bid=' + item.blogId : '#'" target="_blank" type="primary" class="ellipsis-line1">{{item.title}}</el-link>
        </el-tooltip>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import {getCurrentInstance, inject, ref} from "vue";
import request from "../../../utils/request";

export default {
  name: "HotBlog",
  setup() {
    const float_type = inject("float_type"); //样式控制
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    // 热门博客数据
    let hotBlog = ref([]);
    // 获取热门博客排行
    const getHotRank = () => {
      axios.$http.get(request.hotRank,null).then(function (res) {
        if(res.code === 200){
          hotBlog.value = res.data;
        }
      });
    }
    getHotRank();
    return {hotBlog,float_type};
  }
}
</script>

<style scoped>
.float-left{
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 8px;
  margin-bottom: 20px;
  width: 360px;
  float: left;
}
.float-right{
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 8px;
  margin-bottom: 20px;
  width: 360px;
  float: right;
}
.hot-row{
  margin-top: 0px;
  margin-bottom: 0px;
  align-content: center;
  height: 30px;
}
.card-title{
  font-size: 18px;
  font-weight: bold;
}
.ellipsis-line1{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
</style>