package ui;



public class PuzzleProgression {
	
	private int iteration;
	 
	public PuzzleProgression(){
		iteration = 0;
	}

	public void increment(){
		iteration+=1;
	}
	public int getIteration(){
		return iteration;
	}

}
