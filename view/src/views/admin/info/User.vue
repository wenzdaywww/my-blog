<!-- 个人资料 -->
<template>
  <div>
    <div>
      <el-breadcrumb class="crumb-title" separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-s-custom">个人资料</i>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-row>
      <el-col :span="20">
        <!--  基础信息-->
        <user-photo/>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20">
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