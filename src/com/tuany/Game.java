package com.tuany;

import java.util.InputMismatchException;
import java.util.Scanner;

/** Application Purpose: Hosts the Seas of Attrition gameplay (map building, AI actions, player actions, game over sequences).
 * Author: Tuany Van
 * Date: October 17th, 2021
 * Time: 8:23 PM
 */

public class Game {

    // Declare instance variables for the user.
    private final int MINIMUM_SHOTS; // The minimum and maximum shot values should never change.
    private final int MAXIMUM_SHOTS;
    private String captainName;
    private final boolean WILL_PLAY_EXPOSITION;

    // Declare instance variables that change over the course of the game.
    private int playerShipsRemaining = 18;
    private int computerShipsRemaining = 18;
    private Integer roundCount = 1; // Keep a record of the rounds for the AI and game statistics.

    // Declare instance variables that will be printed at the end of the game for statistics.
    private Integer totalShotsMade = 0;
    private int shotsLostToAttrition = 0;

    // Constructor requiring instance variables for the user.
    public Game(int MINIMUM_SHOTS, int MAXIMUM_SHOTS, String captainName, boolean WILL_PLAY_EXPOSITION) {
        this.MINIMUM_SHOTS = MINIMUM_SHOTS;
        this.MAXIMUM_SHOTS = MAXIMUM_SHOTS;
        this.captainName = captainName;
        this.WILL_PLAY_EXPOSITION = WILL_PLAY_EXPOSITION;
        cutscene.setCaptainName(captainName);
    }

    // Instantiate the necessary classes for gameplay once all instance variables are initialized correctly.
    DramaPrinter print = new DramaPrinter(30);
    Scanner input = new Scanner(System.in);
    Cutscenes cutscene = new Cutscenes(captainName);

    // Declare private variables depicting the map for both the player and the AI.
    private int[][] playerMap;
    private int[][] playerShotTrackingMap;
    private int[][] computerMap;
    private final String GRID_ALPHABET = "ABCDEFGHIJ";

    // Declare mapCreator, which creates the playerMap, computerMap and playerShotTrackingMap as 2D arrays, and then autofill the computerMap.
    private void mapCreator() {

        // Initialize the maps for the player, AI, and player shot tracking map.
        int xAxisLength = 10;
        int yAxisLength = 10;
        playerMap = new int[xAxisLength][yAxisLength];
        computerMap = new int[xAxisLength][yAxisLength];
        playerShotTrackingMap = new int[xAxisLength][yAxisLength];

        // Autofill map for AI.
        /*
            There will be 4 different ship types for each player.
            6-LENGTH is the GALLEON.
            5-LENGTH is the FRIGATE.
            4-LENGTH is the SCHOONER.
            3-LENGTH is the CUTTER.
         */
        int shipLength = 6;
        int startingCoordinateX;
        int startingCoordinateY;
        int direction;
        boolean willIntersect;

        while (shipLength >= 3) {


            // Choose random starting coordinate.
            startingCoordinateX = getRandomInt(0, computerMap.length - 1); // Length less 1 to accommodate indexes.
            startingCoordinateY = getRandomInt(0, computerMap[0].length - 1); // First element is the same length as every other element.

            // Determine a random direction to place the enemy ship.
            /*
                0 is up.
                1 is right.
                2 is down.
                3 is left.
             */
            direction = getRandomInt(0, 3);

            // Check if direction is a valid placement twice.
            for (int i = 0; i < 2; i++) {
                // If direction is invalid, the direction will change accordingly. If not, then the direction does not change at all.
                switch (direction) {
                    case 0: // Up
                        if (startingCoordinateX - shipLength <= 0) { // If going up stretches beyond the map...
                            direction = 2; // Then go down instead.
                        }
                        break;
                    case 1: // Right
                        if (startingCoordinateY + shipLength >= computerMap[0].length) { // If going right stretches beyond the map...
                            direction = 3; // Then go left instead
                        }
                        break;
                    case 2: // Down
                        if (startingCoordinateX + shipLength >= computerMap.length) { // If going down stretches beyond the map...
                            direction = 0; // Then go up instead
                        }
                        break;
                    case 3: // Left
                        if (startingCoordinateY - shipLength <= 0) { // If going left stretches beyond the map...
                            direction = 1; // Then go right instead
                        }
                        break;
                }
            }
            // Check if the placement would result in an intersection, make willIntersect true if so.
            willIntersect = false;
            switch (direction) {
                case 0: // Up
                    for (int j = 0; j < shipLength; j++) {
                        if (computerMap[startingCoordinateX - j][startingCoordinateY] == 1) {
                            willIntersect = true;
                            break;
                        }
                    }
                    break;
                case 1: // Right
                    for (int j = 0; j < shipLength; j++) {
                        if (computerMap[startingCoordinateX][startingCoordinateY + j] == 1) {
                            willIntersect = true;
                            break;
                        }
                    }
                    break;
                case 2: // Down
                    for (int j = 0; j < shipLength; j++) {
                        if (computerMap[startingCoordinateX + j][startingCoordinateY] == 1) {
                            willIntersect = true;
                            break;
                        }
                    }
                    break;
                case 3: // Left
                    for (int j = 0; j < shipLength; j++) {
                        if (computerMap[startingCoordinateX][startingCoordinateY - j] == 1) {
                            willIntersect = true;
                            break;
                        }
                    }
                    break;
            }

            // If the placed piece intersects with another piece, restart the loop without making changes to the shipLength.
            if (willIntersect) {
                continue;
            }

            // Now, if every checked condition is ideal, place the ship accordingly.
            switch (direction) {
                case 0: // Up
                    for (int i = 0; i < shipLength; i++) {
                        computerMap[startingCoordinateX - i][startingCoordinateY] = 1; // 1 indicates a ship is present on the map.
                    }
                    break;
                case 1: // Right
                    for (int i = 0; i < shipLength; i++) {
                        computerMap[startingCoordinateX][startingCoordinateY + i] = 1;
                    }
                    break;
                case 2: // Down
                    for (int i = 0; i < shipLength; i++) {
                        computerMap[startingCoordinateX + i][startingCoordinateY] = 1;
                    }
                    break;
                case 3: // Left
                    for (int i = 0; i < shipLength; i++) {
                        computerMap[startingCoordinateX][startingCoordinateY - i] = 1;
                    }
                    break;
            }

            shipLength--; // Decrement the ship length.

        }

    }

    // Declare mapPrinter, which prints a visual representation of the player and shot tracking map.
    private void mapPrinter(boolean printPlayerMap) {

        System.out.println("  12345678910");
        // If game wants to print the player map...
        if (printPlayerMap) {
            for (int i = 0; i < playerMap.length; i++) {
                System.out.print(GRID_ALPHABET.charAt(i) + " ");
                for (int j = 0; j < playerMap[i].length; j++) {
                    if (playerMap[i][j] == 0) {
                        System.out.print('.');
                    } else if (playerMap[i][j] == 1) {
                        System.out.print('S');
                    } else if (playerMap[i][j] == 2) {
                        System.out.print('X');
                    }
                }
                System.out.println();
            }
        }
        // Otherwise, print the shot tracking map.
        else {
            for (int i = 0; i < playerShotTrackingMap.length; i++) {
                System.out.print(GRID_ALPHABET.charAt(i) + " ");
                for (int j =0; j < playerShotTrackingMap[i].length; j++) {
                    if (playerShotTrackingMap[i][j] == 0) { // No shot taken on this spot...
                        System.out.print('.');
                    } else if (playerShotTrackingMap[i][j] == 1) { // Effective hit on this spot.
                        System.out.print('X');
                    }
                    else if (playerShotTrackingMap[i][j] == 2) { // Miss on this spot.
                        System.out.print('O');
                    }
                }
                System.out.println();
            }
        }
    }

    // Declare private coordinateInput function, which tries to get a coordinate input from the user.
    private int[] coordinateInput(boolean willGetEndingCoordinate) {

        // Initialize the return values to -2, a value which will not make any if statement true.
        int beginningCoordinateLetter = -2; // To be converted to a number for indexing.
        int beginningCoordinateNumber = -2;
        int endingCoordinateLetter = -2; // To be converted to a number for indexing.
        int endingCoordinateNumber = -2;

        int maxCoordXIndex = playerMap.length - 1;
        int maxCoordYIndex = playerMap[0].length - 1;

        String temporaryLetter = "";
        boolean isInputValid = false;
        boolean isLetterValid;

        do {
            try {
                /*
                    If willGetEndingCoordinate is true, then the coordinateInput function gets coordinates for ship placement.
                    Otherwise, it's coordinates for firing a shot.
                */

                // Get beginning coordinate letter from user.
                isLetterValid = false;
                while(!isLetterValid) {

                    System.out.print("ENTER BEGINNING COORDINATE LETTER: ");
                    temporaryLetter = input.nextLine().toUpperCase();

                    // Do not allow an empty selection.
                    if (temporaryLetter.equals("") || temporaryLetter.length() > 1) {
                        System.out.println("You haven't specified a coordinate letter!");
                        continue;
                    }
                    isLetterValid = true;

                }
                beginningCoordinateLetter = GRID_ALPHABET.indexOf(temporaryLetter);

                // Get beginning coordinate number from user.
                System.out.print("ENTER BEGINNING COORDINATE NUMBER: ");
                beginningCoordinateNumber = input.nextInt() - 1;
                input.nextLine(); // Compensate for nextInt() inability to process \n

                // If the end coordinate is needed for ship placement...
                if (willGetEndingCoordinate) {
                    // Get ending coordinate letter from user.
                    isLetterValid = false;
                    while(!isLetterValid) {

                        System.out.print("ENTER ENDING COORDINATE LETTER: ");
                        temporaryLetter = input.nextLine().toUpperCase();

                        // Do not allow an empty selection.
                        if (temporaryLetter.equals("") || temporaryLetter.length() > 1) {
                            System.out.println("You haven't specified a coordinate letter!");
                            continue;
                        }
                        isLetterValid = true;

                    }
                    endingCoordinateLetter = GRID_ALPHABET.indexOf(temporaryLetter);

                    // Get ending coordinate number from user.
                    System.out.print("ENTER ENDING COORDINATE NUMBER: ");
                    endingCoordinateNumber = input.nextInt() - 1;
                    input.nextLine(); // Compensate for nextInt() inability to process \n
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid coordinate input! Format: ROW, COLUMN; A, 1 for top left corner of the map!");
                input.nextLine(); // Input mismatch happens almost exclusively after a bad input for nextInt();
                continue;
            }

            // Validate the input before moving any further.
            // If the coordinate exceeds the map boundaries...
            if (beginningCoordinateNumber > maxCoordYIndex || endingCoordinateNumber > maxCoordYIndex || beginningCoordinateLetter > maxCoordXIndex || endingCoordinateLetter > maxCoordXIndex) {
                System.out.println("Invalid coordinates! They exceed the map boundaries!");
                continue;
            }
            // Otherwise, if the user inputs any invalid value for the coordinates...
            else if (beginningCoordinateLetter == -1 || beginningCoordinateNumber == -1 || endingCoordinateLetter == -1 || endingCoordinateNumber == -1) {
                System.out.println("Invalid coordinate input! Format: ROW, COLUMN; A, 1 for top left corner of the map!");
                continue;
            }
            // Or, if the user inputs a diagonal during ship placement, where both the letter and number in the coordinates differ...
            else if (willGetEndingCoordinate) {
                if ((beginningCoordinateLetter != endingCoordinateLetter) && (beginningCoordinateNumber != endingCoordinateNumber)) {
                    System.out.println("Invalid coordinate input! Diagonal ships are not allowed!");
                    continue;
                }
            }

            isInputValid = true; // Step out of the loop once the input is considered valid.

        } while (!isInputValid);

        if (willGetEndingCoordinate) { // If the function was called to place a ship...
            return new int[]{beginningCoordinateLetter, beginningCoordinateNumber, endingCoordinateLetter, endingCoordinateNumber};
        }
        else { // Otherwise, if the function was called to fire a shot...
            return new int[]{beginningCoordinateLetter, beginningCoordinateNumber};
        }
    }

    // Declare playerMapCreator function, which has input handling for the player and places their ships on the map.
    private void playerMapCreator() {

        String shipType = ""; // String updated in switch case to show user what ship is expected to be placed.
        int shipLength = 6; // Integer that decrements with every ship placement, dictates what length ship is expected.

        mapPrinter(true);
        while(shipLength >= 3) {
            // Store the relevant ship type in shipType.
            /*
                There will be 4 different ship types for each player.
                6-LENGTH is the GALLEON.
                5-LENGTH is the FRIGATE.
                4-LENGTH is the SCHOONER.
                3-LENGTH is the CUTTER.
            */

            switch(shipLength) {
                case 6:
                    shipType = "GALLEON convoy? It will patrol 6 spaces on the map!\"";
                    break;
                case 5:
                    shipType = "FRIGATE convoy? It will patrol 5 spaces on the map!\"";
                    break;
                case 4:
                    shipType = "SCHOONER convoy? It will patrol 4 spaces on the map!\"";
                    break;
                case 3:
                    shipType = "CUTTER convoy? It will patrol 3 spaces on the map!\"";
                    break;
            }

            print.dramaPrint("\"Captain " + captainName + "! Where shall we place our " + shipType);

            // Create input handling to ensure the coordinates entered are valid.
            boolean wasPlacementValid = false;
            int beginningCoordinateLetter; // To be converted to a number for indexing.
            int beginningCoordinateNumber;
            int endingCoordinateLetter; // To be converted to a number for indexing.
            int endingCoordinateNumber;

            // Expected input in order of input requests: LETTER, NUMBER, LETTER, NUMBER.
            do {
                // Try to get the coordinate input from the user.
                int[] userInputArray = coordinateInput(true); // Get the start and end coordinates.
                beginningCoordinateLetter = userInputArray[0];
                beginningCoordinateNumber = userInputArray[1];
                endingCoordinateLetter = userInputArray[2];
                endingCoordinateNumber = userInputArray[3];

                // If the length of the coordinates are not in line with the shipLength...
                if (beginningCoordinateLetter == endingCoordinateLetter) { // If row selection is the same...
                    if (Math.abs(endingCoordinateNumber - beginningCoordinateNumber) + 1 != shipLength) { // and length is not shipLength
                        System.out.println("INVALID COORDINATES! NOT A VALID LENGTH FOR THE SHIP TYPE! REQUIRED LENGTH: " + shipLength);
                        continue;
                    }
                }
                else if (beginningCoordinateNumber == endingCoordinateNumber) { // If column selection is the same...
                    if (Math.abs(endingCoordinateLetter - beginningCoordinateLetter) + 1 != shipLength) { // and length is not shipLength
                        System.out.println("INVALID COORDINATES! NOT A VALID LENGTH FOR THE SHIP TYPE! REQUIRED LENGTH: " + shipLength);
                        continue;
                    }
                }

                // Determine if the coordinates are to the up, right, down, or left.
                /*
                    0 is up.
                    1 is right.
                    2 is down.
                    3 is left.
                */
                int direction;
                // If the coordinates are horizontal (i.e. the letter selection is the same) ...
                if (beginningCoordinateLetter == endingCoordinateLetter) {
                    // If beginning number is more than the end number, then it's to the left.
                    if (beginningCoordinateNumber > endingCoordinateNumber) {
                        direction = 3;
                    }
                    // Otherwise, it's to the right.
                    else { direction = 1; }
                }
                else { // Otherwise, the coordinates are vertical.
                    // If beginningCoordinateLetter is below endingCoordinate letter, then it's up.
                    if (beginningCoordinateLetter > endingCoordinateLetter) {
                        direction = 0;
                    }
                    // Otherwise, it's down.
                    else { direction = 2; }
                }

                // Check if the placement would intersect with a ship currently on the map, make willIntersect true if so.
                boolean willIntersect = false;
                switch (direction) {
                    case 0: // Up
                        for (int j = 0; j < shipLength; j++) {
                            if (playerMap[beginningCoordinateLetter - j][beginningCoordinateNumber] == 1) {
                                willIntersect = true;
                                break;
                            }
                        }
                        break;
                    case 1: // Right
                        for (int j = 0; j < shipLength; j++) {
                            if (playerMap[beginningCoordinateLetter][beginningCoordinateNumber + j] == 1) {
                                willIntersect = true;
                                break;
                            }
                        }
                        break;
                    case 2: // Down
                        for (int j = 0; j < shipLength; j++) {
                            if (playerMap[beginningCoordinateLetter + j][beginningCoordinateNumber] == 1) {
                                willIntersect = true;
                                break;
                            }
                        }
                        break;
                    case 3: // Left
                        for (int j = 0; j < shipLength; j++) {
                            if (playerMap[beginningCoordinateLetter][beginningCoordinateNumber - j] == 1) {
                                willIntersect = true;
                                break;
                            }
                        }
                        break;
                }
                // If the placement intersects with another existing ship, continue the loop
                if (willIntersect) {
                    System.out.println("SHIP INTERSECTS WITH A CURRENT SHIP! PUT THE SHIP ELSEWHERE!");
                    continue;
                }

                // Now, if all conditions are ideal, place the ship on the map. As well, place the player coordinates in a list for the AI.
                switch (direction) {
                    case 0: // Up
                        for (int i = 0; i < shipLength; i++) {
                            playerMap[beginningCoordinateLetter - i][beginningCoordinateNumber] = 1;
                        }
                        break;
                    case 1: // Right
                        for (int i = 0; i < shipLength; i++) {
                            playerMap[beginningCoordinateLetter][beginningCoordinateNumber + i] = 1;
                        }
                        break;
                    case 2: // Down
                        for (int i = 0; i < shipLength; i++) {
                            playerMap[beginningCoordinateLetter + i][beginningCoordinateNumber] = 1;
                        }
                        break;
                    case 3: // Left
                        for (int i = 0; i < shipLength; i++) {
                            playerMap[beginningCoordinateLetter][beginningCoordinateNumber - i] = 1;
                        }
                        break;
                }

                wasPlacementValid = true; // Everything went well.

            } while (!wasPlacementValid);
            mapPrinter(true);
            shipLength--;
        }

    }

    // Declare private function that returns a random number within an inclusive range.
    private int getRandomInt(int lowerBound, int upperBound) {
        return (int)(Math.random() * (upperBound + 1 - lowerBound) + lowerBound);
    }

    // Declare a private function that begins the game, allows the player and AI to take turns firing at each other.
    private void beginGame() {

        // Start up the Seas of Attrition AI, who will be passed the player map for their shot validation.
        int[][] computerShotCoordinatesArray;
        int computerShotResult;
        ComputerPlayer ai = new ComputerPlayer(playerMap, playerMap.length - 1, playerMap[0].length - 1, roundCount);

        // Initialize variables for the player attrition system.
        int shotsThisRound; // Shots the player gets for their round.
        int[] userInputArray;

        // Initialize variables for the player's shot coordinates and ship losses.
        int coordinateLetter;
        int coordinateNumber;
        int oldShipsRemaining = playerShipsRemaining;
        int shipsLost;

        // While the game is still going on...
        while(playerShipsRemaining > 0 && computerShipsRemaining > 0) {

            // Set the number of shots the player can make based on the attrition system.
            shotsThisRound = getRandomInt(MINIMUM_SHOTS, MAXIMUM_SHOTS);

            // Print the map and round number.
            print.dramaPrint("===== HOUR " + roundCount + " =====");
            mapPrinter(false);

            // If the player, who is playing on realistic, has no shells to fire this round, let them know.
            if (shotsThisRound == 0) {
                cutscene.playCutscene(getRandomInt(21, 30));
            } else {
                print.dramaPrint("\"We've stocked up " + shotsThisRound + " cannon balls to fire!\"");
            }

            // Add shotsThisRound to totalShotsMade and shotsLostToAttrition for endgame statistics.
            totalShotsMade += shotsThisRound;
            shotsLostToAttrition += (MAXIMUM_SHOTS - shotsThisRound);

            // While the player still has shots remaining...
            while(shotsThisRound > 0) {

                // If there are no more AI ships, break from the loop.
                if(computerShipsRemaining <= 0) {
                    break;
                }

                // Get the coordinate for the player's shot.
                print.dramaPrint("\"Where to fire next, Captain?\"");
                userInputArray = coordinateInput(false);
                coordinateLetter = userInputArray[0];
                coordinateNumber = userInputArray[1];

                // If this shot was already taken...
                if (playerShotTrackingMap[coordinateLetter][coordinateNumber] != 0) {
                    print.dramaPrint("\"WE'VE ALREADY TAKEN A SHOT ON THIS GRID SQUARE! Choose another one Captain " + captainName + "!\"");
                    continue;
                }
                // Otherwise, if the shot is a valid hit on the AI Map...
                else if (computerMap[coordinateLetter][coordinateNumber] == 1) {
                    // Decrement the number of AI ships and shots remaining, print the shot result.
                    computerShipsRemaining--;
                    shotsThisRound--;
                    playerShotTrackingMap[coordinateLetter][coordinateNumber] = 1;
                    mapPrinter(false);

                    // Tell the player they scored a hit.
                    cutscene.playCutscene(getRandomInt(1,10));
                    print.dramaPrint(computerShipsRemaining + " enemy ships remain!");
                }
                // Otherwise, if the shot was a miss on the AI Map...
                else if (computerMap[coordinateLetter][coordinateNumber] == 0) {
                    // Decrement shots remaining, print the shot result.
                    shotsThisRound--;
                    playerShotTrackingMap[coordinateLetter][coordinateNumber] = 2;
                    mapPrinter(false);

                    // Tell the player they missed.
                    cutscene.playCutscene(getRandomInt(11, 20));
                }
                print.dramaPrint("SHOTS REMAINING: " + shotsThisRound + "}\n");
            }
            roundCount++;

            // Now, let the AI take their turn.
            print.dramaPrint("=== THE BRITISH FIRE THEIR BARRAGE. ===");

            // Poll the AI for what shots it wants to take and store their decision in a variable.
            computerShotCoordinatesArray = ai.pollForShots();

            // For every shot the AI takes, mark it as a 2 on the player map (for all 5 shots by the AI).
            for (int i = 0; i < computerShotCoordinatesArray.length; i++) {
                computerShotResult = playerMap[computerShotCoordinatesArray[i][0]][computerShotCoordinatesArray[i][1]];
                // If the shot was a direct hit (1), then decrement the playerShipsRemaining counter.
                if (computerShotResult == 1) {
                    playerShipsRemaining--;
                }
                // Rewrite the map for the AI to let it know what squares it already shot at (2).
                playerMap[computerShotCoordinatesArray[i][0]][computerShotCoordinatesArray[i][1]] = 2;
            }

            // Update the AI's visualization of the playerMap, tell them how many player ships are left (certain conditions will lead to a mercy kill, where the AI is 100% accurate).
            ai.setPlayerMap(playerMap);
            ai.setPlayerShipsRemaining(playerShipsRemaining);
            ai.setRoundCount(roundCount);

            // Show the player what shots the AI took against the player.
            mapPrinter(true);

            for (int i = 0; i < computerShotCoordinatesArray.length; i++) { // For every shot the AI took...
                // Print the letter the AI's X coordinate targeted.
                System.out.print(GRID_ALPHABET.charAt(computerShotCoordinatesArray[i][0]));
                // Print the number the AI's Y coordinate targeted.
                System.out.print(computerShotCoordinatesArray[i][1] + 1);
                // If the element is not the last one in the list, then append a comma.
                if (!(i + 1 == computerShotCoordinatesArray.length)) {
                    System.out.print(", ");
                }
            }
            System.out.println();
            shipsLost = oldShipsRemaining - playerShipsRemaining;

            // Show the player how many ships they lost that round.
            if (shipsLost != 0) {
                print.dramaPrint("\"We lost " + shipsLost + " of our ships in the last hour!\"");
            }
            else {
                print.dramaPrint("\"None of our ships were hit! We're safe for now, Captain.\"");
            }

            // Reprimand the player for having bad luck or a predictable ship setup if the British were able to make a 100% accurate barrage.
            if (oldShipsRemaining - playerShipsRemaining == 5) {
                print.dramaPrint("\"Damn it, Captain! That British barrage was 100% on target! Our ships are not spaced out enough, do you realize how many lives we lost because of your strategic failure?!\"");
            }

            if (playerShipsRemaining != 0) {
                print.dramaPrint("\"We have " + playerShipsRemaining + " of our ships still afloat!\"");
            }
            else {
                print.dramaPrint("\"Captain.}.}.} we've lost everyone in our battalion.} None of our ships or men are left alive}.}.}. it's time to retreat.\"");
            }
            print.dramaPrint("=======================================");
            oldShipsRemaining = playerShipsRemaining;

        }

        // Upon game over, if the AI has no more ships...
        if (computerShipsRemaining <= 0) {
            // And the player has no ships, it was a tie...
            if(playerShipsRemaining <= 0) {
                cutscene.playCutscene("TIE");
            }
            // Otherwise, it was a player victory.
            else {
                cutscene.playCutscene("VICTORY");
            }
        }
        // Otherwise, play the defeat cutscene.
        else if (playerShipsRemaining <= 0) {
            cutscene.playCutscene("DEFEAT");
        }

    }

    // Declare printStatistics(), which prints the player and AI performance statistics.
    private void printStatistics() {
        // Figure out the difficulty the player is on.
        String difficulty;
        if (MINIMUM_SHOTS == 2 && MAXIMUM_SHOTS == 5) {
            difficulty = "NORMAL";
        } else if (MINIMUM_SHOTS == 1 && MAXIMUM_SHOTS == 3) {
            difficulty = "HARD";
        } else {
            difficulty = "REALISTIC";
        }

        Integer computerShipsDown = 18 - computerShipsRemaining;
        Integer playerShipsDown = 18 - playerShipsRemaining;
        Double playerAccuracy = (int)(computerShipsDown.doubleValue() / totalShotsMade.doubleValue() * 10_000) / 100.0; // Rounding to two decimal places.
        Double computerAccuracy = (int)(playerShipsDown.doubleValue() / (roundCount.doubleValue() * 5.0) * 10_000) / 100.0;

        print.dramaPrint("========= PLAYER/AI STATISTICS =========");
        System.out.println("Player Statistics\n_________________");
        System.out.println("Difficulty:                " + difficulty);
        System.out.println("Name:                      " + "Captain " + captainName);
        System.out.println("Survived Until:            " + "Hour " + roundCount);
        System.out.println("Total Shots Made:          " + totalShotsMade);
        System.out.println("Shots On Target:           " + (18 - computerShipsRemaining));
        System.out.println("Shots Off Target:          " + (totalShotsMade - (18 - computerShipsRemaining)));
        System.out.println("Shots Lost To Attrition:   " + shotsLostToAttrition);
        System.out.println("Accuracy:                  " + playerAccuracy + "%");
        System.out.println("\nPLAYER MAP\n__________");
        mapPrinter(true);
        System.out.println();

        System.out.println("\nAI Statistics\n_____________");
        System.out.println("Total Shots Made:        " + (roundCount * 5)); // Since AI takes fives shots every round.
        System.out.println("Shots On Target:         " + (18 - playerShipsRemaining)); // Since AI takes fives shots every round.
        System.out.println("Shots Off Target:        " + (roundCount * 5 - (18 - playerShipsRemaining)));
        System.out.println("Accuracy:                " + computerAccuracy + "%");
        System.out.println("\nAI MAP\n______\n");

        System.out.println("  12345678910");
        for (int i = 0; i < computerMap.length; i++) {
            System.out.print(GRID_ALPHABET.charAt(i) + " ");
            for (int j = 0; j < computerMap[i].length; j++) {
                if (computerMap[i][j] == 0) {
                    System.out.print('.');
                } else if (computerMap[i][j] == 1) {
                    System.out.print('A');
                } else if (computerMap[i][j] == 2) {
                    System.out.print('X');
                }
            }
            System.out.println();
        }
        System.out.println("========================================\n\n");
    }

    // Declare a public function called start(). This method will begin the actual gameplay.
    public void start() {

        // Play the quartermaster introduction if the user wanted exposition.
        if (WILL_PLAY_EXPOSITION) {
            cutscene.playCutscene("POST-INTRO");
        }

        // Call mapCreator() to create the game maps, playerMapCreator() so the user can create their map.
        mapCreator();

        print.dramaPrint("== THE BATTLE OF NEW YORK HARBOR BEGINS ==");
        playerMapCreator();

        // Begin the game.
        beginGame();

        // By this point, the game has ended.
        // Print the player and AI statistics.
        printStatistics();

        // Add a 'press ENTER to quit game' option.
        System.out.println("Press the ENTER key to quit the game.");
        input.nextLine();
    }

}
