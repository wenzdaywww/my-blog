<template>
  <div>
    <div class="crumb-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-edit"></i> 发布博客
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-row>
      <el-col :span="20">
        <el-card>
          <div>
            <el-form label-width="120px" :model="blogText" :rules="blogRules" ref="blogForm">
              <el-row>
                <el-col :span="24">
                  <el-form-item label="主题：" prop="title">
                    <el-input v-model="blogText.title" maxlength="300"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="标签：">
                    <el-select v-model="blogText.tagIds" placeholder="分类" multiple="true" class="handle-select mr10 blog-class">
                      <el-option v-for="item in tagArr" :key="item.tagId" :label="item.tagName" :value="item.tagId"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="分组：">
                    <el-select v-model="blogText.groupId" placeholder="分组" class="handle-select mr10 blog-group">
                      <el-option v-for="item in groupArr" :key="item.groupId" :label="item.groupName" :value="item.groupId"></el-option>
                    </el-select>
                    <span class="add-group" @click="groupVisible=true">新增分组</span>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <!-- 博客内容编辑 -->
            <span class="blog-text-title">博客内容</span>
            <div class="mgb20 editor-div" ref='editor'></div>
            <el-button type="primary" @click="submitBlog" :disabled="submitDisabled">提交</el-button>
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
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import WangEditor from "wangEditor";
import {ref, reactive, onMounted, onBeforeUnmount, getCurrentInstance} from "vue";
import {ElMessage} from "element-plus";
import request from "../../utils/request";

export default {
  name: "EditBlog",
  setup() {
    // 接口请求
    const axios = getCurrentInstance().appContext.config.globalProperties;
    // 标签列表
    const tagArr = ref([]);
    // 博客分组列表
    const groupArr = ref([]);
    //博客编辑器对象
    const editor = ref(null);
    // 新增博客的规则校验
    const blogRules = {
      title : [
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
      title: "",
      tagIds:"",
      groupId:"",
      content: ""
    });
    // 是否显示新增分组弹窗
    const groupVisible = ref(false);
    // 提交按钮
    const submitDisabled = ref(false);
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
      instance.config.height = 600;
      instance.config.showLinkImg = false;
      instance.config.showLinkVideo = false;
      instance.config.uploadImgShowBase64 = true;
      instance.create();
    });
    onBeforeUnmount(() => {
      instance.destroy();
      instance = null;
    });
    // 获取博客分类
    const getBlogClass = () => {
      axios.$http.post(request.tagList,null).then(function (res) {
        if(res.code === 200){
          tagArr.value = res.data;
        }
      });
    }
    getBlogClass();
    // 获取博客分组
    const getBlogGroup = () => {
      axios.$http.post(request.groupList,null).then(function (res) {
        if(res.code === 200){
          groupArr.value = res.data;
        }
      });
    }
    getBlogGroup();
    /** 博客提交 **/
    const submitBlog = () => {
      blogForm.value.validate((valid) => {
        if (valid) {
          blogText.content = instance.txt.html();
          if(blogText.content){
            submitDisabled.value = true;
            axios.$http.post(request.publishBlog,blogText).then(function (res) {
              if(res.code === 200){
                ElMessage.success('发布博客成功');
                instance.txt.clear();
                blogText.title = null;
                blogText.classIds = null;
                blogText.bgId = null;
                blogText.content = null;
              }else {
                ElMessage.error('发布博客失败');
              }
              submitDisabled.value = false;
            });
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
          axios.$http.post(request.addGroup,group).then(function (res) {
            if(res.code === 200){
              groupVisible.value = false;
              ElMessage.success('新增分组成功');
              getBlogGroup();
            }else {
              ElMessage.error('新增分组失败');
            }
          });
        } else {
          return false;
        }
      });
    };
    return {
      submitDisabled,tagArr,groupArr,editor,blogText,blogRules,blogForm,groupVisible,group,groupRules,groupForm,
      submitBlog,addGroup
    };
  },
};
</script>

<style>
body {
  background-color: #f3f5f5;
}
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
.blog-text-title{
  color: #606266;
}
</style>