export default {
    //获取url的参数值
    getUrlParam (name){
        let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        let r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
        if (r != null) {
            return unescape(r[2]);
        }
        return null;
    },
    /** 获取cookie中的值 **/
    getCookie(name) {
        let arr;
        let reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg)){
            return (arr[2]);
        }else{
            return null;
        }
    },
    /** 已登录 **/
    isLogin() {
        let token = this.getCookie("access_token");
        return token ? true : false;
    },
    /** 获取cookie中的用户ID和角色集合
     * {
     *     userId:用户ID,
     *     roles:角色集合
     * } **/
    getUser(){
        let user = this.getCookie("user");
        return user ? JSON.parse(unescape(user)) : {};
    },
    /** 获取当前登录的用户ID **/
    getUserId(){
        let user = this.getUser();
        return user ? user.userId : null;
    },

}