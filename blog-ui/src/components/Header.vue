<template>
  <div class="header">
    <!-- 折叠按钮 -->
    <div class="collapse-btn" @click="collapseChage">
      <i v-if="!collapse" class="el-icon-s-fold"></i>
      <i v-else class="el-icon-s-unfold"></i>
    </div>
    <div class="logo">{{form.userId}}</div>
    <div class="header-right">
      <div class="header-user-con">
        <!-- 消息中心 -->
        <div class="btn-bell">
          <el-tooltip effect="dark" :content="form.message?`有${form.message}条未读消息`:`消息中心`" placement="bottom">
            <router-link to="/tabs">
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
      <el-dialog title="修改密码" v-model="editVisible" width="20%">
        <el-form label-width="120px" :model="form" :rules="editRules" ref="editForm">
          <el-form-item label="旧密码：" prop="passWord">
            <el-input type="password" v-model="form.passWord" maxlength="20" placeholder="请输入旧密码"></el-input>
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
import {ElMessage} from "element-plus";
import cookies from "vue-cookies";

export default {
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 路由
    const router = useRouter();
    const store = useStore();
    const collapse = computed(() => store.state.collapse);
    //修改密码弹出框控制位
    const editVisible = ref(false);
    // 表单对象
    const editForm = ref(null);
    // 表单数据
    let form = reactive({
      userId: cookies.get("userId"),
      newPassWord: "",
      cfmPassWord: "",
      passWord : "",
      photo : "src/assets/img/img.jpg",
      message : 0
    });
    // 修改密码的规则校验
    const editRules = {
      passWord : [
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
              }else{
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
    const getData = () => {
      request.$http.get("api/admin/user/info",form).then(function (res) {
        if(res.code === 200){
          if(res.data.photo){
            form.photo = "api/admin" + res.data.photo;
          }
        }
      });
    };
    getData();
    // 用户头像点击方法
    const handleCommand = (command) => {
      // 退出
      if (command == "loginout") {
        request.$http.post("api/admin/logout",null).then(function (res) {
          if(res.code === 200){
            cookies.set('token',null);
            ElMessage.success("退出成功");
            router.push("/login");
          }else {
            ElMessage.error(res.data);
          }
        }).catch(function (res) {
          ElMessage.error("退出失败");
        });
      } else if (command == "user") { // 个人中心
        router.push("/user");
      } else if (command == "pwd") { // 修改密码
        editVisible.value = true;
        form.passWord = "";
        form.newPassWord = "";
        form.cfmPassWord = "";
      }
    };
    // 保存
    const savePwd = () => {
      editForm.value.validate((valid) => {
        if (valid) {
          request.$http.post("api/admin/user/pwd",form).then(function (res) {
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
    return { form,editVisible,editForm,editRules, collapse,
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
