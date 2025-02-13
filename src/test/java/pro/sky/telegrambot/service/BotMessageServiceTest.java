package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
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
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramException {
        //given

        //when

        //then

    }
}
