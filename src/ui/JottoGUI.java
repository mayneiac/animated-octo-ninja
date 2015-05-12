package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import ui.JottoModel;


/**
 * // Remember to name all your components, otherwise autograder will give a zero.
 * // Remember to use the objects newPuzzleButton, newPuzzleNumber, puzzleNumber,
 * // guess, and guessTable in your GUI!
 */
public class JottoGUI extends JFrame implements ActionListener {

    private final JButton newPuzzleButton;
    private final JTextField newPuzzleNumber;
    private final JLabel puzzleNumber;
    private final JTextField guess;
    private final JTable guessTable;
    private int puzzle_number;
    private PuzzleProgression progress;
    private JLabel guessText;
    private DefaultTableModel data;
    
    public JottoGUI() {
    	progress = new PuzzleProgression();
    	puzzle_number = random_int();
        newPuzzleButton = new JButton();	
        newPuzzleButton.addActionListener(
        	new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        setPuzzle();
		    }
		  }
		);
        newPuzzleButton.setName("newPuzzleButton");
        newPuzzleButton.setText("New Puzzle");
        newPuzzleNumber = new JTextField();
        newPuzzleNumber.addActionListener(
        	new ActionListener() {
      		    public void actionPerformed(ActionEvent e) {
      		      setPuzzle();
      		    }
        	}
      	);
        newPuzzleNumber.setName("newPuzzleNumber");
        puzzleNumber = new JLabel();
        puzzleNumber.setName("puzzleNumber");
        puzzleNumber.setText("" + this.puzzle_number);
        guessText = new JLabel();
        guessText.setText("Type a guess here:");
        guess = new JTextField();
        guess.setName("guess");
        guess.addActionListener(
        	new ActionListener() {
      		    public void actionPerformed(ActionEvent e) {
      		    	try{makeGuess(guess.getText());}
      		    	catch(Exception exc){}
      		    }
        	}
      	);
        
        data = new DefaultTableModel();
        data.addColumn("Guess");
        data.addColumn("Letters");
        data.addColumn("Places");
        guessTable = new JTable(data);
        guessTable.setName("guessTable");
        
        setSize(500,500);
        
        GroupLayout layout = new GroupLayout(this.getContentPane());
        setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
        	layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        			.addComponent(guessTable)
        			.addGroup(layout.createSequentialGroup()
        				.addComponent(this.puzzleNumber)
        				.addComponent(this.newPuzzleButton)
        				.addComponent(this.newPuzzleNumber)
        				)
        			.addGroup(layout.createSequentialGroup()
        				.addComponent(this.guessText)
        				.addComponent(this.guess)
        				)
        			)
        		);
        layout.setVerticalGroup(
        	layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        	.addComponent(this.puzzleNumber)
		    		.addComponent(this.newPuzzleButton)
		    		.addComponent(this.newPuzzleNumber)
		    		)
		    	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		    		.addComponent(this.guessText)
		    		.addComponent(this.guess)
		    		)
		    	.addComponent(guessTable)
	    );
    }
    
    
    public void actionPerformed(ActionEvent e) {
    }
    
    /*
     * @return a random int between 1 and 10000
     */
    private static int random_int(){
        return (int)(Math.random() * 10000);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JottoGUI main = new JottoGUI();

                main.setVisible(true);
            }
        });
    } 
    
    /*
     * @param str guess string sent
     */
    
    private void makeGuess(String str) throws Exception{
    	guess.setText("");
    	JottoModel j = null;
    	synchronized((Object)progress){
    		j = new JottoModel(this.data, str, this.data.getRowCount(), this.puzzle_number, this.progress);
    	}
    	Thread t = new Thread((Runnable)j);	
    	t.start();
    }
    
    private void setPuzzle(){
        String number = newPuzzleNumber.getText();
            int foo = -1;                           
            try{foo = Integer.parseInt(number);}
            catch(NumberFormatException d){
            }
            if(foo < 0){
                this.puzzle_number = random_int();
            }
            else{
                this.puzzle_number = foo;               
            }
            puzzleNumber.setText("" + this.puzzle_number);
            clear(); 
    }
    
    private void clear(){
        while(data.getRowCount() > 0){
            data.removeRow(0);
        }
        synchronized(progress){progress.increment();}
    }
}


