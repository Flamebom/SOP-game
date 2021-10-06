import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class MainGame extends JFrame {
	JPanel panelinput = new JPanel();
	JLabel labelinput = new JLabel();
	JTextField textinput = new JTextField("", 30);
	static final long serialVersionUID = 42L;
	// variable deceleration
	static String playerinput = "";
	static int variables = 0;
	static int map[][];
	static String vararray[];
	static String graycodex[];	
	static String graycodey[];
	static int[][] allp1input = new int[1000][2];
	static int p1counter = 0;
	static int[][] allp2input = new int[1000][2];
	static int p2counter = 0;
	static String player1;
	static String player2;

//draw the grid
	MainGame() {
		setSize(1000, 1000);
		setTitle("Quantum Map");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		panelinput.add(textinput);
		add(panelinput);
		textinput.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					playerinput = textinput.getText();
					textinput.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
	}

	// User input
	public static void main(String[] args) throws InterruptedException {

		for (int i = 0; i < allp1input.length; i++) {
			Arrays.fill(allp1input[i], -1);
			Arrays.fill(allp2input[i], -2);
		}
		Scanner input = new Scanner(System.in);
		System.out.println("Number of variables:");
		variables = input.nextInt();
		System.out.println("Number of 0s:");
		int zeroes = input.nextInt();
		System.out.println("Number of 1s:");
		int ones = input.nextInt();
		System.out.println("Player 1 Name");
		player1 = input.next();
		System.out.println("Player 2 Name");
		player2 = input.next();
		// dimensions
		int[] variablearray = Helpers.variablesplit();
		// variable strings
		vararray = Helpers.variables(variablearray);
		// Gray code strings
		graycodex = Helpers.graycodegenerator(variablearray[0]);
		graycodey = Helpers.graycodegenerator(variablearray[1]);
		// map creation
		QuantumMap.createtheoryitcalmap(graycodex, graycodey);
		map = QuantumMap.createmap(variables, zeroes, ones, variablearray);
		QuantumMap.makeplaymap();
		MainGame m = new MainGame();
		m.repaint();
		boolean flag = false;
		int turn = 1;

		input.close();
		// P1input/P2input

		while (!flag) {
			Thread.sleep(700);
			if (!playerinput.equals("") && turn == 1) {
				play1(m.getGraphics(), Helpers.play(playerinput, turn), Color.RED);
				turn = 2;
			}
			Thread.sleep(700);
			if (!playerinput.equals("") && turn == 2) {
				play2(m.getGraphics(), Helpers.play(playerinput, turn), Color.BLUE);
				turn = 1;
			}

		}

	}

	// paint the grid
	@Override
	public void paint(Graphics g) {
//draw variables
		g.setFont(new Font("TimesRoman", Font.BOLD, 22));
		g.drawLine(20, 20, 100, 100);
		g.drawString(vararray[0], 20, 100);
		g.drawString(vararray[1], 60, 50);
//draw gray code
		for (int i = 0; i < graycodex.length; i++) {
			g.drawString(graycodex[i], 20, i * 100 + 170);
		}
		for (int i = 0; i < graycodey.length; i++) {
			g.drawString(graycodey[i], i * 100 + 100, 95);
		}
		// draw map
		g.setFont(new Font("TimesRoman", Font.BOLD, 30));
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				g.drawRect(i * 100 + 100, j * 100 + 100, 100, 100);
				String temp;
				if (map[i][j] == 2) {
					temp = "-";
				} else {
					temp = Integer.toString(map[i][j]);
				}
				g.drawString(temp, i * 100 + 140, j * 100 + 170);
			}
		}
	}

	public static void play1(Graphics g, int[][] inputarray, Color color) {
		for (int i = 0; i < inputarray.length; i++) {
			allp1input[i + p1counter][0] = inputarray[i][0];
			allp1input[i + p1counter][1] = inputarray[i][1];
		}
		for (int i = 0; i < inputarray.length; i++) {
			if (QuantumMap.playmap[inputarray[i][1]][inputarray[i][0]] == 1
					|| QuantumMap.playmap[inputarray[i][1]][inputarray[i][0]] == 3) {
				QuantumMap.playmap[inputarray[i][1]][inputarray[i][0]] = 2;
			}
		}
		p1counter += inputarray.length;
		g.setColor(color);
		for (int i = 0; i < allp1input.length - 1; i++) {
			for (int j = 0; j < allp2input.length - 1; j++) {
				if (allp1input[i][0] == allp2input[j][0] && allp1input[i][1] == allp2input[j][1]) {
					allp2input[j][0] = -2;
					allp2input[j][1] = -2;
				}
			}
		}
		for (int i = 0; i < allp1input.length; i++) {
			if (allp1input[i][0] != -1) {
				g.fillRect(allp1input[i][1] * 100 + 101, allp1input[i][0] * 100 + 101, 25, 25);
			} else {
				continue;
			}
		}
		QuantumMap.calculatescore(g);
	}

	public static void play2(Graphics g, int[][] inputarray, Color color) {
		for (int i = 0; i < inputarray.length; i++) {
			allp2input[i + p2counter][0] = inputarray[i][0];
			allp2input[i + p2counter][1] = inputarray[i][1];
		}
		for (int i = 0; i < inputarray.length; i++) {
			if (QuantumMap.playmap[inputarray[i][1]][inputarray[i][0]] == 1
					|| QuantumMap.playmap[inputarray[i][1]][inputarray[i][0]] == 2) {
				QuantumMap.playmap[inputarray[i][1]][inputarray[i][0]] = 3;
			}
		}
		p2counter += inputarray.length;
		g.setColor(color);
		for (int i = 0; i < allp2input.length - 1; i++) {
			for (int j = 0; j < allp1input.length - 1; j++) {
				if (allp2input[i][0] == allp1input[j][0] && allp2input[i][1] == allp1input[j][1]) {
					allp1input[j][0] = -1;
						allp1input[j][1] = -1;
					}
				}
			}
	
			for (int i = 0; i < allp2input.length; i++) {
				if (allp2input[i][0] != -2) {
	
					g.fillRect(allp2input[i][1] * 100 + 101, allp2input[i][0] * 100 + 101, 25, 25);
	
				} else {
				continue;
			}
		}
		QuantumMap.calculatescore(g);
	}

}