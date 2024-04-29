import request from "/@/utils/request"

export function fetchList(query?: Object) {
  return request({
    url: '/parkingLot/ParkingLot/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj?: Object) {
  return request({
    url: '/parkingLot/ParkingLot',
    method: 'post',
    data: obj
  })
}

export function getObj(id?: string) {
  return request({
    url: '/parkingLot/ParkingLot/' + id,
    method: 'get'
  })
}

export function delObjs(ids?: Object) {
  return request({
    url: '/parkingLot/ParkingLot',
    method: 'delete',
    data: ids
  })
}

export function putObj(obj?: Object) {
  return request({
    url: '/parkingLot/ParkingLot',
    method: 'put',
    data: obj
  })
}

