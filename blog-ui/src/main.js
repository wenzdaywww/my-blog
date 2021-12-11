import {createApp} from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import axios from './utils/request';
import installElementPlus from './plugins/element';
import './assets/css/icon.css';
import cookies from "vue-cookies";
const app = createApp(App);
installElementPlus(app);
app.use(store)
    .use(router)
    .mount('#app');
app.config.globalProperties.$http = axios;
app.use(cookies);
cookies.set('token',"");
