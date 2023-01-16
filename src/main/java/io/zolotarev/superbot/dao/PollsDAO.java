package io.zolotarev.superbot.dao;

import io.zolotarev.superbot.models.Polls;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PollsDAO {

    private List<Polls> polls;

    public List<Polls> getAllQuestions() throws SQLException {
        List<Polls> question = new ArrayList<>();
        Statement statement =  connection.createStatement();
        String SQL = "SELECT * FROM polls;";
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()){
            Polls polls = new Polls();
            polls.setId(resultSet.getInt("id"));
            polls.setTitle(resultSet.getString("title"));
            question.add(polls);
        }

        return question;
    }

    private static final String URL = "jdbc:postgresql://localhost:5432/tg_bd_test";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123";

    private static Connection connection;

    static{
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
