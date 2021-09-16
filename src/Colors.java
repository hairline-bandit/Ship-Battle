public class Colors {
    private static final String red = "\033[0;31m";
    private static final String green = "\033[0;32m";
    private static final String blue = "\033[0;34m";
    private static final String yellow = "\033[0;33m";
    private static final String reset = "\033[0m";
    private static final String black = "\033[0;30m";
    public static String colored (String text, String color) {
        if (color.equals("red")) {
            return red + text + reset;
        } else if (color.equals("green")) {
            return green + text + reset;
        } else if (color.equals("blue")) {
            return blue + text + reset;
        } else if (color.equals("yellow")) {
            return yellow + text + reset;
        } else if (color.equals("black")) {
            return black + text + reset;
        } else {
            return text;
        }
    }
}
