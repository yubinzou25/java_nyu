import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import static javax.swing.JOptionPane.showMessageDialog;

public class Game extends JFrame {
    // Component in JFrame
    private final int gameTime = 1000;
    private JMenuBar menuBar;
    private JLabel timeLabel;
    private JLabel mineLabel;
    private JPanel gridPanel;
    private JButton[][] gridButton;
    static int FRAME_WIDTH = 250;
    static int FRAME_HEIGHT = 350;
    //Component in JDialog For Open JMenuItem
    private JDialog dialog;
    private JList<myPair> queryList;
    private JScrollPane scrollPane;
    //Component in JDialog For TOP5 JMenuItem
    private JDialog dialogTop;
    private JList<String> topList;
    //Parameter in one Game
    private Grid grid;
    private Image[] imageBuffer;
    private boolean terminate;
    private int timeRemain;
    // Client of this Game
    private Client client;
    public Game(){
        connectToServer();
        loadImageBuffer();
        timeRemain = gameTime;
        terminate = false;
        grid = new Grid();
        createDialog();
        createTop5Dialog();
        createMenu();
        createGridButton();
        this.setJMenuBar(menuBar);
        this.add(gridPanel,BorderLayout.CENTER);
        this.add(createTimePanel(),BorderLayout.NORTH);
        this.add(createMinePanel(),BorderLayout.SOUTH);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Timer();
    }
    private void Timer(){
        while (true) {
            while (timeRemain > 0) {
                try {
                    Thread.sleep(1000);
                    if(!terminate) {
                        timeRemain--;
                        timeLabel.setText("Time Remaining " + timeRemain + "s");
                    }
                } catch (InterruptedException e) {
                    showMessageDialog(null, "Thread Error");
                    e.printStackTrace();
                }
            }
        terminate = true;
        grid.revealFail(imageBuffer,gridButton);
        mineLabel.setText(String.valueOf(grid.getMineRemain()));
        showMessageDialog(null,"You Lost");
        gameRestart();
        }
    }
    private void connectToServer() {
        try {
            client = new Client();
        } catch (IOException e) {
            showMessageDialog(null,"Cannot Connect to the Server");
            System.exit(0);
            e.printStackTrace();
        }
    }
    private void loadImageBuffer(){
        imageBuffer = new Image[13];
        for(int i=0;i<13;i++)
        {
            try {
                imageBuffer[i] = ImageIO.read(getClass().getResource("/resources/"+i+".png"));
            } catch (IOException e) {
                showMessageDialog(null, "Fail to Load the Image");
                e.printStackTrace();
            }
        }
    }
    private JPanel createTimePanel(){
        JPanel timePanel = new JPanel();
        timeLabel = new JLabel("Time Remaining "+timeRemain+"s");
        timePanel.add(timeLabel);
        return timePanel;
    }
    private JPanel createMinePanel(){
        JPanel minePanel = new JPanel(new BorderLayout());
        mineLabel = new JLabel(String.valueOf(grid.getMineRemain()));
        minePanel.add(mineLabel,BorderLayout.WEST);
        return minePanel;
    }
    private void createGridButton(){
        gridButton = new JButton[16][16];
        gridPanel = new JPanel(new GridLayout(16,16));
        Image img = imageBuffer[10];
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                JButton button = new JButton();
                button.putClientProperty(0,16*i+j);
                button.setIcon(new ImageIcon(img));
                button.addActionListener(new ButtonLeftClickListener());
                button.addMouseListener(new ButtonRightClickListener());
                gridPanel.add(button);
                gridButton[i][j] = button;
            }
        }
        repaint();
    }
    private void createDialog(){
        dialog = new JDialog(this,"Open",true);
        dialog.setSize(400,200);
        dialog.setLocation(400, 300);
        JList<String> listLabel = new JList<>(new String[]{"ID \t Remain \t Create Date"});
        scrollPane = new JScrollPane();
        dialog.add(listLabel,BorderLayout.NORTH);
        dialog.add(scrollPane,BorderLayout.CENTER);
    }
    private void createTop5Dialog(){
        dialogTop = new JDialog(this,"Top5",true);
        dialogTop.setSize(300,200);
        dialogTop.setLocation(400, 300);
        JList<String> listLabel = new JList<>(new String[]{"Time Remain \t   Create Date"});
        topList = new JList<>();
        dialogTop.add(listLabel,BorderLayout.NORTH);
        dialogTop.add(topList);
    }
    // Right Click Flag the tile
public class ButtonRightClickListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            JButton button = (JButton)e.getSource();
            Integer num = (Integer)button.getClientProperty(0);
            grid.setFlag(num/16,num%16, imageBuffer,gridButton[num/16][num%16]);
            mineLabel.setText(String.valueOf(grid.getMineRemain()));
            repaint();
        }
    }
}
// Left Click Reveal the Number or Mine
public class ButtonLeftClickListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        Integer num = (Integer)button.getClientProperty(0);
        if(!grid.checkTile(num/16,num%16, imageBuffer,gridButton)){
            terminate = true;
            grid.revealFail(imageBuffer,gridButton);
            mineLabel.setText(String.valueOf(grid.getMineRemain()));
            showMessageDialog(null,"You Lost");
            gameRestart();
        }
        if(grid.checkWin())
        {
            terminate = true;
            grid.revealWin(imageBuffer,gridButton);
            mineLabel.setText(String.valueOf(grid.getMineRemain()));
            showMessageDialog(null,"You Win");
            saveToHistoryWin(timeRemain);
            gameRestart();
        }
        repaint();
    }
}
    private void saveToHistoryWin(int timeRemain){
        try {
            client.saveToHistoryWin(timeRemain);
        } catch (IOException e) {
            showMessageDialog(null, "Fail to Save to the HistoryWin");
            e.printStackTrace();
        }
    }
    private void gameRestart(){
        timeRemain = gameTime;
        grid = new Grid();
        mineLabel.setText(String.valueOf(grid.getMineRemain()));
        timeLabel.setText("Time Remaining "+timeRemain+"s");
        restartGridButton();
        repaint();
        terminate = false;
    }

    private void restartGridButton(){
        Image img = imageBuffer[10];
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                gridButton[i][j].setIcon(new ImageIcon(img));
            }
        }
    }

    private void gameReLoad(Grid grid,int remain){
        timeRemain = remain;
        this.grid = grid;
        mineLabel.setText(String.valueOf(this.grid.getMineRemain()));
        timeLabel.setText("Time Remaining "+timeRemain+"s");
        this.grid.reloadGridButton(imageBuffer,gridButton);
        repaint();
        terminate = false;
    }

    private void createMenu(){
        menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuNew = new JMenuItem("New");
        JMenuItem menuOpen = new JMenuItem("Open");
        JMenuItem menuSave = new JMenuItem("Save");
        JMenuItem menuTop5 = new JMenuItem("Top5");
        JMenuItem menuExit = new JMenuItem("Exit");
        menuFile.add(menuNew);
        menuFile.add(menuOpen);
        menuFile.add(menuSave);
        menuFile.add(menuTop5);
        menuFile.add(menuExit);
        menuBar.add(menuFile);
        menuNew.addActionListener(e-> gameRestart());
        menuExit.addActionListener(e-> System.exit(0));
        menuSave.addActionListener(new menuSaveListener());
        menuOpen.addActionListener(new menuOpenListener());
        menuTop5.addActionListener(new menuTop5Listener());
    }

    class menuSaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            terminate = true;
            Grid SerializedGame = grid;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out ;

            try {
                out = new ObjectOutputStream(bos);
                out.writeObject(SerializedGame);
                out.flush();
                byte[] gameSeq = bos.toByteArray();
                client.saveToServer(gameSeq,timeRemain);
                showMessageDialog(null,"Save to the Database!");
                gameRestart();
            } catch (IOException ex) {
                showMessageDialog(null, "Fail to Save to the Database");
                ex.printStackTrace();
            }
        }
    }

    class menuTop5Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            terminate = true;
            try {
                LinkedList<String>  stringList = client.revealTop5FromServer();
                String[] stringArray = new String[5];
                for(int i=0;i<stringArray.length;i++) {
                    if (!stringList.isEmpty())
                        stringArray[i] = stringList.remove();
                    else
                        stringArray[i] = "None";
                }
                dialogTop.remove(topList);
                topList = new JList<>(stringArray);
                dialogTop.add(topList);
                dialogTop.setVisible(true);
            } catch (IOException ioException) {
                showMessageDialog(null, "Fail to Reveal the Top5 History");
                ioException.printStackTrace();
            }
            terminate = false;
        }
    }

     static class myPair<k,v> extends Pair {
        public myPair(k key, v value) {
            super(key, value);
        }
        @Override
        public String toString() {
            return String.valueOf(super.getValue());
        }
    }

    class menuOpenListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            terminate = true;
            try {
                LinkedList<myPair<Integer,String>> myPairList = client.revealFromServer();
                myPair<Integer,String>[] myPairArray = new myPair[myPairList.size()];
                for(int i=0;i<myPairArray.length;i++)
                    myPairArray[i] = myPairList.remove();
                queryList = new JList<>(myPairArray);
                dialog.remove(scrollPane);
                scrollPane = new JScrollPane(queryList);
                dialog.add(scrollPane,BorderLayout.CENTER);
                JButton reloadButton = new JButton("Reload");
                reloadButton.addActionListener(new reloadButtonListener());
                dialog.add(reloadButton,BorderLayout.SOUTH);
                dialog.setVisible(true);
            } catch (IOException ex) {
                showMessageDialog(null, "Fail to Reveal the Database");
                ex.printStackTrace();
            }
            terminate = false;
        }
    }

    class reloadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Integer ID = (Integer) queryList.getSelectedValue().getKey();
            try {
                Client.reloadPattern pairs = client.loadFromServer(ID);
                byte[] SerializedGame = pairs.getGameSeq();
                int remain = pairs.getTimeRemain();
                ObjectInputStream in;
                if (SerializedGame != null) {
                    in = new ObjectInputStream(new ByteArrayInputStream(SerializedGame));
                    Object deSerializedGame = in.readObject();
                    Grid reloadGame = (Grid) deSerializedGame;
                    dialog.setVisible(false);
                    gameReLoad(reloadGame,remain);
                }
            } catch (IOException | ClassNotFoundException ex) {
                showMessageDialog(null, "Fail to Load from the Database");
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        JFrame frame = new Game();
    }
}

