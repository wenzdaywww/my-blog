<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 发布博客
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-form label-width="120px" :model="blogText" :rules="blogRules" ref="blogForm">
        <el-row>
          <el-col :span="16">
            <el-form-item label="主题：" prop="theme">
              <el-input v-model="blogText.theme" maxlength="300"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4"></el-col>
          <el-col :span="4"></el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="分类：">
              <el-select v-model="blogText.class" placeholder="分类" multiple="true" class="handle-select mr10 blog-class">
                <el-option v-for="item in classArr" :key="item.classCode" :label="item.className" :value="item.classCode"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分组：">
              <el-select v-model="blogText.group" placeholder="分组" class="handle-select mr10 blog-group">
                <el-option v-for="item in groupArr" :key="item.bgId" :label="item.groupName" :value="item.bgId"></el-option>
              </el-select>
              <span class="add-group" @click="groupVisible=true">新增分组</span>
            </el-form-item>
          </el-col>
          <el-col :span="8"></el-col>
        </el-row>
      </el-form>
      <!-- 博客内容编辑 -->
      <div class="mgb20" ref='editor'></div>
      <el-button type="primary" @click="submitBlog">提交</el-button>
    </div>
    <!-- 新增/编辑弹出框 -->
    <el-dialog title="新增分组" v-model="groupVisible" width="20%">
      <el-form label-width="120px" :model="group" :rules="groupRules" ref="groupForm">
        <el-form-item label="分组名称：" prop="name">
          <el-input v-model="group.name" class="el-input-custom"></el-input>
        </el-form-item>
        <div class="btn-save">
          <span class="dialog-footer">
            <el-button type="primary" @click="addGroup">确 定</el-button>
            <el-button @click="groupVisible = false">取 消</el-button>
          </span>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import WangEditor from "wangEditor";
import {ref, reactive, onMounted, onBeforeUnmount, getCurrentInstance} from "vue";
import {ElMessage} from "element-plus";
export default {
  name: "EditBlog",
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    // 博客分类列表
    const classArr = ref([]);
    // 博客分组列表
    const groupArr = ref([]);
    //博客编辑器对象
    const editor = ref(null);
    // 新增博客的规则校验
    const blogRules = {
      theme : [
        { required: true, message: "主题不能为空", trigger: "blur" }
      ]
    };
    // 新增分组的规则校验
    const groupRules = {
      name : [
        { required: true, message: "分组名称不能为空", trigger: "blur" }
      ]
    };
    // 博客表单对象
    const blogForm = ref(null);
    //博客对象
    const blogText = reactive({
      theme: "",
      class:"",
      group:"",
      html: ""
    });
    // 是否显示新增分组弹窗
    const groupVisible = ref(false);
    //博客分组对象
    const group = reactive({
      name: ""
    });
    // 新增分组表单对象
    const groupForm = ref(null);
    // 博客内容
    let instance;
    onMounted(() => {
      instance = new WangEditor(editor.value);
      instance.config.zIndex = 1;
      instance.create();
    });
    onBeforeUnmount(() => {
      instance.destroy();
      instance = null;
    });
    /** 博客提交 **/
    const submitBlog = () => {
      blogForm.value.validate((valid) => {
        if (valid) {
          blogText.html = instance.txt.html();
          if(blogText.html){
            ElMessage.success('上传博客');
            console.log(blogText.html);
          }else {
            ElMessage.error("博客内容不能为空");
          }
        } else {
          return false;
        }
      });
    };
    //新增分组提交
    const addGroup = () => {
      groupForm.value.validate((valid) => {
        if (valid) {
          ElMessage.success('新增分组成功');
        } else {
          return false;
        }
      });
    };
    return {
      classArr,groupArr,editor,blogText,blogRules,blogForm,groupVisible,group,groupRules,groupForm,
      submitBlog,addGroup
    };
  },
};
</script>

<style>
.blog-class{
  width: 100%;
}
.blog-group{
  width: 80%;
}
.add-group{
  margin-left: 8px;
  color: blue;
}
.add-group:hover{
  margin-left: 8px;
  color: red;
  cursor:default;
}
.btn-save{
  text-align: center;
}
</style>