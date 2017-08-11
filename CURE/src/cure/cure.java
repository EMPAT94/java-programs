/*
CURE employs a hierarchical clustering algorithm that adopts a middle ground 
between the centroid based and all point extremes. In CURE, a constant number c
of well scattered points of a cluster are chosen and they are shrunk towards the
centroid of the cluster by a fraction α. The scattered points after shrinking 
are used as representatives of the cluster. The clusters with the closest pair 
of representatives are the clusters that are merged at each step of CURE's 
hierarchical clustering algorithm. This enables CURE to correctly identify the 
clusters and makes it less sensitive to outliers.

Algorithm (That I've Used) :

1. Start

2. 
    a. Input n as elements in C clusters (initially, #C = n) and set representative
       points as the elements themselves.
    b. Input k as number of clusters

3. For all clusters C
    Find the shortest distance between Representative Points of 2 clusters Ci and Cj 
    
4. Merge Cj with Ci and Remove Cj

5. For all clusters C
    if( C = null) then Remove corresponding Rep Pt List
    else if ( C has just 1 element) then set that element as Rep Pt.
    else 
            a. Find the farthest points in the cluster (min, max)
            b. Reduce their distance from center by 20% {x - (x/5}}
            c. Add these points as Rep Pts.

6. If #C > K, Goto 5
    Else Goto 7.

7. End


For indepth Reading, see : http://www.cs.sfu.ca/CourseCentral/459/han/papers/guha98.pdf

 */
package cure;

import java.util.*;

/**
 *
 * @author Pritesh
 */
public class cure {

    Scanner ent = new Scanner(System.in);
    Linked C[]; //Array of Clusters' Objects
    Linked R[]; //Array of Representative points' Objects
    int k;  //Number of clusters

    public static void main(String[] args) {
        System.out.println("\n\t\t\t*** CURE Clustering Algorithm ***");
        cure c = new cure();
        c.run();
        System.out.println("\n\t\t\t\t-- END ---");
    }

    void run() {
        System.out.println("Enter number of elements : ");
        int n = ent.nextInt();
        C = new Linked[n];
        R = new Linked[n];

        System.out.print("Enter space separated elements : ");
        for (int i = 0; i < n; i++) {
            C[i] = new Linked();
            R[i] = new Linked();
            C[i].cluster.add(ent.nextFloat());
        }

        System.out.print("Enter number of clusters : ");
        k = ent.nextInt();

        calc();
    }

    void calc() {
        int pass = C.length;
        while (pass > k) {
            System.out.println("\n\nOperating on Clusters...");
            showC();
            for (int i = 0; i < C.length; i++) {
                if (C[i] == null) {
                    R[i] = null;
                } else if (C[i].cluster.size() == 1) {
                    R[i].cluster.clear();
                    R[i].cluster.addAll(C[i].cluster);
                } else {
                    R[i].cluster.clear();
                    findR(i);
                }
            }

            System.out.println("\nRepresentative Points are...");
            showR();
            findSD();
            showC();
            pass--;
        }
    }

    void findR(int i) {
        float min, max;
        min = C[i].cluster.getFirst();
        max = C[i].cluster.getFirst();
        for (Float item : C[i].cluster) {
            if (item < min) {
                min = item;
            } else if (item >= max) {
                max = item;
            }
        }
        min = min - (min / 5f);
        max = max - (max / 5f);
        R[i].cluster.add(min);
        R[i].cluster.add(max);
    }

    void findSD() {
        float shortest = Math.abs(R[0].cluster.getFirst() - R[1].cluster.getFirst());
        float temp;
        int clu1 = 0, clu2 = 1;
        for (int i = 0; i < R.length - 1; i++) {
            if (R[i] != null) {
                for (Float item1 : R[i].cluster) {
                    for (int j = i + 1; j < R.length; j++) {
                        if (R[j] != null) {
                            for (Float item2 : R[j].cluster) {
                                temp = Math.abs(item1 - item2);
                                if (temp <= shortest) {
                                    shortest = temp;
                                    clu1 = i;
                                    clu2 = j;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("\nClosest Clusters : " + C[clu1].cluster + " & "
                + C[clu2].cluster + " with distance = " + shortest + " between Representative Points.");

        C[clu1].cluster.addAll(C[clu2].cluster);
        C[clu2] = null;
    }

    void showR() {
        for (Linked Ri : R) {
            if (Ri != null) {
                System.out.print(" " + Ri.cluster);
            }
        }
    }

    void showC() {
        for (Linked Ci : C) {
            if (Ci != null) {
                System.out.print(" " + Ci.cluster);
            }
        }
    }

}

class Linked {

    LinkedList<Float> cluster = new LinkedList();
}
