<!-- 用户头像 -->
<template>
  <div>
    <el-card>
      <template #header>
        <div>
          <span>基本信息</span>
        </div>
      </template>
      <div class="info">
        <el-row>
          <div class="user-info">
            <div class="info-image" @click="showDialog">
              <img :src="user.photo" class="user-avator" alt/>
              <span class="info-edit">
                  <i class="el-icon-lx-camerafill"></i>
              </span>
            </div>
          </div>
        </el-row>
        <div class="user-info-list">
          <el-row>
            <el-col :span="4">
              <el-link href="javascript:void(0);" type="primary" @click="toLink('/blog')">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.blog}}</div>
                  博客
                </div>
              </el-link>
            </el-col>
            <el-col :span="4">
              <el-link href="javascript:void(0);" type="primary" @click="toLink('/follow')">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.follow}}</div>
                  <div>关注</div>
                </div>
              </el-link>
            </el-col>
            <el-col :span="4">
              <el-link href="javascript:void(0);" type="primary" @click="toLink('/fans')">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.fans}}</div>
                  <div>粉丝</div>
                </div>
              </el-link>
            </el-col>
            <el-col :span="4">
              <el-link href="#" type="primary">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.praise}}</div>
                  <div>获赞</div>
                </div>
              </el-link>
            </el-col>
            <el-col :span="4">
              <el-link href="#" type="primary">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.comment}}</div>
                  <div>评论</div>
                </div>
              </el-link>
            </el-col>
            <el-col :span="4">
              <el-link href="javascript:void(0);" type="primary" @click="toLink('/collect')">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.collect}}</div>
                  <div>收藏</div>
                </div>
              </el-link>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-card>
    <!-- 头像上传-->
    <el-dialog title="裁剪图片" v-model="dialogVisible" width="600px">
      <vue-cropper ref="cropper" :src="imgSrc" :with-credentials="true" :ready="cropImage" :zoom="cropImage" :cropmove="cropImage" style="width: 100%; height: 400px"></vue-cropper>
      <template #footer>
         <span class="dialog-footer">
           <el-button class="crop-demo-btn" type="primary">选择图片
             <input class="crop-input" type="file" name="image" accept="image/*" @change="setImage" />
           </el-button>
           <el-button type="primary" @click="uploadImg">上传并保存</el-button>
         </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {getCurrentInstance, reactive, ref} from "vue";
import "cropperjs/dist/cropper.css";
import {ElMessage} from "element-plus";
import VueCropper from "vue-cropperjs";
import request from "../../../utils/request";
import utils from "../../../utils/utils";
import {useRouter} from "vue-router";

export default {
  name: "Photo",
  components: {
    VueCropper
  },
  setup() {
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    // 路由
    const router = useRouter();
    //是否有user角色
    const isUser = utils.isUser();
    // 用户信息
    let user = reactive({
      photo : "static/img/img.jpg",
      follow : 0,
      fans : 0,
      blog : 0,
      praise : 0,
      comment : 0,
      collect : 0
    });
    // 获取用户数据
    const getUserData = () => {
      axios.$http.get(request.userInfo,null).then(function (res) {
        if(res.data.photo){
          user.photo = res.data.photo;
        }
      });
    };
    getUserData();
    // 获取用户统计数据
    const getUserCount = () => {
      axios.$http.get(request.userCount,null).then(function (res) {
        user.blog = res.data.blog;
        user.follow = res.data.follow;
        user.fans = res.data.fans;
        user.praise = res.data.praise;
        user.comment = res.data.comment;
        user.collect = res.data.collect;
      });
    };
    getUserCount();
    //图片上传弹出控制位
    const dialogVisible = ref(false);
    //选择的图片
    const imgSrc = ref("");
    //剪辑后的图片对象
    const cropImg = ref("");
    //图片剪辑对象
    const cropper = ref(null);
    //选中的图片文件
    let file = reactive({
      name : user.userId + ".jpg"
    });
    //连接跳转
    const toLink = (path) => {
      if (isUser){
        router.push(path);
      }
    };
    //打开图片上传弹窗
    const showDialog = () => {
      dialogVisible.value = true;
      imgSrc.value = user.photo;
      cropper.value = user.photo;
    };
    //设置选中的图片
    const setImage = (e) => {
      file = e.target.files[0];
      if (!file.type.includes("image/")) {
        return;
      }
      if(file.size > 10*1024*1024){
        ElMessage.error("文件大小不能超过5M");
        return;
      }
      const reader = new FileReader();
      reader.onload = (event) => {
        dialogVisible.value = true;
        imgSrc.value = event.target.result;
        cropper.value && cropper.value.replace(event.target.result);
      };
      //转化为base64
      reader.readAsDataURL(file);
    };
    //图片剪辑
    const cropImage = () => {
      cropImg.value = cropper.value.getCroppedCanvas().toDataURL();
    };
    // 上传图片
    const uploadImg = () => {
      let fd = new FormData();//通过form数据格式来传
      fd.append("photo", base64ToFile(cropImg.value,file.name)); //传文件
      axios.$http.upload(request.uploadPhoto,fd,{'Content-Type': 'multipart/form-data'}).then(function (res){
        ElMessage.success('上传成功');
        dialogVisible.value = false;
        getUserData();
      });
    };
    // 将裁剪的图片转为文件
    const base64ToFile = (urlData, fileName) =>{
      let arr = urlData.split(',');
      if (arr.length == 1){
        return new File(urlData, fileName);
      }
      let mime = arr[0].match(/:(.*?);/)[1];
      let bytes = atob(arr[1]); // 解码base64
      let n = bytes.length
      let ia = new Uint8Array(n);
      while (n--) {
        ia[n] = bytes.charCodeAt(n);
      }
      return new File([ia], fileName, { type: mime });
    };
    return {user,isUser, cropper, imgSrc, toLink,cropImg,showDialog, dialogVisible, setImage, cropImage, uploadImg};
  }
}
</script>

<style scoped>
.info {
  text-align: center;
  padding: 35px 0;
}
.info-image {
  position: relative;
  margin: auto;
  display: flex;
  width: 200px;
  height: 200px;
  align-items: center;
  background: #f8f8f8;
  border: 1px solid #eee;
  border-radius: 50%;
  overflow: hidden;
  border-bottom: 2px solid #ccc;
}
.info-image img {
  width: 100%;
  height: 100%;
}
.info-edit {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 0;
  transition: opacity 0.3s ease;
}
.info-edit i {
  color: #eee;
  font-size: 25px;
}
.info-image:hover .info-edit {
  opacity: 1;
}
.crop-demo-btn {
  position: relative;
}
.crop-input {
  position: absolute;
  width: 100px;
  height: 40px;
  left: 0;
  top: 0;
  opacity: 0;
  cursor: pointer;
}
.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
}
.grid-num {
  font-size: 30px;
  font-weight: bold;
}
.user-info-list {
  font-size: 14px;
  line-height: 25px;
  margin-top: 20px;
}
.user-avator {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}
.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 20px;
  width: 100%;
}
.grid-num {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}
</style>