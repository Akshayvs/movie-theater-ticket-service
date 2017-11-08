import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;


class TicketServerTest {

    @Test
    @DisplayName("Available seats are initially set to 100")
    void numSeatvailableSetToHundred() {
        TicketServer server = new TicketServer();

        assertThat(server.numSeatvailable()).isEqualTo(100);
    }

    @Test
    @DisplayName("Available seats are decreased")
    void numSeatvailable() {
        TicketServer server = new TicketServer();

        server.findAndHoldSeats(4, "al93@gmail.com");
        assertThat(server.numSeatvailable()).isEqualTo(96);
    }


    // Implement timers
    @Test
    @DisplayName("Available seats are reset back if a booking is not confirmed")
    void numSeatvailableReset() {
        TicketServer server = new TicketServer();

        server.findAndHoldSeats(4, "al93@gmail.com");

        assertThat(server.numSeatvailable()).isEqualTo(100);
    }



    @Test
    void findAndHoldSeats() {

        // TO DO


    }

    @Test
    @DisplayName("Seat should be booked if a valid seatHoldId is passed")
    void reserveSeatsSuccess() {
        TicketServer server = new TicketServer();
        SeatHold bookingDetails = server.findAndHoldSeats(4, "al93@gmail.com");

        assertThat(server.reserveSeats(bookingDetails.getSeatHoldId(), "ak93@gmail.com)"))
                .isEqualTo("Seats are Booked");
    }

    @Test
    @DisplayName("Seat should not be booked if an invalid valid seatHoldId is passed")
    void reserveSeatsFailure() {
        TicketServer server = new TicketServer();

        assertThat(server.reserveSeats(1234, "ak93@gmail.com)"))
                .isEqualTo("Invalid Id, your reservation hold might have expired");
    }

}