import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Client {
    private DataOutputStream toServer;
    private DataInputStream fromServer;
    private Socket socket;
    public Client() throws IOException {
        socket = new Socket("localhost",8000);
    }
    public void saveToServer(byte[] gameSeq, int timeRemain) throws IOException {
        toServer = new DataOutputStream(socket.getOutputStream());
        // write save/load/reveal flag
        // if flag==1 then save, else if flag==2 then load, else if flag==3 then reveal saved game
        // else if flag==4 then reveal top5 from win history, else if flag==5 then save to win history
        toServer.writeInt(1);
        toServer.writeInt(timeRemain);
        toServer.writeInt(gameSeq.length);
        toServer.write(gameSeq);
        toServer.flush();
    }
    public reloadPattern loadFromServer(int id) throws IOException {
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
        // if flag==1 then save, else if flag==2 then load, else if flag==3 then reveal
        // else if flag==4 then reveal top5 from win history, else if flag==5 then save to win history
        toServer.writeInt(2);
        toServer.writeInt(id);
        int timeRemain = fromServer.readInt();
        int byteLength = fromServer.readInt();
        byte[] gameSeq = new byte[byteLength];
        fromServer.readFully(gameSeq);
        return new reloadPattern(gameSeq, timeRemain);
    }
    public LinkedList<Game.myPair<Integer,String>> revealFromServer() throws IOException {
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
        // if flag==1 then save, else if flag==2 then load, else if flag==3 then reveal
        //else if flag==4 then reveal top5 from win history, else if flag==5 then save to win history
        LinkedList<Game.myPair<Integer,String>> myPairList = new LinkedList<>();
        toServer.writeInt(3);
        // read hasNext flag from server
        // if has next then true, else false
        while(fromServer.readBoolean()) {
            int id = fromServer.readInt();
            int timeRemain = fromServer.readInt();
            long dateTime = fromServer.readLong();
            java.sql.Date date = new java.sql.Date(dateTime);
            java.sql.Time time = new java.sql.Time(dateTime);
            myPairList.add(new Game.myPair<>(id, id + "   \t   " + timeRemain + "s   \t   " + date + "   \t   " + time + "\n"));
        }
        return myPairList;
    }
    public LinkedList<String> revealTop5FromServer() throws IOException {
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
        LinkedList<String> Top5List = new LinkedList();
        // if flag==1 then save, else if flag==2 then load, else if flag==3 then reveal
        //else if flag==4 then reveal top5 from win history, else if flag==5 then save to win history
        toServer.writeInt(4);
        // read hasNext flag from server
        // if has next then true, else false
        while(fromServer.readBoolean()){
            int timeRemain = fromServer.readInt();
            long dateTime = fromServer.readLong();
            java.sql.Date date = new java.sql.Date(dateTime);
            java.sql.Time time = new java.sql.Time(dateTime);
            Top5List.add("   \t   " + timeRemain + "s   \t   " + date + "   \t   " + time + "\n");
        }
        return Top5List;
    }
    public void saveToHistoryWin(int timeRemain) throws IOException {
        toServer = new DataOutputStream(socket.getOutputStream());
        // if flag==1 then save, else if flag==2 then load, else if flag==3 then reveal
        //else if flag==4 then reveal top5 from win history, else if flag==5 then save to win history
        toServer.writeInt(5);
        toServer.writeInt(timeRemain);
        toServer.flush();
    }

    static class reloadPattern {
        private final byte[] gameSeq;
        private final int timeRemain;
        reloadPattern(byte[] Seq, int Remain){
            gameSeq = Seq;
            timeRemain = Remain;
        }
        public byte[] getGameSeq(){return gameSeq;}
        public int getTimeRemain(){return timeRemain;}
    }
}
