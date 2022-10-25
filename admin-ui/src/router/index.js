import {createRouter, createWebHistory} from "vue-router";

const home = () => import('@/views/home')
const login = () => import("@/views/login")

const routes = [
    { path: "/", redirect: "/home"},
    {
        path: '/home',
        name: 'home',
        component: home
    },
    {
        path: "/login",
        name: "login",
        component: login
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;