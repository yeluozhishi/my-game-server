import request from "@/utils/request";

export function addServer(data) {
    return request({
        url: '/server/add',
        method: 'post',
        data: data
    })
}

export function updateServer(data) {
    return request({
        url: '/server/update',
        method: 'post',
        data: data
    })
}

export function getServers(zone) {
    return request({
        url: '/server/list',
        method: 'post',
        data: {
            zone: zone
        }
    })
}

export function deleteServer(sids) {
    return request({
        url: '/server/delete',
        method: 'post',
        data: {
            serverIds: sids
        }
    })
}