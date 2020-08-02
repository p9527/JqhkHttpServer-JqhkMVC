package jqhkMVC.controller;

import jqhkMVC.Request;
import jqhkMVC.RequestMapping;
import jqhkMVC.models.User;
import jqhkMVC.service.CommentService;
import jqhkMVC.service.TopicService;

import java.util.HashMap;


public class CommentRoute {
    @RequestMapping(path = "/comment/add")
    public static byte[] add(Request request) {
        HashMap<String, String> data = request.form;
        Integer topicId = Integer.valueOf(data.get("topicId"));
        String content = data.get("content");
        User u = Route.currentUser(request);
        CommentService.addBySQL(content, u.id, topicId);
        TopicService.replyCountPlusOne(topicId);
        String url = String.format("/topic?id=%s", topicId);
        return Route.redirect(url);
    }
}
