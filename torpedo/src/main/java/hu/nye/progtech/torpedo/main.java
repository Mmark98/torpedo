package hu.nye.progtech.torpedo;


import java.util.Random;
import java.util.Scanner;

class BattleShips {

    public static Scanner input = new Scanner(System.in);
    public static Random rand = new Random();

    public static String[][] ocean = new String[10][10];//saját hajom terkepe
    public static String[][] ocean2 = new String[10][10];//ocean2 szamitogep terkepe

    public static int userShips = 0;
    public static int computerShips = 0;

    public static void main(String[] args) {
        bevezetes();
        userLoc(ocean); //a jatekos hajoja
        compLoc(ocean); //a szamitogep hajoja
        battle();
    }


    public static void printMap(String[][] ocean) {
        System.out.println("\n  0123456789  ");

        for (int row = 0; row < ocean.length; row++) { // letrehozza a szamokat a racson:
            System.out.print(row + "|");
            for (int col = 0; col < ocean[row].length; col++) {
                if (ocean[row][col] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(ocean[row][col]);
                }
            }
            System.out.println("|" + row);
        }
        System.out.println("  0123456789  ");
    }

    public static void printMap2(String[][] ocean2) {
        //just for test
        System.out.println("OCEAN2");
        System.out.println("  0123456789  ");
        //Create the numbers on the grid:
        for (int row = 0; row < ocean2.length; row++) {
            System.out.print(row + "|");
            for (int col = 0; col < ocean2[row].length; col++) {
                if (ocean2[row][col] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(ocean2[row][col]);
                }
            }
            System.out.println("|" + row);
        }
        System.out.println("  0123456789  ");
    }

    public static void userLoc(String[][] ocean) {
        System.out.println("\nTelepitse a hajoit:");

        while (userShips < 5) {
            System.out.print("Adja meg hajoja X koordinatajat: ");
            int col = input.nextInt();
            System.out.print("Adja meg hajoja Y koordinatajat " + (userShips + 1) + ". hajo: ");
            int row = input.nextInt();


            if (row > 9 || col > 9) {//you can’t place ships outside the 10 by 10 grid
                System.out.println("A megadott koordinata tartomanyon kivül esik. Kerjuk, probalja ujra");
            } else if (ocean[row][col] != null) { //you can NOT place two or more ships on the same location
                System.out.println("A megadott koordinata mar hasznalatban van. Kerjuk, probalja ujra");
            } else {
                ocean[row][col] = "@";
                userShips++;
            }
        }
    }

    public static void compLoc(String[][] ocean) {
        System.out.println("\n\nA szamitogep hajokat telept:");

        while (computerShips < 5) {
            int row = rand.nextInt(10), col = rand.nextInt(10); //you can’t place ships outside the 10 by 10 grid

            if (ocean[row][col] == null && ocean2[row][col] == null) {//you cannot place the ship on a location that is already taken by another ship (player’s or computer’s)
                System.out.println("Hajo telepitve. ");
                ocean2[row][col] = "@";//place ships in Ocean2
                computerShips++;
            }
        }

        System.out.println("\n--------------------------------------\n");
    }

    public static void computerTurn() {
        System.out.println("\n\nszamitogep kore");
        int turn = 0;

        while (turn == 0) {

            int x = rand.nextInt(10);
            ;
            int y = rand.nextInt(10);
            ;

            if (ocean2[x][y] == "@") {//A szamitogep kitalalta a sajat hajojanak koordinatait (a szamitogep elvesziti a hajot).
                System.out.println("a szamitogep elsullyesztette a saját hajojat !");
                ocean[x][y] = "!";
                ocean2[x][y] = "!";
                computerShips--;
                turn++;
            } else if (ocean[x][y] == "@") { //A szamitogep kitalalta a jatekos hajojanak koordinatait (a jatekos elvesziti a hajot).
                System.out.println("a szamítogep elsullyesztette a hajodat!");
                ocean[x][y] = "+";
                ocean2[x][y] = "+";
                userShips--;
                turn++;
            } else if (ocean[x][y] == "!" || ocean[x][y] == "x" || ocean2[x][y] == "-") {

            } else {//Szamitogep tevesztett. Nincs hajo a kitalalt koordinatakon.
                System.out.println("szamitogep nem talalt el a hajodat!");
                ocean2[x][y] = "X";
                turn++;
            }
        }

    }
    public static void userTurn() {

        System.out.println("\nA te korod");
        int turn = 0;

        while (turn == 0) {

            System.out.print("Adja meg az X koordinatat: ");
            int col = input.nextInt();
            System.out.print("Adja meg az Y koordinatat: ");
            int row = input.nextInt();


            if (row > 9 || col > 9) {//nem lohetsz a 10x10-es racson kivul
                System.out.println("A megadott koordinata tartomanyon kivül esik. Kerjuk, probalja ujra.\n");
            } else if (ocean[row][col] == "!" || ocean[row][col] == "x" || ocean[row][col] == "-") {
                System.out.println("A megadott koordinata mar hasznalatban volt. Kerjuk, probalja ujra.\n");
            } else if (ocean2[row][col] == "@") {//A jatekos helyesen talalta ki a szamitogep hajojanak koordinatait (a szamitogep elvesziti a hajojat).
                System.out.println("Gratula! Elsullyesztetted a szamitogep hajojat!");
                ocean[row][col] = "+";
                ocean2[row][col] = "+";
                computerShips--;
                turn++;
            } else if (ocean[row][col] == "@") { //A jatekos megadta a sajat hajoja koordinatait (a jatekos elvesziti a hajot).
                System.out.println("O nem, elsullyesztetted a sajat hajodat! ");
                ocean[row][col] = "-";
                userShips--;
                turn++;
            } else {//Player missed. No ship on the entered coordinates
                System.out.println("Bocsi, teves!");
                ocean[row][col] = "X";
                turn++;
            }
        }
    }
    public static void battle() {
        printMap(ocean); //feltoltott terkep kiirasa
        System.out.println("\nA te hajod: " + userShips + " | szamitogep hajo: " + computerShips + "\n------------------------------------");

        while (userShips > 0 && computerShips > 0) {
            userTurn();
            computerTurn();
            printMap(ocean);
            System.out.println("\nA te hajod: " + userShips + " | szamitogep hajo: " + computerShips + "\n------------------------------------");
        }

        if (userShips == 0) {
            System.out.println("\n*** Jatek vege ***");
            System.out.println("\nA te hajod: " + userShips + " | szamitogep hajo: " + computerShips + "\n------------------------------------");
        } else if (computerShips == 0) {
            System.out.println("gartulalok! Megnyerted a csatat :)");
            System.out.println("\nA te hajod: " + userShips + " | szamitogep hajo: " + computerShips + "\n------------------------------------");
        }
        printMap2(ocean2);// just for test
    }
    public static void bevezetes() {
        System.out.println("\n Udvozloma a Csatahajok jatekban ");
        System.out.println("\nmost a terkep ures.\n");
        printMap(ocean);
    }


}


