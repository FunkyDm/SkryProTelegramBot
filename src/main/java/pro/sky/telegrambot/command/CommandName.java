package pro.sky.telegrambot.command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    EXIT("/exit"),
    NO("unknown command"),
    HELLO("hello"),
    NOTIFY("/notify");

    private final String commandName;

    CommandName(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName(){
        return commandName;
    }
}
