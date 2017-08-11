/*
The runs test (also called Wald–Wolfowitz test after Abraham Wald and Jacob 
Wolfowitz) is a non-parametric statistical test that checks a randomness hypothesis
for a data sequence. More precisely, it can be used to test the 
hypothesis that the elements of the sequence are mutually independent.

Algorithm :

1. Enter critical value za, number of elements n, and then distinct elements themselves [a,b,c,...,n], set integer runs to 1
2. for each element i in set
        if next element is greater than i, add " + " in run Sequence
        else add " - " in run Sequence
3. for each element i in run Sequence
        if next element in not equal to i   (if i is + and next is - or vice versa)
        then increment runs
4. using formulae
        ur = (2n-1)/3;
        or = sqrt{(16n - 29)/90};
        zr  = (r - ur)/or;
5. compare zr with za and accept/reject hypothesis as defined.
6. end.

 */
package runstest;

import java.util.*;

/**
 *
 * @author Pritesh
 */
public class RunsTest {

    LinkedList<Float> data = new LinkedList();
    LinkedList<String> runSeq = new LinkedList();
    int runs = 1;
    int n;
    float ur, or, za, zr;

    Scanner ent = new Scanner(System.in);

    public static void main(String[] args) {

        RunsTest r = new RunsTest();
        System.out.println("\n\t\t*** RUNS TEST START ***\n");
        r.run();
        System.out.println("\n\n\n\t\t--- RUNS TEST END ---");
        System.exit(0);
    }

    void run() {
        System.out.print("Enter number of elements n : ");
        n = ent.nextInt();

        System.out.print("Enter " + n + " space separated distinct integer elements : ");

        for (int i = 0; i < n; i++) {
            float temp = ent.nextFloat();
            data.add(temp);
        }

        System.out.print("Enter critical value za : ");
        za = ent.nextFloat();

        for (int i = 0; i < n - 1; i++) {
            if (data.get(i + 1) - data.get(i) >= 0) {
                runSeq.add("+ ");
            } else {
                runSeq.add("- ");
            }
        }

        for (int i = 0; i < n - 2; i++) {
            if (!runSeq.get(i).equals(runSeq.get(i + 1))) {
                runs++;
            }
        }

        System.out.print("\n\nFor data elements : " + data + "\nRun Sequence : " + runSeq + "\nThus, runs = " + runs);

        ur = (float) (2 * n - 1) / 3;
        or = (float) Math.sqrt(((16 * n - 29) / 90));
        zr = (float) (runs - ur) / or;

        System.out.println("\n\n\t\t### STATISTICS ###");
        System.out.println("ur = " + ur + "\nor = " + or + "\nThus, zr = " + zr);
        System.out.print("Comparing zr(" + zr + ") with +za(" + za + ") and -za(" + -za + ") tails, we find that ");
        if (zr > za || zr < (-za)) {
            System.out.print("zr is out side the tail area of the graph.\n\n\tHence, Hypothesis Ho (Elements are random ie Mutually Independent) is rejected");
        } else {
            System.out.print("zr is in the tail area of the graph.\n\n\tHence, Hypothesis Ho (Elements are random ir Mutually Independent) is accepted");
        }
    }

}
