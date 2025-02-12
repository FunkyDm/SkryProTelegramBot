package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.command.CommandContainer;
import pro.sky.telegrambot.command.NotifyCommand;
import pro.sky.telegrambot.service.BotMessageServiceImpl;
import pro.sky.telegrambot.state.UserStateStorage;

import javax.annotation.PostConstruct;
import java.util.List;

import static pro.sky.telegrambot.command.CommandName.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    public static String COMMAND_PREFIX = "/";

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private CommandContainer commandContainer;

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            if (notifyCheck(update)) {
                commandContainer.retrieveCommand("/notify").handle(update);
            } else if (update.message() != null && update.message().text() != null) {
                String message = update.message().text().trim();
                if (message.startsWith(COMMAND_PREFIX)) {
                    String commandIdentifier = message.split(" ")[0].toLowerCase();
                    commandContainer.retrieveCommand(commandIdentifier).handle(update);
                } else {
                    commandContainer.retrieveCommand(NO.getCommandName()).handle(update);
                }
            }
            else{
                commandContainer.retrieveCommand("hello");
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private boolean notifyCheck(Update update) {
        Long chatId = update.message().chat().id();
        String text = update.message().text();
        UserStateStorage currentState = UserStateStorage.getState(chatId);
        logger.info("Current state for chatId {}: {}", chatId, currentState);
        if (currentState == UserStateStorage.IN_WORK_WITH_NOTIFICATION && !text.equals("/exit")) {
            return true;
        }
        return false;
    }

}
