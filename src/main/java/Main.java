/**
 * Created by asonawane on 11/4/17.
 */

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    private int[] values = new int[10];



    // HoldSeats and reserveSeats are both modifying the same object, may be they both can be overriding the same object at the same given time.
    // To solve this problem, we need to have both of them calling a new method that acts as a writer to the values [] .
    // This method should be declared "Synchronized" i guess. Read more in HFJ.

    public void holdSeats() {
        values[0] = 1;
        values[1] = 1;
        values[2] = 1;

        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

        exec.schedule(new Runnable() {
            public void run() {
                System.out.println(" REACHING HERE");
                values[0] = 0;
                values[1] = 0;
                values[2] = 0;

            }
        }, 5, TimeUnit.SECONDS);
    }

}
