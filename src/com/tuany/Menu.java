package com.tuany;

import java.util.Scanner;

/**
 * The main menu of the Seas of Attrition game that sends difficulty and player information to the Game class.
 * @author Tuany Van
 * @version 1.1, 10/16/21
 */

public class Menu {

    // Declare willPlayExposition, an instance variable that determines if the intro cutscene will play.
    private static boolean willPlayExposition;

    // Declare printTitle, which prints out the game name, picture, and intro cutscene if wanted by the user.
    private static void printIntro() {

        // Instantiate DramaPrinter for exposition and Scanner for input, play intro for whether user wants to see the intro again.
        Scanner input = new Scanner(System.in);
        Cutscenes cutscenes = new Cutscenes();

        // Main Menu Name and Logo
        System.out.println("""


                  ██████ ▓█████ ▄▄▄        ██████           ▒█████    █████▒          ▄▄▄     ▄▄▄█████▓▄▄▄█████▓ ██▀███   ██▓▄▄▄█████▓ ██▓ ▒█████   ███▄    █\s
                ▒██    ▒ ▓█   ▀▒████▄    ▒██    ▒          ▒██▒  ██▒▓██   ▒          ▒████▄   ▓  ██▒ ▓▒▓  ██▒ ▓▒▓██ ▒ ██▒▓██▒▓  ██▒ ▓▒▓██▒▒██▒  ██▒ ██ ▀█   █\s
                ░ ▓██▄   ▒███  ▒██  ▀█▄  ░ ▓██▄            ▒██░  ██▒▒████ ░          ▒██  ▀█▄ ▒ ▓██░ ▒░▒ ▓██░ ▒░▓██ ░▄█ ▒▒██▒▒ ▓██░ ▒░▒██▒▒██░  ██▒▓██  ▀█ ██▒
                  ▒   ██▒▒▓█  ▄░██▄▄▄▄██   ▒   ██▒         ▒██   ██░░▓█▒  ░          ░██▄▄▄▄██░ ▓██▓ ░ ░ ▓██▓ ░ ▒██▀▀█▄  ░██░░ ▓██▓ ░ ░██░▒██   ██░▓██▒  ▐▌██▒
                ▒██████▒▒░▒████▒▓█   ▓██▒▒██████▒▒         ░ ████▓▒░░▒█░              ▓█   ▓██▒ ▒██▒ ░   ▒██▒ ░ ░██▓ ▒██▒░██░  ▒██▒ ░ ░██░░ ████▓▒░▒██░   ▓██░
                ▒ ▒▓▒ ▒ ░░░ ▒░ ░▒▒   ▓▒█░▒ ▒▓▒ ▒ ░         ░ ▒░▒░▒░  ▒ ░              ▒▒   ▓▒█░ ▒ ░░     ▒ ░░   ░ ▒▓ ░▒▓░░▓    ▒ ░░   ░▓  ░ ▒░▒░▒░ ░ ▒░   ▒ ▒\s
                ░ ░▒  ░ ░ ░ ░  ░ ▒   ▒▒ ░░ ░▒  ░ ░           ░ ▒ ▒░  ░                 ▒   ▒▒ ░   ░        ░      ░▒ ░ ▒░ ▒ ░    ░     ▒ ░  ░ ▒ ▒░ ░ ░░   ░ ▒░
                ░  ░  ░     ░    ░   ▒   ░  ░  ░           ░ ░ ░ ▒   ░ ░               ░   ▒    ░        ░        ░░   ░  ▒ ░  ░       ▒ ░░ ░ ░ ▒     ░   ░ ░\s
                      ░     ░  ░     ░  ░      ░               ░ ░                         ░  ░                    ░      ░            ░      ░ ░           ░\s
                                                                                                                                                             \s""");
        System.out.println("""
                                               ..--+my`                                                            \s
                                        -o+:::dMMMMMMN.                          /o.                               \s
                                         `/sdNmdmhMMMN.               --`  -ydmmmMMy                               \s
                                         ..--:/---+mMN:-....-.-..     -ohdmmNMNNMMMy                               \s
                                        -mNNNNNNNNNMMMNNNNNNNNNNN+      `./o/-:+hMMy                               \s
                                         +MMh:-----------------sMM+   /ddddddddddMMNddddddddddo`                   \s
                                          sMN-                 `hMN.  :NMMysssssssssssssssssyNMs                   \s
                                          -NMs                  /MM/   /NMs                  +MM+                  \s
                                          -MMs                  /MM/    hMN.                 `mMd                  \s
                                          sMN-                 `dMm.    sMN-                  dMm`                 \s
                                        `oMMd//////////////////yMN/    `dMm`                 .NMh                  \s
                                        .mNNNmmmmmmNMMNmmmmmmmmNm/    `sMMo..................hMN-                  \s
                                        `--::::::::hMN/:::::::::-`    oMMMmmmmmmmmmmmmmmmmmmmMN/                   \s
                                       +mmmmmmmmmmmNMMmmmmmmmmmmmd/   -+++++++++oNMd++++++++++-                    \s
                                       :NMmo++++++++++++++++++++hMN-`+ssssssssssyMMmsssssssssss/                   \s
                                        oMM/                    .NMh.dMMmdddddddddddddddddddddMMo                  \s
                                        `mMd`                    oMM:-mMd`                    yMN:                 \s
                                         sMN-                    .NMy +MM/                    `mMd`                \s
                                         /MM/                    `mMd `NMh                     oMM-                \s
                                         :MM/                    `dMd  hMN`                    /MM+                \s
                                         +MM:                    .mMh  hMN`                    /MM/                \s
                                         hMN.                    :MM+ `NMh                     sMM.                \s
                                        :NMs                    `dMN` oMM/                    .NMh                 \s
                           `````````   `dMN-````````````````````oMM+ :NMh.```````````````````.dMN.        .odh.    \s
                         .dNNNNNNNNNh/`sMMMNNNNNNNNNNNNNNNNNNNNNMMy `mMMMNNNNNNNNNNNNNNNNNNNNNMN:       -yNMmo`    \s
                         -MMh//////smMmhs//////////hMM+//////////:`  -//////////+MMd///////////-     `/hNMh/`      \s
                         -MMh+/`    `/hNNmo++++++++dMMo:`               .+++++++oMMd++++++++++++++++omMNs-`        \s
                         .dmmMMo       .+dmmmmmmmmmmmmNMd/.............oNMmmmmmmmmmmmmmmmmmmmmmmmNMMMmo.           \s
                          ``:MMo         `````````````-sNMNmNNmNmNNmNmNMm+.````````````````````-sNMm+`             \s
                            -MMmo/-`                    .///////////////.                    `/mMm+`               \s
                             /shmNNmh-                                                      .yMNs.                 \s
                                .+dMM+               -+o/.    `/oo:     -oo/`              -mMm:                   \s
                               -hNNMM+              .mMMMd`   yMMMN/   :NMMMh              hMN-                    \s
                               yMN/mMd.             `hNNNy`   omNNd-   -dNNmo             `mMd                     \s
                      ``.-:::::dMm`:mMd.       ``.-::/oo/.``   .--`     .://:::::-.``     +MM+   ``.--:::--.``     \s
                ...:+shmmNmmmmmNNNdsyMMm-...:+shmmNmmmmmNmmhs+:......:+shmNNmmmmmNNmhs+:-oNMh-:+shmmNmmmmmNmmhs+:...
                NNNNmho/-.`````.-/ohmNNNNNNNNmho/-.`````.-/ohmNNNNNNNNmho/-.`````.-/ohmNNNNNNNNmho/-.`````.-/ohmNNNN
                -..`                 `..--..`                 `..--..`                 `..--..`                 `..-
                                                                                                                   \s
                       `-:/+++//-`              `-:/+++//-`              `-:/+++//-`              `-:/+++/:-`      \s
                --:+ohmMMNmdddmNMMmhs+:----:+ohmMMNmdddmNMMmhs+:----:+ohmMMNmdddmNMMmhs+/:--:/+shmMMNmdddmNMMmho+:--
                MMNmhs+-`       `-/shmNMMMMNmhs+-`       `-/shmNMMMMNmhs+-`       `-/shmNMMMMNmhs+-`       `-+shmNMM


                """);

        // Ask user if they want to play the exposition.
        System.out.println("Play the intro and exposition? Y/N");
        String userResponse = input.nextLine().toLowerCase();

        // If the user wants to play the exposition...
        if(userResponse.equals("y") || userResponse.equals("yes")) {
            // Set willPlayExposition to true.
            willPlayExposition = true;
            // Print out exposition for user.
            cutscenes.playCutscene("INTRO");
        }

    }

    /**
     * Called by Main.java to start the game.
     */
    public static void start() {

        // Instantiate the object necessary for the Menu start() function.
        Scanner input = new Scanner(System.in);
        Cutscenes cutscenes = new Cutscenes();

        // Show the title card.
        printIntro();

        // Print out difficulty settings for the user, with a shorter version if the user wants to speed past story.
        if(willPlayExposition) {
            cutscenes.playCutscene("LONG-DIFFICULTY");
        }
        else {
            cutscenes.playCutscene("SHORT-DIFFICULTY");
        }
        System.out.print("DIFFICULTY SELECTION: ");

        // Do while user input, process input to make sure it is a valid option, otherwise warn them the input was invalid.
        boolean hasMadeValidInput = false;
        String userDifficultySelection;
        int minimumShots = 2;
        int maximumShots = 5;
        do {
            userDifficultySelection = input.nextLine().toLowerCase();
            switch (userDifficultySelection) {
                case "n", "normal", "[normal]" -> hasMadeValidInput = true;
                case "h", "hard", "[hard]" -> {
                    minimumShots = 1;
                    maximumShots = 3;
                    hasMadeValidInput = true;
                }
                case "r", "realistic", "[realistic]" -> {
                    minimumShots = 0;
                    maximumShots = 3;
                    hasMadeValidInput = true;
                }
                case "i", "instructions", "[instructions]" -> {
                    cutscenes.playCutscene("INSTRUCTIONS");
                    System.out.print("DIFFICULTY SELECTION: ");
                }
                default -> System.out.print("You have made an invalid input. To choose a difficulty like normal, input \"n\", \"normal\", or \"[normal]\".\nDIFFICULTY SELECTION: ");
            }
        } while(!hasMadeValidInput);

        // Give the user some more story if they wanted it, then ask the user for their name.
        if(willPlayExposition) {
            cutscenes.playCutscene("WASHINGTON-INTRO");
        }

        // Ask the player for their name.
        String captainName;
        System.out.print("\nYOUR NAME, CAPTAIN: ");
        captainName = input.nextLine().trim();
        if (captainName.equals("")) {
            captainName = "Nameless";
        }

        // Once difficult has been selected and name was chosen, call on the Game class to start the game.
        Game newGame = new Game(minimumShots, maximumShots, captainName, willPlayExposition);
        newGame.start();

    }
}
