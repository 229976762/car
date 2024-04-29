import request from "/@/utils/request"

export function fetchList(query?: Object) {
  return request({
    url: '/carFeeRule/carFeeRule/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj?: Object) {
  return request({
    url: '/carFeeRule/carFeeRule',
    method: 'post',
    data: obj
  })
}

export function getObj(id?: string) {
  return request({
    url: '/carFeeRule/carFeeRule/' + id,
    method: 'get'
  })
}

export function delObjs(ids?: Object) {
  return request({
    url: '/carFeeRule/carFeeRule',
    method: 'delete',
    data: ids
  })
}

export function putObj(obj?: Object) {
  return request({
    url: '/carFeeRule/carFeeRule',
    method: 'put',
    data: obj
  })
}

