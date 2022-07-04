package graphics;
import sun.print.PeekGraphics;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	final static String rootPath = "./PartIV/";
	private Image img;
	private Random random_num;
	private int diceNum;
	public ImagePanel(int dieNum){this(rootPath+"die"+dieNum+".png");this.diceNum = dieNum; }

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}
	
	public ImagePanel(Image img) {
		this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        random_num = new Random();
        /*
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        */
        setLayout(null);
	}
	// Roll Die Function
	protected void rollDie(){
		diceNum = random_num.nextInt(6)+1;
		img = new ImageIcon(rootPath+"die"+diceNum+".png").getImage();
		repaint();
	}
	public int getDiceNum(){return diceNum;}
    public void paintComponent(Graphics g) {
        g.drawImage(img, 50, 0, null);
    }
	public static void main(String[] args){
		Random random = new Random();
		int random_int = random.nextInt(6);
		for(int i=0;i<10;i++){
			random_int= random.nextInt(6);
			System.out.println(random_int);
		}
	}
}
