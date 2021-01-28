package cs2410.assn8.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


//import cs2410.assn7.components.ColorPanel;
import cs2410.assn8.view.Cell;
import cs2410.assn8.view.Scoreboard;

/**
 * @author Samuel Christiansen
 *@version 1.0
 *
 *A class to run the game play of Minesweeper
 */
public class Game implements MouseListener, ActionListener {
	private JFrame frame = new JFrame("Minesweeperish");
	private JPanel gameArea = new JPanel();
	private Scoreboard scoreboard = new Scoreboard();
	private int height = 24;
	private int width = 24;
	private int bombCount = 100;
	private boolean firstClick = true;
	private boolean wonGame = false;
	private Cell[][] cells = new Cell[height][width];
	private int panelsCleared = 0;
	private JFrame winningFrame = new JFrame("Winner!");
	private JFrame losingFrame = new JFrame("Sorry...");
	private JLabel winningLabel;
	private JLabel losingLabel;
	
	private Game(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel pane = (JPanel)frame.getContentPane();
		pane.setLayout(new BorderLayout());
		winningFrame.setSize(250,75);
		losingFrame.setSize(250, 75);
		
		gameArea.setLayout(new GridLayout(24, 24));
		gameArea.setPreferredSize(new Dimension(600, 600));
		addListeners(gameArea);
		addBombs();
		countNeighborBombs();
		pane.add(gameArea);	
		scoreboard.addStartListener(this);
		pane.add(scoreboard, BorderLayout.NORTH);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void addListeners(JPanel gameArea)
	{
		for (int i = 0; i < height; i++) 
		{
			for(int j = 0; j<width; j++)
			{
			cells[i][j] = new Cell();
			cells[i][j].cellHeight = i;
			cells[i][j].cellWidth = j;
			cells[i][j].addMouseListener(this);
			gameArea.add(cells[i][j]);
			}
		}
	}
	
	private void addBombs()
	{
		for(int i =0; i<bombCount; i++)
		{
			Random rand = new Random();
			int xRandom = rand.nextInt(width);
			int yRandom = rand.nextInt(height);
			while(cells[yRandom][xRandom].hasBomb == true)
			{
				xRandom = rand.nextInt(width);
				yRandom = rand.nextInt(height);
			}
			cells[yRandom][xRandom].hasBomb = true;
		}
	}
	
	private void countNeighborBombs()
	{
		for(int i= 0; i<height; i++)
		{
			for(int j=0; j<width; j++)
			{
				try{
					if(cells[i-1][j-1].hasBomb)
						cells[i][j].neighborBombCount++;
				}catch(ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("top left");
				}
				try
				{
					if(cells[i-1][j].hasBomb)
						cells[i][j].neighborBombCount++;
				}catch(ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("top");
				}
				try{
					if(cells[i-1][j+1].hasBomb)
						cells[i][j].neighborBombCount++;
				}catch(ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("top right");
				}
				try{
					if(cells[i][j-1].hasBomb)
						cells[i][j].neighborBombCount++;
				}catch(ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("left");
				}
				try{
					if(cells[i][j+1].hasBomb)
						cells[i][j].neighborBombCount++;
				}catch(ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("right");
				}
				try{
					if(cells[i+1][j-1].hasBomb)
						cells[i][j].neighborBombCount++;
				}catch(ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("top left");
				}
				try{
					if(cells[i+1][j].hasBomb)
						cells[i][j].neighborBombCount++;
				}catch(ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("top left");
				}
				try{
					if(cells[i+1][j+1].hasBomb)
						cells[i][j].neighborBombCount++;
				}catch(ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("top left");
				}
			}
		}
	}

	private void revealNeighborBombs(int x, int y)
	{
		cells[x][y].leftClick();
		panelsCleared++;
		if(panelsCleared==(24*24)-bombCount)
		{
			winGame();
		}
		if(cells[x][y].isFlagged)
			scoreboard.updateFlagCount(true);
		else if(cells[x][y].isQuestioned)
			scoreboard.updateFlagCount(false);
		try{
			if(cells[x-1][y-1].isCleared)
			{
				//do nothing
			}
			else if(cells[x-1][y-1].neighborBombCount==0)
				revealNeighborBombs(x-1,y-1);
			else
			{
				cells[x-1][y-1].leftClick();
				panelsCleared++;
				if(panelsCleared==(24*24)-bombCount)
				{
					winGame();
				}
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			//System.out.println("top left");
		}
		try
		{
			if(cells[x-1][y].isCleared)
			{
				//do nothing
			}
			else if(cells[x-1][y].neighborBombCount==0)
				revealNeighborBombs(x-1, y);
			else
			{
				cells[x-1][y].leftClick();
				panelsCleared++;
				if(panelsCleared==(24*24)-bombCount)
				{
					winGame();
				}
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			//System.out.println("top");
		}
		try{
			if(cells[x-1][y+1].isCleared)
			{
				//do nothing
			}
			else if(cells[x-1][y+1].neighborBombCount==0)
				revealNeighborBombs(x-1,y+1);
			else
			{
				cells[x-1][y+1].leftClick();
				panelsCleared++;
				if(panelsCleared==(24*24)-bombCount)
				{
					winGame();
				}
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			//System.out.println("top right");
		}
		try{
			if(cells[x][y-1].isCleared)
			{
				//do nothing
			}
			else if(cells[x][y-1].neighborBombCount==0)
				revealNeighborBombs(x,y-1);
			else
			{
				cells[x][y-1].leftClick();
				panelsCleared++;
				if(panelsCleared==(24*24)-bombCount)
				{
					winGame();
				}
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			//System.out.println("left");
		}
		try{
			if(cells[x][y+1].isCleared)
			{
				//do nothing
			}
			else if(cells[x][y+1].neighborBombCount==0)
				revealNeighborBombs(x,y+1);
			else
			{
				cells[x][y+1].leftClick();
				panelsCleared++;
				if(panelsCleared==(24*24)-bombCount)
				{
					winGame();
				}
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			//System.out.println("right");
		}
		try{
			if(cells[x+1][y-1].isCleared)
			{
				//do nothing
			}
			else if(cells[x+1][y-1].neighborBombCount==0)
				revealNeighborBombs(x+1,y-1);
			else
			{
				cells[x+1][y-1].leftClick();
				panelsCleared++;
				if(panelsCleared==(24*24)-bombCount)
				{
					winGame();
				}
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			//System.out.println("top left");
		}
		try{
			if(cells[x+1][y].isCleared)
			{
				//do nothing
			}
			else if(cells[x+1][y].neighborBombCount==0)
				revealNeighborBombs(x+1,y);
			else
			{
				cells[x+1][y].leftClick();
				panelsCleared++;
				if(panelsCleared==(24*24)-bombCount)
				{
					winGame();
				}
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			//System.out.println("top left");
		}
		try{
			if(cells[x+1][y+1].isCleared)
			{
				//do nothing
			}
			else if(cells[x+1][y+1].neighborBombCount==0)
				revealNeighborBombs(x+1,y+1);
			else
			{
				cells[x+1][y+1].leftClick();
				panelsCleared++;
				if(panelsCleared==(24*24)-bombCount)
				{
					winGame();
				}
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{
			//System.out.println("top left");
		}
		
	}
	
	private void endGame()
	{
		scoreboard.endTimer();
		for (int i = 0; i < height; i++) 
		{
			for(int j = 0; j<width; j++)
			{
				cells[i][j].revealBomb(wonGame);
				cells[i][j].listenersOn = false;
			}
		}
		if(wonGame)
		{
			JPanel winningPane = (JPanel)winningFrame.getContentPane();
			winningPane.removeAll();
			winningLabel = new JLabel("YAY! \nYou won in " +scoreboard.getTimer()+" seconds!",SwingConstants.CENTER);
			winningPane.add(winningLabel);
			winningFrame.setLocationRelativeTo(null);
		
			winningFrame.setVisible(true);
		}
		else
		{
			JPanel losingPane = (JPanel)losingFrame.getContentPane();
			losingPane.removeAll();
			losingLabel = new JLabel("Ah... You blew up a mine... Play again!", SwingConstants.CENTER);
			losingPane.add(losingLabel);
			losingFrame.setLocationRelativeTo(null);
			
			losingFrame.setVisible(true);
		}
	}
	
	private void winGame()
	{
		wonGame = true;
		endGame();
	}
	
	private void resetGame()
	{
		scoreboard.endTimer();
		for (int i = 0; i < height; i++) 
		{
			for(int j = 0; j<width; j++)
			{
				cells[i][j].hasBomb=false;
				cells[i][j].isFlagged=false;
				cells[i][j].isCleared=false;
				cells[i][j].neighborBombCount=0;
				cells[i][j].rightClickCount = 0;
				cells[i][j].clearImage();
				cells[i][j].listenersOn = true;
			}
		}
		addBombs();
		countNeighborBombs();
		firstClick = true;
		wonGame = false;
		panelsCleared = 0;
		scoreboard.resetTimer();
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {    //Is this correct for EDT?
				new Game();				
			}});
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//endGame();
		resetGame();
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			Cell temp = (Cell)e.getSource();
			if(temp.listenersOn)
			{
				if(!temp.isFlagged)
				{
					if(firstClick)
					{
						scoreboard.startTimer();
						firstClick = false;
					}
					if(temp.hasBomb)
					{
						endGame();
					}
					else if(temp.neighborBombCount==0)
					{
						revealNeighborBombs(temp.cellHeight,temp.cellWidth);
					}
					else
					{
						temp.leftClick();
						panelsCleared++;
						if(panelsCleared==(24*24)-bombCount)
						{
							winGame();
						}
					}
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			//System.out.println("button3");
			Cell temp = (Cell)e.getSource();
			if(temp.listenersOn)
			{
				temp.rightClick();
				if(temp.isFlagged)
					scoreboard.updateFlagCount(true);
				else if(temp.isQuestioned)
					scoreboard.updateFlagCount(false);
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}