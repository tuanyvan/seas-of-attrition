package com.tuany;

/**
 * Hosts drawn-out cutscenes and random event messages which are called in the Game and Menu class.
 * @author Tuany Van
 * @version 1.0, 10/17/2021
 */

public class Cutscenes {

    // A single instance variable, containing the captain's name for personalization.
    private String captainName;

    // 1-parameter constructor.
    public Cutscenes(String captainName) {
        this.captainName = captainName;
    }

    // Default constructor when the captainName is not known yet.
    public Cutscenes() {
        this.captainName = ""; // Placeholder name, will never be used if Cutscenes is instantiated with the default constructor.
    }

    // Instantiate a new DramaPrinter.
    DramaPrinter print = new DramaPrinter(20); // Default narrator speed.

    // Declare function playCutScene, which takes a String to play a drawn-out cutscene.
    public void playCutscene(String cutScene) {
        switch(cutScene) {
            // "INTRO": Introduces the player to the story's background.
            case "INTRO":
                print.dramaPrint("\n}American Revolutionary War. September 15th, 1776.}\n\nGeneral George Washington has ordered a retreat after losing thousands of American soldiers in the Battle of Long Island.} This loss allows the British to begin its invasion of New York.} Their warships have already created a massive blockade to intercept our naval supply lines.} The British Royal Navy holds superiority in New York Harbor.}\n\nWithout the resupply of manpower or rations, the Continental Army's defensive line in New York grows weaker.} In an effort to make a last stand, the Continental Navy will send out its remaining battalion of warships and frigates under your control, Captain.} These ships are short on rations and ammunition, meaning our counterattack will be difficult.}\n\nIf the Navy can take back superiority of New York's waters, the Army can easily counter the British offensive and reclaim New York for good.\n}");
                break;

            // "WASHINGTON-INTRO": Washington is introduced by the narrator and talks to the player.
            case "WASHINGTON-INTRO":
                print.setDelay(20);
                print.dramaPrint("...}\nGeorge Washington stands in the shadow of the USS Providence, the Continental Colors gloriously waving in the wind. He greets you with punctuality, a sense of guilt sewed in his eyes from the aftermath at Long Island.");
                print.setDelay(50);
                print.dramaPrint("}\n\"Say, we haven't been yet acquainted, Captain.} The Army rarely speaks to the Navy, seeing as how we fight on such different front lines.} Our military is in such disarray, and the Revolution sure isn't helping organization.} I entrust in your command to take back the Harbor from the English.} Your quartermaster and I would like to know your name, what is it son?\"");
                break;

            // "POST-INTRO": Captain is assigned to the final Continental Navy battalion, introduced to the first mate.
            case "POST-INTRO":
                print.setDelay(50); // This is the speed George Washington speaks.
                print.dramaPrint("\"An honor to meet you, Captain " + captainName + ".} I expect an American victory out of you.} Show that King George this British madness and tyranny can't go on any longer.} Now go, acquaint yourself with the quartermaster, he is aboard the Providence.} This battle will be tough, son.\"}\n");

                print.setDelay(20); // This will be the delay speed of the narrator.
                print.dramaPrint("Swiftly, you make your way up the gangway of the USS Providence.}}\nAs you organize your belongings and papers in your quarters, you hear a distinctively Scottish voice calling from the main deck. It's your first mate, Robert Witterel, his frantic hails turning his voice hoarse.} You snap out of your haze and swiftly report to him.\n");

                print.setDelay(30); // This will be the delay speed of the first mate, Robert Witterel.
                print.dramaPrint("\"CAPTAIN{} Our ships are to report to their action stations immediately!} The British have broken past The Narrows and are moving towards New York City!\"}");

                print.setDelay(20);
                print.dramaPrint("\nA cannon furiously booms in the distance and you can feel splinters pepper the hull as a cannon ball violently annihilates a moored ship not far from your position.} Your first mate is unfazed.}\n");

                print.setDelay(30);
                print.dramaPrint("\"We have organized a temporary defensive line using commandeered merchant ships, but it won't last a minute Captain " + captainName + "{} I highly recommend we coordinate our convoys immediately in preparation, we're gonna have to make a last stand with our supply lines still crippled{\"}\n");

                print.setDelay(20);
                print.dramaPrint("A surprise breakthrough is exactly what you feared, but you've lived your entire service taking down the British under every circumstance. Surely this battle will be no different.}.}.}\n");
                break;

            // "VICTORY": When the player destroys all the enemy ships but has ships remaining themselves.
            case "VICTORY":
                print.setDelay(20);
                print.dramaPrint("The Harbor falls quiet, but the cheers of the American soldiers are heard all across.} The British Navy cannot rule these waves!} Your first mate, Witterel, stands at the bow, amazed at the victory you managed to pull together, despite the circumstances.} Even he,} despite the several years he's practiced the art of war,} cannot understand why fate spared the Americans today from a total defeat.} Witterel looks back at you and through a grin exclaims,\n");

                print.setDelay(30);
                print.dramaPrint("\"We did it, Captain " + captainName + "!} You are one miracle worker, but I can attest to the fact that you have saved this nation.} There is no battle in the war more important than this one.}.}. I wonder what Washington will think of this...\"");

                print.setDelay(20);
                print.dramaPrint("}}\n[1 MONTH LATER]}}\nWashington presents you with the Comitia Americana medal, a distinction you wear with pride as the Continental Colors are raised over New York.} You gaze around at the crowds gathered at your ceremony, atop a stage that overlooks the city and Harbor.} Your battle won the war, allowing America to win over the Atlantic coast, blocking any further naval invasion from the British, and turning the tables of attrition onto their troops.} As Washington affixes the medal onto your uniform, the crowd of American soldiers and civilians cheer,\n");
                print.setDelay(150);
                print.dramaPrint("\"LIBERTY OR DEATH! LIBERTY OVER DEATH!\"\n\n");
                break;

            // "TIE": When both the player and enemy have no ships remaining.
            case "TIE":
                print.setDelay(20);
                print.dramaPrint("The Harbor falls awfully quiet.} Your command ship is the only one left afloat, and all that remains is the smoldering wrecks of both American and British ships.} You promised Washington a victory, but could you call the loss of every warship and man on both sides a victory?} You look for your first mate.}}\n\nIt takes a few minutes of frantic wandering, but you find your first mate, Witterel, bleeding out in the surgeon's quarters.} He's in a bad way.} The surgeon is nowhere to be found, so you try your best to treat his wounds.} His life comes first.}}\n\n[1 MONTH LATER]}}\n\nYou awake in a nervous sweat.} Outside your window, you see the flag of The Queen's United Colonies being raised.} Nothing out of the ordinary.} In the month after the war, Britain made a bilateral treaty with America to end the revolution and reunite under one government.} The war crippled the military assets of both sides, so Britain and America joined hands in rearming their militaries and reestablishing diplomacy.} You leave for your day job, a humble self-made tailor, a job distant from your military career.} Before you leave, you knock on your neighbor's door.} As he opens the door, Witterel shines a large grin, his wounds healed well.} You walk to work with him, discussing what could come next in this crazy bizarre nation.\n\n");
                break;

            // "DEFEAT": When the enemy destroys all the player ships.
            case "DEFEAT":
                print.setDelay(20);
                print.dramaPrint("The Harbor falls quiet on the American side.} You hear the British cannons boom closer and closer towards your command ship.} This is the end.}}\n\nYour sense of dread is annihilated by a cannon ball that crashes through the hull of the USS Providence.} The ship begins to sink and you sit solemnly in your quarters.} You're going down with the ship as it falls into the Harbor.} You see Witterel outside your window evacuating the crew, a defiance of maritime tradition.} They leave you for dead, but you wouldn't have abandoned ship like that anyway.}}\n\nAs the last of the ship falls below the water, you try to swim for shore.} A British reconnaissance ship impedes your escape and demands you surrender.} You can't exactly argue with the power of a musket in your face.}.}.} but you aren't going down without a fight.}}}\n\nUpon boarding the ship, you wrestle a rifle out of the hands of an inattentive soldier, and fire one last barrage at the enemy.} Your shot pierces through the body of three soldiers, but as you try to dive back into the water, a well-placed shot pierces your chest.}}}\n\nYou hang onto dear life.}}}\n\nYou try to swim but.}.}.}}}\n\nYour body gives out as the cold New York waters consume you.} You feel an odd vision, one where the history books dishonor your name.}.}.} but they will never understand the great fight you put up until the end.}}}\n\n");
                break;

            // "LONG-DIFFICULTY": The normal presentation of difficulty.
            case "LONG-DIFFICULTY":
                print.dramaPrint("SELECT A CAMPAIGN TO FIGHT IN:\n[NORMAL]\nOn a few flanks, the British have left their guard down. We can seize some old ports and open up supply lines there. This way, we can support up to 2-5 shots with every barrage.\n\n[HARD]\nThe British have the upper hand on us! Military provisions can barely slip through their naval blockades and we have no more avenues to resupply our troops and ships. This leaves us with 1-3 shots every barrage.\n\n[REALISTIC]\nEvery soldier hangs on to the skin of their teeth. The British have completely seized our supply lines. Our men fight each other for rations day and night. We can only manage up to 3 shots per barrage, and there's a chance we never get to fire a single round due to infighting and interdiction on our supply lines.\n\n[INSTRUCTIONS] Get instructions on how the game works.\n");
                break;

            // "SHORT-DIFFICULTY": A shortened version of the difficulty options.
            case "SHORT-DIFFICULTY":
                print.dramaPrint("SELECT A CAMPAIGN TO FIGHT IN:\n[NORMAL]\n2-5 shots with every barrage.\n\n[HARD]\n1-3 shots every barrage.\n\n[REALISTIC]\nWe can only manage up to 3 shots per barrage, and there's a chance we never get to fire a single round at any given hour.\n\n[INSTRUCTIONS] Get instructions on how the game works.");
                break;

            case "INSTRUCTIONS":
                System.out.println("Seas of Attrition is a lot like Battleship, but as you can infer, you are at a disadvantage. After placing your ships on the map, the game will go into turn-based combat.\n\nIn the first phase, you will be firing at the enemy first. You get a random number of shots depending on the difficulty you selected and you fire each shot one by one. After you fire all your shots, the enemy shoots exactly 5 shots back at your ships. This combat phase repeats until:\na) You run out of ships.\nb) The enemy has no more ships remaining.\nc) You have less than three ships or 15 rounds have passed, in which case the AI finishes you off.\n\nThis game is meant to be unfair; you are after all at a strategic disadvantage. Accuracy is of the essence. Develop a stratagem to find the enemy ships. Plead that Fortuna is on your side.");

        }
    }

    // Declare function playCutscene, which takes an integer to play a random event message, separated from the String-arg function to prevent tripping any drawn-out cutscenes. This function works well when a randomized integer is passed as an argument.
    public void playCutscene(int cutScene) {
        print.setDelay(30);
        switch(cutScene) {
            // 1-10 will be the unique hit messages. !!! and a two-second pause.
            case 1:
                print.dramaPrint("\"THAT ONE WAS ON THE MARK{\"}");
                break;
            case 2:
                print.dramaPrint("\"CRITICAL HIT{\"}");
                break;
            case 3:
                print.dramaPrint("\"NICE SHOT{\"}");
                break;
            case 4:
                print.dramaPrint("\"THE BRITISH WILL FEEL THAT ONE{\"}");
                break;
            case 5:
                print.dramaPrint("\"GOOD HIT{\"}");
                break;
            case 6:
                print.dramaPrint("\"WE'VE LIT THEM UP WELL{\"}");
                break;
            case 7:
                print.dramaPrint("\"KING GEORGE'S GONNA FEEL THAT ONE{\"}");
                break;
            case 8:
                print.dramaPrint("\"HOWE'S GONNA BE UPSET ABOUT THAT HIT{\"}");
                break;
            case 9:
                print.dramaPrint("\"RIGHT THROUGH THOSE REDCOATS{\"}");
                break;
            case 10:
                print.dramaPrint("\"WE GOT ONE{\"}");
                break;
            // 11 to 20 will be unique miss messages. Some flavor text followed by a one-second delay.
            case 11:
                print.dramaPrint("\"That one hit the fish.\"}");
                break;
            case 12:
                print.dramaPrint("\"Off target.\"}");
                break;
            case 13:
                print.dramaPrint("\"Not quite on target, Captain.\"}");
                break;
            case 14:
                print.dramaPrint("\"Agh, we've missed.\"}");
                break;
            case 15:
                print.dramaPrint("\"Our aim's a little off...\"}");
                break;
            case 16:
                print.dramaPrint("\"That went right past them.\"}");
                break;
            case 17:
                print.dramaPrint("\"Where did they go? Our shell must not have hit!\"}");
                break;
            case 18:
                print.dramaPrint("\"Almost got them, but not quite.\"}");
                break;
            case 19:
                print.dramaPrint("\"Shoot! We haven't shot them one bit!\"}");
                break;
            case 20:
                print.dramaPrint("\"Washington 'll be disappointed, but I'm not, Captain.} We'll get them next shot...\"}");
                break;
            // 21-30 will be no ammunition messages.
            case 21:
                print.dramaPrint("\"Tough luck, Captain. We're out of shells this hour.\"}");
                break;
            case 22:
                print.dramaPrint("\"None of our ships could resupply, we have no shells.\"}");
                break;
            case 23:
                print.dramaPrint("\"Not good...} not good...} we're out of supplies.\"}");
                break;
            case 24:
                print.dramaPrint("\"Our ammunition is completely empty. We need to pass this hour by.\"}");
                break;
            case 25:
                print.dramaPrint("\"Verdammt! We couldn't resupply for any round shots!\"}");
                break;
            case 26:
                print.dramaPrint("\"Our men couldn't even organize a resupply! We cannot fire any of our cannons.\"}");
                break;
            case 27:
                print.dramaPrint("\"No good.} The nearby ports are blocked off, we can't resupply, Captain " + captainName + ".\"}");
                break;
            case 28:
                print.dramaPrint("\"Agh, our nearby resupply point has been intercepted. We have no ammo at this hour!\"}");
                break;
            case 29:
                print.dramaPrint("\"The British have interdicted our supply ships! We can't resupply this hour!.\"}");
                break;
            case 30:
                print.dramaPrint("\"Good grief. None of our supply ships could break through the British! We're dry on cannon shot this hour!\"}");
                break;
        }
    }

    // captainName setter.
    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }
}
