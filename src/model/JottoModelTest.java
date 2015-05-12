package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class JottoModelTest {

    
    @Test
    public void test16952Crazy(){
        try {
            assertEquals(JottoModel.makeGuess(16952,"crazy"), "guess 3 1");
        }
        catch (Exception e) {
            assertEquals(1,0);
        }
    }
    
    @Test
    public void test16952elephant(){
        try {
            assertEquals(JottoModel.makeGuess(16952,"elephant"), "error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.");;
        }
        catch (Exception e) {
            assertEquals(1,0);
        }
    }
    
    @Test
    public void test16952_bean(){
        try {
            assertEquals(JottoModel.makeGuess(16952,"*bean"), "guess 1 0");;
        }
        catch (Exception e) {
            assertEquals(1,0);
        }
    }
    
    @Test
    public void test16952fin_ls(){
        try {
            assertEquals(JottoModel.makeGuess(16952,"fin*ls"), "error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.");;
        }
        catch (Exception e) {
            assertEquals(1,0);
        }
    }
    
    @Test
    public void test501422(){
        try {
            assertEquals(JottoModel.makeGuess(5014,"22"), "error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.");;
        }
        catch (Exception e) {
            assertEquals(1,0);
        }
    }

}
