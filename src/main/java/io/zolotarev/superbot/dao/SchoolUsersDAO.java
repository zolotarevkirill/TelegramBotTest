package io.zolotarev.superbot.dao;
import io.zolotarev.superbot.models.SchoolUser;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolUsersDAO {

    public boolean isUserRegisterByNickName(String nickName) throws SQLException{
        String SQL = "SELECT * FROM school_users WHERE telegramID = '"+nickName+"'";
        Statement statement = null;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        if(!resultSet.next()){
            return false;
        }else{
            return true;
        }
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
