package cz.osu.pic.cipher.application;

import java.util.Scanner;

public class Utils {

    //TODO null safety?
    public static String getConsoleInput(Scanner scanner) {
        String input;
        input = scanner.nextLine();
        return input;
    }

}
