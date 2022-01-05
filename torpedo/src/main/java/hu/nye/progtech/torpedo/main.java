package hu.nye.progtech.torpedo;


import java.util.Random;
import java.util.Scanner;

class BattleShips {

    public static Scanner input = new Scanner(System.in);
    public static Random rand = new Random();

    public static String[][] ocean = new String[10][10];
    public static String[][] ocean2 = new String[10][10];

    public static int userShips = 0;
    public static int computerShips = 0;

    public static void main(String[] args) {
        into();
        userLoc(ocean);
        compLoc(ocean);
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


            if (row > 9 || col > 9)
            {//nem helyezhetsz hajokat a 10 x 10 racson kivulre
                System.out.println("A megadott koordinata tartomanyon kivül esik. Kerjuk, probalja ujra");
            } else if (ocean[row][col] != null)
            { //NEM helyezhet el ket vagy tobb hajot ugyanarra a helyre
                System.out.println("A megadott koordinata mar hasznalatban van. Kerjuk, probalja ujra");
            }
            else
            {
                ocean[row][col] = "@";
                userShips++;
            }
        }
    }

    public static void compLoc(String[][] ocean) {
        System.out.println("\n\nA szamitogep hajokat telept:");

        while (computerShips < 5)
        {
            int row = rand.nextInt(10);
            int col = rand.nextInt(10); //nem helyezhetsz hajokat a 10 x 10 racson kivulre

            if (ocean[row][col] == null && ocean2[row][col] == null)
            {//nem helyezheti el a hajot  olyan helyre, amelyet mar elfoglalt egy masik hajo (jatekos vagy szamitogep)
                System.out.println("Hajo telepitve. ");
                ocean2[row][col] = "@";//hajok helye az ocean2 on
                computerShips++;
            }
        }

        System.out.println("\n--------------------------------------\n");
    }

    public static void computerTurn() {
        System.out.println("\n\nszamitogep kore");
        int turn = 0;

        while (turn == 0)
        {

            int x = rand.nextInt(10);
            int y = rand.nextInt(10);


            if (ocean2[x][y] == "@")
            {//A szamitogep kitalalta a sajat hajojanak koordinatait (a szamitogep elvesziti a hajot).
                System.out.println("a szamitogep elsullyesztette a saját hajojat !");
                ocean[x][y] = "!";
                ocean2[x][y] = "!";
                computerShips--;
                turn++;
            } else if (ocean[x][y] == "@")
            { //A szamitogep kitalalta a jatekos hajojanak koordinatait (a jatekos elvesziti a hajot).
                System.out.println("a szamítogep elsullyesztette a hajodat!");
                ocean[x][y] = "+";
                ocean2[x][y] = "+";
                userShips--;
                turn++;
            } else if (ocean[x][y] == "!" || ocean[x][y] == "+" || ocean2[x][y] == "X")
            {

            }
            else
            {
                //Szamitogep tevesztett. Nincs hajo a kitalalt koordinatakon.
                System.out.println("szamitogep nem talalt el a hajodat!");
                ocean2[x][y] = "X";
                turn++;
            }
        }

    }
    public static void userTurn() {

        System.out.println("\nA te korod");
        int turn = 0;

        while (turn == 0)
        {

            System.out.print("Adja meg az X koordinatat: ");
            int col = input.nextInt();
            System.out.print("Adja meg az Y koordinatat: ");
            int row = input.nextInt();


            if (row > 9 || col > 9)
            {
                //nem lohetsz a 10x10-es racson kivul
                System.out.println("A megadott koordinata tartomanyon kivül esik. Kerjuk, probalja ujra.\n");
            }
            else if (ocean[row][col] == "!" || ocean[row][col] == "x" || ocean[row][col] == "-")
            {
                System.out.println("A megadott koordinata mar hasznalatban volt. Kerjuk, probalja ujra.\n");
            }
            else if (ocean2[row][col] == "@")
            {
                //A jatekos helyesen talalta ki a szamitogep hajojanak koordinatait (a szamitogep elvesziti a hajojat).
                System.out.println("Gratula! Elsullyesztetted a szamitogep hajojat!");
                ocean[row][col] = "+";
                ocean2[row][col] = "+";
                computerShips--;
                turn++;
            }
            else if (ocean[row][col] == "@")
            {
                //A jatekos megadta a sajat hajoja koordinatait (a jatekos elvesziti a hajot).
                System.out.println("O nem, elsullyesztetted a sajat hajodat! ");
                ocean[row][col] = "-";
                userShips--;
                turn++;
            }
            else
            {
                //Jatekos tevesztett. nincs hajo ezzen a koordinatan
                System.out.println("Bocsi, teves!");
                ocean[row][col] = "X";
                turn++;
            }
        }
    }
    public static void battle() {
        printMap(ocean);//feltoltott terkep kiirasa
        System.out.println("\nA te hajod: " + userShips + " | szamitogep hajo: " + computerShips + "\n-----------------------------------");

        while (userShips > 0 && computerShips > 0)
        {
            userTurn();
            computerTurn();
            printMap(ocean);
            System.out.println("\nA te hajod: " + userShips + " | szamitogep hajo: " + computerShips + "\n-------------------------------");
        }

        if (userShips == 0) {
            System.out.println("\n Jatek vege ");
            System.out.println("\nA te hajod: " + userShips + " | szamitogep hajo: " + computerShips + "\n-------------------------------");
        } else if (computerShips == 0) {
            System.out.println("Gratulalok! Megnyerted a csatat :)");
            System.out.println("\nA te hajod: " + userShips + " | szamitogep hajo: " + computerShips + "\n-------------------------------");
        }
        printMap2(ocean2);// teszt
    }
    public static void into() {
        System.out.println("\n Udvozlom a Csatahajok jatekban ");
        System.out.println("\nmost a terkep ures.\n");
        printMap(ocean);
    }


}


