import {createRouter, createWebHistory} from "vue-router";
import Home from "../views/admin/Home.vue";
import cookies from "vue-cookies";

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
                path: "/user",
                name: "user",
                meta: {
                    title: "个人中心"
                },
                component: () => import (/* webpackChunkName: "user" */ "../views/admin/User.vue")
            }, {
                path: "/table",
                name: "userList",
                meta: {
                    title: "用户信息"
                },
                component: () => import ( /* webpackChunkName: "table" */ "../views/admin/UserList.vue")
            }, {
                path: "/tabs",
                name: "tabs",
                meta: {
                    title: "消息中心"
                },
                component: () => import ( /* webpackChunkName: "tabs" */ "../views/admin/News.vue")
            }, {
                path: "/menu",
                name: "menuList",
                meta: {
                    title: "菜单管理"
                },
                component: () => import ( /* webpackChunkName: "tabs" */ "../views/admin/MenuList.vue")
            }
        ]
    }, {
        path: "/login",
        name: "login",
        meta: {
            title: "登录"
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/admin/Login.vue")
    }, {
        path: "/404",
        name: "404",
        meta: {
            title: "404"
        },
        component: () => import ( /* webpackChunkName: "tabs" */ "../views/404.vue")
    },{
        path: "/:catchAll(.*)", // 页面404跳转
        redirect: "/404"
    }
];

const router = createRouter({
    history: createWebHistory(), //createWebHashHistory地址带#，createWebHistory不带#
    routes
});
// 路由跳转前的处理
router.beforeEach((to, from, next) => {
    document.title = "my-blog";
    if (to.path === "/login"){
        next();
    }else {
        const token = cookies.get("token");
        //token失效则调整登录页面
        if (token === null || token === undefined || token === "null" || token === ""){
            next("/login");
        }else {
            next();
        }
    }
});
export default router;