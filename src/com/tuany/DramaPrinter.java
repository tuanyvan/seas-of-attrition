package com.tuany;

/**
 * Designed to simulate a dramatic printer, which prints the passed string letter by letter in the command line.
 * @author Tuany Van
 * @version 1.1
 * @created 10/16/21
 * @modified 01/14/22
 */

public class DramaPrinter {

    // Declare instance variable delay, which determines how slow the text is to be printed.
    private int delay;

    // Single parameter constructor.
    public DramaPrinter(int delay) { this.delay = delay; }

    // Setters
    public void setDelay(int delay) {
        this.delay = delay;
    }

    // Private delay function for brevity.
    private void delayText(double multiplier) {
        // Try to set a delay using Thread.sleep()
        try {
            Thread.sleep((long) (delay * multiplier));
        } catch (InterruptedException error) {
            // Should the current thread by interrupted by another thread, handle an InterruptedException with the stack trace to the error.
            error.printStackTrace();
        }
    }

    // Declare function dramaPrint, prints a String letter by letter.
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
