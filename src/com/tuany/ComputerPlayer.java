package com.tuany;

import java.util.Arrays;

/**
 * Hosts the state and behavior for the artificial intelligence for Seas of Attrition.
 * @author Tuany Van
 * @version 1.0, 10/26/21
 */

public class ComputerPlayer {

    // Declare instance variables for all the AI state values that change overtime.
    private int[][] playerMap;

    // Declare instance variables important in determining the AI's next decisions.
    private int[] coordinateMemory; // 1D array that stores coordinates when a random coordinate selection is a hit.
    private int directionMemory;
    private boolean[] directionsAttempted;
    private boolean hasFoundValidDirection;
    private boolean hasTriedOtherAxis;
    private int stepOver = 0; // How much the AI ship deviates from the coordinateMemory.

    // These grid dimensions will never change.
    private final int MAX_COORD_X;
    private final int MAX_COORD_Y;
    
    // Instance variables that determine whether to activate the endgame function, which ensures the player's defeat.
    private int playerShipsRemaining = 18; // 6 + 5 + 4 + 3 as per the rules of the game.
    private int roundCount;

    // Declare the 4-arg constructor, which takes the player map, its index dimensions, and round count for shot validation.
    public ComputerPlayer(int[][] playerMap, int MAX_COORD_X, int MAX_COORD_Y, int roundCount) {
        this.playerMap = playerMap;
        this.MAX_COORD_X = MAX_COORD_X;
        this.MAX_COORD_Y = MAX_COORD_Y;
        this.roundCount = roundCount;
        coordinateMemory = new int[] { -1, -1 };
        directionMemory = -1;
        directionsAttempted = new boolean[] { false, false, false, false };
    }

    // Declare a function that increments the direction given a number.
    private int findNextDirection(int direction) {
        int nextDirection = switch (direction) {
            case 0 -> // Up
                    1;
            case 1 -> // Right
                    2;
            case 2 -> // Down
                    3;
            case 3 -> // Left
                    0;
            default -> direction;
        };
        stepOver = 1;
        return nextDirection;
    }

    // Declare a function that finds the direction opposite the current direction of travel and sets the directionMemory to the other axis, then resets the stepOver to 1.
    private void findDirectionOppositeCurrentAxis() {
        hasTriedOtherAxis = true;
        switch (directionMemory) {
            case 0: // Up
                // If down not attempted...
                if(!directionsAttempted[2]) {
                    directionMemory = 2;
                    directionsAttempted[2] = true;
                    stepOver = 1;
                }
                break;
            case 1: // Right
                // If left not attempted...
                if(!directionsAttempted[3]) {
                    directionMemory = 3;
                    directionsAttempted[3] = true;
                    stepOver = 1;
                }
                break;
            case 2: // Down
                // If up not attempted...
                if(!directionsAttempted[0]) {
                    directionMemory = 0;
                    directionsAttempted[0] = true;
                    stepOver = 1;
                }
                break;
            case 3: // Left
                // If right not attempted...
                if(!directionsAttempted[1]) {
                    directionMemory = 1;
                    directionsAttempted[1] = true;
                    stepOver = 1;
                }
                break;
        }
    }

    // Declare a function that makes every AI logic value to its default value (-1, 0, and false where applicable).
    private void expungeMemory() {
        coordinateMemory = new int[] {-1, -1};
        directionMemory = -1;
        directionsAttempted = new boolean[] { false, false, false, false };
        hasFoundValidDirection = false;
        hasTriedOtherAxis = false;
        stepOver = 0;
    }

    // If a coordinate shot has already been taken that round, return true.
    private boolean isDuplicateCoordinate(int[][] decision, int[] shotCoordinates) {
        for (int[] ints : decision) {
            if (Arrays.equals(ints, shotCoordinates)) {
                return true;
            }
        }
        return false;
    }

    // Declare function that polls the AI for their 5 shots.
    public int[][] pollForShots() {

        int[][] decision = new int[5][2];
        int[] shotCoordinates = new int[2];
        int shotNumber = 0;
        int shotResult;
        boolean willExpungeMemory;

        // While the AI needs to take its five shots...
        while (shotNumber < 5) {
            /*
                ENDGAME FUNCTION
                If the number of player ships is less than 3, or the AI has reached 15 rounds,
                finish the player off (at this point, most of the map and/or player has been shot at by the AI, it would be time for a mercy kill).
            */
            if(playerShipsRemaining < 3 || roundCount >= 15) {
                // Iterate over the playerMap.
                for(int x = 0; x < playerMap.length; x++) {
                    for(int y = 0; y < playerMap[0].length; y++) {
                        // If the shot will be a hit, put it into the decision return.
                        if(playerMap[x][y] == 1 && shotNumber + 1 < 5) {
                            decision[shotNumber++] = new int[] {x, y};
                        }
                    }
                }
                break;
            }

            // If there is a coordinate and direction in the memory, keep attempting the line of fire in memory.
            if (!Arrays.equals(coordinateMemory, new int[]{-1, -1}) && !(directionMemory == -1)) {
                // Determine the next coordinate to shoot at based on the coordinateMemory and directionMemory.
                switch (directionMemory) {
                    case 0 -> // Up
                            shotCoordinates = new int[]{coordinateMemory[0] - stepOver, coordinateMemory[1]};
                    case 1 -> // Right
                            shotCoordinates = new int[]{coordinateMemory[0], coordinateMemory[1] + stepOver};
                    case 2 -> // Down
                            shotCoordinates = new int[]{coordinateMemory[0] + stepOver, coordinateMemory[1]};
                    case 3 -> // Left
                            shotCoordinates = new int[]{coordinateMemory[0], coordinateMemory[1] - stepOver};
                }
                // If the next coordinate is beyond the boundaries of the game, try a new direction.
                if(shotCoordinates[0] < 0 || shotCoordinates[0] > MAX_COORD_X || shotCoordinates[1] < 0 || shotCoordinates[1] > MAX_COORD_Y) {
                    
                    // If the opposite direction in the same axis has not been attempted, try that other direction.
                    if(!hasTriedOtherAxis) {
                        findDirectionOppositeCurrentAxis();
                    }
                    // Otherwise, expunge the memory.
                    else {
                        expungeMemory();
                    }
                    continue;
                }

                shotResult = playerMap[shotCoordinates[0]][shotCoordinates[1]];
                // If the chosen direction was not a hit (0):
                if(shotResult == 0) {
                    
                    // If the missed shot is a shot that was already taken before, the AI is repeating itself, force it to expunge its memory.
                    willExpungeMemory = isDuplicateCoordinate(decision, shotCoordinates);
                    if(willExpungeMemory) {
                        
                        expungeMemory();
                        continue; // Reset the current loop.
                    }
                    // Add the shot to the decisions list and increment the shot number.
                    decision[shotNumber] = shotCoordinates;
                    shotNumber++;
                    stepOver++;

                    // If this shot was a miss even though the AI was travelling down a suspected path (hasFoundValidDirection), that means it has spanned an entire ship on one axis.

                    // If the AI has already tried the other axis, then expunge the memory.
                    if(hasTriedOtherAxis) {
                        expungeMemory();
                    }

                    // Otherwise, set the directionMemory to the other axis and reset the stepOver to 1, allowing the AI to travel from the coordinateMemory and in the direction as normal.
                    else if(hasFoundValidDirection) {
                        
                        findDirectionOppositeCurrentAxis();
                        
                    }
                    else { // Otherwise, set the directionMemory to another direction.
                        directionMemory = findNextDirection(directionMemory);
                        directionsAttempted[directionMemory] = true;
                    }
                }

                // Otherwise, if the chosen direction was already fired upon (2):
                else if(shotResult == 2) {
                    
                    // Increment the stepOver in case another ship exists on the other side, only is hasFoundValidDirection is true.
                    if(hasFoundValidDirection) {
                        stepOver++;
                    }
                    // Otherwise, expunge the memory.
                    else {
                        expungeMemory();
                    }
                }
                // Otherwise, if the chosen direction was a hit (1), do not try another direction.
                else if(shotResult == 1) {
                    // If the hit is a shot that was already taken before, the AI is repeating itself, force it to expunge its memory.
                    willExpungeMemory = isDuplicateCoordinate(decision, shotCoordinates);
                    if(willExpungeMemory) {
                        
                        expungeMemory();
                        continue; // Reset the current loop.
                    }

                    // Add the shot to the decisions list and increment the shot number.
                    
                    hasFoundValidDirection = true;
                    decision[shotNumber] = shotCoordinates;
                    shotNumber++;
                    stepOver++;
                }
            }

            // Otherwise, determine a random coordinate to shoot at if there is no coordinate or direction memory.
            else {
                int randomX = Random.getRandomInt(MAX_COORD_X);
                int randomY = Random.getRandomInt(MAX_COORD_Y);
                shotCoordinates = new int[] {randomX, randomY};
                shotResult = playerMap[randomX][randomY];
                // If the coordinate does not have a ship (0), then put that coordinate in the decision but do not keep it in memory.
                if(shotResult == 0) {
                    // If shot was already taken, do not add it to the decision.
                    if(isDuplicateCoordinate(decision, shotCoordinates)) {
                        
                        continue;
                    }
                    decision[shotNumber] = shotCoordinates;
                    shotNumber++;
                }

                // Otherwise, if the coordinate is a ship (1), then put it in decision and keep it in memory.
                else if(shotResult == 1) {
                    
                    // If the shot was already taken, do not add it to the decision.
                    if(isDuplicateCoordinate(decision, shotCoordinates)) {
                        
                        continue;
                    }
                    decision[shotNumber] = shotCoordinates;
                    shotNumber++;

                    // Put the coordinates into memory.
                    coordinateMemory = new int[] {randomX, randomY};
                    stepOver++;
                    // Choose a random direction to fire a line, make that entry in directionsAttempted true.
                    directionMemory = Random.getRandomInt(3);
                    directionsAttempted[directionMemory] = true;
                }
                // Otherwise, if the randomly chosen coordinate was already fired upon (2), then do nothing, which continues the loop.
            }
        }

        // Return the AI decision.
        return decision;
    }

    // Setter for instance variables important to AI logic, decisions, and endgame condition.
    public void setPlayerMap(int[][] playerMap) {
        this.playerMap = playerMap;
    }

    public void setPlayerShipsRemaining(int playerShipsRemaining) {
        this.playerShipsRemaining = playerShipsRemaining;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

}
