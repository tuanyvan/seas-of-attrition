package com.tuany;

/**
 * Contains state and behavior for player/AI gameplay maps.
 * @author Tuany Van
 * @version 1.0, 01/12/22
 */

public class Map {

    // Declare instance variables.
    private final int[][] map;

    // 2-arg constructor.
    public Map(int xAxisLength, int yAxisLength) {
        this.map = new int[xAxisLength][yAxisLength];
    }

    // Declare a function to get ship at given coordinate.
    public int getSpaceAt(int x, int y) {
        return map[x][y];
    }

    // Declare a function to get the map as a string.
    public StringBuilder toString(boolean isShotTrackingMap) {

        final String GRID_ALPHABET = "ABCDEFGHIJ";
        StringBuilder mapString = new StringBuilder("  12345678910\n");

        if (isShotTrackingMap) {
            for (int i = 0; i < this.map.length; i++) {

                mapString.append(GRID_ALPHABET.charAt(i)).append(" ");

                for (int j = 0; j < this.map[i].length; j++) {
                    if (this.map[i][j] == 0) { // No shot taken on this spot...
                        mapString.append('.');
                    } else if (this.map[i][j] == 1) { // Effective hit on this spot.
                        mapString.append('X');
                    } else if (this.map[i][j] == 2) { // Miss on this spot.
                        mapString.append('O');
                    }
                }
                mapString.append('\n');
            }
        }

        else {
            for (int i = 0; i < this.map.length; i++) {

                mapString.append(GRID_ALPHABET.charAt(i)).append(" ");

                for (int j = 0; j < this.map[i].length; j++) {
                    if (this.map[i][j] == 0) {
                        mapString.append('.');
                    } else if (this.map[i][j] == 1) {
                        mapString.append('S');
                    } else if (this.map[i][j] == 2) {
                        mapString.append('X');
                    }
                }
                mapString.append('\n');
            }
        }

        return mapString;

    }

    // Declare a function that autofills the map for the AI.
    public void autofillMap() {
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
            startingCoordinateX = Random.getRandomInt(0, this.map.length - 1); // Length less 1 to accommodate indexes.
            startingCoordinateY = Random.getRandomInt(0, this.map[0].length - 1); // First element is the same length as every other element.

            // Determine a random direction to place the enemy ship.
            /*
                0 is up.
                1 is right.
                2 is down.
                3 is left.
             */
            direction = Random.getRandomInt(0, 3);

            // Check if direction is a valid placement twice.
            for (int i = 0; i < 2; i++) {
                // If direction is invalid, the direction will change accordingly. If not, then the direction does not change at all.
                switch (direction) {
                    case 0: // Up
                        if (startingCoordinateX - shipLength < 0) { // If going up stretches beyond the map...
                            direction = 2; // Then go down instead.
                        }
                        break;
                    case 1: // Right
                        if (startingCoordinateY + shipLength >= map[0].length) { // If going right stretches beyond the map...
                            direction = 3; // Then go left instead
                        }
                        break;
                    case 2: // Down
                        if (startingCoordinateX + shipLength >= map.length) { // If going down stretches beyond the map...
                            direction = 0; // Then go up instead
                        }
                        break;
                    case 3: // Left
                        if (startingCoordinateY - shipLength < 0) { // If going left stretches beyond the map...
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
                        if (getSpaceAt(startingCoordinateX - j, startingCoordinateY) == 1) {
                            willIntersect = true;
                            break;
                        }
                    }
                    break;
                case 1: // Right
                    for (int j = 0; j < shipLength; j++) {
                        if (getSpaceAt(startingCoordinateX, startingCoordinateY + j) == 1) {
                            willIntersect = true;
                            break;
                        }
                    }
                    break;
                case 2: // Down
                    for (int j = 0; j < shipLength; j++) {
                        if (getSpaceAt(startingCoordinateX + j, startingCoordinateY) == 1) {
                            willIntersect = true;
                            break;
                        }
                    }
                    break;
                case 3: // Left
                    for (int j = 0; j < shipLength; j++) {
                        if (getSpaceAt(startingCoordinateX, startingCoordinateY - j) == 1) {
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
                        this.map[startingCoordinateX - i][startingCoordinateY] = 1; // 1 indicates a ship is present on the map.
                    }
                    break;
                case 1: // Right
                    for (int i = 0; i < shipLength; i++) {
                        this.map[startingCoordinateX][startingCoordinateY + i] = 1;
                    }
                    break;
                case 2: // Down
                    for (int i = 0; i < shipLength; i++) {
                        this.map[startingCoordinateX + i][startingCoordinateY] = 1;
                    }
                    break;
                case 3: // Left
                    for (int i = 0; i < shipLength; i++) {
                        this.map[startingCoordinateX][startingCoordinateY - i] = 1;
                    }
                    break;
            }

            shipLength--; // Decrement the ship length.

        }
    }

    // Setter
    public void setMapSpace(int x, int y, int value) {
        map[x][y] = value;
    }

    // Getter
    public int[][] getMap() {
        return map;
    }

}
