package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RollDice extends JFrame {
	JButton RollDice;
	JLabel Result;
	ImagePanel Dice1;
	ImagePanel Dice2;
	private int dieSum;
	public RollDice() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.LIGHT_GRAY);
		setupPanel();
		setSize(600,400);
		setVisible(true);
	}
	public void setupPanel(){
		JPanel top_panel = new JPanel();
		JPanel bottom_panel = new JPanel();
		Dice1 = new ImagePanel(1);
		Dice2 = new ImagePanel(1);
		RollDice = new JButton("Roll Dice");
		dieSum = Dice1.getDiceNum()+Dice2.getDiceNum();
		Result = new JLabel("Result: "+ dieSum);
		Result.setAlignmentX(Component.CENTER_ALIGNMENT);
		RollDice.setAlignmentX(Component.CENTER_ALIGNMENT);
		top_panel.setLayout(new GridLayout(1,2));
		top_panel.add(Dice1);
		top_panel.add(Dice2);
		bottom_panel.add(Result);
		bottom_panel.add(RollDice);
		bottom_panel.setLayout(new BoxLayout(bottom_panel,BoxLayout.Y_AXIS));
		this.add(top_panel,BorderLayout.CENTER);
		this.add(bottom_panel,BorderLayout.SOUTH);
		RollDice.addActionListener(new RollDieListener());
		Dice1.addMouseListener(new ClickRollListener1());
		Dice2.addMouseListener(new ClickRollListener2());
	}
	class RollDieListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Dice1.rollDie();
			Dice2.rollDie();
			dieSum = Dice1.getDiceNum()+Dice2.getDiceNum();
			Result.setText("Result: "+ dieSum);
		}
	}
	// Add Mouse Click Listener
	class ClickRollListener1 extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			Dice1.rollDie();
			dieSum = Dice1.getDiceNum()+Dice2.getDiceNum();
			Result.setText("Result: "+ dieSum);
		}
	}
	class ClickRollListener2 extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			Dice2.rollDie();
			dieSum = Dice1.getDiceNum()+Dice2.getDiceNum();
			Result.setText("Result: "+ dieSum);
		}
	}


	public static void main(String[] args) {
		RollDice rollDice = new RollDice();
	
	}
}
