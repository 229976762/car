import request from "/@/utils/request"

export function fetchList(query?: Object) {
  return request({
    url: '/carParkingRecord/carParkingRecord/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj?: Object) {
  return request({
    url: '/carParkingRecord/carParkingRecord',
    method: 'post',
    data: obj
  })
}

export function getObj(id?: string) {
  return request({
    url: '/carParkingRecord/carParkingRecord/' + id,
    method: 'get'
  })
}

export function delObjs(ids?: Object) {
  return request({
    url: '/carParkingRecord/carParkingRecord',
    method: 'delete',
    data: ids
  })
}

export function putObj(obj?: Object) {
  return request({
    url: '/carParkingRecord/carParkingRecord',
    method: 'put',
    data: obj
  })
}

