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
        <el-tooltip class="item" effect="light" :content="item.blogTheme" placement="bottom">
          <el-link :href="item.blogId ? '/article?id=' + item.blogId : '#'" target="_blank" type="primary">{{ellipsis(item.blogTheme)}}</el-link>
        </el-tooltip>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import {getCurrentInstance, inject, ref} from "vue";

export default {
  name: "HotBlog",
  setup() {
    const float_type = inject("float_type"); //样式控制
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 热门博客数据
    let hotBlog = ref([]);
    // 文字超长设置省略号
    const ellipsis = (value) => {
      if (!value) return "";
      if (value.length > 30) {
        return value.slice(0, 30) + "...";
      }
      return value;
    }
    // 获取热门博客排行
    const getHotRank = () => {
      request.$http.get("api/blog/browse/hot-rank",null).then(function (res) {
        if(res.code === 200){
          hotBlog.value = res.data;
        }
      });
    }
    getHotRank();
    return {hotBlog,float_type,ellipsis};
  }
}
</script>

<style scoped>
.float-left{
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 8px;
  margin-bottom: 20px;
  width: 50%;
  float: left;
}
.float-right{
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 8px;
  margin-bottom: 20px;
  width: 50%;
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
</style>