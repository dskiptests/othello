package othello.player.remote;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import othello.game.Color;
import othello.game.GameBoard;
import othello.game.Position;
import othello.player.Agent;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public abstract class RemoteAgent extends Agent {
    private static final String CONTENT_TYPE = "application/json";
    private final ObjectMapper mapper;
    private final HttpClient httpClient;

    public RemoteAgent(final Color color) {
        super(color);
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .proxy(ProxySelector.getDefault())
                .build();
    }

    public final void remoteNewGame() {
        try {
            final String requestBody = this.mapper.writeValueAsString(new NewGameRequestBody(color()));
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.path() + "new-game/"))
                    .header("Content-Type", CONTENT_TYPE)
                    .headers("Authorization", this.key())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            final HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean notHttp200(HttpResponse<String> response) {
        if(response.statusCode() == 200) {
            return false;
        }
        return true;
    }

    @Override
    public final void newGame() {
        this.remoteNewGame();
    }

    @Override
    public final Position nextMove(final GameBoard currentBoard, final List<Position> currentLegalPositions) {
        return this.remoteNextGame();
    }

    public final Position remoteNextGame() {

        try {
            final String requestBody = this.mapper.writeValueAsString(
                    new NextMoveRequestBody(this.currentBoard, this.currentLegalPositions, this.color(), availableTime()));
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.path() + "next-move/"))
                    .header("Content-Type", CONTENT_TYPE)
                    .headers("Authorization", this.key())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            final HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(notHttp200(response)) {
                return null;
            }

            NextMoveResponseBody nextMoveResponseBody = this.mapper.readValue(response.body(), NextMoveResponseBody.class);

            return Position.create(nextMoveResponseBody.row(), nextMoveResponseBody.column())
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    protected String key() {
        return null;
    }

    protected abstract String path();

}
