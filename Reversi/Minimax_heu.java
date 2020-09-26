import java.util.*;


public class Minimax_heu {
	private int searchDepth;
	String player;
	String opponent;
	Board board;

	Hashtable<String, Integer> board_values = new Hashtable<String, Integer>();


	
	public Minimax_heu (Board board, String player, int searchDepth) {
		this.searchDepth=searchDepth;
		this.player=player;
		this.board = board;
		if (player.equals("x")) {
			this.opponent = "o";
		} else {
			this.opponent = "x";
		}
		valuedBaord(board_values);
	}
	
	
	public Board MinimaxDecision()
	{
		
		return maxValue(this.board,Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
       
	}
	
	public Board maxValue(Board board, int a, int b, int depth) {
		
		
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
                if(estimatedUtility(opponentBoard, board_values, this.player) > estimatedUtility(maxBoard, board_values, this.player))
                {
                    maxBoard = opponentBoard;
                }
                
              //AB Pruning
                a = Integer.max(a, estimatedUtility(opponentBoard, board_values, this.player));
                if(b <= a)
                {
                    break;
                }
            }
            
            return maxBoard;
        }

	}
	
	
	public Board minValue(Board board, int a, int b, int depth) {
 
		
		ArrayList<Board> children = board.getChildren(board.grid, board.h, board.black_set, board.white_set, board.empty_set, opponent);

		if(searchDepth == depth ||children.isEmpty())
        {
			return board;
        } else {
        	Board minBoard= children.get(0);
    		
            for (Board child : children)
            {
                Board opponentBoard = maxValue(child, a, b, depth + 1);
                if(estimatedUtility(opponentBoard, board_values, this.player) < estimatedUtility(minBoard, board_values, this.player))
                {
                    minBoard = opponentBoard;
                }
              //AB Pruning
                b = Integer.min(b, estimatedUtility(opponentBoard, board_values, this.player));
                if(b <= a)
                {
                    break;
                }
            }
            return minBoard;	
    	}
      }
	
	
	public void valuedBaord(Hashtable<String, Integer> table){
		table.put("11", 99);table.put("12", -8);table.put("13", 8);table.put("14", 6);
		table.put("15", 6);table.put("16", 8);table.put("17", -8);table.put("18", 99);
		table.put("21", -8);table.put("22", -24);table.put("23", -4);table.put("24", -3);
		table.put("25", -3);table.put("26", -4);table.put("27", -24);table.put("28", -8);
		table.put("31", 8);table.put("32", -4);table.put("33", 7);table.put("34", 4);
		table.put("35", 4);table.put("36", 7);table.put("37", -4);table.put("38", 8);
		table.put("41", 6);table.put("42", -3);table.put("43", 4);table.put("44", 0);
		table.put("45", 0);table.put("46", 4);table.put("47", -3);table.put("48", 6);
		table.put("51", 6);table.put("52", -3);table.put("53", 4);table.put("54", 0);
		table.put("55", 0);table.put("56", 4);table.put("57", -3);table.put("58", 6);
		table.put("61", 8);table.put("62", -4);table.put("63", 7);table.put("64", 4);
		table.put("65", 4);table.put("66", 7);table.put("67", -4);table.put("68", 11);
		table.put("71", -8);table.put("72", -24);table.put("73", -4);table.put("74", -3);
		table.put("75", -3);table.put("76", -4);table.put("77", -24);table.put("78", -8);
		table.put("81", 99);table.put("82", -8);table.put("83", 8);table.put("84", 6);
		table.put("85", 6);table.put("86", 8);table.put("87", -8);table.put("88", 99);
	}
	
	public int estimatedUtility(Board board, Hashtable<String, Integer> board_values, String player) {
		Hashtable<String, String> h =board.getH();
		Set<String> keys = h.keySet();
		int utility = 0;
		for(String key:keys) {
			if (h.get(key).equals(player)) {
				utility = utility + board_values.get(key);
			}
		}
		return utility;
	}

}
