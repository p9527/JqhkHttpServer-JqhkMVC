var loadTodos = function () {
    var path = '/ajax/todo/all'
    var method = "GET"
    var data = ""

    ajax(method, path, data, function (response) {
        log("loadTodos reoponse: ", response)
        var todoList = JSON.parse(response)
        log("todoList: ", todoList)
        for (let i = 0; i < todoList.length; i++) {
            log("insert todo")
            var todo = todoList[i]
            log("todo: ", todo)
            var todoCell = todoTemplate(todo)
            insertTodo(todoCell)
        }
    })
}

var _main = function () {
    loadTodos()
}

_main()