import {createRouter, createWebHistory} from "vue-router";
const modules = import.meta.glob("../views/**/**.vue");

let routes = [
    {
        path: "/",
        redirect: "/login"
    },{
        path: "/login",
        name: "login",
        meta: {
            title: "登录"
        },
        component: () => import ("../views/admin/Login.vue")
    }, {
        path: "/404",
        name: "404",
        meta: {
            title: "404"
        },
        component: () => import ("../views/404.vue")
    },{
        path: "/:catchAll(.*)", // 页面404跳转
        redirect: "/404"
    }
];
const router = createRouter({
    history: createWebHistory(), //createWebHashHistory地址带#，createWebHistory不带#
    routes
});
let indexPath = null; //动态添加router后跳转的页面
let initRouter = []; //后端查询的router数据
// 初始化菜单路由
export const initUserRouter = function (routerData) {
    if(routerData){
        indexPath = null;
        initRouter = [];
        hanleChilden(initRouter,routerData);
        for (var i = 0; i < initRouter.length; i++){
            router.addRoute(initRouter[i]);
        }
        router.push(indexPath);
    }
}
// 递归处理子菜单
const hanleChilden = function (parent,children) {
    children.forEach (temp => {
        const vuePath = '../views/'+temp.vuePath;
        const tempRouter = {
            path: temp.menuUrl,
            name: temp.menuCode,
            component: modules[vuePath],
            children: []
        };
        if (temp.menuName){
            tempRouter.meta = { title: temp.menuName };
        }
        parent.push(tempRouter);
        if(temp.children){ // 有子router
            hanleChilden(tempRouter.children,temp.children);
        }else {
            //获取第一个子路由路径
            if (!indexPath){
                indexPath = temp.menuUrl;
            }
        }
    });
}
// 路由跳转前的处理
router.beforeEach((to, from, next) => {
    document.title = "my-blog";
    if (localStorage.getItem('userId') && routes.length === router.getRoutes().length){

    }
    next();
});

export default router;