package pro.sky.telegrambot.command;


import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final TaskRepository taskRepository;

    private final BotMessageService botMessageService;

    public NotifyCommand(BotMessageService botMessageService, TaskRepository taskRepository) {
        this.botMessageService = botMessageService;
        this.taskRepository = taskRepository;
    }

    private Logger logger = LoggerFactory.getLogger(NotifyCommand.class);

    @Override
    public void handle(Update update) {
        Long chatId = update.message().chat().id();
        String messageText = update.message().text();
        UserStateStorage currentState = UserStateStorage.getState(chatId);
        logger.info("Current state for chatId {}: {}", chatId, currentState);

        if (messageText.equals(getCommand()) && currentState == UserStateStorage.WORK) {
            logger.info("Command /notify received for chatId: {}", chatId);

            botMessageService.sendMessage(chatId, "Отправьте напоминание в формате: ДД.ММ.ГГГГ ЧЧ:ММ Текст напоминания");
            logger.info("Sent message: Отправьте напоминание в формате: ДД.ММ.ГГГГ ЧЧ:ММ Текст напоминания");

            UserStateStorage.setState(chatId, UserStateStorage.IN_WORK_WITH_NOTIFICATION);
            logger.info("User state set to IN_WORK_WITH_NOTIFICATION for chatId: {}", chatId);
        }

        if (currentState == UserStateStorage.IN_WORK_WITH_NOTIFICATION) {
            logger.info("Entered WAITING_FOR_NOTIFICATION state for chatID: {}", chatId);

            Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
            Matcher matcher = pattern.matcher(messageText);

            if (matcher.matches()) {
                logger.info("Pattern matched for message: {}", messageText);

                String dateTimeString = matcher.group(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                LocalDateTime date = LocalDateTime.parse(dateTimeString, formatter);

                taskRepository.save(new Task(
                        chatId,
                        matcher.group(3),
                        date
                ));
                logger.info("Notification task saved for chatID: {}, notification: {}, date: {}", chatId, matcher.group(3), date);

                botMessageService.sendMessage(chatId, String.format("Напоминание добавлено: %s %s", dateTimeString, matcher.group(3)));
                logger.info("Sent message: {}", String.format("Напоминание добавлено: %s, %s", date, matcher.group(3)));

                UserStateStorage.setState(chatId, UserStateStorage.WORK);
                logger.info("User state set to WORK for chatID: {}", chatId);
            }
            else {
                logger.info("Pattern did not match for message: {}", messageText);
                botMessageService.sendMessage(chatId, "Неверный формат напоминания. Используйте формат: ДД.ММ.ГГГГ ЧЧ:ММ Текст напоминания");
                logger.info("Sent message: Неверный формат напоминания. Пожалуйста, используйте формат: ДД.ММ.ГГГГ ЧЧ:ММ Текст напоминания");
            }
        }
    }

    @Override
    public String getCommand() {
        return "/notify";
    }

}
