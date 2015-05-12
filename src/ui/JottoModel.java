package ui;

import java.net.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

public class JottoModel implements Runnable{
    
    
    /*
     * sends and recievese server messages
     * 
     */
	
	private int puzzle_number;
	private DefaultTableModel data;
	private int row;
	private PuzzleProgression progress;
	private int iteration;
	private String input;
	

	public JottoModel(DefaultTableModel table, String input, int row, int puzzle_number, PuzzleProgression progress){
		synchronized(progress){
			this.progress = progress;
			this.iteration = progress.getIteration();
		}
		this.puzzle_number = puzzle_number;
		this.data = table;
		this.row = row;
		this.input = input;
	}
	
	/**
	 * runs with new thread
	 * keeps table data up to date with most recent server messages
	 */
	public void run(){
		String[] test = new String[3];
		test[0] = this.input;
		synchronized(data){
			if(progress.getIteration() == iteration){this.data.addRow(test);}
		}
		String answer = null;
		try{answer = makeGuess(this.input);}
		catch(Exception io){System.out.println("Issue with server communication, try again.");}
		if(progress.getIteration() == iteration){
			synchronized(data){
				if(answer.equals("guess 5 5")){
					data.setValueAt("You win!", this.row, 1);
	    		}
	    		else if(answer.startsWith("guess")){
	    			data.setValueAt(answer.substring(6,7), this.row, 1);
	    			data.setValueAt(answer.substring(8,9), this.row, 2);
	    	    }
	    	    else if(answer.startsWith("error 0:")){
	    	    	data.setValueAt("Ill formatted request.", this.row, 1);
	    	    }
	    	    else if(answer.startsWith("error 1:")){
	    	    	data.setValueAt("Bad puzzle ID", this.row, 1);
	    	    }
	    	    else{
	    	    	data.setValueAt("Bad word input", this.row, 1);
	    	    }
			}
		}
	}

	/*
	 * @param guess string to be attempted
	 * @return the response (string)
	 */
	
    public String makeGuess(String guess) throws Exception {
        StringBuilder url_builder = new StringBuilder("http://courses.csail.mit.edu/6.005/jotto.py?puzzle=");
        
        url_builder.append(this.puzzle_number);
        url_builder.append("&guess=");
        url_builder.append(guess);
        
        URL server = new URL(url_builder.toString());
        BufferedReader in = new BufferedReader(
        new InputStreamReader(server.openStream()));
        String inputLine = in.readLine();
        in.close();
        if(inputLine != null){
        	return inputLine;
        }
        else{throw new IOException("Received a null message from the server.");}
    }
}