<template>
  <div class="header">
    <!-- 折叠按钮 -->
    <div v-if="isAdmin" class="collapse-btn" @click="collapseChage">
      <i v-if="!collapse" class="el-icon-s-fold"></i>
      <i v-else class="el-icon-s-unfold"></i>
    </div>
    <div class="logo header-item" @click="handleCommand('blog-index')">博客</div>
    <div class="header-right">
      <!-- 未登录 -->
      <div v-if="isLogin == false" style="margin-top: 20px">
        <el-link type="danger" :href="loginUrl">登录</el-link>
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
              <el-dropdown-item command="blog">我的博客</el-dropdown-item>
              <el-dropdown-item command="user">个人中心</el-dropdown-item>
              <el-dropdown-item command="pwd">修改密码</el-dropdown-item>
              <el-dropdown-item divided command="loginout" class="login-out">退出登录</el-dropdown-item>
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
import {computed, getCurrentInstance, onMounted, reactive, ref,inject} from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import utils from '../utils/utils';
import {ElMessage} from "element-plus";
import {initUserRouter} from "../router";

export default {
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 路由
    const router = useRouter();
    //store存储对象
    const store = useStore();
    //从父组件获取传值
    const isAdmin = inject("isAdmin"); //是否后台首页
    //环境配置
    const ENV = import.meta.env;
    /** 客户端信息 **/
    let clientInfo = reactive({
      //客户端ID
      client_id: isAdmin ? ENV.VITE_BASE_CLIENT_ID : ENV.VITE_BLOG_CLIENT_ID,
      //客户端签名
      client_secret: isAdmin ? ENV.VITE_BASE_CLIENT_SECRET : ENV.VITE_BLOG_CLIENT_SECRET,
      //授权方式
      grant_type: isAdmin ? ENV.VITE_BASE_GRANT_TYPE : ENV.VITE_BLOG_GRANT_TYPE,
      //uaa返回的地址
      redirect_uri : isAdmin ? ENV.VITE_BASE_REDIRECT_URI : ENV.VITE_BLOG_REDIRECT_URI,
      //授权码
      code: ""
    });
    //TODO 2022/1/11 22:45 测试环境nginx配置多个uaa负载均衡不能使用，待处理
    //登录地址
    const loginUrl =  ENV.VITE_UAA_LOGIN + "client_id=" + clientInfo.client_id + "&response_type=code&redirect_uri=" + clientInfo.redirect_uri;
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
      newPassWord: "",
      cfmPassWord: "",
      password : "",
      photo : "src/assets/img/img.jpg",
      message : 0
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
          if(res.data.photo){
            form.photo = res.data.photo;
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
          clientInfo.code = code;
          request.$http.post("api/uaa/oauth/token", clientInfo).then(function (res) {
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
            //TODO 2021/12/28 22:35 router.push路由跳转页面不刷新，待处理
            //TODO 2022/1/11 22:43 测试环境部署多个前端，退出后不能到登录页面，待处理
            router.push("/index");
          }
        }).catch(function (res) {
          ElMessage.error("退出失败");
        });
      } else if (command == "user") { // 个人中心
        router.push("/user");
      }else if (command == "blog") { // 我的博客
        router.push("/index");
      } else if (command == "pwd") { // 修改密码
        editVisible.value = true;
        form.password = "";
        form.newPassWord = "";
        form.cfmPassWord = "";
      }else if(command == "blog-index"){ //博客首页
        router.push("/index");
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
    return { isAdmin,loginUrl,isLogin,form,editVisible,editForm,editRules, collapse,
      collapseChage, handleCommand,savePwd
    };
  }
};
</script>
<style scoped>
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
  margin-left: 10px;
  float: left;
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
.login-out{
  color: red;
}
.header-item{
  font-size: 18px;
  width: auto;
  padding-left: 8px;
  padding-right: 8px;
}
.header-item:hover {
  background-color: #a19f9f;
}
</style>
