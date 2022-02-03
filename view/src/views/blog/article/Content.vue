<!-- 博客文章内容 -->
<template>
  <div>
    <el-card>
      <el-row><span class="span-title">{{ blog.title }}</span></el-row>
      <el-row>
        <el-col :span="18">
          <el-tooltip class="item" effect="light" content="浏览" placement="bottom">
            <i class="el-icon-view color-grad">{{blog.browse}}</i>
          </el-tooltip>
          <el-tooltip class="item" effect="light" content="点赞" placement="bottom">
            <i class="el-icon-lx-like color-grad padding-left10">{{blog.praise}}</i>
          </el-tooltip>
          <i class="color-grad padding-left10" :class="blog.collection ? 'el-icon-lx-favorfill color-red' : 'el-icon-lx-favor'">
            <el-link href="javascript:void(0);" type="primary" class="item-collect" @click="addCollect()">收藏 {{blog.collect}}</el-link>
          </i>
          <el-tooltip class="item" effect="light" content="评论" placement="bottom">
            <i class="el-icon-lx-comment color-grad padding-left10">{{blog.comment}}</i>
          </el-tooltip>
        </el-col>
        <el-col :span="6"><span class="blog-time color-grad">{{blog.createTime}}</span></el-col>
      </el-row>
      <el-row>
        <span class="color-grad mag-top">分组：</span><el-tag size="mini" class="tag-name">{{blog.groupName}}</el-tag>
        <span class="color-grad mag-top">标签：</span><el-tag v-for="item in blog.blogTag" size="mini" type="success" class="tag-name">{{item.tagName}}</el-tag>
      </el-row>
    </el-card>
    <el-card class="blog-card">
      <div class="artitle-div" v-html="blog.content"></div>
    </el-card>
    <!-- 评论列表 -->
    <blog-comment/>
    <!-- 收藏夹弹出框 -->
    <el-dialog title="添加收藏夹" v-model="addVisible" width="400px">
      <el-row v-for="item in groupArr">
        <div class="collect-div" @click="selectCollect(item)">
          <el-row>
            <el-col :span="20"><span class="collect-name ellipsis-line1">{{item.collectName}}</span></el-col>
            <el-col :span="4"><i class="collect-select" :class="selectCgId === item.cgId ? 'el-icon-check' : ''"></i></el-col>
          </el-row>
        </div>
      </el-row>
      <el-row class="mag-top20">
        <el-col :span="20"><el-input placeholder="新建文件夹" size="mini" maxlength="100" v-model="newCollectName" clearable></el-input></el-col>
        <el-col :span="4"><el-button type="primary" size="mini" class="mag-left10" plain round @click="newCollect">新建</el-button></el-col>
      </el-row>
      <div class="footer-btn">
        <el-button type="primary" size="small" plain round @click="saveCollect">确 定</el-button>
        <el-button size="small" type="danger" plain round @click="addVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import utils from "../../../utils/utils";
import request from "../../../utils/request";
import blogComment from "./Comment.vue";
import {ElMessage} from "element-plus";

export default {
  name: "article",
  components: { blogComment },
  setup() {
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    //博客id
    const blogId = utils.getUrlParam("bid");
    //博客文章信息
    const blog = reactive({});
    // 编辑时弹窗和保存
    const addVisible = ref(false);
    //新建收藏夹名称
    const newCollectName = ref(null);
    //选中的收藏夹id
    const selectCgId = ref(0);
    // 收藏分组数据
    const groupArr = ref([]);
    //收藏博客
    const addCollect = () => {
      if(utils.isLogin()){
        //已收藏，则取消收藏
        if(blog.collection){
          saveCollect();
        }else {//未收藏，则添加收藏
          addVisible.value = true;
          findCollectGroup();
        }
      }else {
        ElMessage.info('请登录');
      }
    }
    //查询收藏夹
    const findCollectGroup = () => {
      axios.$http.get(request.collectGroup,null).then(function (res) {
        if(res.code === 200){
          groupArr.value = res.data;
        }
      });
    }
    //查询博客文章信息
    const getBlogArticle = () => {
      if(blogId){
        axios.$http.get(request.article+blogId,null).then(function (res) {
          if(res.code === 200){
            document.title += res.data.title;//设置浏览器标题
            blog.blogId = res.data.blogId;
            blog.title = res.data.title;
            blog.groupName = res.data.groupName;
            blog.blogTag = res.data.blogTag;
            blog.content = res.data.content;
            blog.browse = res.data.browse;
            blog.praise = res.data.praise;
            blog.collect = res.data.collect;
            blog.collection = res.data.collection;
            blog.comment = res.data.comment;
            blog.createTime = res.data.createTime;
          }
        });
      }
    }
    getBlogArticle();
    //添加/取消收藏
    const saveCollect = () => {
      axios.$http.post(request.addCollect, {bid:blogId,cgid:selectCgId.value}).then(function (res) {
        if(res.code === 200){
          blog.collection = res.data;
          if(blog.collection){
            blog.collect++;
            addVisible.value = false;
            ElMessage.success('收藏成功');
          }else {
            blog.collect--;
            ElMessage.success('取消收藏成功');
          }
        }
      });
    }
    //选择收藏夹处理
    const selectCollect = (item) => {
      selectCgId.value = selectCgId.value === item.cgId ? null : item.cgId;
    }
    //新建收藏夹
    const newCollect = () => {
      axios.$http.post(request.addCollectGroup,{name:newCollectName.value}).then(function (res) {
        if(res.code === 200){
          newCollectName.value = null;
          findCollectGroup();
          ElMessage.success('收新建收藏夹成功');
        }else {
          ElMessage.error('新建收藏夹失败');
        }
      });
    }
    return {addVisible,selectCgId,blog,groupArr,newCollectName,addCollect,saveCollect,newCollect,selectCollect};
  }
};
</script>

<style scoped>
.span-title{
  font-size: 20px;
  margin-bottom: 10px;
}
.blog-card{
  border-radius: 8px;
  margin-bottom: 3px;
}
.color-grad{
  color: #999697;
}
.color-red{
  color: red;
}
.padding-left10{
  padding-left: 10px;
}
.blog-time{
  color: #999697;
  float: right;
  margin-right: 5px;
}
.tag-name{
  margin-right: 10px;
  margin-top: 8px;
}
.mag-top{
  margin-top: 5px;
}
.mag-top20{
  margin-top: 20px;
}
.mag-left10{
  margin-left: 10px;
}
.artitle-div{
  margin-left: 10px;
  margin-right: 10px;
}
.item-collect{
  font-size: 15px;
  margin-bottom: 3px;
}
.collect-div{
  height: 30px;
  width: 100%;
  line-height:30px;
}
.collect-div:hover {
  background-color: #f0f0f0;
}
.collect-name{
  width: 90%;
}
.collect-select{
  height: 30px;
  line-height:30px;
  float: right;
}
.ellipsis-line1{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
.footer-btn{
  margin-top: 20px;
  text-align: center;
}
</style>
