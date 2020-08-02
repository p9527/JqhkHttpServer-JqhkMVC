package jqhkMVC.controller;

import jqhkMVC.RequestMapping;
import jqhkMVC.tools.jqhkTemplate;
import jqhkMVC.Request;
import jqhkMVC.models.Topic;
import jqhkMVC.models.User;
import jqhkMVC.service.TopicService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class PublicRoute {

    public static byte[] hello(Request request) {
        String body = "hello";
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String response = header + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    @RequestMapping(path = "/")
    public static byte[] routeIndex(Request request) {
        HashMap<String, String> data = request.query;
        String tab;
        if (data == null) {
            tab = "all";
        } else {
            tab = data.getOrDefault("tab", "all");
        }
        // Utils.log("routeIndex tab: %s", tab);
        User u = Route.currentUser(request);
        ArrayList<Topic> topicList = TopicService.allBySQL(tab);
        HashMap<String, Object> d = new HashMap<>();
        d.put("u", u);
        d.put("topics", topicList);
        String body = jqhkTemplate.render(d, "index.ftl");
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String response = header + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }
}
