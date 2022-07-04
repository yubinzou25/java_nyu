import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Date;

public class Server extends JFrame implements Runnable{
    private final JTextArea serverText;
    private int clientNo;
    Server(){
        serverText = new JTextArea(10,10);
        clientNo = 0;
        JScrollPane scrollPane = new JScrollPane(serverText);
        this.add(scrollPane);
        this.setTitle("Server Terminal");
        this.setSize(400,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            serverText.append("Build the Server\n");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GameData.db\n");
            connection.close();
            serverText.append("Connect to the DataBase\n");
            while (true){
                Socket socket = serverSocket.accept();
                clientNo++;
                serverText.append("Starting thread for client " + clientNo + " at " + new Date() + '\n');
                InetAddress inetAddress = socket.getInetAddress();
                serverText.append("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
                serverText.append("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");
                new Thread(new HandleAClient(socket,clientNo)).start();
            }
        } catch (IOException | SQLException e) {

            e.printStackTrace();
        }

    }
    class HandleAClient implements Runnable{
        private final Socket socket;
        private final int clientNo;
        public HandleAClient(Socket socket,int clientNo) throws SQLException {
            this.socket = socket;
            this.clientNo = clientNo;

        }
        @Override
        public void run() {
            try {
                DataInputStream fromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
                while(true) {
                    try {
                        int flag = fromClient.readInt();
                        Connection connection = DriverManager.getConnection("jdbc:sqlite:GameData.db");
                        if (flag == 1) {
                            int timeRemain = fromClient.readInt();
                            int bytesLength = fromClient.readInt();
                            byte[] gameSeq = new byte[bytesLength];
                            fromClient.readFully(gameSeq);
                            String insertString = "INSERT INTO Game (gameSeq,dateTime,timeRemain) VALUES(?,?,?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(insertString);
                            preparedStatement.setBytes(1, gameSeq);
                            preparedStatement.setLong(2, System.currentTimeMillis());
                            preparedStatement.setInt(3, timeRemain);
                            preparedStatement.execute();
                            preparedStatement.close();
                            serverText.append("Client " + clientNo + " Save a Game to DataBase\n");
                        } else if (flag == 2) {
                            int ID = fromClient.readInt();
                            String queryString = "SELECT gameSeq,timeRemain FROM Game WHERE id = ?";
                            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                            preparedStatement.setInt(1, ID);
                            ResultSet rest = preparedStatement.executeQuery();
                            byte[] gameSeq = rest.getBytes("gameSeq");
                            int timeRemain = rest.getInt("timeRemain");
                            toClient.writeInt(timeRemain);
                            toClient.writeInt(gameSeq.length);
                            toClient.write(gameSeq);
                            serverText.append("Client " + clientNo + " Load a Game from DataBase\n");
                        } else if (flag == 3) {
                            String queryString = "SELECT id,timeRemain, dateTime FROM Game";
                            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                            ResultSet rest = preparedStatement.executeQuery();
                            while (rest.next()) {
                                int id = rest.getInt("id");
                                int remain = rest.getInt("timeRemain");
                                long dateTime = rest.getLong("dateTime");
                                // write hasNext flag to client
                                // if has next then true, else false
                                toClient.writeBoolean(true);
                                toClient.writeInt(id);
                                toClient.writeInt(remain);
                                toClient.writeLong(dateTime);
                            }
                            toClient.writeBoolean(false);
                            serverText.append("Client " + clientNo + " Reveal the DataBase\n");
                        } else if (flag == 4) {
                            String queryString = "SELECT timeRemain, dateTime From HistoryWin ORDER BY timeRemain DESC LIMIT 5";
                            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                            ResultSet rest = preparedStatement.executeQuery();
                            while (rest.next()) {
                                int remain = rest.getInt("timeRemain");
                                long dateTime = rest.getLong("dateTime");
                                // write hasNext flag to client
                                // if has next then true, else false
                                toClient.writeBoolean(true);
                                toClient.writeInt(remain);
                                toClient.writeLong(dateTime);
                            }
                            toClient.writeBoolean(false);
                            serverText.append("Client " + clientNo + " Reveal the Top5 HistoryWin\n");
                        } else if (flag == 5) {
                            String insertString = "INSERT INTO HistoryWin (timeRemain, dateTime) VALUES(?,?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(insertString);
                            int timeRemain = fromClient.readInt();
                            preparedStatement.setInt(1, timeRemain);
                            preparedStatement.setLong(2, System.currentTimeMillis());
                            preparedStatement.execute();
                            preparedStatement.close();
                            serverText.append("Client " + clientNo + " Save a timeRemain to HistoryWin\n");
                        }
                        connection.close();
                    }
                    catch (EOFException e){}
                }
//                fromClient.close();
//                toClient.close();
            }
            catch (EOFException e){}
            catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        Server serer = new Server();
    }
}
