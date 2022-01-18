<template>
  <el-card>
    <span class="span-title"><b>最新博客</b></span>
  </el-card>
  <el-card class="blog-card" v-for="item in blogList">
    <img :src="item.img" class="image" />
    <div class="blog-detail">
      <el-link class="blog-title" type="primary" @click="showBlogDetail(item.blogId)">{{item.title}}</el-link>
      <div class="bottom card-header">
        <el-row class="el-row">
          <span class="blog-article">{{item.content}}</span>
        </el-row>
        <el-row class="el-row">
          <div style="width: 20%;">
            <i class="el-icon-star-on color-grad">{{item.likeNum}}</i>
            <i class="el-icon-chat-dot-round color-grad" style="padding-left: 10px;">{{item.commentNum}}</i>
          </div>
          <div style="width: 80%">
            <span class="blog-time color-grad">{{item.createTime}}</span>
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
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
export default {
  name: "index",
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    const query = reactive({
      pageNum: 1,
      pageSize: 10,
      pageTotal: 1
    });
    // 博客列表数据
    let blogList = ref([
      {
        blogId: "1",
        img: "src/assets/img/img.jpg",
        title: "你真的理解什么是财富自由吗？",
        content: "正确做好任何一件事情的前提是清晰、正确的理解目标。而事实是，我们很多人很多时候根本没有对目标正确的定义，甚至根本从来没有想过，只是大家都那么做而已,正确做好任何一件事情的前提是清晰、正确的......",
        likeNum: 123,
        commentNum: 456,
        createTime: "2021-01-01 21:21:00"
      },
      {
        blogId: "2",
        img: "src/assets/img/img.jpg",
        title: "你真的理解什么是财富自由吗？",
        content: "正确做好任何一件事情的前提是清晰、正确的理解目标。而事实是，我们很多人很多时候根本没有对目标正确的定义，甚至根本从来没有想过，只是大家都那么做而已,正确做好任何一件事情的前提是清晰、正确的......",
        likeNum: 11,
        commentNum: 22,
        createTime: "2021-01-02 21:21:00"
      }
    ]);
    // 获取用户数据
    const showBlogDetail = (blogId) => {
      ElMessage.success(blogId);
    };
    // 分页导航
    const handlePageChange = (val) => {
      query.pageNum = val;
    };
    return { query,blogList,showBlogDetail,handlePageChange };
  },
};
</script>

<style scoped>
.blog-card{
  border-radius: 8px;
  margin-bottom: 3px;
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
}
.el-row{
  margin-top: 5px;
  margin-bottom: 5px;
}
.blog-article{
  color: #686264;
}
.blog-time{
  float: right;
  margin-right: 5px;
}
.color-grad{
  color: #999697;
}
.detail-left{
  width: 80%;
}
.detail-right{
  width: 20%;
}
</style>
