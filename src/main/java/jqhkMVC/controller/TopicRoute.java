package jqhkMVC.controller;

import jqhkMVC.RequestMapping;
import jqhkMVC.tools.jqhkTemplate;
import jqhkMVC.Request;
import jqhkMVC.models.Comment;
import jqhkMVC.models.Topic;
import jqhkMVC.models.User;
import jqhkMVC.service.CommentService;
import jqhkMVC.service.TopicService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;


public class TopicRoute {
    @RequestMapping(path = "/topic")
    public static byte[] index(Request request) {
        HashMap<String, String> query = request.query;
        Integer id = Integer.valueOf(query.get("id"));
        Topic t = TopicService.findByIdBySQL(id);
        HashMap<String, Object> d = new HashMap<>();
        d.put("t", t);
        ArrayList<Comment> comments = CommentService.allBySQL(id);
        d.put("comments", comments);
        String body = jqhkTemplate.render(d, "topic_index.ftl");
        HashMap<String, String> header = new HashMap<>();
        String response = Route.responseWithHeader(202, header, body);
        TopicService.viewCountPlusOne(id);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    @RequestMapping(path = "/topic/create")
    public static byte[] create(Request request) {
        String body = Route.html("topic_add.html");
        HashMap<String, String> header = new HashMap<>();
        String response = Route.responseWithHeader(202, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    @RequestMapping(path = "/topic/add")
    public static byte[] add(Request request) {
        HashMap<String, String> data = request.form;
        String title = data.get("title");
        String content = data.get("content");
        String tab = data.get("tab");
        User u = Route.currentUser(request);
        TopicService.addBySQL(title, content, u.id, tab);
        return Route.redirect("/");
    }
}
