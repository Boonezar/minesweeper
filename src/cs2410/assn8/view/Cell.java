package cs2410.assn8.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author Samuel Christiansen
 *@version 1.0
 *
 *A class that contains the information for the cells
 *in my Minesweeper
 */

public class Cell extends JPanel{

	private ImageIcon flagIcon = new ImageIcon(new ImageIcon(this.getClass().getResource("/images/flagImage.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));
	private ImageIcon greenBombIcon = new ImageIcon(new ImageIcon(this.getClass().getResource("/images/greenBomb.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
	private ImageIcon yellowFlagIcon = new ImageIcon(new ImageIcon(this.getClass().getResource("/images/yellowFlag.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
	private ImageIcon redBombIcon = new ImageIcon(new ImageIcon(this.getClass().getResource("/images/redBomb.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
	private ImageIcon questionIcon = new ImageIcon(new ImageIcon(this.getClass().getResource("/images/questionMark.png")).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
	private JLabel imageLabel = new JLabel();
	
	public boolean hasBomb = false;
	public boolean isFlagged = false;
	public boolean isCleared = false;
	public boolean isQuestioned = false;
	public boolean listenersOn = true;
	public int neighborBombCount = 0;
	public int rightClickCount = 0;
	public int cellHeight;
	public int cellWidth;
	
	private enum State{
		BLANK,
		FLAGGED,
		QUESTION;
	};
	private State cellState;
	
	
	public Cell(){
		this.add(imageLabel);
	}
	
	public void clearImage()
	{
		imageLabel.setIcon(null);
		imageLabel.setText(null);
	}

	public void revealBomb(boolean wonGame)
	{
		if(wonGame)
		{
			if(hasBomb)
			{
				imageLabel.setIcon(greenBombIcon);
				return;
			}
		}
		if(isFlagged&&!hasBomb)
		{
			imageLabel.setIcon(yellowFlagIcon);
		}
		else if(isFlagged)
		{
			imageLabel.setIcon(greenBombIcon);
		}
		else if(!isFlagged&&hasBomb)
		{
			imageLabel.setIcon(redBombIcon);
		}
	}
	
	public void leftClick()
	{
		imageLabel.setIcon(null);
		imageLabel.setText(""+neighborBombCount);
		listenersOn = false;
		isCleared = true;
	}
	
	public void rightClick()
	{
		
		rightClickCount++;
		if(rightClickCount%3==1)
		{
			imageLabel.setIcon(flagIcon);
			isFlagged = true;
		}
		else if(rightClickCount%3==2)
		{
			imageLabel.setIcon(questionIcon);
			isFlagged = false;
			isQuestioned = true;
		}
		else if(rightClickCount%3==0)
		{
			imageLabel.setIcon(null);
			isQuestioned = false;
		}
	}

	
	//I got this from a stackoverflow result on how to make the panels look nice.
	//http://stackoverflow.com/questions/8472083/how-to-draw-a-jpanel-as-a-nimbus-jbutton
	private final int gradientSize = 18;
	private final Color lighterColor = new Color(250, 250, 250);
    private final Color darkerColor = new Color(225, 225, 230);
    private final Color edgeColor = new Color(140, 145, 145);
    private final Stroke edgeStroke = new BasicStroke(1);
    private final GradientPaint upperGradient = new GradientPaint(
            0, 0, lighterColor,
            0, gradientSize, darkerColor);
	    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint lowerGradient = new GradientPaint(
                0, getHeight()-gradientSize-1, darkerColor,
                0, getHeight(), lighterColor);
        g2.setPaint(upperGradient);
        g2.fillRect(0, 0, getWidth()-1 , gradientSize);
        g2.setPaint(darkerColor);
        g2.fillRect(0, gradientSize, getWidth()-1, getHeight()-gradientSize-1);
        g2.setPaint(lowerGradient);
        g2.fillRect(0, getHeight()-gradientSize, getWidth()-1, getHeight()-1);
        g2.setStroke(edgeStroke);
        g2.setPaint(edgeColor);
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1,
                               gradientSize/2, gradientSize/2);
    }
    //end of stackoverflow resource
	
}
