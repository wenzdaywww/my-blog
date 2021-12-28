import vue from '@vitejs/plugin-vue';
import { defineConfig, loadEnv } from 'vite'

export default  ({ mode }) => defineConfig({
    base: './',
    plugins: [vue()],
    optimizeDeps: {
        include: ['schart.js']
    },
    server : {
        proxy: {
            '/api': {
                target: loadEnv(mode, process.cwd()).VITE_API_URL, //接口域名
                changeOrigin: true,      //是否跨域
                rewrite: (path) => path.replace(/^\/api/, ''), //路径重写
                ws: true,            //是否代理 websockets
                secure: false         //是否https接口
            }
        }
    }
})