<!-- 我的关注 -->
<template>
  <div>
    <div class="crumb-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-likefill"></i> 我的关注({{query.totalNum}}人)
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-row>
      <el-col :span="16">
        <div v-if="followShow">
          <el-card>
            <ul class="list" v-infinite-scroll="loadUserList"  :infinite-scroll-distance="10" :infinite-scroll-delay="800" :infinite-scroll-immediate="true" style="overflow:auto">
              <el-card class="blog-card" v-for="item in userList">
                <el-row>
                  <el-col :span="2">
                    <img :src="item.photo" class="image-photo"/>
                  </el-col>
                  <el-col :span="20">
                    <el-row>
                      <el-link class="info-name" :href="item.userId ? '/blogger?id='+item.userId : '#'" target="_blank">{{item.userName}}</el-link>
                    </el-row>
                    <el-row><div class="ellipsis-line1 user-brief">{{item.brief}}</div></el-row>
                  </el-col>
                  <el-col :span="2">
                    <el-button style="width: 100%" size="mini" round @click="followAuthor(item)" class="follow-btn">{{item.fan ? "取消关注" : "关注"}}</el-button>
                  </el-col>
                </el-row>
              </el-card>
            </ul>
          </el-card>
        </div>
        <div v-else="followShow">
          <el-card>
            <div class="no-follow">
              哦吼，你还没有关注好友哟！
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="8"></el-col>
    </el-row>
  </div>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import request from "../../utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "Follow",
  setup(){
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    // 关注列表数据
    let userList = ref([]);
    //关注列表展示控制
    let followShow = ref(true);
    //查询条件
    const query = reactive({
      pageNum: 1,
      totalNum:0
    });
    //是否还有更多
    const more = ref(true);
    // 加载关注列表
    const loadUserList = () =>{
      if (more.value){
        axios.$http.get(request.followList+query.pageNum, null).then(function (res) {
          if(res.code === 200){
            if (res.data){
              query.pageNum++;//页码增加
              query.totalNum = res.totalNum;
              more.value = res.data.length >= res.pageSize;//判断是否还有更多
              res.data.forEach (temp => {
                userList.value.push(temp);//列表添加
              });
            }else {//没有数据
              more.value = false;//没有更多
              followShow.value = query.pageNum == 1 ? false : followShow.value;//设置是否展示关注列表
            }
          }
        });
      }
    }
    // 取消关注博主
    const followAuthor = (item) => {
      axios.$http.post(request.follow, {id:item.userId}).then(function (res) {
        if(res.code === 200){
          item.fan = res.data;
        }else {
          ElMessage.error('取消或关注博主失败');
        }
      });
    }
    return {query,followShow,userList,loadUserList,followAuthor}
  }
}
</script>

<style scoped>
.list{
  height: calc(80vh);
}
.image-photo{
  width: 35px;
  height: 35px;
  border-radius: 50%;
  margin-top: 5px;
  margin-bottom: 5px;
  margin-left: 10px;
}
::-webkit-scrollbar {
  height: 0;
  width: 0;
  color: transparent;
}
.ellipsis-line1{
  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/
  -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
}
.user-brief{
  margin-top: 10px;
  font-size: 13px;
  color: #999697;
}
.follow-btn{
  margin-top: 15px;
}
.no-follow{
  text-align: center;
  margin-top: 10px;
  color: #999697;
}
</style>