package pro.sky.telegrambot.sheduler;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.repository.TaskRepository;
import pro.sky.telegrambot.service.BotMessageService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class SheduledTasks {
    private final TelegramBot telegramBot;

    private final TaskRepository taskRepository;

    private final BotMessageService botMessageService;

    public SheduledTasks(TelegramBot telegramBot, TaskRepository taskRepository, BotMessageService botMessageService) {
        this.taskRepository = taskRepository;
        this.telegramBot = telegramBot;
        this.botMessageService = botMessageService;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Task> tasks = taskRepository.findByTaskDate(now);

        for (Task task : tasks) {
            Long chatId = task.getChatId();
            botMessageService.sendMessage(chatId, task.getMessageText());
            task.setStatus("sent");
            taskRepository.save(task);
        }
    }
}
