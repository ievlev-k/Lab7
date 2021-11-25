package core;
import commands.*;
import interaction.User;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CommandManager {
    private ReadWriteLock collectionLocker = new ReentrantReadWriteLock();
    private final Command helpCommand;
    private final Command addCommand;
    private final Command infoCommand;
    private final Command saveCommand;
    private final Command showCommand;
    private final Command registerCommand;
    private final Command removeFirstCommand;
    private final Command updateIdCommand;
    private final Command removeByIdCommand;
    private final Command clearCommand;
    private final Command exitCommand;
    private final Command removeGreaterCommand;
    private final Command removeLowerCommand;
    private final Command filterByStandardOfLivingCommand;
    private final Command filterGreaterThanGovernmentCommand;
    private final Command printAscendingCommand;
    private final Command executeScript;
    private final Command loginCommand;
    public CommandManager(Command helpCommand, Command saveCommand,
                          Command removeFirstCommand, Command removeLowerCommand,
                          Command removeGreaterCommand, Command filterByStandardOfLivingCommand, Command addCommand,
                          Command infoCommand, Command filterGreaterThanGovernmentCommand, Command showCommand, Command updateIdCommand,
                          Command removeByIdCommand, Command clearCommand, Command exitCommand, Command printAscendingCommand, Command executeScript, Command registerCommand, Command loginCommand) {
        this.helpCommand = helpCommand;
        this.saveCommand = saveCommand;
        this.removeFirstCommand = removeFirstCommand;
        this.removeGreaterCommand = removeGreaterCommand;
        this.addCommand = addCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.filterByStandardOfLivingCommand = filterByStandardOfLivingCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.updateIdCommand = updateIdCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.exitCommand = exitCommand;
        this.filterGreaterThanGovernmentCommand = filterGreaterThanGovernmentCommand;
        this.printAscendingCommand = printAscendingCommand;
        this.executeScript = executeScript;
        this.registerCommand = registerCommand;
        this.loginCommand = loginCommand;
    }

    public boolean help() {
        return helpCommand.execute();
    }

    public boolean execute_script(String sArgument, Object oArgument){return executeScript.execute(sArgument, oArgument);}

    public boolean add(String sArgument, Object oArgument, User user) {
        return addCommand.execute(sArgument, oArgument, user);
    }

    public boolean info() {
        collectionLocker.readLock().lock();
        try {
            return infoCommand.execute();
        }finally {
            collectionLocker.readLock().unlock();
        }

    }

    public boolean show() {
        collectionLocker.readLock().lock();
        try {
            return showCommand.execute();
        }finally {
            collectionLocker.readLock().unlock();
        }

    }



    public boolean remove_first(User user){
        collectionLocker.readLock().lock();
        try {
            return removeFirstCommand.execute(user);
        }finally {
            collectionLocker.readLock().unlock();
        }

    }

    public boolean update_id(String sArgument, Object oArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return updateIdCommand.execute(sArgument, oArgument, user);
        }finally {
            collectionLocker.readLock().unlock();
        }

    }

    public boolean remove_by_id(String commandStringArgument,Object commandObjectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return removeByIdCommand.execute(commandStringArgument, commandObjectArgument, user);
        }finally {
            collectionLocker.readLock().unlock();
        }

    }



    public boolean clear(User user) {
        collectionLocker.readLock().lock();
        try {
            return clearCommand.execute(user);
        }finally {
            collectionLocker.readLock().unlock();
        }

    }

    public boolean exit() {
        return exitCommand.execute();
    }

    public boolean remove_greater(String commandStringArgument,Object commandObjectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return removeGreaterCommand.execute(commandStringArgument, commandObjectArgument, user);
        }finally {
            collectionLocker.readLock().unlock();
        }

    }

    public boolean remove_lower(String commandStringArgument,Object commandObjectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return removeLowerCommand.execute(commandStringArgument, commandObjectArgument, user);
        }finally {
            collectionLocker.readLock().unlock();
        }

    }

    public boolean filter_by_standard_of_living(String commandStringArgument,Object commandObjectArgument) {
        collectionLocker.readLock().lock();
        try {
            return filterByStandardOfLivingCommand.execute(commandStringArgument, commandObjectArgument);
        }finally {
            collectionLocker.readLock().unlock();
        }

    }

    public boolean register(String commandStringArgument, Object commandObjectArgument, User user){
        return registerCommand.execute(commandStringArgument,commandObjectArgument, user);
    }

    public boolean filter_greater_than_government(String commandStringArgument,Object commandObjectArgument) {
        collectionLocker.readLock().lock();
        try {
            return filterGreaterThanGovernmentCommand.execute(commandStringArgument, commandObjectArgument);
        }finally {
            collectionLocker.readLock().unlock();
        }

    }

    public boolean print_ascending(){
        collectionLocker.readLock().lock();
        try {
            return printAscendingCommand.execute();
        }finally {
            collectionLocker.readLock().unlock();
        }

    }

    public boolean login(String commandStringArgument, Object commandObjectArgument, User user){
        return loginCommand.execute(commandStringArgument,commandObjectArgument, user);
    }
}

