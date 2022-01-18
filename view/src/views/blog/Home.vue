<template>
  <div>
    <v-header />
    <div class="blog-content">
      <router-view v-slot="{ Component }"></router-view>
    </div>
  </div>
</template>
<script>
import {provide} from "vue";
import vHeader from "../../components/common/Header.vue";

export default {
  components: {
    vHeader
  },
  setup() {
    //向子组件传值
    provide("isAdmin",false);
    return {};
  },
};
</script>

<style>
.blog-content{
  margin-top: 5px;
}
</style>