<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-menu"></i> 菜单管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 用户信息列表-->
    <div class="container">
      <div class="handle-box">
        <el-select v-model="query.menuType" placeholder="菜单类型" class="handle-select mr10">
          <el-option key="1" label="页面菜单" value="1"></el-option>
          <el-option key="2" label="请求路径" value="2"></el-option>
        </el-select>
        <el-select v-model="query.roleName" placeholder="角色名称" class="handle-select mr10">
          <el-option v-for="item in rolesArr" :key="item.roleName" :label="item.description" :value="item.roleName"></el-option>
        </el-select>
        <el-input v-model="query.menuCode" placeholder="菜单编码" class="handle-input mr10"></el-input>
        <el-input v-model="query.menuUrl" placeholder="菜单路径" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        <el-button type="primary" icon="el-icon-refresh-left" @click="handleReset">重置</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增菜单</el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="menuId" label="菜单ID" align="center"></el-table-column>
        <el-table-column prop="parentId" label="父菜单ID" align="center"></el-table-column>
        <el-table-column prop="menuCode" label="菜单编码" align="center"></el-table-column>
        <el-table-column prop="menuName" label="菜单名称" align="center"></el-table-column>
        <el-table-column prop="menuIcon" label="菜单图标" align="center">
          <template #default="scope">
            <i :class="scope.row.menuIcon"></i>
          </template>
        </el-table-column>
        <el-table-column prop="menuUrl" label="菜单路径" align="center"></el-table-column>
        <el-table-column prop="menuOrder" label="菜单序号" align="center"></el-table-column>
        <el-table-column prop="menuType" label="菜单类型" align="center">
          <template #default="scope">
            {{ scope.row.menuType === '1' ? '页面菜单' : '请求路径'}}
          </template>
        </el-table-column>
        <el-table-column prop="roleName" label="权限角色" align="center"></el-table-column>
        <el-table-column prop="isDelete" label="是否有效" align="center">
          <template #default="scope">
            {{ scope.row.isDelete === '1' ? '否' : '是'}}
          </template>
        </el-table-column>
        <el-table-column prop="sysCreateTime" label="注册时间" align="center"></el-table-column>
        <el-table-column prop="sysUpdateTime" label="更新时间" align="center"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
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
    </div>
    <!-- 新增/编辑弹出框 -->
    <el-dialog :title="editVisible ? '编辑菜单' :'新增菜单'" v-model="dialogVisible" width="20%">
      <el-form label-width="120px" :model="form" :rules="editRules" ref="editForm">
        <el-form-item label="菜单ID：" prop="menuId"> {{ form.menuId }} </el-form-item>
        <el-form-item label="父菜单ID：" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父菜单ID" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="菜单编码：" prop="menuCode">
          <el-input v-model="form.menuCode" maxlength="40" placeholder="请输入菜单编码" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="权限角色：" prop="roleName">
          <el-select v-model="form.roleArr" placeholder="请选择角色" multiple class="handle-select mr10" style="width: 250px">
            <el-option v-for="item in rolesArr" :key="item.roleName" :label="item.description" :value="item.roleName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单名称：" prop="menuName">
          <el-input v-model="form.menuName" maxlength="100" placeholder="请输入菜单名称" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="菜单图标：" prop="menuIcon">
          <el-input v-model="form.menuIcon" maxlength="100" placeholder="请输入ElementUI图标名称" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="菜单路径：" prop="menuUrl">
          <el-input v-model="form.menuUrl" maxlength="256" placeholder="请输入菜单路径" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="菜单序号：" prop="menuOrder">
          <el-input v-model="form.menuOrder" placeholder="请输入菜单序号" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="菜单类型：" prop="menuType">
          <el-radio v-model="form.menuType" label="1">页面菜单</el-radio>
          <el-radio v-model="form.menuType" label="2">请求路径</el-radio>
        </el-form-item>
        <el-form-item label="是否有效：" :prop="editVisible ? isDelete : ''" v-show="editVisible">
          <el-radio v-model="form.isDelete" label="1">否</el-radio>
          <el-radio v-model="form.isDelete" label="0">是</el-radio>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveSure">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, getCurrentInstance } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";

export default {
  name: "menuList",
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 查询条件
    const query = reactive({
      menuType: "",
      roleName: "",
      menuCode: "",
      menuUrl: "",
      pageNum: 1,
      pageSize: 10,
      pageTotal : 0
    });
    // 菜单表单提交数据
    let form = reactive({
      menuId: "",
      parentId : "",
      menuCode : "",
      menuName : "",
      menuIcon : "",
      menuUrl : "",
      menuOrder : "",
      menuType : "",
      roleArr : [],
      roleName : "",
      isDelete: ""
    });
    // 菜单的规则校验
    const editRules = {
      parentId : [
        { type: 'number', message: '只能输入数字', trigger: 'blur',
          transform (value) {
            if (value){
              if (/[^0-9]/.test(value)) {
                return true
              }else{
              }
            }
          }
        }
      ],
      menuCode : [
        { required: true, message: "菜单编码不能为空", trigger: "blur" }
      ],
      menuUrl : [
        { required: true, message: "菜单路径不能为空", trigger: "blur" }
      ],
      menuOrder : [
        { type: 'number', message: '只能输入数字', trigger: 'blur',
          transform (value) {
            if (value){
              if (/[^0-9]/.test(value)) {
                return true
              }else{
              }
            }
          }
        }
      ],
      menuType : [
        { required: true, message: "菜单类型不能为空", trigger: "blur" }
      ],
      isDelete : [
        { required: true, message: "是否有效不能为空", trigger: "blur" }
      ]
    };
    // 角色列表
    const rolesArr = ref([]);
    // 弹窗编辑或新增标志
    const editVisible = ref(false);
    // 弹窗显示控制标志
    const dialogVisible = ref(false);
    // 表单校验
    const editForm = ref(null);
    // 查询所有角色
    const getRoleList = () => {
      request.$http.get("api/admin/user/role",null).then(function (res) {
        if(res.code === 200){
          rolesArr.value = res.data;
        }
      });
    };
    getRoleList();
    // 表格数据
    const tableData = ref([]);
    // 获取表格数据
    const getData = () => {
      request.$http.get("api/admin/menu/all",query).then(function (res) {
        if(res.code === 200){
          tableData.value = res.data;
          query.pageTotal = res.totalNum;
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
      query.menuType = "";
      query.roleName = "";
      query.menuCode = "";
      query.menuUrl = "";
      getData();
    };
    // 新增菜单
    const handleAdd = () => {
      dialogVisible.value =true;
      editVisible.value = false;
      form.menuId= "";
      form.parentId = "";
      form.menuCode = "";
      form.menuName = "";
      form.menuIcon = "";
      form.menuUrl = "";
      form.menuOrder = "";
      form.menuType = "";
      form.roleArr = [];
      form.isDelete = "";
    };
    // 分页导航
    const handlePageChange = (val) => {
      query.pageNum = val;
      getData();
    };
    //编辑
    const handleEdit = (row) => {
      dialogVisible.value =true;
      editVisible.value = true;
      form.menuId= row.menuId;
      form.parentId = row.parentId;
      form.menuCode = row.menuCode;
      form.menuName = row.menuName;
      form.menuIcon = row.menuIcon;
      form.menuUrl = row.menuUrl;
      form.menuOrder = row.menuOrder;
      form.menuType = row.menuType;
      form.roleArr = [];
      if(row.roleName){
        let roles = row.roleName.split(",");
        rolesArr.value.forEach (temp => {
            if(roles.indexOf(temp.description) !== -1 ){
              form.roleArr.push(temp.roleName);
            }
        });
      }
      form.isDelete = row.isDelete;
    };
    // 保存按钮
    const saveSure = () => {
      editForm.value.validate((valid) => {
        if (valid) {
          form.roleArr.forEach (temp => {
            form.roleName += temp + ",";
          });
          form.roleName = form.roleName ? form.roleName.substring(0,form.roleName.length-1) : "";
          request.$http.post("api/admin/menu/edit", form).then(function (res) {
            if(res.code === 200){
              dialogVisible.value = false;
              ElMessage.success('保存成功');
              getData();
            }else {
              ElMessage.error(res.data);
            }
          });
        } else {
          return false;
        }
      });
    };
    return { query,tableData,editRules,form,rolesArr,editVisible,dialogVisible,editForm,
      handleSearch,handleReset,handleEdit,handlePageChange,saveSure,handleAdd};
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
