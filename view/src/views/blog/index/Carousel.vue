<!-- 幻灯片播放 -->
<template>
  <el-carousel :interval="4000" type="card" height="300px">
    <el-carousel-item v-for="item in list" :key="item">
      <img  :src="item.img" class="image"/>
    </el-carousel-item>
  </el-carousel>
</template>

<script>
import {ref} from "vue";

export default {
  name: "Carousel",
  setup(){
    // 幻灯片列表
    const list = ref([
      { img:"static/img/backgroup1.jpg" },
      { img:"static/img/backgroup2.jpg" },
      { img:"static/img/backgroup3.jpg" },
      { img:"static/img/backgroup4.jpg" }
    ]);
    return {list}
  }
}
</script>

<style scoped>
.image{
  max-width: 100%;
  height: auto;
  width: auto;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>