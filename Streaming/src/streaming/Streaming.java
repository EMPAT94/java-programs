/*
 Class Streaming implements DGIM Streaming Algorithm that uts incoming ones from
 a binary stream in successively exanding pair of buckets; count is given by sum
 of the sizes of buckets.
 */
package streaming;

import java.util.*;

/**
 *
 * @author Pritesh
 */
public class Streaming {

    LinkedList<Integer> stream = new LinkedList();
    LinkedList<Integer> bucket = new LinkedList();
    Scanner ent = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\n\n\t*** Data Streaming ***");
        Streaming s = new Streaming();

        s.run();
        System.out.println("\n\n\t--- END ---");
    }

    void run() {
        int temp;
        while (true) {
            System.out.println("Start Stream, 2 to get count, -1 to Exit.");

            System.out.println("Enter the Incoming Stream into the array");
            while (ent.hasNext()) {
                temp = ent.nextInt();
                switch (temp) {
                    case 0:
                        stream.add(0);
                        System.out.println("Skip for 0");
                        break;
                    case 1:
                        stream.add(1);
                        System.out.println("Creating New Bucket with Size 1");
                        bucket.add(1);
                        add(1);
                        break;
                    case 2:
                        count(1);
                        break;
                    case -1:
                        count(2);
                        System.exit(0);
                    default:
                        System.out.println("Invalid Input...");

                }

            }
        }
    }

    void add(int a) {
        System.out.println("Checking for Bucket Size " + a + " in " + bucket);
        int counter = 0;
        int temp = a;
        int index1, index2;
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i) == temp) {
                counter++;
            }
        }
        if (counter > 2) {
            System.out.println("More than 2 buckets found with size " + a + ", Merging");
            index1 = bucket.indexOf(a);
            bucket.remove(index1);
            index2 = bucket.indexOf(a);
            bucket.add(index2, 2 * a);
            bucket.remove(bucket.indexOf(a));
            System.out.println("New Buckets : " + bucket);
            add(2 * a);
        } else {
            System.out.println("Buckets Good!");
        }
    }

    void count(int a) {

        int count = 0;
        for (int i = 0; i < bucket.size(); i++) {
            count += bucket.get(i);
        }
        if (a == 1) {
            System.out.println("Current Number of 1s is : " + count);
        } else if (a == 2) {
            System.out.println("For output Stream : " + stream);
            System.out.println("size of the bucket : " + bucket);
            System.out.println("And Number of 1s : " + count);
        }
    }
}
