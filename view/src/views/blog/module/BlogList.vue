<template>
  <el-card>
    <span class="span-title">{{query.title}}</span>
  </el-card>
  <div v-if="blogList.length > 0">
    <el-card class="blog-card" v-for="item in blogList">
      <div class="blog-detail">
        <el-link :href="item.blogId ? '/article?id=' + item.blogId : '#'" class="blog-title"
                 target="_blank" type="primary" @click="showBlogDetail(item.blogId)">{{item.blogTheme}}</el-link>
        <div class="bottom card-header">
          <el-row class="el-row">
            <div>
              {{item.blogContent}}
            </div>
          </el-row>
          <el-row class="el-row">
            <div style="width: 50%;">
              <i class="el-icon-view color-grad">{{item.blogView}}</i>
              <i class="el-icon-star-on color-grad padding-left10">{{item.blogLike}}</i>
              <i class="el-icon-chat-dot-round color-grad padding-left10">{{item.blogComment}}</i>
            </div>
            <div style="width: 50%">
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
  </div>
  <div v-if="blogList.length == 0 " class="no-blog">
    没有博客信息
  </div>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import utils from "../../../utils/utils";
export default {
  name: "blogList",
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    //博主id
    const authorId = utils.getUrlParam("id");
    //查询条件
    const query = reactive({
      title: "最新博客",
      userId: authorId,
      classId: 0,
      bgId: 0,
      pageNum: 1,
      pageSize: 10,
      pageTotal: 1
    });
    // 博客列表数据
    let blogList = ref([]);
    const getBlogList = () => {
      if(query.userId){
        request.$http.post("api/blog/browse/list/",query).then(function (res) {
          if(res.code === 200){
            blogList.value = res.data;
            query.pageTotal = res.totalNum;
          }
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
      query.title = title;
      query.bgId = groupId;
      query.classId = 0;
      query.pageNum = 1;
      getBlogList();
    };
    // 获取博主博客分类classId的列表
    const findBlogClass = (classId,title) => {
      query.title = title;
      query.bgId = 0;
      query.classId = classId;
      query.pageNum = 1;
      getBlogList();
    };
    return { query,blogList,showBlogDetail,handlePageChange,findBlogGroup,findBlogClass};
  }
};
</script>

<style scoped>
.span-title{
  font-weight: bold;
}
.blog-card{
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
.padding-left10{
  padding-left: 10px;
}
.no-blog{
  text-align: center;
  margin-top: 10px;
}
</style>
