

import java.util.Scanner;

/**
 * Drives the simulation; contains the main method
 * to play the simulation out.
 * 
 * @author David J. Barnes and Michael KÃ¶lling
 * @version Java
 */
public class Driver {

	public static void main(String[] args) {
		Simulator mySim = new Simulator();

		char choice;
		Scanner sc = new Scanner(System.in);
		do {
			choice = getOption(sc);
			takeAction(choice, mySim);
		} while (choice != 'X');
	}
	
	/**
	 * Prompts the user with a set of selections to allow
	 * them to choose how they want to interact with the simulation.
	 * 
	 * @param sc Scanner object to accept input.
	 * @return character of selection choice.
	 */
	private static char getOption(Scanner sc) {
		System.out.println("Enter a menu option");
		System.out.println(" R. Reset the simulation");
		System.out.println(" 1. Simulate one step");
		System.out.println(" 2. Simulate two steps");
		System.out.println(" 3. Simulate three steps");
		System.out.println(" 4. Simulate forty steps");
		System.out.println(" 5. Simulate fifty steps");
		System.out.println(" 0. Simulate 100 steps");
		System.out.println(" L. Run long simulation (4000 steps)");
		System.out.println(" X. Exit simulator");
		return sc.next().toUpperCase().charAt(0);
	}

	/**
	 * Based on the user's input, the selection's action
	 * will act and play the simulation.
	 * 
	 * @param choice The choice the user has made to interact with the
	 * 				 simulation.
	 * @param mySim The simulation.
	 */
	private static void takeAction(char choice, Simulator mySim) {
		switch (choice) {
		case ('R'): 
			mySim.reset();
			break;
		case('3'):
			mySim.simulateOneStep();
		case('2'):
			mySim.simulateOneStep();
		case('1'):
			mySim.simulateOneStep();
			break;
		case('5'):
			mySim.simulate(10);
		case('4'):
			mySim.simulate(40);
			break;
		case('0'):
			mySim.simulate(100);
			break;
		case('L'):
			mySim.runLongSimulation();
			break;
		case('X'):
			System.out.println("Goodbye.");
			break;
		}
	}
}
