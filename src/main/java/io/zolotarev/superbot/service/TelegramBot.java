package io.zolotarev.superbot.service;
import io.zolotarev.superbot.dao.PollsDAO;
import org.checkerframework.checker.nullness.qual.KeyForBottom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.zolotarev.superbot.config.BotConfig;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.ArrayList;
import java.util.List;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    @Autowired
    private PollsDAO pollsDAO;

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
                       String message = pollsDAO.index();
                       sendMessage(chatId, message);
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
}
