package com.tuany;

/**
 * Designed to simulate a dramatic printer, which prints the passed string letter by letter in the command line.
 * @author Tuany Van
 * @version 1.1, 10/16/21
 */

public class DramaPrinter {

    // Declare instance variable delay, which determines how slow the text is to be printed.
    private int delay;

    // Single parameter constructor.

    /**
     * 1-param constructor that takes an initial delay specification.
     * @param delay An integer value representing the milliseconds between each character.
     */
    public DramaPrinter(int delay) { this.delay = delay; }

    /**
     * Setter to change the delay, used especially when changing between dialogue and narrative prose.
     * @param delay An integer value representing the milliseconds between each character.
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * Private delay function made for brevity.
     * @param multiplier A double-type factor which the current delay should be applied.
     */
    private void delayText(double multiplier) {
        // Try to set a delay using Thread.sleep()
        try {
            Thread.sleep((long) (delay * multiplier));
        } catch (InterruptedException error) {
            // Should the current thread by interrupted by another thread, handle an InterruptedException with the stack trace to the error.
            error.printStackTrace();
        }
    }

    /**
     * Uses the delayText() function to print the String argument letter by letter.
     * @param txt The string to print out letter by letter.
     */
    public void dramaPrint(String txt) {

        String[] characters = txt.split("");
        String letter;

        for (int i = 0; i < txt.length(); i++) {
            letter = characters[i];
            /*
                If the print requires exclamation marks to be appended, do so with an additional delay.
                This will be denoted with an { symbol, which will NEVER be used ordinarily during dialogue or narration.
                When the player battleship makes a hit, the game will report a hit with the exclamation marks
                for dramatic emphasis.
            */
            if (letter.equals("{")) {
                for (int j = 0; j < 3; j++) {
                    System.out.print("!");
                    delayText(250.0/delay); // Delay will always be 250ms.
                }
                continue;
            }
            // Else, if a pause needs to be made for effect, a } will indicate so. It will be used only for person-to-person dialogue.
            else if (letter.equals("}")) {
                delayText(1000.0 / delay); // Delay will always end up being one second.
                continue;
            }

            // Otherwise, print the letter with the normal delay.
            System.out.print(letter);
            delayText(1.00);

        }
        System.out.println(); // Let the program move onto the next line on the next print statement.
    }
}
