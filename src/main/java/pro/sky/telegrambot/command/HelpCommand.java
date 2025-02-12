package pro.sky.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.telegrambot.service.BotMessageService;
import pro.sky.telegrambot.state.UserStateStorage;

public class HelpCommand implements Command {
    private Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    private final BotMessageService botMessageService;

    public HelpCommand(BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
    }

    public final static String HELP_MESSAGE = ", Список доступных команд:\n/notify - добавить напоминание" +
            "\n/exit - приостановить текущую задачу" +
            "\n/start - выйти в главное меню";

    @Override
    public void handle(Update update) {
        Long chatId = update.message().chat().id();
        String username = update.message().chat().firstName();
        String text = username + HELP_MESSAGE;

        botMessageService.sendMessage(chatId, text);
        logger.info("Sent message: {}", text);

        UserStateStorage.setState(chatId, UserStateStorage.WORK);
        logger.info("User state set to WORK for chatId: {}", chatId);
    }

    @Override
    public String getCommand() {
        return "/help";
    }
}
