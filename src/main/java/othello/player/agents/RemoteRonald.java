package othello.player.agents;

import othello.game.Color;
import othello.player.remote.RemoteAgent;

public class RemoteRonald extends RemoteAgent {

    public RemoteRonald(final Color color) {
        super(color);
    }


    @Override
    protected String path() {
        return "http://localhost:8080/othello/";
    }

}

