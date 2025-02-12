package pro.sky.telegrambot.command;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.repository.TaskRepository;
import pro.sky.telegrambot.service.BotMessageService;

import static pro.sky.telegrambot.command.CommandName.*;

@Component
public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    @Autowired
    private TaskRepository taskRepository;

    public CommandContainer(BotMessageService botMessageService, TaskRepository taskRepository) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(botMessageService))
                .put(NOTIFY.getCommandName(), new NotifyCommand(botMessageService, taskRepository))
                .put(EXIT.getCommandName(), new ExitCommand(botMessageService))
                .put(HELP.getCommandName(), new HelpCommand(botMessageService))
                .put(HELLO.getCommandName(), new HelloCommand(botMessageService))
                .build();

        unknownCommand = new UnknownCommand(botMessageService);
    }

    public Command retrieveCommand(String coomandIdentifier) {
        return commandMap.getOrDefault(coomandIdentifier, unknownCommand);
    }
}
