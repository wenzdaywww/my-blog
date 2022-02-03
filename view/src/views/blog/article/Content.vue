<!-- 博客文章内容 -->
<template>
  <div>
    <el-card>
      <el-row><span class="span-title">{{ blog.title }}</span></el-row>
      <el-row>
        <el-col :span="18">
          <el-tooltip class="item" effect="light" content="浏览" placement="bottom">
            <i class="el-icon-view color-grad">{{blog.browse}}</i>
          </el-tooltip>
          <el-tooltip class="item" effect="light" content="点赞" placement="bottom">
            <i class="padding-left10" :class="blog.praised ? 'el-icon-lx-likefill color-red' : 'el-icon-lx-like'" @click="addPraise">{{blog.praise}}</i>
          </el-tooltip>
          <i class="color-grad padding-left10" :class="blog.collection ? 'el-icon-lx-favorfill color-red' : 'el-icon-lx-favor'">
            <el-link href="javascript:void(0);" type="primary" class="item-collect" @click="addCollect">收藏 {{blog.collect}}</el-link>
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
    <!-- 评论列表 -->
    <blog-comment/>
    <!-- 收藏夹弹出窗 -->
    <collect-group ref="collectDialog" @blogCollectAdd="blogCollectAdd"/>
  </div>
</template>

<script>
import {getCurrentInstance, provide, reactive, ref} from "vue";
import utils from "../../../utils/utils";
import request from "../../../utils/request";
import blogComment from "./Comment.vue";
import collectGroup from "../module/CollectGroup.vue";
import {ElMessage} from "element-plus";

export default {
  name: "article",
  components: { blogComment,collectGroup },
  setup() {
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    //博客id
    const blogId = utils.getUrlParam("bid");
    //博客文章信息
    const blog = reactive({});
    //收藏夹弹出窗对象
    const collectDialog = ref();
    //收藏博客
    const addCollect = () => {
      if(utils.isLogin()){
        if(blog.userId !== utils.getUserId()){
          //已收藏，则取消收藏
          if(blog.collection){
            axios.$http.post(request.addCollect, {bid:blogId}).then(function (res) {
              if(res.code === 200){
                blog.collection = res.data.collection;
                if(blog.collection){
                  blog.collect++;
                  ElMessage.success('收藏成功');
                }else {
                  blog.collect = blog.collect == 0 ? 0 :  blog.collect - 1;
                  ElMessage.success('取消收藏成功');
                }
              }
            });
          }else {//未收藏，则添加收藏
            collectDialog.value.openCollectDialog(blogId,false);//调用子组件方法
          }
        }
      }else {
        ElMessage.info('请登录');
      }
    }
    //点赞
    const addPraise = () =>{
      if(utils.isLogin()){
        if(blog.userId !== utils.getUserId()){
          axios.$http.post(request.addPraise+blogId, null).then(function (res) {
            if(res.code === 200){
              blog.praised = res.data;
              if(blog.praised){
                blog.praise++;
                ElMessage.success('点赞成功');
              }else {
                blog.praise = blog.praise == 0 ? 0 :  blog.praise - 1;
                ElMessage.success('取消点赞成功');
              }
            }
          });
        }
      }else {
        ElMessage.info('请登录');
      }
    }
    //查询博客文章信息
    const getBlogArticle = () => {
      if(blogId){
        axios.$http.get(request.article+blogId,null).then(function (res) {
          if(res.code === 200){
            document.title += res.data.title;//设置浏览器标题
            blog.blogId = res.data.blogId;
            blog.userId = res.data.userId;
            blog.title = res.data.title;
            blog.groupName = res.data.groupName;
            blog.blogTag = res.data.blogTag;
            blog.content = res.data.content;
            blog.browse = res.data.browse;
            blog.praise = res.data.praise;
            blog.praised = res.data.praised;
            blog.collect = res.data.collect;
            blog.collection = res.data.collection;
            blog.comment = res.data.comment;
            blog.createTime = res.data.createTime;
          }
        });
      }
    }
    getBlogArticle();
    //收藏数量增加，供CollectGroup.vue调用
    const blogCollectAdd = () => {
      blog.collection = true;
      blog.collect++;
    }
    return {blog,addCollect,collectDialog,blogCollectAdd,addPraise};
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
.color-red{
  color: red;
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
.ellipsis-line1{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
</style>
