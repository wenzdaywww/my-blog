<!-- 菜单管理 -->
<template>
  <div>
    <div class="crumb-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-menu"></i> 菜单管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <!-- 用户信息列表-->
      <div>
        <div class="handle-box">
          <el-select v-model="query.menuType" placeholder="菜单类型" class="handle-select mr10">
            <el-option v-for="item in menuArr" :key="item.value" :label="item.name" :value="item.value"></el-option>
          </el-select>
          <el-select v-model="query.roleCode" placeholder="角色名称" class="handle-select mr10">
            <el-option v-for="item in rolesArr" :key="item.roleCode" :label="item.roleName" :value="item.roleCode"></el-option>
          </el-select>
          <el-input v-model="query.menuCode" placeholder="菜单编码" class="handle-input mr10"></el-input>
          <el-input v-model="query.menuUrl" placeholder="菜单路径" class="handle-input mr10"></el-input>
          <el-input v-model="query.vuePath" placeholder="vue页面路径" class="handle-input mr10"></el-input>
          <el-input v-model="query.module" placeholder="菜单归属模块" class="handle-input mr10"></el-input>
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
          <el-table-column prop="vuePath" label="vue页面路径" align="center"></el-table-column>
          <el-table-column prop="menuOrder" label="菜单序号" align="center"></el-table-column>
          <el-table-column prop="menuType" label="菜单类型" align="center" width="120px">
            <template #default="scope">
              <el-select v-model="scope.row.menuType" disabled size="mini">
                <el-option v-for="item in menuArr" :key="item.value" :label="item.name" :value="item.value"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="module" label="菜单归属模块" align="center"></el-table-column>
          <el-table-column prop="roleCode" label="权限角色" align="center"></el-table-column>
          <el-table-column prop="isDelete" label="是否未失效" align="center">
            <template #default="scope">
              <el-select v-model="scope.row.isDelete" disabled size="mini">
                <el-option v-for="item in yesNoArr" :key="item.value" :label="item.name" :value="item.value"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="注册时间" align="center"></el-table-column>
          <el-table-column prop="updateTime" label="更新时间" align="center"></el-table-column>
          <el-table-column label="操作" width="180" align="center">
            <template #default="scope">
              <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑 </el-button>
              <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.row)">删除 </el-button>
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
      <el-dialog :title="editVisible ? '编辑菜单' :'新增菜单'" v-model="dialogVisible" width="50%">
        <el-form label-width="120px" :model="form" :rules="editRules" ref="editForm">
          <el-row>
            <el-col :span="12">
              <el-form-item label="菜单ID：" prop="menuId">
                <el-input v-model="form.menuId" class="el-input-custom" disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="父菜单ID：" prop="parentId">
                <el-input v-model="form.parentId" placeholder="请输入父菜单ID" class="el-input-custom"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="菜单编码：" prop="menuCode">
                <el-input v-model="form.menuCode" maxlength="40" placeholder="请输入菜单编码" class="el-input-custom"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="权限角色：" prop="roleCode">
                <el-select v-model="form.roleArr" placeholder="请选择角色" multiple class="handle-select mr10 el-input-custom">
                  <el-option v-for="item in rolesArr" :key="item.roleCode" :label="item.roleName" :value="item.roleCode"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="菜单名称：" prop="menuName">
                <el-input v-model="form.menuName" maxlength="100" placeholder="请输入菜单名称" class="el-input-custom"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="菜单归属模块：" prop="module">
                <el-input v-model="form.module" maxlength="100" placeholder="请输入菜单归属模块" class="el-input-custom"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="菜单图标：" prop="menuIcon">
                <el-input v-model="form.menuIcon" maxlength="100" placeholder="请输入ElementUI图标名称" class="el-input-custom"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="菜单路径：" prop="menuUrl">
                <el-input v-model="form.menuUrl" maxlength="256" placeholder="请输入菜单路径" class="el-input-custom"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="vue页面路径：" prop="vuePath">
                <el-input v-model="form.vuePath" maxlength="256" placeholder="请输入vue页面路径" class="el-input-custom"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="菜单序号：" prop="menuOrder">
                <el-input v-model="form.menuOrder" placeholder="请输入菜单序号" class="el-input-custom"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="菜单类型：" prop="menuType">
                <el-radio v-for="item in menuArr" v-model="form.menuType" :label="item.value" style="width: 125px;">{{item.name}}</el-radio>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="是否有效：" prop="isDelete" v-show="editVisible">
                <el-radio v-for="item in yesNoArr" v-model="form.isDelete" :label="item.value" style="width: 125px;">{{item.name}}</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="saveSure">确 定</el-button>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>
        </template>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { ref, reactive, getCurrentInstance } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import request from '../../utils/request';

export default {
  name: "menuList",
  setup() {
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    // 查询条件
    const query = reactive({
      menuType: "",
      roleCode: "",
      menuCode: "",
      menuUrl: "",
      module: "",
      vuePath: "",
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
      module: "",
      vuePath: "",
      roleArr : [],
      roleCode : "",
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
        { required: true, message: "菜单编码不能为空", trigger: "blur" },
        { type: 'string', message: '只能输入字母 数字 / *', trigger: 'blur',
          transform (value) {
            if (value){
              if (/[^A-Za-z0-9\/*]/.test(value)) {
                return true
              }else{
              }
            }
          }
        }
      ],
      menuUrl : [
        { required: true, message: "菜单路径不能为空", trigger: "blur" },
        { type: 'string', message: '只能输入字母 数字 / *', trigger: 'blur',
          transform (value) {
            if (value){
              if (/[^A-Za-z0-9\/*]/.test(value)) {
                return true
              }else{
              }
            }
          }
        }
      ],
      vuePath : [
        { type: 'string', message: '只能输入字母 数字 / *', trigger: 'blur',
          transform (value) {
            if (value){
              if (/[^A-Za-z0-9\/*]/.test(value)) {
                return true
              }else{
              }
            }
          }
        }
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
    // 菜单类型
    const menuArr = ref([]);
    // 是否字典
    const yesNoArr = ref([]);
    // 查询数据字典
    const getCodeDataArr = () => {
      axios.$http.get(request.code + "menuType", null).then(function (res) {
        menuArr.value = res.data.menuType;
      });
      axios.$http.get(request.code + "yesNo", null).then(function (res) {
        yesNoArr.value = res.data.yesNo;
      });
      // let codeArr = [];
      // codeArr.push("menuType");
      // codeArr.push("yesNo");
      // axios.$http.post(request.codes, codeArr).then(function (res) {
      //     menuArr.value = res.data.menuType;
      //     yesNoArr.value = res.data.yesNo;
      // });
    };
    getCodeDataArr();
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
      axios.$http.get(request.roleList,null).then(function (res) {
        rolesArr.value = res.data;
      });
    };
    getRoleList();
    // 表格数据
    const tableData = ref([]);
    // 获取表格数据
    const getData = () => {
      axios.$http.get(request.menuList,query).then(function (res) {
        tableData.value = res.data;
        query.pageTotal = res.totalNum;
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
      query.roleCode = "";
      query.menuCode = "";
      query.menuUrl = "";
      query.module = "";
      query.vuePath = "";
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
      form.module = "";
      form.vuePath = "";
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
      form.module = row.module;
      form.vuePath = row.vuePath;
      form.roleArr = [];
      if(row.roleCode){
        let roles = row.roleCode.split(",");
        rolesArr.value.forEach (temp => {
          if(roles.indexOf(temp.roleName) !== -1 ){
            form.roleArr.push(temp.roleCode);
          }
        });
      }
      form.isDelete = row.isDelete;
    };
    // 删除菜单
    const handleDelete = (row) => {
      // 二次确认删除
      ElMessageBox.confirm("确定要删除吗？", "提示", {type: "warning"}).then(() => {
        form.menuId= row.menuId;
        axios.$http.post(request.deleteMenu, form).then(function (res) {
          ElMessage.success('删除成功');
          getData();
        });
      }).catch(() => {});
    };
    // 保存按钮
    const saveSure = () => {
      editForm.value.validate((valid) => {
        if (valid) {
          form.roleCode = "";
          form.roleArr.forEach (temp => {
            form.roleCode += temp + ",";
          });
          form.roleCode = form.roleCode ? form.roleCode.substring(0,form.roleCode.length-1) : "";
          axios.$http.post(request.editMenu, form).then(function (res) {
            dialogVisible.value = false;
            ElMessage.success('保存成功');
            getData();
          });
        } else {
          return false;
        }
      });
    };
    return { menuArr,yesNoArr,query,tableData,editRules,form,rolesArr,editVisible,dialogVisible,editForm,
      handleSearch,handleReset,handleEdit,handlePageChange,saveSure,handleAdd,handleDelete};
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
.red {
  color: #ff0000;
}
.el-input-custom{
  width: 250px;
}
</style>
