package pro.sky.telegrambot.command;


import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.repository.TaskRepository;
import pro.sky.telegrambot.service.BotMessageService;
import pro.sky.telegrambot.state.UserStateStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NotifyCommand implements Command {
    @Autowired
    private TaskRepository taskRepository;

    private final BotMessageService botMessageService;

    public NotifyCommand(BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.message().chat().id();
        String messageText = update.message().text();

        UserStateStorage currentState = UserStateStorage.getState(chatId);

        if (messageText.equals(getCommand()) && currentState == UserStateStorage.WORK) {
            botMessageService.sendMessage(chatId, "Отправьте напоминание в формате: ДД.ММ.ГГГГ ЧЧ:ММ Текст напоминания");
            UserStateStorage.setState(chatId, UserStateStorage.IN_WORK_WITH_NOTIFICATION);
        }

        if (currentState == UserStateStorage.IN_WORK_WITH_NOTIFICATION) {
            Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
            Matcher matcher = pattern.matcher(messageText);

            if (matcher.matches()) {
                String dateTimeString = matcher.group(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                LocalDateTime date = LocalDateTime.parse(dateTimeString, formatter);

                taskRepository.save(new Task(
                        chatId,
                        matcher.group(3),
                        date
                ));

                botMessageService.sendMessage(chatId, String.format("Напоминание добавлено: %s %s", dateTimeString, matcher.group(3)));

                UserStateStorage.setState(chatId, UserStateStorage.WORK);
            } else {
                botMessageService.sendMessage(chatId, "Неверный формат напоминания. Используйте формат: ДД.ММ.ГГГГ ЧЧ:ММ Текст напоминания");
            }
        }
    }

    @Override
    public String getCommand() {
        return "/notify";
    }

}
