import java.util.*;

public class Moves {
	
	Set<String> black_set = new HashSet<String>();
	Set<String> white_set = new HashSet<String>();
	Set<String> empty_set = new HashSet<String>();
	//Set<String> flipped_set = new HashSet<String>();
	Hashtable<String, Set<String>> flipped_table = new Hashtable<String, Set<String>>(); 
	int n;
	
	public Moves(int N, Set<String> black_set, Set<String> white_set, Set<String> empty_set) {
		this.black_set.addAll(black_set);
		this.white_set.addAll(white_set);
		this.empty_set.addAll(empty_set);
		n = N;
	}
	
	
	public void updateCurrentState(Set<String> black_set, Set<String> white_set, Set<String> empty_set) {
		this.black_set = black_set;
		this.white_set = white_set;
		this.empty_set = empty_set;
	}
	
	
	public String black_validMove(String key, String value) {
		Hashtable<String, String> neighbors2 = generateNeighbors(value);
    	String nextMove = neighbors2.get(key);
    	if (empty_set.contains(nextMove)) {
    		return nextMove;
    	} else if (white_set.contains(nextMove)) {
    		//flipped_set.add(nextMove);
    		nextMove = black_validMove(key, nextMove);
    	} else if (empty_set.contains(nextMove)==false && white_set.contains(nextMove)==false) {
    		return null;
    	}
    	return nextMove;
	}
	
	
	public String white_validMove(String key, String value) {
		Hashtable<String, String> neighbors3 = generateNeighbors(value);
    	String nextMove = neighbors3.get(key);
    	if (empty_set.contains(nextMove)) {
    		return nextMove;
    	} else if (black_set.contains(nextMove)) {
    		//flipped_set.add(nextMove);
    		nextMove = white_validMove(key, nextMove);
    	} else if (empty_set.contains(nextMove)==false && black_set.contains(nextMove)==false) {
    		return null;
    	}
    	return nextMove;
	}
	
	public Set<String> black_Flipped(Set<String> flipped_set, String key, String value) {
		Hashtable<String, String> neighbors2 = generateNeighbors(value);
    	String nextMove = neighbors2.get(key);
    	if (empty_set.contains(nextMove)) {
    		return flipped_set;
    	} else if (white_set.contains(nextMove)) {
    		flipped_set.add(nextMove);
    		flipped_set = black_Flipped(flipped_set, key, nextMove);
    	}
    	return flipped_set;
	}
	
	
	public Set<String> white_Flipped(Set<String> flipped_set, String key, String value) {
		Hashtable<String, String> neighbors3 = generateNeighbors(value);
    	String nextMove = neighbors3.get(key);
    	if (empty_set.contains(nextMove)) {
    		return flipped_set;
    	} else if (black_set.contains(nextMove)) {
    		flipped_set.add(nextMove);
    		flipped_set = white_Flipped(flipped_set, key, nextMove);
    	}
    	return flipped_set;
	}
	

	
	public Set<String> findValidMoves(String e) {
		Set<String> move_set = new HashSet<String>();
		//Set<String> flipped_set = new HashSet<String>();
		String nextMove;
		if (black_set.contains(e)) {
			Hashtable<String, String> neighbors = generateNeighbors(e);
	        Set<String> keys = neighbors.keySet();
	        for(String key: keys){
	        	String value = neighbors.get(key);
	        	Set<String> flipped_set = new HashSet<String>();
	        	Set<String> flipped_temp = new HashSet<String>();
	            //System.out.println("Value of "+key+" is: "+value);
	            if (white_set.contains(value)) {
	            	//System.out.println("Possible Move's Value is "+key+", key is: "+value);
	            	flipped_set.add(value);
	            	flipped_set.addAll(black_Flipped(flipped_temp, key, value));
	            	nextMove = black_validMove(key, value);
	            	if (nextMove != null) {
	            		flipped_table.put(nextMove, flipped_set);
	            	}
	            	move_set.add(nextMove);
	            }
	        }
	       // this.flipped_set = flipped_set;
	        return move_set;
			
		
		} else if (white_set.contains(e)) {
			Hashtable<String, String> neighbors = generateNeighbors(e);
	        Set<String> keys = neighbors.keySet();
	        for(String key: keys){
	        	String value = neighbors.get(key);
	        	Set<String> flipped_set = new HashSet<String>();
	        	Set<String> flipped_temp = new HashSet<String>();
	            //System.out.println("Value of "+key+" is: "+value);
	            if (black_set.contains(value)) {
	            	//System.out.println("Possible Move's Value is "+key+", key is: "+value);
	            	flipped_set.add(value);
	            	flipped_set.addAll(white_Flipped(flipped_temp, key, value));
	            	nextMove = white_validMove(key, value);
	            	if (nextMove != null) {
	            		flipped_table.put(nextMove, flipped_set);
	            	}
	            	move_set.add(nextMove);
	            }
	        }
	        //this.flipped_set = flipped_set;
	        return move_set;
		}
		
		return move_set;
	}
	
	public Hashtable<String, Set<String>> findFlippedSet(){
		return this.flipped_table;
	}
	
	public Hashtable<String, String> generateNeighbors(String element) {
		String[] E = element.split("(?!^)");
		int x = Integer.valueOf(E[0]);
		int y = Integer.valueOf(E[1]);
		
		Hashtable<String, String> neighbors = new Hashtable<String, String>(); 
		
		String nw = String.valueOf(x-1)+String.valueOf(y-1);
		neighbors.put("nw", nw);
		
		String n = String.valueOf(x-1)+String.valueOf(y);
		neighbors.put("n", n);
		
		String ne = String.valueOf(x-1)+String.valueOf(y+1);
		neighbors.put("ne", ne);
		
		String w = String.valueOf(x)+String.valueOf(y-1);
		neighbors.put("w", w);
		
		String e = String.valueOf(x)+String.valueOf(y+1);
		neighbors.put("e", e);
		
		String sw = String.valueOf(x+1)+String.valueOf(y-1);
		neighbors.put("sw",sw);
		
		String s = String.valueOf(x+1)+String.valueOf(y);
		neighbors.put("s", s);
		
		String se = String.valueOf(x+1)+String.valueOf(y+1);
		neighbors.put("se", se);
		
		return neighbors;
	}
}
