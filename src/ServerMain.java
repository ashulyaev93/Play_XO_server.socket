import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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

    public static void main(String[] args) {

        ServerSocket server = null; //создаём сервер;
        Socket socket = null; //создаём сокет;
        Logic gameLogic = new Logic();

        try {
            server = new ServerSocket(8189); //определяем порт;
            System.out.println("Сервер запущен");
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
                        if(str.equals("/end")) {
                            out.println("/end");
                            break;
                        }
                        System.out.println("Client " + str);
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

            // тест программы;
            initMap();
            printMap();

            while (true)
            {
                // тест хода человека;
                humanTurn();
                printMap();

                if (isWinner(DOT_X))
                {
                    System.out.println("Человек победил.");
                    break;
                }
                else if (isMapFull())
                {
                    System.out.println("Ничья.");
                    break;
                }
                //тест хода компьютера 1;
                aiTurn();
                printMap();

                if (isWinner(DOT_O))
                {
                    System.out.println("Компьютер победил.");
                    break;
                }
                else if (isMapFull())
                {
                    System.out.println("Ничья.");
                    break;
                }
                // тест хода 2 компьютера;
                aiTurnTwo();
                printMap();

                if (isWinner(DOT_Y))
                {
                    System.out.println("Компьютер победил.");
                    break;
                }
                else if (isMapFull())
                {
                    System.out.println("Ничья.");
                    break;
                }
                // тест хода 3 компьютера;
                aiTurnThree();
                printMap();

                if (isWinner(DOT_Z))
                {
                    System.out.println("Компьютер победил.");
                    break;
                }
                else if (isMapFull())
                {
                    System.out.println("Ничья.");
                    break;
                }
            }
            System.out.println("Игра закончена.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        static void initMap() //инициализация пустого игрового поля;
        {
            map = new char[SIZE][SIZE];

            for (int i = 0; i < map.length; i++)
            {
                for (int j = 0; j < map[i].length; j++)
                {
                    map[i][j] = DOT_EMPTY;
                }
            }
        }

        static void printMap() //вывод поля в консоль(внешний вид);
        {
            for (int i = 0; i <= SIZE; i++)
            {
                System.out.print(i + " ");
            }

            System.out.println();

            for (int i = 0; i < SIZE; i++)
            {
                System.out.print((i + 1) + " ");

                for (int j = 0; j < SIZE; j++)
                {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

        static void humanTurn() // ход чеовека;
        {
            int x;
            int y;
            Scanner scanner = new Scanner(System.in);

            do
            {
                System.out.println("Введите координаты X Y");
                y = scanner.nextInt() - 1;
                x = scanner.nextInt() - 1;
            }
            while (!isCellValid(x, y));

            map[y][x] = DOT_X;
        }

        public static void aiTurn() // ход первого компьютера;
        {
            int x;
            int y;

            Random random = new Random();

            do
            {
//          НЕ РАБОТАЕТ!! НЕ МОГУ ПОНЯТЬ, КАК ЗАСТАВИТЬ ЕГО ХОДИТЬ В УКАЗАННУЮ КООРДИНАТУ.
                if(DOT_X == map[0][0] || DOT_X == map[2][2] || DOT_X == map[4][4]){ // первый компьютер "O" будет блокировать указанные ходы игрока если эти клетки будут свободны;
                    x = 2;
                    y = 2;
                    map[1][1] = DOT_O;
                }else if(DOT_X == map[0][4] || DOT_X == map[2][2] || DOT_X == map[4][0] ){
                    x = 4;
                    y = 2;
                    map[1][3] = DOT_O;
                }else if(DOT_X == map[2][0] || DOT_X == map[2][2] || DOT_X == map[2][4]) {
                    x = 2;
                    y = 3;
                    map[2][1] = DOT_O;
                }else if(DOT_X == map[0][2] || DOT_X == map[2][2] || DOT_X == map[4][2]) {
                    x = 3;
                    y = 2;
                    map[1][2] = DOT_O;
                }else{
                    x = random.nextInt(SIZE);
                    y = random.nextInt(SIZE);
                }
            }
            while (!isCellValid(x, y));
            System.out.println("Первый компьютер сделал ход по координатам X Y: " + (x + 1) + " " + (y + 1));
            map[y][x] = DOT_O;
        }

        public static void aiTurnTwo() // ход второго компьютера;
        {
            int x;
            int y;

            Random random = new Random();

            do
            {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            }
            while (!isCellValid(x, y));
            System.out.println("Второй компьютер сделал ход по координатам X Y: " + (x + 1) + " " + (y + 1));
            map[y][x] = DOT_Y;
        }

        public static void aiTurnThree() //ход третьего компьютера;
        {
            int x;
            int y;

            Random random = new Random();

            do
            {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            }
            while (!isCellValid(x, y));
            System.out.println("Третий компьютер сделал ход по координатам X Y: " + (x + 1) + " " + (y + 1));
            map[y][x] = DOT_Z;
        }

        public static boolean isCellValid(int x, int y) // проверка возможности устаовки фишки в указанную ячейку;
        {
            if (x < 0 || x >= 5 || y < 0 || y >= 5)
            {
                return false;
            }
            if (map[y][x] == DOT_EMPTY)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        static boolean isWinner(char symbol) // проверка победы;
        {
            boolean isDiagOne, isDiagTwo; // установили логическое значение 1,2
            isDiagOne = true;
            isDiagTwo = true;
            boolean isCols, isRows; // установили логическое значение 3,4

            for (int diagOne = 0; diagOne < DOTS_TO_WIN.length; diagOne++) {

                for(int diagTwo = 0; diagTwo < DOTS_TO_WIN.length; diagTwo++) {
                    isDiagOne &= (map[diagOne][diagOne] == symbol);
                    isDiagTwo &= (map[diagTwo][(DOTS_TO_WIN.length - 1) - diagTwo] == symbol);
                }
            }
            if (isDiagOne|| isDiagTwo) return true;//вернули (true), если во всех клетках нам встретились символы symbol;

            for (int col=0; col < DOTS_TO_WIN.length; col++){
                isCols = true;
                isRows = true;
                for(int row=0; row < DOTS_TO_WIN.length; row++){
                    isCols &= (map[col][row] == symbol);
                    isRows &= (map[row][col] == symbol);
                }
                if (isCols || isRows) return true; //вернули (true), если во всех клетках нам встретились символы symbol;
            }
            return false;
        }

        static boolean isMapFull() // инициализация полностью заполненного игрового поля без возможности хода (ничья);
        {
            for (int i = 0; i < SIZE; i++)
            {
                for (int j = 0; j < SIZE; j++)
                {
                    if (map[i][j] == DOT_EMPTY)
                        return false;
                }
            }
            return true;
        }
    }
}
