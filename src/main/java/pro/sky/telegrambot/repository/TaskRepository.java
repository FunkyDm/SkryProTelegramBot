package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
}
