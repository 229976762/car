import request from "/@/utils/request"

export function pageList(params?: Object) {
  return request({
    url: '/carParkingLot/carParkingLot/page',
    method: 'get',
    params
  })
}

export function addObj(obj?: Object) {
  return request({
    url: '/carParkingLot/carParkingLot',
    method: 'post',
    data: obj
  })
}

export function getObj(id?: string) {
  return request({
    url: '/carParkingLot/carParkingLot/' + id,
    method: 'get'
  })
}

export function delObjs(ids?: Object) {
  return request({
    url: '/carParkingLot/carParkingLot',
    method: 'delete',
    data: ids
  })
}

export function putObj(obj?: Object) {
  return request({
    url: '/carParkingLot/carParkingLot',
    method: 'put',
    data: obj
  })
}


