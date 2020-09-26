
import java.util.*;

public class Outline {
	
	Board board;
	
	public Outline() {}
	
	public void run() {
		//Prompt
		System.out.println("Welcome to Reversi created by Christina Liu and Wei Wu!");
		System.out.println("Choose your game:");
		System.out.println("1. Small 4*4 Reversi");
		System.out.println("2. Medium 6*6 Reversi");
		System.out.println("3. Standard 8*8 Reversi");
		System.out.print("You choose? ");
		Scanner input = new Scanner(System.in);
		String choice1 = input.nextLine();
		
		int N;
		int searchDepth;
		if (choice1.equals("1")){
			N = 4;
			System.out.print("Please specify a search depth (like 4): ");
			searchDepth = input.nextInt();
		} else if (choice1.equals("2")) {
			N = 6;
			System.out.print("Please specify a search depth (like 4): ");
			searchDepth = input.nextInt();
		} else {
			N = 8;
			System.out.print("Please specify a search depth (like 4): ");
			searchDepth = input.nextInt();
		}
		
		//Initialize the board by the specified grids
		board = new Board(N);
		Scanner input2 = new Scanner(System.in);
		Scanner input3 = new Scanner(System.in);
		
		System.out.println("Choose your opponent:");
		System.out.println("1. An agent that plays randomly");
		System.out.println("2. An agent that uses MINMAX");
		System.out.println("3. An agent that uses MINMAX with alpha-beta pruning");
		System.out.println("4. An agent that uses H-MINMAX with a fixed depth cutoff and a-b pruning");
		System.out.print("You choose? ");
		String choice2 = input2.nextLine();
		
		String user = "";
		String AI ="";
		
		do {
			System.out.print("Do you want to play DARK (x) or LIGHT (o)? ");
			String choice3 = input3.nextLine();
			if (choice3.equals("DARK") || choice3.equals("x")){
				user = "x";
				AI = "o";
			} else if (choice3.equals("LIGHT")  || choice3.equals("o") ) {
				user = "o";
				AI = "x";
			} else {
				System.out.println("Your choice is invalid. Please try again.");
			}
		}while (user==null);
		
		
		
		String player="x";
		int user_pass = 0;
		int AI_pass = 0;
		
		do {
			System.out.println();
			board.printBoard();
			int l = board.empty_set.size();
			if (player.equals("x")) {
				System.out.println();
				System.out.println("Next to play: DARK ('x')");
				if (user.equals("x")) {
					action_user(board.getBoard(),user);
					if (board.empty_set.size()!=l) {
						user_pass = 0;
					} else {
						user_pass = 1;
					}
				} else if (AI.equals("x")) {
					long start = System.currentTimeMillis();
					mode(choice2, board.getBoard(), AI, searchDepth);
					// finding the time after the operation is executed
				     long end = System.currentTimeMillis();
				     //finding the time difference and converting it into seconds
				     float sec = (end - start) / 1000F; System.out.println("Time elapsed: "+sec + " seconds");
					if (board.empty_set.size()!=l) {
						AI_pass = 0;
					} else {
						AI_pass = 1;
					}
					
				} 
				player = "o";
				
			} else if (player.equals("o")) {
				System.out.println();
				System.out.println("Next to play: LIGHT ('o')");
				if (user.equals("o")) {
					action_user(board.getBoard(),user);
					if (board.empty_set.size()!=l) {
						user_pass = 0;
					} else {
						user_pass = 1;
					}
				} else if (AI.equals("o")) {
					long start = System.currentTimeMillis();
					mode(choice2, board.getBoard(), AI, searchDepth);
					// finding the time after the operation is executed
				    long end = System.currentTimeMillis();
				     //finding the time difference and converting it into seconds
				    float sec = (end - start) / 1000F; System.out.println("Time elapsed: "+sec + " seconds");
					if (board.empty_set.size()!=l) {
						AI_pass = 0;
					} else {
						AI_pass = 1;
					}
				} 
				player = "x";
			} 
			
			if (user_pass == 1 && AI_pass == 1) {
				break;
			}
			
			System.out.println();
		} while (board.empty_set.isEmpty()==false);
		
		board.printBoard();
		System.out.println();
		if (board.black_set.size()>board.white_set.size()) {
			System.out.println("Winner: DARK");
		} else if (board.black_set.size()<board.white_set.size()) {
			System.out.println("Winner: LIGHT");
		} else {
			System.out.println("Draws");
		}
		System.out.println("\nGame Over");
        
        
	}
	
	public static void mode(String choice2, Board board, String AI, int searchDepth) {
		if (choice2.equals("1")){
			action_randomPlayer(board, AI);
		} else if (choice2.equals("2")) {
			Minimax action_minimax = new Minimax(board, AI, searchDepth); 
			Set<String> minimax_empty_set = action_minimax.MinimaxDecision().empty_set;
			//System.out.println("MINIMAX DECISION:");
			//action_minimax.MinimaxDecision().printBoard();
			Iterator<String> iterator = board.empty_set.iterator();
			while(iterator.hasNext()){
				  String move = iterator.next();
				  if (minimax_empty_set.contains(move)==false) {
					  move = board.numberToAlphabet(move);
					  checkAI(board, AI, move);
					  //System.out.println("The move chosen by MINIMAX is: "+move);
					  //board.updateBoard(move, AI);
					  break;
				  }
			}
		} else if (choice2.equals("3")) {
			Minimax_ab action_minimax = new Minimax_ab(board, AI, searchDepth); 
			Set<String> minimax_empty_set = action_minimax.MinimaxDecision().empty_set;
			Iterator<String> iterator = board.empty_set.iterator();
			while(iterator.hasNext()){
				  String move = iterator.next();
				  if (minimax_empty_set.contains(move)==false) {
					  move = board.numberToAlphabet(move);
					  checkAI(board, AI, move);
					  //System.out.println("The move chosen by MINIMAX-AB is: "+move);
					  //board.updateBoard(move, AI);
					  break;
				  }
			}
		} else {
			Minimax_heu action_minimax_h = new Minimax_heu(board, AI, searchDepth); 
			Set<String> minimax_empty_set_h = action_minimax_h.MinimaxDecision().empty_set;
			//System.out.println("MINIMAX DECISION:");
			//action_minimax_h.MinimaxDecision().printBoard();
			Iterator<String> iterator = board.empty_set.iterator();
			while(iterator.hasNext()){
				  String move = iterator.next();
				  if (minimax_empty_set_h.contains(move)==false) {
					  move = board.numberToAlphabet(move);
					  checkAI(board, AI, move);
					  //System.out.println("The move chosen by H-MINIMAX is: "+move);
					  //board.updateBoard(move, AI);
					  break;
				  }
			}
		}
	}
	
	public static void action_user(Board board, String user) {
		Scanner input_user = new Scanner(System.in);
		Set<String> moves = new HashSet<String>();
		if (user.equals("x")) {
			moves = board.printValidMoves(board.black_set);
			System.out.println("All valid moves are: "+moves);
		} else if (user.equals("o")) {
			moves = board.printValidMoves(board.white_set);
			System.out.println("All valid moves are: "+moves);
		}
		if (moves.size()!=0) {
			System.out.print("Your move (? for help): ");
			String move = input_user.nextLine();
			if (moves.contains(move)) {
				board.updateBoard(move,user);
			} else {
				System.out.println("Invalid input. Please try again.");
				action_user(board, user);
			}

		} else {
			System.out.println("User: PASS");
		}

	}
	
	public static void action_randomPlayer(Board board, String AI) {
		Set<String> all_moves = new HashSet<String>();
		String ai_move = "";
		if (AI.equals("x")) {
			all_moves.addAll(board.printValidMoves(board.black_set));
			ai_move = pickRandom(all_moves);
		} else if (AI.equals("o")){
			all_moves.addAll(board.printValidMoves(board.white_set));
			ai_move = pickRandom(all_moves);
		}
		
		if (ai_move.equals("pass")==false) {
			System.out.println("The move I pick is: "+ai_move);
			board.updateBoard(ai_move, AI);
		} else {
			System.out.println("AI: PASS");
		}
	}
	
	public static String pickRandom(Set<String> set) {
		int size = set.size();
		if (size!=0) {
			int item = new Random().nextInt(size);
			int i = 0;
			List<String> list = new ArrayList<String>(set);
			String r = list.get(0);
			for(String obj : set) {
			    if (i == item) {
			    	r = obj;
			        return r;
			    }
			    i++;
			}
		} 
		return "pass";
	}
	
	public static void checkAI(Board board, String AI, String move) {
		if (AI.equals("x")) {
			Set<String> validSet = board.printValidMoves(board.black_set);
			if (validSet.contains(move)) {
				System.out.println("The move chosen by AI is: "+move);
				 board.updateBoard(move, AI);
			} else {
				action_randomPlayer(board, AI);
				//System.out.println("AI: PASS");
			}
		} else if (AI.equals("o")) {
			Set<String> validSet = board.printValidMoves(board.white_set);
			if (validSet.contains(move)) {
				System.out.println("The move chosen by AI is: "+move);
				 board.updateBoard(move, AI);
			} else {
				action_randomPlayer(board, AI);
				//System.out.println("AI: PASS");
			}
		}
	}
	
	

}
