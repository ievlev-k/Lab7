
import core.*;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import commands.*;

public class Server {
    private int port;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20000);
    private ServerSocket serverSocket;

    private CommandManager commandManager;
    public Server(int port, CommandManager commandManager){
        this.port = port;
        this.commandManager = commandManager;
    }

    private void openServerSocket() {

        try{
            serverSocket = new ServerSocket(port);

        }catch (IOException e){
            try {
                serverSocket.close();
            }catch (IOException exception){
                System.out.println("Не получилось освободить socket!");
            }catch (NullPointerException exception){
                System.out.println("Не получилось освободить socket!");
            }

            System.out.println("Не получилось открыть socket!");
        }
    }


    private Socket connectToClient() {

        try{
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент успешно подключился!");
            return clientSocket;
        }catch (IOException e){
            System.out.println("Произошла ошибка при соединении с клиентом!");
            return  null;
        }
    }

    public void start(){

        openServerSocket();
        while (true){
            Socket client = connectToClient();
            fixedThreadPool.submit(new Executor(client, commandManager));


        }
    }
}




