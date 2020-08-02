package jqhkMVC.controller;

import jqhkMVC.Request;
import jqhkMVC.RequestMapping;

import java.nio.charset.StandardCharsets;


public class TestRoute {

    @RequestMapping(path = "/test")
    public static byte[] test(Request request) {
        String body = String.format("<html><body>test</body></html>");
        String response = "HTTP/1.1 404 NOT OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    // public static byte[] routeStatic(Request request) {
    //     String path = request.path;
    //     ArrayList<String> paths = Request.pathElement(path);
    //     String id = paths.get(1);
    //     String fileName = id;
    //     String dir = "static";
    //     String filePath = String.format("%s/%s", dir, fileName);
    //
    //     String fileType = fileName.split("\\.", 2)[1];
    //
    //     String header;
    //     if (fileType.equals("css")) {
    //         header = "HTTP/1.1 200 very OK\r\nContent-Type: text/css;\r\n\r\n";
    //     } else if (fileType.equals("js")) {
    //         header = "HTTP/1.1 200 very OK\r\nContent-Type: application/javascript;\r\n\r\n";
    //     } else {
    //         header = "HTTP/1.1 200 very OK\r\nContent-Type: image/gif;\r\n\r\n";
    //     }
    //
    //     byte[] body = new byte[1];
    //
    //     try (InputStream is = Utils.fileStream(filePath)) {
    //         body = is.readAllBytes();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     byte[] part1 = header.getBytes(StandardCharsets.UTF_8);
    //     byte[] response = new byte[part1.length + body.length];
    //     System.arraycopy(part1, 0, response, 0, part1.length);
    //     System.arraycopy(body, 0, response, part1.length, body.length);
    //     return response;
    // }

}
