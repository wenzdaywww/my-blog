<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">登录</div>
      <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="param.id" placeholder="username">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="password" v-model="param.pwd" @keyup.enter="submitForm()">
            <template #prepend>
              <el-button icon="el-icon-lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="rememberMe">记住我</el-checkbox>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>
        <p class="login-tips"></p>
      </el-form>
    </div>
  </div>
</template>

<script>
import { ref, reactive, getCurrentInstance } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import {initUserRouter} from "../../router";

export default {
  setup() {
    const router = useRouter();
    const param = reactive({
      id: "",
      pwd: "",
      rmb: "0"
    });
    const rules = {
      id: [
        { required: true, message: "请输入用户名", trigger: "blur", }
      ],
      pwd: [
        { required: true, message: "请输入密码", trigger: "blur" }
      ],
    };
    // 登录表单
    const login = ref(null);
    // 记住我
    const rememberMe = ref(false);
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    //登录方法
    const submitForm = () => {
      param.rmb = rememberMe.value ? "1" : "0";
      request.$http.post("api/admin/login",param).then(function (res) {
        if(res.code === 200){
          localStorage.setItem("userId",param.id);
          request.$http.get("api/admin/user/router", {userId:localStorage.getItem('userId')}).then(function (res) {
            if(res.code === 200){
              initUserRouter(res.data);
            }
          });
        }else {
          ElMessage.error(res.data);
        }
      });
    };
    return {rememberMe, param, rules, login, submitForm
    };
  },
};
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(src/assets/img/login-bg.jpg);
  background-size: 100%;
}
.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}
.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}
.ms-content {
  padding: 30px 30px;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}
.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
</style>