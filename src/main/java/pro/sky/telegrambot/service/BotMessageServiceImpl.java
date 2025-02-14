package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

@Service
public class BotMessageServiceImpl implements BotMessageService{
    private final TelegramBot telegramBot;

    public BotMessageServiceImpl(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);

        try {
            telegramBot.execute(sendMessage);
        } catch (RuntimeException exception){//TODO добавить кастомное исключение
            exception.printStackTrace();
        }
    }

}
