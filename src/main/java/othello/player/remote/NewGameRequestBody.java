package othello.player.remote;

import othello.game.Color;

import java.time.Instant;

public record NewGameRequestBody(Color color) {


}