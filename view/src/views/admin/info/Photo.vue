<template>
  <div>
    <el-card class="info-card">
      <template #header>
        <div class="clearfix">
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
              <el-link href="#" type="primary">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.blogs}}</div>
                  博客
                </div>
              </el-link>
            </el-col>
            <el-col :span="4">
              <el-link href="#" type="primary">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.follows}}</div>
                  <div>关注</div>
                </div>
              </el-link>
            </el-col>
            <el-col :span="4">
              <el-link href="#" type="primary">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.fans}}</div>
                  <div>粉丝</div>
                </div>
              </el-link>
            </el-col>
            <el-col :span="4">
              <el-link href="#" type="primary">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.likes}}</div>
                  <div>获赞</div>
                </div>
              </el-link>
            </el-col>
            <el-col :span="4">
              <el-link href="#" type="primary">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.comments}}</div>
                  <div>评论</div>
                </div>
              </el-link>
            </el-col>
            <el-col :span="4">
              <el-link href="#" type="primary">
                <div class="grid-cont-right">
                  <div class="grid-num">{{user.collects}}</div>
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

export default {
  name: "Photo",
  components: {
    VueCropper
  },
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 用户信息
    let user = reactive({
      photo : "src/assets/img/img.jpg",
      follows : 0,
      fans : 0,
      blogs : 0,
      likes : 0,
      comments : 0,
      collects : 0
    });
    // 获取用户数据
    const getUserData = () => {
      request.$http.get("api/base/user/info",null).then(function (res) {
        if(res.code === 200){
          if(res.data.photo){
            user.photo = res.data.photo;
          }
        }
      });
    };
    getUserData();
    // 获取用户统计数据
    const getUserCount = () => {
      request.$http.get("api/blog/user/count",null).then(function (res) {
        if(res.code === 200){
          user.blogs = res.data.blogs;
          user.follows = res.data.follows;
          user.fans = res.data.fans;
          user.likes = res.data.likes;
          user.comments = res.data.comments;
          user.collects = res.data.collects;
        }
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
      request.$http.upload("api/base/user/photo",fd,{'Content-Type': 'multipart/form-data'}).then(function (res){
        if(res.code === 200){
          ElMessage.success('上传成功');
          dialogVisible.value = false;
          getUserData();
        }else {
          ElMessage.error("上传失败");
        }
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
    return {user,  cropper, imgSrc, cropImg,showDialog, dialogVisible, setImage, cropImage, uploadImg};
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
.grid-content {
  display: flex;
  align-items: center;
  height: 100px;
}
.mgb20 {
  margin-bottom: 10px;
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
.info-card{
  width: 100%;
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
.el-col-crad{
  height: 600px;
}
.grid-num {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}
</style>