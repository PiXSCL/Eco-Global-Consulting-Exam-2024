package ExamNumber1CollatzConjecture;
import java.util.ArrayList;
import java.util.Scanner;

public class ExamNumber1CollatzConjecture {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Create an ArrayList to store the sequence of numbers
        ArrayList<Integer> n = new ArrayList<>();
        
        // Prompt the user to input n
        System.out.print("Input n = ");
        
        // Read the initial value of n and store it in the ArrayList
        n.add(scanner.nextInt());
        
        // Display the initial value of n
        System.out.print(n.get(0));
        
        // Loop until the 3 elements are not the same
        while (!(n.size() > 3 && n.get(n.size() - 1) == n.get(n.size() - 4) 
                 && n.get(n.size() - 2) == n.get(n.size() - 5) 
                 && n.get(n.size() - 3) == n.get(n.size() - 6))) {
            // Generate the next number of the sequence
            int currentNum = n.get(n.size() - 1);
            if (currentNum % 2 == 0) {
                n.add(currentNum / 2); // Update n for even number
            } else {
                n.add(3 * currentNum + 1); // Update n for odd number
            }
            
            // Display the current value of n
            System.out.print(", " + n.get(n.size() - 1));
        }
    }
}
