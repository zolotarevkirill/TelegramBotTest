package io.zolotarev.superbot.service;
import io.zolotarev.superbot.handler.Handler;
import lombok.NonNull;

import org.springframework.stereotype.Component;
import io.zolotarev.superbot.config.BotConfig;
import io.zolotarev.superbot.dao.SchoolUsersDAO;
import io.zolotarev.superbot.dao.ImagesDAO;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// import com.nimbusds.jose.Header;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import org.telegram.telegrambots.meta.api.methods.GetFile;
// import net.minidev.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import java.util.Random;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FileUtils.getFile;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    String[] arrayUsersPolls = new String[10];
    HashMap<String, Handler> userLinkObject = new HashMap<>();
    boolean f_is;
    Handler handler;
    int indexJava;

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

    private SchoolUsersDAO schoolUsersDAO  = new SchoolUsersDAO();
    private ImagesDAO imagesDAO  = new ImagesDAO();



    @Override
    public void onUpdateReceived(Update update){
        
        // if(update.hasMessage() && update.getMessage().hasText()){
        //     String messageText = update.getMessage().getText();
        //     long chatId = update.getMessage().getChatId();
        //     String lastName =  returnStringOrNull(update.getMessage().getChat().getLastName());
        //     String firstName = returnStringOrNull(update.getMessage().getChat().getFirstName());
        //     String userName =  returnStringOrNull(update.getMessage().getChat().getUserName());
        //     String titleChat = returnStringOrNull(update.getMessage().getChat().getTitle());
        //     // long messageId = update.getMessage().getMessageId();
        //     // String adminChatId = String.valueOf("-1001530953532");
        //     // String message = "*Сообщение от "+lastName+" "+firstName+" "+userName+" "+".* \n*В чате:* "+titleChat+" \n*Текст:* \n"+messageText+" \n";
        //     // if(lastName != "Золотарева")
        //     // {
        //     //     try {
        //     //         sendMessage(adminChatId, message);
        //     //     } catch (TelegramApiException e) {
        //     //         e.printStackTrace();
        //     //     }
        //     // }
            
        // }

        // 1001530953532
        // 1001633011508

//         if (update.getMessage().hasPhoto()){
//             long chatId = update.getMessage().getChatId();
//             String lastName =  update.getMessage().getChat().getLastName();
//             String firstName = update.getMessage().getChat().getFirstName();
//             String userName =  update.getMessage().getChat().getUserName();
//             String nickName = userName+"_"+firstName+"_"+lastName;

//             // System.out.println("****!****");
//             // System.out.println(nickName);
//             // System.out.println("****!****");

//             try {
//                 if(schoolUsersDAO.isUserRegisterByNickName(nickName))
//                 {
//                     try {
//                         sendMessage(chatId, "Ловлю файл!");

//                         //Работа с фото из бота
//                         sendMessage(chatId, "Начинаю загрузку только первого файла!");
//                         List<PhotoSize> photos = update.getMessage().getPhoto();
//                         PhotoSize photo = photos.get(0);
//                         String fileId = photo.getFileId();
//                         String filePath = photo.getFilePath();
//                         String botToken = getBotToken();

//                         if(imagesDAO.addImageID(nickName, fileId)){
// //                            URL url = new URL("https://api.telegram.org/bot"+botToken+"/getFile?file_id="+fileId);
//                             URL url = new URL("https://api.telegram.org/file/bot"+botToken+"/photos/file_1.jpg");
//                             sendMessage(chatId, "Загрузил!");
//                             sendMessage(chatId, "Вот ссылка "+url);
//                             sendMessage(chatId, ""+filePath);
//                         }


//                     } catch (TelegramApiException | IOException e) {
//                         throw new RuntimeException(e);
//                     }
//                 }
//                 else{
//                     update.getMessage().setDeleteChatPhoto(true);
//                     update.getMessage().getDeleteChatPhoto();
//                     sendMessage(chatId, "Прости, нет регистрации. Обратись к Надежде Алексеевне");

//                 }
//             } catch (SQLException e) {
//                 throw new RuntimeException(e);
//             } catch (TelegramApiException e) {
//                 throw new RuntimeException(e);
//             }


//         }

        if(update.hasMessage() && update.getMessage().hasText())
        {
           String messageText = update.getMessage().getText();
           Long chatId = update.getMessage().getChatId();
           String lastName =  update.getMessage().getChat().getLastName();
           String firstName = update.getMessage().getChat().getFirstName();
           String userName =  update.getMessage().getChat().getUserName();
           String userTag = userName+"_"+firstName+"_"+lastName;
           int indexPolls = messageText.indexOf("Начать опрос");
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
                if(indexPolls == - 1){
                    switch (messageText){
                        case "/start":
                            try {
                                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                            break;
     
                        case "SECRET_TEST_ZOLOTAREV":
                            try {
                                handler = getHandlerUser(userTag);
                                handler.setPollId(1);
                                int stateUser = handler.checkStateUser(userTag);
                                String message = "Опрос уже пройден! ";
                                 
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
                                sendMessage(chatId, "Нет такого ключевого слова !");
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                            break;
     
                    }
                }else{
                    String numberPoll = messageText.replaceAll("Начать опрос", "");
                    try {
                        handler = getHandlerUser(userTag);
                        handler.setPollId(Integer.parseInt(numberPoll.trim()));
                        int stateUser = handler.checkStateUser(userTag);
                        String message = "Опрос уже пройден!";
                         
                        if(stateUser !=  1){
                       
                           message = handler.pollsInit();
                        }

                        sendMessage(chatId, message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

               
           }


        }
    }

    public String returnStringOrNull(String string){
        if(string == null){
            return "";
        }
        else{
            return string;
        }
    }

    private void startCommandReceived(Long chatId, String name) throws TelegramApiException {
        String answer = "Привет, "+name+"! Введи ключевое слово.";
        sendMessage(chatId, answer);
    }



    private void sendMessage(Long chatId, String textToSend) throws TelegramApiException {

        SendMessage message = new SendMessage();

        if(textToSend == "THE END"){
            message.setChatId(String.valueOf(chatId));
            message.setParseMode("Markdown");
            message.setText("Молодец! Лови подарок!");
            String stickerID = "CAACAgIAAxkBAAEId4BkLUlOgJloFttsZ3r0ITz9uwrNLwACQAADr8ZRGldV33CiNs2qLwQ";
            InputFile inputFile = new InputFile(stickerID);
            SendSticker sendSticker = new SendSticker();
            sendSticker.setChatId(String.valueOf(chatId));
            sendSticker.setSticker(inputFile);
          try {
            execute(sendSticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        }else{
            message.setChatId(String.valueOf(chatId));
            message.setParseMode("Markdown");
            message.setText(textToSend);
        }
        

//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//
//        List<KeyboardRow> keyboardRows = new ArrayList<>();
//        KeyboardRow row = new KeyboardRow();
//        row.add("Начать опрос");
//        keyboardRows.add(row);
//        keyboardMarkup.setKeyboard(keyboardRows);
//        message.setReplyMarkup(keyboardMarkup);

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
