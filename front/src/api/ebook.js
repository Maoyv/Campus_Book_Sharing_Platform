import request from '@/utils/request'

// 上传列表-条件查询
export function uploadListQueryAll(params) {
  return request({
    url: '/uploadList/queryAll',
    method: 'get',
    params
  })
}

// 上传列表-删除电子书查询
export function uploadListDelete(params) {
  return request({
    url: '/uploadList/delete',
    method: 'get',
    params
  })
}

// 上传列表-取消上传
export function uploadListCancel(params) {
  return request({
    url: '/uploadList/cancel',
    method: 'get',
    params
  })
}

// 上传列表-新增电子书
export function uploadListInsert(params) {
  return request({
    url: '/uploadList/insert',
    method: 'get',
    params
  })
}

// 审核列表-条件查询
export function auditListQueryAll(params) {
  return request({
    url: '/auditList/queryAll',
    method: 'get',
    params
  })
}

// 审核列表-上传成功
export function auditListAdopt(params) {
  return request({
    url: '/auditList/adopt',
    method: 'get',
    params
  })
}

// 审核列表-驳回
export function auditListReject(params) {
  return request({
    url: '/auditList/reject',
    method: 'get',
    params
  })
}

// 书库-条件查询
export function libraryQueryAll(params) {
  return request({
    url: '/library/queryAll',
    method: 'get',
    params
  })
}

// 书库-收藏
export function libraryCollection(params) {
  return request({
    url: '/library/collection',
    method: 'get',
    params
  })
}

// 书库-修改
export function libraryUpdate(params) {
  return request({
    url: '/library/update',
    method: 'get',
    params
  })
}

// 书库-删除
export function libraryDelete(params) {
  return request({
    url: '/library/delete',
    method: 'get',
    params
  })
}

// 书架-取消收藏
export function ebookShelfCancelCollection(params) {
  return request({
    url: '/ebookShelf/cancelCollection',
    method: 'get',
    params
  })
}

// 书架-分页查询
export function ebookShelfQuarryAll(params) {
  return request({
    url: '/ebookShelf/quarryAll',
    method: 'get',
    params
  })
}
