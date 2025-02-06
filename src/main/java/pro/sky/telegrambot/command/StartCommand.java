package pro.sky.telegrambot.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.BotMessageService;
import pro.sky.telegrambot.state.UserStateStorage;

@Component
public class StartCommand implements Command {
    @Autowired
    private TelegramBot telegramBot;

    //private Logger logger = LoggerFactory.getLogger(StartCommand.class);

    private final BotMessageService botMessageService;

    public StartCommand(BotMessageService botMessageService){
        this.botMessageService = botMessageService;
    }

    public final static String START_MESSAGE = ", добро пожаловать в главное меню!\nСписок доступных команд:\n/notify - добавить напоминание" +
            "\n/exit - выход в главное меню";

    @Override
    public void handle(Update update) {
        Long chatId = update.message().chat().id();
        String username = update.message().chat().firstName();
        String text = username + START_MESSAGE;

        botMessageService.sendMessage(chatId, text);

        UserStateStorage.setState(chatId, UserStateStorage.WORK);
    }

    @Override
    public String getCommand() {
        return "/start";
    }
}
