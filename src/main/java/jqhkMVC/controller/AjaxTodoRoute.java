package jqhkMVC.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jqhkMVC.Request;
import jqhkMVC.RequestMapping;
import jqhkMVC.Utils;
import jqhkMVC.models.Todo;
import jqhkMVC.models.User;
import jqhkMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;


public class AjaxTodoRoute {
    @RequestMapping(path = "/ajax/todo/all")
    public static byte[] all(Request request) {
            HashMap<String, String> headers = new HashMap<>();
            User u = Route.currentUser(request);
            ArrayList<Todo> todoList = TodoService.findByUserId(u.id);
            headers.put("Content-Type", "application/json");
            String body = JSON.toJSONString(todoList);

            String response = Route.responseWithHeader(200, headers, body);
            return response.getBytes(StandardCharsets.UTF_8);
        }

    @RequestMapping(path = "/todo")
    public static byte[] index(Request request) {
            String body = Route.html("ajax_todo.html");
            HashMap<String, String> header = new HashMap<>();
            header.put("Content-Type", "text/html");

            String response = Route.responseWithHeader(200, header, body);
            return response.getBytes(StandardCharsets.UTF_8);
        }
    @RequestMapping(path = "/ajax/todo/add")
    public static byte[] add(Request request) {
        String jsonString = request.body;
        JSONObject jsonForm = JSONObject.parseObject(jsonString);
        String content = jsonForm.getString("content");
        Utils.log("todo add content: <%s>", content);
        HashMap<String, String> form = new HashMap<>();
        form.put("content", content);
        User u = Route.currentUser(request);
        Todo todo = TodoService.add(form, u.id);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String body = JSON.toJSONString(todo);

        String response = Route.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    @RequestMapping(path = "/ajax/todo/delete")
    public static byte[] delete(Request request) {
        String jsonString = request.body;
        JSONObject jsonForm = JSONObject.parseObject(jsonString);
        String id = jsonForm.getString("id");

        TodoService.delete(Integer.valueOf(id));

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String body = "";

        String response = Route.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    @RequestMapping(path = "/ajax/todo/complete")
    public static byte[] complete(Request request) {
        String jsonString = request.body;
        JSONObject jsonForm = JSONObject.parseObject(jsonString);
        String id = jsonForm.getString("id");

        Todo e = TodoService.complete(Integer.valueOf(id));

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String body = JSON.toJSONString(e);

        String response = Route.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    @RequestMapping(path = "/ajax/todo/edit")
    public static byte[] edit(Request request) {
        String jsonString = request.body;
        JSONObject jsonForm = JSONObject.parseObject(jsonString);
        String content = jsonForm.getString("content");
        Integer id = Integer.valueOf(jsonForm.getString("id"));
        Utils.log("todo eidt content: <%s>", content);
        TodoService.update(id, content);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String body = "";

        String response = Route.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

}
