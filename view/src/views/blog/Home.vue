<template>
  <v-header />
  <div class="control-box">
    <div class="content">
      <router-view v-slot="{ Component }"></router-view>
    </div>
  </div>
</template>
<script>
import {provide} from "vue";
import vHeader from "../../components/Header.vue";

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
</style>