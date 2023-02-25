import java.util.concurrent.ThreadLocalRandom;

public class InstancedQuantumMap {
	static int[][] createmap(int zeroes, int ones, int[] variablesplit) {
		// dimensions are variables to power of 2
		int dimy = (int) Math.pow(2, variablesplit[1]);
		int dimx = (int) Math.pow(2, variablesplit[0]);

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

	static String[][][] createtheoryitcalmap(String graycodex[], String graycodey[]) {
		String theorymap[][][] = new String[2][graycodex.length][graycodey.length];
		for (int i = 0; i < graycodey.length; i++) {
			for (int j = 0; j < graycodex.length; j++) {
				String input = graycodex[j] + graycodey[i];
				String variablename = "";
				for (int k = 0; k < input.length(); k++) {
					if (input.charAt(k) == '1') {
						variablename += (char) ('A' + k);
					} else {
						variablename += (char) ('a' + k);
					}
				}
				theorymap[0][j][i] = variablename;
				theorymap[1][j][i] = input;
			}
		}
		return theorymap;
	}

	static int[][] constantmap() {
		int map[][] = {{1,0,0,1},{1,1,0,0},{0,1,1,0},{0,0,1,1}};
		//int map[][] = {{1,1,0,0},{0,1,1,0},{0,0,1,1},{1,0,0,1}};
		return map;
	}

	static String[] variables(int[] dimensions, int variables) {
		// makes an array for length vars starting from the beginning of the alphabet
		StringBuilder stringbuild = new StringBuilder();
		for (int i = 0; i < variables; i++) {
			stringbuild.append(Character.toString((char) (i + 97)));
		}
		// turn that string builder into a string array split into 2
		char[] left = new char[dimensions[0]];
		char[] right = new char[dimensions[1]];
		stringbuild.getChars(0, dimensions[0], left, 0);
		stringbuild.getChars(stringbuild.length() - dimensions[1], stringbuild.length(), right, 0);

		String[] vararray = { Helpers.chartostring(left), Helpers.chartostring(right) };
		return vararray;
	}

	static int[] variablesplit(int variables) {
		// left side and top side of map
		int x = variables / 2;
		int y = variables / 2;
		// odd case
		if (variables % 2 != 0) {
			y= variables / 2 + 1;
		}
		int[] dimensions = { x, y };
		return dimensions;
	}
}
