<template>
    <div class="about">
        <v-header />
        <div>
            <div class="content">
                <router-view v-slot="{ Component }">
                </router-view>
            </div>
        </div>
    </div>
</template>
<script>
import vHeader from "../../components/Header.vue";
import {ref,provide } from "vue";
export default {
    components: {
        vHeader
    },
    setup() {
        //向子组件传值
        provide("isAdmin",false);
        return {
        };
    },
};
</script>
