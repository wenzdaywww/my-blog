<template>
  <el-card shadow="hover" class="info-card">
    <div>
      <el-row>
        <div class="info-image" @click="showDialog">
          <img :src="author.photo" class="user-avator" alt/>
        </div>
        <div>
          <el-row>
            <el-link class="info-name" href="#" target="_blank">{{author.userName}}</el-link>
          </el-row>
          <el-row>
            <span class="info-age">码龄:{{author.age}}</span>
          </el-row>
        </div>
      </el-row>
      <el-row class="margin-top10">
        <el-col :span="1"></el-col>
        <el-col :span="4">
          <div class="grid-cont-right">
            <div class="grid-num">{{author.blogs}}</div>
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
            <div class="grid-num">{{author.likes}}</div>
            <div>获赞</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="grid-cont-right">
            <div class="grid-num">{{author.comments}}</div>
            <div>评论</div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="grid-cont-right">
            <div class="grid-num">{{author.collects}}</div>
            <div>收藏</div>
          </div>
        </el-col>
      </el-row>
      <el-row class="margin-top10">
        <el-col :span="1"></el-col>
        <el-col :span="20">
          <el-button style="width: 100%" size="mini" round>{{isFan ? "取消关注" : "关注"}}</el-button>
        </el-col>
        <el-col :span="3"></el-col>
      </el-row>
    </div>
  </el-card>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import utils from '../../utils/utils';

export default {
  name: "Author",
  setup(){
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    //博主id
    const authorId = utils.getUrlParam("id");
    // 博主信息
    let author = reactive({
      userName : "",
      photo : "src/assets/img/img.jpg",
      age : "",
      blogs : 0,
      fans : 0,
      likes : 0,
      comments : 0,
      collects : 0
    });
    // 是否已关注
    const isFan = ref(false);
    // 获取博主信息
    const getAuthorInfo = () => {
      request.$http.get("api/blog/browse/author/"+authorId,null).then(function (res) {
        if(res.code === 200){
          author.userName = res.data.userName;
          author.photo = res.data.photo;
          author.age = res.data.age;
          author.blogs = res.data.blogs;
          author.fans = res.data.fans;
          author.likes = res.data.likes;
          author.comments = res.data.comments;
          author.collects = res.data.collects;
        }
      });
    }
    getAuthorInfo();
    return {author,isFan};
  }
}
</script>

<style scoped>
.info-card{
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 8px;
  margin-bottom: 20px;
  width: 50%;
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
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 8px;
}
</style>