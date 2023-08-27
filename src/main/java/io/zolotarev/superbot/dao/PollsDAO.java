package io.zolotarev.superbot.dao;
import io.zolotarev.superbot.models.Polls;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PollsDAO {

    private List<Polls> polls;

    public PollsDAO() {

    }

    public static List<Polls> getAllPolls(int category) throws SQLException {
        List<Polls> pollsList = new ArrayList<>();
        Statement statement =  connection.createStatement();
        String SQL = "SELECT * FROM polls WHERE number = "+category;
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()){
            Polls polls = new Polls();
            polls.setId(resultSet.getInt("id"));
            polls.setTitle(resultSet.getString("title"));
            polls.setNumber(resultSet.getInt("number"));
            pollsList.add(polls);
        }

        return pollsList;
    }

    public List<Polls> getAllQuestions(int pollID) throws SQLException {
        List<Polls> question = new ArrayList<>();
        Statement statement =  connection.createStatement();
        String SQL = "SELECT * FROM polls WHERE number = "+pollID+";";
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()){
            Polls polls = new Polls();
            polls.setId(resultSet.getInt("id"));
            polls.setTitle(resultSet.getString("title"));
            question.add(polls);
        }

        return question;
    }

    public static void addPolls(int category_id, String category_title) throws SQLException{
        Statement statement =  connection.createStatement();
        String SQL = "INSERT into polls(title, number) VALUES ('"+category_title+"',"+category_id+")";
        statement.execute(SQL);
    }

    public static void deletePolls(int id) throws SQLException{
        Statement statement =  connection.createStatement();
        String SQL = "DELETE FROM polls WHERE id = "+id+";";
        statement.execute(SQL);
    }

    public void deleteQuestions() throws SQLException{
        Statement statement =  connection.createStatement();
        String SQL = "DELETE FROM answer;";

        System.out.println("DELETE");
        System.out.println(statement.execute(SQL));
    }

    public int checkuser(String id, int pollID) throws SQLException {
        String SQL = "SELECT * FROM answer WHERE id='"+id+"' AND pollnumber = '"+pollID+"'";
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


    public void saveAnswer(String id, String question, String answer, int pollNumber) throws SQLException {
        String SQL = "INSERT into answer(id, qusetion, answer, pollNumber) VALUES ('"+id+"','"+question+"','"+answer+"','"+pollNumber+"')";
        System.out.println(SQL);
        Statement statement =  connection.createStatement();
        statement.execute(SQL);
    }



    private static final String URL = "jdbc:postgresql://92.255.77.194:5432/default_db";
    //    private static final String URL = "jdbc:postgresql://localhost:5432/tg_bd_test";
    private static final String USERNAME = "gen_user";
    //    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres123";

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
