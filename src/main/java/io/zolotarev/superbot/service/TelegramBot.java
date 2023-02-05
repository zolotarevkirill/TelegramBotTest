package io.zolotarev.superbot.service;
import io.zolotarev.superbot.handler.Handler;
import netscape.javascript.JSObject;
import org.springframework.stereotype.Component;
import io.zolotarev.superbot.config.BotConfig;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import net.minidev.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import java.util.Random;
import java.util.Comparator;

import static org.apache.commons.io.FileUtils.getFile;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    String[] arrayUsersPolls = new String[10];
    HashMap<String, Handler> userLinkObject = new HashMap<>();
    boolean f_is;
    Handler handler;

    String yaDiscURL = "https://cloud-api.yandex.net/v1/disk/";


    public TelegramBot(BotConfig config){
        this.config = config;
    }

    @Override
    public String getBotUsername(){
        return config.getBotName();
    }

    @Override
    public String getBotToken(){
        System.out.println("config.getToken()");
        System.out.println(config.getToken());
        return config.getToken();
    }


    public void getRandomString() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        System.out.println(generatedString);
    }

    @Override
    public void onUpdateReceived(Update update){
        if (update.getMessage().hasPhoto()){
            long chatId = update.getMessage().getChatId();
            try {
                sendMessage(chatId, "Это файл!");

                //Работа с фото из бота
                sendMessage(chatId, "Начинаю загрузку...!");
                List<PhotoSize> photos = update.getMessage().getPhoto();
                PhotoSize photo = photos.get(0);
                String fileId = photo.getFileId();
                String fileNamwe = "MyFirstPhoto";
                String botToken = getBotToken();

                URL url = new URL("https://api.telegram.org/bot"+botToken+"/getFile?file_id="+fileId);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String getFileResponse = br.readLine();

//                https://yandex.ru/video/preview/2052560887994171178
//                https://yandex.ru/video/preview/3339900611007758351




                System.out.println("-----");
                System.out.println(photos.get(0));
                System.out.println(photos.size());
                System.out.println(photo.getFilePath());
                System.out.println("https://api.telegram.org/bot"+botToken+"/getFile?file_id="+fileId);
                System.out.println("-----");


                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://cloud-api.yandex.net/v1/disk/resources?path=%2FTEST"))
                        .header("Authorization", "OAuth y0_AgAAAAAHlGMCAAkY2gAAAADbeyOREl5n_eU3T3ykly0Beo-uxEpWPlk")
                        .build();
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                System.out.println("Status");
                System.out.println(response);

                sendMessage(chatId, "Загрузил!");


            } catch (TelegramApiException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if(update.hasMessage() && update.getMessage().hasText())
        {
           String messageText = update.getMessage().getText();
           long chatId = update.getMessage().getChatId();
           String lastName =  update.getMessage().getChat().getLastName();
           String firstName = update.getMessage().getChat().getFirstName();
           String userName =  update.getMessage().getChat().getUserName();
           String userTag = userName+"_"+firstName+"_"+lastName;
           handler = getHandlerUser(userTag);

           if(handler.getState() ==  1)
           {
               System.out.println("Start save answer");
               handler.setUserId(userTag);
               try {
                   handler.saveAnswer(messageText);
               } catch (SQLException e) {
                   throw new RuntimeException(e);
               }
           }

            if(handler.getState() == 1 && !Objects.equals(messageText, "/stop")){
               try {
                   sendMessage(chatId, handler.nextQuestion());
               } catch (TelegramApiException e) {
                   throw new RuntimeException(e);
               } catch (SQLException e) {
                   throw new RuntimeException(e);
               }
            }else{
               switch (messageText){
                   case "/start":
                       try {
                           startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                       } catch (TelegramApiException e) {
                           throw new RuntimeException(e);
                       }
                       break;
                   case "Начать опрос":
                       try {
                           handler = getHandlerUser(userTag);
                           int stateUser = handler.checkStateUser(userTag);
                           String message = "Опрос уже пройден";

                           if(stateUser !=  1){
                              message = handler.pollsInit();
                           }

                           sendMessage(chatId, message);
                       } catch (TelegramApiException e) {
                           throw new RuntimeException(e);
                       } catch (SQLException e) {
                           throw new RuntimeException(e);
                       }
                       break;
                   case "/stop":
                       try {
                           handler.setState(0);
                           sendMessage(chatId, "Опрос остановлен");
                       } catch (TelegramApiException e) {
                           throw new RuntimeException(e);
                       }
                       break;
                   default:
                       try {
                           sendMessage(chatId, "Нет такого ключевого слова");
                       } catch (TelegramApiException e) {
                           throw new RuntimeException(e);
                       }
                       break;

               }
           }


        }
    }

    private void startCommandReceived(long chatId, String name) throws TelegramApiException {
        String answer = "Привет, "+name+"! Введи ключевое слово.";
        sendMessage(chatId, answer);
    }



    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Начать опрос");
        keyboardRows.add(row);
//        row = new KeyboardRow();
//        row.add("Test 3");
//        row.add("Test 4");
//        row.add("Test 5");
//
//        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);

        try{
            execute(message);
        } catch (TelegramApiException e){
            throw new RuntimeException(e);
        }
    }

    public Handler getHandlerUser(String userTag){
        f_is = false;

        if(userLinkObject.get(userTag) == null)
        {
            Handler userHandler = new Handler();
            userLinkObject.put(userTag, userHandler);
            return userLinkObject.get(userTag);

        }
        else{
            return userLinkObject.get(userTag);
        }



    }
}
