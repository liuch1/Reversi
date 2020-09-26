import java.util.ArrayList;
import java.util.Set;

public class Minimax_ab {

	private int searchDepth;
	String player;
	String opponent;
	Board board;
	Set<String> black_set;
	Set<String> white_set;
	Set<String> empty_set;

	
	public Minimax_ab (Board board, String player, int searchDepth) {
		this.searchDepth=searchDepth;
		this.board = board;
		this.player=player;
		if (player.equals("x")) {
			this.opponent = "o";
		} else {
			this.opponent = "x";
		}
		this.black_set = board.black_set;
		this.white_set = board.white_set;
		this.empty_set = board.empty_set;
	}
	
	public Board MinimaxDecision()
	{
           return maxValue(this.board,Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
   
	}
	
	public Board maxValue(Board board, int a, int b, int depth) {
		this.black_set = board.black_set;
		this.white_set = board.white_set;
		this.empty_set = board.empty_set;
		
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
                Board opponentBoard = minValue(child,a, b, depth + 1);
                if(opponentBoard.getUtility(this.player) > maxBoard.getUtility(this.player))
                {
                    maxBoard = opponentBoard;
                }
                
              //AB Pruning
                a = Integer.max(a, opponentBoard.getUtility(this.player));
                if(b <= a)
                {
                    break;
                }
            }
            
            return maxBoard;
        }

	}
	
    public Board minValue(Board board, int a, int b, int depth) {
    	this.black_set = board.black_set;
		this.white_set = board.white_set;
		this.empty_set = board.empty_set;
		
		ArrayList<Board> children = board.getChildren(board.grid, board.h, board.black_set, board.white_set, board.empty_set, opponent);

		if(searchDepth == depth ||children.isEmpty())
        {
            return board;
        } else {
        	Board minBoard= children.get(0);
    		
            for (Board child : children)
            {
                Board opponentBoard = maxValue(child, a, b, depth + 1);
                if(opponentBoard.getUtility(this.player) < minBoard.getUtility(this.player))
                {
                    minBoard = opponentBoard;
                }
              //AB Pruning
                b = Integer.min(b, opponentBoard.getUtility(this.player));
                if(b <= a)
                {
                    break;
                }
            }
            return minBoard;	
    	}
        }
	

}
