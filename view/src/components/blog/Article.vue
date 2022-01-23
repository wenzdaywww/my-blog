<template>
  <el-card>
    <span class="span-title">{{ blog.blogTheme }}</span>
  </el-card>
  <el-card class="blog-card">
    <div class="editor-div" ref="articleRef"></div>
  </el-card>
</template>

<script>
import WangEditor from "wangEditor";
import {getCurrentInstance, onBeforeUnmount, onMounted, reactive, ref} from "vue";
import utils from "../../utils/utils";

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
      blogTheme: "1234",
      blogContent: ""
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
.blog-detail{
  width: 100%;
}
</style>
