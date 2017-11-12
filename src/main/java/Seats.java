public class Seats {

    private int seatCount = 100;

    public synchronized  int getSeatCount() {
        return seatCount;
    }

    public synchronized void setSeatCount(int seatCount) {

        this.seatCount += seatCount;
    }
}
