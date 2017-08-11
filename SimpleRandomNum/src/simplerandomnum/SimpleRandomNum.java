package simplerandomnum;

import java.util.*;

/**
 *
 * @author Pritesh
 */
public class SimpleRandomNum {

    public static void main(String[] args) {

        int a, c, m;
        float x0, x;
        int r1, r2;
        int n;
        float ri;
        int rand;

        Scanner ent = new Scanner(System.in);

        System.out.println("\n\n\t\t *** RANDOM NUM GNERATION ***");

        System.out.println("Enter X0 : ");
        x0 = ent.nextFloat();
        System.out.println("Enter a (a > 2) : ");
        a = ent.nextInt();
        c = a - 1;
        if( c%4 == 0) {
            m = c*c;
        } else {
            m = c * 99;
        }
        System.out.println("Enter range r1<space>r2 : ");
        r1 = ent.nextInt();
        r2 = ent.nextInt();
        System.out.println("Enter n : ");
        n = ent.nextInt();

        for (int i = 0; i < n; i++) {
            x = (x0 * a + c) % (float) m;
            x0 = x;
            System.out.println("X" + (i + 1) + " = " + x);
            ri = x / m;
            System.out.println("R" + (i + 1) + " = " + ri);
            rand = (int) (ri * 1000 % r2);
            if (rand < r1) {
                rand += r1;
            }
            System.out.println("Random Number = " + rand);
        }

    }

}
