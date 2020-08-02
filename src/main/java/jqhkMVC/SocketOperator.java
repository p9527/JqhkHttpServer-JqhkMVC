package jqhkMVC;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketOperator {
    private static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static String socketReadAll(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        InputStreamReader reader = new InputStreamReader(input);
        int bufferSize = 1024;
        char[] data = new char[bufferSize];
        StringBuilder sb = new StringBuilder();
        while (true) {
            int size = reader.read(data, 0, data.length);
            if (size > 0) {
                sb.append(data, 0, size);
            }
            log("size and data: " + size + " || " + data.length);
            if (size < bufferSize) {
                break;
            }
        }
        return sb.toString();
    }

    public static void socketSendAll(Socket socket, byte[] r) throws IOException {
        OutputStream output = socket.getOutputStream();
        output.write(r);
    }
}
