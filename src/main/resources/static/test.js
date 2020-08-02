var todoTemplate = function (todo) {
    // log("todoTemplate todo", todo)
    // var completed = ""
    // if (todo.completed == true) {
    //     completed = "todo-completed"
    // }
    // log("todoTemplate completed", completed)
    var t = `
        <div class="todo">
            <li class="list-group-item">
                <span class="complete glyphicon glyphicon-unchecked" aria-hidden="true"></span>
                <span class="content">吃饭</span>
                <span class="delete glyphicon glyphicon-remove pull-right" aria-hidden="true"></span>
            </li>
        </div>
    `
    return t
}

var insertTodo = function (todoCell) {
    var form = document.querySelector('#id-todo-list')
    form.insertAdjacentHTML('beforeEnd', todoCell)
}

var bindButtonClick = function () {
    var b = e("#id-button-add")
    log("id-button-add", b)
    b.addEventListener("click", function () {
        log("button click")
        var input = e("#id-input-todo")
        var todo = input.value
        var todoCell = todoTemplate(todo)
        let method = "POST"
        let data = {
            content: todo
        }
        let path = "/ajax/todo/add"
        // ajax(method, path, data, function (response) {
        //     var todo = JSON.parse(response)
        //     var todoCell = todoTemplate(todo)
        //     insertTodo(todoCell)
        // })
        var todoCell = todoTemplate()
        insertTodo(todoCell)
    })
}

var bindTodoList = function () {
    var todoList = e('#id-todo-list')
    todoList.addEventListener('click', function (event) {
        var target = event.target
        log('target: ', target)
        var classList = target.classList
        if (classList.contains("complete")) {
            log("完成")
            completeTodo(target)
        } else if (classList.contains("delete")) {
            log("删除")
            deleteTodo(target)
        }
    })
}

var deleteTodo = function (target) {
    log('delete target', target)
    var parent = target.closest('.todo')
    log("delete todo parent", parent)
    // var todoId = parent.dataset.id
    // log("todoId", todoId)
    // var path = '/ajax/todo/delete'
    // var method = "POST"
    // var data = {
    //     id: todoId,
    // }

    // ajax(method, path, data, function (response) {
    //     parent.remove()
    // })
    parent.remove()
}

var completeTodo = function (target) {
    log('delete target', target)
    var parent = target.closest('.todo')
    // log('complete parent', parent)
    // var todoId = parent.dataset.id
    // log("todoId", todoId)
    // var path = '/ajax/todo/complete'
    // var method = "POST"
    // var data = {
    //     id: todoId,
    // }

    // ajax(method, path, data, function (response) {
    //     var todo = JSON.parse(response)
    //     var a = parent.firstElementChild
    //     var updatedTime = parent.querySelector(".updatedTime")
    //     updatedTime.innerText = `更新时间: ${todo.updatedTime}`
    //     log("complete a", a)
    //     a.classList.add("todo-completed")
    // })
    var content = parent.querySelector('.content')
    content.classList.add('todo-completed')
}

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
    bindButtonClick()
    bindTodoList()
    // loadTodos()
}

_main()