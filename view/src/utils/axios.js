import axios from "axios";
import qs from "qs";
import router from '../router';
import {ElMessage} from 'element-plus';
import store from "../store";
import utils from './utils';
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
        if(response.status == 200){
            //Promise.resolve在then方法使用
            return Promise.resolve(response);
        }else {
            return Promise.reject(response);
        }
    },
    error => {
        if (error.response.status == 403){
            utils.clearCookie();
            store.dispatch("clearRouter",null);
            router.push("/");//首页页面
        }else if(error.response.status == 500){
            //响应码500，则提示报错信息
            ElMessage.error(error.response.data.data);
            //Promise.reject在catch方法使用
            return Promise.reject(error.response.data);
        }else {
            ElMessage.error("未知错误");
            return Promise.reject(error.response.data);
        }
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
                if (err && err.data){
                    reject(err);
                }
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
                if (err && err.data){
                    reject(err);
                }
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
                if (err && err.data){
                    reject(err);
                }
            })
        });
    }
};