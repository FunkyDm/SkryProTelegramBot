package pro.sky.telegrambot.command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    STOP("/stop"),
    NO("unknown command"),
    NOTIFY("/notify");

    private final String commandName;

    CommandName(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName(){
        return commandName;
    }
}
