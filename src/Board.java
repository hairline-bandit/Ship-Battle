import java.util.HashMap;
import java.util.ArrayList;
// 0 = North, 1 = East, 2 = South, 3 = West
public class Board {
    // Stores board with guess coords on it
    private String[][] boardPublic = new String[10][10];
    // Stores board with pieces on it
    private String[][] boardPrivate = new String[10][10];
    // Stores start and end coords for each piece
    private HashMap<String, int[][]> points = new HashMap<String, int[][]>();
    // Stores coords for each part of each piece
    private HashMap<String, ArrayList<int[]>> allPoints = new HashMap<String, ArrayList<int[]>>();
    // Names of ships
    private static final String[] names = {"Carrier", "Battleship", "Destroyer", "Submarine", "Patrol Boat"};
    // Letters 
    private static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public Board () {

    }
    public Board (HashMap<String, int[]> positions) {
        HashMap<String, Integer> lengths = new HashMap<String, Integer>();
        lengths.put("Carrier", 5);
        lengths.put("Battleship", 4);
        lengths.put("Destroyer", 3);
        lengths.put("Submarine", 3);
        lengths.put("Patrol Boat", 2);
        // Error checking
        for (String i : names) {
            if (positions.get(i)[2] == 0) {
                if (positions.get(i)[0] - 1 - lengths.get(i) < 0) {
                    System.out.println("Piece \"" + i + "\" is out of bounds");
                    System.exit(0);
                }
            } else if (positions.get(i)[2] == 2) {
                if (positions.get(i)[0] - 1 + lengths.get(i) >= 10) {
                    System.out.println("Piece \"" + i + "\" is out of bounds");
                    System.exit(0);
                }
            } else if (positions.get(i)[2] == 1) {
                if (positions.get(i)[1] - 1 + lengths.get(i) >= 10) {
                    System.out.println("Piece \"" + i + "\" is out of bounds");
                    System.exit(0);
                }
            } else if (positions.get(i)[2] == 3) {
                if (positions.get(i)[1] - 1 - lengths.get(i) < 0) {
                    System.out.println("Piece \"" + i + "\" is out of bounds");
                    System.exit(0);
                }
            }
        }
        // Fill boards with water
        for (int i = 0; i < boardPublic.length; i++) {
            for (int j = 0; j < boardPublic[i].length; j++) {
                boardPublic[i][j] = Colors.colored("O", "blue");
                boardPrivate[i][j] = Colors.colored("O", "blue");
            }
        }
        // Record start and end points
        for (String c : names) {
            int[] pos1 = new int[2];
            pos1[0] = positions.get(c)[0] - 1;
            pos1[1] = positions.get(c)[1] - 1;
            int[] pos2 = new int[2];
            if (positions.get(c)[2] == 0) {
                pos2[0] = pos1[0] - lengths.get(c);
                pos2[1] = pos1[1];
            } else if (positions.get(c)[2] == 2) {
                pos2[0] = pos1[0] + lengths.get(c);
                pos2[1] = pos1[1];
            } else if (positions.get(c)[2] == 1) {
                pos2[0] = pos1[0];
                pos2[1] = pos1[1] + lengths.get(c);
            } else if (positions.get(c)[2] == 3) {
                pos2[0] = pos1[0];
                pos2[1] = pos1[1] - lengths.get(c);
            }
            int[][] temp = {pos1, pos2};
            ArrayList<int[]> curr = new ArrayList<int[]>();
            int diff = 0;
            if (positions.get(c)[2] == 0 || positions.get(c)[2] == 2) {
                diff = pos2[0] - pos1[0];
                for (int i = 0; i < diff; i++) {
                    int[] t = new int[]{pos1[0] + i, pos1[1]};
                    curr.add(t);
                }
            } else if (positions.get(c)[2] == 1 || positions.get(c)[2] == 3) {
                diff = pos2[1] - pos1[1];
                for (int i = 0; i < diff; i++) {
                    int[] t = new int[]{pos1[0], pos1[1] + i};
                    curr.add(t);
                }
            }
            points.put(c, temp);
            allPoints.put(c, curr);
        }
        
        // Put Pieces 
        for (String c : names) {
            if (points.get(c)[0][0] < points.get(c)[1][0]) {
                for (int i = points.get(c)[0][0]; i < points.get(c)[1][0]; i++) {
                    for (int j = points.get(c)[0][1]; j <= points.get(c)[1][1]; j++) {
                        if (boardPrivate[i][j].equals("O")) {
                            System.out.println("Piece \"" + c + "\" overlaps another piece");
                            System.exit(0);
                        } else {
                            boardPrivate[i][j] = "O";
                        }
                    }
                }
            } else if (points.get(c)[0][0] > points.get(c)[1][0]) {
                for (int i = points.get(c)[1][0] + 1; i <= points.get(c)[0][0]; i++) {
                    for (int j = points.get(c)[0][1]; j <= points.get(c)[1][1]; j++) {
                        if (boardPrivate[i][j].equals("O")) {
                            System.out.println("Piece \"" + c + "\" overlaps another piece");
                            System.exit(0);
                        } else {
                            boardPrivate[i][j] = "O";
                        }
                    }
                }
            } else if (points.get(c)[0][0] == points.get(c)[1][0]) {
                for (int i = points.get(c)[0][0]; i <= points.get(c)[1][0]; i++) {
                    if (points.get(c)[0][1] < points.get(c)[1][1]) {
                        for (int j = points.get(c)[0][1]; j < points.get(c)[1][1]; j++) {
                            if (boardPrivate[i][j].equals("O")) {
                                System.out.println("Piece \"" + c + "\" overlaps another piece");
                                System.exit(0);
                            } else {
                                boardPrivate[i][j] = "O";
                            }
                        }
                    } else if (points.get(c)[0][1] > points.get(c)[1][1]) {
                        for (int j = points.get(c)[1][1] + 1; j <= points.get(c)[0][1]; j++) {
                            if (boardPrivate[i][j].equals("O")) {
                                System.out.println("Piece \"" + c + "\" overlaps another piece");
                                System.exit(0);
                            } else {
                                boardPrivate[i][j] = "O";
                            }
                        }
                    }
                }
            }
        }
    }
    // Print private board
    public void printPriv() {
        System.out.print(" ");
        for (int i = 0; i < 10; i++) {
            System.out.print(" " + letters[i]);
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(letters[i] + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(boardPrivate[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    // Print public board
    public void printPub() {
        System.out.print(" ");
        for (int i = 0; i < 10; i++) {
            System.out.print(" " + letters[i]);
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(letters[i] + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(boardPublic[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    // Add -> Shooting a point and changing color for that point based on what thing is ykyk (private if hit change to yellow if not hit; pass) (public if hit change to green if not hit; red)
    // Add -> If hit remove the point from allpoints and if empty print "You sunk {ship}" and change color of those points on pub and priv to black
    public String addGuessEnemy (int x, int y) {
        if (boardPrivate[y-1][x-1].equals("O")) {
            boardPrivate[y-1][x-1] = Colors.colored("O", "yellow");
            return "Hit";
        }
        return "Miss";
    }
    public void addGuess (int x, int y, Board enemy) {
        String out = enemy.addGuessEnemy(x, y);
        if (out.equals("Hit")) {
            System.out.println("Hit");
            boardPublic[y-1][x-1] = Colors.colored("O", "green");
        } else {
            System.out.println("Miss");
            boardPublic[y-1][x-1] = Colors.colored("O", "red");
        }
    }
    
    public boolean checkLose() {
        for (String[] i : boardPrivate) {
            for (String j : i) {
                if (j.equals("O")) {
                    return false;
                }
            }
        }
        return true;
    }
}
