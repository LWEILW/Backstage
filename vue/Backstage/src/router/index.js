import Vue from 'vue'
import Router from 'vue-router'

import Test from '@/view/Test.vue'

// 登录模块
import Login from '@/view/Login.vue'
// 首页模块
import HomePage from '@/view/HomePage.vue'
// 用户模块
import UserManage from '@/view/SystemManage/UserManage/UserManage.vue'
// 角色模块
import RoleManage from '@/view/SystemManage/RoleManage/RoleManage.vue'
// 权限模块
import PermissionManage from '@/view/SystemManage/PremissionManage/PermissionManage.vue'
// 菜单模块
import MenusManage from '@/view/SystemManage/MenusManage/MenusManage.vue'
// 文章模块
import ArticleManage from '@/view/ArticleManage/ArticleManage.vue'

Vue.use(Router)

const router = new Router({
  // history 模式,该模式下没有#前缀
  mode: 'history',
  // build打包添加的地方
  base: '/backstage/',
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/Test',
      name: 'Test',
      component: Test
    },
    {
      path: '/HomePage',
      name: 'HomePage',
      component: HomePage,
      meta: {
        title: '首页',
        keepAlive: true
      }
    },
    {
      path: '/UserManage',
      name: 'UserManage',
      component: UserManage,
      meta: {
        title: '用户管理',
        keepAlive: true
      }
    },
    {
      path: '/RoleManage',
      name: 'RoleManage',
      component: RoleManage,
      meta: {
        title: '角色管理',
        keepAlive: true
      }
    },
    {
      path: '/PermissionManage',
      name: 'PermissionManage',
      component: PermissionManage,
      meta: {
        title: '权限详情',
        keepAlive: true
      }
    },
    {
      path: '/MenusManage',
      name: 'MenusManage',
      component: MenusManage,
      meta: {
        title: '菜单',
        keepAlive: true
      }
    },
    {
      path: '/ArticleManage',
      name: 'ArticleManage',
      component: ArticleManage,
      meta: {
        title: '文章模块',
        keepAlive: true
      }
    },
  ]
})


// 挂载路由导航守卫
// router.beforeEach((to, from, next) => {
//   // to 将要访问的路径
//   // from 代表从哪个路径跳转而来
//   // next 是一个函数，表示放行
//   // next() 放行  next('/login') 强制跳转
//   if (to.path === '/login') return next();
//   // 获取token
//   const tokenStr = window.sessionStorage.getItem('token')
//   if (!tokenStr) return next('/login')
//   next()
// })


export default router
