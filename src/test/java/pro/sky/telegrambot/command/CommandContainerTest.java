package pro.sky.telegrambot.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.repository.TaskRepository;
import pro.sky.telegrambot.service.BotMessageService;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {
    private CommandContainer commandContainer;

    @BeforeEach
    void setup() {
        BotMessageService botMessageService = Mockito.mock(BotMessageService.class);
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        commandContainer = new CommandContainer(botMessageService, taskRepository);
    }

    @Test
    public void shouldReturnAllExistingCommands() {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void shouldReturnUnknownCommand(){
        //given
        String unknownCommand="someRandomThings";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand);

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}
