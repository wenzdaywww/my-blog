<!-- 推荐博客列表 -->
<template>
  <div>
    <el-card>
      <ul class="list" v-infinite-scroll="loadTipList"  :infinite-scroll-distance="10" :infinite-scroll-delay="800" :infinite-scroll-immediate="true" style="overflow:auto">
        <el-card class="blog-card" v-for="item in blogList">
          <img v-if="item.coverImg" :src="item.coverImg" class="image" />
          <div class="blog-detail">
            <el-tooltip class="item" effect="light" :content="item.title" placement="bottom">
              <el-link :href="item.blogId ? '/article?bid=' + item.blogId : '#'" class="ellipsis-line1 blog-title"
                       target="_blank" type="primary" @click="showBlogDetail(item.blogId)">{{item.title}}</el-link>
            </el-tooltip>
            <div>
              <el-row class="el-row">
                <div class="ellipsis-line4">
                  {{item.summary}}
                </div>
              </el-row>
              <el-row class="el-row">
                <div style="width: 50%;">
                  <el-tooltip class="item" effect="light" content="浏览量" placement="bottom">
                    <i class="el-icon-view color-grad">{{item.browse}}</i>
                  </el-tooltip>
                  <el-tooltip class="item" effect="light" content="点赞量" placement="bottom">
                    <i class="el-icon-lx-like color-grad padding-left10">{{item.praise}}</i>
                  </el-tooltip>
                  <el-tooltip class="item" effect="light" content="收藏数" placement="bottom">
                    <i class="el-icon-lx-favor color-grad padding-left10">{{item.collect}}</i>
                  </el-tooltip>
                  <el-tooltip class="item" effect="light" content="评论数" placement="bottom">
                    <i class="el-icon-lx-comment color-grad padding-left10">{{item.comment}}</i>
                  </el-tooltip>
                </div>
                <div style="width: 50%">
                  <span class="blog-time color-grad">{{item.createDate}}</span>
                </div>
              </el-row>
            </div>
          </div>
        </el-card>
      </ul>
    </el-card>
  </div>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import request from "../../../utils/request";

export default {
  name: "TipList",
  setup(){
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    // 博客列表数据
    let blogList = ref([]);
    //查询条件
    const query = reactive({
      pageNum: 1
    });
    //是否还有更多
    const more = ref(true);
    // 加载推荐博客列表
    const loadTipList = () =>{
      if (more.value){
        axios.$http.post(request.tipList, query).then(function (res) {
          if(res.code === 200){
            blogList.value = res.data;
          }
          if(res.code === 200){
            if (res.data){
              query.pageNum++;//页码增加
              more.value = res.data.length >= res.pageSize;//判断是否还有更多
              res.data.forEach (temp => {
                blogList.value.push(temp);//父评论列表添加
              });
            }else {//没有数据
              more.value = false;//没有更多
            }
          }
        });
      }
    }
    return {blogList,loadTipList}
  }
}
</script>

<style scoped>
.list{
  height: calc(90vh);
}
.image{
  float: right;
  width: 160px;
  height: 100px;
  border-radius: 8%;
  margin-top: 28px;
  margin-left: 5px;
}
.blog-detail{
  width: 100%;
}
::-webkit-scrollbar {
  height: 0;
  width: 0;
  color: transparent;
}
.blog-title{
  font-size: 20px;
  font-width: blod;
}
.ellipsis-line1{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
.ellipsis-line4{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 4; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
.color-grad{
  color: #999697;
}
.padding-left10{
  padding-left: 10px;
}
.blog-time{
  float: right;
  margin-right: 5px;
}
</style>