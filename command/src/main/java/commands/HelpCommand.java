package commands;

import core.ResponseOutput;

public class HelpCommand extends AbstractCommand{
    @Override
    public boolean execute() {
        ResponseOutput.appendln("help                                            - вывести справку по доступным командам");
        ResponseOutput.appendln("info                                            - вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        ResponseOutput.appendln("show                                            - вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        ResponseOutput.appendln("add {element}                                   - добавить новый элемент в коллекцию");

        ResponseOutput.appendln("update id {element}                             - обновить значение элемента коллекции, id которого равен заданному");
        ResponseOutput.appendln("remove_by_id id                                 - удалить элемент из коллекции по его id");
        ResponseOutput.appendln("clear                                           - очистить коллекцию");
        ResponseOutput.appendln("save                                            - сохранить коллекцию в файл");
        ResponseOutput.appendln("execute_script file_name                        - читать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        ResponseOutput.appendln("remove_first                                    - удалить первый элемент из коллекции");
        ResponseOutput.appendln("remove_greater                                  - удалить из коллекции все элементы, превышающие заданный");
        ResponseOutput.appendln("remove_lower                                    - удалить из коллекции все элементы, меньшие, чем заданный");
        ResponseOutput.appendln("filter_by_standard_of_living standardOfLiving   - вывести элементы, значение поля standardOfLiving которых равно заданному");
        ResponseOutput.appendln("filter_greater_than_government government       - вывести элементы, значение поля government которых больше заданного");
        ResponseOutput.appendln("print_ascending                                 - вывести элементы коллекции в порядке возрастания");


        return true;
    }
}
