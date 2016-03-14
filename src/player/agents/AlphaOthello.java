package player.agents;

import java.util.LinkedList;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

public class AlphaOthello extends Agent {
	
	int max_depth = 11;
	int plays = 0;
	long time = 0;
	
	public AlphaOthello(Color color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void newGame() {
		// TODO Auto-generated method stub
		plays = 0;
	}

	@Override
	public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
		Position[] pos = new Position[1]; 
		Integer[] alpha, beta;
		alpha = new Integer[]{Integer.MIN_VALUE};
		beta = new Integer[]{Integer.MAX_VALUE};
		max_depth = Math.max(4,plays);
		if(max_depth > 13)
			max_depth = 13;
		maxValue(alpha, beta, 1, board, color, pos);
		plays++;
		return pos[0];
	}
	
	public Color swap(Color c){
		if(c == Color.BLACK)
			return Color.WHITE;
		else
			return Color.BLACK;
	}
	
	public int maxValue(Integer[] alpha, Integer[] beta, int depth, GameBoard board, Color player, Position[] best_move){
	    if(board.gameIsFinished()){
	        return utility(board);
	    } else if(depth == max_depth){
	        return utility(board);
	    }
	    
	    LinkedList<Position> currentLegalPositions = board.getAllLegalPositions(player);
	    int max = Integer.MIN_VALUE;
	    for(Position pos : currentLegalPositions) {
	    	GameBoard newBoard = board.copyBoard();
	    	newBoard.placeDisk(player, pos);
	        int new_val = minValue(alpha,beta,depth+1,newBoard,swap(player));
	        if(max < new_val){
	            max = new_val;
	            if(depth == 1){
	            	best_move[0] = pos;
	            }
	        }
	        if(max >= beta[0]){
	            return max;
	        }
	        if(alpha[0] < max){
	            alpha[0] = max;
	        }
	    }
	    return max;
	}
	
	public int minValue(Integer[] alpha, Integer[] beta, int depth, GameBoard board, Color player){
	    if(board.gameIsFinished()){
	        return utility(board);
	    } else if(depth == max_depth){
	        return utility(board);
	    }
	    
	    LinkedList<Position> currentLegalPositions = board.getAllLegalPositions(player);
	    int min = Integer.MAX_VALUE;
	    if(currentLegalPositions.size() == 0)
	    	return 0;
	    for(Position pos : currentLegalPositions) {
	    	GameBoard newBoard = board.copyBoard();
	    	newBoard.placeDisk(player, pos);
	        int new_val = maxValue(alpha,beta,depth+1,newBoard,swap(player),null);
	        if(min > new_val){
	            min = new_val;
	        }
	        if(min <= beta[0]){
	            return min;
	        }
	        if(beta[0] > min){
	            beta[0] = min;
	        }
	    }
	    return min;
	}
	
	public int utility(GameBoard state){
		if(color == Color.BLACK)
			return state.getNumberOfDisksInColor(color);// - state.getNumberOfDisksInColor(Color.WHITE);
		else
			return state.getNumberOfDisksInColor(color);// - state.getNumberOfDisksInColor(Color.BLACK);
	}

}
