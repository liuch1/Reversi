import java.util.*;

public class Minimax {
	private int searchDepth;
	String player;
	String opponent;
	Board board;

	
	public Minimax (Board board, String player, int searchDepth) {
		this.searchDepth=searchDepth;
		this.board = board;
		this.player=player;
		if (player.equals("x")) {
			this.opponent = "o";
		} else {
			this.opponent = "x";
		}
	}
	
	public Board MinimaxDecision()
	{
           return maxValue(this.board,0);
        
	}
	
	public Board maxValue(Board board,int depth) {
		
		ArrayList<Board> children = board.getChildren(board.grid, board.h, board.black_set, board.white_set, board.empty_set, this.player);
		
		//checks if state is terminal
		if(searchDepth == depth || children.isEmpty())
        {
            return board;
        } else {
        	 //Return first child
            Board maxBoard= children.get(0);
    		
            //recurse through children
            for (Board child : children)
            {
                Board opponentBoard = minValue(child,depth + 1);
                if(opponentBoard.getUtility(this.player) > maxBoard.getUtility(this.player))
                {
                    maxBoard = opponentBoard;
                }
            }
            
            return maxBoard;
        }

	}
	
	public Board minValue(Board board,int depth) {
		
		ArrayList<Board> children = board.getChildren(board.grid, board.h, board.black_set, board.white_set, board.empty_set, opponent);

		if(searchDepth == depth ||children.isEmpty())
        {
            return board;
        } else {
        	Board minBoard= children.get(0);
    		
            for (Board child : children)
            {
                Board opponentBoard = maxValue(child,depth + 1);
                if(opponentBoard.getUtility(this.player) < minBoard.getUtility(this.player))
                {
                    minBoard = opponentBoard;
                }
            }
            return minBoard;	
    	}
        }
		

}
