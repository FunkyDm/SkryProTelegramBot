package pro.sky.telegrambot.command;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.BotMessageService;

import static pro.sky.telegrambot.command.CommandName.*;

@Component
public class CommandContainer {
    private final ImmutableMap<String,Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(BotMessageService botMessageService){
        commandMap = ImmutableMap.<String,Command>builder()
                .put(START.getCommandName(), new StartCommand(botMessageService))
                .put(NOTIFY.getCommandName(), new NotifyCommand(botMessageService))
                .build();

        unknownCommand = new UnknownCommand(botMessageService);
    }

    public Command retrieveCommand(String coomandIdentifier){
        return commandMap.getOrDefault(coomandIdentifier, unknownCommand);
    }
}
