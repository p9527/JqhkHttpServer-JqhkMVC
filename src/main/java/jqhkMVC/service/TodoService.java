package jqhkMVC.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import jqhkMVC.Utils;
import jqhkMVC.models.ModelFactory;
import jqhkMVC.models.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

// impot ../Utils


public class TodoService {
    public static Todo add(HashMap<String, String> form, Integer userId) {
        String content = form.get("content");
        Todo m = new Todo();
        m.content = content;
        m.completed = false;
        m.createdTime = System.currentTimeMillis() / 1000L;
        m.updatedTime = System.currentTimeMillis() / 1000L;
        m.userId = userId;

        ArrayList<Todo> all = load();
        if (all.isEmpty()){
            m.id = 1;
        } else {
            Todo last = all.get(all.size() - 1);
            m.id = last.id + 1;
        }
        all.add(m);
        save(all);

        return m;
    }

    public static void save(ArrayList<Todo> list) {

        String className = Todo.class.getSimpleName();

        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.id.toString());
            lines.add(model.content);
            lines.add(model.completed.toString());
            lines.add(model.createdTime.toString());
            lines.add(model.updatedTime.toString());
            lines.add(model.userId.toString());
            return lines;
        });
    }

    public static ArrayList<Todo> load() {
        String className = Todo.class.getSimpleName();
        ArrayList<Todo> rs = ModelFactory.load(className, 6, modelData -> {
            Integer id = Integer.parseInt(modelData.get(0));
            String content = modelData.get(1);
            Boolean completed = Boolean.parseBoolean(modelData.get(2));
            Long createdTime = Long.valueOf(modelData.get(3));
            Long updatedTime = Long.valueOf(modelData.get(4));
            Integer userId = Integer.parseInt(modelData.get(5));

            Todo m = new Todo();
            m.id = id;
            m.content = content;
            m.completed = completed;
            m.createdTime = createdTime;
            m.updatedTime = updatedTime;
            m.userId = userId;

            return m;
        });
        return rs;
    }

    public static Todo complete(Integer id) {
        ArrayList<Todo> ms = load();
        Todo r = null;
        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.id.equals(id)) {
                e.completed = true;
                e.updatedTime = System.currentTimeMillis() / 1000L;
                r = e;
            }
        }
        save(ms);
        return r;
    }

    public static void delete(Integer id) {
        ArrayList<Todo> ms = load();

        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.id.equals(id)) {
                ms.remove(e);
            }
        }
        save(ms);
    }

    public static void update(Integer id, String content) {
        ArrayList<Todo> ms = load();
        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.id.equals(id)) {
                e.content = content;
                e.updatedTime = System.currentTimeMillis() / 1000L;
            }
        }
        save(ms);
    }

    public static Todo findById(Integer id) {
        ArrayList<Todo> ms = load();

        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;
    }

    public static ArrayList<Todo> findByUserId(Integer userId) {
        ArrayList<Todo> ms = load();
        ArrayList<Todo> r = new ArrayList<>();
        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.userId.equals(userId)) {
                r.add(e);
            }
        }
        return r;
    }

    public Todo addBySQL(String content) {
        Todo m = new Todo();
        m.setContent(content);

        MysqlDataSource ds = Utils.getDataSource();
        String sqlInsert = "INSERT INTO Todo (content) VALUES (?)";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, m.getContent());
            System.out.println(String.format("Session.add <%s>", statement));

            statement.executeUpdate();

            try(ResultSet rs = statement.getGeneratedKeys()) {
                rs.first();
                Integer id = rs.getInt("GENERATED_KEY");
                m.setId(id);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return m;
    }

    public Todo updateBySQL(Integer id, String content) {
        Todo m = new Todo();
        m.setId(id);
        m.setContent(content);

        MysqlDataSource ds = Utils.getDataSource();
        String sqlInsert = "UPDATE `todo` set content = (?) where id = (?)";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, m.getContent());
            statement.setInt(2, m.getId());
            System.out.println(String.format("Session.add <%s>", statement));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return m;
    }

    public Todo findByIdBySQL(Integer id) {
        Todo m = new Todo();

        MysqlDataSource ds = Utils.getDataSource();
        String sql = "select * from `todo` where id = ?";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, id);

            try(ResultSet rs = statement.executeQuery()) {
                rs.first();
                Integer i = rs.getInt("id");
                String content = rs.getString("content");

                m.setId(i);
                m.setContent(content);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return m;
    }

    public ArrayList<Todo> allBySQL() {
        ArrayList<Todo> ms = new ArrayList<>();

        MysqlDataSource ds = Utils.getDataSource();
        String sql = "select * from `todo`";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            try(ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    String content = rs.getString("content");

                    Todo m = new Todo();
                    m.setId(id);
                    m.setContent(content);;

                    ms.add(m);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ms;
    }

    public void deleteBySQL(Integer id) {

        MysqlDataSource ds = Utils.getDataSource();
        String sqlInsert = "delete from `todo` where id = (?)";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, id);
            System.out.println(String.format("Session.add <%s>", statement));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}