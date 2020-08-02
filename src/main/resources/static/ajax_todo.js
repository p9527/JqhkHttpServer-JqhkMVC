var todoTemplate = function (todo) {
    log("todoTemplate todo", todo)
    var completed = ""
    if (todo.completed == true) {
        completed = "todo-completed"
    }
    log("todoTemplate completed", completed)
    var t = `
        <div class="todo" data-id="${todo.id}">
            <li class="list-group-item">
                <span class="complete glyphicon glyphicon-unchecked" aria-hidden="true"></span>
                <span class="content ${completed}">${todo.content}</span>
                <span class="delete glyphicon glyphicon-remove pull-right" aria-hidden="true"></span>
            </li>
        </div>
    `
    return t
}

var todoEditTemplate = function (content) {
    var input = `
        <div class="todo-edit">
            <form class="form-inline">
                <input class="edit-input" class="form-control" value="${content}">
                <span class="edit-button glyphicon glyphicon-ok" aria-hidden="true"></span>
            </form>
        </div>
    `
    return input
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
        ajax(method, path, data, function (response) {
            var todo = JSON.parse(response)
            var todoCell = todoTemplate(todo)
            insertTodo(todoCell)
        })
    })
}

var bindTodoList = function () {
    var todoList = e('#id-todo-list')
    todoList.addEventListener('click', function (event) {
        var target = event.target
        // log('target: ', target)
        var classList = target.classList
        if (classList.contains("complete")) {
            log("完成")
            completeTodo(target)
        } else if (classList.contains("delete")) {
            log("删除")
            deleteTodo(target)
        } else if (classList.contains("content")) {
            log("修改content")
            editTodo(target)
        }
    })
}

var editTodo = function (target) {
    var parent = target.closest('.todo')
    var complete = parent.querySelector(".complete")
    var content = target.innerText
    var completed = target.classList.contains("todo-completed")

    target.remove()

    var input = todoEditTemplate(content)
    // log("editTodo input", input)
    complete.insertAdjacentHTML('afterend', input)

    var editButton = parent.querySelector(".edit-button")
    var editInput = parent.querySelector(".edit-input")
    var todoEdit = parent.querySelector(".todo-edit")
    editButton.addEventListener('click', function () {
        var content = editInput.value
        var id = parent.dataset.id
        todoEdit.remove()
        updateTodo(complete, id, content, completed)
    })
}

var updateTodo = function (complete, id, content, completed) {
    var completedClass = ""
    if (completed == true) {
        completedClass = "todo-completed"
    }
    var contentSpan = `
        <span class="content ${completedClass}">${content}</span>
    `
    var path = '/ajax/todo/edit'
    var method = "POST"
    var data = {
        id: id,
        content: content,
    }
    ajax(method, path, data, function (response) {
        complete.insertAdjacentHTML('afterend', contentSpan)
    })
}

var deleteTodo = function (target) {
    log('delete target', target)
    var parent = target.closest('.todo')
    log("delete todo parent", parent)
    var todoId = parent.dataset.id
    log("todoId", todoId)
    var path = '/ajax/todo/delete'
    var method = "POST"
    var data = {
        id: todoId,
    }

    ajax(method, path, data, function (response) {
        parent.remove()
    })
}

var completeTodo = function (target) {
    log('delete target', target)
    var parent = target.closest('.todo')
    log('complete parent', parent)
    var todoId = parent.dataset.id
    log("todoId", todoId)
    var path = '/ajax/todo/complete'
    var method = "POST"
    var data = {
        id: todoId,
    }

    ajax(method, path, data, function (response) {
        // var todo = JSON.parse(response)
        var content = parent.querySelector('.content')
        content.classList.add('todo-completed')
    })
}

var loadTodos = function () {
    var path = '/ajax/todo/all'
    var method = "GET"
    var data = ""

    ajax(method, path, data, function (response) {
        log("loadTodos reoponse: ", response)
        var todoList = JSON.parse(response)
        // log("todoList: ", todoList)
        for (let i = 0; i < todoList.length; i++) {
            // log("insert todo")
            var todo = todoList[i]
            // log("todo: ", todo)
            var todoCell = todoTemplate(todo)
            insertTodo(todoCell)
        }

    })
}

var _main = function () {
    bindButtonClick()
    bindTodoList()
    loadTodos()
}

_main()