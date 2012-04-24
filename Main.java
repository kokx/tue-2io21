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
        sc.next(); // to
        int cj = sc.nextInt();
        sc.next(); // clusters

        int n = sc.nextInt();
        sc.next(); // points

        for (int i = 0; i < n; i++) {
            System.out.println(sc.nextInt() + " " + sc.nextInt());
        }
    }

    public static void main(String args[])
    {
        new Main().run();
    }
}
