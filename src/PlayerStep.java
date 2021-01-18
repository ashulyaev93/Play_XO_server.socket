import java.util.Random;
import java.util.Scanner;

public class PlayerStep extends Logic{
    static void humanTurn() // ход человека;
    {
        int x;
        int y;
        Scanner scanner = new Scanner(System.in);

        do
        {
            System.out.println("Введите координаты X Y");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
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
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
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
}
