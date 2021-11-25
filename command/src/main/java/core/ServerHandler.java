package core;

import interaction.Request;
import interaction.Response;
import interaction.ResponseCode;
import interaction.User;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.RecursiveTask;

public class ServerHandler implements Callable<Response> {
    private CommandManager commandManager;

    private final HashMap<String, Boolean> inStack = new HashMap<>();
    private final String outputFile = "oFile";
    private Request request;
    public ServerHandler(CommandManager commandManager, Request request) {
        this.commandManager = commandManager;
        this.request = request;

    }



    public Response call(){
        User hashUser = new User(request.getUser().getUsername(), PasswordHandler.hashPassword(request.getUser().getPassword()));
        ResponseCode responseCode = executeCommand(request.getCommandName(), request.getCommandStringArgument(), request.getCommandObjectArgument(), hashUser);
        return new Response(responseCode, ResponseOutput.getAndClear());
    }

    private ResponseCode executeCommand(String command, String commandStringArgument, Object commandObjectArgument, User user){

        switch (command) {
            case "help":
                if (!commandManager.help()) {
                    return ResponseCode.ERROR;
                }
                break;

            case "execute_script":
                if (!commandManager.execute_script(commandStringArgument, commandObjectArgument)){
                    return ResponseCode.ERROR;
                }
                break;
            case "info":

                if (!commandManager.info()) {
                    return ResponseCode.ERROR;
                }
                break;

            case "show":

                if (!commandManager.show()) {
                    return ResponseCode.ERROR;
                }
                break;
            case "add":
                if (!commandManager.add(commandStringArgument, commandObjectArgument, user)) {
                    return ResponseCode.ERROR;
                }
                break;
            case "clear":
                if (!commandManager.clear(user)) {
                    return ResponseCode.ERROR;
                }
                break;
            case "update":

                if (!commandManager.update_id(commandStringArgument, commandObjectArgument, user)) {
                    return ResponseCode.ERROR;
                }
                break;
            case "remove_by_id":

                if (!commandManager.remove_by_id(commandStringArgument, commandObjectArgument, user)) {
                    return ResponseCode.ERROR;
                }
                break;


            case "exit":
                if (!commandManager.exit()) {
                    return ResponseCode.ERROR;
                }

                return ResponseCode.SERVER_EXIT;
            case "remove_first":

                if (!commandManager.remove_first(user)) {
                    return ResponseCode.ERROR;
                }
                break;

            case "remove_greater":

                if (!commandManager.remove_greater(commandStringArgument, commandObjectArgument, user)) {
                    return ResponseCode.ERROR;
                }
                break;
            case "remove_lower":
                if (!commandManager.remove_lower(commandStringArgument, commandObjectArgument, user)) {
                    return ResponseCode.ERROR;
                }
                break;
            case "filter_by_standard_of_living":

                if(!commandManager.filter_by_standard_of_living(commandStringArgument, commandObjectArgument)) {
                    return ResponseCode.ERROR;
                }
                break;

            case "filter_greater_than_government":

                if (!commandManager.filter_greater_than_government(commandStringArgument, commandObjectArgument)) {
                    return ResponseCode.ERROR;
                }
                break;

            case "print_ascending":
                if (!commandManager.print_ascending()) {
                    return ResponseCode.ERROR;
                }
                break;
            case "register":

                if (!commandManager.register(commandStringArgument, commandObjectArgument, user)){
                    return  ResponseCode.ERROR;
                }

                break;

            case "login":
                if (!commandManager.login(commandStringArgument, commandObjectArgument, user)){
                    return  ResponseCode.ERROR;
                }

                break;
            default:
                System.out.println("Такой команды нет!");
                return ResponseCode.ERROR;
        }
        return ResponseCode.OK;


    }

}
