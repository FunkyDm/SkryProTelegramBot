package pro.sky.telegrambot.command;

public enum CommandName {
    START("/start");

    private final String commandName;

    CommandName(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName(){
        return commandName;
    }
}
