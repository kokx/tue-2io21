import java.io.*;
import java.util.*;

class Main {
    Scanner sc;

    public Main()
    {
        sc = new Scanner(System.in);
    }

    void run()
    {
        // read 'find $C_i$ to $C_j$ clusters'
        // read '$n$ points'
        // read '$x$ $y$' for each point
        sc.next(); // find
        int ci = sc.nextInt();
        int cj = ci;
        if (sc.next().equals("to")) { // otherwise, it is clusters
            cj = sc.nextInt();
            sc.next(); // clusters
        }

        int n = sc.nextInt();
        sc.next(); // points

        int[] points_x = new int[n];
        int[] points_y = new int[n];

        for (int i = 0; i < n; i++) {
            points_x[i] = sc.nextInt();
            points_y[i] = sc.nextInt();
        }

        for (int i = 0; i < n; i++) {
            System.out.println(points_x[i] + " " + points_y[i] + " " + (i % cj));
        }
    }

    public static void main(String args[])
    {
        new Main().run();
    }
}
