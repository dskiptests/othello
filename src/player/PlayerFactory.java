package player;


import game.COLOR;
import player.impl.EdgeEddie;
import player.impl.MinimizingMaria;
import player.impl.RandomRichard;
import player.impl.TerribleTerry;
import player.impl.chattanooga_flowmasters.ChattanoogaPlayer;


public class PlayerFactory {





    public final static String[] availablePlayers = {"DeepOthello", "EdgeEddie", "RandomRichard", "MinimizingMaria", "TerribleTerry", "Skumtomtarna","ChattanoogaFlowmasters"};


    public Player newPlayer(String name, COLOR color) {
        Player player = null;

        switch (name) {
            case "EdgeEddie":
                return new EdgeEddie(color);
            case "RandomRichard":
                return new RandomRichard(color);
            case "MinimizingMaria":
                return new MinimizingMaria(color);
            case "TerribleTerry":
                return new TerribleTerry(color);
            case "DeepOthello":
                return new TerribleTerry(color);
            case "Skumtomtarna":
                return new Skumtomtarna(color);
            case "ChattanoogaFlowmasters":
                return new ChattanoogaPlayer(color);
        }

        return player;
    }
}
