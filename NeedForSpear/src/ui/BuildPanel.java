package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.event.*;

import model.Constants;
import model.controller.GameController;
import model.factory.FactoryObstacle;
import model.obstacle.ExplosiveObstacle;
import model.obstacle.FirmObstacle;
import model.obstacle.GiftObstacle;
import model.obstacle.Obstacle;
import model.obstacle.SimpleObstacle;

public class BuildPanel extends JPanel implements ActionListener, MouseInputListener, KeyListener {

	private GameController gameController = new GameController();
	ArrayList<Obstacle> obstacleList = GameController.getObstacles();
	private JComboBox<String> obsSelect = new JComboBox<String>();
	private JButton obsAddButton;
	private JButton playButton;
	private JButton developer;
	private ArrayList<String> obstacleNames = new ArrayList<String>();
	private JLabel simpleLabel;
	private JLabel firmLabel;
	private JLabel explosiveLabel;
	private JLabel giftLabel;
	private JLabel numberOfObstaclesLabel;
	private JTextField numberOfSimpleText;
	private JTextField numberOfFirmText;
	private JTextField numberOfExplosiveText;
	private JTextField numberOfGiftText;
	private JPanel North = new JPanel();
	private JPanel West = new JPanel();
	private JPanel South = new JPanel();
	private CenterPanel Center = new CenterPanel();

	private int simple = 0;
	private int firm = 0;
	private int explosive = 0;
	private int gift = 0;


	double preX, preY;
	boolean pressOut = false;
	Rectangle2D obs1, obs2;
	Iterator<Obstacle> it;
	Obstacle i;

	public BuildPanel() {

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		createObstacleNameList();
		createObstacleComboBox();
		setLayout(new BorderLayout());

		obsAddButton = new JButton("Add Obstacles");
		playButton = new JButton("Play");
		developer = new JButton("Developer");
		obsAddButton.addActionListener(this);
		playButton.addActionListener(this);
		developer.addActionListener(this);

		simpleLabel = new JLabel();
		firmLabel = new JLabel();
		explosiveLabel = new JLabel();
		giftLabel = new JLabel();
		numberOfObstaclesLabel = new JLabel();

		numberOfSimpleText = new JTextField(3);
		numberOfFirmText = new JTextField(3);
		numberOfExplosiveText = new JTextField(3);
		numberOfGiftText = new JTextField(3);

		numberOfSimpleText.setText("0");
		numberOfFirmText.setText("0");
		numberOfExplosiveText.setText("0");
		numberOfGiftText.setText("0");

		simpleLabel.setText("Number of simple obstacles");
		firmLabel.setText("Number of firm obstacles");
		explosiveLabel.setText("Number of explosive obstacles");
		giftLabel.setText("Number of gift obstacles");
		numberOfObstaclesLabel.setText("# of Simple: "+ simple +" || # of Firm: "+ firm +" || # of Explosive: "+ explosive +" || # of Gift: " + gift);

		South.add(obsSelect);
		South.add(playButton);
		South.add(developer);
		South.add(numberOfObstaclesLabel);
		North.add(simpleLabel);
		North.add(numberOfSimpleText);
		North.add(firmLabel);
		North.add(numberOfFirmText);
		North.add(explosiveLabel);
		North.add(numberOfExplosiveText);
		North.add(giftLabel);
		numberOfGiftText.setSize(10, 30);

		North.add(numberOfGiftText);
		North.add(obsAddButton);

		add(North, BorderLayout.NORTH);
		add(West, BorderLayout.WEST);
		add(South, BorderLayout.SOUTH);
		add(Center, BorderLayout.CENTER);
	}


	public class CenterPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			paintObstacles(g2d);
		}
	}

	private void createObstacleNameList() {
		obstacleNames.add("SimpleObstacle");
		obstacleNames.add("FirmObstacle");
		obstacleNames.add("GiftObstacle");
		obstacleNames.add("ExplosiveObstacle");
	}

	private void createObstacleComboBox() {
		if (obstacleNames != null) {
			for (int i = 0; i < obstacleNames.size(); i++) {
				obsSelect.addItem(obstacleNames.get(i));
			}
		}
		obsSelect.setEditable(false);
	}

	private void paintObstacles(Graphics2D g) {
		for (Obstacle i : gameController.getObstacles()) {
			paintObstacle(g, i);

		}
	}

	private void paintObstacle(Graphics2D g, Obstacle i) {

		if (i instanceof SimpleObstacle) {
			g.setColor(Color.gray);
			g.fill(new Rectangle2D.Double(i.getPosx(), i.getPosy(), Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT));
		}
		else if (i instanceof FirmObstacle) {
			g.setColor(Color.black);
			g.fill(new Rectangle2D.Double(i.getPosx(), i.getPosy(), Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT));
		}
		else if (i instanceof ExplosiveObstacle) {
			g.setColor(Color.orange);
	        g.fill(new Ellipse2D.Double(i.getPosx(),i.getPosy(), Constants.CIRCLE_OBSTACLE_WIDTH, Constants.CIRCLE_OBSTACLE_WIDTH));
		}
		else if (i instanceof GiftObstacle) {
			g.setColor(Color.pink);
			g.fill(new Rectangle2D.Double(i.getPosx(), i.getPosy(), Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT));
		}
	}

	public boolean validateObstacleNumber() {
		simple = 0;
		firm = 0;
		explosive = 0;
		gift = 0;

		for (Obstacle o : gameController.getObstacles()) {
			if (o instanceof SimpleObstacle) {
				simple++;
			}
			else if (o instanceof FirmObstacle) {
				firm++;
			}
			else if (o instanceof ExplosiveObstacle) {
				explosive++;
			}
			else if (o instanceof GiftObstacle) {
				gift++;
			}
		}
		numberOfObstaclesLabel.setText("# of Simple: "+ simple +" || # of Firm: "+ firm +" || # of Explosive: "+ explosive +" || # of Gift: " + gift);
		if (simple >= 75 && firm >= 10 && explosive >= 5 && gift >= 10) {
			return true;

		} else {
			return false;
		}
	}

	//This method add a obstacle to the position of mouse at the time of left click
	//and removes a a obstacle to the position of mouse at the time of right click
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			String name = (String) obsSelect.getSelectedItem();
			gameController.addSingleObstacle(name, e.getX() - (Constants.OBSTACLE_WIDTH / 2) -10 , e.getY() - (Constants.OBSTACLE_HEIGHT / 2) - 35);

			Center.revalidate();
			Center.repaint();

		} else if (e.getButton() == MouseEvent.BUTTON3) {


			// removing the obstacles
			Rectangle2D obs1, obs2;
			obs1 = new Rectangle2D.Double(e.getX(), e.getY(), Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT);
			Iterator<Obstacle> it = obstacleList.iterator();
			while (it.hasNext()) {
				Obstacle i = it.next();
				obs2 = new Rectangle2D.Double(i.getPosx() + 10, i.getPosy() + 35, Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT);
				// remove shape from obstacleList
				if (obs1.intersects(obs2)) {
					it.remove();
					gameController.setMaxObstacles(false);

					Center.revalidate();
					Center.repaint();
				}
			}
		}
		validateObstacleNumber();
	}

	@Override
	public void mousePressed(MouseEvent e) {

		obs1 = new Rectangle2D.Double(e.getX(), e.getY(), Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT);
		Iterator<Obstacle> it = obstacleList.iterator();
		while (it.hasNext()) {
			i = it.next();
			obs2 = new Rectangle2D.Double(i.getPosx() + 10, i.getPosy() + 35, Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT);
			// remove shape from obstacleList
			if (obs1.intersects(obs2)) {

				preX = i.getPosx() - e.getX();
				preY = i.getPosy() - e.getY();
			}
		}
		Center.revalidate();
		Center.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(i != null) {
			double CurrentX = e.getX() + preX;
			double CurrentY = e.getY()+ preY;
			
			if(gameController.canAdd(CurrentX, CurrentY)) {

				i.setPosx(CurrentX);
				i.setPosy(CurrentY);

			}
			else {
				i.setPosx(preX);
				i.setPosy(preY);

			}

			i.setPosx(CurrentX);
			i.setPosy(CurrentY);

		}
		Center.revalidate();
		Center.repaint();
	}


	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == obsAddButton) {
			int simple = Integer.valueOf(numberOfSimpleText.getText());
			int firm = Integer.valueOf(numberOfFirmText.getText());
			int explosive = Integer.valueOf(numberOfExplosiveText.getText());
			int gift = Integer.valueOf(numberOfGiftText.getText());
			gameController.addObstacles(simple, firm, explosive, gift);
			Center.revalidate();
			Center.repaint();

		} else if (e.getSource() == playButton) {
			if(validateObstacleNumber()) {
				gameController.changeMode();
				gameController.startTimer();
				GameUI.showPlayMode();
			} else {
				JOptionPane.showMessageDialog(this, "There has to be at least 75 simple obstacles,\n"
						+ "At least 10 firm obstacles,\n"
						+ "At least 5 explosive-obstacles, and\n"
						+ "At least 10 gift obstacles");
			}

		} else if (e.getSource() == developer) {
			gameController.changeMode();
			gameController.startTimer();
			GameUI.showPlayMode();
		}

		validateObstacleNumber();
		numberOfSimpleText.setText("0");
		numberOfFirmText.setText("0");
		numberOfExplosiveText.setText("0");
		numberOfGiftText.setText("0");
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Y || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameUI.showPauseMenu();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
