
import core.CommandManager;
import core.ServerHandler;
import interaction.Request;
import interaction.Response;
import interaction.ResponseCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Executor implements Runnable{
    private final Socket client;

    private ExecutorService responce = Executors.newCachedThreadPool();
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private CommandManager commandManager;
    public Executor(Socket client, CommandManager commandManager){
        this.client = client;
        this.commandManager = commandManager;
    }


    @Override
    public void run(){
        while (processClientRequest()) {}
    }

    private boolean processClientRequest(){
        Request userRequest = null;
        Response responseToUser = null;
        Future<Response> responseFuture = null;
        try(ObjectInputStream clientReader = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream clientWriter = new ObjectOutputStream(client.getOutputStream())) {
            do {

                userRequest = (Request) clientReader.readObject();

                ServerHandler serverHandler = new ServerHandler(commandManager, userRequest);

                responseFuture = responce.submit(serverHandler);

                Response finalResponce = responseFuture.get();

                if (!cachedThreadPool.submit(()->{
                    try {
                        clientWriter.writeObject(finalResponce);
                        clientWriter.flush();
                        return true;
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    return false;
                }).get()) break;
            }while (responseFuture.get().getResponseCode() != ResponseCode.SERVER_EXIT);
            return false;
        }catch (IOException e){
            if (userRequest == null){
                e.printStackTrace();
                System.out.println("Непредвиденный разрыв соединения с клиентом!");
                return false;
            }

            else {
                System.out.println("Клиент успешно отключен от сервера!");
                return false;
            }

        }catch (ClassNotFoundException classNotFoundException){
            System.out.println("Произошла ошибка при чтении полученных данных!");
        }catch (InterruptedException e){
            System.out.println("Беда");
        }catch (ExecutionException e){
            e.printStackTrace();
            System.out.println("Беда2");
        }
        return true;
    }

}
