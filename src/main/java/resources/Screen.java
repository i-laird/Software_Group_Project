package resources;

import Enums.Color;
import Enums.Direct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class should be a singleton b/c only one screen should exist
 */
//TODO Most of this class is unfinished...We need to implement Swing stuff
    //-Ian
public class Screen extends JPanel {
    private int width;
    private int height;
    private static Screen thisInstance = null;
    private int cellWidth;
    private JFrame frame;
    private GameBoard board;

    /**
     * @author: Andrew Walker
     * Because singleton the constructor is private
     */
    private Screen(int width, int length)
    {
        frame = new JFrame("Snake");
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void initBoard(int width, int height, int cellWidth){
        this.width = width;
        this.height = height;
        this.cellWidth = cellWidth;
        this.board = new GameBoard(this.width, this.height, this.cellWidth);
    }

    /**
     * @author: Andrew Walker
     * This function uses the singleton design pattern to return the instance of the screen
     */
    public static Screen getInstance(int width, int length)
    {
        return (thisInstance == null ? thisInstance = new Screen(width, length) : thisInstance);
    }

    /**
     * @author: Ian Laird
     * This function will simply update the game screen. It itself does not change.
     */
    public void updateScreen()
    {

    }

    /**
     * @author: Andrew Walker
     * This function will toggle the display of the screen
     */
    public void showScreen(boolean show){
        frame.setVisible(show);
    }

    /**
     * this function should color the rectangle at a certain location in the game the desired color.
     * This indicates that the player currently has visited there
     */
    public void colorLocation(int row, int col, Color color){

    }

    /**
     * This function should restore a block to default color indicating that the player no longer covers
     * that space.
     */
    public void unColorLocation(int row, int col){
        //This might not be a necessary function
        //COuld just plot background and redraw snake instead
        //Probably Unncessary
    }

    public void plotWinScreen(){

    }

    public void plotDefeatScreen(){

    }

    public void plotBackground(){

    }

    public void plotPowerUp(int row, int col){

    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    /**
     *
     * @return direction the player wants to move
     */
    public Direct readMoveFromKeyboard(){
        /*TODO implement so that keystrokes from user are recorded
        This means that the input has to be unbuffered so as to not wait for ENTER
        Unsure how to do this we will have to research -Ian
        */
        return null;
    }
}
