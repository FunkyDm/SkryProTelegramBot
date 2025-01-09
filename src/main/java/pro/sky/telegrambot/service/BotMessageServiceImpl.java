package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class BotMessageServiceImpl implements BotMessageService{
    private final TelegramBot telegramBot;

    @Autowired
    public BotMessageServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);

        try {
            telegramBot.execute(sendMessage);
        } catch (RuntimeException exception){//TODO добавить кастомное исключение
            exception.printStackTrace();
        }
    }

}
