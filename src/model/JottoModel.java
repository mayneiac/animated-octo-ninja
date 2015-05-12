package model;

import java.net.*;
import java.io.*;

/**
 * // 
 */
public class JottoModel {
    
    /**
     * @param puzzle is a integer to identify the puzzle number
     * @param guess is a string of the guess. 
     * @return string of the server response. 
     */
    public static String makeGuess(int puzzle, String guess) throws Exception {
        
        StringBuilder url_builder = new StringBuilder("http://courses.csail.mit.edu/6.005/jotto.py?puzzle=");
        
        url_builder.append(puzzle);
        url_builder.append("&guess=");
        url_builder.append(guess);
        
        URL query_url = new URL(url_builder.toString());
        
        BufferedReader in = new BufferedReader( new InputStreamReader(query_url.openStream()));
        
        String inputLine;
        inputLine = in.readLine();
        in.close();
        return inputLine;
    }
}
