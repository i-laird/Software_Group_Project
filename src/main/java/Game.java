import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author: Ian Laird
 */
public abstract class Game {
    private static Logger LOGGER = Logger.getLogger("Game");

    protected Snake playerOne;

    protected Snake playerTwo;

    protected Screen gameScreen;

    protected Socket networkSocket = null;

    //For sending mopves over network
    protected PrintWriter moveSender = null;

    //for receiving moves over network
    protected Scanner moveReader = null;

    private boolean gameOver = false;

    Cell powerUp = Cell.createRandom(gameScreen.getWidth(), gameScreen.getHeight());

    //////////////////////////////////////////////////////////////////////////////////////

    public boolean isGameOver() {
        return gameOver;
    }

    public void playAgain() {
        this.gameOver = false;
    }
    public void MovePlayers(){
        Cell playerOneMove, playerTwoMove;
        playerOneMove = getPlayerOneMove();
        playerTwoMove = getPlayerTwoMove();
        //See if player one  just lost
        //Seeing if playerOne cell exists in Player 2!!!
        if(playerDeadAfterMove(playerTwo, playerOneMove)){
            gameOver = true;
            gameScreen.plotDefeatScreen();
            //Should this return IDK yet
        }
        //See if player 1 just won
        if(playerDeadAfterMove(playerOne, playerTwoMove)){
            gameOver = true;
            gameScreen.plotWinScreen();
        }
        boolean resetPowerUp = false;
        if(powerUp.equals(playerOneMove)){
            playerOne.increaseLength();
            resetPowerUp = true;
        }
        if(powerUp.equals(playerTwoMove)){
            playerTwo.increaseLength();
            resetPowerUp = true;
        }
        //get new power up location such that it is not occupied by a Snake
        if(resetPowerUp) {
            do {
                powerUp = Cell.createRandom(gameScreen.getWidth(), gameScreen.getHeight());
            }while(playerOne.snakeCoverMove(powerUp) || playerTwo.snakeCoverMove(powerUp));
        }
        gameScreen.plotBackground();
        gameScreen.plotPowerUp();
        playerOne.moveLocation(playerOneMove);
        playerTwo.moveLocation(playerTwoMove);
        playerOne.drawSnake(gameScreen);
        playerTwo.drawSnake(gameScreen);
    }

    public void initNetwork(String hostName, int portNum){
        try {
            initializeSocket(hostName, portNum);
        }catch(IOException e){
            LOGGER.severe("Socket failed to open!");
            return;
        }
        if(!initNetIn() || !initNetOut()){
            LOGGER.severe("Network initialization failed");
        }
    }

    /**
     * See if the move will kill the player
     * @param otherPlayer
     * @return
     */
    protected boolean playerDeadAfterMove(Snake otherPlayer, Cell moveTo){
        boolean moveKills = false;
        moveKills = otherPlayer.snakeCoverMove(moveTo);
        if(moveKills == true) {
            return true;
        }
        return moveInBounds(moveTo);
    }

    /**
     * This should get the move for home Machine from user on this machine
     * @return the move generated by the player
     */
    protected Cell getPlayerOneMove(){

    }

    protected boolean initNetIn(){
        try{
        moveReader = new Scanner( new BufferedReader(
                new InputStreamReader(networkSocket.getInputStream())));
        }catch(IOException e){
            LOGGER.severe("Network input stream unable to be opened!");
            return false;
        }
        return true;
    }

    protected boolean initNetOut(){
        try {
            moveSender = new PrintWriter(networkSocket.getOutputStream(), true);
        }
        catch(IOException e){
            LOGGER.severe("network output stream unable to be initialized!");
            return false;
        }
        return true;
    }
    //TODO
    protected boolean moveInBounds(Cell move){

    }

    ////////////////////////////////////////////////////////////////

    public abstract void initializeSocket(String hostName, int PortNumber) throws IOException;

    /**
     * This should get a move for other player from over the network
     * @return
     */
    protected abstract Cell getPlayerTwoMove();

}
