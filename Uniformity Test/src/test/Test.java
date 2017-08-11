/*

One of the tests for random numbers is the Uniformity test. It included 
Chi-Square and KS Test.


A] Kolmogorov Smirnov {KS} Test Compares the distribution of a set of numbers
generated to a uniform distribution.

Algorithm used for KS Test:

1. Take Inputs R1, R2, R3... RN, and significance value alpha.
2. Sort Inputs in ascending order of Ri
3. Calculate for all i 
    a = i/N
    b = (i-1)/N
    D+ = a - Ri
    D- = Ri - b
4. Compare all D+ and D- to find Maximum D
5. Compare D to D*alpha (D*alpha is from the Distribution table)
6. If D*aplha > D
    then Null Hypothesis is not rejected
   Else 
        Null Hypothesis is rejected


I've used the KS Distribution from http://www.real-statistics.com/wp-content/uploads/2012/12/image3737.png
Check it out in KDistribution.txt

B] The chi-square test (Snedecor and Cochran, 1989) is used to test if a sample 
of data came from a population with a specific distribution.

 
Pseudocode for chi sq test :
 
    For the chi-square goodness-of-fit computation of k data items,

        χ2 = ∑ (Oi−Ei)2/Ei      for i = 1,2 ... k

    where Oi is the observed frequency and Ei is the expected frequency
    for i. The expected frequency is calculated by

        Ei=N*Pi

    where Pi is the Probability of i. I've taken Ei by user input instead of calculating.


Distribution Table : https://www.medcalc.org/manual/chi-square-table.php
Check it out in CDistribution.txt

 */
package test;

import java.util.Scanner;
import java.util.Arrays;
import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author Pritesh
 */
public class Test {

    private final Scanner ent;
    private int n;      //Number of Values
    private float values[];     //All actual R values input by user
    private float evalues[];     //All expected R values input by user
    private float a[];    //All a values [i/N]
    private float b[];    //All b values [(i-1)/N]
    private float D_plus[];     //All D+ values
    private float D_minus[];    //All D- values
    private float alpha;        //Significance value 
    private int alpha_column;     //Significance value column in Distribution matrix 

    private final float Distribution[][];
    private boolean runAgain;

    public Test() {

        /* 
        Distribution here is a generic one that can be reset to capture
        any distribution. It takes input from Distribution.txt that has the following
        format :
        
        n       0.01    0.05    0.10    0.20    
        
        1       0.995   0.975   .       .       
        2       .       .       .       .       
        3       ...
        4       ...
        5       ...
        .
        10      ...
              
        This one defines a Distribution with n = 10 {10 rows} and 4 alpha values
        {columns = 5}
         */
        this.Distribution = new float[10][5];   //Initialize Distribution Matrix
        this.ent = new Scanner(System.in);  //Initialize Scanner for User Input
        this.runAgain = true;
    }

    public static void main(String[] args) {
        Test k = new Test();
        Scanner a = new Scanner(System.in);
        System.out.println("\t*** Uniformity Test ***");
        while (k.runAgain) {
            System.out.print("\nChoose : [1] KS Test [2] Chi Square Test [3] Exit : ");
            int ch = a.nextInt();
            if (ch == 3) {
                System.out.println("\n\t\t*** END ***");
                System.exit(0);
            } else if (ch > 2 || ch < 1) {
                System.out.print("\nInvalid Choice... ");
            } else {
                k.get_distribution(ch);
            }
        }

        System.out.println("\n\t\t*** END ***");
        System.exit(0);
    }

    void get_distribution(int choice) {
        /* This module imports the Distribution table from a file into a matrix inside the program */

        int r = 0, c = 0;   //row, column for adding values to Distribution

        try {
            Scanner tnt;
            if (choice == 1) {
                tnt = new Scanner(new File("src\\test\\KDistribution.txt"));
            } else {
                tnt = new Scanner(new File("src\\test\\CDistribution.txt"));
            }
            while (tnt.hasNextLine()) {
                String line = tnt.nextLine();
                Scanner pnt = new Scanner(line);
                while (pnt.hasNext()) {
                    Distribution[r][c] = pnt.nextFloat();
                    c++;
                }
                c = 0;
                r++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        get_R(choice);
    }

    void get_R(int choice) {
        if (choice == 1) {
            System.out.print("\n1. Kolmogorov - Smirnoff Test");
            System.out.print("\nEnter total number of sample values N : ");
            n = ent.nextInt();
            if (n > 10) {
                System.out.println("\nUnfortunately, system cannot compute values exceeding 10 : ");
                System.out.print("\n\n\tTry Again? (y/n) : ");
                String ch = ent.next();
                if (ch.equals("y") || ch.equals("Y")) {
                    choice = 0;
                    runAgain = true;
                } else {
                    runAgain = false;
                    System.exit(0);
                }
            } else {
                /* Initialize all arrays to input size */
                values = new float[n + 1];
                a = new float[n + 1];
                b = new float[n + 1];
                D_plus = new float[n + 1];
                D_minus = new float[n + 1];

                System.out.println("\nEnter sample values Ri : ");
                values[0] = 0;
                for (int i = 1; i <= n; i++) {
                    values[i] = ent.nextFloat();
                }
            }
        } else if (choice == 2) {
            System.out.print("\n2. Chi Square Test");
            System.out.print("\nEnter total number of sample values N : ");
            n = ent.nextInt();
            if (n > 10) {
                System.out.println("\nUnfortunately, system cannot compute values exceeding 10 : ");

                System.out.print("\n\n\tTry Again? (y/n) : ");
                String ch = ent.next();
                if (ch.equals("y") || ch.equals("Y")) {
                    choice = 0;
                    runAgain = true;
                } else {
                    runAgain = false;
                    System.exit(0);
                }
            } else {
                /* Initialize all arrays to input size */
                values = new float[n + 1];
                evalues = new float[n + 1];
                a = new float[n + 1];
                b = new float[n + 1];
                D_plus = new float[n + 1];
                D_minus = new float[n + 1];

                System.out.println("\nEnter Actual values Oi : ");
                values[0] = 0;
                for (int i = 1; i <= n; i++) {
                    values[i] = ent.nextFloat();
                }
                System.out.println("\nEnter Expected values Ei : ");
                evalues[0] = 0;
                for (int i = 1; i <= n; i++) {
                    evalues[i] = ent.nextFloat();
                }
            }
        } else {
            System.out.print("\n\n\tTry Again? (y/n) : ");
            String ch = ent.next();
            if (ch.equals("y") || ch.equals("Y")) {
                choice = 0;
                runAgain = true;
            } else {
                runAgain = false;
                System.exit(0);
            }
        }
        get_alpha(choice);

    }

    void get_alpha(int choice) {

        if (choice != 0) {
            /* You must make changes in this module corresponding to your Distribution Table */
            System.out.print("\nEnter Significance Level alpha (0.01, 0.05, 0.1, 0.2) : ");

            alpha = ent.nextFloat();

            if (alpha == 0.01f) {
                alpha_column = 0;
            } else if (alpha == 0.05f) {
                alpha_column = 1;
            } else if (alpha == 0.10f) {
                alpha_column = 2;
            } else if (alpha == 0.20f) {
                alpha_column = 3;
            } else {
                System.out.println("\nIncorrent Alpha Value...");
                System.out.print("\n\n\tRun Again? (y/n) : ");
                String ch = ent.next();
                if (ch.equals("y") || ch.equals("Y")) {
                    choice = 0;
                    runAgain = true;
                } else {
                    runAgain = false;
                    System.exit(0);
                }
            }
        }

        calc_dist(choice);
    }

    void calc_dist(int choice) {
        if (choice == 1) {
            /* Sort all R is ascending order */
            Arrays.sort(values);

            /* Calculate a, b, D+, D- in same loop */
            for (int i = 1; i <= n; i++) {

                a[i] = (float) i / (float) n;
                b[i] = (float) (i - 1) / (float) n;

                if (a[i] - values[i] > 0) {
                    D_plus[i] = (a[i] - values[i]);
                } else {
                    D_plus[i] = 0;
                }
                if (values[i] - b[i] > 0) {
                    D_minus[i] = (values[i] - b[i]);
                } else {
                    D_minus[i] = 0;
                }
            }
            DecimalFormat df = new DecimalFormat("#.###");
            //df.setRoundingMode(RoundingMode.CEILING);
            System.out.println("\n\n\t\t##### Table #####");
            System.out.println("\t n \t a \t b \tD_plus\tD_minus");
            for (int i = 1; i <= n; i++) {
                System.out.print("\t" + values[i]);
                System.out.print("\t" + df.format(a[i]));
                System.out.print("\t" + df.format(b[i]));
                System.out.print("\t" + df.format(D_plus[i]));
                System.out.println("\t" + df.format(D_minus[i]));
            }

            float d_plus_max = D_plus[1], d_minus_max = D_minus[1];

            /* Find the maximum D values from D+ and D- arrays */
            for (int i = 2; i <= n; i++) {
                if (D_plus[i] > d_plus_max) {
                    d_plus_max = D_plus[i];
                }
                if (D_minus[i] > d_minus_max) {
                    d_minus_max = D_minus[i];
                }
            }

            float Dmax;       //Maximum D value
            /*Compare maximum of D+ and D- to get Dmax */
            if (d_plus_max > d_minus_max) {
                Dmax = d_plus_max;
            } else {
                Dmax = d_minus_max;
            }

            op(choice, Dmax);

        } else if (choice == 2) {
            float xsq = 0;
            float sum = 0;
            DecimalFormat df = new DecimalFormat("#.###");
            System.out.println("\n\n\t\t##### Table #####");
            System.out.print("\t Actual Values   : ");
            for (int i = 1; i <= n; i++) {
                System.out.print("  " + df.format(values[i]));
            }

            System.out.print("\n\t EXpected Values : ");
            for (int i = 1; i <= n; i++) {
                System.out.print("  " + df.format(evalues[i]));
            }

            System.out.print("\n\t {(O-E)*(O-E)}/E : ");
            for (int i = 1; i <= n; i++) {
                xsq = ((values[i] - evalues[i]) * (values[i] - evalues[i]) / evalues[i]);
                System.out.print("  " + df.format(xsq));
                sum += xsq;

            }
            System.out.print("\n\n\n");
            op(choice, sum);

        } else {
            op(choice, 0);
        }
    }

    void op(int choice, float value) {
        if (choice == 1) {
            float Dmax = value;
            float Dalpha = Distribution[n - 1][alpha_column];
            System.out.println("\nCalculated Dmax Value = " + Dmax);
            System.out.println("\nCritical Value for n " + n + " and  alpha " + alpha + " = " + Dalpha);
            if (Dmax > Dalpha) {
                System.out.println("\n\t\tNull Hypothesis Rejected ");
            } else {
                System.out.println("\n\t\tNull Hypothesis Not Rejected ");
            }
        } else if (choice == 2) {
            float xsq = value;
            float Dalpha = Distribution[n - 2][alpha_column];
            System.out.println("\nCalculated x square Value = " + xsq);
            System.out.println("\nCritical Value for degress of freedom " + (n - 1) + " and  alpha " + alpha + " = " + Dalpha);
            if (xsq > Dalpha) {
                System.out.println("\n\t\tNull Hypothesis Rejected ");
            } else {
                System.out.println("\n\t\tNull Hypothesis Not Rejected ");
            }
        } else if (choice == 0) {
            System.out.println("\n\t\tRestarting...");
        } else {
            System.out.print("\n\n\tRun Again? (y/n) : ");
            String ch = ent.next();
            if (ch.equals("y") || ch.equals("Y")) {
                choice = 0;
                runAgain = true;
            } else {
                runAgain = false;
                System.exit(0);
            }
        }
    }

}
