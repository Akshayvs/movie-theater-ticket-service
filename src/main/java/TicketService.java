/**
 * Created by asonawane on 11/4/17.
 */
public interface TicketService {
    int numSeatvailable();

    SeatHold findAndHoldSeats( int numSeats, String customerEmail);

    String reserveSeats(int seatHoldId, String customerEmail);
}
