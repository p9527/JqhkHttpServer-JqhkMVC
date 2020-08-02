package jqhkMVC.controller;

import jqhkMVC.RequestMapping;
import jqhkMVC.tools.jqhkTemplate;
import jqhkMVC.Request;
import jqhkMVC.Utils;
import jqhkMVC.models.User;
import jqhkMVC.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class UserRoute {
    @RequestMapping(path = "/login")
    public static byte[] login(Request request) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");
        HashMap<String, String> data = null;
        if (request.method.equals("POST")) {
            data = request.form;
        }

        String loginResult = "";
        // Utils.log("login before data");
        if (data != null) {
            Utils.log("login data not null");
            String username = data.get("username");
            String password = data.get("password");
            if (UserService.validLogin(username, password)) {
                User user = UserService.findByUsername(username);
                String session = UserService.userJwt(user);
                Utils.log("login session set %s", session);
                // SessionService.add(sessionId, user.id);
                header.put("Set-Cookie", String.format("session=%s", session));
                loginResult = "登录成功";
            } else {
                loginResult = "登录失败";
            }
        }

        HashMap<String, Object> d = new HashMap<>();
        d.put("loginResult", loginResult);
        String body = jqhkTemplate.render(d, "login.ftl");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    @RequestMapping(path = "/register")
    public static byte[] register(Request request) {
        HashMap<String, String> data = null;
        if (request.method.equals("POST")) {
            data = request.form;
        }
        Utils.log("register data: %s", data);
        String registerResult = "";
        if (data != null) {
            UserService.add(data);
            registerResult = "注册成功";
        }

        HashMap<String, Object> d = new HashMap<>();
        d.put("registerResult", registerResult);
        String body = jqhkTemplate.render(d, "register.ftl");

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

}
