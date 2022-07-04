import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Grid implements java.io.Serializable{
    public static final long serialVersionUID = 206147912798728943L;
    private final Tile[][] tileGrid;
    private int mineRemain;
    private int blankRemain;
    public Grid(){
        mineRemain=40;
        blankRemain = 16*16-mineRemain;
        tileGrid = new Tile[16][16];
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                tileGrid[i][j] = new Tile();
            }
        }
        generateMine(mineRemain);
        setNeighbor();
    }

    private void generateMine(int mineNum){
        Random rand = new Random();
        for(int i =0;i<mineNum;i++){
            int randNum = rand.nextInt(256);
            while(tileGrid[randNum/16][randNum%16].isMine()) {
                randNum = rand.nextInt(256);
            }
            tileGrid[randNum / 16][randNum % 16].setMine();
        }
    }

    private void setNeighbor(){
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                if(tileGrid[i][j].isMine())
                    continue;
                int mineCount=0;
                for(int k=-1;k<2;k++){
                    for (int l =-1;l<2;l++){
                        if((i+k>=0)&&(i+k<=15)&&(j+l>=0)&&(j+l<=15)){
                            if(tileGrid[i+k][j+l].isMine())
                                mineCount++;
                        }
                    }
                }
                tileGrid[i][j].setMineNeighbor(mineCount);
            }
        }

    }
    public void setFlag(int x,int y,Image[] imageBuffer,JButton Button){
        Tile tile = tileGrid[x][y];
        if(!(tile.isCheck())) {
            if (!(tile.isFlag())) {
                tile.setFlag(imageBuffer[11],Button);
                if (tile.isMine())
                    mineRemain--;
            } else {
                tile.deFlag(imageBuffer[10],Button);
                if (tile.isMine())
                    mineRemain++;
            }
        }
    }
// return false if this Tile(x,y) has mine
// return true if this Tile(x,y) doesn't have mine
    public boolean checkTile(int x,int y,Image[] imageBuffer,JButton[][] gridButton){
        if(!tileGrid[x][y].isCheck()) {
            if (tileGrid[x][y].setChecked(imageBuffer,gridButton[x][y])) {
                blankRemain--;
                if (tileGrid[x][y].getMineNeighbor() > 0)
                    return true;
                checkAround(x, y, imageBuffer,gridButton);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
//  Breadth First Search
    private void checkAround(int x,int y,Image[] imageBuffer,JButton[][] gridButton){
        Pair<Integer,Integer> start = new Pair<>(x,y);
        Queue<Pair<Integer,Integer>> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()){
            Pair<Integer,Integer> now = queue.remove();
            for(Pair<Integer,Integer> succession: getEightNeighbors(now)){
                Tile successionTile = tileGrid[succession.getKey()][succession.getValue()];
//                Each checking a tile without mine, blankTile remain --
                if(successionTile.setChecked(imageBuffer,gridButton[succession.getKey()][succession.getValue()]))
                {
                    blankRemain--;
                    if(successionTile.getMineNeighbor()==0){
//                        Enqueue zero neighbor  tile
                            queue.addAll(getEightNeighbors(succession));
                    }
                }
            }
        }
    }

    private ArrayList<Pair<Integer,Integer>> getEightNeighbors(Pair<Integer,Integer> now){
        ArrayList<Pair<Integer,Integer>> succession = new ArrayList<>();
        int i = now.getKey();int j = now.getValue();
        for(int k=-1;k<2;k++){
            for (int l =-1;l<2;l++){
                if((i+k>=0)&&(i+k<16)&&(j+l>=0)&&(j+l<16)){
                    if((!tileGrid[i+k][j+l].isCheck())&&(!tileGrid[i+k][j+l].isMine())&&(!tileGrid[i+k][j+l].isFlag()))
                        succession.add(new Pair<>(i+k,j+l));
                }
            }
        }
        return succession;
    }

    public void revealFail(Image[] imageBuffer, JButton[][] gridButton){
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                if(tileGrid[i][j].isMine()){
                    tileGrid[i][j].setChecked(imageBuffer,gridButton[i][j]);
                }
                else if(tileGrid[i][j].isFlag())
                {
                    tileGrid[i][j].wrongFlag(imageBuffer[12],gridButton[i][j]);
                }
            }
        }
    }
    public void revealWin(Image[] imageBuffer,JButton[][] gridButton){
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                if(tileGrid[i][j].isMine()){
                    tileGrid[i][j].setFlag(imageBuffer[11],gridButton[i][j]);
                }
            }
        }
    }

    public void reloadGridButton(Image[] imageBuffer,JButton[][] gridButton){
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                if(tileGrid[i][j].isFlag()){
                    gridButton[i][j].setIcon(new ImageIcon(imageBuffer[11]));
                }
                else if(tileGrid[i][j].isCheck())
                {
                    gridButton[i][j].setIcon(new ImageIcon(imageBuffer[tileGrid[i][j].getMineNeighbor()]));
                }
                else if(!tileGrid[i][j].isCheck()){
                    gridButton[i][j].setIcon(new ImageIcon(imageBuffer[10]));
                }
            }
        }
    }
    public boolean checkWin(){
        if (blankRemain==0)
        {
            this.mineRemain=0;
            return true;
        }
        return false;
    }
    public int getMineRemain(){return mineRemain;}
}
