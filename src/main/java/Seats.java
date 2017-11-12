public class Seats {

    private int seatCount = 100;

    public synchronized int getSeatCount() {
        return seatCount;
    }

    public synchronized void setSeatCount(int seatCount) {
        int value = this.seatCount + seatCount;

        if (value <= 100 && value >= 0) {
            this.seatCount += seatCount;
        } else {
            this.seatCount = 100;
        }
    }
}
