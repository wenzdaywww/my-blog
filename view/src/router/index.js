import {createRouter, createWebHistory} from "vue-router";
const modules = import.meta.glob("../views/**/**.vue");// 接口请求
//用户后台路由
let homeRouter = {
    path: "/",
    name: "Admin",
    meta: {
        title: '个人中心'
    },
    component: () => import ("../views/admin/Home.vue"),
    children: []
};
// 初始路由
let routes = [
    {
        path: "/",
        redirect: "/index"
    },{
        path: "/",
        name: "Blog",
        component: () => import ("../views/blog/Home.vue"),
        children: [
            {
                path: "/index",
                name: "blogIndex",
                meta: {
                    title: 'wWw博客-首页'
                },
                component: () => import ("../views/blog/index/Index.vue")
            },{
                path: "/blogger",
                name: "userBlog",
                meta: {
                    title: 'wWw博客-{0}的博客'
                },
                component: () => import ("../views/blog/blogger/Blogger.vue")
            },{
                path: "/article",
                name: "BlogArticle",
                meta: {
                    title: 'wWw博客-'
                },
                component: () => import ("../views/blog/article/Article.vue")
            }
        ]
    },{
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
routes.push(homeRouter);
const router = createRouter({
    history: createWebHistory(), //createWebHashHistory地址带#，createWebHistory不带#
    routes
});
// 初始化菜单路由
export const initUserRouter = function (routerData) {
    if(routerData){
        hanleChilden(homeRouter.children,routerData);
        router.addRoute(homeRouter);
        localStorage.setItem("router",JSON.stringify(routerData));
    }
}
// 递归处理子菜单
const hanleChilden = function (parent,children) {
    children.forEach (temp => {
        const vuePath = '../views/'+temp.vuePath;
        const tempRouter = {
            path: temp.menuUrl,
            name: temp.menuCode,
            meta : { title: temp.menuName },
            component : modules[vuePath],
            children: []
        };
        parent.push(tempRouter);
        if(temp.children){ // 有子router
            hanleChilden(tempRouter.children,temp.children);
        }
    });
}
// 路由跳转前的处理
router.beforeEach((to, from, next) => {
    document.title = to.meta.title;
    if (router.getRoutes().length === 5 && localStorage.getItem('userId')){
        if(localStorage.getItem("router")){
            const routerList = JSON.parse(localStorage.getItem("router"));
            initUserRouter(routerList);
        }
    }
    //TODO 2021/12/26 19:34 刷新404，待处理
    next();
});

export default router ;
