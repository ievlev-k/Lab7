package commands;

import core.ResponseOutput;

public class ExecuteScriptCommand extends AbstractCommand{
    @Override
    public boolean execute(String argument, Object oArgument) {
        ResponseOutput.appendln("Выполнение");
        return true;
    }
}
