export default [
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
                meta: { title: 'wWw博客-首页' },
                component: () => import ("../views/blog/index/Index.vue")
            },{
                path: "/blogger",
                name: "userBlog",
                meta: { title: 'wWw博客-{0}的博客' },
                component: () => import ("../views/blog/blogger/Blogger.vue")
            },{
                path: "/article",
                name: "BlogArticle",
                meta: { title: 'wWw博客-' },
                component: () => import ("../views/blog/article/Article.vue")
            }
        ]
    },
    {
        path: "/",
        name: "Admin",
        meta: {
            title: '个人中心'
        },
        component: () => import ("../views/admin/Home.vue"),
        children: [
            {
                path: "/user",
                name: "router_user",
                meta: { title: 'wWw博客-个人资料' },
                component: () => import ("../views/admin/info/User.vue")
            }, {
                path: "/users",
                name: "router_users",
                meta: { title: 'wWw博客-用户列表' },
                component: () => import ("../views/admin/UserList.vue")
            }, {
                path: "/news",
                name: "router_news",
                meta: { title: 'wWw博客-消息中心' },
                component: () => import ("../views/admin/News.vue")
            }, {
                path: "/menu",
                name: "router_menu",
                meta: { title: 'wWw博客-菜单管理' },
                component: () => import ("../views/admin/MenuList.vue")
            }, {
                path: "/editor",
                name: "router_editor",
                meta: { title: 'wWw博客-发布博客' },
                component: () => import ("../views/blog/EditBlog.vue")
            }, {
                path: "/blog",
                name: "router_blog",
                meta: { title: 'wWw博客-博客管理' },
                component: () => import ("../views/blog/ManageBlog.vue")
            }, {
                path: "/follow",
                name: "router_follow",
                meta: { title: 'wWw博客-我的关注' },
                component: () => import ("../views/blog/Follow.vue")
            }, {
                path: "/fans",
                name: "router_fans",
                meta: { title: 'wWw博客-我的粉丝' },
                component: () => import ("../views/blog/Fans.vue")
            }, {
                path: "/collect",
                name: "router_collect",
                meta: { title: 'wWw博客-我的收藏' },
                component: () => import ("../views/blog/Collect.vue")
            }, {
                path: "/monitor",
                name: "router_monitor",
                meta: { title: 'wWw博客-后台监控' },
                component: () => import ("../views/admin/Monitor.vue")
            }
        ]
    }, {
        path: "/403",
        name: "403",
        meta: { title: "403" },
        component: () => import ("../views/common/403.vue")
    },{
        path: "/404",
        name: "404",
        meta: { title: "404" },
        component: () => import ("../views/common/404.vue")
    },{
        path: "/:catchAll(.*)", // 页面404跳转
        redirect: "/404"
    }
]
