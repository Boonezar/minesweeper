package cs2410.assn8.view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Samuel Christiansen
 *@version 1.0
 *
 *A class that contains the Scoreboard information
 *for my Minesweeper game.
 */

public class Scoreboard extends JPanel{
	private int bombsLeft = 100;
	private int timeCounter = 0;
	private Timer timer = new Timer(1000, new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			timeCounter+=1;
			updateScoreboardView();
			//System.out.println(timeCounter);
		}
		
	});
	private JLabel bombsLeftLabel = new JLabel();
	private JLabel timeLabel = new JLabel();
	private JButton startBtn = new JButton("Start New Game");
	
	public Scoreboard() {
		this.setLayout(new GridLayout(1,3));
		timeLabel.setFont(new Font("Papyrus",Font.BOLD+Font.ITALIC, 15));
		startBtn.setFont(new Font("Papyrus",Font.BOLD+Font.ITALIC, 15));
		bombsLeftLabel.setFont(new Font("Papyrus",Font.BOLD+Font.ITALIC, 15));
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(timeLabel);
		startBtn.setHorizontalAlignment(JLabel.CENTER);
		this.add(startBtn);
		bombsLeftLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(bombsLeftLabel);
		this.updateScoreboardView();
	}
	
	public int getTimer()
	{
		return timeCounter;
	}
	
	public void startTimer()
	{
		timer.start();
	}
	
	public void endTimer()
	{
		timer.stop();
	}
	
	public void resetTimer()
	{
		timeCounter = 0;
		bombsLeft = 100;
		updateScoreboardView();
	}
	
	public void updateFlagCount(boolean minus)
	{
		if(minus)
		{
			bombsLeft--;
			//System.out.println("bomb minus");
		}
		else
		{
			bombsLeft++;
			//System.out.println("bomb plus");
		}
		updateScoreboardView();
	}
	
	public void addStartListener(ActionListener list)
	{
		startBtn.addActionListener(list);
	}
	
	public void disableStart()
	{
		startBtn.setEnabled(false);
	}
	
	public void enableStart()
	{
		startBtn.setEnabled(true);
	}
	
	private void updateScoreboardView() {
		timeLabel.setText("Timer: " + timeCounter);
		bombsLeftLabel.setText("Bombs Left: " + bombsLeft);
		//this.update(this.getGraphics());
	}
}

