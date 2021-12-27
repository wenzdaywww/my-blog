<template>
  <div class="header">
    <!-- 折叠按钮 -->
    <div class="collapse-btn" @click="collapseChage">
      <i v-if="!collapse" class="el-icon-s-fold"></i>
      <i v-else class="el-icon-s-unfold"></i>
    </div>
    <div class="logo">{{form.userId}}</div>
    <div class="header-right">
      <!-- 未登录 -->
      <div v-if="isLogin == false" style="margin-top: 20px">
        <el-link type="danger" :href="loginUrl">登录/注册</el-link>
      </div>
      <!-- 已登录 -->
      <div class="header-user-con" v-if="isLogin == true">
        <!-- 消息中心 -->
        <div class="btn-bell">
          <el-tooltip effect="dark" :content="form.message?`有${form.message}条未读消息`:`消息中心`" placement="bottom">
            <router-link to="/news">
              <i class="el-icon-bell"></i>
            </router-link>
          </el-tooltip>
          <span class="btn-bell-badge" v-if="form.message"></span>
        </div>
        <!-- 用户名下拉菜单 -->
        <el-dropdown class="user-name" trigger="hover" @command="handleCommand">
          <!-- 用户头像 -->
          <div class="user-avator">
            <img :src="form.photo" />
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="user">个人中心</el-dropdown-item>
              <el-dropdown-item command="pwd">修改密码</el-dropdown-item>
              <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <!-- 修改密码弹出框 -->
      <el-dialog title="修改密码" v-model="editVisible" width="20%" v-if="isLogin == true">
        <el-form label-width="120px" :model="form" :rules="editRules" ref="editForm">
          <el-form-item label="旧密码：" prop="password">
            <el-input type="password" v-model="form.password" maxlength="20" placeholder="请输入旧密码"></el-input>
          </el-form-item>
          <el-form-item label="新密码：" prop="newPassWord">
            <el-input type="password" v-model="form.newPassWord" maxlength="20" placeholder="请输入新密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码：" prop="cfmPassWord">
            <el-input type="password" v-model="form.cfmPassWord" maxlength="20" placeholder="请重新输入新密码"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible = false">取 消</el-button>
          <el-button type="primary" @click="savePwd">确 定</el-button>
        </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import {computed, getCurrentInstance, onMounted, reactive, ref} from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import utils from '../utils/utils';
import {ElMessage} from "element-plus";
import router, {initUserRouter} from "../router";

export default {
  setup() {
    //uaa返回的地址
    const redirect_uri = location.origin+"/home";
    //登录地址
    const loginUrl =  "http://localhost:8003/oauth/authorize?client_id=my-base&response_type=code&redirect_uri="+redirect_uri;
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 路由
    const router = useRouter();
    //store存储对象
    const store = useStore();
    // 是否已登录
    const isLogin = ref(false);
    //侧边栏是否收缩
    const collapse = computed(() => store.state.collapse);
    //修改密码弹出框控制位
    const editVisible = ref(false);
    // 表单对象
    const editForm = ref(null);
    // 表单数据
    let form = reactive({
      userId: "",
      newPassWord: "",
      cfmPassWord: "",
      password : "",
      photo : "src/assets/img/img.jpg",
      message : 0
    });
    // token数据
    let token = reactive({
      client_id: 'my-base',
      client_secret: 'wenzday',
      grant_type: 'authorization_code',
      code: '',
      redirect_uri: redirect_uri
    });
    // 修改密码的规则校验
    const editRules = {
      password : [
        { required: true, message: "旧密码不能为空", trigger: "blur" },
        { min: 6, message: "密码不能小于6位数", trigger: "blur" }
      ],
      newPassWord : [
        { required: true, message: "新密码不能为空", trigger: "blur" },
        { min: 6, message: "密码不能小于6位数", trigger: "blur" }
      ],
      cfmPassWord : [
        { required: true, message: "确认密码不能为空", trigger: "blur" },
        { min: 6, message: "密码不能小于6位数", trigger: "blur" },
        { type: 'string', message: '与新密码不一致', trigger: 'blur',
          transform (value) {
            if (value){
              if (value !== form.newPassWord) {
                return true
              }
            }
          }
        }
      ]
    };
    // 侧边栏折叠
    const collapseChage = () => {
      store.commit("handleCollapse", !collapse.value);
    };
    // 侧边栏缩放点击
    onMounted(() => {
      if (document.body.clientWidth < 1500) {
        collapseChage();
      }
    });
    // 获取用户数据
    const getUserData = (userId) => {
      request.$http.get("api/base/user/info", {userId : userId}).then(function (res) {
        if(res.code === 200){
          localStorage.setItem("userId",res.data.userId);
          isLogin.value = true;
          form.userId = res.data.userId;
          if(res.data.photo){
            form.photo = "api/base" + res.data.photo;
          }
        }
      });
    };
    //获取token
    const getToken = () => {
      if (localStorage.getItem("userId")){
        isLogin.value = true;
        //加载用户拥有的路由权限
        request.$http.get("api/base/user/router", {userId : localStorage.getItem("userId")}).then(function (res) {
          if(res.code === 200){
            initUserRouter(res.data);
          }
        });
        getUserData(localStorage.getItem("userId"));
      }else{
        isLogin.value = false;
        const code = utils.getUrlParam("code");
        if(code){
          token.code = code;
          request.$http.post("api/uaa/oauth/token", token).then(function (res) {
            if(res && res.data){
              localStorage.setItem("userId",res.data.userId);
              router.go(0);//重新跳转当前页面
            }
          });
        }
      }
    };
    getToken();
    // 用户头像点击方法
    const handleCommand = (command) => {
      // 退出
      if (command == "loginout") {
        request.$http.post("api/uaa/logout",null).then(function (res) {
          if(res.code === 200){
            localStorage.clear();
            ElMessage.success("退出成功");
            router.push("/home");
          }
        }).catch(function (res) {
          ElMessage.error("退出失败");
        });
      } else if (command == "user") { // 个人中心
        router.push("/user");
      } else if (command == "pwd") { // 修改密码
        editVisible.value = true;
        form.password = "";
        form.newPassWord = "";
        form.cfmPassWord = "";
      }
    };
    // 保存
    const savePwd = () => {
      editForm.value.validate((valid) => {
        if (valid) {
          request.$http.post("api/base/user/pwd",form).then(function (res) {
            if(res.code === 200){
              ElMessage.success('修改成功');
              editVisible.value = false;
            }else {
              ElMessage.error(res.data);
            }
          });
        } else {
          return false;
        }
      });
    };
    return { loginUrl,isLogin,form,editVisible,editForm,editRules, collapse,
      collapseChage, handleCommand,savePwd
    };
  }
};
</script>
<style scoped>
.i-login{
  color: white;
  font-size: 18px;
}
.header {
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: #fff;
}
.collapse-btn {
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 70px;
}
.header .logo {
  float: left;
  width: 250px;
  line-height: 70px;
}
.header-right {
  float: right;
  padding-right: 50px;
}
.header-user-con {
  display: flex;
  height: 70px;
  align-items: center;
}
.btn-fullscreen {
  transform: rotate(45deg);
  margin-right: 5px;
  font-size: 24px;
}
.btn-bell,
.btn-fullscreen {
  position: relative;
  width: 30px;
  height: 30px;
  text-align: center;
  border-radius: 15px;
  cursor: pointer;
}
.btn-bell-badge {
  position: absolute;
  right: 0;
  top: -2px;
  width: 8px;
  height: 8px;
  border-radius: 4px;
  background: #f56c6c;
  color: #fff;
}
.btn-bell .el-icon-bell {
  color: #fff;
}
.user-name {
  margin-left: 10px;
}
.user-avator {
  margin-left: 20px;
}
.user-avator img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.el-dropdown-link {
  color: #fff;
  cursor: pointer;
}
.el-dropdown-menu__item {
  text-align: center;
}
</style>
