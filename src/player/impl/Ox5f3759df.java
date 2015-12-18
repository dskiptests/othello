package player.impl;

import java.util.LinkedList;

import game.Position;
import player.Board;
import player.Player;
import game.COLOR;

public class Ox5f3759df extends Player
{
    private COLOR oppCOLOR;

	class Node
	{
		Node parent = null;
		Position pos = null;
		long visits = 0;
		double reward = 0;
		Node[] children = null;
		
		Node()
		{}
		
		Node(Node parent, Position pos)
		{
			this.parent = parent;
			this.pos = pos;
		}
		
		Node(Node parent, Position pos, long visits, double reward)
		{
			this.parent = parent;
			this.pos = pos;
			this.visits = visits;
			this.reward = reward;
		}
		
		double avgReward()
		{
			return reward/visits;
		}
		
		//upper confidence bound
		double ucb()
		{
			return avgReward() + Math.sqrt(Math.log(parent.avgReward()));
		}
		
		public boolean expanded()
		{
			return children != null;
		}
	}
	
	public Ox5f3759df(game.COLOR color)
	{
		super(color);
	}

	@Override
	public void newGame()
	{
        if (this.COLOR == game.COLOR.BLACK) {
            this.oppCOLOR = game.COLOR.WHITE;
        } else {
            this.oppCOLOR = game.COLOR.BLACK;
        }
	}

	@Override
	public Position nextMove()
	{
		Node root = new Node();
		
		return nextMove(currentBoard, root, System.currentTimeMillis()+1500);
	}
	
	private static game.COLOR reverseColor(game.COLOR color) {
		if (color == game.COLOR.BLACK)
			return game.COLOR.WHITE;
		else
			return game.COLOR.BLACK;
	}

	private Position nextMove(Board rootBoard, Node root, long timeout)
	{
		while (timeout < System.currentTimeMillis())
		{
			Board board = rootBoard.copy();
			Node node = root;
			game.COLOR currentColor = COLOR;
			
			while (!board.gameIsFinished())
			{
				if (!node.expanded()) //expand?
				{
					LinkedList<Position> moves = board.getAllLegalMoves(currentColor);
					if (moves == null || moves.isEmpty())
						node.children = new Node[1]; //null, can't move
					else
					{
						node.children = new Node[moves.size()];
						int i=0;
						for (Position move : moves)
							node.children[i++] = new Node(node, move);
					}
				}
				
				node = select(board, node);
				currentColor = reverseColor(currentColor);
			}
			
			//simulate
			double score = evaluate(board);
			
			backpropagate(board, node, score);
		}
		
		return select(rootBoard, root).pos;
	}
	
	private void backpropagate(Board board, Node node, double score)
	{
		do
		{
			node.visits++;
			node.reward += score;
			node = node.parent;
		}
		while (node != null);
	}

    // returns end game evaluation (our score) (normalized to 0.0-1.0)
    private double evaluate(Board board) {

        double myScore, oppScore, numEmpty;
        myScore = oppScore = numEmpty = 0.0d;

        COLOR[][] cm = board.getColorMatrix();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cm[i][j] == game.COLOR.EMPTY) {
                    numEmpty++;
                } else if (cm[i][j] == this.COLOR) {
                    myScore++;
                } else {
                    oppScore++;
                }
            }
        }

        if (myScore < oppScore) {
            return myScore/64;
        } else if (myScore > oppScore) {
            return (myScore + numEmpty)/64;
        } else if (myScore == oppScore) {
            return myScore/64;
        }
        return 0.0d;
    }

    // returns reward for current board state (not normalized)
    private double evaluateMove(Board board) {
        double myScore, oppScore;
        myScore = oppScore = 0.0d;

        int[][] weights = {{20, -3, 11, 8, 8, 11, -3, 20},
                {-3, -7, -4, 1, 1, -4, -7, -3},
                {11, -4,  2, 2, 2,  2, -4, 11},
                { 8,  1,  2,-3,-3,  2,  1,  8},
                { 8,  1,  2,-3,-3,  2,  1,  8},
                {11, -4,  2, 2, 2,  2, -4, 11},
                {-3, -7, -4, 1, 1, -4, -7, -3},
                {20, -3, 11, 8, 8, 11, -3, 20}};

        COLOR[][] cm = board.getColorMatrix();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cm[i][j] == this.COLOR) {
                    myScore += weights[i][j];
                } else if (cm[i][j] == this.oppCOLOR) {
                    oppScore += weights[i][j];
                }
            }
        }
        return myScore-oppScore;
    }

	private Node select(Board board, Node node)
	{
		Node best = node.children[0];
		double bestUCB = best.ucb();
		for (int i=1; i<node.children.length; i++)
		{
			double ucb = node.children[i].ucb();
			if (bestUCB < ucb)
			{
				best = node.children[i];
				bestUCB = ucb;
			}
		}
		return best;
	}
}
