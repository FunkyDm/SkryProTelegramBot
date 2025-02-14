package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Unit test for BotMessageService class")
public class BotMessageServiceTest {
    private BotMessageService botMessageService;

    private TelegramBot telegramBot;

    @BeforeEach
    public void init(){
        telegramBot = Mockito.mock(TelegramBot.class);
        botMessageService = new BotMessageServiceImpl(telegramBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramException {
        //given
        Long chatId = 1L;
        String message = "testMessage";

        SendMessage sendMessage = new SendMessage(chatId, message);

        //when
        botMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(telegramBot).execute(sendMessage);
    }
}
