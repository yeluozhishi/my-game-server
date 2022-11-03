import axios from 'axios'
import {getToken} from "@/utils/auth";
import cache from '@/plugins/cache'
import errorCode from "@/utils/errorCode";
import {ElMessage} from 'element-plus'
import store from "@/store";

// 是否显示重新登录
export let  isShowBackOnline = {show: false}

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

// 创建axios实例
const service = axios.create({
    // axios中请求配置有baseURL选项，表示请求URL公共部分
    baseURL: process.env.VUE_APP_BASE_API,
    // 超时
    timeout: 1000
})

// request拦截器
service.interceptors.request.use(config => {
    // 是否需要设置 token
    const needToken = false
    // 是否需要防止数据重复提交
    const isRepeatSubmit = false

    if (!needToken && getToken()){
        config.headers['token'] = getToken()
    }

    console.log(config.data, config.headers['token'])

    if (!isRepeatSubmit) {
        const requestObj = {
            url: config.url,
            data: typeof config.data === 'object' ? JSON.stringify(config.data) : config.data,
            time: new Date().getTime()
        }
        const sessionObj = cache.session.getJSON('sessionObj')
        if (sessionObj === undefined || sessionObj === null || sessionObj === '') {
            cache.session.setJSON('sessionObj', requestObj)
        } else {
            const s_url = sessionObj.url;                  // 请求地址
            const s_data = sessionObj.data;                // 请求数据
            const s_time = sessionObj.time;                // 请求时间
            const interval = 1000;                         // 间隔时间(ms)，小于此时间视为重复提交
            if (s_data === requestObj.data && requestObj.time - s_time < interval && s_url === requestObj.url) {
                const message = '数据正在处理，请勿重复提交';
                console.warn(`[${s_url}]: ` + message)
                return Promise.reject(new Error(message))
            } else {
                cache.session.setJSON('sessionObj', requestObj)
            }
        }
    }
    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

service.interceptors.response.use(res => {
    // 未设置状态码则默认成功状态
    const code = Number(res.data.code) || 200;
    // 获取错误信息
    const msg = errorCode[code] || res.data.err_msg || errorCode['default']

    // 二进制数据则直接返回
    if(res.request.responseType ===  'blob' || res.request.responseType ===  'arraybuffer'){
        return res.data
    }

    if (code === 401) {
        if (!isShowBackOnline.show) {
            isShowBackOnline.show = true;
            ElMessage.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
                    confirmButtonText: '重新登录',
                    cancelButtonText: '取消',
                    type: 'warning'
                }
            ).then(() => {
                isShowBackOnline.show = false;
                store.dispatch('LogOut').then(() => {
                    location.href = '/index';
                })
            }).catch(() => {
                isShowBackOnline.show = false;
            });
        }
        return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
    } else if (code === 500) {
        ElMessage.error(msg)
        return Promise.reject(new Error(msg))
    } else if (code !== 200 && code !== 0) {
        ElMessage.error(msg)
        return Promise.reject('error')
    } else {
        return res.data
    }
})

export default service