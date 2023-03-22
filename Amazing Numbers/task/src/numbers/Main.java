package numbers;

import java.util.Scanner;

public class Main {

    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Amazing Numbers!");
        System.out.println("""
                \nSupported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                * the first parameter represents a starting number;
                * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and a property to search for;
                - two natural numbers and two properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.
                """);
        launch();
    }

    static void launch() {
        String input;
        while (!(input = request()).equals("0")) {
            String[] values = input.split(" ");
            if (input.matches("^[1-9]\\d*$")) System.out.printf(new NumberProperties(input).toString());
            else if (values.length >= 2) NumberProperties.elementsProcess(values);
            else System.out.println("The first parameter should be a natural number or zero.");
        }
    }

    static String request() {
        System.out.print("Enter a request: ");
        return scanner.nextLine();
    }
}
