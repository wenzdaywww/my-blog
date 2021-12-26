<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-s-custom"></i> 首页
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover" class="mgb20" style="height:252px;">
          <div class="user-info">
            <img :src="user.photo" class="user-avator" alt />
            <div class="user-info-cont">
              <div class="user-info-name">{{ user.userName }}</div>
            </div>
          </div>
          <div class="user-info-list">
            个性签名： <span>{{ user.brief }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-row :gutter="20" class="mgb20">
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-1">
                <i class="el-icon-user grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.friends}}</div>
                  <div>关注</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-2">
                <i class="el-icon-s-custom grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.fans}}</div>
                  <div>粉丝</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-3">
                <i class="el-icon-tickets grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.blogs}}</div>
                  <div>博客</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Schart from "vue-schart";
import {getCurrentInstance, reactive} from "vue";
export default {
  name: "index",
  components: { Schart },
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 用户信息
    let user = reactive({
      userId: localStorage.getItem('userId'),
      userName : "",
      brief : "",
      photo : "src/assets/img/img.jpg",
      friends : 0,
      fans : 0,
      blogs : 0
    });
    // 获取用户数据
    const getData = () => {
      if(localStorage.getItem('userId')){
        request.$http.get("api/base/user/info",user).then(function (res) {
          if(res.code === 200){
            user.userName = res.data.userName;
            user.brief = res.data.brief;
            if (res.data.photo){
              user.photo = "api/base" + res.data.photo;
            }
            user.friends = res.data.friends;
            user.fans = res.data.fans;
            user.blogs = res.data.blogs;
          }
        });
      }
    };
    getData();

    return { user };
  },
};
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}

.grid-content {
  display: flex;
  align-items: center;
  height: 100px;
}

.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: #999;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}

.grid-con-icon {
  font-size: 50px;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  color: #fff;
}

.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 20px;
}

.user-avator {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

.user-info-cont {
  padding-left: 50px;
  flex: 1;
  font-size: 14px;
  color: #999;
}

.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}

.user-info-list {
  font-size: 14px;
  color: #999;
  line-height: 25px;
}

.user-info-list span {
  margin-left: 70px;
}

.mgb20 {
  margin-bottom: 20px;
}

.todo-item {
  font-size: 14px;
}

.todo-item-del {
  text-decoration: line-through;
  color: #999;
}

.schart {
  width: 100%;
  height: 300px;
}
</style>
