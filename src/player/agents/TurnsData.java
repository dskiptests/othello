package player.agents;

import game.Position;


public class TurnsData {

    public int CoinsFlipped;
    public int Depth;
    public Position FirstMove;

    public TurnsData(Position firstMove){
        CoinsFlipped = 0;
        Depth = 0;
        FirstMove = firstMove;
    }

}