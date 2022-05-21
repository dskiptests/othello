# Othello AI Competition
Version 1.1

## Prerequisites
 * Java 17
 * Maven and/or Eclipse/IntelliJ

## Get started

### Maven 
 1. Check out the code: `$ git clone https://github.com/davstromb/othello.git`
 2. Go to the root directory of this project
 3. Build the project by typing `$ mvn clean install`
 4. Start the application by either running `$ java -jar target/othello.jar` or with Maven: `mvn exec:java -Dexec.mainClass=main.othello.Othello`

 
### IntelliJ IDEA
 1. Check out the code: `$ git clone https://github.com/davstromb/othello.git`
 2. _File_ -> _New_ -> _Project from existing sources_ -> (choose the cloned pom.xml file)
 3. _Next_ -> _..._ -> _Next_ -> _Finish_
 4. Right click on `src/main/othello.Othello.java` -> _Run othello.Othello.main()_

### Eclipse

 1. Check out the code: `$ git clone https://github.com/davstromb/othello.git` 
 2. _File_ -> _New..._ -> _Java Project_
 3. Project name: othello
 4. Don't apply _"Use default location"_, instead you need to specify the catalogue where you cloned the repository
 5. _Finish_
 6. Right click on `src/main/othello.Othello.java` -> _Run as_ -> _Java Application_

## Create an Agent
Create a class in `src/othello/player/agent` and make sures it inherits the class `Agent.java` or `RemoteAgent.java`. The name of the class will be the team name.

## Ranking

Score system

  * Win = 3 points
  * Draw = 1 point
  * Defeat = 0 points 

If two or more teams are equal on points on completion of the group matches, the following criteria are applied in the order given to determine the rankings:  

  1. Superior score difference in all matches 
  2. Superior score difference from the matches played among the agents in question
  3. Higher number of positive scores

## Hints
 * Every move is timed, unused time during a move will be saved to the next move, and so on.
 * If the agent throws an exception or if the agent can return a value on time, the agent is assigned a random move. All saved time will be removed as well.
 * If the agent returns _null_ or an invalid value, a random move will be assigned to the agent. The saved time will not be affected.
 * Time is relative.
 * There are some simple agents implemented already, use them as inspiration.
 * The method `newGame()` is triggered in the beginning of each new othello.game.
 * The method `nextMove()` is triggered when it's your agent's turn.
 * You can't provide any additional Java classes other than the Agent.
 * If you want, you can utilize services outside this application. Feel free to call
a server with the method `callForHelp("<URL>")`.
 * Use the method `availableTime()` to find out how many milliseconds you have left in each turn

 ## Screenshot

 <img src="http://i.imgur.com/RswfbHi.png" alt="Drawing" style="width: 400px;"/>

## Remote Agent
If you're playing with a Remote Agent, the same rules apply *AND* your Java Class can only be 2 kB.
use the method `remoteCall()` in order to call your remote agent. 

Your local agent must extend `RemoteAgent.java`, you can
have a look at `RemoteRonald.java` for an example. If the Agent returns any other HTTP code than 200 OK,
a random move will be selected for you and all saved time will be lost.

If you write your remote agent in Java, you can simply copy some of the Java objects
from this repository.

### Hints for Remote Agents

 * The Java file size can only be 2 kB
 * The Agent has longer time to execute
 * The Remote API must always return `200 OK`, otherwise the move is considered illegal.

### New Game
**Request from Game**
```json
$ curl --location --request POST 'https://<YOUR-HOST>/othello/new-game' \
--header 'Content-Type: application/json' \
--header 'key: <YOUR-SECRET-KEY>' \
--data-raw '{
   "color":"WHITE"
}'
```
The Game sends the color the Agent will have for the upcoming game,
it can be either `BLACK` or `WHITE`.

### Next Move

**Request from Game**
```json
$ curl --location --request POST 'https://<YOUR-HOST>/othello/next-move' \
--header 'Content-Type: application/json' \
--header 'key: <YOUR-SECRET-KEY>' \
--data-raw '{
   "currentBoard":{
      "boardMatrix": [["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY"],
 		["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY"],
				["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY"],
				["EMPTY","EMPTY","EMPTY","WHITE","BLACK","EMPTY","EMPTY","EMPTY"],
				["EMPTY","EMPTY","EMPTY","BLACK","WHITE","EMPTY","EMPTY","EMPTY"],
				["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY"],
				["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY"],
				["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY"]],
      "boardSize":8
   },
   "currentLegalPositions":[
      {
         "row":2,
         "column":4
      },
      {
         "row":3,
         "column":5
      },
      {
         "row":4,
         "column":2
      },
      {
         "row":5,
         "column":3
      }
   ],
   "availableTime": 2000
   "color":"WHITE"
}'
```

The current board is a matrix with cells that can be either
`EMPTY | BLACK | WHITE`, the color of the Agent is also
provided in the message. In order for the Agent to successfully
return a position, it must pick on of the positions in the array
`currentLegalPositions`.


**Response Body**

The Response is `Position.java`
```json
{
    "row": 5,
    "column": 3
}
```
 
  

