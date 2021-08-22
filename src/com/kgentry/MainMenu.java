package com.kgentry;

import com.kgentry.api.HotelResource;
import com.kgentry.model.Reservation;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

    HotelResource hotelResource = HotelResource.getInstance();


    public void printMainMenu(){
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
                menuOption = scanner.nextInt();
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

    public void createAccount(Scanner accountScanner){
        System.out.println("Enter First Name: ");
        String firstName = accountScanner.nextLine();
        System.out.println("Enter Last name: ");
        String lastname = accountScanner.nextLine();
        System.out.println("Enter Email:");
        String email = accountScanner.nextLine();
        hotelResource.createACustomer(email, firstName, lastname);
    }

    public void findReservation(Scanner findScanner){
        System.out.println("Enter Email of reservation:");
        String email = findScanner.nextLine();
        Collection<Reservation> customerReservation = hotelResource.getCustomerReservations(email);
        if(!customerReservation.isEmpty() || customerReservation != null){
            customerReservation.forEach(System.out::println);
        }
        else {
            System.out.println("No Reservation found");
        }

    }

    public void reserveRoom(Scanner reserveScanner){

        System.out.println("Do you have a account: yes or no");
        String haveAccount = reserveScanner.nextLine();
        if(haveAccount.equalsIgnoreCase("no") || haveAccount.equalsIgnoreCase("n")){
            createAccount(reserveScanner);
        }
        else{
            System.out.println("please enter email:");
            String email = reserveScanner.nextLine();
            hotelResource.getCustomer(email);
        }


    }
}
