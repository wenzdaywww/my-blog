    <!-- 上传头像弹窗-->
    <el-dialog title="上传头像" v-model="photoVisible" width="300px">
      <el-form :model="form">
        <el-form-item ref="uploadForm">
          <el-upload ref="upload"
                     action="#"
                     accept="image/png,image/gif,image/jpg,image/jpeg"
                     list-type="picture-card"
                     limit=1
                     :auto-upload="false"
                     :on-success="handleAvatarSuccess"
                     :on-exceed="handleExceed"
                     :before-upload="handleBeforeUpload"
                     :on-preview="handlePictureCardPreview"
                     :on-remove="handleRemove"
                     :on-change="imgChange">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="photoVisible">
            <img width="100%" :src="form.photo" alt="">
          </el-dialog>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="uploadFile">立即上传</el-button>
          <el-button size="small" @click="photoVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
	
	// 文件超出个数限制时的钩子
    const handleExceed = (files, fileList) => {
      ElMessage.error('只能选择一张图片');
    };
    // 上传文件之前的钩子
    const handleBeforeUpload = (file) => {
      if (!(file.type === 'image/png' || file.type === 'image/gif' || file.type === 'image/jpg' || file.type === 'image/jpeg')) {
        ElMessage.error('请上传格式为image/png, image/gif, image/jpg, image/jpeg的图片');
      }
      let size = file.size / 1024 / 1024 / 2
      if (size > 2) {
        ElMessage.error('图片大小必须小于2M');
      }
      let fd = new FormData();//通过form数据格式来传
      fd.append("photo", file); //传文件
      //添加zuul解决文件上传问题
      request.$http.upload("api/zuul/admin/common/up",fd,{'Content-Type': 'multipart/form-data'}).then(function (res){
        if(res.code === 200){
          photoVisible.value = false;
          form.photo = res.data;
        }
      });
    };
    // 点击文件列表中已上传的文件时的钩子
    const handlePictureCardPreview = (file) => {
    };
    // 文件列表移除文件时的钩子
    const handleRemove = (file, fileList) => {
      hideUploadVisible.value = fileList.length >= 1;
    };
    //图片变化
    const imgChange = (files, fileList) =>{
      hideUploadVisible.value = fileList.length >= 1;
    };
    //上传
    const uploadFile = () => {
      upload.value.submit();
    };
    // 添加成功处理
    const handleAvatarSuccess = (res, file) => {

    };