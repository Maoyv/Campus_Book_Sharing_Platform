import request from '@/utils/request'

// 条件查询
export function queryAllBook(params) {
  return request({
    url: '/book/queryAll',
    method: 'get',
    params
  })
}

// 新增数据
export function addBook(params) {
  return request({
    url: '/book/add',
    method: 'get',
    params
  })
}

// 编辑数据
export function updateBook(params) {
  return request({
    url: '/book/update',
    method: 'get',
    params
  })
}

// 删除数据
export function deleteByIdForBook(params) {
  return request({
    url: '/book/deleteById',
    method: 'get',
    params
  })
}

// 预约借阅(新增)
export function addUserBook(params) {
  return request({
    url: '/userBook/addUserBook',
    method: 'get',
    params
  })
}
