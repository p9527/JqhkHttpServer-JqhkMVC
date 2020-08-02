package jqhkMVC.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import jqhkMVC.Utils;
import jqhkMVC.models.Comment;

import java.sql.*;
import java.util.ArrayList;

// impot ../Utils


public class CommentService {

    public static Comment addBySQL(String content, Integer userId, Integer topicId) {
        Comment m = new Comment();
        m.setContent(content);
        m.setUserId(userId);
        m.setTopicId(topicId);
        Long time = System.currentTimeMillis() / 1000L;
        m.setCreatedTime(time);
        m.setUpdatedTime(time);
        m.setDeleted(false);
        Integer floor = CommentService.allBySQL(topicId).size() + 1;
        m.setFloor(floor);

        MysqlDataSource ds = Utils.getDataSource();
        String sqlInsert = "INSERT INTO Comment " +
                "(topicId, content, userId, createdTime, updatedTime, deleted, floor) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, m.getTopicId());
            statement.setString(2, m.getContent());
            statement.setInt(3, m.getUserId());
            statement.setLong(4, m.getCreatedTime());
            statement.setLong(5, m.getUpdatedTime());
            statement.setBoolean(6, m.getDeleted());
            statement.setInt(7, m.getFloor());
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

    public static Comment findByIdBySQL(Integer id) {
        Comment m = new Comment();

        MysqlDataSource ds = Utils.getDataSource();
        String sql = "select * from `Comment` where id = ?";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, id);
            System.out.println(String.format("Session.findById <%s>", statement));

            try(ResultSet rs = statement.executeQuery()) {
                rs.first();
                m = rsToComment(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return m;
    }

    public static ArrayList<Comment> allBySQL(Integer topicId) {
        ArrayList<Comment> ms = new ArrayList<>();

        MysqlDataSource ds = Utils.getDataSource();
        String sql = "select * from `Comment` where topicId = ?";

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, topicId);
            System.out.println(String.format("Session.add <%s>", statement));

            try(ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Comment m = rsToComment(rs);
                    ms.add(m);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ms;
    }

    private static Comment rsToComment(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        Integer topicId = rs.getInt("topicId");
        String content = rs.getString("content");
        Boolean deleted = rs.getBoolean("deleted");
        Long createdTime = rs.getLong("createdTime");
        Long updatedTime = rs.getLong("updatedTime");
        Integer userId = rs.getInt("userId");
        Integer floor = rs.getInt("floor");

        Comment m = new Comment();
        m.setId(id);
        m.setContent(content);
        m.setTopicId(topicId);
        m.setUpdatedTime(updatedTime);
        m.setCreatedTime(createdTime);
        m.setUserId(userId);
        m.setDeleted(deleted);
        m.setFloor(floor);

        return m;
    }

}