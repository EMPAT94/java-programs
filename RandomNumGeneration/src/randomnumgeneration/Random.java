/*
 To find Random numbers using GCD & LCM Functions of user defined input.
 */
package randomnumgeneration;

import java.util.Scanner;

/**
 *
 * @author Pritesh
 */
public class Random {

    int a, c, n, range1, range2;
    boolean flag = true;

    int random[];

    Scanner ent = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\n\t*** Random Number Generator usign LCG Algorithm ***\n");
        Random l = new Random();
        l.generate_rand();
    }

    void generate_rand() {

        System.out.print("Enter a large range for values (eg : 324 596): ");
        range1 = ent.nextInt();
        range2 = ent.nextInt();
        System.out.print("\nEnter number of random numbers to generate : ");
        n = ent.nextInt();
        random = new int[n];

        System.out.print("\nMore Options? (y/n) : ");
        String ch = ent.next();
        
        a = range1;
        c = (range2 - range1) / 3;
        
        if (ch.equalsIgnoreCase("y")) {
            System.out.print("\nEnter start value (X0) {default is " + a + "} : ");
            a = ent.nextInt();
            System.out.print("\nEnter custom constant value (c) {default is " + c + "} : ");
            c = ent.nextInt();
        }
        System.out.print("\nGenerating Random Numbers...\n\n\t");

        random[0] = a;
        System.out.print("\n " + random[0]);
        int m = range2;
        for (int i = 1; i < n; i++) {

            random[i] = (((random[i - 1] * a) + c) % m);

            if (random[i] < range1) {
                random[i] = (range1 - random[i] + 1) + random[i];
            }

            System.out.print("\n " + random[i]);

            if (random[i] == random[i - 1]) {
                System.out.print("\nNumber loops occur (drawback of this method), please try a different c value in 'More Options'.");
                flag = false;
                break;
            }
        }
        if (flag) {
            System.out.print("\n\nNote : Repetition of values is a trade-off for simplicity of this algorithm. Use with Caution.");
        }
        System.out.println("\n\n\t\t*** END ***");
    }
}
