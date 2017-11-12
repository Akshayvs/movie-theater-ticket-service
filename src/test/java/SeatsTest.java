import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class SeatsTest {
    @Test
    @DisplayName("it should initialize availableSeats to 100")
    void getSeatCount() {
        Seats instance = new Seats();

        int seatCount = instance.getSeatCount();
        assertThat(seatCount).isEqualTo(100);
    }

    @Test
    @DisplayName("it should decrement the number of seats by the parameter passed to setSeatCount")
    void setSeatCountDecrement() {
        Seats instance = new Seats();
        instance.setSeatCount(-100);

        int seatCount = instance.getSeatCount();

        assertThat(seatCount).isEqualTo(0);
    }

    @Test
    @DisplayName("it should increment the number of seats by the parameter passed to setSeatCount")
    void setSeatCountIncrement() {
        Seats instance = new Seats();
        instance.setSeatCount(-40);
        instance.setSeatCount(+10);

        int seatCount = instance.getSeatCount();

        assertThat(seatCount).isEqualTo(70);
    }

    @Test
    @DisplayName("it should reset seatCount to 100 if seatCount is incremented beyond 100")
    void setSeatCountMax() {
        Seats instance = new Seats();
        instance.setSeatCount(+50);

        int seatCount = instance.getSeatCount();

        assertThat(seatCount).isEqualTo(100);
    }

    @Test
    @DisplayName("it should reset seatCount to 100 if seatCount is decremented below 0")
    void setSeatCountMin() {
        Seats instance = new Seats();
        instance.setSeatCount(-101);
        int seatCount = instance.getSeatCount();
        assertThat(seatCount).isEqualTo(100);
    }
}