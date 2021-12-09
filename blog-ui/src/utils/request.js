import axios from "axios";
import qs from "qs";
import router from '../router';
import { ElMessageBox } from 'element-plus';
//设置超时
axios.defaults.timeout = 10000;
axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8";
axios.interceptors.request.use(
    config => {
        // 通过拦截request请求,主动为请求头,追加新属性 Authorization,等于 token 值
        if (config.url !== "api/admin/login"){
            config.headers.Authorization = "Bearer " + localStorage.getItem('token');
        }
        config.withCredentials = true;
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);
axios.interceptors.response.use(
    response => {
        if (response.status == 200) {
            //接口返回403则清除token
            if(response.data.code === 403){
                localStorage.setItem('token',"");
                localStorage.setItem('userId',"");
                ElMessageBox('请重新登录', '请重新登录', {
                    confirmButtonText: '确定',
                    callback: action  => {
                    }
                });
                //调整登录页面
                router.push("/login");
            }
            return Promise.resolve(response);
        } else {
            return Promise.reject(response);
        }
    },
    error => {
        console.log('请求异常：'+JSON.stringify(error));
        ElMessageBox('请求失败', '请求异常', {
            confirmButtonText: '确定',
            callback: action => {}
        });
    }
);
export default {
    post(url, data) {
        return new Promise((resolve, reject) => {
            axios({
                method: 'post',
                url,
                data: qs.stringify(data),
            })
                .then(res => {
                    resolve(res.data)
                })
                .catch(err => {
                    reject(err)
                });
        })
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
                    resolve(res.data)
                })
                .catch(err => {
                    reject(err)
                });
        })
    },
    get(url,data) {
        return new Promise((resolve, reject) => {
            axios({
                method: 'get',
                url,
                params: data,
            })
                .then(res => {
                    resolve(res.data)
                })
                .catch(err => {
                    reject(err)
                })
        })
    }
};