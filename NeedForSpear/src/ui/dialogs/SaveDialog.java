package ui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.controller.GameController;
import ui.GameUI;

public class SaveDialog extends JPanel implements KeyListener, ActionListener{
	private GameController gameController = new GameController();
	private JButton save = new JButton("Save");
	private JTextField saveName = new JTextField(10);
	
	public SaveDialog() {
		addKeyListener(this);
		setFocusable(true);
		setLayout(new GridBagLayout());
		setBackground(Color.GRAY);
		setSize(200,200);
		setBounds(0,0,100,100);
		
		save.addActionListener(this);
		
		Dimension buttonDim = new Dimension(100,25);
		save.setPreferredSize(buttonDim);
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(saveName, gbc);
		add(save, gbc);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(saveName.getText() != null) {
				gameController.save(saveName.getText());
			} else {
				gameController.save("Saved Game");
			}
			GameUI.showMainMenu();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) {
			if(saveName.getText() != null) {
				gameController.save(saveName.getText());
			} else {
				gameController.save("Saved Game");
			}
			gameController.cancelTimer();
			GameUI.showMainMenu();
		}
	}
}
