package io.zolotarev.superbot.dao;
import io.zolotarev.superbot.models.Images;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImagesDAO {

    private List<Images> images;

    public boolean addImageID(String nickName, String imageID) throws SQLException {
        String SQL = "INSERT INTO images (telegramID, imageID) VALUES ('"+nickName+"','"+imageID+"')";
        Statement statement =  connection.createStatement();
        statement.execute(SQL);
        return true;
    }

    public List<Images> getImageList() throws SQLException {
        List<Images> listImages = new ArrayList<>();
        Statement statement =  connection.createStatement();
        String SQL = "SELECT * FROM images";
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()){
            Images images = new Images();
            images.setId(resultSet.getInt("id"));
            images.settelegramID(resultSet.getString("telegramID"));
            images.setimageID(resultSet.getString("imageID"));
            System.out.println(images.toString());
            listImages.add(images);
        }
        return listImages;
    }

    private static final String URL = "jdbc:postgresql://92.255.77.194:5432/default_db";
    //    private static final String URL = "jdbc:postgresql://localhost:5432/tg_bd_test";
    private static final String USERNAME = "gen_user";
    //    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres123";
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
