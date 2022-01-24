<template>
    <div class="about">
        <v-header />
        <v-sidebar />
        <div class="content-box" :class="{ 'content-collapse': collapse }">
<!--          //TODO 2021/12/26 22:19 第一次导航栏时标签栏未展示，先隐藏待处理-->
<!--            <v-tags></v-tags>-->
            <div class="content">
                <router-view v-slot="{ Component }">
                    <transition name="move" mode="out-in">
                        <keep-alive :include="tagsList">
                            <component :is="Component" />
                        </keep-alive>
                    </transition>
                </router-view>
            </div>
        </div>
    </div>
</template>
<script>
import {computed, ref,provide } from "vue";
import { useStore } from "vuex";
import vHeader from "../../components/Header.vue";
import vSidebar from "../../components/Sidebar.vue";
import vTags from "../../components/Tags.vue";
export default {
    components: {
        vHeader,
        vSidebar,
        vTags,
    },
    setup() {
        //向子组件传值
        provide("isAdmin",true);
        const store = useStore();
        const tagsList = computed(() =>
            store.state.tagsList.map((item) => item.name)
        );
        const collapse = computed(() => store.state.collapse);
        return {
            tagsList,
            collapse,
        };
    },
};
</script>
<style>
</style>