package player;


import game.COLOR;
import player.impl.EdgeEddie;
import player.impl.RandomRichard;
import player.impl.MinimizingMaria;
import player.impl.TerribleTerry;


public class PlayerFactory {



    public final static String[] availablePlayers = {"EdgeEddie", "RandomRichard", "MinimizingMaria", "TerribleTerry"};


    public Player newPlayer(String name, COLOR color) {
        Player player = null;

        switch (name) {
            case "EdgeEddie":       return new EdgeEddie(color);
            case "RandomRichard":   return new RandomRichard(color);
            case "MinimizingMaria":     return new MinimizingMaria(color);
            case "TerribleTerry":   return new TerribleTerry(color);
        }

        return player;
    }
}
