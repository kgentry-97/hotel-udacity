package com.kgentry;

import com.kgentry.api.HotelResource;
import com.kgentry.model.Customer;
import com.kgentry.model.IRoom;
import com.kgentry.model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Kansas Gentry
 */
public class MainMenu {

    HotelResource hotelResource = HotelResource.getInstance();


    public void printMainMenu() {
        System.out.println("Main Menu");
        System.out.println("=================================");
        System.out.println("1. find and reserve a room");
        System.out.println("2. see my reservation");
        System.out.println("3. create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("=================================");
        System.out.println("Select a number from the menu options");
    }

    public void mainMenu() {

        Scanner scanner = new Scanner(System.in);
        boolean finished = false;
        AdminMenu adminMenu = new AdminMenu();
        int menuOption;

        do {
            printMainMenu();
            try {
                menuOption = Integer.parseInt(scanner.nextLine());
                switch (menuOption) {
                    case 1 -> reserveRoom(scanner);
                    case 2 -> findReservation(scanner);
                    case 3 -> createAccount(scanner);
                    case 4 -> adminMenu.adminMenuSelect();
                    case 5 -> finished = true;
                    default -> System.out.println("enter an invalid number please try again");
                }
            } catch (InputMismatchException ex) {
                System.out.println("invalid input only numbers expected");
                scanner.nextLine();
            }
        } while (!finished);


    }

    public void createAccount(Scanner accountScanner) {
        System.out.println("Enter First Name: ");
        String firstName = accountScanner.nextLine();
        System.out.println("Enter Last name: ");
        String lastname = accountScanner.nextLine();
        System.out.println("Enter Email:");
        String email = accountScanner.nextLine();
        hotelResource.createACustomer(email, firstName, lastname);
    }

    public void createAccount(Scanner accountScanner, String email) {
        System.out.println("could find account please create one");
        System.out.println("Enter First Name: ");
        String firstName = accountScanner.nextLine();
        System.out.println("Enter Last name: ");
        String lastname = accountScanner.nextLine();
        hotelResource.createACustomer(email, firstName, lastname);
    }

    public void findReservation(Scanner findScanner) {
        System.out.println("Enter Email of reservation:");
        String email = findScanner.nextLine();
        Collection<Reservation> customerReservation = hotelResource.getCustomerReservations(email);
        if (customerReservation.size() > 0) {
            customerReservation.forEach(System.out::println);
        } else {
            System.out.println("No Reservation found");
        }

    }

    public void reserveRoom(Scanner reserveScanner) {
        boolean doneLookingForRooms = false;
        IRoom bookedRoom = null;
        Date checkInDate;
        Date checkOutDate;

        do {
            checkInDate = getDate(reserveScanner, "CheckIn");
            checkOutDate = getDate(reserveScanner, "CheckOut");
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

            if (!availableRooms.isEmpty()) {
                System.out.println("the following rooms are available for your stay");
               for(IRoom room: availableRooms){
                    System.out.println(room.toString());
                }
                System.out.println("please enter Room number you would like reserve: ");
                String roomInput = reserveScanner.nextLine();
                bookedRoom = hotelResource.getRoom(roomInput);
                doneLookingForRooms = true;
            }
            else {
                System.out.println("No rooms available for you dates");

                try {
                    System.out.println("Would you like to try different dates: yes or no");
                    String newdates = reserveScanner.nextLine();
                    if (newdates.equalsIgnoreCase("no") || newdates.equalsIgnoreCase("n")) {
                        System.out.println("exiting to main menu");
                        return;
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("invalid response please try again");
                }
            }

        }while (!doneLookingForRooms);

        System.out.println("Enter email for account: ");
        String email = reserveScanner.nextLine();
        Customer customer = hotelResource.getCustomer(email);
        if (customer.getEmail() == null) {
            createAccount(reserveScanner, email);
            customer = hotelResource.getCustomer(email);
        }

        Reservation reservation = hotelResource.bookARoom(email,bookedRoom ,checkInDate, checkOutDate );
        System.out.println("room reserved successfully with");
        System.out.println(reservation.toString());

    }

    private Date getDate(Scanner reserveScanner, String whichDate) {
        boolean finished = false;
        SimpleDateFormat format = new SimpleDateFormat("mm-dd-yyy");
        Date userdate = new Date();
        do {
            System.out.println(String.format("enter %s date with format : %s", whichDate, format.toPattern()));
            String checkout = reserveScanner.nextLine();
            try {
                userdate = format.parse(checkout);
                finished = true;
            } catch (ParseException ex) {
                System.out.println("please enter a a correct formatted date: (mm-dd-yyyy)");
            }
        } while (!finished);
        return userdate;
    }
}
