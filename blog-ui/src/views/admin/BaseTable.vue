<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-s-custom"></i> 用户信息
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 用户信息列表-->
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
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增用户</el-button>
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
            {{ scope.row.stateCd === '1' ? '有效' : (scope.row.stateCd === '2' ? '注销' : '封号') }}
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
            <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑 </el-button>
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
    <el-dialog title="编辑" v-model="editVisible" width="20%">
      <el-form label-width="120px">
        <el-form-item label="用户ID">
          <el-input v-model="form.userId" :disabled="true" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="用户名称">
          <el-input v-model="form.userName" :disabled="true" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="用户状态">
          <el-select v-model="form.stateCd" placeholder="用户状态" class="handle-select mr10" style="width: 250px">
            <el-option key="1" label="有效" value="1"></el-option>
            <el-option key="2" label="注销" value="2"></el-option>
            <el-option key="3" label="封号" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否过期">
          <el-radio v-model="form.expired" label="1">否</el-radio>
          <el-radio v-model="form.expired" label="0">是</el-radio>
        </el-form-item>
        <el-form-item label="账号是否锁定">
          <el-radio v-model="form.locked" label="1">否</el-radio>
          <el-radio v-model="form.locked" label="0">是</el-radio>
        </el-form-item>
        <el-form-item label="密码是否过期">
          <el-radio v-model="form.credentials" label="1">否</el-radio>
          <el-radio v-model="form.credentials" label="0">是</el-radio>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveEdit">确 定</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 新增用户弹出框 -->
    <el-dialog title="新增用户" v-model="addVisible" width="20%">
      <el-form label-width="120px" :model="form" :rules="addRules" ref="addForm">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" maxlength="40" placeholder="请输入用户ID" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="form.userName" maxlength="100" placeholder="请输入用户名称" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="passWord">
          <el-input v-model="form.passWord" type="passWord" maxlength="20" placeholder="请输入密码" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="roleName">
          <el-select v-model="form.roleName" placeholder="角色" class="handle-select mr10" style="width: 250px">
            <el-option v-for="item in rolesArr" :key="item.roleName" :label="item.description" :value="item.roleName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="1">男</el-radio>
          <el-radio v-model="form.sex" label="0">女</el-radio>
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNum">
          <el-input v-model="form.phoneNum" maxlength="11" placeholder="请输入手机号码"
                    οninput="value=value.replace(/[^\d]/g,'');if(value.length > 11)value = value.slice(0, 11)" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker v-model="form.birthday" type="date" style="width: 250px"
                          value-format="YYYY-MM-DD" format="YYYY年MM月DD日" placeholder="请选择出生日期" >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="邮箱" prop="eMail">
          <el-input v-model="form.eMail" maxlength="100" placeholder="请输入邮箱地址" style="width: 250px"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveAdd">确 定</el-button>
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
    // 查询条件
    const query = reactive({
      stateCd: "",
      userId: "",
      userName: "",
      pageNum: 1,
      pageSize: 10
    });
    // 角色列表
    const rolesArr = ref([]);
    // 新增用户的规则校验
    const addRules = {
      userId : [
        { required: true, message: "用户ID不能为空", trigger: "blur" },
        { type: 'string', message: '只能输入字母和数字', trigger: 'blur',
          transform (value) {
            if (value){
              if (/[^A-Za-z0-9]/.test(value)) {
                return true
              }else{
              }
            }
          }
        }
      ],
      userName : [
        { required: true, message: "用户名称不能为空", trigger: "blur" }
      ],
      passWord : [
        { required: true, message: "密码不能为空", trigger: "blur" }
      ],
      roleName : [
        { required: true, message: "角色不能为空", trigger: "blur" }
      ],
      phoneNum: [
        { min: 11, message: "手机号格式不正确", trigger: "blur" },
        { type: 'number', message: '手机号格式不正确', trigger: 'blur',
          transform (value) {
            if(value){
              var phonereg = 11 && /^((13|14|15|16|17|18|19)[0-9]{1}\d{8})$/
              if (!phonereg.test(value)) {
                return false
              }else{
                return Number(value)
              }
            }
          }
        }
      ],
      eMail: [
        { type: 'string', message: '长度不能超过100位', trigger: 'blur', max: 100 },
        { type: 'string', message: '邮箱格式不正确', trigger: 'blur',
          transform (value) {
            if (value){
              if (!/^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(value)) {
                return true
              }else{
              }
            }
          }
        }
      ]
    };
    // 表单校验
    const addForm = ref(null);
    // 表格数据
    const tableData = ref([]);
    // 页数
    const pageTotal = ref(0);
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 获取表格数据
    const getData = () => {
      request.$http.get("api/admin/user/all",query).then(function (res) {
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
    // 表格编辑时弹窗和保存
    const editVisible = ref(false);
    // 新增用户弹出
    const addVisible = ref(false);
    // 表单数据
    let form = reactive({
      userId: "",
      userName : "",
      passWord : "",
      phoneNum : "",
      birthday : "",
      sex : "",
      photo : "",
      roleName : "",
      eMail : "",
      stateCd: "",
      expired : "",
      locked : "",
      credentials : ""
    });
    //编辑
    const handleEdit = (row) => {
      form.userId = row.userId;
      form.userName = row.userName;
      form.stateCd = row.stateCd;
      form.expired = row.notExpired;
      form.locked = row.notLocked;
      form.credentials = row.credentialsNotExpired;
      editVisible.value = true;
    };
    // 编辑页面的保存按钮
    const saveEdit = () => {
      request.$http.post("api/admin/user/state",form).then(function (res) {
        if(res.code === 200){
          editVisible.value = false;
          ElMessage.success('修改成功');
          getData();
        }else {
          ElMessage.error('修改失败');
        }
      })
    };
    //新增用户
    const handleAdd = () => {
      addVisible.value = true;
      form.userId =  "";
      form.userName = "";
      form.passWord = "";
      form.phoneNum = "";
      form.birthday = "";
      form.sex = "";
      form.photo = "";
      form.roleName = "";
      form.eMail = "";
      request.$http.get("api/admin/user/role",null).then(function (res) {
        if(res.code === 200){
          rolesArr.value = res.data;
        }
      })
    };
    // 新增页面的保存按钮
    const saveAdd = () => {
      addForm.value.validate((valid) => {
        if (valid) {
          request.$http.post("api/admin/user/new", form).then(function (res) {
            if(res.code === 200){
              addVisible.value = false;
              ElMessage.success('新增成功');
              getData();
            }else {
              ElMessage.error(res.data);
            }
          })
        } else {
          return false;
        }
      });
    };
    return { query,rolesArr,addRules,tableData,pageTotal,editVisible,addVisible,form,addForm,
      handleSearch,handleReset,handlePageChange,handleEdit,saveEdit,handleAdd,saveAdd
    };
  }
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
