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
        <el-tooltip class="item" effect="light" content="收藏" placement="bottom">
          <i class="el-icon-lx-favor color-grad padding-left10">{{blog.collect}}</i>
        </el-tooltip>
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

export default {
  name: "article",
  components: { blogComment },
  setup() {
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    //博客id
    const blogId = utils.getUrlParam("bid");
    //博客文章信息
    let blog = reactive({
      blogId: 0,
      title: "",
      groupName: "",
      blogTag:[],
      content: '',
      browse:-1,
      praise:-1,
      collect:-1,
      comment:-1,
      createTime:""
    });
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
              blog.comment = res.data.comment;
              blog.createTime = res.data.createTime;
            }
          });
      }
    }
    getBlogArticle();
    return {blog};
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
</style>
