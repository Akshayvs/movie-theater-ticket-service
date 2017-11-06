/**
 * Created by asonawane on 11/4/17.
 */

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TicketServer implements TicketService {
    private int MAX_CAPACITY = 100;
    private int TEMP_COUNT = MAX_CAPACITY;


    private static int[][] CHART = new int[10][10];
    Map seatHoldIdIndex = Collections.synchronizedMap(new HashMap<Integer, SeatHold>());


    private static synchronized int[][] seatChartMutator(String command, int seatCount, int[][] seats) {

        // ADD LOGIC
        if (command.equals("add")) {
            int[][] bookedSeats = new int[seatCount][2];

            int index= 0;




            for (int row = 0; row < 10; row++) {
                int[] seatingRow = CHART[row];

                for (int column = 0; column < 10; column++) {
                    if (seatingRow[column] == 0) {

                        seatingRow[column] = 1;
                        int [] resArr = {row, column};

                        bookedSeats[index] = resArr;
                        index++;
                        System.out.println("incr = " +index);
                        if (index == seatCount-1){
                            return bookedSeats;
                        }
                    }
                }
            }

            System.out.println("returning reserved seats array");


        } else if (command.equals("remove")) {

            System.out.println("Remove Detected ");
            for (int[] seat : seats) {
                int row = seat[0];
                int column = seat[1];

                CHART[row][column] = 0;

            }
            return null;
        }
        return null;
    }


    public int numSeatvailable() {
        return TEMP_COUNT;
    }

    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        if (TEMP_COUNT - numSeats >= 0) {
            TEMP_COUNT -= numSeats;

            int seatsOnHold[][] = seatChartMutator("add", numSeats, null);



            final SeatHold booking = new SeatHold(numSeats, customerEmail, seatsOnHold);
            seatHoldIdIndex.put(booking.getSeatHoldId(), booking);

            // TIMEOUT !!
            ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
            exec.schedule(new Runnable() {

                public void run() {
                    /*
                    Delete entry from hashtable
                    Update the count,
                    update the 2d array
                    */
                    System.out.println(" Checking if reservation is confirmed for : " + booking.getSeatHoldId());
                    if (seatHoldIdIndex.containsKey(booking.getSeatHoldId())) {
                        TEMP_COUNT += booking.getNumberOfSeats();
                        seatHoldIdIndex.remove(booking.getSeatHoldId());


                        seatChartMutator("remove", 0, booking.getSeatsOnHold());

                        System.out.println("Booking not confirmed, deleting entry");

                    } else {
                        System.out.println(" Booking was confirmed for : " + booking.getSeatHoldId());
                    }
                }
            }, 15, TimeUnit.SECONDS);
            return booking;
        } else {
            return new SeatHold(-TEMP_COUNT, customerEmail, null);
        }
    }

    public String reserveSeats(int seatHoldId, String customerEmail) {

        if (seatHoldIdIndex.containsKey(seatHoldId)) {

            seatHoldIdIndex.remove(seatHoldId);

            return "Seats are Booked";

        } else {
            return "Invalid Id, your reservation hold might have expired";
        }
    }
}
