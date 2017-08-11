/*
The poker test for independence is based on the frequency in which certain digits are repeated in a series of numbers.

For example 0.255, 0.577, 0.331, 0.414, 0.828, 0.909, 0.303, 0.001... In each case, a pair of like digits appears in the number.
In a three digit number, there are only three possibilities.

The individual digits can be all different. Case 1.
The individual digits can all be the same. Case 2.
There can be one pair of like digits. Case 3.

P(case 1) = P(second differ from the first) * P(third differ from the first and second) = 0.9 * 0.8 = 0.72
P(case 2) = P(second the same as the first) * P(third same as the first) = 0.1 * 0.1 = 0.01 P(case 3) = 1 - 0.72 - 0.01 = 0.27
 */
package pokertest;

import java.util.*;

/**
 *
 * @author Pritesh
 */
public class PokerTest {

    Scanner ent = new Scanner(System.in);
    LinkedList<Float> data = new LinkedList();
    int count[] = new int[3];
    int size;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PokerTest p = new PokerTest();
        System.out.println("\n\t\t\t*** POKER TEST ***");
        p.run();
        System.out.println("\n\t\t\t*** TEST END ***");
        System.exit(0);
    }

    void run() {
        int ch = 0;
        System.out.print("\n\n1. Enter Raw Data\n2. Enter Observed Data\nChoice : ");
        ch = ent.nextInt();
        switch (ch) {
            case 1:
                getInput();
                break;
            case 2:
                setInput();
                break;
            default:
        }

    }

    void getInput() {
        System.out.println("Enter space separated float values (exit with -1) : ");
        float temp = 0;
        while (true) {
            temp = ent.nextFloat();
            if (temp == -1) {
                break;
            } else {
                data.add(temp);
            }
        }
        size = data.size();
        for (float value : data) {
            compare(value);
        }
        calc();
    }

    void compare(float a) {
        int B = 0, t1 = 0, t2 = 0, t3 = 0;
        if (Float.compare(a, 1) < 0) {
            float x = a * 1000;
            B = (int) x;
        } else {
            B = (int) a;
        }

        t1 = (int) B / 100;
        B = B % 100;
        t2 = B / 10;
        B = B % 10;
        t3 = B;

        if (t1 == t2 || t1 == t3 || t2 == t3) {

            if (t1 == t2 && t1 == t3) {
                count[0]++;
            } else {
                count[1]++;
            }
        } else {
            count[2]++;
        }
    }

    void setInput() {
        System.out.println("Enter number of values with all same digits : ");
        count[0] = ent.nextInt();
        System.out.println("Enter number of values with 2 same digits : ");
        count[1] = ent.nextInt();
        System.out.println("Enter number of values with no same digits : ");
        count[2] = ent.nextInt();
        size = count[0] + count[1] + count[2];
        calc();
    }

    void calc() {
        float exp[] = new float[3];
        exp[2] = (float) (size * 72) / 100;
        exp[1] = (float) (size * 27) / 100;
        exp[0] = (float) size / 100;
        System.out.print("Enter critical value Xa : ");
        float xa = ent.nextFloat();
        System.out.println("\n\t\t### CALCULATIONS ###");

        System.out.println("                        All Pair None");
        System.out.println(" Observed Frequencies : " + count[0] + ", " + count[1] + ", " + count[2]);
        System.out.println(" Expected Frequencies : " + exp[0] + ", " + exp[1] + ", " + exp[2]);

        float x = 0;
        for (int i = 0; i < 3; i++) {
            x += (float) Math.pow((count[i] - exp[i]), 2) / (exp[i]);
        }
        System.out.println("Calculated Xr sq : " + x);

        System.out.println("Critical Xa sq : " + xa);

        System.out.print(" Thus, Null Hypothesis is ");
        if (x < xa) {
            System.out.println("Accepted! ");
        } else {
            System.out.println("Rejected! ");
        }

    }

}
