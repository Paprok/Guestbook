package com.codecool.krk.app.dao;

import com.codecool.krk.app.models.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOSQL implements DAO {
    private Connection connection;

    public DAOSQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean putComment(Comment comment) {
        String sql = "INSERT INTO comments (nick, comment) VALUES (?, ?)";
        boolean isDone = false;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, comment.getNick());
            ps.setString(2, comment.getText());
            isDone = ps.execute();
        } catch (SQLException e) {
            System.out.println("Couldn't post your comment");
        }
        return isDone;
    }

    @Override
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            comments = getCommentsFromResults(resultSet);

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database");
        }

        return comments;
    }

    private List<Comment> getCommentsFromResults(ResultSet results) throws SQLException{
        List<Comment> comments = new ArrayList<>();
        while(results.next()){
            Comment comment = new Comment();
            comment.setNick(results.getString("nick"));
            comment.setText(results.getString("comment"));
            comments.add(comment);
        }
        return comments;
    }
}
