package com.kgentry;

import com.kgentry.api.AdminResource;
import com.kgentry.model.*;

import java.util.*;

public class AdminMenu {
    AdminResource adminResource = AdminResource.getInstance();

    public void printAdminMenu() {
        System.out.println("Admin Menu");
        System.out.println("=================================");
        System.out.println("1. See all customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to Main Menu");
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
                    default -> System.out.println("invalid number entered please try again");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input only numbers expected");
                scanner.nextLine();
            }
        } while (!adminFinished);

    }

    public void addRooms(Scanner roomScanner) {
        //Scanner roomScanner = new Scanner(System.in);
        RoomType roomInput = null;
        String roomNum = null;
        double roomPrice = 0.0;
        boolean finshed = false;
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

            }
            catch(IllegalArgumentException ex){
                System.out.println("invalid room type try again");
            }
            Room room;
            if (roomPrice == 0.0){
                room = new FreeRoom(roomNum,roomInput);
            }
            else {
                room = new Room(roomNum, roomPrice, roomInput);
            }
            newRooms.add(room);

            System.out.println("Would you like to add more rooms: yes or no");
            String toEnd = roomScanner.nextLine();
            if(toEnd.equalsIgnoreCase("no") || toEnd.equalsIgnoreCase("n")) {
                finshed = true;
            }
        } while (!finshed);

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

    public void listRooms(){
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if(!rooms.isEmpty()){
           rooms.forEach(System.out::println);
        }
        else {
            System.out.println("no rooms");
        }

    }

    public void listCustomers(){
        Collection<Customer> customers = adminResource.getAllCustomers();
        if(!customers.isEmpty()){
            customers.forEach(System.out::println);
        }
        else {
            System.out.println("no Customers");
        }
    }

}
