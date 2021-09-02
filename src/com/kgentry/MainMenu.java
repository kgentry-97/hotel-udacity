package com.kgentry;

import com.kgentry.api.HotelResource;
import com.kgentry.model.Customer;
import com.kgentry.model.IRoom;
import com.kgentry.model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        System.out.println("could not find an account please create one");
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
        String bookedRoom = null;
        Date checkInDate = getDate(reserveScanner, "CheckIn");
        Date checkOutDate = getDate(reserveScanner, "CheckOut");
        Collection<IRoom> checkedAvailableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

        if (!checkedAvailableRooms.isEmpty()) {
            bookedRoom = getBookedRoom(reserveScanner, checkInDate, checkOutDate, checkedAvailableRooms);
        }
        if(bookedRoom == null || checkedAvailableRooms.isEmpty()) {
            System.out.println("Autochecking up to week for other options");
            bookedRoom = findAltRoom(reserveScanner, checkInDate, checkOutDate);
        }

        if(bookedRoom != null) {

            System.out.println("Enter email for account: ");
            String email = reserveScanner.nextLine();
            Customer customer = hotelResource.getCustomer(email);
            if (customer == null) {
                createAccount(reserveScanner, email);
            }

            IRoom room = hotelResource.getRoom(bookedRoom);
            Reservation reservation = hotelResource.bookARoom(email, room, checkInDate, checkOutDate);
            System.out.println("room reserved successfully:");
            System.out.println(reservation.toString());
        }
        else{
            System.out.println("no room found");
            System.out.println("Returning to main menu");
        }

    }

    private String findAltRoom(Scanner reserveScanner, Date checkInDate, Date checkOutDate) {
        String bookedRoom = null;
        for(int i  =1; i <= 7; i++) {
            Date newCheckInDate = addDate(checkInDate, i);
            Date newCheckOutDate = addDate(checkOutDate, i);
            Collection<IRoom> altAvailableRooms =(hotelResource.findARoom(newCheckInDate, newCheckOutDate));
            if(!altAvailableRooms.isEmpty()){
                bookedRoom = getBookedRoom(reserveScanner, newCheckInDate, newCheckOutDate, altAvailableRooms);
                if(bookedRoom != null ){
                   break;
                }
            }
        }
        return bookedRoom;
    }

    private String getBookedRoom(Scanner reserveScanner, Date checkInDate, Date checkOutDate, Collection<IRoom> availableRooms) {
        String bookedRoom = null;
        printAvailableRooms(availableRooms, checkInDate, checkOutDate);
        System.out.println("Would you like to book one of these rooms: yes or no");
        String bookRoom = reserveScanner.nextLine();
        if(bookRoom.equalsIgnoreCase("yes") || bookRoom.equalsIgnoreCase("y")){
            System.out.println("enter the room number you would like:");
            bookedRoom = reserveScanner.nextLine();
        }
        return bookedRoom;
    }

    private void printAvailableRooms(Collection<IRoom> availableRooms, Date checkInDate, Date checkOutDate) {
        System.out.printf("the following rooms are available for your stay on %s to %s\n", checkInDate, checkOutDate);
        for (IRoom room : availableRooms) {
            System.out.println(room.toString());
        }
    }

    private Date getDate(Scanner reserveScanner, String whichDate) {
        boolean finished = false;
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyy");
        Date userDate = new Date();
        do {
            System.out.printf("enter %s date with format : %s\n", whichDate, format.toPattern());
            String checkout = reserveScanner.nextLine();
            try {
                userDate = format.parse(checkout);
                finished = true;
            } catch (ParseException ex) {
                System.out.println("please enter a correct formatted date: (MM-dd-yyyy)");
            }
        } while (!finished);
        return userDate;
    }

    private Date addDate(Date orgdate, int plusNum){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orgdate);
        calendar.add(Calendar.DATE,plusNum );
        return calendar.getTime();
    }
}
