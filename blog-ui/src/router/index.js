import {createRouter, createWebHistory} from "vue-router";
import Home from "../views/admin/Home.vue";

const routes = [
    {
        path: "/",
        redirect: "/login"
    }, {
        path: "/",
        name: "home",
        component: Home,
        children: [
            {
                path: "/home",
                name: "index",
                meta: {
                    title: "首页"
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/admin/Index.vue")
            }, {
                path: "/table",
                name: "userList",
                meta: {
                    title: "用户信息"
                },
                component: () => import ( /* webpackChunkName: "table" */ "../views/admin/UserList.vue")
            }, {
                path: "/charts",
                name: "basecharts",
                meta: {
                    title: "图表"
                },
                component: () => import ( /* webpackChunkName: "charts" */ "../views/BaseCharts.vue")
            }, {
                path: "/form",
                name: "baseform",
                meta: {
                    title: "表单"
                },
                component: () => import ( /* webpackChunkName: "form" */ "../views/BaseForm.vue")
            }, {
                path: "/tabs",
                name: "tabs",
                meta: {
                    title: "消息中心"
                },
                component: () => import ( /* webpackChunkName: "tabs" */ "../views/admin/News.vue")
            }, {
                path: "/donate",
                name: "donate",
                meta: {
                    title: "鼓励作者"
                },
                component: () => import ( /* webpackChunkName: "donate" */ "../views/Donate.vue")
            }, {
                path: "/permission",
                name: "permission",
                meta: {
                    title: "权限管理",
                    permission: true
                },
                component: () => import ( /* webpackChunkName: "permission" */ "../views/Permission.vue")
            }, {
                path: "/i18n",
                name: "i18n",
                meta: {
                    title: "国际化语言"
                },
                component: () => import ( /* webpackChunkName: "i18n" */ "../views/I18n.vue")
            }, {
                path: "/upload",
                name: "upload",
                meta: {
                    title: "上传插件"
                },
                component: () => import ( /* webpackChunkName: "upload" */ "../views/Upload.vue")
            }, {
                path: "/icon",
                name: "icon",
                meta: {
                    title: "自定义图标"
                },
                component: () => import ( /* webpackChunkName: "icon" */ "../views/Icon.vue")
            }, {
                path: "/404",
                name: "404",
                meta: {
                    title: "找不到页面"
                },
                component: () => import (/* webpackChunkName: "404" */ "../views/404.vue")
            }, {
                path: "/403",
                name: "403",
                meta: {
                    title: "没有权限"
                },
                component: () => import (/* webpackChunkName: "403" */ "../views/403.vue")
            }, {
                path: "/user",
                name: "user",
                meta: {
                    title: "个人中心"
                },
                component: () => import (/* webpackChunkName: "user" */ "../views/admin/User.vue")
            }, {
                path: "/editor",
                name: "editor",
                meta: {
                    title: "富文本编辑器"
                },
                component: () => import (/* webpackChunkName: "editor" */ "../views/Editor.vue")
            }
        ]
    }, {
        path: "/login",
        name: "login",
        meta: {
            title: "登录"
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/admin/Login.vue")
    }
];

const router = createRouter({
    history: createWebHistory(), //createWebHashHistory地址带#，createWebHistory不带#
    routes
});

router.beforeEach((to, from, next) => {
    document.title = "my-blog";
    if (to.path === "/login"){
        next();
    }else {
        const token = localStorage.getItem("token");
        //token失效则调整登录页面
        if (token === null || token === undefined || token === "null" || token === ""){
            next("/login");
        }else {
            next();
        }
    }
});

export default router;