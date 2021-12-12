<template>
  <div class="sidebar">
    <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
             text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
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
import {computed, ref, getCurrentInstance} from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import cookies from "vue-cookies";
export default {
  setup() {
    // 接口请求
    const request = getCurrentInstance().appContext.config.globalProperties;
    const items = ref([]);
    // 获取菜单
    const getData = () => {
      request.$http.get("api/admin/user/menu", {userId:cookies.get('userId')}).then(function (res) {
        if(res.code === 200){
          items.value = res.data;
        }
      })
    };
    getData();
    // 路由
    const route = useRoute();
    const onRoutes = computed(() => {
      return route.path;
    });
    const store = useStore();
    const collapse = computed(() => store.state.collapse);

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
