/**
 * Created by asonawane on 11/4/17.
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TicketServer implements TicketService {
    /*
    We are using a 2d array to represent the theater seating chart
    which is synchronized using a mutator function
    and
    a synchronized hashMap to store the mapping of seatHoldId to seats
    */
    // Syschrnoized Data Stores
    private Seats seats = new Seats();
    private Map unconfirmedBookings = Collections.synchronizedMap(new HashMap<Integer, SeatHold>());
    private static boolean[][] CHART = new boolean[10][10];

    private static synchronized int[][] seatChartMutator(String command, int seatCount, int[][] seats) {

        /* ADD LOGIC
        To achieve a logical trade-of between theater utilization and best seat; the approach used is to iterate from top all the way till the last row
        while allocating the first possible seat to the seat requester. This solves two purposes,
        1. Theater utilization is maximized
        2. Customer satisfaction is increased by providing them with the farthest possible row from the screen.
        */
        if (command.equals("add")) {
            int[][] bookedSeats = new int[seatCount][2];

            int index = 0;
            for (int row = 0; row < 10; row++) {

                for (int column = 0; column < 10; column++) {
                    if (CHART[row][column] == false) {
                        CHART[row][column] = true;

                        int[] seatLocation = {row, column};

                        bookedSeats[index] = seatLocation;
                        index++;
                        if (index == seatCount) {
                            return bookedSeats;
                        }
                    }
                }
            }
        } else if (command.equals("remove")) {
            for (int[] seat : seats) {
                int row = seat[0];
                int column = seat[1];

                CHART[row][column] = false;
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
            // update totalSeats counter, update the seatChart, create a booking Object and map it in the unconfirmedBookings map`

            seats.setSeatCount(-totalSeatsRequested);
            int seatsOnHold[][] = seatChartMutator("add", totalSeatsRequested, null);
            final SeatHold booking = new SeatHold(totalSeatsRequested, customerEmail, seatsOnHold);
            unconfirmedBookings.put(booking.getSeatHoldId(), booking);


            // TIMEOUT !!
            ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
            exec.schedule(new Runnable() {
                public void run() {
                    /*
                    Delete entry from unconfirmedBookings map, Update the count, update the 2d seatChart array
                    */
                    if (unconfirmedBookings.containsKey(booking.getSeatHoldId())) {
                        seats.setSeatCount(booking.getNumberOfSeats());
                        unconfirmedBookings.remove(booking.getSeatHoldId());
                        seatChartMutator("remove", 0, booking.getSeatsOnHold());
                    }
                }
            }, 15, TimeUnit.SECONDS);
            return booking;
        } else {
            // error response passing logic to provide a negated count of the actual remaining seats.
            return new SeatHold(-seats.getSeatCount(), customerEmail, null);
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
