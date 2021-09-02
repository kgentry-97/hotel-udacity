package com.kgentry;

import com.kgentry.api.AdminResource;
import com.kgentry.api.HotelResource;
import com.kgentry.model.Customer;
import com.kgentry.model.FreeRoom;
import com.kgentry.model.IRoom;
import com.kgentry.model.Room;
import com.kgentry.model.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    AdminResource adminResource = AdminResource.getInstance();
    HotelResource hotelResource = HotelResource.getInstance();

    public void printAdminMenu() {
        System.out.println("Admin Menu");
        System.out.println("=================================");
        System.out.println("1. See all customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to Main Menu");
        System.out.println("6. add test data");
        System.out.println("=================================");
        System.out.println("Select a number from the menu options");
    }

    public void adminMenuSelect() {
        boolean adminFinished = false;
        Scanner scanner = new Scanner(System.in);
        String userInput;
        do {
            printAdminMenu();
            try {
                userInput = scanner.nextLine();
                switch (Integer.parseInt(userInput)) {
                    case 1 -> listCustomers();
                    case 2 -> listRooms();
                    case 3 -> adminResource.displayAllReservations();
                    case 4 -> addRooms(scanner);
                    case 5 -> adminFinished = true;
                    case 6 -> addTestData();
                    default -> System.out.println("invalid number entered please try again");
                }
            } catch (InputMismatchException | ParseException ex) {
                System.out.println("invalid input please try again");
                scanner.nextLine();
            }
        } while (!adminFinished);

    }

    public void addRooms(Scanner roomScanner) {
        RoomType roomInput = null;
        String roomNum = null;
        double roomPrice = 0.0;
        boolean finished = false;
        List<IRoom> newRooms = new ArrayList<>();

        do {
            try {
                System.out.println("enter Room number:");
                roomNum = roomScanner.nextLine();
                System.out.println("enter room price");
                roomPrice = Double.parseDouble(roomScanner.nextLine());
                roomTypeMenu();
                int roomType = Integer.parseInt(roomScanner.nextLine());
                roomInput = RoomType.valueOfNumber(roomType);
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input please try again");

            } catch (IllegalArgumentException ex) {
                System.out.println("invalid room type try again");
            }
            Room room;
            if (roomPrice == 0.0) {
                room = new FreeRoom(roomNum, roomInput);
            } else {
                room = new Room(roomNum, roomPrice, roomInput);
            }
            newRooms.add(room);

            System.out.println("Would you like to add more rooms: yes or no");
            String toEnd = roomScanner.nextLine();
            if (toEnd.equalsIgnoreCase("no") || toEnd.equalsIgnoreCase("n")) {
                finished = true;
            }
        } while (!finished);

        adminResource.AddRoom(newRooms);
    }

    public void roomTypeMenu() {
        System.out.println("room type menu\n" +
                "++++++++++++++++++++++++++++\n" +
                "1. Single\n" +
                "2. Double\n" +
                "++++++++++++++++++++++++++++\n" +
                "Please select a room type");
    }

    public void listRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (!rooms.isEmpty()) {
            rooms.forEach(System.out::println);
        } else {
            System.out.println("no rooms");
        }

    }

    public void listCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (!customers.isEmpty()) {
            customers.forEach(System.out::println);
        } else {
            System.out.println("no Customers");
        }
    }

    public void addTestData() throws ParseException {
        List<IRoom> newRooms = new ArrayList<>();
        Room room = new Room("211", 23.00, RoomType.valueOfNumber(1));
        Room room1 = new Room("221", 26.00, RoomType.valueOfNumber(2));
        Room room2 = new Room("231", 23.00, RoomType.valueOfNumber(2));
        FreeRoom room3 = new FreeRoom("241", RoomType.valueOfNumber(1));
        newRooms.add(room);
        newRooms.add(room1);
        newRooms.add(room2);
        newRooms.add(room3);
        adminResource.AddRoom(newRooms);

        hotelResource.createACustomer("kak@eje.ne", "jsjew", "ejjhe");
        Date checkInDate = createDate("12-11-2021");
        Date checkOutDate = createDate("12-14-2021");
        hotelResource.bookARoom("kak@eje.ne", room1, checkInDate, checkOutDate);
    }

    private Date createDate(String passedDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyy");
        return format.parse(passedDate);
    }

}
