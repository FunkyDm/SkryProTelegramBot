package pro.sky.telegrambot.state;

import java.util.HashMap;
import java.util.Map;

public enum UserStateStorage {
    NONE,
    WORK,
    IN_WORK_WITH_NOTIFICATION;

    private static final Map<Long, UserStateStorage> userStates = new HashMap<>();

    public static UserStateStorage getState(Long chatId){
        return userStates.getOrDefault(chatId, WORK);
    }

    public static void setState(Long chatId, UserStateStorage state){
        userStates.put(chatId, state);
    }
}
