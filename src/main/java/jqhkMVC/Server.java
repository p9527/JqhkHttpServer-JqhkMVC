package jqhkMVC;

import jqhkMVC.aspect.AspectAnnotationFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class jqhkServlet implements Runnable {
    Socket socket;
    String request;

    public jqhkServlet(Socket socket, String request) {
        this.socket = socket;
        this.request = request;
    }

    @Override
    public void run() {
        Utils.log("请求:\n%s", this.request);
        Request r = new Request(this.request);
        byte[] response = responseForPath(r);
        try {
            SocketOperator.socketSendAll(socket, response);
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static byte[] responseForPath(Request reqeust) {
        byte[] response = AspectAnnotationFactory.aop(reqeust);
        return response;
    }
}

public class Server {
    static ExecutorService pool = Executors.newCachedThreadPool();

    public static void run(Integer port) {
        Utils.log("服务器启动, 访问 http://localhost:%s", port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    Utils.log("client 连接成功");
                    String request = SocketOperator.socketReadAll(socket);
                    if (Request.validRequest(request)) {
                        jqhkServlet servlet = new jqhkServlet(socket, request);
                        Thread t = new Thread(servlet);
                        pool.execute(t);
                    } else {
                        byte[] response = new byte[1];
                        Utils.log("接受到了一个空请求");
                        SocketOperator.socketSendAll(socket, response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.out.println("exception: " + ex.getMessage());
        }
    }

}
