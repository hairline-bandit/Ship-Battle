import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
// 0 = North, 1 = East, 2 = South, 3 = West
// pos[0] = y coord pos[1] = x coord
public class Main {
    static final String[] lettersNum = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    static final String[] names = {"Carrier", "Battleship", "Destroyer", "Submarine", "Patrol Boat"};
    static final int[] lengths = {5, 4, 3, 3, 2};
    public static void main (String[] args) {
        cls();
        Scanner s = new Scanner(System.in);
        String name1 = "", name2 = "";
        Board player1 = new Board(), player2 = new Board();
        for (int i = 0; i < 2; i++) {
            HashMap<String, int[]> currentOut = new HashMap<String, int[]>();
            cls();
            System.out.print("Enter Player's Name: ");
            String inp = s.nextLine();
            if (i == 0) {
                name1 = inp;
            } else {
                name2 = inp;
            }
            cls();
            grid();
            System.out.println("This is a the board, now we will place your pieces.");
            System.out.println("Enter the coordinates for the vertex in form \"AA\" (x, y)");
            sleep(5);
            for (String c : names) { 
                cls();
                grid();
                int[] curr = new int[3];
                System.out.print("Enter the vertex for the \""+ c + "\" ship (length " + lengths[idx(names, c)]+ "): ");
                inp = s.nextLine();
                if (getNum("" + inp.charAt(0)) == -1) {
                    System.exit(0);
                } else {
                    curr[0] = getNum("" + inp.charAt(1));
                }
                if (getNum("" + inp.charAt(0)) == -1) {
                    System.exit(0);
                } else {
                    curr[1] = getNum("" + inp.charAt(0));
                }
                System.out.print("Now enter the cardinal direction (north, south, east, west) that you want the ship to face: ");
                inp = s.nextLine();
                if (cardinalToInt(inp) == -1) {
                    System.exit(0);
                } else {
                    curr[2] = cardinalToInt(inp);
                }
                currentOut.put(c, curr);
                cls();
            }
            if (i == 0) {
                player1 = new Board(currentOut);
                player1.printPub();
                player1.printPriv();
                System.out.println("The board on the top is the board that you will use to make guesses");
                System.out.println("The board on the bottom is the board that you will use to see when your ships are hit");
                sleep(10);
            } else {
                player2 = new Board(currentOut);
                player2.printPub();
                player2.printPriv();
                System.out.println("The board on the top is the board that you will use to make guesses");
                System.out.println("The board on the bottom is the board that you will use to see when your ships are hit");
                sleep(10);
            }
        }
        cls();
        String winner = "";
        while (winner.equals("")) {
            System.out.println("It is " + name1 + "'s turn");
            sleep(3);
            cls();
            player1.printPriv();
            player1.printPub();
            System.out.print("Enter the point you want to fire upon: ");
            String inp = s.nextLine();
            int x = getNum("" + inp.charAt(0));
            int y = getNum("" + inp.charAt(1));
            if (x == -1 || y == -1) {
                System.exit(0);
            }
            player1.addGuess(x, y, player2);
            sleep(5);
            if (player2.checkLose()) {
                winner = name1;
                break;
            }
            cls();
            System.out.println("It is " + name2 + "'s turn");
            sleep(3);
            cls();
            player2.printPriv();
            player2.printPub();
            System.out.print("Enter the point you want to fire upon: ");
            inp = s.nextLine();
            x = getNum("" + inp.charAt(0));
            y = getNum("" + inp.charAt(1));
            if (x == -1 || y == -1) {
                System.exit(0);
            }
            player2.addGuess(x, y, player1);
            sleep(5);
            if (player1.checkLose()) {
                winner = name2;
                break;
            }
            cls();
        }
        cls();
        System.out.println(winner + " wins!");
        s.close();
    }

    static int getNum(String letter) {
        for (int i = 0; i < lettersNum.length; i++) {
            if (lettersNum[i].equals(letter.toUpperCase())) {
                return i+1;
            }
        }
        return -1;
    }
    static int idx(String[] a, String b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i].equals(b)) {
                return i;
            }
        }
        return -1;
    }
    static void cls () {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.exit(0);
        }
        System.out.flush();
    }
    static void grid() {
        System.out.print(" ");
        for (int j = 0; j < 10; j++) {
            System.out.print(" " + lettersNum[j]);
        }
        System.out.println();
        for (int j = 0; j < 10; j++) {
            System.out.print(lettersNum[j] + " ");
            for (int k = 0; k < 10; k++) {
                System.out.print(Colors.colored("O", "blue") + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    static void sleep(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            System.exit(0);
        }
    }
    static int cardinalToInt(String cardinal) {
        if (cardinal.trim().toLowerCase().equals("north")) {
            return 0;
        } else if (cardinal.trim().toLowerCase().equals("east")) {
            return 1;
        } else if (cardinal.trim().toLowerCase().equals("south")) {
            return 2;
        } else if (cardinal.trim().toLowerCase().equals("west")) {
            return 3;
        }
        return -1;
    }
}
