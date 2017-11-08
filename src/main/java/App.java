/**
 * Created by asonawane on 11/4/17.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class App {

    public static void main(String args[]) {
        displayInstructions();
        navigation();
    }

    protected static void displayInstructions() {
        System.out.println();
        System.out.println(" Please provide a numeric input to perform one of the actions");
        System.out.println(" [ 1 ] for remaining seats");
        System.out.println(" [ 2 ] for reserve seats");
        System.out.println(" [ 3 ] for book seats");
        System.out.println(" To Exit, press any other key");
    }

    protected static void navigation() {
        TicketService server = new TicketServer();
        Scanner scr = new Scanner(System.in);

        while (true) {
            String value = scr.nextLine();

            if (value.equals("1")) {
                //REMAINING SEATS

                System.out.println("Reamining Seats = " + server.numSeatvailable());

            } else if (value.equals("2")) {
                //HOLD SEATS
                System.out.println(" Hold seats, You can reserve upto " + server.numSeatvailable() + " seats");
                System.out.println(" How many seats ? ");
                int requestedSeats = scr.nextInt();
                System.out.println(" What is your email ? ");
                String clientEmail = scr.next();


                SeatHold reservation = server.findAndHoldSeats(requestedSeats, clientEmail);

                int seatCount = reservation.getNumberOfSeats();
                if (seatCount > 0) {
                    System.out.println(seatCount + " Seats reserved. Your reservation id  is  " + reservation.getSeatHoldId());

                    System.out.println("Following " + seatCount + "seats have been reserved : ");

                    int[][] seats = reservation.getSeatsOnHold();

                    for (int[] seat: seats) {
                        System.out.print(Arrays.toString(seat));
                        System.out.print(" , ");
                    }

                    System.out.println("Your Reservation ID  is  " + reservation.getSeatHoldId());
                    scr.nextLine();
                } else {
                    System.out.println(" We cannot complete your request. We can provide only " + seatCount * -1 + " seats");
                    scr.nextLine();
                }

            } else if (value.equals("3")) {
                // RESERVE

                System.out.println(" Reserve seats");
                System.out.println("What is your seat hold id ???");
                int seatHoldId = scr.nextInt();
                System.out.println(" What is your email ? ");
                String email = scr.next();
                String response = server.reserveSeats(seatHoldId, email);

                System.out.println("response = " + response);

                scr.nextLine();

            } else {
                //Invalid Input Handler
                while (true) {
                    System.out.println(" Are you sure you want to Quit ? Y / N");
                    String answer = scr.nextLine().trim().toLowerCase();
                    if (answer.equals("y") || answer.equals("yes")) {
                        System.out.println(" Goodbye !");
                        scr.close();
                        System.exit(0);

                    } else if (answer.equals("n") || answer.equals("no")) {
                        System.out.println("GREAT, Lets continue !!");
                        displayInstructions();
                        break;
                    }
                }
            }
        }
    }
}
