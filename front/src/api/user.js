import request from '@/utils/request'

// 注册
export function registered(params) {
  return request({
    url: '/user/registered',
    method: 'get',
    params
  })
}

// 登录
export function login(params) {
  return request({
    url: '/user/login',
    method: 'get',
    params
  })
}

// 重新获取账户信息
export function getInfo() {
  return request({
    url: '/user/info',
    method: 'get',
  })
}

// 退出登录
export function logout() {
  return request({
    url: '/user/exit',
    method: 'get'
  })
}

// 修改用户名
export function updateUserName(params) {
  return request({
    url: '/user/updateUserName',
    method: 'get',
    params
  })
}

// 修改密码
export function updatePassword(params) {
  return request({
    url: '/user/updatePassword',
    method: 'get',
    params
  })
}

// 添加图书类型
export function insertBookCategory(params) {
  return request({
    url: '/user/insertBookCategory',
    method: 'get',
    params
  })
}

// 删除书籍分类
export function deleteBookCategory(params) {
  return request({
    url: '/user/deleteBookCategory',
    method: 'get',
    params
  })
}

// 修改指定的图书类型
export function updateBookCategory(params) {
  return request({
    url: '/user/updateBookCategory',
    method: 'get',
    params
  })
}
