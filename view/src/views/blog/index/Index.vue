<!-- 博客首页 -->
<template>
  <div>
    <v-header />
    <div>
      <el-row>
        <el-col :span="2">
        </el-col>
        <el-col :span="15">
          <!-- 幻灯片 -->
          <tip-carousel/>
          <!-- 推荐博客列表 -->
          <tip-list/>
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
import hotBlog from "../module/HotBlog.vue";
import tipList from "./TipList.vue";
import tipCarousel from "./Carousel.vue";
import {provide} from "vue";

export default {
  components: {
    hotBlog,tipCarousel,tipList
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