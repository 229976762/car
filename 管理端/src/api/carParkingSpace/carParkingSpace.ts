import request from "/@/utils/request"

export function fetchList(query?: Object) {
  return request({
    url: '/carParkingSpace/carParkingSpace/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj?: Object) {
  return request({
    url: '/carParkingSpace/carParkingSpace',
    method: 'post',
    data: obj
  })
}

export function getObj(id?: string) {
  return request({
    url: '/carParkingSpace/carParkingSpace/' + id,
    method: 'get'
  })
}

export function delObjs(ids?: Object) {
  return request({
    url: '/carParkingSpace/carParkingSpace',
    method: 'delete',
    data: ids
  })
}

export function putObj(obj?: Object) {
  return request({
    url: '/carParkingSpace/carParkingSpace',
    method: 'put',
    data: obj
  })
}

