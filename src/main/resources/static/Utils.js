var log = console.log.bind(console)

var e = function (selector) {
    return document.querySelector(selector)
}

const ajax = function (method, path, data, callback) {
    var r = new XMLHttpRequest();
    r.open(method, path, true)

    r.setRequestHeader("Content-Type", "application/json")

    r.onreadystatechange = function () {
        if (r.readyState === 4) {
            callback(r.response)
        }
    }

    data = JSON.stringify(data);
    r.send(data)
}