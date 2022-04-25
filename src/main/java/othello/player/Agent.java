package othello.player;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import othello.game.Color;
import othello.game.GameBoard;
import othello.game.Position;
import othello.player.game.ServerCall;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class Agent implements Callable<Position> {

    private static final String URL = "";
    private static final String CONTENT_TYPE = "application/json";
    private final String PLAYER_NAME = this.getClass().getSimpleName();
    private final Color color;
    private final ObjectMapper mapper;
    private final HttpClient httpClient;

    public GameBoard currentBoard;
    public List<Position> currentLegalPositions;
    private String key = "";

    @Override
    public Position call() throws Exception {
        Position position = null;
        try{
            position = nextMove(this.currentBoard, this.currentLegalPositions);
        } catch(Exception e){
            e.printStackTrace();
        }
        return position;
    }

    public Agent(Color color) {
        this.mapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.color = color;
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .proxy(ProxySelector.getDefault())
                .build();
    }


    @Override
    public String toString() {
        return PLAYER_NAME + " (" + color + ")";
    }

    /**
     *  This Method initializes the othello.player when a new othello.game starts. Time slot ~ 4 seconds.
     */

    public final Position callForHelp(String url) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Content-Type", CONTENT_TYPE)
                    .headers("Authorization", this.key)
                    .POST(HttpRequest.BodyPublishers.ofString(this.mapper.writeValueAsString(
                            new ServerCall(this.currentBoard, this.currentLegalPositions, this.color()))))
                    .build();

            final HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return this.mapper.readValue(response.body(), Position.class);
        } catch (Exception e) {
            return null;
        }
    }

    public final String name() {
        return PLAYER_NAME;
    }

    public final Color color() {
        return this.color;
    }

    public abstract void newGame();

    /**
     * This method is called when it is the players turn to make a move. Time slot ~ 2 seconds.
     * If the othello.player is not able to return a move within the given time slot, a random
     * move is choosen from the list of available moves. Any time not used will be added
     * to the next time this method is called.
     *
     * Before this method is called, all global variables are updated.
     *
     * @param board A copy of the current Board. The same variable as the global board in this class.
     * @param currentLegalPositions A copy of the legal positions. Same as the gloal list.
     * @return A choosen position for the next turn.
     * @throws InterruptedException
     */
    public abstract Position nextMove(final GameBoard board, final List<Position> currentLegalPositions) ;
}
