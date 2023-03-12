import axios from "axios";
import qs from "qs";
import router from '../router';
import {ElMessage} from 'element-plus';
import store from "../store";
//创建请求对象
const http = axios.create();
//设置超时
http.defaults.timeout = 180000;
http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8";
http.interceptors.request.use(
    config => {
        config.withCredentials = true;
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);
http.interceptors.response.use(
    response => {
        //TODO redis的token丢失且浏览器cooke没丢失，此时无法重新登录，待处理
        //接口返回403
        if ((response.data && (response.data.code == 401 || response.data.code == 403)) || response.status == 401 || response.status == 403) {
            store.dispatch("clearRouter",null);
            let routerTemp = store.state.routerList;//获取允许的router
            if(routerTemp.includes(window.location.pathname)){
                router.push(window.location.pathname);//跳转权限不足页面
            }else {
                router.push("/404");//跳转404页面
            }
        }else if(response.status == 200){
            return Promise.resolve(response);
        }else {
            return Promise.reject();
        }
    },
    error => {
        ElMessage.error("请求失败");
        return Promise.reject();
    }
);
export default {
    post(url, data) {
        return new Promise((resolve, reject) => {
            http({
                method: 'post',
                url,
                data: qs.stringify(data)
            }).then(res => {
                if(res && res.data){
                    resolve(res.data);
                }
            }).catch(err => {
                reject(err);
            });
        });
    },
    upload(url, data,headers) {
        return new Promise((resolve, reject) => {
            http({
                method: 'post',
                url,
                data: data,
                headers : headers
            }).then(res => {
                if(res && res.data){
                    resolve(res.data);
                }
            }).catch(err => {
                reject(err);
            });
        });
    },
    get(url,data) {
        return new Promise((resolve, reject) => {
            http({
                method: 'get',
                url,
                params: data,
            }).then(res => {
                if(res && res.data){
                    resolve(res.data);
                }
            }).catch(err => {
                reject(err);
            })
        });
    }
};