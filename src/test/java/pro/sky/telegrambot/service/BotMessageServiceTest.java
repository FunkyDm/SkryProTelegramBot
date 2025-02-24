package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Unit test for BotMessageService class")
@ExtendWith(MockitoExtension.class)
public class BotMessageServiceTest {
    private BotMessageService botMessageService;

    @Mock
    private TelegramBot telegramBot = Mockito.mock(TelegramBot.class);

    @BeforeEach
    void setup(){
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
        Mockito.verify(telegramBot).execute(Mockito.any(SendMessage.class));
    }
}
