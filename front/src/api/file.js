import request from '@/utils/request'

export function downloads(params) {
  return request({
    url: '/file/downloads',
    method: 'get',
    params
  })
}


