<!-- 博客列表 -->
<template>
  <el-card>
    <span class="span-title">{{ query.listTitle }}</span>
  </el-card>
  <div v-if="blogList != null && blogList.length > 0">
    <el-card class="blog-card" v-for="item in blogList">
      <img v-if="item.coverImg" :src="item.coverImg" class="image" />
      <div class="blog-detail">
        <el-tooltip class="item" effect="light" :content="item.title" placement="bottom">
          <el-link :href="item.blogId ? '/article?bid=' + item.blogId : '#'" class="ellipsis-line1 blog-title"
                   target="_blank" @click="showBlogDetail(item.blogId)">{{item.title}}</el-link>
        </el-tooltip>
        <div>
          <el-row class="el-row">
            <div class="ellipsis-line4">
              {{item.summary}}
            </div>
          </el-row>
          <el-row class="el-row">
            <div style="width: 50%;">
              <el-tooltip class="item" effect="light" content="浏览量" placement="bottom">
                <i class="el-icon-view color-grad">{{item.browse}}</i>
              </el-tooltip>
              <el-tooltip class="item" effect="light" content="点赞量" placement="bottom">
                <i class="el-icon-lx-like color-grad padding-left10">{{item.praise}}</i>
              </el-tooltip>
              <el-tooltip class="item" effect="light" content="收藏数" placement="bottom">
                <i class="el-icon-lx-favor color-grad padding-left10">{{item.collect}}</i>
              </el-tooltip>
              <el-tooltip class="item" effect="light" content="评论数" placement="bottom">
                <i class="el-icon-lx-comment color-grad padding-left10">{{item.comment}}</i>
              </el-tooltip>
            </div>
            <div style="width: 50%">
              <span class="blog-time color-grad">{{item.createDate}}</span>
            </div>
          </el-row>
        </div>
      </div>
    </el-card>
    <div class="pagination">
      <el-pagination background layout="total, prev, pager, next" :current-page="query.pageNum"
                     :page-size="query.pageSize" :total="query.pageTotal" @current-change="handlePageChange">
      </el-pagination>
    </div>
  </div>
  <div v-if="blogList == null || blogList.length == 0 " class="no-blog">
    没有博客信息
  </div>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import utils from "../../../utils/utils";
import request from "../../../utils/request";

export default {
  name: "blogList",
  setup() {
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    //博主id
    const authorId = utils.getUrlParam("id");
    document.title = document.title.replace('{0}',authorId);//设置浏览器标题
    //查询条件
    const query = reactive({
      listTitle: "最新博客",
      userId: authorId,
      tagId: 0,
      groupId: 0,
      pageNum: 1,
      pageSize: 10,
      pageTotal: 1
    });
    // 博客列表数据
    let blogList = ref([]);
    const getBlogList = () => {
      if(query.userId){
        axios.$http.post(request.bloList,query).then(function (res) {
          blogList.value = res.data;
          query.pageTotal = res.totalNum;
        });
      }
    }
    getBlogList();
    // 获取用户数据
    const showBlogDetail = (blogId) => {
      ElMessage.success(blogId);
    };
    // 分页导航
    const handlePageChange = (val) => {
      query.pageNum = val;
      getBlogList();
    };
    // 获取博主博客分组groupId的列表
    const findBlogGroup = (groupId,title) => {
      query.listTitle = title;
      query.groupId = groupId;
      query.tagId = 0;
      query.pageNum = 1;
      getBlogList();
    };
    // 获取博主博客分类tagId的列表
    const findBlogTag = (tagId,title) => {
      query.listTitle = title;
      query.groupId = 0;
      query.tagId = tagId;
      query.pageNum = 1;
      getBlogList();
    };
    return { query,blogList,showBlogDetail,handlePageChange,findBlogGroup,findBlogTag};
  }
};
</script>

<style scoped>
.span-title{
  font-weight: bold;
}
.image{
  float: right;
  width: 160px;
  height: 100px;
  border-radius: 8%;
  margin-bottom: 20px;
  margin-left: 5px;
}
.blog-detail{
  width: 100%;
}
.blog-title{
  font-size: 20px;
  font-width: blod;
  color: black;
}
.el-row{
  margin-top: 5px;
  margin-bottom: 5px;
}
.image{
  float: right;
  width: 160px;
  height: 120px;
  border-radius: 8%;
  margin-top: 30px;
  margin-left: 5px;
}
.blog-time{
  float: right;
  margin-right: 5px;
}
.color-grad{
  color: #999697;
}
.padding-left10{
  padding-left: 10px;
}
.no-blog{
  text-align: center;
  margin-top: 10px;
  color: #999697;
}
.ellipsis-line1{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
.ellipsis-line4{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 4; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
</style>
