import request from '@/utils/request'

// 取消预约借阅(删除)
export function deleteUserBook(params) {
  return request({
    url: '/userBook/deleteUserBook',
    method: 'get',
    params
  })
}

// 借阅图书(修改)
export function borrowingUserBook(params) {
  return request({
    url: '/userBook/borrowingUserBook',
    method: 'get',
    params
  })
}

// 归还图书(修改)
export function restoreUserBook(params) {
  return request({
    url: '/userBook/restoreUserBook',
    method: 'get',
    params
  })
}

// 查询
export function findAllUserBook(params) {
  return request({
    url: '/userBook/findAllUserBook',
    method: 'get',
    params
  })
}

