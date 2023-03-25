<!-- 用户列表 -->
<template>
  <div>
    <div class="crumb-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-data-analysis"></i> 监控信息
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <div>
        <div class="handle-box">
          <el-input v-model="query.name" placeholder="监控名称" class="handle-input mr10"></el-input>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
          <el-button type="primary" icon="el-icon-refresh-left" @click="handleReset">重置</el-button>
        </div>
        <!-- 监控列表-->
        <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
          <el-table-column prop="monitorId" label="监控ID" align="center"></el-table-column>
          <el-table-column prop="monitorName" label="监控名称" align="center"></el-table-column>
          <el-table-column prop="monitorAddr" label="监控地址" align="center"></el-table-column>
          <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>
          <el-table-column prop="updateTime" label="更新时间" align="center"></el-table-column>
          <el-table-column label="操作" width="180" align="center">
            <template #default="scope">
              <el-button type="text" icon="el-icon-view" @click="handleView(scope.row)">查看 </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination background layout="total, prev, pager, next" :current-page="query.pageNum"
                         :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange">
          </el-pagination>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { ref, reactive, getCurrentInstance } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import request from '../../utils/request';

export default {
  name: "Monitor",
  setup() {
    // 查询条件
    const query = reactive({
      name: "",
      pageNum: 1,
      pageSize: 10
    });
    // 表格数据
    const tableData = ref([]);
    // 页数
    const pageTotal = ref(0);
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    // 获取表格数据
    const getData = () => {
      axios.$http.get(request.monitorList,query).then(function (res) {
        tableData.value = res.data;
        pageTotal.value = res.totalNum;
      })
    };
    getData();
    // 查询操作
    const handleSearch = () => {
      query.pageNum = 1;
      getData();
    };
    // 重置
    const handleReset = () => {
      query.name = "";
      getData();
    };
    // 分页导航
    const handlePageChange = (val) => {
      query.pageNum = val;
      getData();
    };
    /** 查看 **/
    const handleView = (row) => {
      window.open(row.monitorAddr, '_blank');
    };
    return { query,tableData,pageTotal, handleSearch,handleReset,handlePageChange,handleView};
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}
.handle-input {
  width: 200px;
  display: inline-block;
}
.table {
  width: 100%;
  font-size: 14px;
}
.mr10 {
  margin-right: 10px;
}
</style>
