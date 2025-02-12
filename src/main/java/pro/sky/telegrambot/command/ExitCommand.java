package pro.sky.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.BotMessageService;
import pro.sky.telegrambot.state.UserStateStorage;

@Component
public class ExitCommand implements Command{
    private final Logger logger = LoggerFactory.getLogger(ExitCommand.class);

    private final BotMessageService botMessageService;

    public ExitCommand(BotMessageService botMessageService){
        this.botMessageService = botMessageService;
    }

    public final static String EXIT_MESSAGE = ", текущая работа бота приостановлена. Для возвращения в главное меню введите команду /start.";

    @Override
    public void handle(Update update){
        Long chatId = update.message().chat().id();
        String username = update.message().chat().firstName();
        String text = username + EXIT_MESSAGE;

        botMessageService.sendMessage(chatId, text);
        logger.info("Sent message: {}", text);

        UserStateStorage.setState(chatId, UserStateStorage.NONE);
        logger.info("User state set to WORK for chatId: {}", chatId);
    }

    @Override
    public String getCommand(){
        return "/exit";
    }
}
