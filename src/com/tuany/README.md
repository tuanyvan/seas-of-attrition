# Main.java
This class is run upon execution and calls the .start() function from the _Menu_ class. 

# Menu.java
This class prints the Seas of Attrition title and logo. It also plays the first few cutscenes of the game (exposition/introduction, George Washington’s introduction).

The end goal of this class is to inform the player of the game’s rules and plot, let the user select the difficulty they wish to play on, and store the user’s name for later use in various classes. 

At the end of the class, it instantiates the Game class using the difficulty settings, player name, and whether they want to play the exposition. After that, it calls the .start() function from the instantiated _Game_ class. 

# Game.java
The _Game_ class contains the entire Seas of Attrition gameplay.

It contains state about the player (minimum and maximum shots, name), maps for the player and AI (a third including the player’s shot tracking map), and variables to determine logical decisions (number of remaining player/enemy ships, round count, total shots made versus shots lost to attrition). 

It also contains methods to receive coordinate input from a player, uses static methods for randomization functions (Random.java), processes whether a player/AI shot was a hit or miss, and ensures the player doesn’t make repeat or out-of-bounds shots on their turn. 

The _Game_ class also instantiates and makes frequent function calls from the _ComputerPlayer_ class. _Game_ will handle the AI’s game decisions and make their actions affect its state. At the end of the class, it prints the player/AI statistics and allows the player to terminate the game. 

# ComputerPlayer.java
This class contains the state and behavior for AI logic and decisions. It is completely encapsulated from the user and abstracted away from the _Game_ class, meaning its decisions cannot directly be affected by the player or the _Game_ class.

Every time the _Game_ class calls its .pollForShots() function, it will return five coordinates and change its state based on the impact of those coordinates on the player.

It uses the following strategy to determine how to change its state and logic when it is polled for shots: 

a) If the AI has no coordinates ‘in memory’, choose a random square and add it to the list of decisions. 

b) If that randomly chosen square was a hit, commit the coordinate to memory and then choose a random direction to fire in for the next shot. 

c) The AI can proceed past c) if it has a shot and direction in memory. 

d) If the direction that was randomly chosen was a miss, add it to the list of decisions and choose the next clockwise cardinal direction. e) Otherwise, if the direction was a hit, commit the direction to memory. 

f) When the AI has a coordinate and direction committed to memory, shoot in that direction relative to the coordinate memory +1 squares further than the last shot (this is what stepOver does). 

g) Upon reaching a miss or a square that was already shot at, try the other direction in the same axis and follow f) if it hasn’t already.

h) Once it is satisfied it has run down the length of an entire ship, it can expunge its memory and restart from a). 

This AI implementation can take down ships that are obviously lined up together (for example, a GALLEON and SCHOONER put together from B1 to B10 will be recognized by the AI). It also attempts to systematically take down the entire ship’s length rather than just a few squares here and there. The AI has access to the player’s map, player ships remaining, and round count thanks to the use of its setters by the _Game_ class. It does not use its omniscience to cheat. 

If the AI lasts long enough to see the player with less than 3 ships or past Round/Hour 15, the AI’s next shots will be 100% accurate to forcefully end the game, preventing a slow death or deliberate delay by the player. 

# Cutscenes.java
This class contains the captain’s name as state, and contains all the cutscenes and random event messages, consolidating very long print statements into overloaded methods. It makes frequent use of the DramaPrinter class to print character dialogue, narration, cutscenes, and event messages. 

It is more organized to have all these print statements in one class and enforces abstraction by having complex code handled by another class. Centralizing all the cutscenes into one class makes it easier to see what the actual cutscene being played is, especially during refactoring. 

For example, instead of writing the drawn-out print statement for a victory in Game, that statement can be written in _Cutscenes_ and called on using .playCutscene(“VICTORY”). 

When refactoring and debugging, this makes it so you don’t have to read the entire print statement to check if it's right, you just need to make sure that a “VICTORY” is being played by the _Cutscenes_ class. 

The .playCutscene() function is overloaded, taking an Integer for random event messages (works well with a random Integer function) and a String for drawn-out cutscenes (played throughout the game).

The only thing other classes have to do to print a cutscene is call the .playCutscene() function from _Cutscenes_. That’s it. No need to have the paragraphs of text in its code, just a simple function call. 

# DramaPrinter.java 
This class contains a public method called .dramaPrint(), which prints out a String character by character. It uses a single state variable, delay, to control the speed at which each character is printed. 

In command line games, it is difficult to make a good reading experience when text is printed all at once. The user feels overwhelmed as must stop and read the walls of text as they appear. 

By printing text letter by letter (or as I call it, drama printing), it allows the user to keep up with the program and focus in on the text being written. 

Pauses using the drama printer can also create suspense, something not otherwise available using the regular print statements.

Dialogue also feels more like speaking when it isn’t printed out all at once. As well, the code for this type of behavior would be a nightmare to copy and paste for every String and cutscene. Centralizing this method into a separate class enforces abstraction and reduces code complexity in the long run.

# New Classes made in January Revisions
I revisted this project in January to refresh my skills in Java and to improve the code. I wanted to enforce more OOP and to reduce the amount of bloat inside the _Game_ class. The following are what was added.

## Map.java
This class contains the state, getters, and setters for a map. Maps are used in gameplay to track the player/AI ships and shot placement.

The behavior in this class was previously a part of the _Game_ class, which caused the file to be very bloated with methods simply for filling a map or checking a grid square.

By moving the behavior into objects that inherit those functions, the complexity of the _Game_ class' code can be reduced. This implementation is much more readable and also enforce encapsulation.

## Random.java
This class contains a public static method which is also overloaded. The first method takes an inclusive upper and lower bound, and generates a random integer within that range. The overloaded method takes only an upper bound, and generates a random integer from 0 to the inclusive upper bound.
