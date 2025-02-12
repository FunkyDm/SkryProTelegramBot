package pro.sky.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.BotMessageService;
import pro.sky.telegrambot.state.UserStateStorage;

@Component
public class HelloCommand implements Command{
    private Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    public static String HELLO_MESSAGE = ", привет. Введи /start для начала работы с ботом.";

    private final BotMessageService botMessageService;

    public HelloCommand(BotMessageService botMessageService){
        this.botMessageService = botMessageService;
    }

    @Override
    public void handle(Update update){
        Long chatId = update.message().chat().id();
        String username = update.message().chat().firstName();
        String text = username + HELLO_MESSAGE;

        botMessageService.sendMessage(chatId, text);
        logger.info("Sent message: {}", text);

        UserStateStorage.setState(chatId, UserStateStorage.NONE);
        logger.info("User state set to WORK for chatId: {}", chatId);
    }

    @Override
    public String getCommand(){
        return "hello";
    }
}
