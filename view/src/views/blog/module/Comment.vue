<template>
  <div>
    <el-card>
      <el-link href="javascript:void(0);" type="primary" @click="openInputHandle">评论</el-link>
      <!-- 新增评论 -->
      <el-row v-if="openInput">
        <el-col :span="24">
          <el-input v-model="inputContent" type="textarea" placeholder="请输入，最多256字符" maxlength="256" :rows="2"></el-input>
        </el-col>
      </el-row>
      <el-row v-if="openInput">
        <el-col :span="24">
          <el-button size="mini" type="primary" plain class="submit-comment" @click="addComment">确定</el-button>
          <el-button size="mini" type="danger" plain class="submit-comment" @click="openInput = false">取消回复</el-button>
        </el-col>
      </el-row>
      <!-- 历史评论 -->
      <div>
        <ul class="list" v-infinite-scroll="loadParentData"  :infinite-scroll-distance="10" :infinite-scroll-delay="800" :infinite-scroll-immediate="true" style="overflow:auto">
          <el-collapse v-model="activeNames">
            <el-collapse-item v-for="item in list" :name="item.commentId">
              <!-- 父评论 -->
              <template #title>
                <div class="comment-div">
                  <el-row>
                    <el-col :span="1"><img :src="item.photo" class="comment-img"/></el-col>
                    <el-col :span="2">
                      <el-tooltip class="item" effect="light" :content="item.userName" placement="bottom">
                        <el-link :href="item.userId ? '/blog?id=' + item.userId : '#'" target="_blank" type="primary" class="ellipsis-line1">{{item.userName}}</el-link>
                      </el-tooltip>
                    </el-col>
                    <el-col :span="17"><div class="comment-text " :class="activeNames.indexOf(item.commentId) != -1 ? 'ellipsis-line5' : 'ellipsis-line1'">{{item.comment}}</div></el-col>
                    <el-col :span="4">
                      <el-link href="javascript:void(0);" type="primary" @click="replyHandle(item)">回复</el-link>
                      <el-tooltip class="item" effect="light" content="点赞" placement="bottom">
                        <i class="el-icon-lx-like comment-like">{{item.praise}}</i>
                      </el-tooltip>
                      <div class="comment-time">{{item.createDate}}</div>
                    </el-col>
                  </el-row>
                </div>
              </template>
              <div class="parent-input">
                <!-- 父评论发表评论 -->
                <el-row v-if="activeNames.indexOf(item.commentId) != -1 && item.open">
                  <el-col :span="3"></el-col>
                  <el-col :span="20">
                    <el-input v-model="item.input" type="textarea" placeholder="请输入，最多256字符" maxlength="256" :rows="2"></el-input>
                  </el-col>
                </el-row>
                <el-row v-if="activeNames.indexOf(item.commentId) != -1 && item.open">
                  <el-col :span="3"></el-col>
                  <el-col :span="10">
                    <el-button size="mini" type="primary" plain class="submit-comment" @click="submitComment(item.commentId,item.input)">确定</el-button>
                    <el-button size="mini" type="danger" plain class="submit-comment" @click="item.open = false">取消回复</el-button>
                  </el-col>
                </el-row>
              </div>
              <!-- 子评论 -->
              <div class="comment-div" v-for="subItem in item.subList">
                <el-row>
                  <el-col :span="1"></el-col>
                  <el-col :span="1"><img :src="subItem.photo" class="comment-img"/></el-col>
                  <el-col :span="2">
                    <el-tooltip class="item" effect="light" :content="subItem.userName" placement="bottom">
                      <el-link :href="subItem.userId ? '/blog?id=' + subItem.userId : '#'" target="_blank" type="primary" class="sub-comment-name ellipsis-line1">{{subItem.userName}}</el-link>
                    </el-tooltip>
                    <div class="sub-comment-reply">回复</div>
                    <el-tooltip class="item" effect="light" :content="subItem.replyName" placement="bottom">
                      <div class="ellipsis-line1">{{subItem.replyName}}</div>
                    </el-tooltip>
                  </el-col>
                  <el-col :span="20"><div class="sub-comment-text">{{subItem.comment}}</div></el-col>
                </el-row>
                <el-row>
                  <el-col :span="4"></el-col>
                  <el-col :span="1">
                    <el-tooltip class="item" effect="light" content="点赞" placement="bottom">
                      <i class="el-icon-lx-like comment-like">{{subItem.praise}}</i>
                    </el-tooltip>
                  </el-col>
                  <el-col :span="1"><el-link class="comment-back" href="javascript:void(0);" type="primary" @click="replyHandle(subItem)">回复</el-link></el-col>
                  <el-col :span="2"><div class="sub-comment-time">{{subItem.createDate}}</div></el-col>
                </el-row>
                <!-- 子评论发表评论 -->
                <el-row v-if="subItem.open">
                  <el-col :span="4"></el-col>
                  <el-col :span="19">
                    <el-input v-model="subItem.input" type="textarea" placeholder="请输入，最多256字符" maxlength="256" :rows="2"></el-input>
                  </el-col>
                </el-row>
                <el-row v-if="subItem.open">
                  <el-col :span="4"></el-col>
                  <el-col :span="10">
                    <el-button size="mini" type="primary" plain class="submit-comment" @click="submitComment(subItem.commentId,subItem.input)">确定</el-button>
                    <el-button size="mini" type="danger" plain class="submit-comment" @click="subItem.open = false">取消回复</el-button>
                  </el-col>
                </el-row>
              </div>
              <div v-if="item.more" class="parent-more">
                <el-link href="javascript:void(0);" type="primary" @click="loadChildrenData(item.commentId,item.subList)">查看更多</el-link>
              </div>
            </el-collapse-item>
          </el-collapse>
          <div v-if="parentMore" class="parent-more">
            查看更多
          </div>
        </ul>
      </div>
    </el-card>
  </div>
</template>

<script>
import {getCurrentInstance,ref} from "vue";
import utils from "../../../utils/utils";
import {ElMessage} from "element-plus";
import request from "../../../utils/request";

export default {
  name: "Comment",
  setup(){
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    //博客id
    const blogId = utils.getUrlParam("bid");
    //评论列表
    const list = ref([]);
    //展开面板对象
    const activeNames = ref([]);
    //是否打开评论
    const openInput = ref(false);
    //新增评论内容
    const inputContent = ref(null);
    //父评论是否还有更多
    const parentMore = ref(false);
    let index = 0;
    //加载父评论数据
    const loadParentData = () =>{
      if (index < 10){
        list.value.push({
          commentId:index++,
          userId:"admin",
          userName:"超级管理员",
          praise: "0",
          input:"",
          open:false,
          photo:"src/assets/img/img.jpg",
          comment:"在界面中一致：所有的元素和结构需保持一比如：设计样式、图标和文本、元素的位置等。在界面中一致：所有的元素和结构需保持文本、元素的位置等。在界面中一致：所有的元素和结构需保持文本、元素的位置等。在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。",
          createDate:"2020-01-01",
          more:true,
          subList: [
            {
              commentId:index++,
              userId:"www",
              userName:"wenzday",
              praise: "0",
              input:"",
              open:false,
              replyName:"超级管理员的元素和结构需保持一致",
              photo:"src/assets/img/img.jpg",
              comment:"在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和设需保持一致，比如：设计样式、，比如：设计样式、图标和设需保持一致，比如：设计样式、图标和文本、元素的位置，比如：设计样式、图标和设需保持一致，比如：设计样式、图标和文本、元素的位置，比如：设计样式、图标和设需保持一致，比如：设计样式、图标和文本、元素的位置，比如：设计样式、图标和设需保持一致，比如：设计样式、图标和文本、元素的位置图标和文本、元素的位置等。在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。",
              createDate:"2020-01-01",
            },{
              commentId:index++,
              userId:"test",
              userName:"test",
              praise: "0",
              input:"",
              open:false,
              replyName:"wenzday的元素和结构需保持一致",
              photo:"src/assets/img/img.jpg",
              comment:"在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和设需保持一致，比如：设计样式、图标和文本、元素的位置等。在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。",
              createDate:"2020-01-01",
            }
          ]
        });
      }
    }
    //加载子评论数据
    const loadChildrenData = (parentId,subList) =>{
      subList.push({
        commentId:index++,
        userId:"test",
        userName:"test",
        praise: "0",
        input:"",
        open:false,
        replyName:"wenzday的元素和结构需保持一致",
        photo:"src/assets/img/img.jpg",
        comment:"在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和设需保持一致，比如：设计样式、图标和文本、元素的位置等。在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。",
        createDate:"2020-01-01",
      });
    }
    // 点击回复按钮处理
    const replyHandle = (item) =>{
      if(utils.isLogin()){
        item.open = true;
      }else {
        ElMessage.info('请登录');
      }
    }
    // 点击评论处理
    const openInputHandle = () =>{
      if(utils.isLogin()){
        openInput.value = true;
      }else {
        ElMessage.info('请登录');
      }
    }
    // 评论提交
    const submitComment = (cmtId,text) => {
      ElMessage.info("cmtId="+cmtId+",text="+text);
    }
    // 评论新增
    const addComment = () => {
      if(inputContent.value){
        axios.$http.post(request.addComment, {text:inputContent.value,bid:blogId}).then(function (res) {
          if(res.code === 200){
            if(res.data){
              inputContent.value = null;
              list.value.unshift(res.data);
            }
            ElMessage.success('评论成功');
          }else {
            ElMessage.error('评论失败');
          }
        });
      }else {
        ElMessage.error('评论内容不能为空');
      }
    }
    return {list,inputContent,openInput,activeNames,parentMore,openInputHandle,loadParentData,submitComment,loadChildrenData,replyHandle,addComment}
  }
}
</script>

<style scoped>
.list{
  height: 500px;
}
.list-children{
  height: 300px;
}
::-webkit-scrollbar {
  height: 0;
  width: 0;
  color: transparent;
}
.comment-img{
  width: 35px;
  height: 35px;
  border-radius: 50%;
  margin-top: 5px;
  margin-bottom: 5px;
}
.sub-comment-name{
  margin-top: 10px;
}
.sub-comment-text{
  margin-top: 10px;
}
.comment-text{
  margin-top: 15px;
  line-height: 20px;
  margin-right: 10px;
}
.comment-time{
  font-size: 10px;
  color: #999697;
  float: right;
}
.sub-comment-time{
  margin-top: 15px;
  color: #999697;
  font-size: 10px;
  float: right;
  margin-right: 5px;
}
.sub-comment-reply{
  color: #999697;
}
.comment-like{
  margin-top: 15px;
  margin-left: 5px;
}
.comment-back{
  margin-top: 10px;
}
.comment-div{
  margin-top: 15px;
  width: 100%;
}
.ellipsis-line1{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
.ellipsis-line2{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 2; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
.ellipsis-line5{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 5; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
.submit-comment{
  margin-top: 5px;
}
.parent-input{
  margin-top: 20px;
}
.parent-more{
  text-align: center;
  margin-top: 10px;
  color: #999697;
}
</style>