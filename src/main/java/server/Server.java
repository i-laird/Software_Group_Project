package server;

import server.communication.SynchronizedMessageHandler;
import server.communication.message.*;
import server.communication.message.Error;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {

    private static class MessageTypeException extends Throwable{
        private String message;
        private MessageTypeException(){
            super();
        }
        private MessageTypeException(String s){
            super(s);
        }
    }

    /**
     * This is a server for the Snake game
     *
     * @param args
     *  port: the port that the server is to run on
     */
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        try {

            // used in order to accept connections
            ServerSocket ss = new ServerSocket(port);

            // create thread pool
            ExecutorService gameBeginExecutorService = Executors.newFixedThreadPool(10);

            // forever accept connections
            while(true){

                // block for a connection
                Socket connection = ss.accept();
                Server server = new Server(connection);
                gameBeginExecutorService.submit(server);
            }
        }catch(IOException e){}
    }

    // to communicate with the client
    private Socket socket = null;

    private SynchronizedMessageHandler messageHandler = null;

    private String approvedClientName;

    private Server(Socket s){
        this.socket = s;
    }

    /**
     * Runs when a client first connects to the Server
     */
    public void run(){

        // set up the message handler
        try {
            this.messageHandler = new SynchronizedMessageHandler(this.socket);
        }catch(IOException e){}
        this.handleClient();
    }

    private void handleClient(){
        try {
            try {
                // first send confirmation that connection has been established
                messageHandler.sendMessage(new ACK("Connection Established"));

                // get the name of this player
                handleClientName();

                // send the lobbies that are currently open
                sendLobbies();

                // handle player's lobby selection
                handleLobby();

            } catch (MessageTypeException e) {
                messageHandler.sendMessage(new Error(e.getMessage()));
                closeConnection();
            }
        }
        catch(IOException | ClassNotFoundException e2){}
    }

    private void handleClientName() throws IOException, ClassNotFoundException, MessageTypeException {
        // this message needs to be the player name
        Message receive = messageHandler.receiveMessage();
        if(! (receive instanceof Register_Name)){
            throw new MessageTypeException("Error: REGISTER_NAME expected");
        }
        Register_Name clientName = (Register_Name) receive;

        //todo make sure this is not a duplicate name
        approvedClientName = clientName.getPlayerName();
        messageHandler.sendMessage(new ACK(approvedClientName));
    }

    private void sendLobbies(){

    }

    private void handleLobby() throws ClassNotFoundException, IOException, MessageTypeException {
        Message m = messageHandler.receiveMessage();

        if(m instanceof Create_Lobby){
            // TODO create lobby
        }

        else if( m instanceof Join_Lobby){
            // TODO add player to this lobby
        }

        else{
            throw new MessageTypeException("Error: Either CREATE_LOBBY OR JOIN_LOBBY expected");
        }
    }

    private void closeConnection() throws IOException{
        this.messageHandler.closeConnections();
        this.socket.close();
    }


}
