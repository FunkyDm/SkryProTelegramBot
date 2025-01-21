package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import pro.sky.telegrambot.state.State;

import java.util.List;

public interface Handler {
    List<SendMessage> handle(String message);

    State operatedState();

    List<String> operatedCallBackQuery();
}
