package pro.sky.telegrambot.state;

import java.util.HashMap;
import java.util.Map;

public class UserStateStorage {
    private static final Map<Long, State> userStates = new HashMap<>();

    public static State getState(Long chatId){
        return userStates.getOrDefault(chatId, State.WORK);
    }

    public static void setState(Long chatId, State state){
        userStates.put(chatId, state);
    }
}
