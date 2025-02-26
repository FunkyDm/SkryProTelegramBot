package pro.sky.telegrambot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.service.BotMessageService;
import pro.sky.telegrambot.state.UserStateStorage;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit-level testing for all existing commands")
public class AllComandsTest {

    @Mock
    private BotMessageService botMessageService;

    @Mock
    private ExitCommand exitCommand;

    @Mock
    private HelpCommand helpCommand;

    @Mock
    private NotifyCommand notifyCommand;

    @Mock
    private StartCommand startCommand;

    @Mock
    private UnknownCommand unknownCommand;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private Chat chat;

    @Mock
    private CommandContainer commandContainer;

    @InjectMocks
    private TelegramBotUpdatesListener updatesListener;

    public static Stream<Arguments> provideParamsForTests(){
        return Stream.of(
                Arguments.of(1L)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTests")
    public void testProcessWithCommandStart(Long chatId){
        UserStateStorage.setState(chatId, UserStateStorage.WORK);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(message.text()).thenReturn("/start");
        when(commandContainer.retrieveCommand(message.text())).thenReturn(startCommand);

        List<Update> updates = List.of(update);

        updatesListener.process(updates);

        verify(startCommand).handle(update);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTests")
    public void testProcessWithUnknownCommand(Long chatId){
        UserStateStorage.setState(chatId, UserStateStorage.WORK);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(message.text()).thenReturn("unknown command");
        when(commandContainer.retrieveCommand(message.text())).thenReturn(unknownCommand);

        List<Update> updates = List.of(update);

        updatesListener.process(updates);

        verify(commandContainer).retrieveCommand("unknown command");
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTests")
    public void testProcessWithCommandExit(Long chatId){
        UserStateStorage.setState(chatId, UserStateStorage.NONE);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(message.text()).thenReturn("/exit");
        when(commandContainer.retrieveCommand(message.text())).thenReturn(exitCommand);

        List<Update> updates = List.of(update);

        updatesListener.process(updates);

        verify(exitCommand).handle(update);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTests")
    public void testProcessWithCommandHelp(Long chatId){
        UserStateStorage.setState(chatId, UserStateStorage.WORK);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(message.text()).thenReturn("/help");
        when(commandContainer.retrieveCommand(message.text())).thenReturn(helpCommand);

        List<Update> updates = List.of(update);

        updatesListener.process(updates);

        verify(helpCommand).handle(update);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTests")
    public void testProcessWithCommandNotify(Long chatId){
        UserStateStorage.setState(chatId, UserStateStorage.IN_WORK_WITH_NOTIFICATION);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(message.text()).thenReturn("/notify");
        when(commandContainer.retrieveCommand(message.text())).thenReturn(notifyCommand);

        List<Update> updates = List.of(update);

        updatesListener.process(updates);

        verify(notifyCommand).handle(update);
    }
}
