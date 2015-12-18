package player;


import game.COLOR;
import player.impl.EdgeEddie;
import player.impl.Ox5f3759df;
import player.impl.RandomRichard;
import player.impl.MinimizingMaria;
import player.impl.TerribleTerry;


public class PlayerFactory {



    public final static String[] availablePlayers = {"EdgeEddie", "RandomRichard", "MinimizingMaria", "TerribleTerry", "0x5f3759df"};


    public Player newPlayer(String name, COLOR color) {
        Player player = null;

        switch (name) {
            case "EdgeEddie":       return new EdgeEddie(color);
            case "RandomRichard":   return new RandomRichard(color);
            case "MinimizingMaria":     return new MinimizingMaria(color);
            case "TerribleTerry":   return new TerribleTerry(color);
            case "0x5f3759df" :     return new Ox5f3759df(color);
        }

        return player;
    }
}
