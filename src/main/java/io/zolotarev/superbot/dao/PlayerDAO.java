package io.zolotarev.superbot.dao;
import io.zolotarev.superbot.models.Players;
import lombok.val;

import org.springframework.stereotype.Component;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*** QR */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Component
public class PlayerDAO {

    private List<Players> players;

    public static void createPlayer(String name) throws SQLException, WriterException, IOException {
        // String PATH_IMG = "/Users/kzolotarev/Documents/JavaSpring/superbot/src/main/resources/static";
        // String PATH_IMG = "/Users/kzolotarev/Documents/JavaSpring/superbot/resources/static/images/qr/qr384547.png ""

        // String PATH_IMG = "/Users/kzolotarev/Documents/JavaSpring/superbot/src/main/resources/static";

        //Users/kzolotarev/Documents/JavaSpring/superbot/resources/static/images/qr/qr758086.png/
     
        //final Path PATH_IMG = Paths.get("/resources/static");
        final Path PATH_IMG = Paths.get("/root");

        int code_1 = new Random().nextInt(900000);
        int code_2 = new Random().nextInt(900000);
        int code_3 = new Random().nextInt(900000);
        int code_4 = new Random().nextInt(900000);
        int code_5 = new Random().nextInt(900000);
        int code_6 = new Random().nextInt(900000);

        String img1 = "/images/qr/qr"+code_1+".png";
        String path_img1= PATH_IMG+img1;
        String url1 = "http://localhost/play/open?n=1&id=";


        String img2 = "/images/qr/qr"+code_2+".png";
        String path_img2 = PATH_IMG+img2;
        String url2 = "http://localhost/play/open?n=2&id=";
     

        String img3 = "/images/qr/qr"+code_3+".png";
        String path_img3 = PATH_IMG+img3;
        String url3 = "http://localhost/play/open?n=3&id=";
     

        String img4 = "/images/qr/qr"+code_4+".png";
        String path_img4 = PATH_IMG+img4;
        String url4 = "http://localhost/play/open?n=4&id=";
        

        String img5 = "/images/qr/qr"+code_5+".png";
        String path_img5 = PATH_IMG+img5;
        String url5 = "http://localhost/play/open?n=5&id=";
      

        String img6 = "/images/qr/qr"+code_6+".png";
        String path_img6 = PATH_IMG+img6;
        String url6 = "http://localhost/play/open?n=6&id=";
        


        String SQL = "INSERT INTO players (name, code1, code2, code3, code4,code5,code6, img1, img2, img3, img4, img5, img6) VALUES ('"+name+"', '"+code_1+"', '"+code_2+"', '"+code_3+"', '"+code_4+"', '"+code_5+"', '"+code_6+"', '"+img1+"', '"+img2+"', '"+img3+"', '"+img4+"', '"+img5+"', '"+img6+"') RETURNING id";
        Statement statement =  connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        
        if(resultSet.next()){
            url1 = url1+resultSet.getInt("id");
            url2 = url2+resultSet.getInt("id");
            url3 = url3+resultSet.getInt("id");
            url4 = url4+resultSet.getInt("id");
            url5 = url5+resultSet.getInt("id");
            url6 = url6+resultSet.getInt("id");
            
        }

        generateQRCodeImage(url1, 250, 250,path_img1);
        generateQRCodeImage(url2, 250, 250,path_img2);
        generateQRCodeImage(url3, 250, 250,path_img3);
        generateQRCodeImage(url4, 250, 250,path_img4);
        generateQRCodeImage(url5, 250, 250,path_img5);
        generateQRCodeImage(url6, 250, 250,path_img6);
    }


    public static void deletePlayer(String id) throws SQLException {
        String SQL = "DELETE FROM players WHERE id = "+id;
        Statement statement =  connection.createStatement();
        statement.execute(SQL);
    }

    /**
     * @param n
     * @param code
     * @param id
     * @return
     * @throws SQLException
     */
    public static  boolean checkCode(String n, String code, String id) throws SQLException {
        String SQL = "";
        String SQL_UPDATE = "";
        
        switch (n){
            case "1":
            SQL = "SELECT * FROM players WHERE id='"+id+"' AND code1 ='"+code+"'";
            SQL_UPDATE = "UPDATE players SET code1 = 'NULL' WHERE id = '"+id+"'";
            break;
            case "2":
            SQL = "SELECT * FROM players WHERE id='"+id+"' AND code2 ='"+code+"'";
            SQL_UPDATE = "UPDATE players SET code2 = 'NULL' WHERE id = '"+id+"'";
            break;
            case "3":
            SQL = "SELECT * FROM players WHERE id='"+id+"' AND code3 ='"+code+"'";
            SQL_UPDATE = "UPDATE players SET code3 = 'NULL' WHERE id = '"+id+"'";
            break;
            case "4":
            SQL = "SELECT * FROM players WHERE id='"+id+"' AND code4 ='"+code+"'";
            SQL_UPDATE = "UPDATE players SET code4 = 'NULL' WHERE id = '"+id+"'";
            break;
            case "5":
            SQL = "SELECT * FROM players WHERE id='"+id+"' AND code5 ='"+code+"'";
            SQL_UPDATE = "UPDATE players SET code5 = 'NULL' WHERE id = '"+id+"'";
            break;
            case "6":
            SQL = "SELECT * FROM players WHERE id='"+id+"' AND code6 ='"+code+"'";
            SQL_UPDATE = "UPDATE players SET code6 = 'NULL' WHERE id = '"+id+"'";
            break;
        }
        Statement statement =  connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);

        if(resultSet.next()){
            System.out.println(" SQL_UPDATE SQL_UPDATE SQL_UPDATE");
            updateCodeUser(SQL_UPDATE);
            return true;
        } else{
            System.out.println("NOT UPDATE");
            return false;
        }
       
    }


    public static void updateCodeUser(String SQL) throws SQLException{
        Statement statement =  connection.createStatement();
        statement.execute(SQL);
    }



    /**
     * @return
     * @throws SQLException
     */
    public  List<Players> getPlayers() throws SQLException {
        List<Players> playersList = new ArrayList<>();
        Statement statement =  connection.createStatement();
        String SQL = "SELECT * FROM players LEFT JOIN chests ON players.id = chests.user_id";
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()){
                Players players = new Players();
                players.setId(resultSet.getInt("id"));
                players.setUserID(resultSet.getInt("user_id"));
                players.setName(resultSet.getString("name"));
                players.setCode1(resultSet.getString("code1"));
                players.setCode2(resultSet.getString("code2"));
                players.setCode3(resultSet.getString("code3"));
                players.setCode4(resultSet.getString("code4"));
                players.setCode5(resultSet.getString("code5"));
                players.setCode6(resultSet.getString("code6"));
                players.setImg1(resultSet.getString("img1"));
                players.setImg2(resultSet.getString("img2"));
                players.setImg3(resultSet.getString("img3"));
                players.setImg4(resultSet.getString("img4"));
                players.setImg5(resultSet.getString("img5"));
                players.setImg6(resultSet.getString("img6"));
                playersList.add(players);   
                
                
        }

        return playersList;
    }

    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            
            File file = new File(filePath);
            String pathFile = System.getProperty("user.dir")+file.getPath();          
            String newPathFile = pathFile.replaceAll("superbot", "superbot/src/main");
	        Path path = FileSystems.getDefault().getPath(newPathFile);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);


            System.out.println(pathFile);
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