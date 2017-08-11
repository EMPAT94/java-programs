/*
 Class Planning is a simulation on Total Order Planning Problem solution
 using backward chaining technique.
 */
package planning;

import java.util.Scanner;

/**
 *
 * @author Pritesh
 */
public class Planning {

    String goal;
    String[] initial_states;
    String[] actions;
    String[] solution;
    OnAction a[];
    int c = 0;
    boolean flag = true;

    public static void main(String[] args) {
        Planning p = new Planning();
        p.run();
        p.find();
        p.show();
        System.out.print("\n\n*** END ***\n");
        System.exit(0);
    }

    void run() {
        Scanner ent = new Scanner(System.in);
        int n;

        System.out.println("*** Planning Problem ***");

        System.out.print("Enter number of Initial States : ");
        n = ent.nextInt();

        initial_states = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.print("  Enter Initial States " + i + " : ");
            initial_states[i] = ent.next();
        }

        System.out.print("\nEnter number of Actions : ");
        n = ent.nextInt();
        a = new OnAction[n];
        actions = new String[n];
        solution = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.print("\n  Enter action " + i + " : ");
            actions[i] = ent.next();
            System.out.println("  For " + actions[i]);
            a[i] = new OnAction();
            a[i].act();
        }

        System.out.print("\nEnter Goal : ");
        goal = ent.next();

    }

    void find() {
        int n = actions.length;
        for (int i = 0; i < n; i++) {
            int m = a[i].effects.length;
            int o = a[i].conditions.length;
            for (int j = 0; j < m; j++) {
                if (goal.equals(a[i].effects[j])) {
                    solution[c] = actions[i];
                    c += 1;
                    for (int k = 0; k < o; k++) {
                        if (!initial(a[i].conditions[k])) {
                            goal = a[i].conditions[k];
                            find();
                        }
                    }
                    break;
                }
            }
        }
    }

    boolean initial(String s) {
        for (int i = 0; i < initial_states.length; i++) {
            if (s.equals(initial_states[i])) {
                return true;
            }
        }
        return false;
    }

    void show() {
        System.out.println("\n\nOutput Sequencce : ");

        for (int i = solution.length - 1; i >= 0; i--) {
            if (solution[i] != null) {
                System.out.println(solution[i]);
            }
        }
    }
}

class OnAction {

    String[] effects;
    String[] conditions;

    void act() {
        Scanner ent = new Scanner(System.in);
        int n;
        System.out.print("  Enter number of Conditions : ");
        n = ent.nextInt();
        conditions = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.print("    Enter Condition " + i + " : ");
            conditions[i] = ent.next();
        }

        System.out.print("  Enter number of Effects : ");
        n = ent.nextInt();
        effects = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.print("    Enter Effect " + i + " : ");
            effects[i] = ent.next();
        }
    }

}
