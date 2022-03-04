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

// 审核-条件查询
export function auditListQueryAll(params) {
  return request({
    url: '/auditList/queryAll',
    method: 'get',
    params
  })
}
