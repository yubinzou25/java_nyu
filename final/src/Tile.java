import javax.swing.*;
import java.awt.*;

public class Tile implements java.io.Serializable{
    private boolean mine;
    private boolean flag;
    private boolean check;
    private int mineNeighbor;
    private int imgNum;
    public Tile(){
        mine=false;
        flag=false;
        check=false;
        mineNeighbor=0;
        imgNum = 0;
    }
    public boolean isMine() {
        return mine;
    }
    public boolean isCheck() {
        return check;
    }
    public boolean isFlag() {
        return flag;
    }
    public int getMineNeighbor() {
        return mineNeighbor;
    }
    // setChecked: if has Mine will return false
    //if not has Mine will return true
    public boolean setChecked(Image[] buffer,JButton button) {
        if(this.check)
            return true;
        this.check = true;
        this.flag = false;
        button.setIcon(new ImageIcon(buffer[imgNum]));
        return !this.mine;

    }
    public void setMine() {
        this.mine = true;
        imgNum = 9;
    }
    public void setFlag(Image flag,JButton button) {
        this.flag = true;
        button.setIcon(new ImageIcon(flag));
    }
    public void deFlag(Image blank,JButton button){
        this.flag = false;
        button.setIcon(new ImageIcon(blank));
    }

    public void wrongFlag(Image wrong,JButton button){
        button.setIcon(new ImageIcon(wrong));
    }

    public void setMineNeighbor(int mineNeighbor) {
        if(!this.mine) {
            this.mineNeighbor = mineNeighbor;
            if(this.mineNeighbor==0)
                imgNum = 0;
            else if(this.mineNeighbor==1)
                imgNum = 1;
            else if(this.mineNeighbor==2)
                imgNum = 2;
            else if(this.mineNeighbor==3)
                imgNum = 3;
            else if(this.mineNeighbor==4)
                imgNum = 4;
            else if(this.mineNeighbor==5)
                imgNum = 5;
            else if(this.mineNeighbor==6)
                imgNum = 6;
            else if(this.mineNeighbor==7)
                imgNum = 7;
            else if(this.mineNeighbor==8)
                imgNum = 8;
        }
    }

}
