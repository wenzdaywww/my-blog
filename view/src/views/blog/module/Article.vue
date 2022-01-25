<template>
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
        <el-tooltip class="item" effect="light" content="收藏" placement="bottom">
          <i class="el-icon-lx-favor color-grad padding-left10">{{blog.collect}}</i>
        </el-tooltip>
        <el-tooltip class="item" effect="light" content="评论" placement="bottom">
          <i class="el-icon-lx-comment color-grad padding-left10">{{blog.comment}}</i>
        </el-tooltip>
      </el-col>
      <el-col :span="6"><span class="blog-time color-grad">{{blog.createTime}}</span></el-col>
    </el-row>
    <el-row>

    </el-row>
  </el-card>
  <el-card class="blog-card">
    <div class="editor-div" ref="articleRef"></div>
  </el-card>
</template>

<script>
import WangEditor from "wangEditor";
import {getCurrentInstance, onBeforeUnmount, onMounted, reactive, ref} from "vue";
import utils from "../../../utils/utils";

export default {
  name: "article",
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    //博客文章富文本编辑器
    const articleRef = ref(null);
    //博客id
    const blogId = utils.getUrlParam("id");
    //博客文章信息
    let blog = reactive({
      blogId: 0,
      title: "python和java的相爱相杀，数据可视化告诉你该学哪一个？",
      content: "",
      browse:0,
      praise:1,
      collect:0,
      comment:2,
      createTime:"2021-01-01 12:12:30"
    });
    // 博客内容
    let articleEditor;
    onMounted(() => {
      articleEditor = new WangEditor(articleRef.value);
      articleEditor.config.zIndex = 1;
      articleEditor.config.height = 600;
      articleEditor.create();
    });
    onBeforeUnmount(() => {
      articleEditor.destroy();
      articleEditor = null;
    });
    //查询博客文章信息
    const getBlogArticle = () => {
      if(blogId){
      //   request.$http.post("api/blog/browse/article/"+blogId,null).then(function (res) {
      //     if(res.code === 200){
      //
      //     }
      //   });
      }
    }
    getBlogArticle();
    return {blog,articleRef};
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
.padding-left10{
  padding-left: 10px;
}
.blog-time{
  color: #999697;
  float: right;
  margin-right: 5px;
}
</style>
