
import core.ClientHandler;

import interaction.Request;
import interaction.Response;
import interaction.ResponseCode;
import interaction.User;
import org.apache.wml.dom.WMLHeadElementImpl;
import util.AuthHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.WeakHashMap;

public class Client{
    private String host;
    private int port;
    private SocketChannel socketChannel;
    private ObjectInputStream serverReader;
    private ObjectOutputStream serverWriter;
    private ClientHandler clientHandler;
    private AuthHandler authHandler;
    private User user;
    public Client(String host, int port, ClientHandler clientHandler,AuthHandler authHandler){
        this.host = host;
        this.port = port;
        this.clientHandler = clientHandler;
        this.authHandler = authHandler;
    }

    public void run(){
        try {
            connectToServer();
            processAuthentication();
            System.out.println("Вы можете ввести первую комманду. Если не знаете команды введите help.");
            processRequestToServer();
            if (socketChannel != null) socketChannel.close();
            System.out.println("Работа завершена!");
        }catch (IOException e){
            System.out.println("произошла ошибка при попытке завершить соединение с сервером!");
        }

    }
    private void connectToServer(){
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(host,port));
            System.out.println("------------");

            System.out.println("Соединение с сервером успешно установлено.");



            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());

            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());

        }catch (IOException e){
            System.out.println("Произошла ошибка при соединении с сервером! Сервер отключен");
            readAnswer();
        }
    }

    private void processRequestToServer(){
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {

                requestToServer = serverResponse != null ? clientHandler.handle(serverResponse.getResponseCode(),user):
                        clientHandler.handle(null,user);
                if (requestToServer.isEmpty()) continue;

                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                System.out.println(serverResponse.getResponseBody());
                System.out.println("------------");
                System.out.println("Следующая команда:");
            }catch (IOException e){
                System.out.println("Произошел разрыв с сервером");
                if (readAnswer()){
                connectToServer();}
                else {
                    System.out.println("Можете ввести ");
                }


            }catch (ClassNotFoundException e){
                System.out.println("Произошел разрыв с сервером");
            }catch (NullPointerException e){
                System.out.println("Сервер отключен. Комманды обработана не будет!");
            }

        }while (!requestToServer.getCommandName().equals("exit"));

    }

    public boolean readAnswer() {
        Scanner scanner = new Scanner(System.in);
        String s;
        System.out.println("Повторить попытку? (yes/no)");
        while (true) {
            if (!scanner.hasNextLine()) System.exit(1);
            s = scanner.nextLine();
            switch (s) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    System.out.println("Неверный ввод! Введите yes/no");
                    break;
            }
        }
    }

    private void processAuthentication(){
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = authHandler.handle();

                if (requestToServer.isEmpty()) continue;

                serverWriter.writeObject(requestToServer);

                serverResponse = (Response) serverReader.readObject();
                System.out.println(serverResponse.getResponseBody());
            }catch (IOException e){
                System.out.println("Произошел разрыв с сервером");
                if (readAnswer()){
                    connectToServer();}
                else {
                    System.out.println("Можете ввести ");
                }

            }catch (ClassNotFoundException e){
                System.out.println("Произошел разрыв с сервером");
            }

        }   while (serverResponse == null || !serverResponse.getResponseCode().equals(ResponseCode.OK));
        user = requestToServer.getUser();
    }

}
