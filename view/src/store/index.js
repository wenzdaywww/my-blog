import {createStore} from 'vuex'

export default createStore({
    state: {
        tagsList: [], //标签栏信息
        routerList : [], //用户拥有的router信息
        collapse: false //侧边栏是否收缩
    },
    mutations: {
        // 添加router
        setRouter(data){
            state.routerList = data;
        },
        delTagsItem(state, data) {
            state.tagsList
                .splice(data.index, 1);
        },
        setTagsItem(state, data) {
            state.tagsList
                .push(data)
        },
        clearTags(state) {
            state.tagsList = []
        },
        closeTagsOther(state, data) {
            state.tagsList = data;
        },
        closeCurrentTag(state, data) {
            for (let i = 0, len = state.tagsList.length; i < len; i++) {
                const item = state.tagsList[i];
                if (item.path === data.$route.fullPath) {
                    if (i < len - 1) {
                        data.$router.push(state.tagsList[i + 1].path);
                    } else if (i > 0) {
                        data.$router.push(state.tagsList[i - 1].path);
                    } else {
                        data.$router.push("/");
                    }
                    statetagsList.splice(i, 1);
                    break;
                }
            }
        },
        // 侧边栏折叠
        handleCollapse(state, data) {
            state.collapse = data;
        }
    },
    actions: {},
    modules: {}
})