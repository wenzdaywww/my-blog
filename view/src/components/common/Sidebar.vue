<template>
  <div class="sidebar" style="margin-top: 5px;">
    <el-menu class="sidebar-el-menu el-menu-vertical-demo" :collapse="collapse" :default-active="onRoutes" unique-opened router>
      <template v-for="item in items">
        <template v-if="item.subMenu">
          <el-submenu :index="item.menuUrl" :key="item.menuUrl">
            <template #title>
              <i :class="item.menuIcon"></i>
              <span>{{ item.menuName }}</span>
            </template>
            <template v-for="subItem in item.subMenu">
              <el-submenu v-if="subItem.subMenu" :index="subItem.menuUrl" :key="subItem.menuUrl">
                <i :class="subItem.menuIcon"></i>
                <template #title>{{ subItem.menuName }}</template>
                <el-menu-item v-for="(threeItem, i) in subItem.subMenu" :key="i" :index="threeItem.menuUrl">
                  {{ threeItem.menuName }}
                </el-menu-item>
              </el-submenu>
              <el-menu-item v-else :index="subItem.menuUrl" :key="subItem.menuUrl">
                <i :class="subItem.menuIcon"></i>
                <span>{{ subItem.menuName }}</span>
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.menuUrl" :key="item.menuUrl">
            <i :class="item.menuIcon"></i>
            <template #title>{{ item.menuName }}</template>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
import {computed, ref, getCurrentInstance, reactive} from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import utils from "../../utils/utils";

export default {
  setup() {
    // 路由
    const router = useRoute();
    const store = useStore();
    const collapse = computed(() => store.state.collapse);
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    //菜单列表
    const items = ref([]);
    // 获取菜单
    const getData = () => {
      if(utils.getToken()){
        request.$http.get("api/base/user/menu", null).then(function (res) {
          if(res.code === 200){
            items.value = res.data;
          }
        });
      }else {
        items.value = [];
      }
    };
    getData();

    const onRoutes = computed(() => {
      return router.path;
    });

    return {
      items,
      onRoutes,
      collapse,
    };
  },
};
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
  background-color: #46a1ff;
  margin-top: 10px;
}
.sidebar::-webkit-scrollbar {
  width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}
.sidebar > ul {
  height: 100%;
}
</style>
