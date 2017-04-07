# Othello AI Competition


## Prerequisites
 * Java 8
 * Maven and/or Eclipse/IntelliJ

## Get started

### Maven 
 1. Check out the code: `https://github.com/davstromb/othello.git`
 2. Go to the root directory of this project
 3. Build the project by typing `mvn clean install`
 4. Start the application by either running `jar-filen java -jar target/othello-1.0-SNAPSHOT.jar` or with Maven: `mvn exec:java -Dexec.mainClass=main.Othello`

 
### IntelliJ IDEA
 1. Check out the code: `https://github.com/davstromb/othello.git`
 2. _File_ -> _New_ -> _Project from existing sources_ -> (choose the cloned pom.xml file)
 3. _Next_ -> _..._ -> _Next_ -> _Finish_
 4. Right click on `src/main/Othello.java` -> _Run Othello.main()_

###Eclipse

 1. Check out the code: `https://github.com/davstromb/othello.git` 
 2. _File_ -> _New..._ -> _Java Project_
 3. Project name: othello
 4. Don't apply _"Use default location"_, instead you need to specify the catalogue where you cloned the repository
 5. _Finish_
 6. Right click on `src/main/Othello.java` -> _Run as_ -> _Java Application_

## Create an Agent
Create a class in `src/player/agent` and make sures it src/player/agent the class `Player.java`. The name of the agent will be the team name.

## Ranking

Score system

  * Win = 3 points
  * Draw = 1 point
  * Defeat = 0 points 

If two or more teams are equal on points on completion of the group matches, the following criteria are applied in the order given to determine the rankings  

  1. superior score difference in all matches 
  2. superior score difference from the matches played among the agents in question
  3. higher number of positive scores 
  4. Knock-out ping-pong 

## Hints
 * Every move is timed, unused time during a move will be saved to the next move, and so on.
 * If the agent throws an exception or if the agent can return a value on time, the agent is assigned a random move. All saved time will be removed as well.
 * If the agent returns _null_ or an invalid value, a random move will be assigned to the agent. The saved time will not be affected.
 * Time is relative.
 * There are some simple agents implemented already, use them as inspiration.
 * The method `newGame()` is triggered in the beginning of each new game.
 * The method `nextMove()` is triggered when it's your agent's turn.
 
  

