package interaction;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private String commandStringArgument;
    private Object commandObjectArgument;
    private User user;


    public Request(String commandName, String commandStringArgument,Serializable commandObjectArgument, User user){
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.commandObjectArgument = commandObjectArgument;
        this.user = user;

    }
    public Request(String commandName, String commandStringArgument, User user){
        this(commandName,commandStringArgument, null, user);


    }
    public Request(User user) {
        this("", "","",user);
    }

    public User getUser(){
        return user;
    }

    public Object getCommandObjectArgument() {
        return commandObjectArgument;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandStringArgument() {
        return commandStringArgument;
    }
    public boolean isEmpty() {
        return commandName.isEmpty() && commandStringArgument.isEmpty() && commandObjectArgument == null;
    }
}
