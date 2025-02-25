package pro.sky.telegrambot.command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    EXIT("/exit"),
    NOTIFY("/notify");

    private final String commandName;

    CommandName(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName(){
        return commandName;
    }
}
