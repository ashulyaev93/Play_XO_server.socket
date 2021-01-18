import java.util.Random;
import java.util.Scanner;

public class Logic {

    static final int SIZE = 4;// игровое поле;
    static final char[][] DOTS_TO_WIN = new char[4][4];// вводимые координаты;
    static final char DOT_EMPTY = '.';
    // игроки;
    static final char DOT_X = 'X';// человек;
    static final char DOT_O = 'O';// первый компьютер;
    static final char DOT_Y = 'Y';// второй компьютер;
    static char[][] map;
    static char symbol;

    public static void step()
    {
        // запуск игрового поля программы;
//        GameBoard.initMap();
//        GameBoard.printMap();

        while (true)
        {
            // тест хода человека;
            PlayerStep.humanTurn();
            GameBoard.printMap();

            if (isWinner(DOT_X))
            {
                System.out.println("Человек победил.");
                break;
            }
            else if (GameBoard.isMapFull())
            {
                System.out.println("Ничья.");
                break;
            }
            //тест хода компьютера 1;
            PlayerStep.aiTurn();
            GameBoard.printMap();

            if (isWinner(DOT_O))
            {
                System.out.println("Компьютер победил.");
                break;
            }
            else if (GameBoard.isMapFull())
            {
                System.out.println("Ничья.");
                break;
            }
            // тест хода 2 компьютера;
            PlayerStep.aiTurnTwo();
            GameBoard.printMap();

            if (isWinner(DOT_Y))
            {
                System.out.println("Компьютер победил.");
                break;
            }
            else if (GameBoard.isMapFull())
            {
                System.out.println("Ничья.");
                break;
            }
        }
        System.out.println("Игра закончена.");
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

}