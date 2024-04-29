import request from "/@/utils/request"

export function fetchList(query?: Object) {
  return request({
    url: '/carAnnouncement/carAnnouncement/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj?: Object) {
  return request({
    url: '/carAnnouncement/carAnnouncement',
    method: 'post',
    data: obj
  })
}

export function getObj(id?: string) {
  return request({
    url: '/carAnnouncement/carAnnouncement/' + id,
    method: 'get'
  })
}

export function delObjs(ids?: Object) {
  return request({
    url: '/carAnnouncement/carAnnouncement',
    method: 'delete',
    data: ids
  })
}

export function putObj(obj?: Object) {
  return request({
    url: '/carAnnouncement/carAnnouncement',
    method: 'put',
    data: obj
  })
}

