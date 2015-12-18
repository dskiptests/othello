package player;


import game.COLOR;
import player.impl.*;


public class PlayerFactory {



    public final static String[] availablePlayers = {"EdgeEddie", "RandomRichard", "MinimizingMaria", "TerribleTerry" ,"ForThello"};


    public Player newPlayer(String name, COLOR color) {
        Player player = null;

        switch (name) {
            case "ForThello" : return new ForThello(color);
            case "EdgeEddie":       return new EdgeEddie(color);
            case "RandomRichard":   return new RandomRichard(color);
            case "MinimizingMaria":     return new MinimizingMaria(color);
            case "TerribleTerry":   return new TerribleTerry(color);
        }

        return player;
    }
}
