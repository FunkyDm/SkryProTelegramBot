package pro.sky.telegrambot.command;


import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.BotMessageService;
import pro.sky.telegrambot.state.State;
import pro.sky.telegrambot.state.UserStateStorage;

@Component
public class NotifyCommand implements Command {
    private final BotMessageService botMessageService;

    public NotifyCommand(BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.message().chat().id();
        String messageText = update.message().text();

        State currentState = UserStateStorage.getState(chatId);

        if (messageText.equals(getCommand()) && currentState == State.START) {
            botMessageService.sendMessage(chatId, "Отправьте напоминание в формате: ДД.ММ.ГГГГ ЧЧ:ММ Текст напоминания");
            UserStateStorage.setState(chatId, State.IN_WORK_WITH_NOTIFICATION);
        }


    }

    @Override
    public String getCommand() {
        return "/notify";
    }
}
