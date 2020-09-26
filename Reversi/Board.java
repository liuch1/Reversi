
import java.util.*;

public class Board {
	
	String [][] grid;
	//all the coordinates in the table/set below are expressed as "row number+column number". i.e. "32", which represents b3 on board
	Hashtable<String, String> h = new Hashtable<String, String>(); 
	Set<String> black_set = new HashSet<String>();
	Set<String> white_set = new HashSet<String>();
	Set<String> empty_set = new HashSet<String>();
	//String turn = "x";
	int n;
	String [] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r",
			"s","t","u","v","w","x","y","z"};
	String [] number = {"1","2","3","4","5","6","7","8"};
	
	public Board(int N) {
		n = N+2;
		grid = new String [n][n];
		initialBoard();
	}
	
	//Set up a new Reversi board
	public void initialBoard() {
		String character;
		String num; 
		
		for (int i=0; i<n; i++) {
			
			for (int j=0; j<n; j++) {
				
				if (i==0 && j!=0 && j!=n-1) {
					character = alphabet[j-1];
					grid[i][j] = character;
				} else if (i==n-1 && j!=0 && j!=n-1 ) {
					character = alphabet[j-1];
					grid[i][j] = character;
				} else if (j==0 && i!= 0 && i!=n-1) {
					num = number[i-1];
					grid[i][j] = num;
				} else if (j==n-1 && i!= 0 && i!=n-1) {
					num = number[i-1];
					grid[i][j] = num;
				} else {
					grid[i][j] = " ";
				}
			}
			
		}
		
		int center1 = n/2;
		int center2 = center1-1;
		
		grid[center2][center2] = "o";
		String initial1 = String.valueOf(center2)+String.valueOf(center2);
		white_set.add(initial1);
		h.put(initial1, "o");
		
		grid[center1][center1] = "o";
		String initial2 = String.valueOf(center1)+String.valueOf(center1);
		white_set.add(initial2);
		h.put(initial2, "o");
		
		
		grid[center2][center1] = "x";
		String initial3 = String.valueOf(center2)+String.valueOf(center1);
		black_set.add(initial3);
		h.put(initial3, "x");
		
		grid[center1][center2] = "x";
		String initial4 = String.valueOf(center1)+String.valueOf(center2);
		black_set.add(initial4);
		h.put(initial4, "x");
		
		for (int i=1; i<n-1; i++) {
			for (int j=1; j<n-1; j++) {
				String blank = String.valueOf(i)+String.valueOf(j);
				if (blank.equals(initial1)==false && blank.equals(initial2)==false && 
					blank.equals(initial3)==false && blank.equals(initial4)==false) {
					empty_set.add(blank);
				}
			}
		}
		
	}
	
	
	public Hashtable<String, String> getH(){
		return h;
	}
	
	
	public int findIndex(String arr[], String e) 
    { 

        if (arr == null) { 
            return -1; 
        } 

        int len = arr.length; 
        int i = 0; 
  
        while (i < len) { 
  
            if (arr[i].equals(e)) { 
                return i; 
            } 
            else { 
                i = i + 1; 
            } 
        } 
        return -1; 
    } 
	

	
	
	public String alphabetToNumber(String c) {
		String[] arr = c.split("(?!^)"); //{"b", "1"} 
		String first_c = arr[0]; //"b"
		
		int index = findIndex(alphabet, first_c); //index = 1
		
		String num = number[index]; //find 2 in array number
		String newCordinate = arr[1]+num;
		return newCordinate;
	}
	
	public String numberToAlphabet(String e){
		  String[] arr = e.split("(?!^)");
		  String first_c = arr[0];
		  String second_c = arr[1];
		  int index = findIndex(number, second_c);
		  String al = alphabet[index];
		  String newCordinate = al+first_c;
		  
		  
		  return newCordinate;
	}
	
	public Set<String> convertSet(Set<String> set){
		if (set != null) {
			Iterator<String> iterator = set.iterator();
			Set<String> new_set = new HashSet<String>();
		
			
			while(iterator.hasNext()){
			  String element = iterator.next();
			  if (element == null) {
				  continue;
			  } else {
				  String[] arr = element.split("(?!^)");
				  String first_c = arr[0];
				  String second_c = arr[1];
				  int index = findIndex(number, second_c);
				  String al = alphabet[index];
				  String newCordinate = al+first_c;
				  new_set.add(newCordinate);
			  }
			}
			
			return new_set;
		}
		
		return set;
	}
	
	
	
	
	public Set<String> validMoves(String e) {
		
		Set<String> move_set = new HashSet<String>();

		Moves move = new Moves(n, black_set, white_set, empty_set);
		move_set = move.findValidMoves(e);
		
		
		if (move_set != null) {
			return convertSet(move_set);
		}
		return move_set;
	}
	
	public Set<String> printValidMoves(Set<String> set){
		Set<String> moves_all = new HashSet<String>();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			  String element = iterator.next();
			  Set<String> move_set = new HashSet<String>();
			  move_set = validMoves(element);
			  moves_all.addAll(move_set);
		}
		return moves_all;
	} 
	
	
	public boolean blackIsValid(String key) {
		Set<String> moves_all = printValidMoves(black_set);
		
		if (moves_all.contains(key)) {
			
			return true;
		} else {
			
			return false;
		}
	}
	
	public boolean whiteIsValid(String key) {
		Set<String> moves_all = printValidMoves(white_set);
		
		if (moves_all.contains(key)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public Hashtable<String, Set<String>> flippedTable (String e){
		Hashtable<String, Set<String>> flipped_table = new Hashtable<String, Set<String>>();

		Moves move = new Moves(n, black_set, white_set, empty_set);
		move.findValidMoves(e);
		flipped_table = move.findFlippedSet();
		return flipped_table;
	}
	
	public Hashtable<String, Set<String>> flippedWhite(String key) {
		Hashtable<String, Set<String>> flipped_table_all = new Hashtable<String, Set<String>>();
		Iterator<String> iterator = black_set.iterator();
		
		while(iterator.hasNext()){
		  String element = iterator.next();
		  Hashtable<String, Set<String>> flipped_table = new Hashtable<String, Set<String>>();
		  flipped_table = flippedTable(element);
		  if (flipped_table_all.isEmpty()) {
			  flipped_table_all.putAll(flipped_table);
		  } else {
			  Set<String> keys = flipped_table_all.keySet();
		       for(String y: keys){
		            if(flipped_table.containsKey(y)) {
		            	Set<String> s1 = flipped_table_all.get(y);
			        	Set<String> s2 = flipped_table.get(y);
			        	s1.addAll(s2);
			        	flipped_table_all.replace(y, s1);
			        	flipped_table.remove(y);
		            }
		       }
		       flipped_table_all.putAll(flipped_table);
			 
		  }
		 }
		
		return flipped_table_all;
		
	}
	
	public Hashtable<String, Set<String>> flippedBlack(String key) {
		Hashtable<String, Set<String>> flipped_table_all = new Hashtable<String, Set<String>>();
		Iterator<String> iterator = white_set.iterator();
		
		while(iterator.hasNext()){
		  String element = iterator.next();
		  Hashtable<String, Set<String>> flipped_table = new Hashtable<String, Set<String>>();
		  flipped_table = flippedTable(element);
		  if (flipped_table_all.isEmpty()) {
			  flipped_table_all.putAll(flipped_table);
		  } else {
			  Set<String> keys = flipped_table_all.keySet();
		       for(String y: keys){
		            if(flipped_table.containsKey(y)) {
		            	Set<String> s1 = flipped_table_all.get(y);
			        	Set<String> s2 = flipped_table.get(y);
			        	s1.addAll(s2);
			        	flipped_table_all.replace(y, s1);
			        	flipped_table.remove(y);
		            }
		       }
		       flipped_table_all.putAll(flipped_table);
			 
		  }
		 }
		
		return flipped_table_all;
	}
		

	

	
	//Update the board once a move is taken by either side
	public void addNewDisk(String where, String who) {
		
		String[] arr = where.split("(?!^)");
		int row = Integer.valueOf(arr[0]);
		int column = Integer.valueOf(arr[1]); 
		grid[row][column] = who;
		if (who.equals("x")) {
			black_set.add(where);
			h.put(where, "x");
		} else {
			white_set.add(where);
			h.put(where, "o");
		}
	
		empty_set.remove(where);
	}
	
	public void flippDisks(Set<String> flipped_set, String value) {
		Iterator<String> iterator = flipped_set.iterator();
		
		while(iterator.hasNext()){
		  String element = iterator.next();
		  String[] arr = element.split("(?!^)");
		  int row = Integer.valueOf(arr[0]);
		  int column = Integer.valueOf(arr[1]); 
		  grid[row][column] = value;
		  if (value.equals("x")) {
				black_set.add(element);
				white_set.remove(element);
				h.put(element, "x");
			} else {
				white_set.add(element);
				black_set.remove(element);
				h.put(element, "o");
			}
		 }
	}

	
	public void updateBoard(String key, String value) {
		String key_num = alphabetToNumber(key);
		if (black_set.contains(key_num) || white_set.contains(key_num)) {
			
		} else if (empty_set.contains(key_num) && value.equals("x") && blackIsValid(key)) {
			Set<String> flipped_set = flippedWhite(key).get(key_num);
			flippDisks(flipped_set, value);
			addNewDisk(key_num,"x");
			
		} else if (empty_set.contains(key_num) && value.equals("o") && whiteIsValid(key)) {
			Set<String> flipped_set = flippedBlack(key).get(key_num);
			flippDisks(flipped_set, value);
			addNewDisk(key_num,"o");
			
		} 
		
	}
	
	//Print out the current Reversi board
	public void printBoard() {
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
						System.out.print(grid[i][j]+" ");
			}
					System.out.println();
		}
				
	}
	
	public Set<String> returnBlack(){
		return this.black_set;
	}
	
	
	public ArrayList<Board> getChildren(String[][] grid1, Hashtable<String,String> h1, Set<String> black_set1, Set<String> white_set1, Set<String> empty_set1, String player){
		ArrayList<Board> arrBoard = new ArrayList<Board>();
		final Set<String> black_set2 = new HashSet<String>();
		black_set2.addAll(black_set1);
		final Set<String> white_set2 = new HashSet<String>();
		white_set2.addAll(white_set1);
		final Set<String> empty_set2 = new HashSet<String>();
		empty_set2.addAll(empty_set1);
		final String[][] grid2 = grid1.clone();
		
		final Hashtable<String, String> h2 = new Hashtable<String,String>();
		h2.putAll(h1);
		
		if (player.equals("x")) { 
			Set<String> move_set = printValidMoves(black_set2);
			Iterator<String> iterator = move_set.iterator();
			while(iterator.hasNext()){
				Board child = new Board(n-2);
				child.black_set.clear();
				child.black_set.addAll(black_set2);
				child.white_set.clear();
				child.white_set.addAll(white_set2);
				child.empty_set.clear();
				child.empty_set.addAll(empty_set2);
				for( int i = 1; i < child.grid.length-1; i++ ) {
					for (int j=1; j<child.grid.length-1; j++) {
						child.grid[i][j] = " ";
					}
				}
				Iterator<String> iterator2 = child.black_set.iterator();
				Iterator<String> iterator3 = child.white_set.iterator();
				while(iterator2.hasNext()) {
					String b = iterator2.next();
					String[] arr = b.split("(?!^)");
					
					int r = Integer.valueOf(arr[0]);
					int c = Integer.valueOf(arr[1]);
					child.grid[r][c] = "x";
					
				}
				while(iterator3.hasNext()) {
					String w = iterator3.next();
					String[] arr = w.split("(?!^)");
					int r = Integer.valueOf(arr[0]);
					int c = Integer.valueOf(arr[1]);
					child.grid[r][c] = "o";
					
				}
				child.h.clear();
				child.h.putAll(h2);
				String move = iterator.next();
				child.updateBoard(move, player);
				arrBoard.add(child); 
			}
			
		} else if (player.equals("o")) {
			Set<String> move_set = printValidMoves(white_set);
			Iterator<String> iterator = move_set.iterator();
			while(iterator.hasNext()){
				Board child = new Board(n-2);
				child.black_set.clear();
				child.black_set.addAll(black_set2);
				child.white_set.clear();
				child.white_set.addAll(white_set2);
				child.empty_set.clear();
				child.empty_set.addAll(empty_set2);
				for( int i = 1; i < child.grid.length-1; i++ ) {
					for (int j=1; j<child.grid.length-1; j++) {
						child.grid[i][j] = " ";
					}
				}
				Iterator<String> iterator2 = child.black_set.iterator();
				Iterator<String> iterator3 = child.white_set.iterator();
				while(iterator2.hasNext()) {
					String b = iterator2.next();
					String[] arr = b.split("(?!^)");
					
					int r = Integer.valueOf(arr[0]);
					int c = Integer.valueOf(arr[1]);
					child.grid[r][c] = "x";
					
				}
				while(iterator3.hasNext()) {
					String w = iterator3.next();
					String[] arr = w.split("(?!^)");
					int r = Integer.valueOf(arr[0]);
					int c = Integer.valueOf(arr[1]);
					child.grid[r][c] = "o";
					
				}
				child.h.clear();
				child.h.putAll(h2);
				String move = iterator.next();
				child.updateBoard(move, player);
				arrBoard.add(child);  
			}
		}
		return arrBoard;
	}
	
	
	
	public int getUtility(String player) {
		int ai, opponent, value;
		
		if (player.equals("x")) {
			ai = black_set.size();
			opponent = white_set.size();
			value = (ai-opponent)/(ai+opponent);
		} else {
			ai = white_set.size();
			opponent = black_set.size();
			value = (ai-opponent)/(ai+opponent);
		}
	
		return value;
	}

	public Board getBoard() {
		return this;
	}
	

}
