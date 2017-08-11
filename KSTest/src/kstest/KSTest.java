/*

Kolmogorov Smirnov {KS} Test Compares the distributiion of a set of numbers
generated to a uniform distribution. &#945; = alpha

Algorithm used :

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
Check it out in Distribution.txt


 */
package kstest;

import java.util.Scanner;
import java.util.Arrays;
import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author Pritesh
 */
public class KSTest {

    private final Scanner ent;
    private int n;      //Number of Values
    private float values[];     //All R values input by user
    private float a[];    //All a values [i/N]
    private float b[];    //All b values [(i-1)/N]
    private float D_plus[];     //All D+ values
    private float D_minus[];    //All D- values
    private float alpha;        //Significance value 
    private int alpha_row;     //Significance value column in Distribution matrix 
    private float Dmax;       //Maximum D value

    private final float Distribution[][];
    boolean runAgain = true;

    public static void main(String[] args) {
        KSTest k = new KSTest();
        k.get_distribution();
        System.out.println("\t*** Kolmogorov - Smirnov Single Parameter Test ***");

        while (k.runAgain) {
            k.get_R();      //Re-run the program
        }

        System.out.println("\n\t\t*** END ***");
        System.exit(0);
    }

    public KSTest() {

        /* 
        Distribution here is a generic one that can be reset to capture
        any distribution. It takes input from Distribution.txt that has the following
        format :
        
        n       0.01    0.05    0.10    0.20    ...
        
        1       0.995   0.975   .       .       ...
        2       .       .       .       .       ...
        3       ...
        4       ...
        5       ...
        .
        .
              
        This one defines a Distribution with n = 10 {10 rows} and 5 alpha values
        {columns = 5}
         */
        this.Distribution = new float[10][5];   //Initialize Distribution Matrix
        this.ent = new Scanner(System.in);  //Initialize Scanner for User Input
    }

    void get_distribution() {
        /* This module imports the Distribution table from a file into a matrix inside the program */

        int r = 0, c = 0;   //row, column for adding values to Distribution

        try {
            Scanner tnt = new Scanner(new File("F:\\Programs\\Java\\KSTest\\src\\kstest\\Distribution.txt"));
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
    }

    void get_R() {
        System.out.print("\nEnter total number of sample values N : ");
        n = ent.nextInt();
        if (n > 10) {
            System.out.println("\nUnfortunately, system cannot compute values exceeding 10 : ");

            System.out.print("\n\n\tTry Again? (y/n) : ");
            String ch = ent.next();
            if (ch.equals('y') || ch.equals('Y')) {
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
            get_alpha();
            calc_dist();
        }

    }

    void get_alpha() {

        /* You must make changes in this module corresponding to your Distribution Table */
        System.out.println("\nEnter Significance Level alpha (0.01, 0.05, 0.1, 0.15, 0.2) : ");

        alpha = ent.nextFloat();

        if (alpha == 0.01f) {
            alpha_row = 0;
        } else if (alpha == 0.05f) {
            alpha_row = 1;
        } else if (alpha == 0.10f) {
            alpha_row = 2;
        } else if (alpha == 0.15f) {
            alpha_row = 3;
        } else if (alpha == 0.20f) {
            alpha_row = 4;
        } else {
            System.out.println("\nIncorrent Alpha Value, exiting...");
            System.exit(0);
        }
    }

    void calc_dist() {
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

        /*Compare maximum of D+ and D- to get Dmax */
        if (d_plus_max > d_minus_max) {
            Dmax = d_plus_max;
        } else {
            Dmax = d_minus_max;
        }

        op();
    }

    void op() {
        float Dalpha = Distribution[n - 1][alpha_row];
        System.out.println("\nCalculated Dmax Value = " + Dmax);
        System.out.println("\nCritical Value for n " + n + " and  alpha " + alpha + " = " + Dalpha);
        if (Dmax > Dalpha) {
            System.out.println("\n\t\tNull Hypothesis Rejected ");
        } else {
            System.out.println("\n\t\tNull Hypothesis Not Rejected ");
        }

        System.out.print("\n\n\tRun Again? (y/n) : ");
        String ch = ent.next();
        if (ch.equals('y') || ch.equals('Y')) {
            runAgain = true;
        } else {
            runAgain = false;
        }
    }

}
