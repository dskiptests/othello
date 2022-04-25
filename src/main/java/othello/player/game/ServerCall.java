package othello.player.game;

import othello.game.Color;
import othello.game.GameBoard;
import othello.game.Position;

import java.util.List;

public record ServerCall(GameBoard currentBoard, List<Position> currentLegalPositions, Color color) {

}
