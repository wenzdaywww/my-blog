<!-- 用户头注册 -->
<template>
  <div>
    <el-dialog title="用户注册" v-model="registVisible" destroy-on-close="true" width="20%">
      <el-form label-width="120px" :model="form" :rules="addRules" ref="addForm">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" maxlength="40" placeholder="请输入用户ID"></el-input>
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="form.userName" maxlength="100" placeholder="请输入用户名称"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" maxlength="20" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="1">男</el-radio>
          <el-radio v-model="form.sex" label="0">女</el-radio>
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNum">
          <el-input v-model="form.phoneNum" maxlength="11" placeholder="请输入手机号码"
                    οninput="value=value.replace(/[^\d]/g,'');if(value.length > 11)value = value.slice(0, 11)"></el-input>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker v-model="form.birthday" type="date" style="width: 100%;"
                          value-format="YYYY-MM-DD" format="YYYY年MM月DD日" placeholder="请选择出生日期" >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" maxlength="100" placeholder="请输入邮箱地址"></el-input>
        </el-form-item>
      </el-form>
      <div class="btn-save">
        <span class="dialog-footer">
          <el-button @click="registVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveAdd">确 定</el-button>
        </span>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import {getCurrentInstance,  reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import request from '../../utils/request';

export default {
  name: "Register",
  emits: ['toLogin'], //父组件中引用子组件定义的方法
  setup(props,{emit}){
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    //弹窗控制
    const registVisible = ref(false);
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
      password : [
        { required: true, message: "密码不能为空", trigger: "blur" }
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
      email: [
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
    // 表单数据
    let form = reactive({
      userId: "",
      userName : "",
      password : "",
      phoneNum : "",
      birthday : "",
      sex : "",
      photo : "",
      email : ""
    });
    // 表单校验
    const addForm = ref(null);
    // 新增页面的保存按钮
    const saveAdd = () => {
      addForm.value.validate((valid) => {
        if (valid) {
          axios.$http.post(request.register, form).then(function (res) {
            if(res.code === 200){
              ElMessage.success('新增成功');
              registVisible.value = false;
              emit('toLogin',null);//调用父组件方法跳转登录页面
            }else {
              ElMessage.error(res.data);
            }
          });
        } else {
          return false;
        }
      });
    };
    // 用户注册弹窗显示
    const shwoDialog = () => {
      registVisible.value = true;
    };
    return {addRules,addForm,registVisible,form,saveAdd,shwoDialog};
  }
}
</script>

<style scoped>
.btn-save{
  text-align: center;
}
</style>