package io.zolotarev.superbot.dao;

import io.zolotarev.superbot.models.Polls;
import io.zolotarev.superbot.models.Question;
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

    public int checkuser(String id) throws SQLException {
        String SQL = "SELECT * FROM answer WHERE id='"+id+"'";
        Statement statement = null;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        if(!resultSet.next())
        {
           return 0;
        }
        else{
            return 1;
        }
    }


    public void saveAnswer(String id, String question, String answer) throws SQLException {
        String SQL = "INSERT into answer(id, qusetion, answer) VALUES ('"+id+"','"+question+"','"+answer+"')";
        Statement statement =  connection.createStatement();
        statement.execute(SQL);
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
