// 配置请求地址信息
export default {
    //查询用户信息
    userInfo: "api/base/user/info",
    //查询用户路由权限
    userRouter: "api/base/user/router",
    //查询用户菜单列表
    userMenu: "api/base/user/menu",
    //获取token信息
    getToken: "api/uaa/oauth/token",
    //查询所有角色
    roleList: "api/base/admin/role",
    //查询所有菜单信息
    menuList: "api/base/admin/menus",
    //删除菜单信息
    deleteMenu: "api/base/menu/down",
    //编辑菜单信息
    editMenu: "api/base/menu/edit",
    //修改密码
    modifyPwd: "api/base/user/pwd",
    //注册用户
    register: "api/base/new/user",
    //查询用户列表信息
    userList: "api/base/admin/users",
    //修改用户状态
    userState: "api/base/admin/state",
    //修改用户信息
    editInfo: "api/base/user/edit",
    //查询用户统计信息
    userCount: "api/blog/user/count",
    //上传头像
    uploadPhoto: "api/base/user/photo",
    //获取所有博客标签
    tagList: "api/blog/edit/tag",
    //获取所有博客分组
    groupList: "api/blog/edit/group",
    //发布博客
    publishBlog: "api/blog/edit/new",
    //添加博客分组
    addGroup: "api/blog/edit/new-group",
    //查询博客文章
    article: "api/blog/browse/article/",
    //查询博主信息
    author: "api/blog/browse/author",
    //关注博主
    follow: "api/blog/user/follow/",
    //查询博主的博客列表
    bloList: "api/blog/browse/list",
    //查询博主的博客分组列表
    browseGroup: "api/blog/browse/group",
    //查询博主的博客标签列表
    browseTag: "api/blog/browse/tag",
    //查询热门博客列表
    hotRank: "api/blog/browse/hot-rank",
    //新增/回复评论
    addComment: "api/blog/user/comment",
    //博客添加收藏
    addCollect: "api/blog/user/collect/",
    //评论列表
    commentList: "api/blog/browse/cmt-list",
    //推荐博客列表
    tipList: "api/blog/browse/tip-list",
    //退出
    logout: "api/uaa/logout"
}