/**
 * Created by asonawane on 11/4/17.
 */

import java.util.Arrays;
import java.util.Scanner;

public class App {
    private static TicketService server;

    public static void main(String args[]) {

        server = new TicketServer();
        printDefaultInstructions();
        navigation();
    }

    protected static void printDefaultInstructions() {
        System.out.println();
        System.out.println(" Please provide a numeric input to perform one of the actions");
        System.out.println(" [ 1 ] for remaining seats");
        System.out.println(" [ 2 ] for reserve seats");
        System.out.println(" [ 3 ] for book seats");
        System.out.println(" To Exit, press any other key");
    }

    protected static void navigation() {
        Scanner scr = new Scanner(System.in);

        try {
            while (true) {
                String value = scr.nextLine();
                if (value.equals("1")) {
                    //REMAINING SEATS
                    System.out.println("Reamining Seats  : " + server.totalAvailableSeats());

                } else if (value.equals("2")) {
                    //HOLD SEATS
                    int requestedSeats;
                    int seatCount;
                    String clientEmail;


                    System.out.println(" Hold Seats : You can reserve upto " + server.totalAvailableSeats() + " seats");
                    System.out.println(" How many seats ? ");
                    requestedSeats = scr.nextInt();

                    System.out.println(" What is your email ? ");
                    clientEmail = scr.next();
                    // todo : regex check for email address validation and error handling.

                    SeatHold reservation = server.findAndHoldSeats(requestedSeats, clientEmail);
                    seatCount = reservation.getNumberOfSeats();

                    // A negative seatCount signifies the actual remainig seat count

                    if (seatCount > 0) {
                        System.out.println("Following " + seatCount + "seats have been reserved : ");

                        int[][] seats = reservation.getSeatsOnHold();

                        for (int[] seat : seats) {
                            System.out.print(Arrays.toString(seat));
                            System.out.print("  ");
                        }

                        System.out.println();
                        System.out.println("Your Reservation ID  : " + reservation.getSeatHoldId());
                        scr.nextLine();
                    } else {
                        System.out.println(" We cannot complete your request. We can provide only " + seatCount * -1 + " seats");
                        scr.nextLine();
                    }
                } else if (value.equals("3")) {
                    // RESERVE
                    int seatHoldId;
                    String email;

                    System.out.println(" Reserve Seats : ");
                    System.out.println("What is your seat hold id ???");
                    seatHoldId = scr.nextInt();
                    System.out.println(" What is your email ? ");
                    email = scr.next();
                    // todo : logic to confirm of the email and the seatHold id match
                    // todo : if match -> proceed; else -> error handling


                    String response = server.reserveSeats(seatHoldId, email);
                    System.out.println("Response : " + response);

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
                            printDefaultInstructions();
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Invalid Input Detected  - >  " + e);
            System.out.println();
            printDefaultInstructions();
            navigation();
        }
    }
}
