import request from "/@/utils/request"

export function fetchList(query?: Object) {
  return request({
    url: '/carOrder/carOrder/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj?: Object) {
  return request({
    url: '/carOrder/carOrder',
    method: 'post',
    data: obj
  })
}

export function getObj(id?: string) {
  return request({
    url: '/carOrder/carOrder/' + id,
    method: 'get'
  })
}

export function delObjs(ids?: Object) {
  return request({
    url: '/carOrder/carOrder',
    method: 'delete',
    data: ids
  })
}

export function putObj(obj?: Object) {
  return request({
    url: '/carOrder/carOrder',
    method: 'put',
    data: obj
  })
}

