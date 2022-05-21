package othello.player.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import othello.game.Color;
import othello.game.GameBoard;
import othello.game.Position;

import java.util.List;

public record NextMoveRequestBody(GameBoard currentBoard, @JsonProperty("possibleMoves") List<Position> currentLegalPositions, Color color,
                                  long availableTime) {

}
