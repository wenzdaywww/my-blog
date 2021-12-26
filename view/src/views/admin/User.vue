<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-s-custom"></i> 个人中心
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-row :gutter="20">
      <!--  基础信息-->
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="clearfix">
              <span>基本信息</span>
            </div>
          </template>
          <div class="info">
            <div class="info-image" @click="showDialog">
              <img :src="form.photo" />
              <span class="info-edit">
                  <i class="el-icon-lx-camerafill"></i>
              </span>
            </div>
            <div class="info-name"></div>
            <div class="info-desc">{{form.brief}}</div>
          </div>
        </el-card>
      </el-col>
      <!-- 账户编辑-->
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="clearfix">
              <span>信息修改</span>
            </div>
          </template>
          <el-form label-width="90px" :model="form" :rules="editRules" ref="editForm">
            <el-form-item label="用户ID："> {{ form.userId }} </el-form-item>
            <el-form-item label="用户名称" prop="userName">
              <el-input v-model="form.userName" maxlength="100" placeholder="请输入用户名称"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-radio v-model="form.sex" label="1">男</el-radio>
              <el-radio v-model="form.sex" label="0">女</el-radio>
            </el-form-item>
            <el-form-item label="手机号" prop="phoneNum">
              <el-input v-model="form.phoneNum" maxlength="11" placeholder="请输入手机号码"
                        οninput="value=value.replace(/[^\d]/g,'');if(value.length > 11)value = value.slice(0, 11)"></el-input>
            </el-form-item>
            <el-form-item label="生日">
              <el-date-picker v-model="form.birthday" type="date" value-format="YYYY-MM-DD" format="YYYY年MM月DD日" placeholder="请选择出生日期" style="width: 100%">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" maxlength="100" placeholder="请输入邮箱地址"></el-input>
            </el-form-item>
            <el-form-item label="个性签名：">
              <el-input v-model="form.brief" maxlength="100" placeholder="请输入个性签名"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">保存</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
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
import VueCropper from "vue-cropperjs";
import "cropperjs/dist/cropper.css";
import {ElMessage} from "element-plus";

export default {
  name: "user",
  components: {
    VueCropper,
  },
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 新增用户的规则校验
    const editRules = {
      userName : [
        { required: true, message: "用户名称不能为空", trigger: "blur" }
      ],
      phoneNum: [
        { min: 11, message: "手机号格式不正确", trigger: "blur" },
        { type: 'number', message: '手机号格式不正确', trigger: 'blur',
          transform (value) {
            if(value){
              var phonereg = 11 && /^((13|14|15|16|17|18|19)[0-9]{1}\d{8})$/
              if (!phonereg.test(value)) {
                return false
              }else{
                return Number(value)
              }
            }
          }
        }
      ],
      email: [
        { type: 'string', message: '长度不能超过100位', trigger: 'blur', max: 100 },
        { type: 'string', message: '邮箱格式不正确', trigger: 'blur',
          transform (value) {
            if (value){
              if (!/^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(value)) {
                return true
              }else{
              }
            }
          }
        }
      ]
    };
    // 表单对象
    const editForm = ref(null);
    // 表单数据
    let form = reactive({
      userId: localStorage.getItem('userId'),
      userName : "",
      phoneNum : "",
      birthday : "",
      sex : "",
      photo : "src/assets/img/img.jpg",
      email : "",
      brief : ""
    });
    // 获取用户数据
    const getData = () => {
      request.$http.get("api/base/user/info",form).then(function (res) {
        if(res.code === 200){
          form.userName = res.data.userName;
          form.phoneNum = res.data.phoneNum;
          form.birthday = res.data.birthday;
          form.sex = res.data.sex;
          if(res.data.photo){
             form.photo = "api/base" + res.data.photo;
          }
          form.email = res.data.email;
          form.brief = res.data.brief;
        }
      });
    };
    getData();
    // 保存按钮
    const onSubmit = () => {
      editForm.value.validate((valid) => {
        if (valid) {
          request.$http.post("api/base/user/edit",form).then(function (res) {
            if(res.code === 200){
              ElMessage.success('修改成功');
              getData();
            }else {
              ElMessage.error(res.data);
            }
          });
        } else {
          return false;
        }
      });
    };
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
      name : form.userId + ".jpg"
    });
    //打开图片上传弹窗
    const showDialog = () => {
      dialogVisible.value = true;
      imgSrc.value = form.photo;
      cropper.value = form.photo;
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
      fd.append("userId", form.userId);
      request.$http.upload("api/base/user/photo",fd,{'Content-Type': 'multipart/form-data'}).then(function (res){
        if(res.code === 200){
          ElMessage.success('上传成功');
          dialogVisible.value = false;
          getData();
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
    return { editRules, editForm, form, onSubmit, cropper, imgSrc, cropImg,
      showDialog, dialogVisible, setImage, cropImage, uploadImg
    };
  },
};
</script>

<style scoped>
.info {
  text-align: center;
  padding: 35px 0;
}
.info-image {
  position: relative;
  margin: auto;
  width: 180px;
  height: 180px;
  background: #f8f8f8;
  border: 1px solid #eee;
  border-radius: 90px;
  overflow: hidden;
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
.info-name {
  margin: 15px 0 10px;
  font-size: 24px;
  font-weight: 500;
  color: #262626;
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
</style>