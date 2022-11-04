package Task;


import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static char[][] field;
    public static boolean bool;
    public static int[] bestTurn = {0,0,0};
    public static Random rand = new Random();
    public static Scanner scan = new Scanner(System.in);
    public static final int SIZE_WIDTH = 5;
    public static final int SIZE_LENGTH = 5;
    public static final int WIN_DOTS = 4;
    public static final char DOT_EMPTY = '.';
    public static final char DOT_CROSS = 'X';
    public static final char DOT_CIRCLE = 'O';

    public static void main(String args[]) {
        start();
        print_field();
        while (true) {
            int[] coord = new int[2];
            coord = You_Turn();
            print_field();
            if (checkWin(DOT_CROSS)) {
                System.out.println(ANSI_GREEN + "Вы победили" + ANSI_RESET);
                break;
            }
            if (fieldCheck()) {
                System.out.println(ANSI_YELLOW + "Ничья" + ANSI_RESET);
                break;
            }
            AI_Turn(coord[0], coord[1]);
            print_field();
            if (checkWin(DOT_CIRCLE)) {
                System.out.println(ANSI_RED + "Вы проиграли" + ANSI_RESET);
                break;
            }
            if (fieldCheck()) {
                System.out.println(ANSI_YELLOW + "Ничья" + ANSI_RESET);
                break;
            }
        }
    }

    public static void start() {
        field = new char[SIZE_LENGTH][SIZE_WIDTH];
        for (int i = 0; i < SIZE_LENGTH; i++)
            for (int j = 0; j < SIZE_WIDTH; j++)
                field[i][j] = DOT_EMPTY;
    }

    public static void print_field() {
        System.out.print("  ");
        for (int i = 1; i <= SIZE_LENGTH; i++) {
            System.out.print(ANSI_YELLOW + i + " ");
        }
        System.out.println(ANSI_RESET);
        for (int i = 0; i < SIZE_LENGTH; i++) {
            System.out.print(ANSI_YELLOW + (i + 1) + " " + ANSI_RESET);
            for (int j = 0; j < SIZE_WIDTH; j++)
                System.out.print(field[i][j] + " ");
            System.out.println();
        }
    }

    public static void AI_Turn(int hx, int hy) {
        bestTurn[2] = 0;
        blockBot();
        int x = bestTurn[1];
        int y = bestTurn[0];
        System.out.println(ANSI_RED + "Противник сходил на: " + (x + 1) + " " + (y + 1) + ANSI_RESET);
        if (checkAvailable(x, y)) field[y][x] = DOT_CIRCLE;
    }

    public static int[] You_Turn() {
        System.out.print("Введите координаты хода (число в ряду, номер ряда):");
        int x = scan.nextInt() - 1;
        int y = scan.nextInt() - 1;
        while (!checkAvailable(x, y)) {
            System.out.print("Недоступная клетка, введите другие координаты хода:");
            x = scan.nextInt() - 1;
            y = scan.nextInt() - 1;
        }
        System.out.println(ANSI_BLUE + "Вы сходили на: " + (x + 1) + " " + (y + 1) + ANSI_RESET);
        if (checkAvailable(x, y)) field[y][x] = DOT_CROSS;
        int[] coordinates = new int[2];
        coordinates[0] = x;
        coordinates[1] = y;
        return coordinates;
    }

    public static boolean checkAvailable(int x, int y) {
        if (x < 0 || x > SIZE_LENGTH - 1 || y < 0 || y > SIZE_WIDTH - 1) return false;
        return field[y][x] == DOT_EMPTY;
    }

    public static boolean checkWin(char dot) {
        for (int i = 0; i < SIZE_LENGTH; i++) {
            for (int j = 0; j < SIZE_WIDTH; j++) {
                recCheck(i, j, 0, dot, 0);
                if (bool) return true;
//               System.out.println(recCheck(i,j,0, dot));
            }
        }
        return false;
    }

    public static void recCheck(int x, int y, int i, char dot, int vector) {
        if (field[x][y] == dot && i < WIN_DOTS - 1) {
//            System.out.println(x + " " +  y + " " + field[x][y] + " " + i);
            // vector 1 = - , vector 2 = \ , vector 3 = | , vector 4 = /
            switch (vector) {
                case 0: {
                    if (x + 1 < SIZE_LENGTH) recCheck(x + 1, y, i + 1, dot, 1);
                    if ((x + 1) < SIZE_LENGTH && (y + 1) < SIZE_WIDTH) recCheck(x + 1, y + 1, i + 1, dot, 2);
                    if (y + 1 < SIZE_WIDTH) recCheck(x, y + 1, i + 1, dot, 3);
                    if ((x + 1) >= SIZE_LENGTH && (y - 1) >= 0) recCheck(x - 1, y - 1, i + 1, dot, 4);
                    if ((x - 1) >= 0 && (y + 1) < SIZE_WIDTH) recCheck(x - 1, y + 1, i + 1, dot, 5);
                    if ((x - 1) >= 0 && (y - 1) >= 0) recCheck(x - 1, y - 1, i + 1, dot, 6);
                    break;
                }
                case 1: {
                    if (x + 1 < SIZE_LENGTH) recCheck(x + 1, y, i + 1, dot, 1);
                    break;
                }
                case 2: {
                    if ((x + 1) < SIZE_LENGTH && (y + 1) < SIZE_WIDTH) recCheck(x + 1, y + 1, i + 1, dot, 2);
                    break;
                }
                case 3: {
                    if (y + 1 < SIZE_WIDTH) recCheck(x, y + 1, i + 1, dot, 3);
                    break;
                }
                case 4: {
                    if ((x + 1) < SIZE_LENGTH && (y - 1) >= 0) recCheck(x + 1, y - 1, i + 1, dot, 4);
                    break;
                }
                case 5: {
                    if ((x - 1) >= 0 && (y + 1) < SIZE_WIDTH) recCheck(x - 1, y + 1, i + 1, dot, 5);
                    break;
                }
                case 6: {
                    if ((x - 1) >= 0 && (y - 1) >= 0) recCheck(x - 1, y - 1, i + 1, dot, 6);
                    break;
                }
                default: {
                    System.out.println("How??");
                    break;
                }
            }
        } else {
            if (field[x][y] == dot && i == WIN_DOTS - 1) bool = true;
        }
    }

    public static boolean fieldCheck() {
        for (int i = 0; i < SIZE_LENGTH; i++)
            for (int j = 0; j < SIZE_WIDTH; j++)
                if (field[i][j] == DOT_EMPTY) return false;
        return true;
    }

    public static void blockBot() {
        for (int i = 0; i < SIZE_LENGTH; i++) {
            for (int j = 0; j < SIZE_WIDTH; j++) {
                recBlock(i,j,0,0);
            }
        }
    }
    public static void recBlock(int x, int y, int i, int vector){
        if (field[x][y] == DOT_CROSS) {
//            System.out.println(x + " " +  y + " " + field[x][y] + " " + i);
            // vector 1 = - , vector 2 = \ , vector 3 = | , vector 4 = /
            switch (vector) {
                case 0: {
                    if (x + 1 < SIZE_LENGTH) recBlock(x + 1, y, i + 1, 1);
                    if ((x + 1) < SIZE_LENGTH && (y + 1) < SIZE_WIDTH) recBlock(x + 1, y + 1, i + 1, 2);
                    if (y + 1 < SIZE_WIDTH) recBlock(x, y + 1, i + 1, 3);
                    if ((x + 1) >= SIZE_LENGTH && (y - 1) >= 0) recBlock(x - 1, y - 1, i + 1, 4);
                    if ((x - 1) >= 0 && (y + 1) < SIZE_WIDTH) recBlock(x - 1, y + 1, i + 1, 5);
                    if ((x - 1) >= 0 && (y - 1) >= 0) recBlock(x - 1, y - 1, i + 1, 6);
                    if (x - 1 >= 0) recBlock(x - 1, y, i + 1, 7);
                    if (y - 1 >= 0) recBlock(x, y - 1, i + 1, 8);
                    break;
                }
                case 1: {
                    if (x + 1 < SIZE_LENGTH) recBlock(x + 1, y, i + 1, 1);
                    break;
                }
                case 2: {
                    if ((x + 1) < SIZE_LENGTH && (y + 1) < SIZE_WIDTH) recBlock(x + 1, y + 1, i + 1, 2);
                    break;
                }
                case 3: {
                    if (y + 1 < SIZE_WIDTH) recBlock(x, y + 1, i + 1, 3);
                    break;
                }
                case 4: {
                    if ((x + 1) < SIZE_LENGTH && (y - 1) >= 0) recBlock(x + 1, y - 1, i + 1, 4);
                    break;
                }
                case 5: {
                    if ((x - 1) >= 0 && (y + 1) < SIZE_WIDTH) recBlock(x - 1, y + 1, i + 1, 5);
                    break;
                }
                case 6: {
                    if ((x - 1) >= 0 && (y - 1) >= 0) recBlock(x - 1, y - 1, i + 1, 6);
                    break;
                }
                case 7: {
                    if (x - 1 >= 0) recBlock(x - 1, y, i + 1, 7);
                    break;
                }
                case 8: {
                    if (y - 1 >= 0) recBlock(x, y - 1, i + 1, 8);
                    break;
                }
                default: {
                    System.out.println("How??");
                    break;
                }
            }
        } else{
            if(field[x][y] == DOT_EMPTY && i >= bestTurn[2]){
                    bestTurn[0] = x;
                    bestTurn[1] = y;
                    bestTurn[2] = i+1;
                }
            }
        }
}