<!-- 我的收藏 -->
<template>
  <div>
    <div class="crumb-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-favorfill"></i> 我的收藏({{query.pageTotal}})篇
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <div class="handle-box">
        <el-row>
          <el-col :span="4">
            <el-select v-model="query.cgId" placeholder="分组" class="handle-select mr10">
              <el-option v-for="item in collectArr" :key="item.cgId" :label="item.collectName" :value="item.cgId"></el-option>
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
        <el-table-column label="收藏夹" align="center">
          <template #default="scope">
            <el-tooltip class="item" effect="light" :content="scope.row.collectName" placement="bottom">
              <span class="ellipsis-line1">{{scope.row.collectName}}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="browse" label="浏览量" align="center"></el-table-column>
        <el-table-column prop="praise" label="点赞数" align="center"></el-table-column>
        <el-table-column prop="comment" label="评论数" align="center"></el-table-column>
        <el-table-column prop="createDate" label="发布时间" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row.blogId)">编辑 </el-button>
            <i class="el-icon-lx-favorfill color-red">
              <el-link href="javascript:void(0);" type="danger" class="item-collect" @click="addCollect(scope.row.blogId)">取消收藏</el-link>
            </i>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background layout="total, prev, pager, next" :current-page="query.pageNum"
                       :page-size="query.pageSize" :total="query.pageTotal" @current-change="handlePageChange">
        </el-pagination>
      </div>
    </el-card>
    <!-- 收藏夹弹出窗 -->
    <collect-group ref="collectDialog" @findBlogList="findBlogList"/>
  </div>
</template>

<script>
import {getCurrentInstance, provide, reactive, ref} from "vue";
import request from "../../utils/request";
import collectGroup from "./module/CollectGroup.vue";
import {ElMessage} from "element-plus";

export default {
  name: "Collect",
  components: { collectGroup },
  setup(){
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    //收藏夹弹出窗对象
    const collectDialog = ref();
    // 查询条件
    const query = reactive({
      cgId:"",
      pageNum: 1,
      pageSize: 10,
      pageTotal: 0
    });
    // 博客分组数据
    const collectArr = ref([]);
    // 博客列表数据
    const blogList = ref([]);
    //查询收藏夹
    const findCollectGroup = () => {
      axios.$http.get(request.collectGroup,null).then(function (res) {
        collectArr.value = res.data;
      });
    }
    findCollectGroup();
    // 获取表格数据
    const findBlogList = () => {
      axios.$http.get(request.collectList,query).then(function (res) {
        blogList.value = res.data;
        query.pageTotal = res.totalNum;
      })
    };
    findBlogList();
    //取消收藏博客
    const addCollect = (blogId) => {
      axios.$http.post(request.addCollect, {bid:blogId}).then(function (res) {
        findBlogList();
        ElMessage.success('取消收藏成功');
      });
    }
    // 查询操作
    const handleSearch = () => {
      query.pageNum = 1;
      findBlogList();
    };
    // 重置
    const handleReset = () => {
      query.cgId = "";
      findBlogList();
    };
    // 分页导航
    const handlePageChange = (val) => {
      query.pageNum = val;
      findBlogList();
    };
    // 编辑按钮
    const handleEdit = (val) => {
      collectDialog.value.openCollectDialog(val,true);//调用子组件方法
    };
    return {query,collectArr,collectDialog,blogList,handleSearch,handleReset,handlePageChange,handleEdit,addCollect,findBlogList}
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
.ellipsis-line1{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
.item-collect{
  font-size: 13px;
  margin-bottom: 3px;
}
.color-red{
  color: red;
  margin-left: 5px;
}
.collect-div{
  height: 30px;
  width: 100%;
  line-height:30px;
}
.collect-div:hover {
  background-color: #f0f0f0;
}
.collect-name{
  width: 90%;
}
.collect-select{
  height: 30px;
  line-height:30px;
  float: right;
}
.footer-btn{
   margin-top: 20px;
   text-align: center;
}
.mag-top20{
  margin-top: 20px;
}
.mag-left10{
  margin-left: 10px;
}
</style>