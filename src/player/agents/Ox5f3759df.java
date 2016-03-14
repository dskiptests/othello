package player.agents;

import java.util.LinkedList;
import java.util.Random;

import game.GameBoard;
import game.Position;
import player.Agent;
import game.Color;

public class Ox5f3759df extends Agent
{
	private Color oppCOLOR;
	private Random rand = new Random();

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

	public Ox5f3759df(Color color)
	{
		super(color);
	}

	@Override
	public void newGame()
	{
		if (this.color == Color.BLACK) {
			this.oppCOLOR = Color.WHITE;
		} else {
			this.oppCOLOR = Color.BLACK;
		}
	}

	@Override
    public Position nextMove(GameBoard currentBoard, LinkedList<Position> currentLegalPositions) throws InterruptedException
	{
		Node root = new Node();

		return nextMove(currentBoard, root, System.currentTimeMillis()+1500);
	}

	private static Color reverseColor(Color color) {
		if (color == Color.BLACK)
			return Color.WHITE;
		else
			return Color.BLACK;
	}

	private Position nextMove(GameBoard rootBoard, Node root, long timeout)
	{
		while (timeout > System.currentTimeMillis())
		{
			GameBoard board = rootBoard.copyBoard();
			Node node = root;
			Color currentColor = color;

			while (!board.gameIsFinished())
			{
				boolean wasExpanded = node.expanded();
				if (!node.expanded()) //expand?
				{
					LinkedList<Position> moves = board.getAllLegalPositions(currentColor);
					if (moves == null || moves.isEmpty())
						node.children = new Node[]{new Node(node, null)}; //null move, can't move
					else
					{
						node.children = new Node[moves.size()];
						int i=0;
						for (Position move : moves)
							node.children[i++] = new Node(node, move);
					}
				}

				node = select(board, node);

				if (!wasExpanded)
					break;

				//selection
				if (node.pos != null)
				{
					if(board.isLegalMove(currentColor, node.pos)) {
						board.placeDisk(currentColor, node.pos);
					} else throw new RuntimeException("invalid move?");


				}
				currentColor = reverseColor(currentColor);
			}

			//simulate
			double score = evaluate(board);

			backpropagate(board, node, score);
		}

		return select(rootBoard, root).pos;
	}

	private double playout(GameBoard board, Color currentColor)
	{
		int i = 0;
		while (!board.gameIsFinished() || i > 60)
		{
			LinkedList<Position> moves = board.getAllLegalPositions(currentColor);
			if (moves != null && !moves.isEmpty())
			{
				if(board.isLegalMove(currentColor, moves.get(rand.nextInt(moves.size())))) {
					board.placeDisk(currentColor, moves.get(rand.nextInt(moves.size())));
				} else throw new RuntimeException("invalid move?");


			}
			currentColor = reverseColor(currentColor);
			i++;
		}
		return evaluate(board);
	}

	private char colorChar(Color c)
	{
		return c== color.BLACK?'x':c== color.WHITE?'o':'.';
	}

	private void backpropagate(GameBoard board, Node node, double score)
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
	private double evaluate(GameBoard board) {

		double myScore, oppScore, numEmpty;
		myScore = oppScore = numEmpty = 0.0d;

		Color[][] cm = board.getBoardMatrix();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (cm[i][j] == Color.EMPTY) {
					numEmpty++;
				} else if (cm[i][j] == this.color) {
					myScore++;
				} else {
					oppScore++;
				}
			}
		}

		if (myScore < oppScore) {
			return myScore/64;
		} else if (myScore > oppScore) {
			return (myScore)/64;
		} else if (myScore == oppScore) {
			return myScore/64;
		}
		return 0.0d;
	}

	// returns reward for current board state (not normalized)
	private double evaluateMove(GameBoard board) {
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

		Color[][] cm = board.getBoardMatrix();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (cm[i][j] == this.color) {
					myScore += weights[i][j];
				} else if (cm[i][j] == this.oppCOLOR) {
					oppScore += weights[i][j];
				}
			}
		}
		return myScore-oppScore;
	}

	private Node select(GameBoard board, Node node)
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
