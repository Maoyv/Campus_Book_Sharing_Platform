import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: '主页',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '主页', icon: 'table' }
    }]
  },
  // {
  //   path: '/file',
  //   component: Layout,
  //   redirect: '/file',
  //   children: [{
  //     path: 'file',
  //     name: '文件操作',
  //     component: () => import('@/views/file/index'),
  //     meta: { title: '文件操作', icon: 'table' }
  //   }]
  // },

]

// 动态路由  tourists:游客；user:用户；admin:管理员；superAdmin：超级管理员(仅负责管理账户)
export const asyncRoutes  = [

  {
    path: '/campusBook',
    component: Layout,
    redirect: '/campusBook/book',
    name: '校园图书',
    meta: {
      title: '校园图书',
      icon: 'nested',
      roles:["user","admin","tourists"]
    },
    children: [
      {
        path: 'book',
        component: () => import('@/views/campusBook/book/index'),
        name: '校园图书',
        meta: { title: '校园图书',roles:["user","admin","tourists"] },
      },
      {
        path: 'bookAppointment',
        component: () => import('@/views/campusBook/bookAppointment/index'),
        name: '借阅管理',
        meta: { title: '借阅管理',roles:["admin","user"] },
      },
      {
        path: 'bookCategory',
        component: () => import('@/views/campusBook/bookCategory/index'),
        name: '图书类别管理',
        meta: { title: '图书类别管理',roles:["admin"] },
      },
    ]
  },
  {
    path: '/ebook',
    component: Layout,
    redirect: '/ebook/library',
    name: '电子书',
    meta: {
      title: '电子书',
      icon: 'example',
      roles:["user","admin","tourists"]
    },
    children: [
      {
        path: 'library',
        component: () => import('@/views/ebook/library/index'),
        name: '书库',
        meta: { title: '书库',roles:["admin","user","tourists"] },
      },
      {
        path: 'ebookShelf',
        component: () => import('@/views/ebook/ebookShelf/index'),
        name: '书架',
        meta: { title: '书架',roles:["admin","user"] },
      },
      {
        path: 'uploadList',
        component: () => import('@/views/ebook/uploadList/index'),
        name: '上传列表',
        meta: { title: '上传列表',roles:["admin","user"] },
      },
      {
        path: 'auditList',
        component: () => import('@/views/ebook/auditList/index'),
        name: '审核列表',
        meta: { title: '审核列表',roles:["admin"] },
      },
    ]
  },
  {
    path: '/account',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '个人中心',
        component: () => import('@/views/account'),
        meta: { title: '个人中心', icon: 'user',roles:["user","admin"] }
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
