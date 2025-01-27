package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.request.SendMessage;
import liquibase.pro.packaged.S;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartHandler implements Handler {
    @Override
    public List<SendMessage> handle(String message){
        SendMessage welcomeMessage =
    }
}
