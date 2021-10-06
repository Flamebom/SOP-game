
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

//Game related Stuff
public class QuantumMap {
	static String[][] theorymap;
	static int dimx;
	static int dimy;
	static int playmap[][];

	// class of quantum map related stuff
	static int[][] createmap(int variables, int zeroes, int ones, int[] variablesplit) {
		// dimensions are variables to power of 2
		dimy = (int) Math.pow(2, variablesplit[0]);
		dimx = (int) Math.pow(2, variablesplit[1]);

		int[][] map = new int[dimx][dimy];
		// don't cares is the total number of squares minus the number of zeros and ones
		int dontcares = dimx * dimy - ones - zeroes;
		// Array with all 0s,1s and don't cares
		int[] assets = { zeroes, ones, dontcares };

		// randomly plots random numbers until it runs out of numbers
		while (!(assets[0] == 0 && assets[1] == 0 && assets[2] == 0)) {
			int x = ThreadLocalRandom.current().nextInt(0, dimx);
			int y = ThreadLocalRandom.current().nextInt(0, dimy);
			if (map[x][y] == 0) {
				int choice = ThreadLocalRandom.current().nextInt(0, 3);
				if (assets[choice] == 0) {
					choice++;
					choice = capped(choice);
					if (assets[choice] == 0) {
						choice++;
					}
					choice = capped(choice);
				}
				map[x][y] = choice;
				assets[choice]--;

			}

		}

		return map;
	}

	// helper instead of having an if gate
	static int capped(int num) {
		if (num == 3) {
			num = 0;
		}
		return num;
	}

	static void createtheoryitcalmap(String graycodex[], String graycodey[]) {
		theorymap = new String[graycodex.length][graycodey.length];
		for (int i = 0; i < graycodey.length; i++) {
			for (int j = 0; j < graycodex.length; j++) {
				String input = graycodex[j] + graycodey[i];
				theorymap[j][i] = input;
			}
		}
	}

	static int[][] inputtoanswer(String input) {
		ArrayList<String> OutputString = new ArrayList<String>();
		for (int i = 0; i < theorymap[0].length; i++) {
			loop: for (int j = 0; j < theorymap.length; j++) {
				for (int k = 0; k < input.length(); k++) {
					if (input.charAt(k) != '0') {
						char charinput = input.charAt(k);
						if (charinput == '2') {
							charinput = '0';
						}
						if (charinput != theorymap[j][i].charAt(k)) {
							continue loop;
						}
					}
				}
				OutputString.add(j + "," + i);
			}
		}
		int output[][] = new int[OutputString.size()][2];
		for (int i = 0; i < OutputString.size(); i++) {
			String[] element = OutputString.get(i).split(",");
			output[i][0] = Integer.parseInt(element[0]);
			output[i][1]= Integer.parseInt(element[1]);
		
		}
		return output;
	}

	static void makeplaymap() {
		playmap = new int[MainGame.map.length][];
		for (int i = 0; i < MainGame.map.length; i++) {
			playmap[i] = MainGame.map[i].clone();
		}
		for (int i = 0; i < playmap.length; i++) {
			for (int j = 0; j < playmap[i].length; j++) {
				if (playmap[i][j] != 1) {
					playmap[i][j] = 0;
				}
			}
		}

	}

	static void calculatescore(Graphics g) {
		int p1allsquares = 0;
		int p2allsquares = 0;
		for (int i = 0; i < playmap.length; i++) {
			for (int j = 0; j < playmap[i].length; j++) {
				if (playmap[i][j] == 2) {
					p1allsquares++;

				} else if (playmap[i][j] == 3) {
					p2allsquares++;
				}
			}
		}
		g.clearRect(90, 0, 600, 70);
		g.setFont(new Font("TimesRoman", Font.BOLD, 22));
		g.setColor(Color.RED);

		g.drawString(MainGame.player1 + ":" + p1allsquares, 100, 50);
		g.setColor(Color.BLUE);
		g.drawString(MainGame.player2 + ":" + p2allsquares, 100, 69);
	}

}
