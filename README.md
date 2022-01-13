# Seas of Attrition
Seas of Attrition is a CLI game programmed in Java. It was made as a final project for COMP1030G - Programming Fundamental, which enforced the use of various OOP concepts such as encapsulation, inheritance, and abstraction. Fundamental programming concepts such as do while loops, wrapper classes, explicit exception handling, static methods, and multi-dimensional array operations.

This project was developed since October 16th, 2021 and completed on November 22nd, 2021. On January 12th, 2022, additional amendments to the project were created to enforce OOP.

## Why I Revisited the Project
I revisited the Seas of Attrition project on January 2022 after attending the first class of my second semester Java course. I wanted to brush up on my Java skills again and found improving my game and its conformity with OOP would be a great start. For one, I felt a little odd about not implementing a class for _Map_ objects and using _Random_ to store my static methods which I copied and pasted into various files. Due to the rubric limitations on the project (maximum number of static methods being a big one), I had to postpone some of these changes for a later time.

I am still proud of what I made in the month headstart I gave myself to complete this project. I recall getting up everyday, doing every other class as normal and then spending teh rest of the time sinking in hours on Seas of Attrition. I became passionate about it, and maybe it was something more than just programming. It was being able to be creative, adding things like "dramatic console printing", a legitamately agressive and unfair game mechanic (attrition), and text-based cutscenes to make the final product stand out.

## Description
Seas of Attrition is a unique spin on Battleship that is designed to be unfair and show the impact of a strategic disadvantage. 

The game rules are like Battleship but with a caveat for the player... 

At the beginning of the game, the player and AI place 4 different ships on a 10x10 grid. These ships are the **GALLEON**, **FRIGATE**, **SCHOONER**, and **CUTTER** convoys. Respectively, they take up 6, 5, 4, and 3 squares on the 10x10 grid. These ships cannot be placed diagonally and cannot intersect each other. 

After the ships are placed, the player is guaranteed to go first. The player has a limited and random number of shots on their turn based on the difficulty they chose before beginning the game (2-5 on Normal, 1- 3 on Hard, 0-3 on Realistic). 

The player takes each of their shots one at a time and are told whether they missed or hit an enemy ship. 

After the player runs out of ammunition, the AI player is allowed to make a guaranteed 5 shots against the player. Based on how its logic works, the AI also evaluates their shots one by one. The game between the player and AI continues until:

<ol type="a">
  <li>The player has no more ships.</li>
  <li>The enemy has no more ships. </li>
  <li>The player has less than 3 ships or 15 rounds have passed, in which case the AI is allowed to make every shot a guaranteed hit.</li>
</ol>

## Goal
The player’s goal in Seas of Attrition is to achieve a victory by sinking all the enemy ships, even with a disadvantage in firepower. They will need to implement deductive reasoning and a reliable strategy to ensure every shot their mark against the enemy. Every. Shot. Counts.
