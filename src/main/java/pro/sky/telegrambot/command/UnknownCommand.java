package pro.sky.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import pro.sky.telegrambot.service.BotMessageService;

public class UnknownCommand implements Command{
    //public static final String UNKNOWN_MESSAGE =

    private final BotMessageService botMessageService;

    public UnknownCommand(BotMessageService botMessageService){
        this.botMessageService = botMessageService;
    }

    @Override
    public void handle(Update update){
        Long chatId = update.message().chat().id();
        String username = update.message().chat().firstName();
        String userMessage = update.message().text();
        botMessageService.sendMessage(chatId, String.format(username + "кажется, вы написали что-то в духе: " + userMessage));
    }

    @Override
    public String getCommand(){
        return "unknown command";
    }
}
