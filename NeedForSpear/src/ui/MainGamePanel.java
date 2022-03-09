package ui;

import model.Constants;
import model.abilities.Ability;
import model.abilities.ChanceGiving;
import model.abilities.DoubleAccel;
import model.abilities.HollowPurple;
import model.abilities.InfiniteVoid;
import model.abilities.MagicalHex;
import model.abilities.NoblePhantasmExpansion;
import model.abilities.UnstoppableEnchantedSphere;
import model.controller.EnchantedSphereController;
import model.controller.GameController;
import model.controller.NoblePhantasmController;
import model.obstacle.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.geom.*;
import java.util.HashMap;
 
public class MainGamePanel extends JPanel implements ActionListener, KeyListener{
	
	private GameController gameController = new GameController();
	private EnchantedSphereController sphereController = gameController.esController();
	private NoblePhantasmController phantasmController = gameController.npController();
	private String movedirection = "STOP";
	private Timer timer;
	private int delay = 2;
	private boolean rotateFlag = false;
	private JPanel topPanelLeft = new JPanel();
	private JPanel topPanel = new JPanel();
	private JLabel inventoryLabel = new JLabel();
	private JLabel scoreLabel = new JLabel(); 
	private JLabel remainingChancesLabel = new JLabel();
	private JLabel timeLabel = new JLabel();
	
	public MainGamePanel() {
		addKeyListener(this);
		setFocusable(true);
		setLayout(new BorderLayout());
		timer = new Timer(delay, this);
		
		scoreLabel.setText("Score: " + gameController.getScore());
		remainingChancesLabel.setText("Remaining Chances: " + gameController.getNumberOfChances());
		timeLabel.setText("Time 0:00");
		
		remainingChancesLabel.setSize(100, 50);
		scoreLabel.setSize(100, 50);
		timeLabel.setSize(100, 50);
		scoreLabel.setBorder(new EmptyBorder(0,0,0,50));
		remainingChancesLabel.setBorder(new EmptyBorder(0,0,0,50));
		inventoryLabel.setSize(400, 50);
		
		
		topPanelLeft.setSize(400, 50);
		topPanelLeft.setBorder(new EmptyBorder(0,0,0,50));
		topPanelLeft.add(scoreLabel);
		topPanelLeft.add(remainingChancesLabel);
		topPanelLeft.add(timeLabel);
		
		
		topPanel.setLayout(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.setSize(Constants.FRAME_X, 50);
		topPanel.add(topPanelLeft, BorderLayout.LINE_START);
		topPanel.add(inventoryLabel, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if (rotateFlag && phantasmController.getAngle() != 0) {
			phantasmController.rotateBack();
		} else rotateFlag = false;
		Graphics2D graphics = (Graphics2D) g;
		paintEnchantedSphere(graphics);	
		paintNoblePhantasm(graphics);
		paintObstacles(graphics);
		paintParticles(graphics);
		paintHexes(graphics);
		setUpInventory();
		paintWalls(graphics);
		
		g.dispose();
		
	}
	
	private void paintWalls(Graphics2D g) {
		g.setColor(Color.black);
		g.fill(new Rectangle2D.Double(0, 40, Constants.FRAME_X, 2));
		g.fill(new Rectangle2D.Double(0, Constants.FRAME_Y - 40, Constants.FRAME_X, 40));
	}

	private void paintObstacles(Graphics2D g) {
		for(Obstacle i :gameController.getObstacles()) {
			paintObstacle(g, i);
		}	
	}
	
	private void paintObstacle(Graphics2D g, Obstacle i) {
		
		if (i instanceof SimpleObstacle) {
			g.setColor(Color.gray);
			g.fill(new Rectangle2D.Double(i.getPosx(),i.getPosy(),Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT));
		}
		else if (i instanceof FirmObstacle) {
			g.setColor(Color.black);
			g.fill(new Rectangle2D.Double(i.getPosx(),i.getPosy(),Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT));
			g.setColor(Color.white);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 9));
			g.drawString(Integer.toString(i.getRemainingHit()),(int) i.getPosx() + (Constants.OBSTACLE_WIDTH/2) - 2,(int) i.getPosy() + (Constants.OBSTACLE_HEIGHT/2)+3);
		}
		else if (i instanceof ExplosiveObstacle) {
			g.setColor(Color.orange);
	        g.fill(new Ellipse2D.Double(i.getPosx(),i.getPosy(), Constants.CIRCLE_OBSTACLE_WIDTH, Constants.CIRCLE_OBSTACLE_WIDTH));
		}		
		else if (i instanceof GiftObstacle) {
			g.setColor(Color.pink);
			g.fill(new Rectangle2D.Double(i.getPosx(),i.getPosy(),Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT));
		}
		
		else if (i instanceof HollowPurpleObstacle) {
			g.setColor(Color.magenta);
			g.fill(new Rectangle2D.Double(i.getPosx(),i.getPosy(),Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT));
		}
	     
	}

	private void paintEnchantedSphere(Graphics2D g) {
		g.setColor(Color.red);
        g.fill(new Ellipse2D.Double(sphereController.getPosX(), sphereController.getPosY(), 15, 15));
	}
	
	private void paintNoblePhantasm(Graphics2D g) {
		 g.setColor(Color.black);
		 Rectangle2D paddle = new Rectangle(phantasmController.getPosX(), Constants.FRAME_Y - 100, phantasmController.getWidth(), 20);
		 AffineTransform t = new AffineTransform();
		 double centerX = phantasmController.getPosX() + phantasmController.getWidth()/2;
		 double centerY = GameController.getFRAME_Y() - 100;
		 t.rotate(Math.toRadians(phantasmController.getAngle()),centerX,centerY);
		 Shape paddleup = t.createTransformedShape(paddle);
	     g.fill(paddleup);
	     
	     if(phantasmController.hasCannons()) {
	     Rectangle2D leftCannon = new Rectangle(phantasmController.getPosX() - 5, Constants.FRAME_Y - 120, 10, 30);
	     Rectangle2D rightCannon = new Rectangle(phantasmController.getPosX() + phantasmController.getWidth() - 5, Constants.FRAME_Y - 120, 10, 30);
		 Shape leftCannonRotated = t.createTransformedShape(leftCannon);
		 Shape rightCannonRotated = t.createTransformedShape(rightCannon);
	     g.fill(leftCannonRotated);
	     g.fill(rightCannonRotated);
	     
	     }
	}

	private void paintParticles(Graphics2D g) {
		for (Particle i : gameController.getParticles()) {
			if (i != null) {
			i.setPosy(i.getPosy()+0.4);
			paintParticle(g,i);
			}
		}
	}

	private void paintParticle(Graphics2D g, Particle i) {
		if (i.getType().equals("ExplosiveParticle")) {
			g.setColor(Color.orange);
	        g.fill(new Ellipse2D.Double(i.getPosx(),i.getPosy(), 15, 15));
		}
		else if (i.getType().equals("GiftParticle")) {
			g.setColor(Color.pink);
			g.fill(new Rectangle2D.Double (i.getPosx(), i.getPosy(),i.getWidth(),i.getHeight()));
		}
		else if (i.getType().equals("MagicalHex")) {
			g.setColor(Color.black);
			g.fill(new Rectangle2D.Double (i.getPosx(), i.getPosy(),i.getWidth(),i.getHeight()));
		}
		
	}
	
	private void paintHexes(Graphics2D g) {
		for (Particle i : phantasmController.getHexes()) {
			if (i != null && i.getPosy() >= 50) {
			i.setPosy(i.getPosy()-1);
			paintParticle(g,i);
			}
		}
	}
	
	private void setUpInventory() {
		
		int magicalHex = gameController.getSpecificUnusedAbiltyAmount(Constants.nonYmirAbilityTypes[1]);
		int expansion = gameController.getSpecificUnusedAbiltyAmount(Constants.nonYmirAbilityTypes[2]);
		int unstoppable = gameController.getSpecificUnusedAbiltyAmount(Constants.nonYmirAbilityTypes[3]);
		inventoryLabel.setText("Magical Hex: "+ magicalHex + " || NoblePhantasmExpansion: " + expansion + " || Unstoppable: " + unstoppable);
	}
	
	private void calculateTime() {
		int seconds = gameController.getTime();
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        timeLabel.setText("Time " + minutes + (remainingSeconds < 10 ? ":0" : ":") + remainingSeconds);
        
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		 if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			phantasmController.moveRight();
			
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			phantasmController.moveLeft();
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_Y || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameController.changeStatus();
			if(gameController.getStatus()) {
				timer.start();
			} else {
				timer.stop();	
				GameUI.showPauseMenu();
			}
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER || (e.getKeyCode() == KeyEvent.VK_SPACE && !phantasmController.hasCannons())) {
			//press enter to start
			gameController.changeStatus();
			timer.start();
			
		} else if(gameController.getStatus()) {
			
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			movedirection = "RIGHT";
			
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			movedirection = "LEFT";
			
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			phantasmController.rotateLeft();
			
		} else if(e.getKeyCode() == KeyEvent.VK_D) {
			phantasmController.rotateRight();
			
		} //Ability actions
		  else if(e.getKeyCode() == KeyEvent.VK_N) {
			gameController.useNoblePhantasmExpansion();
			
		} else if(e.getKeyCode() == KeyEvent.VK_M) {
			gameController.useMagicalHex();
			
		} else if(e.getKeyCode() == KeyEvent.VK_U) {
			gameController.useUnstoppable();
			
		} else if(e.getKeyCode() == KeyEvent.VK_SPACE && phantasmController.hasCannons()) {
			phantasmController.generateHex();
		}
	}		
}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
			rotateFlag = true;
		}	
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			movedirection = "STOP";
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		calculateTime();
		winCheck();
		gameController.collisionHandler();
		scoreLabel.setText("Score: " + gameController.getScore());
		sphereController.move();
		phantasmController.move(movedirection);
		
		//move the obstacles
		for(Obstacle o : gameController.getObstacles()) {
			o.move();
		}
		
		//check if ball passed the paddle
		if(sphereController.getChanceLost()) {
			sphereController.setChanceLost(false);
			gameController.changeStatus();
			timer.stop();
		}
		
		remainingChancesLabel.setText("Remaining Chances: " + gameController.getNumberOfChances());
		
		//lost the game if no chance remaining
		if(gameController.lose()) {
			gameController.changeStatus();
			timer.stop();
			JOptionPane.showMessageDialog(this, "YOU LOST!");
			if(JOptionPane.OK_OPTION == 0) {
				GameUI.showMainMenu();
			}
		}	
		repaint();
		
	}

	public void winCheck() {
		if (gameController.getObstacles().size() == 0) {
			gameController.changeStatus();
			timer.stop();
			JOptionPane.showMessageDialog(this, "You Win! Score: " + gameController.getScore());
			if(JOptionPane.OK_OPTION == 0) {
				GameUI.showMainMenu();
			}
		}
	}

}
