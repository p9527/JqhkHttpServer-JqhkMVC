package jqhkMVC.controller;

import jqhkMVC.Request;
import jqhkMVC.RequestMapping;
import jqhkMVC.Utils;
import jqhkMVC.models.User;
import jqhkMVC.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Route {

    public static String responseWithHeader(int code, HashMap<String, String> headerMap, String body) {
        String header = String.format("HTTP/1.1 %s\r\n", code);

        for (String key: headerMap.keySet()) {
            String value = headerMap.get(key);
            String item = String.format("%s: %s \r\n", key, value);
            header = header + item;
        }
        String response =  String.format("%s\r\n%s", header, body);
        return response;
    }

    public static String html(String filename) {
        String dir = "templates";
        String path = dir + "/" + filename;
        Utils.log("html path: %s", path);
        byte[] body = new byte[1];
        // 读取文件
        try (InputStream is = Utils.fileStream(path)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String r = new String(body);
        return r;

    }

    public static byte[] redirect(String url) {
        String body = "";
        HashMap<String, String> header = new HashMap<>();
        header.put("Location", url);
        String response = responseWithHeader(302, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static User currentUser(Request request) {
        // Utils.log("currentUser cokkies %s", request.cookies);
         if (request.cookies.containsKey("session")) {
             String session = request.cookies.get("session");
             // Utils.log("jwt session %s", session);
             if (session == null) {
                 return UserService.guest();
             } else {
                 return UserService.validJwt(session);
             }
         } else {
             return UserService.guest();
         }
    }

    @RequestMapping(path = "/static")
    public static byte[] routeStatic(Request request) {
        String fileName = request.query.get("file").strip();
        String dir = "static";
        String path = String.format("%s/%s", dir, fileName);

        String fileType = fileName.split("\\.", 2)[1];

        String header;
        if (fileType.equals("css")) {
            header = "HTTP/1.1 200 very OK\r\nContent-Type: text/css;\r\n\r\n";
        } else if (fileType.equals("js")) {
            header = "HTTP/1.1 200 very OK\r\nContent-Type: application/javascript;\r\n\r\n";
        } else {
            header = "HTTP/1.1 200 very OK\r\nContent-Type: image/gif;\r\n\r\n";
        }

        byte[] body = new byte[1];
        try (InputStream is = Utils.fileStream(path)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] part1 = header.getBytes(StandardCharsets.UTF_8);
        byte[] response = new byte[part1.length + body.length];
        System.arraycopy(part1, 0, response, 0, part1.length);
        System.arraycopy(body, 0, response, part1.length, body.length);

        // ByteArrayOutputStream response2 = new ByteArrayOutputStream();
        // response2.write(header.getBytes());
        // response2.write(body);
        return response;
    }

    public static byte[] route404(Request request) {
        String body = "<html><body><h1>404</h1><br><img src='/static?file=doge2.gif'></body></html>";
        String response = "HTTP/1.1 404 NOT OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }
}
