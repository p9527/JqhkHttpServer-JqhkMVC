package jqhkMVC;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Utils {
    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static void ensure(boolean condition, String message) {
        if (!condition) {
            Utils.log("%s ensure error", message);
        } else {
            Utils.log("ensure success");
        }
    }

    public static void save(String path, String data) {
        try (FileOutputStream out = new FileOutputStream(path)) {
            out.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            String s = String.format("Save file <%s>, error: <%s>", path, e);
            throw new RuntimeException(s);
        }
    }

    public static String load(String path) {
        try (FileInputStream is = new FileInputStream(path)) {
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return content;
        } catch (IOException e) {
            String s = String.format("load file <%s>, error: <%s>", path, e);
            throw new RuntimeException(s);
        }
    }

    public static InputStream fileStream(String path) throws FileNotFoundException {
        String resource = String.format("%s.class", Utils.class.getSimpleName());
        Utils.log("resource %s", resource);
        Utils.log("resource path %s", Utils.class.getResource(""));
        var res = Utils.class.getResource(resource);
        if (res != null && res.toString().startsWith("jar:")) {
            path = String.format("/%s", path);
            InputStream is = Utils.class.getResourceAsStream(path);
            if (is == null) {
                throw new FileNotFoundException(String.format("在 jar 里面找不到 %s", path));
            } else {
                return is;
            }
        } else {
            path = String.format("build/resources/main/%s", path);
            return new FileInputStream(path);
        }
    }

    public static MysqlDataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("zkuvzkqi");
        dataSource.setServerName("127.0.0.1");
        dataSource.setDatabaseName("cnode?characterEncoding=UTF-8");
        return dataSource;
    }

    public static void main(String[] args) {
    }
}
