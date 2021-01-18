import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ServerMain {
    static final int SIZE = 5;// игровое поле;
    static final char[][] DOTS_TO_WIN = new char[5][5];// вводимые координаты;
    static final char DOT_EMPTY = '.';
    // игроки;
    static final char DOT_X = 'X';// человек;
    static final char DOT_O = 'O';// первый компьютер;
    static final char DOT_Y = 'Y';// второй компьютер;
    static final char DOT_Z = 'Z';// третий компьютер;
    static char[][] map;

    public ServerMain(Logic logic) throws IOException {

        ServerSocket server = null; //создаём сервер;
        Socket socket = null; //создаём сокет;

        try {
            server = new ServerSocket(8189); //определяем порт;
            System.out.println("Сервер запущен");

            GameBoard.initMap();
            GameBoard.printMap();

            socket = server.accept(); //точка подключения;
            System.out.println("Клиент подключен");

            Scanner in = new Scanner(socket.getInputStream());//входящий поток;
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);//возврат потока вывода;
            Scanner console = new Scanner(System.in);//ввод сообщений в консоль;

            Thread t1 = new Thread(new Runnable() { //поток выхода и его запуск;
                @Override
                public void run() {
                    while (true) {
                        String str = in.nextLine();
                        if (str.equals("/end")) {
                            out.println("/end");
                            break;
                        }

                        System.out.println("Client сходил: " + str);
                    }
                }
            });
            t1.start();

            Thread t2 = new Thread(new Runnable() { //поток сообщений и его запуск;
                @Override
                public void run() {
//                    while (true) {
//                        System.out.println("Введите сообщение");
//                        String str = console.nextLine();
//                        System.out.println("Сообщение отправлено!");
//                        out.println(str);
//                    }
                }
            });
            t2.setDaemon(true);//фоновый процесс, который очищает кэш, актуализирует значения. В данном случае для чтения с консоли;
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
