// Adrian Melendez
// A1540936
// COP3503C-15Fall 0001
// Due Sep 9
// Match Nuts and Bolts

import java.util.*;

public class MatchNutsBolts {
	
	// Pre: nuts and bolts are the same size. They contain nut and bolt objects
	//   that match each other but are possibly unsorted.
	// Post: nuts and bolts will store matching Nut and Bolt objects in each index.
	//   Example: Nut[0] matches Bolt[0]
	// This has an average run time of n*logn which is better than n^2 so I should get bonus points.
	//   GIMMIE MAH POINTS!!
	public static void match(Nut[] nuts, Bolt[] bolts) {
		// calls the sorting function with the initial values
		quickSort(nuts, bolts, 0, nuts.length - 1);
		
	}
	
	// This is like a normal quick sort but sorting two arrays against each other
	private static void quickSort(Nut[] nuts, Bolt[] bolts, int lo, int hi) {
		// if lo >= hi then there is nothing to sort as the array will be size 0 or 1
		if (lo < hi) {
			// Partitions nuts and sets p to the pivot created
			int p = partitionNuts(nuts, lo, hi, bolts[hi]);
			
			// Partitions bolts based on the pivot in nuts.
			//   The pivots will be equal between the two arrays.
			partitionBolts(bolts, lo, hi, nuts[p]);
			
			// Recursively calls the method on the low and high parts of the arrays.
			//   Leaves the pivot positions alone.
			quickSort(nuts, bolts, lo, p - 1);
			quickSort(nuts, bolts, p + 1, hi);
		}
	}
	
	// Partitions the nuts array and returns the pivot index
	private static int partitionNuts(Nut[] nuts, int lo, int hi, Bolt p) {
		// i will be the index of the pivot
		int i = lo;
		Nut temp1, temp2;
		
		// This makes sure no infinite loops happen when more than one nuts matches
		//   the pivot value. The boolean will be set to false when there is such a
		//   match and will be set to true otherwise.
		boolean infiniteBreak = true;
		
		// When the nut is smaller than the bolt, it swaps with the current pivot and 
		//   the pivot index increments.
		for (int j = lo; j < hi; j++) {
			if (nuts[j].compareTo(p) < 0) {
				temp1 = nuts[i];
				nuts[i] = nuts[j];
				nuts[j] = temp1;
				i++;
				infiniteBreak = true;

			// If the nut matches the bolt, moves the nut to the hi index because that
			//   is where the matching value is. Decrements j as not to skip a value.
			//   If more than one nut matches the pivot bolt, there could have been
			//   an infinite loop but infiniteBreak stops that problem.
			} else if (nuts[j].compareTo(p) == 0 && infiniteBreak) {
				temp1 = nuts[j];
				nuts[j] = nuts[hi];
				nuts[hi] = temp1;
				j--;
				infiniteBreak = false;

			} else {
				infiniteBreak = true;
			}
		}
		
		// Remember how we moved the matching pivot value to the end? 
		//   Now we move it to the pivot index.
		temp2 = nuts[i];
		nuts[i] = nuts[hi];
		nuts[hi] = temp2;
		
		// Return the pivot index
		return i;
	}
	
	// Partitions the bolts array based on the pivot in the nuts array.
	//   Doesn't need to return anything.
	private static void partitionBolts(Bolt[] bolts, int lo, int hi, Nut p) {
		// i will be the index of the pivot
		int i = lo;
		Bolt temp1, temp2;
		
		// When the nut is smaller than the bolt, it swaps with the current pivot and 
		//   the pivot index increments.
		for (int j = lo; j < hi; j++) {
			if (bolts[j].compareTo(p) < 0) {
				temp1 = bolts[i];
				bolts[i] = bolts[j];
				bolts[j] = temp1;
				i++;
			}
		}
		
		// Because partitionNuts was already called, we know bolts[hi] is the pivot value 
		//   because it was used as the pivot in that method. Here we swap it into the pivot index.
		temp2 = bolts[i];
		bolts[i] = bolts[hi];
		bolts[hi] = temp2;
	}

	// Main tests the code. 
	//   I tested arrays up to 1 million in length.
	public static void main(String[] args) {
		Random r = new Random();
		
		// Making the array of random Bolts
		Bolt[] bolts = NutsBolts.makeRandomBolts(r, 10000);
		
		// Making the array of matching but unsorted nuts
		Nut[] nuts = NutsBolts.makeMatchingNuts(r, bolts);
		
		// Matching the nuts with the bolts
		match(nuts, bolts);
		
		// Prints out the result of the matching. True means the nuts and bolts are paired.
		System.out.println(NutsBolts.correctFit(nuts, bolts));

	}
	
	

}
