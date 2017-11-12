import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class TicketServerTest {


    //TOTAL AVAILABLE SEATS
    @Test
    @DisplayName("Should have 100 available seats")
    void totalAvailableSeatsSetToHundred() {
        TicketServer server = new TicketServer();

        assertThat(server.totalAvailableSeats()).isEqualTo(100);
    }

    // FIND AND HOLD SEATS
    @Test
    @DisplayName("Should decrease the available seat count")
    void findAndHoldSeats() {
        TicketServer server = new TicketServer();

        server.findAndHoldSeats(4, "al93@gmail.com");
        assertThat(server.totalAvailableSeats()).isEqualTo(96);
    }

    @Test
    @DisplayName("Should return a new object if with a negative seat count if  seats requested are more than available seats")
    void findAndHoldSeatsMoreThanHundred() {
        TicketServer server = new TicketServer();

        SeatHold reservation = server.findAndHoldSeats(200, "al93@gmail.com");
        assertThat(reservation.getNumberOfSeats()).isLessThan(0);
    }

    // todo - Testing the child process by using fake clock

    // RESERVE SEATS
    @Test
    @DisplayName("Seat should be booked if a valid seatHoldId is passed")
    void reserveSeatsSuccess() {
        TicketServer server = new TicketServer();
        SeatHold bookingDetails = server.findAndHoldSeats(4, "al93@gmail.com");

        assertThat(server.reserveSeats(bookingDetails.getSeatHoldId(), "ak93@gmail.com)"))
                .isEqualTo("Seats are Booked");
    }

    @Test
    @DisplayName("Seat should not be booked if an invalid seatHoldId is passed")
    void reserveSeatsFailure() {
        TicketServer server = new TicketServer();

        assertThat(server.reserveSeats(1234, "ak93@gmail.com)"))
                .isEqualTo("Invalid Id, your reservation hold might have expired");
    }
}