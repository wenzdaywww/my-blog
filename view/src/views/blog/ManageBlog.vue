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
              <el-option v-for="item in userTagArr" :key="item.tagId" :label="item.tagName" :value="item.tagId"></el-option>
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
    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" v-model="editVisible" width="500px">
      <el-form label-width="120px">
        <el-form-item label="博客ID：">
          <el-input v-model="form.blogId" :disabled="true" class="dialog-width"></el-input>
        </el-form-item>
        <el-form-item label="标题：">
          <el-input v-model="form.title" :disabled="true" class="dialog-width"></el-input>
        </el-form-item>
        <el-form-item label="分组：">
          <el-select v-model="form.groupId" placeholder="分组" class="mr10 dialog-width">
            <el-option v-for="item in groupArr" :key="item.groupId" :label="item.groupName" :value="item.groupId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标签：">
          <el-select v-model="form.tagIds" placeholder="标签" multiple class="mr10 dialog-width">
            <el-option v-for="item in allTagArr" :key="item.tagId" :label="item.tagName" :value="item.tagId"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="saveEdit">确 定</el-button>
          <el-button @click="editVisible = false">取 消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import request from "../../utils/request";
import {ElMessage} from "element-plus";

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
    // 表单数据
    let form = reactive({
      blogId: "",
      title:"",
      tagIds : [],
      groupId : ""
    });
    // 博客标签数据
    const userTagArr = ref([]);
    // 所有标签数据
    const allTagArr = ref([]);
    // 博客分组数据
    const groupArr = ref([]);
    // 博客列表数据
    const blogList = ref([]);
    // 表格编辑时弹窗和保存
    const editVisible = ref(false);
    // 获取博客分组
    const getBlogGroup = () => {
      axios.$http.post(request.groupList,null).then(function (res) {
        if(res.code === 200){
          groupArr.value = res.data;
          groupArr.value.push({groupId:-1,groupName:"<不分组>"});
        }
      });
    }
    getBlogGroup();
    // 获取博客标签
    const getBlogTags = () => {
      axios.$http.get(request.userTags,null).then(function (res) {
        if(res.code === 200){
          userTagArr.value = res.data;
        }
      });
    }
    getBlogTags();
    // 获取所有标签
    const getAllTags = () => {
      axios.$http.post(request.tagList,null).then(function (res) {
        if(res.code === 200){
          allTagArr.value = res.data;
        }
      });
    }
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
      getAllTags();
      axios.$http.get(request.blogTagGroup+row.blogId,null).then(function (res) {
        if(res.code === 200){
          editVisible.value = true;
          form.blogId = res.data.blogId;
          form.title = res.data.title;
          form.groupId = res.data.groupId;
          form.tagIds = res.data.tagIds;
        }
      });
    };
    // 编辑页面的保存按钮
    const saveEdit = () => {
      form.groupId = form.groupId == -1 ? "" : form.groupId;
      axios.$http.post(request.updateTagGroup,form).then(function (res) {
        if(res.code === 200){
          editVisible.value = false;
          findBlogList();
          ElMessage.success('修改成功');
        }else {
          ElMessage.error('修改失败');
        }
      })
    };
    return {query,userTagArr,allTagArr,groupArr,form,editVisible,blogList,handleSearch,handleReset,handlePageChange,handleEdit,saveEdit}
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
.dialog-width{
  width: 100%;
}
</style>