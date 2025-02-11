package pro.sky.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.telegrambot.service.BotMessageService;

public class UnknownCommand implements Command{
    private final BotMessageService botMessageService;

    private Logger logger = LoggerFactory.getLogger(UnknownCommand.class);

    public UnknownCommand(BotMessageService botMessageService){
        this.botMessageService = botMessageService;
    }

    @Override
    public void handle(Update update){
        Long chatId = update.message().chat().id();
        String username = update.message().chat().firstName();
        String userMessage = update.message().text();
        botMessageService.sendMessage(chatId, String.format(username + "кажется, вы написали что-то в духе: " + userMessage));
        logger.info("Sent message: {}, кажется, вы написали что-то в духе: {}", username, userMessage);
    }

    @Override
    public String getCommand(){
        return "unknown command";
    }
}
