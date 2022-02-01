<!-- 博客文章内容 -->
<template>
  <el-card>
    <el-row><span class="span-title">{{ blog.title }}</span></el-row>
    <el-row>
      <el-col :span="18">
        <el-tooltip class="item" effect="light" content="浏览" placement="bottom">
          <i class="el-icon-view color-grad">{{blog.browse}}</i>
        </el-tooltip>
        <el-tooltip class="item" effect="light" content="点赞" placement="bottom">
          <i class="el-icon-lx-like color-grad padding-left10">{{blog.praise}}</i>
        </el-tooltip>
        <i class="color-grad padding-left10" :class="blog.collection ? 'el-icon-lx-favorfill' : 'el-icon-lx-favor'">
          <el-link href="javascript:void(0);" type="primary" class="item-collect" @click="addCollect()">收藏 {{blog.collect}}</el-link>
        </i>
        <el-tooltip class="item" effect="light" content="评论" placement="bottom">
          <i class="el-icon-lx-comment color-grad padding-left10">{{blog.comment}}</i>
        </el-tooltip>
      </el-col>
      <el-col :span="6"><span class="blog-time color-grad">{{blog.createTime}}</span></el-col>
    </el-row>
    <el-row>
      <span class="color-grad mag-top">分组：</span><el-tag size="mini" class="tag-name">{{blog.groupName}}</el-tag>
      <span class="color-grad mag-top">标签：</span><el-tag v-for="item in blog.blogTag" size="mini" type="success" class="tag-name">{{item.tagName}}</el-tag>
    </el-row>
  </el-card>
  <el-card class="blog-card">
    <div class="artitle-div" v-html="blog.content"></div>
  </el-card>
  <blog-comment/>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import utils from "../../../utils/utils";
import request from "../../../utils/request";
import blogComment from "./Comment.vue";
import {ElMessage} from "element-plus";

export default {
  name: "article",
  components: { blogComment },
  setup() {
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    //博客id
    const blogId = utils.getUrlParam("bid");
    //博客文章信息
    const blog = reactive({});
    //收藏博客
    const addCollect = () => {
      if(utils.isLogin()){
        axios.$http.post(request.addCollect+blogId,null).then(function (res) {
          if(res.code === 200){
            blog.collection = res.data;
            if(blog.collection){
              blog.collect++;
              ElMessage.success('收藏成功');
            }else {
              blog.collect--;
              ElMessage.success('取消收藏成功');
            }
          }
        });
      }else {
        ElMessage.info('请登录');
      }
    }
    //查询博客文章信息
    const getBlogArticle = () => {
      if(blogId){
        axios.$http.get(request.article+blogId,null).then(function (res) {
          if(res.code === 200){
            blog.blogId = res.data.blogId;
            blog.title = res.data.title;
            blog.groupName = res.data.groupName;
            blog.blogTag = res.data.blogTag;
            blog.content = res.data.content;
            blog.browse = res.data.browse;
            blog.praise = res.data.praise;
            blog.collect = res.data.collect;
            blog.collection = res.data.collection;
            blog.comment = res.data.comment;
            blog.createTime = res.data.createTime;
          }
        });
      }
    }
    getBlogArticle();
    return {blog,addCollect};
  }
};
</script>

<style scoped>
.span-title{
  font-size: 20px;
  margin-bottom: 10px;
}
.blog-card{
  border-radius: 8px;
  margin-bottom: 3px;
}
.color-grad{
  color: #999697;
}
.padding-left10{
  padding-left: 10px;
}
.blog-time{
  color: #999697;
  float: right;
  margin-right: 5px;
}
.tag-name{
  margin-right: 10px;
  margin-top: 8px;
}
.mag-top{
  margin-top: 5px;
}
.artitle-div{
  margin-left: 10px;
  margin-right: 10px;
}
.item-collect{
  font-size: 15px;
  margin-bottom: 3px;
}
</style>
