// 配置请求地址信息
export default {
    /** uaa应用 **/
    //获取token信息
    getToken: "api/uaa/oauth/token",
    //退出
    logout: "api/uaa/logout",
    /** base应用**/
    //注册用户
    register: "api/base/new/user",
    //查询所有角色
    roleList: "api/base/admin/role",
    //查询所有菜单信息
    menuList: "api/base/admin/menus",
    //删除菜单信息
    deleteMenu: "api/base/admin/down",
    //编辑菜单信息
    editMenu: "api/base/admin/edit",
    //查询用户列表信息
    userList: "api/base/admin/users",
    //修改用户状态
    userState: "api/base/admin/state",
    //查询用户信息
    userInfo: "api/base/user/info",
    //查询用户路由权限
    userRouter: "api/base/user/router",
    //查询用户菜单列表
    userMenu: "api/base/user/menu",
    //修改密码
    modifyPwd: "api/base/user/pwd",
    //修改用户信息
    editInfo: "api/base/user/edit",
    //上传头像
    uploadPhoto: "api/base/user/photo",
    //查询单个数据字典数据
    code: "api/base/user/code/",
    //查询多个数据字典数据
    codes: "api/base/user/codes",
    /** blog应用 **/
    //查询博客文章
    article: "api/blog/browse/article/",
    //查询博主信息
    author: "api/blog/browse/author",
    //查询博主的博客列表
    bloList: "api/blog/browse/list",
    //查询博主的博客分组列表
    browseGroup: "api/blog/browse/group",
    //查询博主的博客标签列表
    browseTag: "api/blog/browse/tag",
    //查询热门博客列表
    hotRank: "api/blog/browse/hot-rank",
    //评论列表
    commentList: "api/blog/browse/cmt-list",
    //推荐博客列表
    tipList: "api/blog/browse/tip-list",
    //获取所有博客标签
    tagList: "api/blog/edit/tag",
    //获取所有博客分组
    groupList: "api/blog/edit/group",
    //发布博客
    publishBlog: "api/blog/edit/new",
    //添加博客分组
    addGroup: "api/blog/edit/new-group",
    //博客管理博客列表
    manageBlog: "api/blog/edit/blogs",
    //用户的博客标签列表
    userTags: "api/blog/edit/tags",
    //查询博客的分组及标签信息
    blogTagGroup: "api/blog/edit/btg/",
    //修改博客的分组及标签信息
    updateTagGroup: "api/blog/edit/newtg",
    //关注博主
    follow: "api/blog/user/follow/",
    //新增/回复评论
    addComment: "api/blog/user/comment",
    //查询用户统计信息
    userCount: "api/blog/user/count",
    //查询关注列表
    followList: "api/blog/user/follows/",
    //查询粉丝列表
    fansList: "api/blog/user/fans/",
    //新增收藏夹
    addCollectGroup: "api/blog/user/newgp",
    //查询收藏夹
    collectGroup: "api/blog/user/cltgp",
    //博客添加收藏
    addCollect: "api/blog/user/collect",
    //博客收藏列表查询
    collectList: "api/blog/user/collects",
    //修改博客收藏夹位置
    modifyCollect: "api/blog/user/newclt",
    //博客点赞
    addPraise: "api/blog/user/praise/"
}