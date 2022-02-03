<!-- 博客收藏夹 -->
<template>
  <div>
    <el-dialog title="博客收藏夹" v-model="addVisible" width="400px">
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
import {getCurrentInstance, inject, ref} from "vue";
import request from "../../../utils/request";
import {ElMessage} from "element-plus";
import utils from "../../../utils/utils";

export default {
  name: "CollectGroup",
  emits: ['blogCollectAdd','findBlogList'], //父组件中为子组件定义的@方法
  setup(props,{emit}) { //调用父组件方法，必须有props,{emit}。否则调用失败
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    //博客id
    const blogId = ref(0);
    // 编辑时弹窗和保存
    const addVisible = ref(false);
    // 是否是修改博客收藏夹位置
    const updateFlag = ref(true);
    // 收藏分组数据
    const groupArr = ref([]);
    //选中的收藏夹id
    const selectCgId = ref(0);
    //新建收藏夹名称
    const newCollectName = ref(null);
    //选择收藏夹处理
    const selectCollect = (item) => {
      selectCgId.value = selectCgId.value === item.cgId ? null : item.cgId;
    }
    //查询收藏夹
    const findCollectGroup = () => {
      axios.$http.get(request.collectGroup,null).then(function (res) {
        if(res.code === 200){
          groupArr.value = res.data;
        }
      });
    }
    findCollectGroup();
    //新建收藏夹
    const newCollect = () => {
      axios.$http.post(request.addCollectGroup,{name:newCollectName.value}).then(function (res) {
        if(res.code === 200){
          newCollectName.value = null;
          findCollectGroup();
          ElMessage.success('新建收藏夹成功');
        }else {
          ElMessage.error('新建收藏夹失败');
        }
      });
    }
    //添加收藏
    const saveCollect = () => {
      let cgId = selectCgId.value > 0 ? selectCgId.value : "";
      //修改博客收藏夹位置
      if (updateFlag.value){
        axios.$http.post(request.modifyCollect, {bid:blogId.value,cgid:cgId}).then(function (res) {
          if(res.code === 200){
            if(res.data){
              emit('findBlogList',null);//调用父组件Collect.vue的findBlogList方法
              addVisible.value = false;
              ElMessage.success('修改成功');
            }
          }else {
            ElMessage.error('修改失败');
          }
        });
      }else {//添加到收藏夹
        axios.$http.post(request.addCollect, {bid:blogId.value,cgid:cgId}).then(function (res) {
          if(res.code === 200){
            if(res.data){
              emit('blogCollectAdd',null);//调用父组件Content.vue的blogCollectAdd方法
              addVisible.value = false;
              ElMessage.success('收藏成功');
            }
          }else {
            ElMessage.error('收藏失败');
          }
        });
      }
    }
    /**
     * 打开博客收藏夹弹窗
     * @param bid 博客id
     * @param flag 是否是修改博客收藏夹位置，true或false
     */
    const openCollectDialog = (bid,flag) => {
      blogId.value = bid;
      addVisible.value = true;
      updateFlag.value = flag;
      selectCgId.value = 0;
    }
    return {addVisible,selectCgId,openCollectDialog,groupArr,newCollectName,saveCollect,newCollect,selectCollect};
  }
}
</script>

<style scoped>
.mag-top20{
  margin-top: 20px;
}
.mag-left10{
  margin-left: 10px;
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