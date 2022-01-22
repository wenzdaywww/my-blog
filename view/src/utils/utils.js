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
        var arr;
        var reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg)){
            return (arr[2]);
        }else{
            return null;
        }
    },
    /** 获取cookie中的token值 **/
    getToken() {
        return this.getCookie("access_token");
    }
}