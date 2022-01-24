<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-s-custom">个人资料</i>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-row :gutter="20">
      <el-col :span="1"></el-col>
      <el-col :span="18">
        <!--  基础信息-->
        <user-photo/>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="1"></el-col>
      <el-col :span="18">
        <!--  信息修改-->
        <user-info/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userPhoto from "./Photo.vue";
import userInfo from "./Info.vue";

export default {
  name: "user",
  components: {
    userPhoto,userInfo
  },
  setup() {
    return {};
  },
};
</script>

<style scoped>

</style>