import java.util.*;

public class Helpers {
	static String parseInput(String input) {
		int[] output = new int[MainGame.variables];
//checks if it is not or regular statement
		for (int i = 0; i < input.length(); i++) {
			int additive = 1;
			if (input.charAt(i) == '~') {
				additive = 2;
				i++;
			}
			output[input.charAt(i) - 'A'] = additive;
		}
// 1= normal; 2= not 0 = nothing
		StringBuilder stringoutput = new StringBuilder();
		for (int i = 0; i < output.length; i++) {
			stringoutput.append(output[i]);
		}
		return stringoutput.toString();
	}

	static String[] variables(int[] dimensions) {
		// makes an array for length vars starting from the beginning of the alphabet
		StringBuilder stringbuild = new StringBuilder();
		for (int i = 0; i < MainGame.variables; i++) {
			stringbuild.append(Character.toString((char) (i + 97)));
		}
		// turn that string builder into a string array split into 2
		char[] left = new char[dimensions[0]];
		char[] right = new char[dimensions[1]];
		stringbuild.getChars(0, dimensions[0], left, 0);
		stringbuild.getChars(stringbuild.length() - dimensions[1], stringbuild.length(), right, 0);

		String[] vararray = { chartostring(left), chartostring(right) };
		return vararray;
	}

// java default function is pretty bad 
	static String chartostring(char[] array) {
		StringBuilder stringbuild = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			stringbuild.append(array[i]);
		}
		return stringbuild.toString();
	}

//graycode generator using Geeks for Greeks method of copying an array recursively
	static String[] graycodegenerator(int bits) {
		// base array
		String[] graycode = { "0", "1" };
		for (int i = 1; i < bits; i++) {
			String[] graycodereverse = graycode.clone();
			Collections.reverse(Arrays.asList(graycodereverse));
			for (int j = 0; j < graycode.length; j++) {
				graycode[j] = "0" + graycode[j];
			}
			for (int j = 0; j < graycode.length; j++) {
				graycodereverse[j] = "1" + graycodereverse[j];
			}
			// concat array together
			String[] newgraycode = new String[graycode.length * 2];
			System.arraycopy(graycode, 0, newgraycode, 0, graycode.length);
			System.arraycopy(graycodereverse, 0, newgraycode, graycode.length, graycode.length);
			graycode = new String[newgraycode.length];
			graycode = newgraycode.clone();
		}
		return graycode;
	}

	// create dimensions
	static int[] variablesplit() {
		// left side and top side of map
		int x = MainGame.variables / 2;
		int y = MainGame.variables / 2;
		// odd case
		if (MainGame.variables % 2 != 0) {
			y = MainGame.variables / 2 + 1;
		}
		int[] dimensions = { x, y };
		return dimensions;
	}

	static int[][] play(String input, int turn) {
		MainGame.playerinput = "";
		input = input.toUpperCase();
		input = Helpers.parseInput(input);
		int inputarray[][] = QuantumMap.inputtoanswer(input);
		return inputarray;
	}
}
