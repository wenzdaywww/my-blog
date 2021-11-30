<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 用户信息
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-select v-model="query.stateCd" placeholder="用户状态" class="handle-select mr10">
                    <el-option key="1" label="有效" value="1"></el-option>
                    <el-option key="2" label="注销" value="2"></el-option>
                    <el-option key="3" label="封号" value="3"></el-option>
                </el-select>
                <el-input v-model="query.userId" placeholder="用户ID" class="handle-input mr10"></el-input>
                <el-input v-model="query.userName" placeholder="用户名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-refresh-left" @click="handleReset">重置</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="suId" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="userId" label="用户ID"></el-table-column>
                <el-table-column prop="userName" label="用户名称"></el-table-column>
                <el-table-column label="头像(查看大图)" align="center">
                    <template #default="scope">
                        <el-image class="table-td-thumb" :src="scope.row.photo" :preview-src-list="[scope.row.photo]">
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="phoneNum" label="手机号码"></el-table-column>
                <el-table-column prop="email" label="邮箱"></el-table-column>
                <el-table-column prop="birthday" label="出生日期"></el-table-column>
                <el-table-column prop="sex" label="性别">
                  <template #default="scope">
                    {{ scope.row.sex === '1' ? '男' : scope.row.sex === '0' ? '女' : '未知' }}
                  </template>
                </el-table-column>
                <el-table-column label="用户状态" align="center">
                    <template #default="scope">
                        {{ scope.row.stateCd === '1' ? '有效' : scope.row.stateCd === '2' ? '注销' : '封号' }}
                    </template>
                </el-table-column>
                <el-table-column label="是否过期" align="center">
                  <template #default="scope">
                    {{ scope.row.notExpired === '1' ? '否' : '是' }}
                  </template>
                </el-table-column>
                <el-table-column label="账号是否锁定" align="center">
                  <template #default="scope">
                    {{ scope.row.notLocked === '1' ? '否' : '是' }}
                  </template>
                </el-table-column>
                <el-table-column label="密码是否过期" align="center">
                  <template #default="scope">
                    {{ scope.row.credentialsNotExpired === '1' ? '否' : '是' }}
                  </template>
                </el-table-column>
                <el-table-column prop="sysCreateTime" label="注册时间"></el-table-column>
                <el-table-column prop="sysUpdateTime" label="更新时间"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                  <template #default="scope">
                    <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑 </el-button>
                    <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                  </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageNum"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ref, reactive, getCurrentInstance } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";

export default {
    name: "basetable",
    setup() {
        const query = reactive({
            stateCd: "",
            userId: "",
            userName: "",
            pageNum: 1,
            pageSize: 10,
        });
        const tableData = ref([]);
        const pageTotal = ref(0);
        const request = getCurrentInstance().appContext.config.globalProperties;
        // 获取表格数据
        const getData = () => {
            request.$http.get("/admin/user/all",query).then(function (res) {
              if(res.code === 200){
                tableData.value = res.data;
                pageTotal.value = res.totalNum;
              }
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
          query.stateCd = "";
          query.userId = "";
          query.userName = "";
          getData();
        };
        // 分页导航
        const handlePageChange = (val) => {
            query.pageNum = val;
            getData();
        };
        // 删除操作
        const handleDelete = (index) => {
            // 二次确认删除
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            })
                .then(() => {
                    ElMessage.success("删除成功");
                    tableData.value.splice(index, 1);
                })
                .catch(() => {});
        };

        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        let form = reactive({
            name: "",
            address: "",
        });
        let idx = -1;
        const handleEdit = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            editVisible.value = true;
        };
        const saveEdit = () => {
            editVisible.value = false;
            ElMessage.success(`修改第 ${idx + 1} 行成功`);
            Object.keys(form).forEach((item) => {
                tableData.value[idx][item] = form[item];
            });
        };

        return {
            query,
            tableData,
            pageTotal,
            editVisible,
            form,
            handleSearch,
            handleReset,
            handlePageChange,
            handleDelete,
            handleEdit,
            saveEdit,
        };
    },
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 200px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
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
</style>
