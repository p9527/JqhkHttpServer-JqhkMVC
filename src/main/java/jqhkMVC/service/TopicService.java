package jqhkMVC.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import jqhkMVC.Utils;
import jqhkMVC.models.Topic;

import java.sql.*;
import java.util.ArrayList;

// impot ../Utils


public class TopicService {

    public static Topic addBySQL(String title, String content, Integer userId, String tab) {
        Topic m = new Topic();
        m.setContent(content);
        m.setTitle(title);
        m.setUserId(userId);
        Long time = System.currentTimeMillis() / 1000L;
        m.setCreatedTime(time);
        m.setUpdatedTime(time);
        m.setDeleted(false);
        m.setViewCount(0);
        m.setReplyCount(0);
        m.setTab(tab);

        MysqlDataSource ds = Utils.getDataSource();
        String sqlInsert = "INSERT INTO Topic " +
                "(title, content, userId, createdTime, updatedTime, deleted, viewCount, replyCount, tab) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, m.getTitle());
            statement.setString(2, m.getContent());
            statement.setInt(3, m.getUserId());
            statement.setLong(4, m.getCreatedTime());
            statement.setLong(5, m.getUpdatedTime());
            statement.setBoolean(6, m.getDeleted());
            statement.setInt(7, m.getViewCount());
            statement.setInt(8, m.getReplyCount());
            statement.setString(9, m.getTab());

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

    public static Topic updateBySQL(Integer id, String content) {
        Topic m = new Topic();
        m.setId(id);
        m.setContent(content);
        Long time = System.currentTimeMillis() / 1000L;
        m.setUpdatedTime(time);

        MysqlDataSource ds = Utils.getDataSource();
        String sqlInsert = "UPDATE `Topic` set content = (?), updatedTime = (?) where id = (?)";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, m.getContent());
            statement.setLong(2, m.getUpdatedTime());
            statement.setInt(3, m.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return m;
    }

    public static Topic viewCountPlusOne(Integer id) {
        Topic m = new Topic();
        m.setId(id);
        Topic t = findByIdBySQL(id);
        Integer viewCount = t.getViewCount() + 1;
        m.setViewCount(viewCount);

        MysqlDataSource ds = Utils.getDataSource();
        String sqlInsert = "UPDATE `Topic` set viewCount = (?) where id = (?)";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, m.getViewCount());
            statement.setInt(2, m.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return m;
    }

    public static Topic replyCountPlusOne(Integer id) {
        Topic m = new Topic();
        m.setId(id);
        Topic t = findByIdBySQL(id);
        Integer Count = t.getReplyCount() + 1;
        m.setReplyCount(Count);

        MysqlDataSource ds = Utils.getDataSource();
        String sqlInsert = "UPDATE `Topic` set replyCount = (?) where id = (?)";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, m.getReplyCount());
            statement.setInt(2, m.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return m;
    }

    public static Topic findByIdBySQL(Integer id) {
        Topic m = new Topic();

        MysqlDataSource ds = Utils.getDataSource();
        String sql = "select * from `Topic` where id = ?";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, id);

            try(ResultSet rs = statement.executeQuery()) {
                rs.first();
                m = rsToTopic(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return m;
    }

    public static ArrayList<Topic> allBySQL(String tab) {
        ArrayList<Topic> ms = new ArrayList<>();

        MysqlDataSource ds = Utils.getDataSource();
        String sql = "";
        if (tab.equals("all")) {
            sql = "select * from `Topic`";
        } else {
            sql = "select * from `Topic` where tab = (?)";
        }

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            if (!tab.equals("all")) {
                statement.setString(1, tab);
            }

            try(ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Topic m = rsToTopic(rs);
                    ms.add(m);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ms;
    }

    private static Topic rsToTopic(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String title = rs.getString("title");
        String content = rs.getString("content");
        Boolean deleted = rs.getBoolean("deleted");
        Long createdTime = rs.getLong("createdTime");
        Long updatedTime = rs.getLong("updatedTime");
        Integer userId = rs.getInt("userId");
        Integer viewCount = rs.getInt("viewCount");
        Integer replyCount = rs.getInt("replyCount");
        String tab = rs.getString("tab");

        Topic m = new Topic();
        m.setId(id);
        m.setContent(content);
        m.setTitle(title);
        m.setUpdatedTime(updatedTime);
        m.setCreatedTime(createdTime);
        m.setUserId(userId);
        m.setDeleted(deleted);
        m.setReplyCount(replyCount);
        m.setViewCount(viewCount);
        m.setTab(tab);

        return m;
    }

}