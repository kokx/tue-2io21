package algorithm;

import java.io.*;
import java.util.*;

import model.*;

public abstract class Algorithm
{

    protected Field field;

    /**
     * Constructor.
     */
    public Algorithm()
    {
    }

    /**
     * Find the parameters
     *
     * @param ci Minimum number of clusters
     * @param cj Maximum number of clusters
     * @param n Number of points
     */
    public abstract void findParameters(int ci, int cj, int n);

    /**
     * Set the field.
     *
     * @param field Field
     */
    public void setField(Field field)
    {
        this.field = field;
    }

    /**
     * Run the algorithm.
     */
    public abstract void run();
}
