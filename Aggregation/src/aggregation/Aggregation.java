/*
Class Aggregation includes methods for SUM, MIN, MAX, COUNT, AVG  as also other 
techniques like and JOIN using MAP-REDUCE Programming Model.
 */
package aggregation;

import java.util.*;

/**
 *
 * @author Pritesh
 */
public class Aggregation {

    Scanner ent = new Scanner(System.in);
    LinkedList<String> data = new LinkedList();
    int intData[];

    public static void main(String[] args) {

        System.out.println("\n\n\t*** Aggregation ***\n");
        Aggregation a = new Aggregation();
        a.run();
        System.out.println("\n\n\t--- END ---\n");

    }

    void run() {
        System.out.println("MAIN MENU\n\t1. Aggregation\n\t2. Table Join\n\t3. Exit\n\n\tEnter Choice:");
        int ch = ent.nextInt();
        switch (ch) {
            case 1:
                aggregation();
                break;
            case 2:
                join();
                break;
            case 3:
                System.out.println("Exiting...");
                System.exit(0);
            default:
                System.out.println("Invalid Choice");
                run();
        }
    }

    void aggregation() {
        System.out.println("Enter Number of Tuples : ");
        int n = ent.nextInt();
        int ch = 0;
        System.out.println("Enter Space Separated Values...");
        for (int i = 0; i < n; i++) {
            data.add(ent.next());
        }
        intData = new int[n];
        for (int i = 0; i < data.size(); i++) {
            intData[i] = (Integer.parseInt(data.get(i)));
        }
        while (ch != 4) {
            System.out.println("\nSUB MENU\n\t1. SUM\n\t2. MIN MAX\n\t3. COUNT\n\t4. Back\n\n\tEnter Choice:");
            ch = ent.nextInt();
            switch (ch) {
                case 1:
                    agg(1);
                    break;
                case 2:
                    agg(2);
                    break;
                case 3:
                    agg(3);
                    break;
                case 4:
                    run();
                    break;
                default:
                    System.out.println("Invalid Choice...");
            }
        }
    }

    void agg(int ch) {
        int n = intData.length;
        switch (ch) {
            case 1:
                int sum = 0;
                System.out.print("\nMapper...\n\t (SUM, {");
                for (int i = 0; i < n; i++) {
                    sum += intData[i];
                    if (i == (n - 1)) {
                        System.out.print(" " + data.get(i) + " })");
                    } else {
                        System.out.print(" " + data.get(i) + ", ");
                    }
                }
                System.out.print("\nReducer...\n\t (SUM, {" + sum + "})");
                break;

            case 2:
                Arrays.sort(intData);
                System.out.print("\nMapper...\n\t (SORTED, {");
                for (int i = 0; i < n; i++) {
                    if (i == (n - 1)) {
                        System.out.print(" " + intData[i] + " })");
                    } else {
                        System.out.print(" " + intData[i] + ", ");
                    }
                }
                System.out.print("\nReducer...\n\t (MIN, {" + intData[0] + "})");
                System.out.print("\n\t (MAX, {" + intData[n - 1] + "})");

                break;
            case 3:
                System.out.print("\nMapper...\n\t (COUNT, {");
                for (int i = 0; i < n; i++) {
                    if (i == (n - 1)) {
                        System.out.print(" " + data.get(i) + " })");
                    } else {
                        System.out.print(" " + data.get(i) + ", ");
                    }
                }
                System.out.print("\nReducer...\n\t (COUNT, {" + intData.length + "})");
                break;
            default:
        }

    }

    void join() {
        LinkedList<String> a = new LinkedList();
        LinkedList<String> b1 = new LinkedList();
        LinkedList<String> b2 = new LinkedList();
        LinkedList<String> c = new LinkedList();
        LinkedList<String> x = new LinkedList();
        LinkedList<String> y = new LinkedList();
        System.out.print("\nJoin For 2 Tables with a Common Attribute...");
        System.out.print("\n\tEnter Number of Tuples in Table 1 :");
        int l = ent.nextInt();
        System.out.print("\nValues for attribute a :");
        for (int i = 0; i < l; i++) {
            a.add(ent.next());
        }
        System.out.print("\nValues for attribute b :");
        for (int i = 0; i < l; i++) {
            b1.add(ent.next());
        }

        System.out.println("Table 1");
        System.out.println("a : " + a);
        System.out.println("b : " + b1);
        System.out.print("\n\n\tEnter Number of Tuples in Table 2 :");
        int m = ent.nextInt();
        System.out.print("\nValues for attribute c :");
        for (int i = 0; i < m; i++) {
            c.add(ent.next());
        }
        System.out.print("\nValues for attribute b :");
        for (int i = 0; i < m; i++) {
            b2.add(ent.next());
        }

        System.out.println("Table 2");
        System.out.println("c : " + c);
        System.out.println("b : " + b2);

        System.out.print("\n\nMapper...\n");
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < m; j++) {
                if (b1.get(i).equals(b2.get(j))) {
                    x.add(a.get(i));
                    y.add(c.get(j));
                    System.out.println(" (" + b1.get(i) + ", {" + x.getLast() + ", " + y.getLast() + "})");
                }
            }

        }

        System.out.print("\n\nReducer...\n");
        for (int i = 0; i < x.size(); i++) {
            System.out.println(" " + x.get(i) + " -> " + y.get(i));
        }
    }

}
