package pro.sky.telegrambot.command;


import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.state.State;
import pro.sky.telegrambot.state.UserStateStorage;

@Component
public class NotifyCommand implements Command{
    @Override
    public void handle(Update update){
        Long chatId = update.message().chat().id();
        String messageText = update.message().text();

        State currentState = UserStateStorage.getState(chatId);
        
    }

    @Override
    public String getCommand(){
        return "/notify";
    }
}
