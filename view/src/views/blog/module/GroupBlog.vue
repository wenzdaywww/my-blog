<template>
  <!-- 博客分组 -->
  <el-card v-if="groupBlog.length > 0" class="group-card">
    <template #header>
      <div class="clearfix card-title">
        <span>分组</span>
      </div>
    </template>
    <el-row class="group-row" v-for="item in groupBlog">
      <el-col :span="16">
        <el-tooltip v-if="item.groupName.length > 14" class="item" effect="light" :content="item.groupName" placement="bottom">
          <el-tag size="small" class="group-name">
            <el-link @click="findBlogGroup(item.bgId,item.groupName)" type="primary" href="javascript:void(0);" >{{ellipsis(item.groupName)}}</el-link>
          </el-tag>
        </el-tooltip>
        <el-tag v-if="item.groupName.length <= 14" size="small" class="group-name">
          <el-link @click="findBlogGroup(item.bgId,item.groupName)" type="primary" href="javascript:void(0);" >{{ellipsis(item.groupName)}}</el-link>
        </el-tag>
      </el-col>
      <el-col :span="8">
        <el-tag type="danger" size="mini">{{item.groupNum}}</el-tag>
      </el-col>
    </el-row>
  </el-card>
  <!-- 博客分类 -->
  <el-card v-if="classBlog.length > 0" class="class-card">
    <template #header>
      <div class="clearfix card-title">
        <span>分类专栏</span>
      </div>
    </template>
    <el-row class="class-row" v-for="item in classBlog">
      <el-col :span="16">
        <el-tooltip v-if="item.className.length > 14" class="item" effect="light" :content="item.className" placement="bottom">
          <el-tag type="success" size="small" class="group-name">
            <el-link @click="findBlogClass(item.classId,item.className)" type="success" href="javascript:void(0);" >{{ellipsis(item.className)}}</el-link>
          </el-tag>
        </el-tooltip>
        <el-tag v-if="item.className.length <= 14" type="success" size="small" class="group-name">
          <el-link @click="findBlogClass(item.classId,item.className)" type="success" href="javascript:void(0);" >{{item.className}}</el-link>
        </el-tag>
      </el-col>
      <el-col :span="8">
        <el-tag type="danger" size="mini">{{item.classNum}}</el-tag>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import {getCurrentInstance, ref,defineEmit } from "vue";
import utils from "../../../utils/utils";
import {ElMessage} from "element-plus";

export default {
  name: "GroupBlog",
  emits: ['findBlogGroup','findBlogClass'], //父组件中引用子组件定义的方法
  setup(props,{emit}) { //调用父组件方法，必须有props,{emit}。否则调用失败
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    //博主id
    const authorId = utils.getUrlParam("id");
    //博客id
    const blogId = utils.getUrlParam("bid");
    // 博客分组数据
    let groupBlog = ref([
      {
        bgId: 0,
        groupName: "未分组",
        groupNum: 0
      }
    ]);
    // 博客分类数据
    let classBlog = ref([
      {
        classId: 0,
        className: "未分类",
        classNum: 0
      }
    ]);
    // 文字超长设置省略号
    const ellipsis = (value) => {
      if (!value) return "";
      if (value.length > 14) {
        return value.slice(0, 14) + "...";
      }
      return value;
    }
    // 获取博主博客分组列表
    const getBlogGroup = () => {
      request.$http.get("api/blog/browse/group",{id:authorId,bid:blogId}).then(function (res) {
        if(res.code === 200){
          groupBlog.value = res.data;
        }
      });
    }
    getBlogGroup();
    // 获取博主博客分类列表
    const getBlogClass = () => {
      request.$http.get("api/blog/browse/class",{id:authorId,bid:blogId}).then(function (res) {
        if(res.code === 200){
          classBlog.value = res.data;
        }
      });
    }
    getBlogClass();
    // 获取博主博客分组groupId的列表
    const findBlogGroup = (groupId,groupName) => {
      emit('findBlogGroup',groupId,"分组：" + groupName);//调用父组件的findBlogGroup方法
    }
    // 获取博主博客分类classId的列表
    const findBlogClass = (classId,className) => {
      emit('findBlogClass',classId,"分类：" + className);//调用父组件的findBlogClass方法
    }
    return {authorId,groupBlog,classBlog,ellipsis,findBlogGroup,findBlogClass};
  }
}
</script>

<style scoped>
.text-center{
  text-align: center;
}
.class-card{
  margin-left: 10px;
  margin-bottom: 20px;
  border-radius: 8px;
  width: 360px;
}
.group-card{
  margin-left: 10px;
  margin-bottom: 20px;
  border-radius: 8px;
  width: 360px;
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
.group-name{
  margin-bottom: 5px;
}
</style>