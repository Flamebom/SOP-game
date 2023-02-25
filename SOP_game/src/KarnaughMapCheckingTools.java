import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;

public class KarnaughMapCheckingTools {
	static ArrayList<String> allvariables;
	static HashSet<String> allzerocombo;

	static int isCyclic(int variables, int dontcares) {
		int variablearray[] = InstancedQuantumMap.variablesplit(variables);
		// variable strings
		String[] vararray = InstancedQuantumMap.variables(variablearray, variables);
		// Gray code strings
		String[] graycodex = Helpers.graycodegenerator(variablearray[0]);
		String[] graycodey = Helpers.graycodegenerator(variablearray[1]);
		// map creation
		String[][][] kmapkey = InstancedQuantumMap.createtheoryitcalmap(graycodex, graycodey);
		int cellsleft = (int) (Math.pow(2, variables) - dontcares);
		int zeroes = ThreadLocalRandom.current().nextInt(0, cellsleft - 1);
		int ones = cellsleft - zeroes;
		if (ones <= 2) {
			return 0;
		}
		int map[][] = InstancedQuantumMap.createmap(zeroes, ones, variablearray);
		//int map[][] = InstancedQuantumMap.constantmap();
		ArrayList<String> allone = new ArrayList<>();
		ArrayList<String> allzero = new ArrayList<>();
		for (int i = 0; i < map.length; i++) {	
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 1) {
					allone.add(kmapkey[0][i][j]);
				}
				if (map[i][j] == 0) {
					allzero.add(kmapkey[0][i][j]);
				}
			}
		}
		allzerocombo = new HashSet<>();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < allzero.size(); i++) {
			findallcombonationszero(allzero.get(i), buf, 0);
		}
		// 1 = numbers
		// 0 = variable names
		HashSet<Prime> AllBestPrimes = new HashSet<Prime>();
		for (String s : allone) {
			allvariables = new ArrayList<>();
			buf = new StringBuffer();
			findallcombonations(s, buf, 0);
			allvariables.sort(Comparator.comparingInt(String::length));
			ArrayList<Prime> primes = new ArrayList<>();
			loop: for (int i = 0; i < allvariables.size(); i++) {
				if (allzerocombo.contains(allvariables.get(i))) {
					continue loop;
				}
				int numberofones = 0;
				for (String one : allone) {
					boolean match = true;
					for (int j = 0; j < allvariables.get(i).length(); j++) {
						char c = allvariables.get(i).charAt(j);
						if (one.indexOf(c) == -1) {
							match = false;
						}
					}
					if (match) {
						numberofones++;
					}
				}
				primes.add(new Prime(allvariables.get(i), numberofones));

			}

			Collections.sort(primes, new PrimeComparator());
			Prime bestprime = primes.get(0);
			if (primes.size() == 1) {
				return 0;
			}
			if (!(bestprime.ones == primes.get(1).ones
					&& bestprime.variables.length() == primes.get(1).variables.length() && bestprime.ones > 1)) {
				return 0;
			}
			for (int i = 0; i < primes.size(); i++) {
				if (!(bestprime.ones == primes.get(i).ones
						&& bestprime.variables.length() == primes.get(i).variables.length())) {
					break;
				}
				AllBestPrimes.add(primes.get(i));
			}

		}
		if (AllBestPrimes.size() <= 2) {
			return 0;
		}
	    Iterator<Prime> it = AllBestPrimes.iterator();
	    while (it.hasNext()) {
            System.out.println(it.next().variables + " ");
        }
		try {
			MainGame.intializeconstantandthink(variables, map);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;

	}

	private static void findallcombonations(String instr, StringBuffer outstr, int index) {
		for (int i = index; i < instr.length(); i++) {
			outstr.append(instr.charAt(i));
			allvariables.add(outstr.toString());
			findallcombonations(instr, outstr, i + 1);
			outstr.deleteCharAt(outstr.length() - 1);
		}
	}

	private static void findallcombonationszero(String instr, StringBuffer outstr, int index) {
		for (int i = index; i < instr.length(); i++) {
			outstr.append(instr.charAt(i));
			allzerocombo.add(outstr.toString());
			findallcombonationszero(instr, outstr, i + 1);
			outstr.deleteCharAt(outstr.length() - 1);
		}
	}

	static class Prime {
		String variables;
		int ones;

		Prime(String variables, int ones) {
			this.variables = variables;
			this.ones = ones;
		}
@Override
public int hashCode() {
	return variables.length()*31;
}
		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof Prime)) {
				return false;
			}
			Prime p = (Prime) o;
			if (p.variables.equals(variables)) {
				return true;
			}
			return false;
		}

	}

	static class PrimeComparator implements Comparator<Prime> {
		@Override
		public int compare(Prime f1, Prime f2) {
			int result = f1.variables.length() - f2.variables.length();
			if (result == 0) {
				result = f2.ones - f1.ones;
			}
			return result;
		}
	}
}
