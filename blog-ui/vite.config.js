import vue from '@vitejs/plugin-vue';

export default {
    base: './',
    plugins: [vue()],
    optimizeDeps: {
        include: ['schart.js']
    },
    server : {
        proxy: {
            '/api': {
                target: 'http://localhost:8001/', //接口域名
                changeOrigin: true,      //是否跨域
                rewrite: (path) => path.replace(/^\/api/, ''), //路径重写
                ws: true,            //是否代理 websockets
                secure: false         //是否https接口
            }
        }
    }
}