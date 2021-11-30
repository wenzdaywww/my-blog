import axios from "axios";
import qs from "qs";
import { ElMessageBox } from 'element-plus';
//请求地址
axios.defaults.baseURL = 'http://localhost:8000'
//post请求头
axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8";
//设置超时
axios.defaults.timeout = 10000;
axios.interceptors.request.use(
    config => {
        // 通过拦截request请求,主动为请求头,追加新属性 Authorization,等于 token 值
        if (config.url !== "/admin/login"){
            config.headers.Authorization = "Bearer " + localStorage.getItem('token');
        }
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
    get(url, data) {
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