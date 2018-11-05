package com.codecool.krk.app.dao;

import com.codecool.krk.app.models.Comment;

import java.util.List;

public interface DAO {
    boolean putComment(Comment comment);
    List<Comment> getAllComments();

}
