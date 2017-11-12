/**
 * Created by asonawane on 11/4/17.
 */

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TicketServer implements TicketService {
     /*
     We are using a 2d array to represent the theater seating chart
     which is synchronized using a mutator function
     and
     a synchronized hashMap to store the mapping of seatHoldId to seats
     */

    private Seats seats = new Seats();
    private Map unconfirmedBookings = Collections.synchronizedMap(new HashMap<Integer, SeatHold>());


    private static int[][] CHART = new int[10][10];




    private static synchronized int[][] seatChartMutator(String command, int seatCount, int[][] seats) {

        /* ADD LOGIC
        To achieve a logical trade-of between theater utilization and best seat; the approach used is to iterate from top all the way till the last row
        while allocating the first possible seat to the seat requester. This solves two purposes,
        1. Theater utilization is maximized
        2. Customer satisfaction is increased by providing them with the farthest possible row from the screen.
        */
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


    public int totalAvailableSeats() {
        return seats.getSeatCount();
    }

    public SeatHold findAndHoldSeats(int totalSeatsRequested, String customerEmail) {

        if (seats.getSeatCount() - totalSeatsRequested >= 0) {

            seats.setSeatCount(-totalSeatsRequested);

            int seatsOnHold[][] = seatChartMutator("add", totalSeatsRequested, null);

            final SeatHold booking = new SeatHold(totalSeatsRequested, customerEmail, seatsOnHold);
            unconfirmedBookings.put(booking.getSeatHoldId(), booking);

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

                    if (unconfirmedBookings.containsKey(booking.getSeatHoldId())) {

                        seats.setSeatCount(booking.getNumberOfSeats());
                        unconfirmedBookings.remove(booking.getSeatHoldId());


                        seatChartMutator("remove", 0, booking.getSeatsOnHold());

                        System.out.println("Booking not confirmed, deleting entry");

                    } else {
                        System.out.println(" Booking was confirmed for : " + booking.getSeatHoldId());
                    }
                }
            }, 15, TimeUnit.SECONDS);
            return booking;
        } else {
            // error response passing logic to provide a count on actual remaining seats.

            
            return new SeatHold(-AVAILABLE_SEATS_COUNTER, customerEmail, null);
        }
    }

    public String reserveSeats(int seatHoldId, String customerEmail) {
        if (unconfirmedBookings.containsKey(seatHoldId)) {
            unconfirmedBookings.remove(seatHoldId);
            return "Seats are Booked";
        } else {
            return "Invalid Id, your reservation hold might have expired";
        }
    }
}
