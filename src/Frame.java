import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings({ "serial" })
public class Frame extends JFrame implements ActionListener, KeyListener {

	public final int WIDTH = 12;
	public final int LENGTH = 12;
	public JButton[][] buttons;
	public Container contentPane;
	public int player;

	public Frame() {
		super();
		this.setTitle("GAME");
		this.setSize(900, 800);
		this.setLocation(0, 0);
		this.setResizable(false);
		contentPane = this.getContentPane();
		contentPane.setLayout(new GridLayout(WIDTH, LENGTH));
		player = 1;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		buttons = new JButton[WIDTH][LENGTH];
		addJButtons();
		addPlayers();
	}

	public void addJButtons() {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				JButton jb = new JButton();
				jb.setBackground(Color.white);
				jb.setActionCommand(i + "," + j);
				jb.addActionListener(this);
				jb.addKeyListener(this);
				contentPane.add(jb);
				buttons[i][j] = jb;
			}
		}
	}

	public void addPlayers() {
		// for( int i = 0; i < 20; i ++){
		// buttons[(int)(Math.random()* WIDTH)][(int)(Math.random()*
		// LENGTH)].setBackground(Color.black);
		// }
		// for( int i = 0; i < 20; i ++){
		// buttons[(int)(Math.random()* WIDTH)][(int)(Math.random()*
		// LENGTH)].setBackground(Color.gray);
		// }

		buttons[2][2].setBackground(Color.red);
		buttons[WIDTH - 3][LENGTH - 3].setBackground(Color.blue);

	}

	public void checkWin(int player) {
		int redSquare = 0;
		int blueSquare = 0;
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				if (buttons[i][j].getBackground().equals(Color.white)) {
					if (player == 2)
						buttons[i][j].setBackground(Color.red);
					if (player == 1)
						buttons[i][j].setBackground(Color.blue);
				}
			}
		}
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				if (buttons[i][j].getBackground().equals(Color.red))
					redSquare++;
				if (buttons[i][j].getBackground().equals(Color.blue))
					blueSquare++;
			}
		}
		if (redSquare > blueSquare)
			System.out.println("Red wins");
		if (redSquare < blueSquare)
			System.out.println("Blue wins");
		if (redSquare == blueSquare)
			System.out.println("Tie");
	}

	public void actionPerformed(ActionEvent e) {

		// System.out.println(e.getActionCommand());
		String[] list = e.getActionCommand().split(",");
		int x = Integer.parseInt(list[0]);
		int y = Integer.parseInt(list[1]);
		Color compare = null;
		if (player == 1) {
			compare = Color.blue;
		}
		if (player == 2) {
			compare = Color.red;
		}
		if (checkSquare(compare, x, y) && buttons[x][y].getBackground().equals(Color.white)) {

			buttons[x][y].setBackground(compare);
			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {
					if (i >= 0 && j >= 0 && i < WIDTH && j < LENGTH) {
						if (!buttons[i][j].getBackground().equals(compare)
								&& !buttons[i][j].getBackground().equals(Color.white)) {
							buttons[i][j].setBackground(compare);
						}

					}
				}
			}
			// check other player can move
			if (player == 1) {
				compare = Color.red;
			}
			if (player == 2) {
				compare = Color.blue;
			}
			int whiteSquare = 0;
			boolean hasMove = false;
			for (int i = 0; i < WIDTH; i++) {
				for (int j = 0; j < LENGTH; j++) {
					if (buttons[i][j].getBackground().equals(Color.white))
						whiteSquare++;
					if (checkSquare(compare, i, j) && buttons[i][j].getBackground().equals(Color.white)) {
						hasMove = true;
					}
				}
			}

			if (whiteSquare == 0 || !hasMove)
				checkWin(player);
			if (player == 1)
				player = 2;
			else if (player == 2)
				player = 1;
		}

	}

	public boolean checkSquare(Color c, int x, int y) {
		for (int i = -2; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {
				if (Math.abs(i) != Math.abs(j) && i != 0 && j != 0) {
					if (x + i < WIDTH && y + j < LENGTH && x + i >= 0 && y + j >= 0) {
						if (buttons[x + i][y + j].getBackground().equals(c)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 32) {
			if (player == 1)
				player = 2;
			else if (player == 2)
				player = 1;
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}
}