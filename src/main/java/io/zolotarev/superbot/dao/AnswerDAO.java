package io.zolotarev.superbot.dao;

import io.zolotarev.superbot.models.Answer;
import io.zolotarev.superbot.models.Polls;
import io.zolotarev.superbot.models.Question;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerDAO {

    private List<Answer> answers;

    public List<Answer> getAllAnswer() throws SQLException {
        List<Answer> answers = new ArrayList<>();
        Statement statement =  connection.createStatement();
        String SQL = "SELECT * from answer;";
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()){
            Answer answer = new Answer();
            answer.setId(resultSet.getInt("id"));
            answer.setQuestion(resultSet.getString("question"));
            answer.setAnswer(resultSet.getString("answer"));
            answers.add(answer);
        }
        return answers;
    }

        private static final String URL = "jdbc:postgresql://92.255.77.194:5432/default_db";
//    private static final String URL = "jdbc:postgresql://localhost:5432/tg_bd_test";
        private static final String USERNAME = "gen_user";
//    private static final String USERNAME = "postgres";
        private static final String PASSWORD = "postgres";
//    private static final String PASSWORD = "123";

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
