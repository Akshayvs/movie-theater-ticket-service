/**
 * Created by asonawane on 11/4/17.
 */

import java.util.ArrayList;
import java.util.Random;

public class SeatHold {

    private int seatHoldId;
    private int numberOfSeats;
    private String customerEmail;
    private int[][] reservedSeats;

    public SeatHold(int numberOfSeats, String customerEmail, int[][] seatsOnHold) {
        this.seatHoldId = new Random().nextInt(100);
        this.numberOfSeats = numberOfSeats;
        this.customerEmail = customerEmail;
        this.reservedSeats = seatsOnHold;
    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public int getSeatHoldId() {
        return this.seatHoldId;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    public int[][] getSeatsOnHold() {
        return this.reservedSeats;
    }
}

