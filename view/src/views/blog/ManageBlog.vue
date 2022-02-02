<!-- 博客管理 -->
<template>
  <div>
    <div class="crumb-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-menu"></i> 博客管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <div class="handle-box">
        <el-row>
          <el-col :span="4">
            <el-select v-model="query.tagId" placeholder="标签" class="handle-select mr10">
              <el-option v-for="item in tagArr" :key="item.tagId" :label="item.tagName" :value="item.tagId"></el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            <el-select v-model="query.groupId" placeholder="分组" class="handle-select mr10">
              <el-option v-for="item in groupArr" :key="item.groupId" :label="item.groupName" :value="item.groupId"></el-option>
            </el-select>
          </el-col>
          <el-col :span="16">
            <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            <el-button type="primary" icon="el-icon-refresh-left" @click="handleReset">重置</el-button>
          </el-col>
        </el-row>
      </div>
      <el-table :data="blogList" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column label="标题" align="center">
          <template #default="scope">
            <el-tooltip class="item" effect="light" :content="scope.row.title" placement="bottom">
              <el-link :href="scope.row.blogId ? '/article?bid=' + scope.row.blogId : '#'" target="_blank" type="primary" class="ellipsis-line1">{{scope.row.title}}</el-link>
            </el-tooltip>
            </template>
        </el-table-column>
        <el-table-column label="摘要" align="center">
          <template #default="scope">
            <el-tooltip class="item" effect="light" :content="scope.row.summary" placement="bottom">
              <span class="ellipsis-line1">{{scope.row.summary}}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="封面" align="center">
          <template #default="scope">
            <el-image class="table-td-thumb" :src=" scope.row.coverImg ? scope.row.coverImg : ''" :preview-src-list="[scope.row.coverImg]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column label="分组" align="center">
          <template #default="scope">
            <el-tooltip class="item" effect="light" :content="scope.row.groupName" placement="bottom">
              <span class="ellipsis-line1">{{scope.row.groupName}}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="标签" align="center">
          <template #default="scope">
            <el-tooltip class="item" effect="light" :content="scope.row.tagName" placement="bottom">
              <span class="ellipsis-line1">{{scope.row.tagName}}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="browse" label="浏览量" align="center"></el-table-column>
        <el-table-column prop="praise" label="点赞数" align="center"></el-table-column>
        <el-table-column prop="comment" label="评论数" align="center"></el-table-column>
        <el-table-column prop="collect" label="收藏数" align="center"></el-table-column>
        <el-table-column prop="createTime" label="发布时间" align="center"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑 </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background layout="total, prev, pager, next" :current-page="query.pageNum"
                       :page-size="query.pageSize" :total="query.pageTotal" @current-change="handlePageChange">
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import request from "../../utils/request";

export default {
  name: "ManageBlog",
  setup(){
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    // 查询条件
    const query = reactive({
      tagId:"",
      groupId:"",
      pageNum: 1,
      pageSize: 10,
      pageTotal: 0
    });
    // 博客标签数据
    const tagArr = ref([]);
    // 博客分组数据
    const groupArr = ref([]);
    // 博客列表数据
    const blogList = ref([]);
    // 获取博客分组
    const getBlogGroup = () => {
      axios.$http.post(request.groupList,null).then(function (res) {
        if(res.code === 200){
          groupArr.value = res.data;
          groupArr.value.push({groupId:-1,groupName:"<未分组>"});
        }
      });
    }
    getBlogGroup();
    // 获取博客标签
    const getBlogTags = () => {
      axios.$http.get(request.userTags,null).then(function (res) {
        if(res.code === 200){
          tagArr.value = res.data;
        }
      });
    }
    getBlogTags();
    // 获取表格数据
    const findBlogList = () => {
      axios.$http.post(request.manageBlog,query).then(function (res) {
        if(res.code === 200){
          blogList.value = res.data;
          query.pageTotal = res.totalNum;
        }
      })
    };
    findBlogList();
    // 查询操作
    const handleSearch = () => {
      query.pageNum = 1;
      findBlogList();
    };
    // 重置
    const handleReset = () => {
      query.tagId = "";
      query.groupId = "";
      findBlogList();
    };
    // 分页导航
    const handlePageChange = (val) => {
      query.pageNum = val;
      findBlogList();
    };
    //编辑
    const handleEdit = (row) => {
    };
    return {query,tagArr,groupArr,blogList,handleSearch,handleReset,handlePageChange,handleEdit}
  }
}
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}
.table {
  width: 100%;
  font-size: 14px;
}
.mr10 {
  margin-right: 10px;
}
.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
.ellipsis-line1{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
</style>