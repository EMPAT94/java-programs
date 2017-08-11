/*
Class Rickart Agrawala implements the algorithm for mutual 
exclusion on a distributed system.

Assuming 
1. Messages are sent instantaneously
2. Processes start requesting CS from time unit 1
3. Processes enter and exit CS instantaneously

Algorithm : 

1. Start
2. Enter n Processes
    a. Enter a process A already in CS (if required)
3. Enter Xi processes requesting CS corresponding to time Ti (0 < i < n)
4. For each prcoess Xi at time Ti
    a. Send a "REQ" message to all Processes
    b. Process Y replys "OK" to each "REQ" if and only if
        i. It is not in CS
        ii. It has lower priority than Process Xi
5. If Xi acquires "OK" from all n - 1 Processes, Xi enters CS
6. If Xi+1 exists, go to 4, else go to 7
7. END
 */
package rickart.agrawala;

import java.util.*;

/**
 *
 * @author Pritesh
 */
public class RickartAgrawala {

    Scanner ent;

    private final ArrayList<String> process;
    private int[] timestamp;
    private int[] timeinCS;
    private boolean inCS;
    private int size, index;
    private String inCSProcess;

    RickartAgrawala() {
        ent = new Scanner(System.in);
        process = new ArrayList();
    }

    public static void main(String[] args) {
        System.out.println("\n\t\t\t*** Rickart-Agrwala Algoritm ***");
        RickartAgrawala RA = new RickartAgrawala();
        RA.run();
        System.out.println("\n\t\t\t--- Algoritm End  ---");
        System.exit(0);
    }

    void run() {
        getInput();
        execute();
    }

    void getInput() {

        //Input n Processes and initialize corresponding arrays
        String temp;
        System.out.println("Enter Processes (as A x P1 or alpha; -1 to end) : ");
        while (true) {
            temp = ent.next();
            if (temp.equals("-1")) {
                break;
            }
            process.add(temp);
        }
        size = process.size();
        timestamp = new int[size];
        timeinCS = new int[size];
        System.out.println(process.size() + " processes Added");

        //Enter Input details of Process already in CS
        System.out.println("Enter process already in Critical Section (-1 in None) : ");
        temp = ent.next();
        inCSProcess = temp;
        if (!temp.equals("-1")) {
            index = process.indexOf(temp);
            if (index == -1) {
                System.out.println("\nProcess Doesnt Exist, Exiting (No exception handling yet)");
                System.exit(1);
            }
            System.out.println("How much time does process " + temp + " still requires to exit CS : ");
            timestamp[index] = 0;
            timeinCS[index] = ent.nextInt();
            inCS = true;
        }

        //Input timestamp and period of each process requesting CS
        System.out.println("Enter a process requiring Critical Section : ");
        while (true) {
            temp = ent.next();
            if (!temp.equals("-1")) {
                index = process.indexOf(temp);
                if (index == -1) {
                    System.out.println("\nProcess Doesnt Exist, Exiting (No exception handling yet)");
                    System.exit(1);
                }
                System.out.println("At what time will " + temp + " require CS : ");
                timestamp[index] = ent.nextInt();
                System.out.println("How much time will " + temp + " require CS : ");
                timeinCS[index] = ent.nextInt();
                System.out.println("Enter next process requiring Critical Section (-1 to End) : ");

            } else {
                break;
            }
        }

        //Display all Input
        System.out.println("\n\n###INPUT###\nProcess Name\tAt Time CS Required \tPeriod of CS ");
        for (int i = 0; i < size; i++) {
            System.out.println(process.get(i) + "\t\t\t" + timestamp[i] + "\t\t\t" + timeinCS[i]);
        }

        System.out.println("If Input is Proper, Enter c to see the System messages during Execution...");
        ent.next();

    }

    void execute() {

        //Create Copies of Data
        LinkedList<String> tProcess = new LinkedList();
        int[] tTimeinCS = new int[size];
        int[] tTimestamp = new int[size];
        for (int i = 0; i < size; i++) {
            tTimeinCS[i] = timeinCS[i];
            tTimestamp[i] = timestamp[i];
        }

        //Create a temp list to hold sorted valid timestamps
        LinkedList<Integer> temp = new LinkedList();
        for (int i = 0; i < size; i++) {
            int t = tTimeinCS[i];
            if (t != 0) {
                temp.add(tTimestamp[i]);
            }
        }
        Collections.sort(temp);

        //Create processes list in order of descending priority
        while (temp.size() > 0) {
            int t = temp.pop();
            if (t == 0) {
                tProcess.add(inCSProcess);
            } else {
                index = indexOf(t, tTimestamp);
                tProcess.add(process.get(index));
                tTimestamp[index] = 0;
            }
        }

        //Create Actual Start and End times' Lists
        int start, per, end = 0;
        LinkedList<Integer> csStart = new LinkedList();
        LinkedList<Integer> csEnd = new LinkedList();

        for (String t : tProcess) {
            index = process.indexOf(t);
            start = timestamp[index];
            per = timeinCS[index];

            if (start < end) {
                start = end;
            }
            end = start + per;
            csStart.add(start);
            csEnd.add(end);
        }

        //Compare timestamp, actual start & end times and arrange processes
        String t = "";
        int a = 0, b = 0, c = 0;
        LinkedList<String> prior = new LinkedList();
        while (!csEnd.isEmpty()) {
            //While End time list is not empty

            c = csEnd.peek();

            if (!csStart.isEmpty()) {
                //If Start time list is not empty
                b = csStart.peek();

                if (!tProcess.isEmpty()) {
                    //If Process list is not empty
                    t = tProcess.peek();
                    index = process.indexOf(t);
                    a = timestamp[index];

                    if (inCS) {
                        inCS = false;
                        prior.add(tProcess.remove());
                        csStart.remove();
                        continue;
                    }

                    if (a <= b && a <= c && a != 0) {
                        System.out.print("\n\tAt Time : " + a);
                        prior.add(tProcess.remove());
                        sendREQ(t);
                        timestamp[index] = 0;
                        sendOK(t, prior);
                    }
                } else {
                    a = 0;
                }
                if (b <= a && b < c) {
                    System.out.print("\n\tAt Time : " + b);
                    System.out.print("\nProcess " + prior.peekFirst() + " enters CS");
                    csStart.remove();
                }
            } else {
                b = 0;
            }
            if (c <= a && c <= b) {
                System.out.print("\n\tAt Time : " + c);
                t = prior.remove();
                System.out.print("\nProcess " + t + " exits CS");
                csEnd.remove();
                sendOK(prior, t);
            }

            System.out.print("\n\nEnter c to Contine...");
            ent.next();
            if (csEnd.size() == 1) {
                System.out.print("\nFinally At Time : " + csEnd.remove());
                System.out.print("\nProcess " + t + " exits CS.\nThus, all processes have acquired CS mutually exclusive of each other!");
            }
        }
    }

    void sendREQ(String x) {
        for (int i = 0; i < size; i++) {
            String a = process.get(i);
            if (x.equals(a)) {
                continue;
            }
            System.out.print("\n  " + x + " ---REQ--> " + a);
        }
    }

    void sendOK(String x, LinkedList<String> p) {
        for (int i = 0; i < size; i++) {
            String a = process.get(i);
            if (x.equals(a) || p.contains(a)) {
                continue;
            }
            System.out.print("\n  " + a + " ---OK--> " + x);
        }
    }

    void sendOK(LinkedList<String> x, String a) {
        for (int i = 0; i < x.size(); i++) {
            if (a.equals(x.get(i))) {
                continue;
            }
            System.out.print("\n  " + a + " ---OK--> " + x.get(i));
        }
    }

    int indexOf(int x, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                return i;
            }
        }
        return -1;
    }
}
