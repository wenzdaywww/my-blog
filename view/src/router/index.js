import {createRouter, createWebHistory} from "vue-router";
import routesDate from './routes';
const modules = import.meta.glob("../views/**/**.vue");// 接口请求
//路由信息
let routes = routesDate;
/**
 * 创建路由
 * @type {Router}
 */
const router = createRouter({
    history: createWebHistory(), //createWebHashHistory地址带#，createWebHistory不带#
    routes
});
/**
 * 初始化路由
 * @param routerData 路由数据
 */
export const initUserRouter = function (routerData) {
    if(routerData){
        //TODO 2022/7/3 由于动态加载路由后，刷新页面会404，所以暂弃用
        // hanleChilden(homeRouter.children,routerData);
        // router.addRoute(homeRouter);
        // localStorage.setItem("router",JSON.stringify(routerData));
        let routerList = [];
        routerData.forEach(item => {
            routerList.push(item.menuUrl);
        });
        localStorage.setItem("router",JSON.stringify(routerList));
    }
}
/**
 * 递归处理子路由
 * @deprecated 由于动态加载路由后，刷新页面会404，所以暂弃用
 * @param parent 父路由
 * @param children 子路由
 */
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
/**
 * 路由跳转前的处理
 */
router.beforeEach((to, from, next) => {
    document.title = to.meta.title;
    if (from.name){
        next();
    }else {
        let routerList = ['/index', '/blogger', '/article', '/404'];
        if(routerList.includes(to.path)){
            next();
        }else {
            routerList = JSON.parse(localStorage.getItem("router"));
            if (routerList.includes(to.path)){
                next();
            }else {
                next("/404");
            }
        }
    }
});

export default router ;
