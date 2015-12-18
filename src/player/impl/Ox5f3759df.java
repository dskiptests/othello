package player.impl;

import java.util.LinkedList;

import game.Position;
import player.Board;
import player.Player;
import game.COLOR;

public class Ox5f3759df extends Player
{
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
		
	}

	private double evaluate(Board board)
	{
		return 0;
	}

	private Node select(Board board, Node node)
	{
		return node.children[0];
	}
}
