import request from "/@/utils/request"

export function fetchList(query?: Object) {
  return request({
    url: '/pig/carParkingLot/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj?: Object) {
  return request({
    url: '/pig/carParkingLot',
    method: 'post',
    data: obj
  })
}

export function getObj(id?: string) {
  return request({
    url: '/pig/carParkingLot/' + id,
    method: 'get'
  })
}

export function delObjs(ids?: Object) {
  return request({
    url: '/pig/carParkingLot',
    method: 'delete',
    data: ids
  })
}

export function putObj(obj?: Object) {
  return request({
    url: '/pig/carParkingLot',
    method: 'put',
    data: obj
  })
}

