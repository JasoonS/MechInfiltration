Mech Infiltration

General gameplay:
two phases:
1. set up your playing field. You have THREE belts to place on gears. You have to place your belts ONE SPACE DIAGONALLY, no other possition works. On completion can continue to the second phase.
2. use your arrow keys to move the character. Use the space bar to jump on and use the conveyor belts when you are on/at one. Aim is to get to the key on the otherside without capture.

Part 1:
AI - I have implemented the vanilla A* algorithm for all of the enemies in my game.
You can see this path that they choose by the differently coloured lines that are drawn to the target.
[the core of my A* algorithm can be found in the classes 'jason.smythe.uct.csc2003.path_finding.AStarNode' and 'jason.smythe.uct.csc2003.desktop.maps.MapGrid']

Please see my moddel sollutions (ModelSollution1.png & ModelSollution2.png) to the levels if you get stuck. (sometimes it is worth just waiting, in an attempt to 'distract' the enemies).

notes about my algorithm implementation:
-doesn't use a closed list rather looks at the state of the given cell.
-uses a priority queue for the open list.
-has two lists that are used to 'clean up' the data during repeated use:
--one list is used to set all cells that were set to closed/open back to unvisited.
--the other list is used for breadcrumb/heatMap reset (see part 2 for details).
-Algorithm uses the Manhattan heuristic.


Part 2:
(section 1)
I implemented a breadCrumb/heatMap algorithm to prompt enemies to choose different paths 
Please 'play' with this by selecting the 'PATH FINDING SAND BOX' option.
You can:
--adjust the intensity of the breadCrumms/heatMap.
--move the player around.
--reset the scene.
--get the enemies to chase on their paths.
--stop/freeze all entities from moving.

Understanding my bread crumb system:
Each enemy calculates the shortest path in orrder. The first enemy to calculate its path is the enemy with the thickest line, and then in order they are calculated. BreadCrumbing 'lengthens' the edges between the nodes each time they are used as the final path for an enemy. So you can clearly see that The thickest lined enemy takes the most effecient route, and the thin lined enemy very often will not.

(section 2)
There are 3 types of enemy in this game. 
-The first is the standard enemy, that just chases the player as directly as possible.
-The second is a guard, that Chases the player but doesn't get too close, he rather tries to stay inbetween the player and the goal/key. Only when the player is very close does he go in for the attack.
-The third is a cautious attacker. He aims to stay inbetween you and the goal/key, but if you get closer, he will attack you directly.

The way the 'inbetween' position is calculated, a line is drawn from the player to the key/goal (drawn in white). Halfway point of that line is taken (drawn as white target), and the closest grid square to the midpoint became the target (drawn as either RED or BLUE targets depending on the player).

So there is also AI involved in deciding whether to 'attack' or 'guard' for these two enemies.
