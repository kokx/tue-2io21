package algorithm;

import java.io.*;
import java.util.*;

import model.*;

public abstract class Algorithm
{

    protected Field field;

    /**
     * Constructor.
     *
     * @param field
     */
    public Algorithm(Field field)
    {
        this.field = field;
    }

    /**
     * Run the algorithm.
     */
    public abstract void run();
}
