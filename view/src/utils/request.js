import axios from "axios";
import qs from "qs";
import router from '../router';
import {ElMessage, ElMessageBox} from 'element-plus';
import {getCurrentInstance} from "vue";
//设置超时
axios.defaults.timeout = 10000;
axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8";
axios.interceptors.request.use(
    config => {
        config.withCredentials = true;
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);
axios.interceptors.response.use(
    response => {
        //接口返回403
        if ((response.data && (response.data.code == 401 || response.data.code == 403)) || response.status == 401 || response.status == 403) {
            localStorage.clear();
            router.push("/home?" + new Date().getTime());//跳转登录页面
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
            axios({
                method: 'post',
                url,
                data: qs.stringify(data)
            })
                .then(res => {
                    if(res && res.data){
                        resolve(res.data);
                    }
                })
                .catch(err => {
                    reject(err);
                });
        });
    },
    upload(url, data,headers) {
        return new Promise((resolve, reject) => {
            axios({
                method: 'post',
                url,
                data: data,
                headers : headers
            })
                .then(res => {
                    if(res && res.data){
                        resolve(res.data);
                    }
                })
                .catch(err => {
                    reject(err);
                });
        });
    },
    get(url,data) {
        return new Promise((resolve, reject) => {
            axios({
                method: 'get',
                url,
                params: data,
            })
                .then(res => {
                    if(res && res.data){
                        resolve(res.data);
                    }
                })
                .catch(err => {
                    reject(err);
                })
        });
    }
};