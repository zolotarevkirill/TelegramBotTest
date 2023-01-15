package io.zolotarev.superbot.service;
import io.zolotarev.superbot.handler.Handler;
import org.springframework.stereotype.Component;
import io.zolotarev.superbot.config.BotConfig;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    String[] arrayUsersPolls = new String[10];
    HashMap<String, Handler> userLinkObject = new HashMap<>();
    boolean f_is;
    Handler handler;


    public TelegramBot(BotConfig config){
        this.config = config;
    }

    @Override
    public String getBotUsername(){
        return config.getBotName();
    }

    @Override
    public String getBotToken(){
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update){
        if(update.hasMessage() && update.getMessage().hasText())
        {
           String messageText = update.getMessage().getText();
           long chatId = update.getMessage().getChatId();
           handler = getHandlerUser(update.getMessage().getChat().getFirstName());
//           handler = getHandlerUser("Надежда");

            try {
                sendMessage(chatId, "Handler State = "+handler.getState());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

            if(handler.getState() == 1 && !Objects.equals(messageText, "/stop")){
               try {
                   sendMessage(chatId, handler.nextQuestion());
               } catch (TelegramApiException e) {
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
                   case "/polls":
                       try {
                           handler = getHandlerUser(update.getMessage().getChat().getFirstName());
//                           handler = getHandlerUser("Надежда");
                           String message = handler.pollsInit();
                           sendMessage(chatId, message);
                       } catch (TelegramApiException e) {
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

        row.add("Test 1");
        row.add("Test 2");
        row.add("Test 3");

        keyboardRows.add(row);

        row = new KeyboardRow();

        row.add("Test 3");
        row.add("Test 4");
        row.add("Test 5");

        keyboardRows.add(row);

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
        System.out.println("Длина массива");
        System.out.println(arrayUsersPolls.length);
        System.out.println(arrayUsersPolls[0]);
        System.out.println(arrayUsersPolls[1]);
        System.out.println(userLinkObject.get("r4353"));
        System.out.println("________");

        if(userLinkObject.get(userTag) == null)
        {
            System.out.println("Не Нашел");
            System.out.println(userTag);
            System.out.println("________");
            Handler userHandler = new Handler();
            userLinkObject.put(userTag, userHandler);
            return userLinkObject.get(userTag);

        }
        else{
            System.out.println("Нашел");
            System.out.println(userTag);
            System.out.println("________");
            return userLinkObject.get(userTag);
        }



    }
}
