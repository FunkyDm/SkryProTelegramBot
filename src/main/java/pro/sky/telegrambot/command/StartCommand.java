package pro.sky.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.BotMessageService;
import pro.sky.telegrambot.state.UserStateStorage;

@Component
public class StartCommand implements Command {
    private Logger logger = LoggerFactory.getLogger(StartCommand.class);

    private final BotMessageService botMessageService;

    public StartCommand(BotMessageService botMessageService){
        this.botMessageService = botMessageService;
    }

    public final static String START_MESSAGE = ", добро пожаловать в главное меню!\nВведите:\n/notify - добавить напоминание" +
            "\n/exit - приостановить текущую задачу" +
            "\n/help - вывести список доступных команд";

    @Override
    public void handle(Update update) {
        Long chatId = update.message().chat().id();
        String username = update.message().chat().firstName();
        String text = username + START_MESSAGE;

        botMessageService.sendMessage(chatId, text);
        logger.info("Sent message: {}", text);

        UserStateStorage.setState(chatId, UserStateStorage.WORK);
        logger.info("User state set to WORK for chatId: {}", chatId);
    }

    @Override
    public String getCommand() {
        return "/start";
    }
}
