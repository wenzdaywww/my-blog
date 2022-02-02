<!-- 博主信息 -->
<template>
  <el-card class="info-card">
    <template #header>
      <div class="clearfix">
        <div class="info-image">
          <img :src="author.photo" class="user-avator" alt/>
        </div>
        <div>
          <el-row>
            <el-link class="info-name" :href="author.userId ? '/blogger?id='+author.userId : '#'" target="_blank">{{author.userName}}</el-link>
          </el-row>
          <el-row>
            <span class="info-age">码龄:{{author.age}}</span>
          </el-row>
        </div>
      </div>
    </template>
    <div>
      <el-row>
        <el-col :span="1"></el-col>
        <el-col :span="4">
          <div class="grid-cont-right">
            <div class="grid-num">{{author.blog}}</div>
            <div>博客</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="grid-cont-right">
            <div class="grid-num">{{author.fans}}</div>
            <div>粉丝</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="grid-cont-right">
            <div class="grid-num">{{author.praise}}</div>
            <div>获赞</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="grid-cont-right">
            <div class="grid-num">{{author.comment}}</div>
            <div>评论</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="grid-cont-right">
            <div class="grid-num">{{author.collect}}</div>
            <div>收藏</div>
          </div>
        </el-col>
      </el-row>
      <el-row class="margin-top10">
        <el-col :span="1"></el-col>
        <el-col :span="20">
          <el-button v-if="author.flag" style="width: 100%" size="mini" round @click="followAuthor">{{author.isFan ? "取消关注" : "关注"}}</el-button>
        </el-col>
        <el-col :span="3"></el-col>
      </el-row>
    </div>
  </el-card>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import utils from '../../../utils/utils';
import request from "../../../utils/request";
import {ElMessage} from "element-plus";
import { useRouter } from "vue-router";

export default {
  name: "Author",
  setup(){
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    // 路由
    const router = useRouter();
    //博主id
    const authorId = utils.getUrlParam("id");
    //博客id
    const blogId = utils.getUrlParam("bid");
    // 博主信息
    let author = reactive({
      userId:'',
      userName : "",
      photo : "src/assets/img/img.jpg",
      age : "",
      blog : 0,
      fans : 0,
      praise : 0,
      comment : 0,
      collect : 0
    });
    // 获取博主信息
    const getAuthorInfo = () => {
      axios.$http.get(request.author, {id:authorId,bid:blogId}).then(function (res) {
        if(res.code === 200){
          author.userId = res.data.userId;
          author.userName = res.data.userName;
          author.photo = res.data.photo;
          author.age = res.data.age;
          author.blog = res.data.blog;
          author.fans = res.data.fans;
          author.praise = res.data.praise;
          author.comment = res.data.comment;
          author.collect = res.data.collect;
          author.isFan = res.data.fan;
          author.flag = res.data.flag;
        }else {
          router.push("/404");
        }
      });
    }
    getAuthorInfo();
    // 关注博主
    const followAuthor = () => {
      if(utils.isLogin()){
        axios.$http.get(request.follow, {id:authorId,bid:blogId}).then(function (res) {
          if(res.code === 200){
            author.isFan = res.data;
          }
        });
      }else {
        ElMessage.info('请登录');
      }
    }
    return {author,followAuthor};
  }
}
</script>

<style scoped>
.clearfix{
  margin-bottom: 10px;
}
.info-card{
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 8px;
  margin-bottom: 20px;
  width: 360px;
  float: right;
}
.info-image {
  float: left;
  display: flex;
  width: 70px;
  height: 70px;
  background: #f8f8f8;
  border: 1px solid #eee;
  border-radius: 50%;
  overflow: hidden;
  border-bottom: 2px solid #ccc;
}
.info-name{
  font-size: 18px;
  width: auto;
  margin-top: 10px;
  margin-left: 10px;
}
.info-age{
  color: darkgray;
  font-size: 14px;
  width: auto;
  margin-top: 10px;
  margin-left: 10px;
}
.margin-top10{
  margin-top: 20px;
}
.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: #999;
}
.grid-num {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}
</style>